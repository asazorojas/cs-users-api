package com.cornershopapp.usersapi.rest.response.users;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersListOBean {
    private List<UserOBean> users;
}
