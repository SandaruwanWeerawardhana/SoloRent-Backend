package edu.icet.solorent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Review {
    @NotEmpty(message = "This should not be blank")
    private Long reviewID;

    @NotEmpty(message = "This should not be blank")
    private Long userID;

    @NotEmpty(message = "This should not be blank")
    private Long vehicleID;

    @NotEmpty(message = "This should not be blank")
    private String comment;

    @NotNull(message = "This should not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date datePosted   ;
}
