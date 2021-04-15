package sample.mvc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sample.mvc.entity.User;

@RequestMapping(value = "/user")
@Controller(value = "sample.mvc.controller.UserController")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/add")
    @ResponseBody
    public User add(User user) {
        logger.info("user {}", user);
        return user;
    }

}
