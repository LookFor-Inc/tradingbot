package com.lookfor.trading.services.implementations;

import com.lookfor.trading.repositories.UsersTickersRepository;
import com.lookfor.trading.services.UsersTickersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersTickersServiceImpl implements UsersTickersService {
    private final UsersTickersRepository usersTickersRepository;

    @Override
    public List<String> getAllUsersTickersNames() {
        return usersTickersRepository.findAllNames();
    }
}
