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
    public List<AuthorEntity> getAllAuthorsOrderByName() throws Exception {
        try {
            List<AuthorEntity> list = authorRepository.findByOrderByNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Autores, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void createAuthor(String name) throws Exception {
        try{
            AuthorEntity authorEntity = new AuthorEntity();
            authorEntity.setName(name.toUpperCase());
            authorEntity.setActivate(true);
            authorRepository.save(authorEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la creación de Autor en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateAuthor(Integer id_author, String name, Boolean activate) throws Exception {
        try {
            AuthorEntity authorEntity = new AuthorEntity();
            authorEntity.setId_author(id_author);
            authorEntity.setName(name.toUpperCase());
            authorEntity.setActivate(activate);
            authorRepository.save(authorEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la actualización de Autor en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteAuthor(Integer id_author) throws Exception {
        try{
            AuthorEntity authorEntity = authorRepository.findById(id_author).get();
            authorRepository.delete(authorEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la eliminación de Autor en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorEntity findAuthorById(Integer id_author) throws Exception {
        try {
            AuthorEntity author = authorRepository.findById(id_author).get();
            if (author == null) {
                throw new Exception("No se ha encontrado un Autor con el número de ID indicado");
            } else {
                return author;
            }
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Autor, por ID, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> getAllAuthorsActivated() throws Exception {
        try {
            List<AuthorEntity> list = authorRepository.findByActivateTrueOrderByNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Autores habilitados, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorEntity> getAllAuthorsNoActivated() throws Exception {
        try {
            List<AuthorEntity> list = authorRepository.findByActivateFalseOrderByNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Autores deshabilitados, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorEntity findAuthorByName(String name) throws Exception {
        try {
            AuthorEntity author = authorRepository.findByNameIgnoreCase(name);
            return author;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Autor, por nombre, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public boolean authorExist(String name) throws Exception {
        try {
            if (this.findAuthorByName(name.toUpperCase()) != null) {
                return true;
            }
            return false;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido verificar la existencia de Autor en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public void validateFormAndCreate(String name) throws Exception {
        try {
            if (this.authorExist(name)) {
                throw new Exception("Ya existe un Autor registrado con el mismo nombre.");
            }
            this.createAuthor(name);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    public void validateFormAndUpdate(Integer id_author, String name, Boolean activate) throws Exception {
        try {
            if (this.authorExist(name)) {
                throw new Exception("Ya existe un Autor registrado con el mismo nombre.");
            }
            this.updateAuthor(id_author, name, activate);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

}
