package club.common.utils;
public class StringUtil {
    //Determine whether the string object is an empty object or a null character
    public static boolean isNullOrEmpty(Object obj){
        if(obj==null){
            return true;
        }
        if(obj instanceof String){
            return ((String) obj).isEmpty();
        }
        return false;
    }

    public static boolean notNullOrEmpty(Object obj){
        return !isNullOrEmpty(obj);
    }
}
