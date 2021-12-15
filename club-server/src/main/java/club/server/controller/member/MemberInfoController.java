package club.server.controller.member;

import club.common.utils.R;
import club.server.model.entity.UserInfoEntity;
import club.server.model.query.StudentClubQuery;
import club.server.service.MemberInfoService;
import club.server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/16 23:01
 */
@RestController
@RequestMapping("/api/member")
public class MemberInfoController {
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private UserInfoService userInfoService;

    //Get personal information
    @GetMapping("/info")
    public R getInfo() {
        return memberInfoService.getStudentInfoByToken();
    }

    @PostMapping("/club")
    R getClubInfo(@RequestBody StudentClubQuery query) {
        return memberInfoService.getClubInfoByToken(query);
    }

    //Application to quit the club
    @GetMapping("/temp/delete/{clubCode}")
    R deleteClubInfo(@PathVariable("clubCode") String clubCode) {
        return memberInfoService.deleteClubInfoByToken(clubCode);
    }

    //Application to join the club
    @GetMapping("/temp/add/{clubCode}")
    R addClubInfo(@PathVariable("clubCode") String clubCode) {
        return memberInfoService.addClubInfoByToken(clubCode);
    }

    //Application record
    @PostMapping("/temp/history")
    R history(@RequestBody StudentClubQuery query) {
        return memberInfoService.history(query);
    }

    //Get verification code
    @GetMapping("/email/{email}")
    R reset(@PathVariable("email") String email) {
        return userInfoService.sendEmail(email);
    }
    //Modify email address
    @PostMapping("/info/update")
    R updateEmail(@RequestBody UserInfoEntity entity){
        return userInfoService.updateEmail(entity);
    }
}
