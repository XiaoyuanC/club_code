package club.server.utils;
import club.common.utils.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.lang.reflect.Field;
import java.util.Locale;
/*
 *@Description Used to encapsulate queryWrapper query conditions
 *@Author Chen
 *@Date 2021/10/16 20:56
 */
public class QueryConditionSpliceUtil {
    /**
     *
     * @param wrapper Wrapper to be spliced
     * @param obj To check the package query entity
     */
    public static void setWrapper(QueryWrapper wrapper,Object obj){
        Class<?> aClass = obj.getClass();
        //Get all fields
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            try {
                Object fieldValue = declaredField.get(obj);
                if(fieldValue==null){
                    continue;
                }
                //Determine if it is a string
                if(fieldValue instanceof String){
                    if (StringUtil.isNullOrEmpty((String) fieldValue)) {
                        continue;
                    }
                }
                //Attribute name
                String fieldName = declaredField.getName();
                //Convert to column name
                String sqlColumnName = getSqlColumn(fieldName);
                //
                wrapper.eq(sqlColumnName,fieldValue);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    //Field check
    private static String getSqlColumn(String filed){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(filed);
        String d = "create_date";
        //Sign
        int t = 0;
        for (int i = 0; i < filed.length(); i++) {
            //uppercase letter
            if(filed.charAt(i)>='A'&&filed.charAt(i)<='Z'){
                //Save the position of the space in
                stringBuilder.insert(i+(t++),'_');
            }
        }
        //Change all characters to lowercase
        String result = stringBuilder.toString().toLowerCase(Locale.ROOT);
        return result;
    }
}
