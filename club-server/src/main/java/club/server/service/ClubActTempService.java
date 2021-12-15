package club.server.service;

import club.common.utils.R;
import club.server.model.entity.ClubActTempEntity;
import club.server.model.query.ClubActTempQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 22:31
 */
public interface ClubActTempService extends IService<ClubActTempEntity> {
    R actTempAdd(ClubActTempEntity entity);

    R getEntityPendingByClubCodeId(String clubCode);

    R getAllEntityByClubCodeId(String clubCode);

    R getClubActTemp(ClubActTempQuery query);

    R queryClubActTemp(ClubActTempQuery query);

    R pass(String id);

    R forbid(String id);
}
