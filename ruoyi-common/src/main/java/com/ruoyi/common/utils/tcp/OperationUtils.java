package com.ruoyi.common.utils.tcp;

/**
 * 异或运算工具类
 *
 * @author snkj
 * @create 2023-07-10 20:29
 */
public class OperationUtils {

    public static String yihuo(String content) {
        content = change(content);
        String[] b = content.split(" ");
        int a = 0;
        for (int i = 0; i < b.length; i++) {
            a = a ^ Integer.parseInt(b[i], 16);
        }
        if (a < 10) {
            StringBuffer sb = new StringBuffer();
            sb.append("0");
            sb.append(a);
            return sb.toString();
        }
        return Integer.toHexString(a);
    }

    public static String change(String content) {
        String str = "";
        for (int i = 0; i < content.length(); i++) {
            if (i % 2 == 0) {
                str += " " + content.substring(i, i + 1);
            } else {
                str += content.substring(i, i + 1);
            }
        }
        return str.trim();
    }

    public static void main(String[] args) {
        String str = "5501A15F00";
        String yihuo = yihuo(str);
        System.out.println(yihuo);
    }

}
