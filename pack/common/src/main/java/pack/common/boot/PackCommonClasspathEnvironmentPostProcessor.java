package pack.common.boot;

import com.github.hualuomoli.boot.autoconfigure.env.ClasspathEnvironmentPostProcessor;

public class PackCommonClasspathEnvironmentPostProcessor extends ClasspathEnvironmentPostProcessor {

    @Override
    protected String resourceName() {
        return "application-common";
    }

}
