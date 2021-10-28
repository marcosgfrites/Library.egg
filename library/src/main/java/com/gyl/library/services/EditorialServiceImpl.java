package com.gyl.library.services;

import com.gyl.library.entities.EditorialEntity;
import com.gyl.library.repositories.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EditorialServiceImpl implements EditorialService{

    @Autowired
    EditorialRepository editorialRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EditorialEntity> getAllEditorialsOrderByName() { return editorialRepository.findByOrderByNameAsc(); }

    @Override
    @Transactional
    public void createEditorial(String name, Boolean activate) {
        EditorialEntity editorialEntity = new EditorialEntity();
        editorialEntity.setName(name.toUpperCase());
        editorialEntity.setActivate(activate);
        editorialRepository.save(editorialEntity);
    }

    @Override
    @Transactional
    public void updateEditorial(Integer id_editorial, String name, Boolean activate) {
        EditorialEntity editorialEntity = new EditorialEntity();
        editorialEntity.setId_editorial(id_editorial);
        editorialEntity.setName(name.toUpperCase());
        editorialEntity.setActivate(activate);
        editorialRepository.save(editorialEntity);
    }

    @Override
    @Transactional
    public void deleteEditorial(Integer id_editorial) {
        EditorialEntity editorialEntity = editorialRepository.findById(id_editorial).get();
        editorialRepository.delete(editorialEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public EditorialEntity findEditorialById(Integer id_editorial) { return editorialRepository.findById(id_editorial).get(); }

    @Override
    @Transactional(readOnly = true)
    public List<EditorialEntity> getAllEditorialsActivated() { return editorialRepository.findByActivateTrueOrderByNameAsc(); }

    @Override
    @Transactional(readOnly = true)
    public List<EditorialEntity> getAllEditorialsNoActivated() { return editorialRepository.findByActivateFalseOrderByNameAsc(); }

    @Override
    @Transactional(readOnly = true)
    public EditorialEntity findEditorialByName(String name) {
        if (editorialRepository.findByNameIgnoreCase(name) != null) {
            return editorialRepository.findByNameIgnoreCase(name);
        }
        return null;
    }

    public boolean editorialExist(String name) {
        if (this.findEditorialByName(name) != null) {
            return true;
        }
        return false;
    }

}
