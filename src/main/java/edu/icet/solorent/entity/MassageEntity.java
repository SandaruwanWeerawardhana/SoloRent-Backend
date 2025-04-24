package edu.icet.solorent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "massage")
public class MassageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long massageID;

    @NotEmpty(message = "should not be blank")
    private String name;

    @NotEmpty(message = "should not be blank")
    @Email
    private String email;

    @NotEmpty(message = "should not be blank")
    private String contact;

    @NotEmpty(message = "should not be blank")
    private String massage;
}
