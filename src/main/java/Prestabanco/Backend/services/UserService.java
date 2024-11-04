package Prestabanco.Backend.services;


import Prestabanco.Backend.entities.UserEntity;
import Prestabanco.Backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    UserEntity user;




    public ArrayList<UserEntity> getUsers() {
        ArrayList<UserEntity> users = (ArrayList<UserEntity>) userRepository.findAll();
        if (users == null) {
            users = new ArrayList<>();
            return users;
        }
        return users;
    }

    public UserEntity saveUser(UserEntity user){
        if (user.getRut() == null || user.getRut().equals("")) {
            throw new IllegalArgumentException("Ruto nulo");
        }else{
            return userRepository.save(user);
        }
    }

    public UserEntity getUserById(Long id){
        if (id == null) {
            throw new IllegalArgumentException("Error de campos nulos");
        }
        return userRepository.findById(id).get();
    }


    public UserEntity updateUser(UserEntity user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("Error de campos nulos");
        }
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) throws Exception {
        try{
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }
}
