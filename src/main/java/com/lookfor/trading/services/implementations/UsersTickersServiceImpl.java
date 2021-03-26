package com.lookfor.trading.services.implementations;

import com.lookfor.trading.models.UsersTickers;
import com.lookfor.trading.repositories.UsersTickersRepository;
import com.lookfor.trading.services.UsersTickersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersTickersServiceImpl implements UsersTickersService {
    private final UsersTickersRepository usersTickersRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsersTickers> getAllUsersTickers() {
        return usersTickersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllUsersTickersNames() {
        return getAllUsersTickers().stream()
                .map(UsersTickers::getName)
                .collect(Collectors.toList());
    }
}
