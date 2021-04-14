package pack.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.base.entity.User;

@Service(value = "pack.base.service.UserService")
@Transactional(readOnly = true)
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Value(value = "${pack.base.name}")
    private String name;

    @Transactional(readOnly = false)
    public void save(User user) {
        logger.info("name: {}, user: {}", name, user);
    }

}
