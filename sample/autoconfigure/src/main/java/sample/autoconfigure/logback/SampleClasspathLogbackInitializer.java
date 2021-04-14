package sample.autoconfigure.logback;

import com.github.hualuomoli.boot.autoconfigure.logback.ClasspathLogbackInitializer;

public class SampleClasspathLogbackInitializer extends ClasspathLogbackInitializer {

    @Override
    protected String logbackName() {
        return "sample-logback";
    }

}
