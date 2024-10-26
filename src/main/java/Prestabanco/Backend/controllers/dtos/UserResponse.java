package Prestabanco.Backend.controllers.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class UserResponse {
    private Long id;
    private String rut;
}
