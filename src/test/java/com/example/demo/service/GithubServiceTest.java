package com.example.demo.service;

import com.example.demo.model.Branch;
import com.example.demo.model.GithubUser;
import com.example.demo.model.Owner;
import com.example.demo.model.Repository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    private GithubService githubService;


    @Test
    public void testGetRepositoriesSuccess() {
        String username = "test_123";
        GithubUser[] githubUsers = new GithubUser[]{new GithubUser()};
        githubUsers[0].setName("repo1");
        githubUsers[0].setOwner(new Owner());
        githubUsers[0].setFork(false);
        githubUsers[0].setBranches_url("test_url");

        when(restTemplate.getForEntity("https://api.github.com/users/{username}/repos", GithubUser[].class, username))
                .thenReturn(new ResponseEntity<>(githubUsers, HttpStatus.OK));
        when(restTemplate.getForEntity("test_url", Branch[].class))
                .thenReturn(new ResponseEntity<>(new Branch[0], HttpStatus.OK));

        List<Repository> repositories = githubService.getRepositories(username);

        assertNotNull(repositories);
        assertEquals(1, repositories.size());
        assertEquals("repo1", repositories.get(0).getName());
        assertEquals(0, repositories.get(0).getBranches().size());
    }

    @Test
    public void testGetRepositoriesUserNotFound() {
        String username = "test_123";

        when(restTemplate.getForEntity("https://api.github.com/users/{username}/repos", GithubUser[].class, username))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        Exception exception = assertThrows(RuntimeException.class, () -> githubService.getRepositories(username));

        assertEquals("User not found", exception.getMessage());
    }
}