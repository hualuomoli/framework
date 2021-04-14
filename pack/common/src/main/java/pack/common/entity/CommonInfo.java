package pack.common.entity;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Getter
@Component(value = "pack.common.entity.CommonInfo")
public class CommonInfo {

    @Value(value = "${project.name}")
    private String projectName;
    @Value(value = "${project.charset}")
    private Charset charset;

}
