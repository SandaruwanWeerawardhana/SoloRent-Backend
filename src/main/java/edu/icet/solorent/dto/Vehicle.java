package edu.icet.solorent.dto;

import edu.icet.solorent.util.VehicleStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Vehicle {
    @NotEmpty(message = "This should not be blank")
    private Long vehicleID;

    @NotEmpty(message = "This should not be blank")
    private String brand;

    @NotEmpty(message = "This should not be blank")
    private String model;

    @NotEmpty(message = "This should not be blank")
    private String fuelType;

    @NotEmpty(message = "This should not be blank")
    private String year;

    @NotEmpty(message = "This should not be blank")
    private String pricePerDay;

    @NotEmpty(message = "This should not be blank")
    private VehicleStatus status;

    @NotEmpty(message = "This should not be blank")
    private String imageURl;

    @NotEmpty(message = "This should not be blank")
    private String description;
}
