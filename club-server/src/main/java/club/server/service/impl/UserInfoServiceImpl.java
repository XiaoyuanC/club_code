package club.server.service.impl;

import club.common.utils.CodeUtil;
import club.common.utils.JsonTimestampUtil;
import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.UserInfoDao;
import club.server.model.entity.StudentInfoEntity;
import club.server.model.entity.UserInfoEntity;
import club.server.model.query.UpdatePasswordQuery;
import club.server.model.vo.LoginVo;
import club.server.service.StudentInfoService;
import club.server.service.UserInfoService;
import club.server.utils.JwtUtil;
import club.server.utils.LogoutUtil;
import club.server.utils.QueryConditionSpliceUtil;
import club.third.email.ClubSimpleEmail;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 23:17
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfoEntity> implements UserInfoService {
    private static final String member = "/member";
    private static final String manager = "/manager";
    private static final String admin = "/admin";
    //Verification code validity period
    private static final long TIME = 60000*5;
    //Verification code
    private static final Map<String,String> codes = new ConcurrentHashMap();
    @Autowired
    private StudentInfoService studentInfoService;
    @Override
    public R login(UserInfoEntity entity) {
        QueryWrapper<UserInfoEntity> condition = new QueryWrapper<>();
        if (entity.getType() == null) {
            return R.error("Please select the login type before logging in");
        }
        LoginVo loginVo = new LoginVo();
        //login
        if (entity.getType() == 0) {
            QueryConditionSpliceUtil.setWrapper(condition, entity);
            UserInfoEntity one = this.getOne(condition);
            if (one != null) {
                one.setPassword("");
                String token = JwtUtil.createToken(one.getUsername(), one.getType(), JSONObject.toJSONString(one));
                loginVo.setPath(member);
                loginVo.setToken(token);
                return R.success("login successful").data(loginVo);
            }
            return R.error("Incorrect username or password");
        }
        if (entity.getType() == 1) {
            QueryConditionSpliceUtil.setWrapper(condition, entity);
            UserInfoEntity one = this.getOne(condition);
            if (one != null) {
                one.setPassword("");
                String token = JwtUtil.createToken(one.getUsername(), one.getType(), JSONObject.toJSONString(one));
                loginVo.setPath(manager);
                loginVo.setToken(token);
                return R.success("login successful").data(loginVo);
            }
            return R.error("Incorrect username or password");
        }
        if (entity.getType() == 2) {
            QueryConditionSpliceUtil.setWrapper(condition, entity);
            UserInfoEntity one = this.getOne(condition);
            if (one != null) {
                one.setPassword("");
                String token = JwtUtil.createToken(one.getUsername(), one.getType(), JSONObject.toJSONString(one));
                loginVo.setPath(admin);
                loginVo.setToken(token);
                return R.success("login successful").data(loginVo);
            }
            return R.error("Incorrect username or password");
        }
        return R.error("Incorrect login type");
    }

    @Override
    public R register(UserInfoEntity entity) {
        if (StringUtil.isNullOrEmpty(entity.getUsername())) {
            return R.error("username is null or blank");
        }
        //Check whether the database has student information
        StudentInfoEntity byId = studentInfoService.getById(entity.getUsername());
        if (byId == null) {
            return R.error("There is no record of the student number in the database, so it cannot be registered").code("201");
        }
        //Check whether the data has been registered
        QueryWrapper<UserInfoEntity> qw = new QueryWrapper<>();
        qw.eq("username", entity.getUsername());
        qw.eq("type", 0);
        List<UserInfoEntity> list = this.list(qw);
        if (list.size() > 0) {
            return R.error("The student ID has been registered for this type of account");
        }
        entity.setType(0);
        boolean save = this.save(entity);
        if (save) {
            return R.success("register success");
        }
        return R.success("register failed");
    }

    @Override
    public R logout() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        try {
            DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(token);
            LogoutUtil.set(decodedJWT.getClaim("userId").asString() + JsonTimestampUtil.getTimestamp(decodedJWT.getExpiresAt()),
                    JsonTimestampUtil.getTimestamp(decodedJWT.getExpiresAt()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success("Logout successfully");
    }

    @Override
    public R password(UpdatePasswordQuery query) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        try {
            DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(token);
            String userId = decodedJWT.getClaim("userId").asString();
            LambdaUpdateWrapper<UserInfoEntity> qw = new LambdaUpdateWrapper<>();
            qw.eq(true, UserInfoEntity::getUsername, userId);
            qw.eq(true, UserInfoEntity::getPassword, query.getOldPassword());
            qw.set(true, UserInfoEntity::getPassword, query.getNewPassword());
            qw.set(true, UserInfoEntity::getUpdateDate, LocalDateTime.now());
            if (this.update(qw)) {
                this.logout();
                return R.success("Successfully reset password");
            }
            return R.error("The original password is wrong").code("201");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("Password reset failed").code("201");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R reset(UserInfoEntity entity) {
        if (StringUtil.isNullOrEmpty(entity.getEmail())) {
            return R.error("Email address cannot be blank");
        }
        if (StringUtil.isNullOrEmpty(entity.getUsername())) {
            return R.error("Username cannot be empty");
        }
        String code = CodeUtil.getCode();
        //Operational database
        //search
        StudentInfoEntity byId = studentInfoService.getById(entity.getUsername());
        if(byId==null||!entity.getEmail().equals(byId.getEmail())){
            return R.error("Password reset failed, your email address information address does not match");
        }
        //Modify
        LambdaUpdateWrapper<UserInfoEntity> qw = new LambdaUpdateWrapper<>();
        qw.eq(true, UserInfoEntity::getUsername, entity.getUsername());
        qw.eq(true, UserInfoEntity::getType, 0);
        qw.set(true, UserInfoEntity::getPassword, code);
        qw.set(true, UserInfoEntity::getUpdateDate, LocalDateTime.now());
        if (!this.update(qw)) {
            return R.error("Password reset failed");
        }
        try {
            ClubSimpleEmail.send(entity.getEmail(),"Reset Password","Your new password is:"+code);
        } catch (EmailException e) {
            throw new RuntimeException();
        }
        return R.success("The password reset was successful, and the new password has been sent to your email");
    }

    @Override
    public R sendEmail(String email) {
        //get verification code
        String code = CodeUtil.getCode();
        String key = email + getUid();
        try {
            ClubSimpleEmail.send(email,"(Valid for 5 minutes) Your verification code is:",code);
            codes.put(key,code);
            //Start a scheduled task
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(TIME);
                        //Clear verification code
                        codes.remove(key);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (EmailException e) {
            e.printStackTrace();
        }
        return R.success("Email verification code sent successfully");
    }

    @Override
    public R updateEmail(UserInfoEntity entity) {
        String uid = getUid();
        String s = this.codes.get(entity.getEmail() + uid);
        if(StringUtil.isNullOrEmpty(s)){
            return R.error("The verification code has expired");
        }
        if(!s.equals(entity.getCode())){
            return R.error("Incorrect verification code");
        }
        //Modify
        LambdaUpdateWrapper<StudentInfoEntity> lqw = new LambdaUpdateWrapper<>();
        lqw.eq(true,StudentInfoEntity::getStudentNo,uid);
        lqw.set(true,StudentInfoEntity::getEmail,entity.getEmail());
        lqw.set(true,StudentInfoEntity::getUpdateDate,LocalDateTime.now());
        if (studentInfoService.update(lqw)) {
            return R.success("Saved successfully");
        }
        return R.error("The modification is abnormal");
    }

    //get token
    private String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        return token;
    }

    //Get user ID
    private String getUid() {
        DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(this.getToken());
        String userId = decodedJWT.getClaim("userId").asString();
        return userId;
    }
}
