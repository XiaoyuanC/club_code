package club.common.utils;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/24 15:26
 */
public class CodeUtil {
    public static String getCode() {
        //Random six digits
        return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
    }
}
