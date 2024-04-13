package com.dyc.order.cashier.data.local;

import com.dyc.order.cashier.constant.GoodsActivityConstant;
import com.dyc.order.cashier.constant.OrderFieldConstant;
import com.dyc.order.cashier.data.fields.PaymentRequestFields;
import com.dyc.order.cashier.data.response.GoodsInfoList;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.administrator.toollibrary.utils.NumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * func: 购物车数据
 * author:丁语成 on 2020/2/27 15:01
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class ShoppingCart {
	private final static MLogger logger = MLogger.getLogger(ShoppingCart.class);
	private final static Map<String, ShoppingCartItem> cartNow = new TreeMap<>();
	private final static Set<OnGoodsChangeListener> goodsChangeListeners = new HashSet<>();
	private static double oriPrice = 0;
	private static double totalPrice = 0;
	private static double totalNum = 0;
	private static boolean isMember;

	public static boolean isIsMember() {
		return isMember;
	}

	public static void setIsMember(boolean isMember) {
		ShoppingCart.isMember = isMember;
		for (ShoppingCartItem item : cartNow.values()){
			item.setMember(isMember);
		}
		calItemPrice();
	}

	public static boolean contains(GoodsInfoList.GoodsDetailData data){
		if (data != null && data.getBarcode() != null){
			return cartNow.containsKey(data.getBarcode());
		}
		return false;
	}

	public static ShoppingCartItem getItem(GoodsInfoList.GoodsDetailData item){
		if (cartNow.containsKey(item.getBarcode())){
			return cartNow.get(item.getBarcode());
		}
		return null;
	}

	public static double getTotalDiscountPrice(){
		double discount = 0;
		for (ShoppingCartItem item : cartNow.values()){
			discount += (item.data.getPrice() - item.getActualPrice()) * item.num;
		}
		return discount;
	}

	public static double getMemberDiscountPrice(){
		double discount = 0;
		boolean oriIsMember = isMember;
		setIsMember(true);
		for (ShoppingCartItem item : cartNow.values()){
			if (item.getPriceType() == ShoppingCartItem.MEMBER){
				discount += (item.data.getPrice() - MemberCenter.getMemberPrice(item.data)) * item.num;
			}
		}
		setIsMember(oriIsMember);
		return discount;
	}

	private static void calItemPrice(){
		double res = 0.0;
		double oriRes = 0.0;
		for (ShoppingCartItem item : cartNow.values()){
			res += item.actualPrice * item.num;
			res = NumUtils.remain2Num(res);
			oriRes += item.getData().getPrice() * item.num;
			oriRes = NumUtils.remain2Num(oriRes);
		}
		totalPrice = res;
		oriPrice = oriRes;
	}

	private static double calItemNum(ShoppingCartItem item){
		totalNum += item.getNum();
		if (totalNum > 0){
			return totalNum;
		}
		return  0;
	}

	public static void clearCartNow(){
		List<ShoppingCartItem> oriCart = new ArrayList<>(cartNow.values());
		cartNow.clear();
		totalPrice = 0;
		totalNum = 0;
		for (OnGoodsChangeListener listener : goodsChangeListeners){
			for (ShoppingCartItem item : oriCart) {
				listener.onGoodsChange(-item.num, item);
			}
		}
	}

	public static void addToShoppingCart(ShoppingCartItem item){
		double changeNum = item.num;
		item.setMember(isIsMember());
		if (item.data.getBarcode() != null
				&& cartNow.containsKey(item.data.getBarcode())
				&& cartNow.get(item.data.getBarcode()) != null){
			//已存在
			GoodsInfoList.GoodsDetailData data = item.getData();
			String barcode = data.getBarcode();
			ShoppingCartItem itemInCart = cartNow.get(barcode);
			//商品信息变更，防止购买的时候更新商品信息出问题
			itemInCart.data = data;
			double oriNum = itemInCart.num;
			itemInCart.add(item.getNum());
			//减少的数量超出或等于原有数量
			if (itemInCart.getNum() <= 0){
				cartNow.remove(barcode);
				changeNum = -oriNum;
			}
		}else {
			//新增
			if (item.getData().getBarcode() == null){
				item.getData().setBarcode("" + System.currentTimeMillis());
			}
			cartNow.put(item.getData().getBarcode(), item);
		}
		calItemNum(item);
		calItemPrice();
		for (OnGoodsChangeListener listener : goodsChangeListeners){
			listener.onGoodsChange(changeNum, item);
		}
	}

	public static Map<String, ShoppingCartItem> getCartNow() {
		return cartNow;
	}

	public static ArrayList<ShoppingCartItem> getCartItems(){
		return new ArrayList<>(cartNow.values());
	}

	public static int[] getCartGoodsId(){
		int[] ids = new int[cartNow.size()];
		ArrayList<ShoppingCartItem> items = new ArrayList<>(cartNow.values());
		for (int i = 0; i < items.size(); ++i){
			ids[i] = items.get(i).data.getId();
		}
		return ids;
	}

	public static PaymentRequestFields getPaymentRequest(int memberId) {
		List<ShoppingCart.ShoppingCartItem> items = new ArrayList<>(ShoppingCart.getCartNow().values());
		logger.info("getPaymentRequest:" + cartNow.size());
		PaymentRequestFields fields = new PaymentRequestFields();
		//非直接付款
		fields.setFlag(false);
		//没申请权限到不了这
//		@SuppressLint("MissingPermission")
//		String serial = "DIV012000099999"();
		String serial = "D1V012000099999";
		//订单金额等
		fields.setOrder(new PaymentRequestFields.Order(-1d,
				OrderFieldConstant.OrderSource.POS.getNum(), null, 0,
				ShoppingCart.getTotalNum(), totalPrice,null,
				memberId, null, serial, serial,
				oriPrice));
		//是自助模式
		fields.getOrder().setSelf(true);
		if (memberId == -1){
			fields.getOrder().setMemberId(null);
		}
		//商品设置
		fields.setProductInfoList(new PaymentRequestFields.ProductInfoList[items.size()]);
		for (int i = 0; i < items.size(); ++i){
			ShoppingCart.ShoppingCartItem item = items.get(i);
			PaymentRequestFields.ProductInfoList infoList = new PaymentRequestFields.ProductInfoList(
					item.getNum(), NumUtils.remain2Num(item.getActualPrice() * item.getNum()),
					item.getActualPrice(), item.getNum(), false, item.getData().getId(),
					item.getData().getName(), item.getData().getRemark(),
					NumUtils.remain2Num(item.getNum() * item.getData().getPrice())
			);
			fields.getProductInfoList()[i] = infoList;
		}
		fields.setTransProp(true);
		return fields;
	}

	public static double getTotalPrice() {
		return totalPrice;
	}

	public static double getOriPrice() {
		return oriPrice;
	}

	public static double getTotalNum() {
		return totalNum;
	}

	public static void addGoodsChangeListener(OnGoodsChangeListener listener){
		goodsChangeListeners.add(listener);
	}

	public static void removeGoodsChangeListener(OnGoodsChangeListener listener){
		goodsChangeListeners.remove(listener);
	}

	public interface OnGoodsChangeListener{
		void onGoodsChange(double changeNum, ShoppingCartItem item);
	}

	public static class ShoppingCartItem {
		public final static int NONE = 0;
		public final static int MEMBER = 1;
		public final static int ACTIVITY = 2;
		public final static int CHANGED_PRICE = 3;

		private boolean isMember = false;
		private int priceType = NONE;
		private double actualPrice;
		private double num;
		private GoodsInfoList.GoodsDetailData data;

		public ShoppingCartItem(GoodsInfoList.GoodsDetailData data, double num) {
			this.data = data;
			this.num = num;
			chooseActualPrice();
		}

		public ShoppingCartItem(boolean isMember, double num, GoodsInfoList.GoodsDetailData data) {
			this.isMember = isMember;
			this.num = num;
			this.data = data;
			chooseActualPrice();
		}

		public boolean isMember() {
			return isMember;
		}

		public void setMember(boolean member) {
			isMember = member;
			chooseActualPrice();
		}

		public void add(double num){
			this.num += num;
		}

		public void chooseActualPrice() {
			if (data != null){
				this.actualPrice = data.getPrice() != null ? data.getPrice() : 0;
				double memberPrice = MemberCenter.getMemberPrice(data);
				double oriPrice = data.getPrice();
				double activityPrice = oriPrice;
				if (data.getActivityInfos() != null
						&& data.getActivityInfos().length > 0) {
					List<GoodsInfoList.GoodsDetailData.GoodsActivityInfos> activityInfosList
							= Arrays.asList(data.getActivityInfos());
					//找出特价/减价
					for (GoodsInfoList.GoodsDetailData.GoodsActivityInfos infos : activityInfosList) {
						if (infos.getType() == GoodsActivityConstant.ACTIVITY_TYPE_SPECIAL_PRICE
								|| infos.getType() == GoodsActivityConstant.ACTIVITY_TYPE_DISCOUNT) {
							activityPrice = infos.getDiscountPrice();
							break;
						}
					}
				}
				logger.info(data.getName() + " memberPrice:" + memberPrice
						+ " oriPrice:" + oriPrice + " activityPrice:" + activityPrice
						+ " isMember:" + (isMember));
				if (isMember) {
					if (MemberCenter.getMemberInfoData() != null &&
							MemberCenter.getMemberInfoData().isEnableMemberPrice()){
						//是会员且有活动价
						if (activityPrice > 0){
							if (memberPrice <= activityPrice && memberPrice > 0) {
								actualPrice = memberPrice;
								priceType = MEMBER;
							} else if (activityPrice < oriPrice){
								actualPrice = activityPrice;
								priceType = ACTIVITY;
							}else {
								actualPrice = oriPrice;
								priceType = NONE;
							}
						} else if (memberPrice < oriPrice && memberPrice > 0){
							//是会员但没有活动价且会员价比原价便宜
							actualPrice = memberPrice;
							priceType = MEMBER;
						}else {
							//是会员且没有活动价且会员价反而贵？
							actualPrice = oriPrice;
							priceType = NONE;
						}
					}
				}else {
					//不是会员
					if (activityPrice > 0) {
						//有活动价且便宜
						if (activityPrice < oriPrice){
							actualPrice = activityPrice;
							priceType = ACTIVITY;
						}else {
							priceType = NONE;
						}
					}else {
						//没有活动价
						priceType = NONE;
						actualPrice = oriPrice;
					}
				}
			}else {
				//data为null
				priceType = NONE;
			}
			logger.info("actualPrice:" + actualPrice + " priceType:" + priceType);
		}

		public GoodsInfoList.GoodsDetailData getData() {
			return data;
		}

		public void setData(GoodsInfoList.GoodsDetailData data) {
			this.data = data;
		}

		public double getNum() {
			return num;
		}

		public void setNum(double num) {
			this.num = num;
		}

		public int getPriceType() {
			return priceType;
		}

		public double getActualPrice() {
			return actualPrice;
		}

		public void setActualPrice(double actualPrice) {
			this.actualPrice = actualPrice;
		}
	}
}
