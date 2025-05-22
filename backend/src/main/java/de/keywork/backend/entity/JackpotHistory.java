package de.keywork.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.util.Objects;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class JackpotHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "form_data_id", unique = false)
    private FormData formData;

    @OneToOne
    @JoinColumn(name = "result_id", unique = true)
    private Result result;
    @OneToOne
    @JoinColumn(name = "jackpot_id", unique = true)
    private Jackpot jackpot;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JackpotHistory that = (JackpotHistory) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" + "id=" + id + '}';
    }
}
