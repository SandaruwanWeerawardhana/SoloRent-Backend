package edu.icet.solorent.dto;

import edu.icet.solorent.util.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @NotEmpty(message = "User ID should not be blank")
    private Long userID;

    @NotEmpty(message = "name should not be blank")
    private String name;

    @NotEmpty(message = "address should not be blank")
    private String address;

    @NotEmpty(message = "contact should not be blank")
    @Size(max = 10)
    private String contact;

    @NotEmpty(message = "role should not be blank")
    private UserRole role;

    @NotEmpty(message = "email should not be blank")
    @Email
    private String email;

    @NotEmpty(message = "password should not be blank")
    private String password;
}
