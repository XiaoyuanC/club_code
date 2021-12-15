package club.server.service;

import club.common.utils.R;
import club.server.model.entity.UserInfoEntity;
import club.server.model.query.UpdatePasswordQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 23:17
 */
public interface UserInfoService extends IService<UserInfoEntity> {
    R login(UserInfoEntity entity);
    R register(UserInfoEntity entity);
    R logout();
    R password(UpdatePasswordQuery query);
    R reset(UserInfoEntity entity);
    R sendEmail(String email);
    R updateEmail(UserInfoEntity entity);
}
