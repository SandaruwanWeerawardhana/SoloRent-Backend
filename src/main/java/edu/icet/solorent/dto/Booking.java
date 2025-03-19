package edu.icet.solorent.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet.solorent.util.BookingPaymentStatus;
import edu.icet.solorent.util.BookingStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Booking {
    @NotEmpty(message = "This should not be blank")
    private Long bookingID;

    @NotEmpty(message = "User ID should not be blank")
    private Long customerName;

    @NotEmpty(message = "This should not be blank")
    @Email
    private String email;

    @NotEmpty(message = "This should not be blank")
    @Size(min = 10, max = 10)
    private String contact;

    @NotEmpty(message = "This should not be blank")
    private Long vehicleID;

    @NotNull(message = "This should not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date startDate;

    @NotNull(message = "This should not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date endDate;

    @NotEmpty(message = "This should not be blank")
    private String pickupTime;

    @NotEmpty(message = "This should not be blank")
    private String pickupLocation;

    @NotEmpty(message = "This should not be blank")
    private String returnLocation;

    private String description;

    private LocalDate bookingDate;


    private LocalDate bookingTime;

    private Double totalPrice;

    private BookingStatus bookingStatus;

    private BookingPaymentStatus paymentStatus;
}
