package de.keywork.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String username;
    private String passwordHash;

    @OneToOne
    @JoinColumn(name = "form_data_id", unique = true)
    private FormData formData;
}
