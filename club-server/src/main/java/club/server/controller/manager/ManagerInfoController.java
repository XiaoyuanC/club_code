package club.server.controller.manager;

import club.common.utils.R;
import club.server.model.entity.ClubActTempEntity;
import club.server.model.entity.ClubInfoTempEntity;
import club.server.model.entity.ClubMemberEntity;
import club.server.model.query.ClubActTempQuery;
import club.server.model.query.ClubInfoTempQuery;
import club.server.model.query.StudentClubQuery;
import club.server.model.query.StudentInfoQuery;
import club.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/16 23:01
 */
@RestController
@RequestMapping("/api/manager")
public class ManagerInfoController {
    @Autowired
    private ClubManagerService clubManagerService;
    @Autowired
    private ClubInfoTempService clubInfoTempService;
    @Autowired
    private ClubActTempService clubActTempService;
    @Autowired
    private ClubMemberService clubMemberService;
    @Autowired
    private ClubMemberTempService clubMemberTempService;
    @Autowired
    private StudentInfoService studentInfoService;

    //Obtain the clubs that the manager can manage
    @GetMapping("/club")
    public R getCurrentManagerAndClubMap() {
        return clubManagerService.getClubManagerToOptionByUserId();
    }

    //The manager modifies the club information
    @PostMapping("/club/temp/add")
    public R clubTempAdd(@RequestBody @Validated ClubInfoTempEntity clubInfoTempEntity) {
        return clubInfoTempService.addClubInfoTemp(clubInfoTempEntity);
    }

    //The manager posts new activity information
    @PostMapping("/act/temp/add")
    public R actTempAdd(@RequestBody @Validated ClubActTempEntity clubActTempEntity) {
        return clubActTempService.actTempAdd(clubActTempEntity);
    }

    //Query audit records
    @PostMapping("/club/temp/query")
    public R getClubInfoTempByClubCodeId(@RequestBody ClubInfoTempQuery query) {
        return clubInfoTempService.getClubInfoTempByClubCodeId(query);
    }

    //Query audit records
    @PostMapping("/act/temp/query")
    public R getClubActTemp(@RequestBody ClubActTempQuery query) {
        return clubActTempService.getClubActTemp(query);
    }

    //Add new member
    @PostMapping("/member/add")
    public R addNewMemberInfo(@RequestBody ClubMemberEntity entity) {
        return clubMemberService.addNewMemberInfo(entity);
    }

    //Delete a member
    @GetMapping("/member/delete/{id}")
    public R deleteMemberInfo(@PathVariable("id") String id) {
        return clubMemberService.deleteMemberInfo(id);
    }

    //Search member
    @PostMapping("/member/query")
    R getInfoByStudentOrClub(@RequestBody StudentClubQuery query) {
        return clubMemberService.getInfoByStudentOrClub(query);
    }

    //Update member positions
    @PostMapping("/member/update")
    public R updateMemberInfo(@RequestBody ClubMemberEntity entity) {
        return clubMemberService.updateMemberInfo(entity);
    }

    //Pending review list
    @PostMapping("/member/temp/get")
    public R getClubMemberTempWait(@RequestBody StudentClubQuery query) {
        return clubMemberTempService.getClubMemberTempByClubCode(query);
    }

    //Membership application approved
    @GetMapping("/member/temp/pass/{id}")
    public R passClubMemberTemp(@PathVariable("id") String id) {
        return clubMemberTempService.passClubMemberTemp(id);
    }

    //Membership application review failed
    @GetMapping("/member/temp/forbid/{id}")
    public R forbidClubMemberTemp(@PathVariable("id") String id) {
        return clubMemberTempService.forbidClubMemberTemp(id);
    }

    //Get student information
    @PostMapping("/student/query")
    public R studentInfoService(@RequestBody StudentInfoQuery query) {
        return studentInfoService.queryStudentInfo(query);
    }
}
