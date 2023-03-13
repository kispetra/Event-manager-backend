package com.hackathon.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubRepoNumberResponseDto {
    @JsonProperty("public_repos")
    private Integer numberOfRepos;
}
