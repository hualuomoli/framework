package com.github.hualuomoli.boot.mvc.method;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.hualuomoli.boot.mvc.handler.CustomHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

public class CustomExtendedServletRequestDataBinderTest {

    @BeforeClass
    public static void beforeClass() {
        CustomExtendedServletRequestDataBinder.setCustomHandler(new CustomHandler() {
            @Override
            public boolean customClass(Class<?> clazz) {
                return clazz != null && clazz.getName().startsWith("com.github.hualuomoli.boot.mvc");
            }

            @Override
            public String parameterName(Field field) {
                return Optional.ofNullable(field.getAnnotation(JsonProperty.class)).map(JsonProperty::value).orElse(null);
            }
        });
    }

    @Test
    public void parseParameterName() {

        Class<?> clazz = Request.class;

        // 一级
        Assert.assertEquals("username", this.parseParameterName("username", clazz));
        Assert.assertEquals("nickname", this.parseParameterName("nick_name", clazz));
        Assert.assertEquals("hobbies", this.parseParameterName("hobby", clazz));
        Assert.assertEquals("hobbies[0]", this.parseParameterName("hobby[0]", clazz));

        // 两级
        Assert.assertEquals("user.username", this.parseParameterName("user.username", clazz));
        Assert.assertEquals("user.nickname", this.parseParameterName("user.nickname", clazz));
        Assert.assertEquals("user.hobbies", this.parseParameterName("user.hobby", clazz));
        Assert.assertEquals("user.hobbies[0]", this.parseParameterName("user.hobby[0]", clazz));
        Assert.assertEquals("users[0].username", this.parseParameterName("users[0].username", clazz));
        Assert.assertEquals("users[0].nickname", this.parseParameterName("users[0].nickname", clazz));
        Assert.assertEquals("users[0].hobbies", this.parseParameterName("users[0].hobby", clazz));
        Assert.assertEquals("users[0].hobbies[0]", this.parseParameterName("users[0].hobby[0]", clazz));

        // 多级
        Assert.assertEquals("user.homeAddress.province", this.parseParameterName("user.home_address.province", clazz));
        Assert.assertEquals("user.addresses[0].county", this.parseParameterName("user.address[0].county", clazz));
        Assert.assertEquals("users[1].homeAddress.province", this.parseParameterName("users[1].home_address.province", clazz));
        Assert.assertEquals("users[1].addresses[0].city", this.parseParameterName("users[1].address[0].city", clazz));
    }

    private String parseParameterName(String parameterName, Class<?> clazz) {
        CustomExtendedServletRequestDataBinder binder = new CustomExtendedServletRequestDataBinder(null, null);
        try {
            Method method = CustomExtendedServletRequestDataBinder.class.getDeclaredMethod("parseParameterName", new Class[]{String.class, Class.class});
            return (String) method.invoke(binder, parameterName, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Getter
    @Setter
    @ToString
    public static class Request {
        /** 用户名 */
        private String username;
        /** 昵称 */
        @JsonProperty(value = "nick_name")
        private String nickname;
        /** 爱好 */
        @JsonProperty(value = "hobby")
        private List<String> hobbies;
        /** 用户 */
        private User user;
        /** 用户 */
        private List<User> users;
    }


    @Getter
    @Setter
    @ToString
    public static class User {

        /** 用户名 */
        private String username;
        /** 昵称 */
        @JsonProperty(value = "nick_name")
        private String nickname;
        /** 性别 */
        private Gender gender;
        /** 爱好 */
        @JsonProperty(value = "hobby")
        private List<String> hobbies;
        /** 居住地 */
        @JsonProperty(value = "home_address")
        private Address homeAddress;
        /** 住址 */
        @JsonProperty(value = "address")
        private List<Address> addresses;

        @Getter
        @Setter
        @ToString
        public static class Address {
            /** 省 */
            private String province;
            /** 市 */
            private String city;
            /** 县 */
            private String county;
            /*** 详细地址 */
            @JsonProperty(value = "info")
            private String detail;
        }

        public static enum Gender {
            /** 女 */
            FEMALE,
            /** 男 */
            MALE,
        }

    }

}