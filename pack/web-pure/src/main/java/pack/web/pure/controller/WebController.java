package pack.web.pure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pack.base.entity.User;
import pack.base.service.UserService;
import pack.common.service.CommonService;

import java.util.Date;

@RequestMapping(value = "/")
@Controller(value = "pack.web.pure.controller.WebController")
public class WebController {

    @Autowired
    private UserService userService;
    @Autowired
    private CommonService commonService;

    @RequestMapping(value = "/")
    @ResponseBody
    public String execute(@RequestParam(name = "username") String username, String nickname) {
        // print
        commonService.print();

        // save
        User user = new User();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setGender("MALE");
        user.setLastLoginTime(new Date());
        user.setLastUpdateTime(new Date());
        userService.save(user);

        return "SUCCESS";
    }

}
