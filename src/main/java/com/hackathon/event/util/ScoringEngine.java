package com.hackathon.event.util;

import com.hackathon.event.dto.GithubEventsResponseDto;
import com.hackathon.event.dto.GithubLanguageResponseDto;
import com.hackathon.event.dto.GithubRepoNumberResponseDto;
import com.hackathon.event.dto.RegistrationRequestDto;
import com.hackathon.event.model.enumeration.SkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ScoringEngine {

    private Integer yearsOfEducationMultiplier = 2;
    private Integer yearsOfExperienceMultiplier = 5;
    private Integer primarySkillsMultiplier = 20;
    private Integer secondarySkillstMultiplier = 10;
    private Integer otherSkillsMultiplier = 5;
    private Integer repositoryMultiplier = 10;

    public Integer CalculateScore(RegistrationRequestDto registrationRequest) {
        Integer yearsOfEducationPoints = registrationRequest.getPersonal().getEducation().getYear() * yearsOfEducationMultiplier;
        Integer yearsOfExperiencePoints = registrationRequest.getExperience().getYears() * yearsOfExperienceMultiplier;
        Integer primarySkillsPoints = calculatePrimarySkillsPoints(registrationRequest.getExperience().getSkills());
        Integer secondarySkillsPoints = calculateSecondarySkillsPoints(registrationRequest.getExperience().getSkills());
        Integer otherSkillsPoints = calculateOtherSkillsPoints(registrationRequest.getExperience().getSkills());
        Integer repositoryPoints = registrationRequest.getExperience().getRepositoryUrl().isBlank() ? 0 : repositoryMultiplier;
        Integer languageAndSizeScore = 0, activityScore = 0 ;
        if(registrationRequest.getExperience().getRepositoryUrl().substring(8,14).equals("github")) {
             languageAndSizeScore = calculateRepoLanguageAndSizeScore(registrationRequest.getExperience().getRepositoryUrl());
             activityScore = calculateGithubActivityScore(registrationRequest.getExperience().getRepositoryUrl());
        }
        return yearsOfEducationPoints + yearsOfExperiencePoints + primarySkillsPoints +
                secondarySkillsPoints + otherSkillsPoints + repositoryPoints +
                languageAndSizeScore + activityScore;
    }

    private Integer calculatePrimarySkillsPoints(List<String> skills) {
        Integer points = 0;
        List<SkillType> primarySkills = new ArrayList<>();
        primarySkills.add(SkillType.Java);
        primarySkills.add(SkillType.Spring);
        primarySkills.add(SkillType.SpringBoot);

        for (String skill : skills) {
            for (SkillType primarySkill : primarySkills) {
                if (primarySkill.name().equals(skill)) {
                    points += primarySkillsMultiplier;
                }
            }
        }
        return points;
    }

    private Integer calculateSecondarySkillsPoints(List<String> skills) {
        Integer points = 0;
        List<SkillType> secondarySkills = new ArrayList<>();
        secondarySkills.add(SkillType.Hibernate);
        secondarySkills.add(SkillType.JPA);
        secondarySkills.add(SkillType.Scala);

        for (String skill : skills) {
            for (SkillType secondarySkill : secondarySkills) {
                if (secondarySkill.name().equals(skill)) {
                    points += secondarySkillstMultiplier;
                }
            }
        }
        return points;
    }

    private Integer calculateOtherSkillsPoints(List<String> skills) {
        Integer points = 0;

        for (String skill : skills) {
            if (!skillTypeExists(skill)) {
                points += otherSkillsMultiplier;
            }
        }

        return points;
    }

    private boolean skillTypeExists(String skillType) {
        for (SkillType skill : SkillType.values()) {
            if (skill.name().equals(skillType)) {
                return true;
            }
        }
        return false;
    }



    private Integer calculateRepoLanguageAndSizeScore(String repositoryUrl) {
        WebClient webClient = WebClient.create();
        String username = repositoryUrl.substring(19);
        String apiUrl = "https://api.github.com/users/" + username + "/repos";
        Integer languageScore = 0;

        ResponseEntity<List<GithubLanguageResponseDto>> response =
                webClient.get()
                        .uri(apiUrl)
                        .retrieve()
                        .toEntity(new ParameterizedTypeReference<List<GithubLanguageResponseDto>>() {})
                        .block();

        List<GithubLanguageResponseDto> body = response.getBody();

        for (GithubLanguageResponseDto languageDto : body){
            if(languageDto.getLanguage().equals("Java")){
                languageScore++;
            }
        }
        return languageScore + body.size();
    }

    private Integer calculateGithubActivityScore(String repositoryUrl){
        WebClient webClient = WebClient.create();
        String username = repositoryUrl.substring(19);
        String apiUrl = "https://api.github.com/users/" + username + "/events";


        Integer activityScore = webClient.get()
                .uri(apiUrl)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<List<GithubEventsResponseDto>>() {})
                .block().getBody().size();

        return activityScore;
    }

}

