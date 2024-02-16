package com.bondis.cifrado.modules.users.model;

import jakarta.validation.constraints.NotNull;

public class UserDto {
    private Long id;
    @NotNull(groups = {Save.class})
    private String name;
    @NotNull(groups = {Save.class})
    private String email;
    @NotNull(groups = {Save.class})
    private String password;
    @NotNull(groups = {Save.class})
    private String motivation;

    public interface Save{}

    public String toString() {
        return "UserDto(id=" + this.id + ", name=" + this.name + ", email=" + this.email + ", password=" + this.password + ", motivation=" + this.motivation + ")";
    }
}
