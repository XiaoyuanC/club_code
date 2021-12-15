package club.server.service.impl;

import club.common.utils.R;
import club.server.model.entity.ClubMemberTempEntity;
import club.server.model.query.StudentClubQuery;
import club.server.service.ClubMemberService;
import club.server.service.ClubMemberTempService;
import club.server.service.MemberInfoService;
import club.server.service.StudentInfoService;
import club.server.utils.JwtUtil;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*
 *@Description
 *@Author Chen
 *@Date 2021/11/6 19:23
 */
@Service("memberInfoService")
public class MemberInfoServiceImpl implements MemberInfoService {
    @Autowired
    private StudentInfoService studentInfoService;
    @Autowired
    private ClubMemberService clubMemberService;
    @Autowired
    private ClubMemberTempService clubMemberTempService;

    @Override
    public R getStudentInfoByToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        try {
            DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(token);
            String userId = decodedJWT.getClaim("userId").asString();
            return studentInfoService.getStudentInfoByStudentNo(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("").data(null);
    }

    @Override
    public R getClubInfoByToken(StudentClubQuery query) {
        try {
            DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(this.getToken());
            String userId = decodedJWT.getClaim("userId").asString();
            //Query by student number
            query.setStudentNo(userId);
            R infoByStudentOrClub = clubMemberService.getInfoByStudentOrClub(query);
            return infoByStudentOrClub;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("").data(null);
    }

    @Override
    public R deleteClubInfoByToken(String clubCode) {
        try {
            DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(this.getToken());
            String userId = decodedJWT.getClaim("userId").asString();
            //Check whether the application exists
            Boolean exit = clubMemberTempService.addClubTempMemberCheck(userId, clubCode, "exit");
            if (!exit) {
                return R.success("One of your records is under review, please do not submit repeatedly");
            }
            //Package data
            ClubMemberTempEntity clubMemberTempEntity = new ClubMemberTempEntity();
            clubMemberTempEntity.setClubCode(clubCode);
            clubMemberTempEntity.setStudentNo(userId);
            clubMemberTempEntity.setType("exit");
            return clubMemberTempService.addClubMemberTemp(clubMemberTempEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("Operation failed");
    }

    @Override
    public R addClubInfoByToken(String clubCode) {
        try {
            DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(this.getToken());
            String userId = decodedJWT.getClaim("userId").asString();
            //Check if it has been joined
            Boolean aBoolean = clubMemberService.addMemberInfoCheck(userId, clubCode);
            if (!aBoolean) {
                return R.success("You are already in the club");
            }
            //Check whether the application exists
            Boolean exit = clubMemberTempService.addClubTempMemberCheck(userId, clubCode, "enter");
            if (!exit) {
                return R.success("One of your records is under review, please do not submit repeatedly");
            }
            //Package data
            ClubMemberTempEntity clubMemberTempEntity = new ClubMemberTempEntity();
            clubMemberTempEntity.setClubCode(clubCode);
            clubMemberTempEntity.setStudentNo(userId);
            clubMemberTempEntity.setType("enter");
            return clubMemberTempService.addClubMemberTemp(clubMemberTempEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return R.error("Operation failed");
    }

    @Override
    public R history(StudentClubQuery query) {
        //Set account information
        query.setStudentNo(this.getUid());
        return clubMemberTempService.getClubMemberTempByStudentNo(query);
    }

    //Get token
    private String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        return token;
    }

    //Get user ID
    private String getUid() {
        DecodedJWT decodedJWT = JwtUtil.getDecodedJWT(this.getToken());
        String userId = decodedJWT.getClaim("userId").asString();
        return userId;
    }
}
