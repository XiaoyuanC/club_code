package club.server.service;

import club.common.utils.R;
import club.server.model.entity.ClubMemberEntity;
import club.server.model.query.StudentClubQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/31 20:43
 */
public interface ClubMemberService extends IService<ClubMemberEntity> {
    //Add new member
    R addNewMemberInfo(ClubMemberEntity entity);

    //Change member position
    R updateMemberInfo(ClubMemberEntity entity);

    //Delete member
    R deleteMemberInfo(String id);

    //Get the corresponding members of the club
    R getInfoByStudentOrClub(StudentClubQuery query);

    //Check whether the user is already in the community by checking the community ID and student ID
    Boolean addMemberInfoCheck(String studentNo, String clubCode);
}
