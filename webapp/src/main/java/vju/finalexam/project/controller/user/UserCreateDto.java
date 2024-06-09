package vju.finalexam.project.controller.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String username;
    private String password;
    private String email;

    private String role;
}
