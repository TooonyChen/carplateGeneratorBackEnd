package com.example.demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CarPlateService {

    @Autowired
    private UserRepository userRepository;

    public String generateChinaCarPlate() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder carPlate = new StringBuilder();
        int letterCount = random.nextInt(5);  // 0 to 4 letters
        for (int i = 0; i < letterCount; i++) {
            carPlate.append(letters.charAt(random.nextInt(letters.length())));
        }
        while (carPlate.length() < 5) {
            carPlate.append(digits.charAt(random.nextInt(digits.length())));
        }
        if (carPlate.length() == 0) {
            carPlate.append(digits.charAt(random.nextInt(digits.length())));
        }
        return carPlate.toString();
    }

    public String processCarPlate(String macauCarPlate, User user) throws IllegalStateException {
        if (!macauCarPlate.matches("^[A-Z]{2}\\d{4}$")) {
            throw new IllegalStateException("Invalid Macau car plate format.");
        }

        // Check if the Macau car plate already has a corresponding China car plate
        if (user.getMacauCarPlates().contains(macauCarPlate)) {
            int index = user.getMacauCarPlates().indexOf(macauCarPlate);
            return user.getChinaCarPlates().get(index);
        }

        String chinaCarPlate = generateChinaCarPlate();
        // Check if generated China car plate already exists
        while (user.getChinaCarPlates().contains(chinaCarPlate)) {
            chinaCarPlate = generateChinaCarPlate();
        }

        // Add the new Macau and China car plates to the user and persist to the database
        user.getMacauCarPlates().add(macauCarPlate);
        user.getChinaCarPlates().add(chinaCarPlate);
        userRepository.save(user);

        return chinaCarPlate;
    }
}
