package Prestabanco.Backend.services;

import Prestabanco.Backend.entities.UserEntity;
import Prestabanco.Backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    public AuthServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenAuthenticateWithCorrectCredentials_thenReturnTrue() {
        // given
        UserEntity user = new UserEntity();
        user.setRut("12345678-9");
        user.setPassword("password123");

        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        // when
        boolean isAuthenticated = authService.authenticate("12345678-9", "password123");

        // then
        assertTrue(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithIncorrectPassword_thenReturnFalse() {
        // given
        UserEntity user = new UserEntity();
        user.setRut("12345678-9");
        user.setPassword("password123");

        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        // when
        boolean isAuthenticated = authService.authenticate("12345678-9", "wrongPassword");

        // then
        assertFalse(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithNonExistentRut_thenReturnFalse() {
        // given
        when(userRepository.findByRut("nonexistent-rut")).thenReturn(null);

        // when
        boolean isAuthenticated = authService.authenticate("nonexistent-rut", "password123");

        // then
        assertFalse(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithEmptyRut_thenReturnFalse() {
        // when
        boolean isAuthenticated = authService.authenticate("", "password123");

        // then
        assertFalse(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithEmptyPassword_thenReturnFalse() {
        // given
        UserEntity user = new UserEntity();
        user.setRut("12345678-9");
        user.setPassword("password123");

        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        // when
        boolean isAuthenticated = authService.authenticate("12345678-9", "");

        // then
        assertFalse(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithNullRut_thenReturnFalse() {
        // when
        boolean isAuthenticated = authService.authenticate(null, "password123");

        // then
        assertFalse(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithNullPassword_thenReturnFalse() {
        // given
        UserEntity user = new UserEntity();
        user.setRut("12345678-9");
        user.setPassword("password123");

        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        // when
        boolean isAuthenticated = authService.authenticate("12345678-9", null);

        // then
        assertFalse(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithBothNullCredentials_thenReturnFalse() {
        // when
        boolean isAuthenticated = authService.authenticate(null, null);

        // then
        assertFalse(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithWhitespaceRut_thenReturnFalse() {
        // given
        UserEntity user = new UserEntity();
        user.setRut("12345678-9");
        user.setPassword("password123");

        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        // when
        boolean isAuthenticated = authService.authenticate(" ", "password123");

        // then
        assertFalse(isAuthenticated);
    }

    @Test
    public void whenAuthenticateWithWhitespacePassword_thenReturnFalse() {
        // given
        UserEntity user = new UserEntity();
        user.setRut("12345678-9");
        user.setPassword("password123");

        when(userRepository.findByRut("12345678-9")).thenReturn(user);

        // when
        boolean isAuthenticated = authService.authenticate("12345678-9", " ");

        // then
        assertFalse(isAuthenticated);
    }
}
