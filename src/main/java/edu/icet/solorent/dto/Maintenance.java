package edu.icet.solorent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class Maintenance {
    @NotEmpty(message = "This should not be blank")
    private Long maintenanceID;

    @NotEmpty(message = "This should not be blank")
    private Long vehicleID;

    @NotEmpty(message = "address should not be blank")
    private String description;

    @NotEmpty(message = "address should not be blank")
    private Double cost;

    @NotNull(message = "This should not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date date;

}
