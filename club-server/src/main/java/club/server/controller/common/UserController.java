package club.server.controller.common;

import club.common.utils.R;
import club.server.model.entity.UserInfoEntity;
import club.server.model.query.UpdatePasswordQuery;
import club.server.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/*
 *@Description
 *@Author Chen
 *@Date 2021/10/16 20:44
 */
@RestController
@RequestMapping("/api/user/")
public class UserController {
    @Autowired
    private UserInfoService userInfoService;

    //User logout
    @GetMapping("/logout")
    public R logout() {
        return userInfoService.logout();
    }

    //Change password
    @PostMapping("/password")
    public R password(@RequestBody @Validated UpdatePasswordQuery query) {
        return userInfoService.password(query);
    }

}
