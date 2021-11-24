package com.cornershopapp.usersapi.domain.dtos;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO implements Serializable {
    private Long id;
    private String number;
}
