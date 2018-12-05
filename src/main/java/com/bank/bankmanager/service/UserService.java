package com.bank.bankmanager.service;

import com.bank.bankmanager.domain.Role;
import com.bank.bankmanager.domain.User;
import com.bank.bankmanager.repos.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) return false;

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.CLIENT));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);
        return true;
    }

    public User findByUsername(String name) {
        return userRepo.findByUsername(name);
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public List<User> findByActive(boolean active) {
        return userRepo.findByActive(active);
    }

    public boolean userEnable(User user) {
        if (user.isActive()) return false;
        user.setActive(true);
        userRepo.save(user);
        return true;
    }

    public boolean userDisable(User user) {
        if (!user.isActive()) return false;
        user.setActive(false);
        userRepo.save(user);
        return true;
    }

    public void updateProfile(
            User user,
            String password,
            String firstName,
            String lastName,
            String secondName,
            String address
    ) {
        String userFirstName = user.getFirstName();
        String userLastName = user.getLastName();
        String userSecondName = user.getSecondName();
        String userAddress = user.getAddress();

        if (firstName != null && !firstName.equals(userFirstName) || userFirstName != null && !userFirstName.equals(firstName))
            user.setAddress(firstName);
        if (lastName != null && !lastName.equals(userLastName) || userLastName != null && !userLastName.equals(lastName))
            user.setAddress(lastName);
        if (secondName != null && !secondName.equals(userSecondName) || userSecondName != null && !userSecondName.equals(secondName))
            user.setAddress(secondName);
        if (address != null && !address.equals(userAddress) || userAddress != null && !userAddress.equals(address))
            user.setAddress(address);

        if (!StringUtils.isEmpty(password)) user.setPassword(password);

        userRepo.save(user);
    }
}
