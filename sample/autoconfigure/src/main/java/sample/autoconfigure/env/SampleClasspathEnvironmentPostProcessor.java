package sample.autoconfigure.env;

import com.github.hualuomoli.boot.autoconfigure.env.ClasspathEnvironmentPostProcessor;

public class SampleClasspathEnvironmentPostProcessor extends ClasspathEnvironmentPostProcessor {

    @Override
    protected String resourceName() {
        return "sample-application";
    }

}
