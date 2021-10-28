package com.gyl.library.services;

import com.gyl.library.model.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {

    public List<AuthorEntity> getAllAuthorsOrderByName();
    public void createAuthor(String name, Boolean activate);
    public void updateAuthor(Integer id_author, String name, Boolean activate);
    public void deleteAuthor(Integer id_author);
    public AuthorEntity findAuthorById(Integer id_author);
    public List<AuthorEntity> getAllAuthorsActivated();
    public List<AuthorEntity> getAllAuthorsNoActivated();
    public AuthorEntity findAuthorByName(String name);

}
