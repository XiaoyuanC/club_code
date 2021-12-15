package club.server.utils;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/*
 *@Description
 *@Author Chen
 *@Date 2021/10/16 22:34
 */
public class LogoutUtil {
    private static Map<String,Long> logoutMap;
    //Singleton mode
    private static Map<String, Long> getLogoutMap() {
        if(logoutMap==null){
            synchronized (LogoutUtil.class){
                if(logoutMap==null){
                    logoutMap = new ConcurrentHashMap();
                }
                return logoutMap;
            }
        }
        return logoutMap;
    }

    public static boolean get(String username) {
        Long aLong = getLogoutMap().get(username);
        if(aLong != null){
            return true;
        }
        return false;
    }
    public static void set(String username,Long expiresAt) {
        getLogoutMap().put(username,expiresAt);
    }
    //Timed tasks remove the data inside
    public static boolean remove(String username) {
        return false;
    }
}
