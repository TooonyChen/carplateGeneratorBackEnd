package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;



@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public boolean validateUser(String username, String password) {
        Optional<User> user = userRepository.findByName(username);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public void addNewUser(User user) {
        Optional<User> userOptional = userRepository.findByName(user.getName());
        if (userOptional.isPresent()) {
            throw new IllegalStateException("Name already taken");
        }
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByName(username)
                .orElseThrow(() -> new IllegalStateException("User not found with username: " + username));
    }




}
