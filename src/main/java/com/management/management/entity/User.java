package com.management.management.entity;

import com.management.management.annotation.EmailValidate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @EmailValidate
    private String email;

    @NotNull
    private String password;

    private String role;

    @NotNull
    private String name;




    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name= name;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }



    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }





    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
