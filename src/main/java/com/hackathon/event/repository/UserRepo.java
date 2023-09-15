package com.hackathon.event.repository;

import com.hackathon.event.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<AppUser,Long>
{
    Optional<AppUser> findByLogin(String login);

}
