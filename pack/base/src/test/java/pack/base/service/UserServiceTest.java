package pack.base.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pack.base.entity.User;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void save() {
        User user = new User();
        user.setUsername("hualuomoli");
        user.setNickname("花落寞离");
        user.setGender("MALE");
        user.setLastLoginTime(new Date());
        user.setLastUpdateTime(new Date());
        userService.save(user);
    }

}