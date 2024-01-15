package com.LibManagementSystem.LibManagementSystem.controllers;

import com.LibManagementSystem.LibManagementSystem.DTO.requests.AuthRelated.LoginRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;




@SpringBootTest
@ExtendWith(SpringExtension.class)
//context cleaned after each test
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthControllerIntegrationTests {
    @Value("${application.env.creds.admin-email}")
    private String ADMIN_EMAIL;

    @Value("${application.env.creds.admin-pass}")
    private String ADMIN_PASSWORD;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper; //Convert vanilla obj to json

    @Autowired
    public AuthControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testThatAdminLoginSuccessfullyReturnsStatusOk() throws Exception {

        LoginRequestDTO loginRequestData = LoginRequestDTO.builder().
                email(ADMIN_EMAIL).password(ADMIN_PASSWORD).build();

        String loginRequestDataToJSON = objectMapper.writeValueAsString(loginRequestData);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequestDataToJSON)

        ).andExpect(MockMvcResultMatchers.status().isOk());


    }

}
