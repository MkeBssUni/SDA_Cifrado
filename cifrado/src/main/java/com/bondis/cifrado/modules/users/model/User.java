package com.bondis.cifrado.modules.users.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Email
    private String username;
    @Column(columnDefinition = "TEXT")
    private String password;
    private String motivation;
    @Column(columnDefinition = "TEXT")
    private String key;
    @Column(columnDefinition = "BOOL default false")
    private boolean blocked;

    public User(String email, String admin1234) {
    }

    public void save (UserDto dto){
        this.name = dto.getName();
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.motivation = dto.getMotivation();
    }
}
