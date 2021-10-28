package com.gyl.library.repositories;

import com.gyl.library.model.entities.AuthorEntity;
import com.gyl.library.model.entities.BookEntity;
import com.gyl.library.model.entities.EditorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {

    List<BookEntity> findByOrderByTitleAsc(); ////retorna la lista de libros ordenados segun titulo

    List<BookEntity> findByActivateTrueOrderByTitleAsc(); ////retorna la lista de libros con active=true ordenados segun titulo

    List<BookEntity> findByActivateFalseOrderByTitleAsc(); ////retorna la lista de libros con active=false ordenados segun titulo

    BookEntity findByIsbn(Long isbn);

    List<BookEntity> findByAuthorOrderByTitleAsc(AuthorEntity authorEntity); ////retorna la lista de libros de un autor especifico ordenados segun titulo

    List<BookEntity> findByEditorialOrderByTitleAsc(EditorialEntity editorialEntity); ////retorna la lista de libros de una editorial especifica ordenados segun titulo

}
