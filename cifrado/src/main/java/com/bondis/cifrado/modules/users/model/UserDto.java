package com.bondis.cifrado.modules.users.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    @NotNull(groups = {Save.class})
    private String name;
    @NotNull(groups = {Save.class})
    private String username;
    @NotNull(groups = {Save.class})
    private String password;
    @NotNull(groups = {Save.class})
    private String motivation;

    public interface Save{}

    public String toString() {
        return "UserDto(id=" + this.id + ", name=" + this.name + ", email=" + this.username + ", password=" + this.password + ", motivation=" + this.motivation + ")";
    }
}
