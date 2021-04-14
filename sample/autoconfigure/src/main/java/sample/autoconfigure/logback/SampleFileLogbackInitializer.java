package sample.autoconfigure.logback;

import com.github.hualjomoli.boot.autoconfigure.logback.FileLogbackInitializer;
import sample.autoconfigure.env.SampleFileEnvironmentPostProcessor;

public class SampleFileLogbackInitializer extends FileLogbackInitializer {

    @Override
    protected String filename() {
        return SampleFileEnvironmentPostProcessor.class.getClassLoader().getResource("location/logback.xml").getPath();
    }

}
