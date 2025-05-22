package de.keywork.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "jackpots")
public class Jackpot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String name;
    private String description;

    @OneToOne(mappedBy = "jackpot")
    private JackpotHistory jackpotHistory;
}
