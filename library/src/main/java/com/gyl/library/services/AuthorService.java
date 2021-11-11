package com.gyl.library.services;

import com.gyl.library.entities.AuthorEntity;

import java.util.List;

public interface AuthorService {

    public List<AuthorEntity> getAllAuthorsOrderByName() throws Exception;
    public void createAuthor(String name) throws Exception;
    public void updateAuthor(Integer id_author, String name, Boolean activate) throws Exception;
    public void deleteAuthor(Integer id_author) throws Exception;
    public AuthorEntity findAuthorById(Integer id_author) throws Exception;
    public List<AuthorEntity> getAllAuthorsActivated() throws Exception;
    public List<AuthorEntity> getAllAuthorsNoActivated() throws Exception;
    public AuthorEntity findAuthorByName(String name) throws Exception;

}
