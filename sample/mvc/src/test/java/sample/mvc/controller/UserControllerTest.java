package sample.mvc.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getMethod() throws Exception {
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
    public void getMethodCustom() throws Exception {
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
    public void formUrlEncodedMethod() throws Exception {
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
    public void formUrlEncodedMethodCustom() throws Exception {
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