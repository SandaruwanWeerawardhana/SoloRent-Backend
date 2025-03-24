package edu.icet.solorent.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Massage {
    private Long massageID;

    @NotEmpty(message = "should not be blank")
    private String name;

    @NotEmpty(message = "should not be blank")
    private String email;

    @NotEmpty(message = "should not be blank")
    private String contact;

    @NotEmpty(message = "should not be blank")
    private String massage;
}
