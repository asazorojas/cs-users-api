package com.cornershopapp.usersapi.domain.models;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "phones", indexes = {
        @Index(name = "idx_phone_id", columnList = "id")
})
@RequiredArgsConstructor
@Getter
@Setter
public class Phone {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

    @OneToOne(mappedBy = "phone", fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return id.equals(phone.id) && number.equals(phone.number) && user.equals(phone.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, user);
    }
}