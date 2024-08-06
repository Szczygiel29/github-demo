package com.example.demo.controller;

import com.example.demo.service.GithubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class GithubControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GithubService githubService;

    @Test
    void testListRepositoriesWithErrorMessage() throws Exception {
        String username = "Szczygiel29_unknown-user";

        mockMvc.perform(get("/api/github/user/{username}/repositories", username)
                        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(404));
    }
}