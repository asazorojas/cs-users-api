package com.cornershopapp.usersapi.domain.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private Long id;
    private UUID uuid;
    private BigDecimal total;
}
