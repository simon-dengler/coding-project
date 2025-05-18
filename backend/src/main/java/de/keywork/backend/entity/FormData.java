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
public class FormData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;


    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String favouriteAnimal;
    private String zodiac;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FormData that = (FormData) o;

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
