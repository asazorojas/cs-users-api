package com.cornershopapp.usersapi.domain.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_id", columnList = "id"),
        @Index(name = "idx_user_email", columnList = "email"),
        @Index(name = "idx_user_uuid", columnList = "uuid")
})
@RequiredArgsConstructor
@Getter
@Setter
public class User {
    @Column(nullable = false, updatable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_id", referencedColumnName = "id")
    private Phone phone;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Order> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id)
                && uuid.equals(user.uuid)
                && firstName.equals(user.firstName)
                && lastName.equals(user.lastName)
                && email.equals(user.email)
                && createdAt.equals(user.createdAt)
                && updatedAt.equals(user.updatedAt)
                && phone.equals(user.phone)
                && orders.equals(user.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, firstName, lastName, email, createdAt, updatedAt, phone, orders);
    }
}