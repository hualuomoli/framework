package com.wizhong.ln.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.hualuomoli.validator.Validator;
import com.github.hualuomoli.validator.constraints.Greater;
import com.github.hualuomoli.validator.constraints.Less;
import com.github.hualuomoli.validator.lang.InvalidParameterException;

public class ValidatorTest {

  private static final Logger logger = LoggerFactory.getLogger(Validator.class);

  @Test(expected = InvalidParameterException.class)
  public void testBasic() {
    ValidatorTest.Basic basic = new ValidatorTest.Basic();
    basic.age = 18;
    try {
      Validator.valid(basic);
    } catch (Exception e) {
      logger.info("{}", e.getMessage());
      throw e;
    }
  }

  // 简单数据类型
  public class Basic {

    @NotBlank(message = "用户名不能为空") // 自定义信息
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private String nickname;

    @Min(value = 20)
    @Max(value = 65)
    @Greater(value = 24)
    @Less(value = 80)
    private Integer age;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getNickname() {
      return nickname;
    }

    public void setNickname(String nickname) {
      this.nickname = nickname;
    }

    public Integer getAge() {
      return age;
    }

    public void setAge(Integer age) {
      this.age = age;
    }

  }

  @Test(expected = InvalidParameterException.class)
  public void testUser() {
    ValidatorTest.User user = new ValidatorTest.User();
    user.setUsername("hualuomoli");

    // 级联验证
    ValidatorTest.User.Info info = new ValidatorTest.User.Info();
    info.setIdNumber("123456789012345678");
    info.setBirthday("0101");
    user.setInfo(info);

    // list
    List<ValidatorTest.User.Address> addresses = new ArrayList<ValidatorTest.User.Address>();
    ValidatorTest.User.Address address1 = new ValidatorTest.User.Address();
    address1.setProvince("山东省");
    //		address1.setCity("青岛市");
    address1.setCounty("市北区");

    ValidatorTest.User.Address address2 = new ValidatorTest.User.Address();
    address2.setProvince("山东省");
    address2.setCity("青岛市");
    addresses.add(address1);
    addresses.add(address2);
    user.setAddresses(addresses);

    // 不级联验证
    ValidatorTest.User.Relation relation = new ValidatorTest.User.Relation();
    user.setRelation(relation);

    try {
      Validator.valid(user);
    } catch (Exception e) {
      logger.info("{}", e.getMessage());
      throw e;
    }

  }

  // 用戶。复杂数据
  public static class User {

    private String username;
    @Valid // 需要加这个注解,复杂数据类型才会验证
    private Info info;
    @Valid
    private List<Address> addresses;
    private Set<String> hobbys;
    private Relation relation;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public Info getInfo() {
      return info;
    }

    public void setInfo(Info info) {
      this.info = info;
    }

    public List<Address> getAddresses() {
      return addresses;
    }

    public void setAddresses(List<Address> addresses) {
      this.addresses = addresses;
    }

    public Set<String> getHobbys() {
      return hobbys;
    }

    public void setHobbys(Set<String> hobbys) {
      this.hobbys = hobbys;
    }

    public Relation getRelation() {
      return relation;
    }

    public void setRelation(Relation relation) {
      this.relation = relation;
    }

    // 级联验证
    public static class Info {

      @NotBlank
      @Pattern(regexp = "\\d{15}|\\d{18}")
      private String idNumber;
      @Pattern(regexp = "\\d{1,2}/\\d{1,2}")
      private String birthday;

      public String getIdNumber() {
        return idNumber;
      }

      public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
      }

      public String getBirthday() {
        return birthday;
      }

      public void setBirthday(String birthday) {
        this.birthday = birthday;
      }
    }

    public static class Address {

      @NotBlank
      private String province;
      @NotBlank
      private String city;
      @NotBlank
      private String county;

      public String getProvince() {
        return province;
      }

      public void setProvince(String province) {
        this.province = province;
      }

      public String getCity() {
        return city;
      }

      public void setCity(String city) {
        this.city = city;
      }

      public String getCounty() {
        return county;
      }

      public void setCounty(String county) {
        this.county = county;
      }
    }

    // 不级联验证
    public static class Relation {
      @NotBlank
      private String username;
      private Integer age;

      public String getUsername() {
        return username;
      }

      public void setUsername(String username) {
        this.username = username;
      }

      public Integer getAge() {
        return age;
      }

      public void setAge(Integer age) {
        this.age = age;
      }

    }

  }

}
