package com.dyc.order.cashier.data.response;

/**
 * @author 梁栋 2020/3/15 10:40
 * 返回各个状态订单的数量
 */
public class OrderCountData {

    // 待付款数量
    private int initNum;
    // 待收货/配送中数量
    private int inTransNum;
    // 待自提数量
    private int waitForReceiptNum;
    // 待发货
    private int waitForSend;
    // 待配送
    private int waitForDelivery;
    // 申请退款
    private int refundApplying;

    public int getInitNum() {
        return initNum;
    }

    public void setInitNum(int initNum) {
        this.initNum = initNum;
    }

    public int getInTransNum() {
        return inTransNum;
    }

    public void setInTransNum(int inTransNum) {
        this.inTransNum = inTransNum;
    }

    public int getWaitForReceiptNum() {
        return waitForReceiptNum;
    }

    public void setWaitForReceiptNum(int waitForReceiptNum) {
        this.waitForReceiptNum = waitForReceiptNum;
    }

    public int getWaitForSend() {
        return waitForSend;
    }

    public void setWaitForSend(int waitForSend) {
        this.waitForSend = waitForSend;
    }

    public int getWaitForDelivery() {
        return waitForDelivery;
    }

    public void setWaitForDelivery(int waitForDelivery) {
        this.waitForDelivery = waitForDelivery;
    }

    public int getRefundApplying() {
        return refundApplying;
    }

    public void setRefundApplying(int refundApplying) {
        this.refundApplying = refundApplying;
    }
}
