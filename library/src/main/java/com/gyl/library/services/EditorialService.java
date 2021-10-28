package com.gyl.library.services;

import com.gyl.library.model.entities.EditorialEntity;

import java.util.List;

public interface EditorialService {

    public List<EditorialEntity> getAllEditorialsOrderByName();
    public void createEditorial(String name, Boolean activate);
    public void updateEditorial(Integer id_editorial, String name, Boolean activate);
    public void deleteEditorial(Integer id_editorial);
    public EditorialEntity findEditorialById(Integer id_editorial);
    public List<EditorialEntity> getAllEditorialsActivated();
    public List<EditorialEntity> getAllEditorialsNoActivated();
    public EditorialEntity findEditorialByName(String name);

}
