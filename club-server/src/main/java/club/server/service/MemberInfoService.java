package club.server.service;

import club.common.utils.R;
import club.server.model.query.ClubMemberTempQuery;
import club.server.model.query.StudentClubQuery;

/*
 *@Description
 *@Author Chen
 *@Date 2021/11/6 19:21
 */
public interface MemberInfoService {
    //Get current user information through token
    R getStudentInfoByToken();
    //Get the community that the current user has joined through the token
    R getClubInfoByToken(StudentClubQuery query);
    //Apply to quit the club
    R deleteClubInfoByToken(String clubCode);
    //Apply to join the club
    R addClubInfoByToken(String clubCode);
    //Get historical information
    R history(StudentClubQuery query);
}
