package elte.rrlg.spark.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import elte.rrlg.spark.AuthenticatedUser;
import elte.rrlg.spark.model.User;
import elte.rrlg.spark.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticatedUser authUser;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
          
    
    public Optional<User> getUser() {
        Optional<User> user = userRepository.findById(authUser.getUser().getId());
        if (user.isPresent()) {
            user.get().reduceForProfile();
        }
        return user;
    }

    public User registerUser(User user) {
        Optional<User> regUser = userRepository.findByEmail(user.getEmail());
        if (regUser.isPresent()) {
            return null;
        }
        user.setRole(User.Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPictures(new ArrayList());
        user.setInterestUsers(new ArrayList());
        user.setInterestedUsers(new ArrayList());

        return userRepository.save(user);
    }

    // Password/email/name update
    public User updateAccount(User user) {
        Optional<User> updateUser = userRepository.findById(authUser.getUser().getId());
        if (!updateUser.isPresent()) {
            return null;
        }

        if (user.getPassword() != null) {
            updateUser.get().setPassword(user.getPassword());
        }

        if (user.getFullName() != null) {
            updateUser.get().setFullName(user.getFullName());
        }

        if (user.getEmail() != null) {
            updateUser.get().setEmail(user.getEmail());
        }

        user = userRepository.save(updateUser.get());
        user.setPassword(null);
        return user;
    }

    // Profile update
    public User updateProfile(User user) {
        Optional<User> updateUser = userRepository.findById(authUser.getUser().getId());
        if (!updateUser.isPresent()) {
            return null;
        }

        if (user.getGender() != null) {
            updateUser.get().setGender(user.getGender());
        }

        if (user.getSexuality() != null) {
            updateUser.get().setSexuality(user.getSexuality());
        }

        if (user.getDescription() != null) {
            updateUser.get().setDescription(user.getDescription());
        }

        user = userRepository.save(updateUser.get());
        user.setPassword(null);
        return user;
    }

    public User logout(User user) {
        return null;
    }

    public User loginUser() {
        return getUser().get();
    }
}