package sample.autoconfigure.env;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SampleClasspathEnvironmentPostProcessorTest {

    @Value(value = "${sample.name}")
    private String name;
    @Value(value = "${location}")
    private String location;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("spring.profiles.active", "dev,test");
    }

    @Test
    public void test() {
        Assert.assertEquals("name for test", name);
        Assert.assertEquals("location for absolute", location);
    }

}