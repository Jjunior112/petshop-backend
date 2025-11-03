package com.littlebirds.petshop.application.services;

import com.littlebirds.petshop.domain.dtos.user.AdminRegisterDto;
import com.littlebirds.petshop.domain.dtos.user.UserListDto;
import com.littlebirds.petshop.domain.dtos.user.UserRegisterDto;
import com.littlebirds.petshop.domain.enums.UserRole;
import com.littlebirds.petshop.domain.models.Client;
import com.littlebirds.petshop.domain.models.User;
import com.littlebirds.petshop.domain.models.Worker;
import com.littlebirds.petshop.infra.repositories.ClientRepository;
import com.littlebirds.petshop.infra.repositories.UserRepository;
import com.littlebirds.petshop.infra.repositories.WorkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final WorkerRepository workerRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ClientRepository clientRepository, WorkerRepository workerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.workerRepository = workerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByEmail(username);

        if (!user.isEnabled()) {
            throw new IllegalArgumentException("Usuário inativo. Entre em contato com o administrador.");
        }

        return user;
    }

    @Transactional
    public User createUserAdmin(AdminRegisterDto register) {

        if (userRepository.findByEmail(register.email()) != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(register.password());

        User user = new User(
                register.fullName(),
                register.email(),
                encryptedPassword,
                register.phone(),
                UserRole.ADMIN
        );

        return userRepository.save(user);

    }

    @Transactional
    public Worker createWorkerUser(UserRegisterDto register) {

        if (userRepository.findByEmail(register.email()) != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(register.password());

        Worker worker = new Worker(
                register.fullName(),
                register.email(),
                encryptedPassword,
                register.phone(),
                UserRole.WORKER
        );

        return userRepository.save(worker);
    }

    @Transactional
    public Client createCommonUser(UserRegisterDto register) {

        if (userRepository.findByEmail(register.email()) != null) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        String encryptedPassword = passwordEncoder.encode(register.password());

        Client client = new Client(
                register.fullName(),
                register.email(),
                encryptedPassword,
                register.phone(),
                UserRole.CLIENT
        );

        return userRepository.save(client);

    }

    public Page<UserListDto> findAllUsers(Pageable pagination, UserRole role) {
        if (role == null) {
            return userRepository.findAll(pagination).map(UserListDto::new);
        } else {
            return userRepository.findByRole(role, pagination).map(UserListDto::new);
        }
    }

    public User findUserById(UUID id) {
        return userRepository.getReferenceById(id);
    }

    @Transactional
    public void inactiveUser(UUID id) {
        User user = userRepository.getReferenceById(id);

        user.inactiveUser();
    }

    @Transactional
    public void reactiveUser(UUID id) {
        User user = userRepository.getReferenceById(id);

        user.reactiveUser();
    }

    public boolean existUser(String email) {

        var user = userRepository.findByEmail(email);
        return user != null;
    }
}