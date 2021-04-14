package sample.autoconfigure.env;

import com.github.hualjomoli.boot.autoconfigure.env.FileEnvironmentPostProcessor;

public class SampleFileEnvironmentPostProcessor extends FileEnvironmentPostProcessor {

    @Override
    protected String filename() {
        return SampleFileEnvironmentPostProcessor.class.getClassLoader().getResource("location/application.yml").getPath();
    }

}
