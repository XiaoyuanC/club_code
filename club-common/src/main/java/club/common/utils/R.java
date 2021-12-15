package club.common.utils;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
@Data
public  class R<T> implements Serializable {
    private String isSuccess;
    private String msg;
    private String level;
    private String code = "200";
    private T data;
    //Timestamp prevents json caching
    private Long timestamp;
    public R(String isSuccess, String msg, T data){
        this.isSuccess = isSuccess;
        this.msg = msg;
        this.data = data;
        //Set timestamp
        this.timestamp = JsonTimestampUtil.getTimestamp(new Date());
    }
    //success
    public static <T> R success(T data){
        return new R("success","",data);
    }
    public static R success(String msg){
        return new R("success",msg,null);
    }
    //fail
    public static R error(String errInfo){
        R error = new R("error", errInfo, null);
        error.code = "500";
        return error;
    }
    //message notification
    public static R msg(String msg){
        return new R("msg",msg,null);
    }
    //data
    public R data(T data){
        this.data = data;
        return this;
    }

    public R message(String msg){
        this.msg = msg;
        return this;
    }

    public R level(String level){
        this.level = level;
        return this;
    }
    public R code(String code){
        this.code = code;
        return this;
    }
}
