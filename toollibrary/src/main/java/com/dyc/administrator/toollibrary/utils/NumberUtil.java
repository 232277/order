package com.dyc.administrator.toollibrary.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
    public static String convertMoney(String moneyInput) {
        //格式转化，转化成000000000001
        String amountFormat;
        if (moneyInput.indexOf(".") == -1) {
            long moneyInt = Long.parseLong(moneyInput, 10);
            if (checkIntegralPart(moneyInt)) {
                return moneyInput;
            }
            return String.format("%010d00", moneyInt);
        } else {
            String[] moneyParts = moneyInput.split("\\.");
            if (moneyParts.length > 3) {
            }

            long moneyIntegralPart = Long.parseLong(moneyParts[0], 10);
            if (checkIntegralPart(moneyIntegralPart)) {
            }

            String fractionalPartStr = moneyParts[1];
            if (fractionalPartStr.length() > 2) {
                fractionalPartStr = fractionalPartStr.substring(0, 2);
            }

            //丁语成 2020年7月23日15:18:44 添加
            if(fractionalPartStr.length()==1){
                fractionalPartStr=fractionalPartStr+"0";
            }
            Long moneyFractionalPart = Long.parseLong(fractionalPartStr, 10);
            amountFormat = String.format("%010d%02d", moneyIntegralPart, moneyFractionalPart);
            return amountFormat;
        }
    }

    public static void main(String[] args){
        double amount1 = Double.parseDouble("0.01");
        double amount2 = 0.02d;
        double amount3 = 0.001d;
        double amount4 = 1d;
        double amount5 = 0.049d;
        double amount6 = 0.99d;
        double amount7 = 1.001d;
        double amount8 = 1.01d;
        double amount9 = 1.00d;
        System.out.println(convertMoney(Double.toString(amount1)));
        System.out.println(convertMoney(Double.toString(amount2)));
        System.out.println(convertMoney(Double.toString(amount3)));
        System.out.println(convertMoney(Double.toString(amount4)));
        System.out.println(convertMoney(Double.toString(amount5)));
        System.out.println(convertMoney(Double.toString(amount6)));
        System.out.println(convertMoney(Double.toString(amount7)));
        System.out.println(convertMoney(Double.toString(amount8)));
        System.out.println(convertMoney(Double.toString(amount9)));
    }

    private static boolean checkIntegralPart(long moneyInt) {
        if (moneyInt > 9999999999L) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 0000000001250转化成12.50
     * 默认返回0.00
     */
    public static String unformatMount(String mount) {
        if ("".equals(mount) || mount == null) {
            return "0.00";
        }
        double money = 0;
        try {
            money = (double) (Long.parseLong(mount) * 0.01);
        } catch (Exception e) {
            return "0.00";
        }
        if (money > 0) {
            DecimalFormat df = new DecimalFormat("##0.00");
            return df.format(money);

        } else {
            return "0.00";
        }
    }
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("-?[0-9]+(.[0-9]+)?");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
