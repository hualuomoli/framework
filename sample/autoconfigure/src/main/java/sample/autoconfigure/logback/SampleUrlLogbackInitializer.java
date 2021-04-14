package sample.autoconfigure.logback;

import com.github.hualuomoli.boot.autoconfigure.logback.UrlLogbackInitializer;
import sample.autoconfigure.env.SampleFileEnvironmentPostProcessor;

import java.io.File;

public class SampleUrlLogbackInitializer extends UrlLogbackInitializer {

    @Override
    protected String urlString() {
        String path = SampleFileEnvironmentPostProcessor.class.getClassLoader().getResource("location/logback-url.xml").getPath();
        File file = new File(path);
        try {
            return file.toURI().toURL().toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // end
    }

}
