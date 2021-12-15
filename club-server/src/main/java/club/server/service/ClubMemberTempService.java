package club.server.service;

import club.common.utils.R;
import club.server.model.entity.ClubMemberTempEntity;
import club.server.model.query.ClubMemberTempQuery;
import club.server.model.query.StudentClubQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/31 20:43
 */
public interface ClubMemberTempService extends IService<ClubMemberTempEntity> {
    R addClubMemberTemp(ClubMemberTempEntity entity);
    R getClubMemberTempByClubCode(StudentClubQuery query);
    R getClubMemberTempByStudentNo(StudentClubQuery query);
    R getClubMemberTempWait(ClubMemberTempQuery query);
    R passClubMemberTemp(String id);
    R forbidClubMemberTemp(String id);
    Boolean addClubTempMemberCheck(String studentNo,String clubCode,String type);
}
