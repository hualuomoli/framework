package sample.mvc;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.hualuomoli.boot.mvc.handler.CustomHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Optional;

@Component(value = "sample.mvc.SampleCustomHandler")
public class SampleCustomHandler implements CustomHandler {

    @Value(value = "${project.basePackage}")
    private String basePackage;

    @Override
    public boolean customClass(Class<?> clazz) {

        // ignore enum
        if (Enum.class.isAssignableFrom(clazz)) {
            return false;
        }

        return clazz.getName().startsWith(basePackage);
    }

    @Override
    public String parameterName(Field field) {
        return Optional.ofNullable(field.getAnnotation(JsonProperty.class)).map(JsonProperty::value).orElse(field.getName());
    }

}
