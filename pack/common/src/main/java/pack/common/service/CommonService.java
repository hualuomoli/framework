package pack.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pack.common.entity.CommonInfo;

@Service(value = "pack.common.service.CommonService")
public class CommonService {

    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private CommonInfo commonInfo;

    public void print() {
        logger.info("project name {}", commonInfo.getProjectName());
        logger.info("charset {}", commonInfo.getCharset());
    }

}
