package club.server.service;

import club.common.utils.R;
import club.server.model.entity.ClubInfoEntity;
import club.server.model.query.ClubInfoQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 18:07
 */
public interface ClubInfoService extends IService<ClubInfoEntity> {
    R addClub(ClubInfoEntity entity);

    R deleteClubById(String id);

    R updateClubById(ClubInfoEntity entity);

    R getClubInfoById(String id);

    R getClubInfoByClubCode(String clubCode);

    R getAllEntity();

    //Get key-value pairs
    R getAllClubMap();

    R queryClubInfo(ClubInfoQuery query);

    R getAllClubInfoToOption();

    int addClubPeople(String clubCode);

    int subClubPeople(String clubCode);
}
