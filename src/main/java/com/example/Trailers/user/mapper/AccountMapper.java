package com.example.Trailers.user.mapper;

import com.example.Trailers.user.model.UserAccount;
import com.example.Trailers.auth.dto.AuthRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class AccountMapper {

    public UserAccount toUserAccount(AuthRequest dto) {
        UserAccount userAccount = new UserAccount();

        userAccount.setEmail(dto.email());
        userAccount.setPassword(dto.password());

        return userAccount;
    }
}
