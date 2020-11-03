package com.example.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private int userId;
    private String userName;
    private String password;
    private boolean isActive;
    private String roles;

    @OneToMany(orphanRemoval=false , mappedBy = "user")
    private List<Account> accounts = new ArrayList<>();


}
