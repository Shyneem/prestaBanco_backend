package Prestabanco.Backend.controllers.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Data

// DTO para la solicitud de login
public class LoginRequest {
    @Column(unique = true, nullable = false)
    private String rut;
    private String password;


}

