package sample.autoconfigure.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import sample.autoconfigure.service.SampleService;
import sample.other.service.OtherService;

@Service(value = "sample.autoconfigure.runner.SampleApplicationRunner")
public class SampleApplicationRunner implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SampleApplicationRunner.class);

    @Autowired
    private OtherService otherService;
    @Autowired
    private SampleService sampleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("sample runner running...");
        String message = otherService.show("sample");
        sampleService.print(message);
    }

}
