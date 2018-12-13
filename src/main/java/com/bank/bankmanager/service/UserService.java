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

import java.util.*;
import java.util.stream.Collectors;

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

    public boolean create(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) return false;

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.CLIENT));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // TODO: убрать, даёт права админа первому зарегестрированному пользователю
        List<User> chckUser = userRepo.findAll();
        if (chckUser.isEmpty()) {
            Set<Role> set = EnumSet.allOf(Role.class);
            user.setRoles(set);
        }

        userRepo.save(user);
        return true;
    }

    public User getByUsername(String name) {
        return userRepo.findByUsername(name);
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public boolean updateProfile(String password, String password2, User user) {
        if (!StringUtils.isEmpty(password) &&
                !StringUtils.isEmpty(password2) &&
                password.equals(password2)
        ) { user.setPassword(passwordEncoder.encode(password)); } else { return false; }

        userRepo.save(user);

        return true;
    }

    public boolean edit(User user, Map<String, String> form) {
        if (user == null && form.isEmpty()) return false;

        String username = form.get("username");
        String password = form.get("password");
        String password2 = form.get("password2");
        String firstName = form.get("firstName");
        String lastName = form.get("lastName");
        String secondName = form.get("secondName");
        String address = form.get("address");
        boolean active = form.get("active") != null;

        // username
        if (!StringUtils.isEmpty(username) && !username.equals(user.getUsername()))
            user.setUsername(username);

        // password
        if (!StringUtils.isEmpty(password) && !StringUtils.isEmpty(password2)) {
            if (password.equals(password2)) {
                user.setPassword(passwordEncoder.encode(password));
            } else {
                return false;
            }
        }

        // firstName
        if (!StringUtils.isEmpty(firstName) && !firstName.equals(user.getFirstName()))
            user.setFirstName(firstName);

        // lastName
        if (!StringUtils.isEmpty(lastName) && !lastName.equals(user.getLastName()))
            user.setLastName(lastName);

        // secondName
        if (!StringUtils.isEmpty(secondName) && !secondName.equals(user.getSecondName()))
            user.setSecondName(secondName);

        // address
        if (!StringUtils.isEmpty(address) && !address.equals(user.getAddress()))
            user.setAddress(address);

        // add Roles
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        user.setActive(active);

        userRepo.save(user);

        return true;
    }
}
