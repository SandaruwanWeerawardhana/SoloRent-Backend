package edu.icet.solorent.entity;

import edu.icet.solorent.util.VehicleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static edu.icet.solorent.util.VehicleStatus.AVAILABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "vehicle")
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleID;
    @NotNull(message = "This should not be blank")
    private String brand;

    @NotNull(message = "This should not be blank")
    private String fuelType;

    @NotNull(message = "This should not be blank")
    private String registerNumber;

    @NotNull(message = "This should not be blank")
    private Double pricePerDay;

    @NotNull(message = "This should not be blank")
    private String imageURl;

    @NotNull(message = "This should not be blank")
    private String description;
}
