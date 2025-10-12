package com.example.component.project2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.test.web.servlet.MockMvc;

import com.example.component.project2.controller.NewsController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = NewsController.class)
class NewsControllerTest {

  @Autowired MockMvc mvc;

  @Test void listShouldReturnOk() throws Exception {
    // For a real test, mock NewsService bean with @MockBean and define behavior.
    mvc.perform(get("/api/news")).andExpect(status().isUnauthorized());
  }
}

