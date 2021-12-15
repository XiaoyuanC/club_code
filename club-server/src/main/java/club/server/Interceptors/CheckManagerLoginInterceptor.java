package club.server.Interceptors;

import club.common.utils.StringUtil;
import club.server.utils.JwtUtil;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CheckManagerLoginInterceptor implements HandlerInterceptor {
    //check token
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        /**
         * It was intercepted
         */
        String token = request.getHeader("token");
        if(!StringUtil.isNullOrEmpty(token)){
            //token expired
            DecodedJWT decodedJWT = JwtUtil.verifyToken(token);
            if(decodedJWT!=null&&decodedJWT.getClaim("type").asInt()==1){
                return true;
            }
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        //Create Json object
        JSONObject jsonObject = new JSONObject();
        //Set the properties of the Json object
        jsonObject.put("code", "403");
        jsonObject.put("type", "error");
        jsonObject.put("msg", "Login timed out, please login first");
        //Return Json data
        response.getWriter().write(jsonObject.toString());
        return false;
    }
}
