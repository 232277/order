package com.dyc.order.cashier.data.response;

import java.util.List;

/**
 * @author liupengcheng@centerm.com
 * @date 2020-03
 * @desc 统计查询返回接口
 */
public class StaticsQueryData {
    /**
     * addMemberNum : 0
     * beginDate :
     * customerRefundPrice : 0
     * customerSales : 0
     * customerSalesTimes : 0
     * customerTurnover : 0
     * endDate :
     * memberSales : 0
     * memberSalesRate : 0
     * memberSalesTimes : 0
     * orderMemberNum : 0
     * profit : 0
     * profitRate : 0
     * refundPrice : 0
     * refundStatistics : [{"amount":0,"payType":0,"times":0}]
     * refundTimes : 0
     * saleStatistics : [{"amount":0,"payType":0,"times":0}]
     * sales : 0
     * salesTimes : 0
     * storePrice : 0
     * storeTimes : 0
     * totalMemberNum : 0
     * turnover : 0
     * turnoverList : [{"amount":0,"time":""}]
     * unitNum : 0
     * unitPrice : 0
     */

    private double addMemberNum;
    private String beginDate;
    private double customerRefundPrice;
    private double customerSales;
    private int customerSalesTimes;
    private double customerTurnover;
    private String endDate;
    private double memberSales;
    private double memberSalesRate;
    private int memberSalesTimes;
    private int orderMemberNum;
    private double profit;
    private double profitRate;
    private double refundPrice;
    private int refundTimes;
    private double sales;
    private int salesTimes;
    private double storePrice;
    private int storeTimes;
    private int totalMemberNum;
    private double turnover;
    private double unitNum;
    private double unitPrice;
    private List<RefundStatisticsBean> refundStatistics;
    private List<SaleStatisticsBean> saleStatistics;
    private List<TurnoverListBean> turnoverList;

    public double getAddMemberNum() {
        return addMemberNum;
    }

    public void setAddMemberNum(double addMemberNum) {
        this.addMemberNum = addMemberNum;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public double getCustomerRefundPrice() {
        return customerRefundPrice;
    }

    public void setCustomerRefundPrice(double customerRefundPrice) {
        this.customerRefundPrice = customerRefundPrice;
    }

    public double getCustomerSales() {
        return customerSales;
    }

    public void setCustomerSales(double customerSales) {
        this.customerSales = customerSales;
    }

    public int getCustomerSalesTimes() {
        return customerSalesTimes;
    }

    public void setCustomerSalesTimes(int customerSalesTimes) {
        this.customerSalesTimes = customerSalesTimes;
    }

    public double getCustomerTurnover() {
        return customerTurnover;
    }

    public void setCustomerTurnover(double customerTurnover) {
        this.customerTurnover = customerTurnover;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getMemberSales() {
        return memberSales;
    }

    public void setMemberSales(double memberSales) {
        this.memberSales = memberSales;
    }

    public double getMemberSalesRate() {
        return memberSalesRate;
    }

    public void setMemberSalesRate(double memberSalesRate) {
        this.memberSalesRate = memberSalesRate;
    }

    public int getMemberSalesTimes() {
        return memberSalesTimes;
    }

    public void setMemberSalesTimes(int memberSalesTimes) {
        this.memberSalesTimes = memberSalesTimes;
    }

    public int getOrderMemberNum() {
        return orderMemberNum;
    }

    public void setOrderMemberNum(int orderMemberNum) {
        this.orderMemberNum = orderMemberNum;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getProfitRate() {
        return profitRate;
    }

    public void setProfitRate(double profitRate) {
        this.profitRate = profitRate;
    }

    public double getRefundPrice() {
        return refundPrice;
    }

    public void setRefundPrice(double refundPrice) {
        this.refundPrice = refundPrice;
    }

    public int getRefundTimes() {
        return refundTimes;
    }

    public void setRefundTimes(int refundTimes) {
        this.refundTimes = refundTimes;
    }

    public double getSales() {
        return sales;
    }

    public void setSales(double sales) {
        this.sales = sales;
    }

    public int getSalesTimes() {
        return salesTimes;
    }

    public void setSalesTimes(int salesTimes) {
        this.salesTimes = salesTimes;
    }

    public double getStorePrice() {
        return storePrice;
    }

    public void setStorePrice(double storePrice) {
        this.storePrice = storePrice;
    }

    public int getStoreTimes() {
        return storeTimes;
    }

    public void setStoreTimes(int storeTimes) {
        this.storeTimes = storeTimes;
    }

    public int getTotalMemberNum() {
        return totalMemberNum;
    }

    public void setTotalMemberNum(int totalMemberNum) {
        this.totalMemberNum = totalMemberNum;
    }

    public double getTurnover() {
        return turnover;
    }

    public void setTurnover(double turnover) {
        this.turnover = turnover;
    }

    public double getUnitNum() {
        return unitNum;
    }

    public void setUnitNum(double unitNum) {
        this.unitNum = unitNum;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public List<RefundStatisticsBean> getRefundStatistics() {
        return refundStatistics;
    }

    public void setRefundStatistics(List<RefundStatisticsBean> refundStatistics) {
        this.refundStatistics = refundStatistics;
    }

    public List<SaleStatisticsBean> getSaleStatistics() {
        return saleStatistics;
    }

    public void setSaleStatistics(List<SaleStatisticsBean> saleStatistics) {
        this.saleStatistics = saleStatistics;
    }

    public List<TurnoverListBean> getTurnoverList() {
        return turnoverList;
    }

    public void setTurnoverList(List<TurnoverListBean> turnoverList) {
        this.turnoverList = turnoverList;
    }

    public static class RefundStatisticsBean {
        /**
         * amount : 0
         * payType : 0
         * times : 0
         */

        private double amount;
        private int payType;
        private int times;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }

    public static class SaleStatisticsBean {
        /**
         * amount : 0
         * payType : 0
         * times : 0
         */

        private double amount;
        private int payType;
        private int times;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }

    public static class TurnoverListBean {
        /**
         * amount : 0
         * time :
         */

        private double amount;
        private String time;

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
