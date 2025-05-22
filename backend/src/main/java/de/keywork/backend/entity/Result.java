package de.keywork.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    private String category;
    private String question;
    private String correctAnswer;
    private boolean answeredCorrectly;

    @ManyToOne(optional = false)
    @JoinColumn(name = "form_data_id", unique = false)
    private FormData formData;

    @OneToOne(mappedBy = "result")
    private JackpotHistory jackpotHistory;
}
