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
    private Long favNumber;
    private String favNumberString;

    public interface Save{}

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", motivation='" + motivation + '\'' +
                ", favNumber=" + favNumber +
                '}';
    }
}
