package edu.icet.solorent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet.solorent.util.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {
    private Long bookingID;

    @NotEmpty(message = "User ID should not be blank")
    private String customerName;

    @NotEmpty(message = "This should not be blank")
    @Email
    private String email;

    @NotEmpty(message = "This should not be blank")
    @Size(min = 10, max = 10)
    private String contact;

    private Long vehicleID;

    @NotNull(message = "This should not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate  startDate;

    @NotNull(message = "This should not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate  endDate;

    @NotEmpty(message = "This should not be blank")
    private String pickupTime;

    @NotEmpty(message = "This should not be blank")
    private String pickupLocation;

    @NotEmpty(message = "This should not be blank")
    private String returnLocation;

    private String description;

    private LocalDateTime bookingDateTime;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

}
