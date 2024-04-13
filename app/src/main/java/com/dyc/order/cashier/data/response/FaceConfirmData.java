package com.dyc.order.cashier.data.response;

/**
 * func:
 * author:丁语成 on 2020/3/31 14:58
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */


/**
 * 人脸识别返回的信息
 *     {"code":"response.success",
 *     "msg":"操作成功",
 *     "data":
 *     [
 *     {
 *     "id":202,"username":"doing","phoneNumber":"18877776666","
 *     status":0,"faceId":"D02K5Sawyj","facePicture":"D02K5Sawyj.jpg",
 *     "cardNo":"0000000000113","merchantId":1,"levelName":"VIP1"
 *     }
 *     ],
 *     "serverTime":1585636808409}
 *
 */
public class FaceConfirmData {
	public int id;
	public String username;
	public String phoneNumber;
	public int status;
	public String faceId;
	public String facePicture;
	public String cardNo;
	public int merchantId;
	public String levelName;
}
