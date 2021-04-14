package pack.base.boot;

import com.github.hualuomoli.boot.autoconfigure.env.ClasspathEnvironmentPostProcessor;

public class PackBaseClasspathEnvironmentPostProcessor extends ClasspathEnvironmentPostProcessor {

    @Override
    protected String resourceName() {
        return "application-base";
    }

}
