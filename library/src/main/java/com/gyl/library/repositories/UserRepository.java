package com.gyl.library.repositories;

import com.gyl.library.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsernameIgnoreCase(String username);

}
