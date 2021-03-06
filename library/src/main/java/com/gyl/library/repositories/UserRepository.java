package com.gyl.library.repositories;

import com.gyl.library.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsernameIgnoreCase(String username);
    Optional<UserEntity> findByMailIgnoreCase(String mail);
    List<UserEntity> findByActivateTrueOrderByUsernameAsc();

}
