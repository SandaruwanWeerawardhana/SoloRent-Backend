package edu.icet.solorent.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "admin")
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminID;

    @NotEmpty(message = "should not be blank")
    private String name;

    @NotEmpty(message = "should not be blank")
    private String nic;

    @NotEmpty(message = "should not be blank")
    private String email;

    @NotEmpty(message = "should not be blank")
    private String password;
}
