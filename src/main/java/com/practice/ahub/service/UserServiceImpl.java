package com.practice.ahub.service;

import com.practice.ahub.exception.CustomException;
import com.practice.ahub.jwt.JwtRequest;
import com.practice.ahub.jwt.JwtResponse;
import com.practice.ahub.jwt.JwtService;
import com.practice.ahub.model.Role;
import com.practice.ahub.model.User;
import com.practice.ahub.model.UserProfile;
import com.practice.ahub.repository.UserProfileRepository;
import com.practice.ahub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserProfileRepository profileRepository;

    @Override
    public User createUser(User user) {
        user.setPassword(cryptPassword(user.getPassword()));
        user.setRole(Role.USER);
        User savedUSer = userRepository.save(user);
        UserProfile profile = UserProfile.builder()
                .user(user)
                .link(String.valueOf(user.getId()))
                .build();
        profileRepository.save(profile);
        return savedUSer;
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
    }

    @Override
    public ResponseEntity<JwtResponse> login(JwtRequest request) {
        User user = loadUserByUsername(request.getEmail());
        if (user != null && passwordEncoder.matches(
                request.getPassword(), user.getPassword()
        )) {
            return jwtService.generateToken(user);
        } else {
            throw new CustomException("You entered the wrong password");
        }
    }

    private String cryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

}
