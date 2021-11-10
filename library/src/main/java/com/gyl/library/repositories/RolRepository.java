package com.gyl.library.repositories;

import com.gyl.library.entities.AuthorEntity;
import com.gyl.library.entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolRepository extends JpaRepository<RolEntity, Integer> {

    List<RolEntity> findByOrderByNamerolAsc();

    List<RolEntity> findByActivateTrueOrderByNamerolAsc();

    List<RolEntity> findByActivateFalseOrderByNamerolAsc();

    RolEntity findByNamerolIgnoreCase(String namerol);

}
