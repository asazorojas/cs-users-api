package com.cornershopapp.usersapi.rest.response.users;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersListOBean {
    private List<UserOBean> users;
}
