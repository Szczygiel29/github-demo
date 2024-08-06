package com.example.demo.controller;

import com.example.demo.model.ErrorResponse;
import com.example.demo.model.Repository;
import com.example.demo.service.GithubService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GithubController {

    GithubService githubService;

    @GetMapping("/user/{username}/repositories")
    public ResponseEntity<?> listRepositories(@PathVariable String username) {
        try {
            List<Repository> repositories = githubService.getRepositories(username);
            return new ResponseEntity<>(repositories, HttpStatus.OK);
        } catch (Exception e) {
            log.info(String.format("[%s]: Error: %s", GithubController.class, e.getMessage()));
            return new ResponseEntity<>(new ErrorResponse(404, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
