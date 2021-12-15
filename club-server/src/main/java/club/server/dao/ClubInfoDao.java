package club.server.dao;
import club.server.model.entity.ClubInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 18:11
 */
public interface ClubInfoDao extends BaseMapper<ClubInfoEntity> {
    public int addClubPeople(String clubCode);
    public int subClubPeople(String clubCode);
}
