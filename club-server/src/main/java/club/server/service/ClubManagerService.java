package club.server.service;

import club.common.utils.R;
import club.server.model.entity.ClubManagerEntity;
import club.server.model.query.ClubManagerQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 22:49
 */
public interface ClubManagerService extends IService<ClubManagerEntity> {
    R addManager(ClubManagerEntity entity);

    R deleteManagerById(String id);

    R queryManager(ClubManagerQuery query);

    R getClubManagerToOptionByUserId();

    R resetManagerPassword(String userId);
}
