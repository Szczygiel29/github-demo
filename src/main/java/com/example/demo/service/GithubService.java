package com.example.demo.service;

import com.example.demo.model.Branch;
import com.example.demo.model.GithubUser;
import com.example.demo.model.Repository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class GithubService {

    private final static String GITHUB_USER_REPO_URL = "https://api.github.com/users/{username}/repos";

    RestTemplate restTemplate;

    public List<Repository> getRepositories(String username) {
        ResponseEntity<GithubUser[]> response = restTemplate.getForEntity(
                GITHUB_USER_REPO_URL,
                GithubUser[].class,
                username
        );

        GithubUser[] repositories = response.getBody();

        if (repositories == null || repositories.length == 0) {
            log.info(String.format("[%s]: user not found", GithubService.class));
            throw new RuntimeException("User not found");
        }

        return Arrays.stream(repositories)
                .filter(repo -> !repo.isFork())
                .map(repo -> new Repository(repo.getName(), repo.getOwner().getLogin(), getBranches(repo.getBranches_url())))
                .collect(Collectors.toList());
    }

    private List<Branch> getBranches(String branchesUrl) {
        if (branchesUrl == null) {
            return List.of();
        }

        ResponseEntity<Branch[]> response = restTemplate.getForEntity(
                branchesUrl.replace("{/branch}", ""),
                Branch[].class
        );

        Branch[] branches = response.getBody();

        if (branches == null) {
            return List.of();
        }

        return Arrays.asList(branches);
    }
}
