package club.server.service;

import club.common.utils.R;
import club.server.model.entity.ClubInfoTempEntity;
import club.server.model.query.ClubInfoTempQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 18:07
 */
public interface ClubInfoTempService extends IService<ClubInfoTempEntity> {
    R addClubInfoTemp(ClubInfoTempEntity entity);

    //Paging to obtain records to be reviewed
    R getClubInfoTempPending(ClubInfoTempQuery query);

    //Obtain the review records of the revised club information submitted by manager
    R getClubInfoTempByClubCodeId(ClubInfoTempQuery query);

    R pass(String id);

    R forbid(String id);
}
