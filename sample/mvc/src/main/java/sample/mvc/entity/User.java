package sample.mvc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class User {

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
