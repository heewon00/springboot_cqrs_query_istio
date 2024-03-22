package com.kt.edu.thirdproject.common;

import com.kt.edu.thirdproject.common.controller.JwtAuthenticationController;
import com.kt.edu.thirdproject.employee.query.service.EmployeeService;
import org.junit.jupiter.api.Test;
import com.kt.edu.thirdproject.common.util.JwtTokenUtil;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(JwtAuthenticationController.class)
//@AutoConfigureMockMvc
@CrossOrigin(origins ="*")
public class JwtAuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @MockBean
    private EmployeeService employeeService;

    //@Autowired
    //private ObjectMapper objectMapper;

    @Test
    public void testCreateAuthenticationToken() throws Exception {

        String testStr = "{\n" +
                "  \"username\": \"edu\", \n" +
                "  \"password\": \"edu1234\"\n" +
                "}";

        //Map<String, String> input = new HashMap<>();
        // body에 json 형식으로 회원의 데이터를 넣기 위해서 Map을 이용한다.
        //input.put("username", "edu");
        //input.put("password", "edu1234");

        mockMvc.perform(post("/api/login")
                 .content(testStr)
                //.content(objectMapper.writeValueAsString(input))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
