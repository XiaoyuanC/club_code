package club.server.service.impl;

import club.common.utils.R;
import club.common.utils.StringUtil;
import club.server.dao.ClubManagerDao;
import club.server.model.entity.ClubManagerEntity;
import club.server.model.entity.UserInfoEntity;
import club.server.model.query.ClubManagerQuery;
import club.server.model.vo.ClubManagerVo;
import club.server.service.ClubManagerService;
import club.server.service.UserInfoService;
import club.server.utils.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 22:51
 */
@Service("clubManagerService")
public class ClubManagerServiceImpl extends ServiceImpl<ClubManagerDao, ClubManagerEntity> implements ClubManagerService {
    @Autowired
    private UserInfoService userInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R addManager(ClubManagerEntity entity) {
        QueryWrapper<ClubManagerEntity> qw = new QueryWrapper<>();
        qw.eq("user_id", entity.getUserId());
        qw.eq("club_code", entity.getClubCode());
        if (this.count(qw) > 0) {
            return R.success("Successfully entered the club manager’s information");
        }
        //Retrieve whether there is account information
        QueryWrapper<UserInfoEntity> user = new QueryWrapper<>();
        user.eq("username", entity.getUserId());
        user.eq("type", 1);
        if (userInfoService.count(user) < 1) {
            //Create an account
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setType(1);
            userInfoEntity.setUsername(entity.getUserId());
            userInfoEntity.setPassword("123456");
            if (!userInfoService.save(userInfoEntity)) {
                throw new RuntimeException();
            }
        }
        //Add manager information
        if (this.save(entity)) {
            return R.success("Successfully entered the club manager’s information");
        }
        throw new RuntimeException();
    }

    @Override
    public R deleteManagerById(String id) {
        if (this.getById(id) == null) {
            return R.error("No relevant information to operate");
        }
        if (this.removeById(id)) {
            return R.success("Successfully removed the club manager");
        }
        return R.error("Failed to remove the club manager");
    }

    @Override
    public R queryManager(ClubManagerQuery query) {
        //get data
        QueryWrapper<ClubManagerQuery> qw = new QueryWrapper<>();
        qw.eq(StringUtil.notNullOrEmpty(query.getUserId()), "t1.user_id", query.getUserId());
        qw.eq(StringUtil.notNullOrEmpty(query.getClubCode()), "t1.club_code", query.getClubCode());
        qw.like(StringUtil.notNullOrEmpty(query.getClubName()), "t2.club_name", query.getClubName());
        qw.eq(true, "t2.club_exit", 1);
        qw.orderByDesc("t1.create_date");
        IPage iPage = this.baseMapper.queryClubManagerInfo(query.getIPage(), qw);
        return R.success(iPage);
    }

    @Override
    public R getClubManagerToOptionByUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        List<Map<String, String>> maps = new ArrayList<>();
        try {
            DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(token);
            String userId = decodedJWT.getClaim("userId").asString();
            List<ClubManagerVo> clubManagerInfo = this.baseMapper.getClubManagerInfo(userId);
            clubManagerInfo.forEach(item -> {
                Map<String, String> obj = new HashMap<>();
                obj.put("key", item.getClubCode());
                obj.put("value", item.getClubName());
                maps.add(obj);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.success(maps);
    }

    @Override
    public R resetManagerPassword(String userId) {
        LambdaUpdateWrapper<UserInfoEntity> qw = new LambdaUpdateWrapper<>();
        qw.eq(true, UserInfoEntity::getUsername, userId);
        qw.eq(true, UserInfoEntity::getType, 1);
        qw.set(true, UserInfoEntity::getPassword, "123456");
        qw.set(true, UserInfoEntity::getUpdateDate, LocalDateTime.now());
        if (userInfoService.update(qw)) {
            return R.success("Password reset successfully");
        }
        return R.error("Password reset failed");
    }
}
