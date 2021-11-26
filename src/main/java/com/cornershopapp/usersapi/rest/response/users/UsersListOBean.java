package com.cornershopapp.usersapi.rest.response.users;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersListOBean {
    private List<UserOBean> users;
}
