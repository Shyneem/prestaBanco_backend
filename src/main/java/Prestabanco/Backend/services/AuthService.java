package Prestabanco.Backend.services;

import Prestabanco.Backend.entities.UserEntity;
import Prestabanco.Backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String rut, String password) {
        UserEntity user = userRepository.findByRut(rut);

        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }
}