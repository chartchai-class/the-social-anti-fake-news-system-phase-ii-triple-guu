package com.example.component.project2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.component.project2.security.auth.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthFlowTest {
  @Autowired private WebApplicationContext context;

  @Test void loginShouldWork() throws Exception {
    MockMvc mvc = MockMvcBuilders.webAppContextSetup(context).build();
    AuthenticationRequest req = new AuthenticationRequest();
    req.setUsername("admin");
    req.setPassword("admin");
    String body = new ObjectMapper().writeValueAsString(req);

    mvc.perform(post("/api/v1/auth/authenticate")
      .contentType(MediaType.APPLICATION_JSON)
      .content(body))
      .andExpect(status().isOk());
  }
}
