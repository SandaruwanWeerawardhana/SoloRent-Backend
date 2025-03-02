package edu.icet.solorent.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Admin {
    @NotEmpty(message = "User ID should not be blank")
    private Long userID;

    @NotEmpty(message = "Admin ID should not be blank")
    private Long adminID;

}
