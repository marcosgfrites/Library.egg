package com.gyl.library.repositories;

import com.gyl.library.model.entities.EditorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EditorialRepository extends JpaRepository<EditorialEntity, Integer> {

    List<EditorialEntity> findByOrderByNameAsc(); //retorna la lista de editoriales ordenados segun nombre

    List<EditorialEntity> findByActivateTrueOrderByNameAsc(); //retorna la lista de editoriales con active=true ordenados segun nombre

    List<EditorialEntity> findByActivateFalseOrderByNameAsc(); //retorna la lista de editoriales con active=false ordenados segun nombre

    EditorialEntity findByNameIgnoreCase(String name);

}
