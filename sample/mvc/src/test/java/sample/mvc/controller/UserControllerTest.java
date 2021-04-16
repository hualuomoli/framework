package sample.mvc.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.hualuomoli.boot.mvc.handler.CustomHandler;
import com.github.hualuomoli.boot.mvc.method.CustomExtendedServletRequestDataBinder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Field;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Value(value = "${project.basePackage}")
    private String basePackage;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        // init custom handler
        CustomExtendedServletRequestDataBinder.setCustomHandler(new CustomHandler() {

            @Override
            public boolean customClass(Class<?> clazz) {
                return clazz.getName().startsWith(basePackage);
            }

            @Override
            public String parameterName(Field field) {
                return Optional.ofNullable(field.getAnnotation(JsonProperty.class)).map(JsonProperty::value).orElse(null);
            }
        });
    }

    @Test
    public void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add")
                .param("username", "hualuomoli")
                .param("nickname", "花落寞离")
                .param("gender", "MALE")
                .param("hobbies", "read")
                .param("hobbies", "write")
                .param("hobbies", "dance")
                .param("homeAddress.province", "山东省")
                .param("homeAddress.city", "青岛市")
                .param("homeAddress.county", "市北区")
                .param("homeAddress.detail", "合肥路666号")
                .param("addresses[0].province", "山东省")
                .param("addresses[0].city", "青岛市")
                .param("addresses[0].county", "市南区")
                .param("addresses[0].detail", "香港中路66号")
                .param("addresses[1].province", "山东省")
                .param("addresses[1].city", "青岛市")
                .param("addresses[1].county", "崂山区")
                .param("addresses[1].detail", "苗岭路88号")
                .param("addresses[8].province", "北京市") // 跨过中间的下标
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void customGet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add")
                .param("username", "hualuomoli")
                .param("nick_name", "花落寞离")
                .param("gender", "MALE")
                .param("hobby", "read")
                .param("hobby", "write")
                .param("hobby", "dance")
                .param("home_address.province", "山东省")
                .param("home_address.city", "青岛市")
                .param("home_address.county", "市北区")
                .param("home_address.info", "合肥路666号")
                .param("address[0].province", "山东省")
                .param("address[0].city", "青岛市")
                .param("address[0].county", "市南区")
                .param("address[0].info", "香港中路66号")
                .param("address[1].province", "山东省")
                .param("address[1].city", "青岛市")
                .param("address[1].county", "崂山区")
                .param("address[1].info", "苗岭路88号")
                .param("address[8].province", "北京市") // 跨过中间的下标
                .param("address[12].county", "市北区") // 两位下标
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void formUrlEncoded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "hualuomoli")
                .param("nickname", "花落寞离")
                .param("gender", "MALE")
                .param("hobbies", "read")
                .param("hobbies", "write")
                .param("hobbies", "dance")
                .param("homeAddress.province", "山东省")
                .param("homeAddress.city", "青岛市")
                .param("homeAddress.county", "市北区")
                .param("homeAddress.detail", "合肥路666号")
                .param("addresses[0].province", "山东省")
                .param("addresses[0].city", "青岛市")
                .param("addresses[0].county", "市南区")
                .param("addresses[0].detail", "香港中路66号")
                .param("addresses[1].province", "山东省")
                .param("addresses[1].city", "青岛市")
                .param("addresses[1].county", "崂山区")
                .param("addresses[1].detail", "苗岭路88号")
                .param("addresses[8].province", "北京市") // 跨过中间的下标
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void customFormUrlEncoded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "hualuomoli")
                .param("nick_name", "花落寞离")
                .param("gender", "MALE")
                .param("hobby", "read")
                .param("hobby", "write")
                .param("hobby", "dance")
                .param("home_address.province", "山东省")
                .param("home_address.city", "青岛市")
                .param("home_address.county", "市北区")
                .param("home_address.info", "合肥路666号")
                .param("address[0].province", "山东省")
                .param("address[0].city", "青岛市")
                .param("address[0].county", "市南区")
                .param("address[0].info", "香港中路66号")
                .param("address[1].province", "山东省")
                .param("address[1].city", "青岛市")
                .param("address[1].county", "崂山区")
                .param("address[1].info", "苗岭路88号")
                .param("address[8].province", "北京市") // 跨过中间的下标
                .param("address[12].county", "市北区") // 两位下标
        )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}