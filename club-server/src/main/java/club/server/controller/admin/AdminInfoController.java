package club.server.controller.admin;

import club.common.utils.R;
import club.server.model.entity.ClubActEntity;
import club.server.model.entity.ClubInfoEntity;
import club.server.model.entity.ClubManagerEntity;
import club.server.model.query.ClubActTempQuery;
import club.server.model.query.ClubInfoTempQuery;
import club.server.model.query.ClubManagerQuery;
import club.server.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/24 15:26
 */
@RestController()
@RequestMapping("/api/admin")
public class AdminInfoController {
    @Autowired
    private ClubInfoTempService clubInfoTempService;
    @Autowired
    private ClubInfoService clubInfoService;
    @Autowired
    private ClubManagerService clubManagerService;
    @Autowired
    private ClubActService clubActService;
    @Autowired
    private ClubActTempService clubActTempService;

    //The record of the approval of the club information modification application
    @GetMapping("/club/temp/pass/{id}")
    public R passClub(@PathVariable("id") String id) {
        return clubInfoTempService.pass(id);
    }

    //Record of failed review of club information modification application
    @GetMapping("/club/temp/forbid/{id}")
    public R forbidClub(@PathVariable("id") String id) {
        return clubInfoTempService.forbid(id);
    }

    //Search club
    @PostMapping("/club/temp/query")
    public R getClubInfoTempPending(@RequestBody ClubInfoTempQuery query) {
        return clubInfoTempService.getClubInfoTempPending(query);
    }

    //Add club
    @PostMapping("/club/add")
    public R addClub(@RequestBody @Valid ClubInfoEntity entity) {
        return clubInfoService.addClub(entity);
    }

    //Modify club information
    @PostMapping("/club/update")
    public R updateClub(@RequestBody @Valid ClubInfoEntity entity) {
        return clubInfoService.updateClubById(entity);
    }

    //Delete club information
    @GetMapping("/club/delete/{clubCode}")
    public R deleteClub(@PathVariable("clubCode") String clubCode) {
        return clubInfoService.deleteClubById(clubCode);
    }

    //Add club manager
    @PostMapping("/manager/add")
    public R addManager(@RequestBody ClubManagerEntity clubManagerEntity) {
        return clubManagerService.addManager(clubManagerEntity);
    }

    //Reset the password of the club manager
    @GetMapping("/manager/reset/{userId}")
    public R resetManagerPassword(@PathVariable("userId") String userId) {
        return clubManagerService.resetManagerPassword(userId);
    }

    //Delate club manager
    @GetMapping("/manager/delete/{id}")
    public R deleteManagerById(@PathVariable("id") String id) {
        return clubManagerService.deleteManagerById(id);
    }

    //Search club manager
    @PostMapping("/manager/query")
    public R queryManager(@RequestBody ClubManagerQuery query) {
        return clubManagerService.queryManager(query);
    }

    //Modify activity information
    @PostMapping("/act/update")
    public R updateAct(@RequestBody ClubActEntity entity) {
        return clubActService.updateActInfo(entity);
    }

    //Delete activity information
    @GetMapping("/act/delete/{clubCode}")
    public R deleteAct(@PathVariable("clubCode") String clubCode) {
        return clubActService.deleteActInfoById(clubCode);
    }

    //Activity information approved
    @GetMapping("/act/temp/pass/{id}")
    public R passAct(@PathVariable("id") String id) {
        return clubActTempService.pass(id);
    }

    //Activity information review failed
    @GetMapping("/act/temp/forbid/{id}")
    public R forbidAct(@PathVariable("id") String id) {
        return clubActTempService.forbid(id);
    }

    //Pending activity information
    @PostMapping("/act/temp/query")
    R queryClubActTemp(@RequestBody ClubActTempQuery query) {
        return clubActTempService.queryClubActTemp(query);
    }
}
