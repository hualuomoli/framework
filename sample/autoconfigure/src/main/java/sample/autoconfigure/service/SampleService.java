package sample.autoconfigure.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(value = "sample.autoconfigure.service.SampleService")
public class SampleService {

    private static final Logger logger = LoggerFactory.getLogger(SampleService.class);

    public void print(String message) {
        logger.info("{}", message);
    }

}
