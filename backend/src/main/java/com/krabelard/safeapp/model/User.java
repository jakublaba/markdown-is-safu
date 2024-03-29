package com.krabelard.safeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
// table name here needs to be changed, user is reserved keyword in postgres and causes db to crash
@Table(name = "usr")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Size(min = 4, max = 32)
    @Column(unique = true)
    private String username;

    @Size(min = 4, max = 64)
    @Column(unique = true)
    private String email;

    @Size(min = 8, max = 128)
    private String password;

}
