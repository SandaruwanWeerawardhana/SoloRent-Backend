package edu.icet.solorent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet.solorent.util.BookingPayment;
import edu.icet.solorent.util.BookingStatus;
import jakarta.persistence.*;
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
@Entity
@Table(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotEmpty(message = "This should not be blank")
    private Long bookingID;

    @NotEmpty(message = "User ID should not be blank")
    private Long userName;

    @NotEmpty(message = "This should not be blank")
    private String email;

    @NotEmpty(message = "This should not be blank")
    private Long vehicleID;

    @NotNull(message = "This should not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date startDate;

    @NotNull(message = "This should not be blank")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private Date endDate;

    @NotEmpty(message = "This should not be blank")
    private Double totalPrice;

    @NotEmpty(message = "This should not be blank")
    private String pickupLocation;

    @NotEmpty(message = "This should not be blank")
    private String returnLocation;

    @NotEmpty(message = "This should not be blank")
    private BookingStatus bookingStatus;

    @NotEmpty(message = "This should not be blank")
    private BookingPayment paymentStatus;
}
