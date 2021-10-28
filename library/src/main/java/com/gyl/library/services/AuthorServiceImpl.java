package com.gyl.library.services;

import com.gyl.library.entities.AuthorEntity;
import com.gyl.library.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> getAllAuthorsOrderByName() { return authorRepository.findByOrderByNameAsc(); }

    @Override
    @Transactional
    public void createAuthor(String name, Boolean activate) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setName(name.toUpperCase());
        authorEntity.setActivate(activate);
        authorRepository.save(authorEntity);
    }

    @Override
    @Transactional
    public void updateAuthor(Integer id_author, String name, Boolean activate) {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId_author(id_author);
        authorEntity.setName(name.toUpperCase());
        authorEntity.setActivate(activate);
        authorRepository.save(authorEntity);
    }

    @Override
    @Transactional
    public void deleteAuthor(Integer id_author) {
        AuthorEntity authorEntity = authorRepository.findById(id_author).get();
        authorRepository.delete(authorEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorEntity findAuthorById(Integer id_author) { return  authorRepository.findById(id_author).get(); }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> getAllAuthorsActivated() { return authorRepository.findByActivateTrueOrderByNameAsc(); }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> getAllAuthorsNoActivated() { return authorRepository.findByActivateFalseOrderByNameAsc(); }

    @Override
    @Transactional(readOnly = true)
    public AuthorEntity findAuthorByName(String name) {
        if(authorRepository.findByNameIgnoreCase(name) != null) {
            return authorRepository.findByNameIgnoreCase(name);
        }
        return null;
    }

    public boolean authorExist(String name) {
        if (this.findAuthorByName(name) != null) {
            return true;
        }
        return false;
    }

}
