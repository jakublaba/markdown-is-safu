package com.krabelard.safeapp.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class IV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    private byte[] value;

    @OneToOne
    @JoinColumn(name = "note_id")
    @Cascade(value = CascadeType.ALL)
    private Note note;

}
