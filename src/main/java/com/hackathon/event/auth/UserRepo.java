package com.hackathon.event.auth;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepo extends JpaRepository<appUser,Long>
{
    Optional<appUser> findOneByEmailAndPassword(String email, String password);

    appUser findByEmail(String email);
}
