package Prestabanco.Backend.controllers;


import Prestabanco.Backend.dtos.LoginRequest;
import Prestabanco.Backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {

        boolean isAuthtenticated = authService.authenticate(loginRequest.getRut(),loginRequest.getPassword());
        if(isAuthtenticated) {
            return ResponseEntity.ok("Login existoso");
        }
        else{
            return ResponseEntity.ok("Fallo en las credenciales o usuario inexistente");
        }
    }
}
