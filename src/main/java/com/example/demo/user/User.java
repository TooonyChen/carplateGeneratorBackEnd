package com.example.demo.user;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(unique = true)
    private String name;
    private String password;

    @ElementCollection
    @CollectionTable(name = "macau_car_plates", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "macau_car_plate")
    private List<String> macauCarPlates = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "china_car_plates", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "china_car_plate")
    private List<String> chinaCarPlates = new ArrayList<>();

    //constructor
    public User(){

    }

    public User(Long id, String name, String password, List<String> macauCarPlates, List<String> chinaCarPlates) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.macauCarPlates = macauCarPlates;
        this.chinaCarPlates = chinaCarPlates;
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    //getter and setter


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getMacauCarPlates() {
        return macauCarPlates;
    }

    public void setMacauCarPlates(List<String> macauCarPlates) {
        this.macauCarPlates = macauCarPlates;
    }

    public List<String> getChinaCarPlates() {
        return chinaCarPlates;
    }

    public void setChinaCarPlates(List<String> chinaCarPlates) {
        this.chinaCarPlates = chinaCarPlates;
    }

    //toString

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
