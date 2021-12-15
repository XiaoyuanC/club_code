package club.common.utils;
import java.util.Date;
public class JsonTimestampUtil {
    public static Long getTimestamp(Date date){
        if (null == date) {
            return (long) 0;
        }
        String timestamp = String.valueOf(date.getTime());
        return Long.valueOf(timestamp);
    }
}
