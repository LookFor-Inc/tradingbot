package com.lookfor.trading.services.implementations;

import com.lookfor.trading.models.User;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.repositories.UserTickerRepository;
import com.lookfor.trading.services.UserService;
import com.lookfor.trading.services.UsersTickersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersTickersServiceImpl implements UsersTickersService {
    private final UserTickerRepository userTickerRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<UserTicker> getAllUsersTickers() {
        return userTickerRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllUserTickerNames() {
        return getAllUsersTickers().stream()
                .map(UserTicker::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public boolean save(UserTicker userTicker, int userId) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            userTicker.setUser(userOptional.get());
            userTickerRepository.save(userTicker);
            log.info("User ticker successfully saved!");
            return true;
        }

        return false;
    }
}
