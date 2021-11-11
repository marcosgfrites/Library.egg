package com.gyl.library.services;

import com.gyl.library.entities.EditorialEntity;

import java.util.List;

public interface EditorialService {

    public List<EditorialEntity> getAllEditorialsOrderByName() throws Exception;
    public void createEditorial(String name) throws Exception;
    public void updateEditorial(Integer id_editorial, String name, Boolean activate) throws Exception;
    public void deleteEditorial(Integer id_editorial) throws Exception;
    public EditorialEntity findEditorialById(Integer id_editorial) throws Exception;
    public List<EditorialEntity> getAllEditorialsActivated() throws Exception;
    public List<EditorialEntity> getAllEditorialsNoActivated() throws Exception;
    public EditorialEntity findEditorialByName(String name) throws Exception;

}
