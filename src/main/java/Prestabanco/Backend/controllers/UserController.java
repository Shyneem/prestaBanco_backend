package Prestabanco.Backend.controllers;


import Prestabanco.Backend.entities.UserEntity;
import Prestabanco.Backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserEntity>> listUsers() {
        List<UserEntity> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable Long id) {
        UserEntity user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/")
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user) {
        UserEntity userNew = userService.saveUser(user);
        return ResponseEntity.ok(userNew);
    }



}
