package com.cornershopapp.usersapi.domain.models;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "orders", indexes = {
        @Index(name = "idx_order_id", columnList = "id"),
        @Index(name = "idx_order_uuid", columnList = "uuid")
})
@RequiredArgsConstructor
@Getter
@Setter
public class Order {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid", nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column(nullable = false)
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id.equals(order.id) && uuid.equals(order.uuid) && total.equals(order.total) && user.equals(order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, total, user);
    }
}
