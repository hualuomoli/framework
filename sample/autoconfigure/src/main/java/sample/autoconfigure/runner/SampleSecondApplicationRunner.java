package sample.autoconfigure.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

@Service(value = "sample.autoconfigure.runner.SampleSecondApplicationRunner")
public class SampleSecondApplicationRunner implements ApplicationRunner, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(SampleSecondApplicationRunner.class);

    /** 不设置相当于设置了 {@link Ordered#LOWEST_PRECEDENCE} */
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("sample second runner running...");
    }

}
