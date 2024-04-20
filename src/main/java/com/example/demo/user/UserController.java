package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;
    private final CarPlateService carPlateService;

    @Autowired
    public UserController(UserService userService, CarPlateService carPlateService){
        this.userService = userService;
        this.carPlateService = carPlateService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping(path = "/register")
    public ResponseEntity<Map<String, Boolean>> registerNewUser(@RequestBody User user) {
        try {
            userService.addNewUser(user);
            Map<String, Boolean> response = new HashMap<>();
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            Map<String, Boolean> response = new HashMap<>();
            response.put("success", false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<Map<String, Boolean>> loginUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        boolean isValid = userService.validateUser(username, password);
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", isValid);
        return isValid ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

    @PostMapping(path = "/carplate")
    public ResponseEntity<Map<String, String>> convertCarPlate(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String macauCarPlate = request.get("macauCarPlate");
        Map<String, String> response = new HashMap<>();
        try {
            User user = userService.findByUsername(username);
            // Check if the Macau car plate already has a corresponding China car plate
            if (user.getMacauCarPlates().contains(macauCarPlate)) {
                int index = user.getMacauCarPlates().indexOf(macauCarPlate);
                response.put("chinaCarPlate", user.getChinaCarPlates().get(index));
            } else {
                // Process new Macau car plate
                String chinaCarPlate = carPlateService.processCarPlate(macauCarPlate, user);
                response.put("chinaCarPlate", chinaCarPlate);
            }
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
