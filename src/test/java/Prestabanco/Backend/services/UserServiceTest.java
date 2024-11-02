package Prestabanco.Backend.services;




import Prestabanco.Backend.entities.UserEntity;


import Prestabanco.Backend.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity user;
    private UserEntity user2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserEntity();
        user.setId(1L);
        user.setRut("12345678-9");
        user.setName("Freddy");
        user.setLastname("Rodriguez");
        user.setPassword("password123");
    }

    //Testing saveUser
    @Test
    void whenSaveUser_thenReturnSavedEntity() {
        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.saveUser(user);

        assertNotNull(result);
        assertEquals(user, result);
    }
    @Test
    void whenSaveUserWithNullEntity_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(null));
    }
    @Test
    void whenSaveUserWithNullRut_thenThrowException() {
        user.setRut(null);
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user));
    }

    @Test
    void whenSaveUserWithExistingID_thenThrowException() {
        MockitoAnnotations.openMocks(this);
        user2 = new UserEntity();
        user2.setRut("12345678-9");
        user2.setName("Freddy");
        user2.setLastname("Rodriguez");
        user2.setPassword("password123");
        user2.setId(2L);

        when(userRepository.existsById(2L)).thenReturn(true);
        userService.saveUser(user);
        assertThrows(IllegalArgumentException.class, () -> userService.saveUser(user2));
    }

    @Test
    void whenSaveUser_thenCorrectRepositoryMethodCalled() {
        userService.saveUser(user);
        verify(userRepository).save(user);
    }


    //Testing updateUser
    @Test
    void whenUpdateUserWithValidUser_thenReturnUpdatedUser() {
        when(userRepository.save(user)).thenReturn(user);

        UserEntity updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals(user.getId(), updatedUser.getId());
        assertEquals(user.getRut(), updatedUser.getRut());
    }

    @Test
    void whenUpdateUserWithNullUser_thenThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(null));
    }

    @Test
    void whenUpdateUserWithNonExistentUser_thenThrowIllegalArgumentException() {
        user.setId(null);
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(user));
    }

    @Test
    void whenUpdateUserWithExistingUserId_thenUpdateSuccessfully() {
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        UserEntity updatedUser = userService.updateUser(user);

        assertNotNull(updatedUser);
        assertEquals("Freddy", updatedUser.getName());
    }

    @Test
    void whenUpdateUserWithDifferentFields_thenReturnUpdatedUserWithNewFields() {
        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(1L);
        updatedUser.setRut("12345678-9");
        updatedUser.setName("Jane");
        updatedUser.setLastname("Doe");
        updatedUser.setPassword("newpassword123");

        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        UserEntity result = userService.updateUser(updatedUser);

        assertEquals("Jane", result.getName());
        assertEquals("newpassword123", result.getPassword());
    }

    // Testing  deleteUser

    @Test
    void whenDeleteUserWithExistingId_thenReturnTrue() throws Exception {
        doNothing().when(userRepository).deleteById(1L);

        boolean isDeleted = userService.deleteUser(1L);

        assertTrue(isDeleted);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteUserWithNonExistentId_thenThrowException() {
        doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(2L);

        Exception exception = assertThrows(Exception.class, () -> userService.deleteUser(2L));

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void whenDeleteUserWithNullId_thenThrowIllegalArgumentException() {
        doThrow(new RuntimeException("User not found")).when(userRepository).deleteById(null);
        Exception exception = assertThrows(Exception.class, () -> userService.deleteUser(null));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void whenDeleteUser_thenVerifyRepositoryInteraction() throws Exception {
        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteUserWithExceptionInRepository_thenThrowException() {
        doThrow(new RuntimeException("Database error")).when(userRepository).deleteById(1L);

        Exception exception = assertThrows(Exception.class, () -> userService.deleteUser(1L));

        assertEquals("Database error", exception.getMessage());
    }


    //Testing getUserById
    @Test
    void whenGetUserByIdWithNullId_thenThrowIllegalArgumentException(){
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(null));
    }

    @Test
    void whenGetUserById_thenGetSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserEntity result = userService.getUserById(1L);
        assertNotNull(user);
        assertEquals(1L, userService.getUserById(1L).getId());
    }

    //Testing getUsers
    @Test
    void whenGetUsers_withExistingUsers_thenReturnUserList() {
        // given
        UserEntity user1 = new UserEntity();
        user1.setId(1L);
        user1.setRut("12345678-9");

        UserEntity user2 = new UserEntity();
        user2.setId(2L);
        user2.setRut("98765432-1");

        List<UserEntity> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userRepository.findAll()).thenReturn(userList);

        // when
        ArrayList<UserEntity> result = userService.getUsers();

        // then
        assertNotNull(result);  // Verifica que la lista no sea nula
        assertEquals(2, result.size());  // Verifica que el tamaño de la lista sea correcto
        assertTrue(result.contains(user1) && result.contains(user2));  // Verifica que los usuarios esperados están en la lista
    }

    @Test
    void whenGetUsers_withNoUsers_thenReturnEmptyList() {
        // given
        when(userRepository.findAll()).thenReturn(new ArrayList<>());  // Simula una lista vacía

        // when
        ArrayList<UserEntity> result = userService.getUsers();

        // then
        assertNotNull(result);  // Verifica que la lista no sea nula
        assertTrue(result.isEmpty());  // Verifica que la lista esté vacía
    }

    @Test
    void whenGetUsers_withNullResultFromRepository_thenReturnEmptyList() {
        // given
        when(userRepository.findAll()).thenReturn(null);  // Simula que el repositorio devuelve nulo

        // when
        ArrayList<UserEntity> result = userService.getUsers();

        // then
        assertNotNull(result);  // Verifica que la lista devuelta no sea nula
        assertTrue(result.isEmpty());  // Verifica que la lista esté vacía en caso de nulo
    }

}
