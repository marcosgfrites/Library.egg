package com.gyl.library.repositories;

import com.gyl.library.entities.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Integer> {

    List<AuthorEntity> findByOrderByNameAsc(); //retorna la lista de autores ordenados segun nombre

    List<AuthorEntity> findByActivateTrueOrderByNameAsc(); //retorna la lista de autores con active=true ordenados segun nombre

    List<AuthorEntity> findByActivateFalseOrderByNameAsc(); //retorna la lista de autores con active=false ordenados segun nombre

    AuthorEntity findByNameIgnoreCase(String name);

}
