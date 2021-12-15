package club.server.controller.common;

import club.common.utils.R;
import club.server.model.entity.UserInfoEntity;
import club.server.model.query.ClubActQuery;
import club.server.model.query.ClubInfoQuery;
import club.server.service.ClubActService;
import club.server.service.ClubInfoService;
import club.server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/27 21:50
 */
@RestController
@RequestMapping("/api/common/")
public class HomeController {
    @Autowired
    private ClubInfoService clubInfoService;
    @Autowired
    private ClubActService clubActService;
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/club")
    public R getAllClubMap() {
        return clubInfoService.getAllClubMap();
    }

    @PostMapping("/club/query")
    public R queryClub(@RequestBody ClubInfoQuery query) {
        return clubInfoService.queryClubInfo(query);
    }

    @GetMapping("/act")
    public R getClubActInfo(ClubActQuery query) {
        return clubActService.getActInfo(query);
    }
    @PostMapping("/act/query")
    public R queryActInfo(@RequestBody ClubActQuery query) {
        return clubActService.queryActInfo(query);
    }
    @GetMapping("/act/info/{id}")
    public R getClubActInfo(@PathVariable("id") String id) {
        return clubActService.getActInfoById(id);
    }

    @GetMapping("/club/info/{clubCode}")
    public R getClubInfo(@PathVariable("clubCode") String clubCode) {
        return clubInfoService.getClubInfoByClubCode(clubCode);
    }

    //Log entry
    @PostMapping("/login")
    public R login(@Valid @RequestBody UserInfoEntity entity) {
        return userInfoService.login(entity);
    }

    //Member registration entrance
    @PostMapping("/register")
    public R register(@RequestBody UserInfoEntity entity) {
        //Set type
        entity.setType(0);
        return userInfoService.register(entity);
    }

    //Reset password
    @PostMapping("/reset")
    public R reset(@RequestBody UserInfoEntity entity) {
        return userInfoService.reset(entity);
    }
}
