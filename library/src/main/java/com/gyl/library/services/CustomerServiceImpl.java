package com.gyl.library.services;

import com.gyl.library.entities.CustomerEntity;
import com.gyl.library.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> getAllCustomersOrderByName() throws Exception {
        try {
            List<CustomerEntity> list = customerRepository.findByOrderByLastNameAscFirstNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Clientes, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void createCustomer(Long dni, String firstName, String lastName, String phone) throws Exception {
        try {
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setDni(dni);
            customerEntity.setFirstName(firstName.toUpperCase());
            customerEntity.setLastName(lastName.toUpperCase());
            customerEntity.setPhone(phone);
            customerEntity.setActivate(true);
            customerRepository.save(customerEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la creación de Cliente en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void updateCustomer(Integer id_customer, Long dni, String firstName, String lastName, String phone, Boolean activate) throws Exception {
        try {
            CustomerEntity customerEntity = new CustomerEntity();
            customerEntity.setId_customer(id_customer);
            customerEntity.setDni(dni);
            customerEntity.setFirstName(firstName);
            customerEntity.setLastName(lastName);
            customerEntity.setPhone(phone);
            customerEntity.setActivate(activate);
            customerRepository.save(customerEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la actualización de Cliente en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteCustomer(Integer id_customer) throws Exception {
        try {
            CustomerEntity customerEntity = customerRepository.findById(id_customer).get();
            customerRepository.delete(customerEntity);
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la eliminación de Cliente en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerEntity findCustomerById(Integer id_customer) throws Exception {
        try {
            CustomerEntity customer = customerRepository.findById(id_customer).get();
            if (customer == null) {
                throw new Exception("No se ha encontrado un Cliente con el número de ID indicado.");
            } else {
                return customer;
            }
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Cliente, por ID, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> getAllCustomersActivated() throws Exception {
        try {
            List<CustomerEntity> list = customerRepository.findByActivateTrueOrderByLastNameAscFirstNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Clientes habilitados, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerEntity> getAllCustomersNoActivated() throws Exception {
        try {
            List<CustomerEntity> list = customerRepository.findByActivateFalseOrderByLastNameAscFirstNameAsc();
            return list;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la obtención del listado de Clientes deshabilitados, ordenados alfabéticamente, de la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerEntity findCustomerByLastNameAndFistName(String firstName, String lastName) throws Exception {
        try {
            CustomerEntity customer = customerRepository.findByLastNameAndFirstNameIgnoreCase(firstName, lastName);
            return customer;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Cliente, por nombre y apellido, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerEntity findCustomerByDni(Long dni) throws Exception {
        try {
            CustomerEntity customer = customerRepository.findByDni(dni);
            return customer;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido procesar la búsqueda de Cliente, por DNI, en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public boolean customerExist(Long dni) throws Exception {
        try {
            if (this.findCustomerByDni(dni) != null) {
                return true;
            }
            return false;
        } catch (Exception exception) {
            throw new Exception("Algo ha sucedido y no se ha podido verificar la existencia de Cliente en la base de datos." +
                    "\n - Descripción del error: " + exception.getMessage());
        }
    }

    public void validateFormAndCreate(Long dni, String firstName, String lastName, String phone) throws Exception {
        try {
            if (this.customerExist(dni)) {
                throw new Exception("Ya existe un Cliente registrado con el mismo DNI.");
            }
            this.createCustomer(dni, firstName, lastName, phone);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    public void validateFormAndUpdate(Integer id_customer, Long dni, String firstName, String lastName, String phone, Boolean activate) throws Exception {
        try {
            this.updateCustomer(id_customer, dni, firstName, lastName, phone, activate);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

}
