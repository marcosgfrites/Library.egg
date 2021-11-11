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
    public List<EditorialEntity> getAllEditorialsOrderByName() throws Exception {
        try {
            List<EditorialEntity> list = editorialRepository.findByOrderByNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Editoriales, ordenadas alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void createEditorial(String name) throws Exception {
        try {
            EditorialEntity editorialEntity = new EditorialEntity();
            editorialEntity.setName(name.toUpperCase());
            editorialEntity.setActivate(true);
            editorialRepository.save(editorialEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la creación de Editorial en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateEditorial(Integer id_editorial, String name, Boolean activate) throws Exception {
        try {
            EditorialEntity editorialEntity = new EditorialEntity();
            editorialEntity.setId_editorial(id_editorial);
            editorialEntity.setName(name.toUpperCase());
            editorialEntity.setActivate(activate);
            editorialRepository.save(editorialEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la actualización de Editorial en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());

        }
    }

    @Override
    @Transactional
    public void deleteEditorial(Integer id_editorial) throws Exception {
        try {
            EditorialEntity editorialEntity = editorialRepository.findById(id_editorial).get();
            editorialRepository.delete(editorialEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la eliminación de Editorial en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EditorialEntity findEditorialById(Integer id_editorial) throws Exception {
        try {
            EditorialEntity editorial = editorialRepository.findById(id_editorial).get();
            if (editorial == null) {
                throw new Exception("No se ha encontrado una Editorial con el número de ID indicado");
            } else {
                return editorial;
            }
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Editorial, por ID, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EditorialEntity> getAllEditorialsActivated() throws Exception {
        try {
            List<EditorialEntity> list = editorialRepository.findByActivateTrueOrderByNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Editoriales habilitadas, ordenadas alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<EditorialEntity> getAllEditorialsNoActivated() throws Exception {
        try {
            List<EditorialEntity> list = editorialRepository.findByActivateFalseOrderByNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Editoriales deshabilitadas, ordenadas alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EditorialEntity findEditorialByName(String name) throws Exception {
        try {
            EditorialEntity editorial = editorialRepository.findByNameIgnoreCase(name);
            return editorial;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Editorial, por nombre, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public boolean editorialExist(String name) throws Exception {
        try {
            if (this.findEditorialByName(name.toUpperCase()) != null) {
                return true;
            }
            return false;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido verificar la existencia de Editorial en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public void validateFormAndCreate(String name) throws Exception {
        try {
            if (this.editorialExist(name)) {
                throw new Exception("Ya existe una Editorial registrada con el mismo nombre.");
            }
            this.createEditorial(name);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    public void validateFormAndUpdate(Integer id_editorial, String name, Boolean active) throws Exception {
        try {
            if (this.editorialExist(name)) {
                throw new Exception("Ya existe una Editorial registrada con el mismo nombre.");
            }
            this.updateEditorial(id_editorial, name, active);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

}
