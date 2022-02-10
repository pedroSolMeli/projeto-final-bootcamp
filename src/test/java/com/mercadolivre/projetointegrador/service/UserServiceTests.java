package com.mercadolivre.projetointegrador.service;

import com.mercadolivre.projetointegrador.enums.UserRole;
import com.mercadolivre.projetointegrador.user.dto.UserDto;
import com.mercadolivre.projetointegrador.user.dto.UserRequestDto;
import com.mercadolivre.projetointegrador.user.dto.UserResponseDto;
import com.mercadolivre.projetointegrador.user.model.User;
import com.mercadolivre.projetointegrador.user.repository.UserRepository;
import com.mercadolivre.projetointegrador.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Test
    public void shouldCreateUserWithSuccess() {
        UserRequestDto request = UserRequestDto.builder()
                .cpf("86545843001").name("pedro").username("pedro").email("pedro@email.com")
                .password("pedro123").roles(Arrays.asList("A", "S")).build();

        User user = UserRequestDto.ConvertToObject(request);
        user.setId(1l);
        Mockito.when(userRepository.saveAndFlush(Mockito.any())).thenReturn(user);
        UserResponseDto response = userService.createUser(request);

        Assertions.assertEquals(request.getCpf(), response.getCpf());
        Assertions.assertEquals(request.getName(), response.getName());
        Assertions.assertEquals(request.getEmail(), response.getEmail());
        Assertions.assertEquals(request.getRoles(), response.getRoles());
    }

    @Test
    public void shouldCreateUserWithException() {
        UserRequestDto request = UserRequestDto.builder()
                .cpf("86545843001").name("pedro").username("pedro").email("pedro@email.com")
                .password("pedro123").roles(Arrays.asList("A", "S")).build();

        Mockito.when(userRepository.saveAndFlush(Mockito.any())).thenThrow(ConstraintViolationException.class);

        Assertions.assertThrows(ConstraintViolationException.class, () -> userService.createUser(request));
    }

    @Test
    public void shouldReturnAllUsersWithSuccess() {
        // User pedro = User.builder().id(1L).cpf("86545843001").name("pedro").username("psol").email("pedro@email.com")
        //       .password(encoder.encode("pedro123")).roles(Arrays.asList(UserRole.A, UserRole.S)).build();

        User ana = User.builder().id(2L).cpf("98840453059").name("ana").username("anag").email("ana@email.com")
                .password(encoder.encode("ana123")).roles(Arrays.asList(UserRole.A, UserRole.S, UserRole.B)).build();
        // User renan = User.builder().id(3L).cpf("55027433069").name("renan").username("renanmeli").email("renan@email.com")
        //         .password(encoder.encode("renan123")).roles(Arrays.asList(UserRole.A, UserRole.B)).build();
        List<User> users = Arrays.asList(ana);

        Mockito.when(userRepository.findAll()).thenReturn(users);
        List<UserResponseDto> response = userService.findAllUsers();

        Assertions.assertEquals(ana.getId(), response.get(0).getId());
        Assertions.assertEquals(ana.getCpf(), response.get(0).getCpf());
        Assertions.assertEquals(ana.getName(), response.get(0).getName());
        Assertions.assertEquals(ana.getEmail(), response.get(0).getEmail());
        Assertions.assertEquals(ana.getRoles().toString(), response.get(0).getRoles().toString());
    }

    @Test
    public void shouldFinduserByIdWithSuccess() {
        User ana = User.builder().id(2L).cpf("98840453059").name("ana").username("anag").email("ana@email.com")
                .password(encoder.encode("ana123")).roles(Arrays.asList(UserRole.A, UserRole.S, UserRole.B)).build();

        Mockito.when(userRepository.getUserById(2L)).thenReturn(ana);
        UserResponseDto response = userService.findUser(2L);

        Assertions.assertEquals(ana.getId(), response.getId());
        Assertions.assertEquals(ana.getCpf(), response.getCpf());
        Assertions.assertEquals(ana.getName(), response.getName());
        Assertions.assertEquals(ana.getEmail(), response.getEmail());
        Assertions.assertEquals(ana.getRoles().toString(), response.getRoles().toString());
    }

    @Test
    public void shouldFindUserEntityByIdWithSuccess() {
        User ana = User.builder().id(2L).cpf("98840453059").name("ana").username("anag").email("ana@email.com")
                .password(encoder.encode("ana123")).roles(Arrays.asList(UserRole.A, UserRole.S, UserRole.B)).build();

        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(ana));
        User response = userService.findUserWithoutConvert(2L);

        Assertions.assertEquals(ana.getId(), response.getId());
        Assertions.assertEquals(ana.getCpf(), response.getCpf());
        Assertions.assertEquals(ana.getName(), response.getName());
        Assertions.assertEquals(ana.getEmail(), response.getEmail());
        Assertions.assertEquals(ana.getRoles().toString(), response.getRoles().toString());
    }

    @Test
    public void shouldFindUserByUsernameWithSuccess() {
        User ana = User.builder().id(2L).cpf("98840453059").name("ana").username("anag").email("ana@email.com")
                .password(encoder.encode("ana123")).roles(Arrays.asList(UserRole.A, UserRole.S, UserRole.B)).build();

        Mockito.when(userRepository.findUserByUsername("anag")).thenReturn(Optional.ofNullable(ana));
        Optional<UserDto> response = userService.findUserByUsername("anag");

        Assertions.assertEquals(ana.getId(), response.get().getId());
        Assertions.assertEquals(ana.getUsername(), response.get().getUsername());
        Assertions.assertEquals(ana.getRoles().toString(), response.get().getRoles().toString());
    }

}
