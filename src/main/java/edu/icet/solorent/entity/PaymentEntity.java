package edu.icet.solorent.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import edu.icet.solorent.util.PaymentMethod;
import edu.icet.solorent.util.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "payment")
public class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

    @NotEmpty(message = "This should not be blank")
    private Long bookingID;

    @NotEmpty(message = "This should not be blank")
    private Double amount;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate paymentDate;

    @NotEmpty(message = "This should not be blank")
    private PaymentMethod method;

    @NotEmpty(message = "This should not be blank")
    private PaymentStatus status;

}
