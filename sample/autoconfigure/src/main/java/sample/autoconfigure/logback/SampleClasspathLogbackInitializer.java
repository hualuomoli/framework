package sample.autoconfigure.logback;

import com.github.hualjomoli.boot.autoconfigure.logback.ClasspathLogbackInitializer;

public class SampleClasspathLogbackInitializer extends ClasspathLogbackInitializer {

    @Override
    protected String logbackName() {
        return "sample-logback";
    }

}
