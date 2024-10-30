package Prestabanco.Backend.services;

import Prestabanco.Backend.entities.UserEntity;
import Prestabanco.Backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String rut, String password) {
        Optional<UserEntity> userOpt = Optional.ofNullable(userRepository.findByRut(rut));

        // Verifica si el usuario existe y si la contraseña coincide
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            return user.getPassword().equals(password);
        }
        return false;
    }
}