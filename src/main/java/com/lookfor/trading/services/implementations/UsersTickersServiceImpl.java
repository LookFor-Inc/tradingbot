package com.lookfor.trading.services.implementations;

import com.lookfor.trading.models.User;
import com.lookfor.trading.models.UsersTickers;
import com.lookfor.trading.repositories.UsersTickersRepository;
import com.lookfor.trading.services.UserService;
import com.lookfor.trading.services.UsersTickersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersTickersServiceImpl implements UsersTickersService {
    private final UsersTickersRepository usersTickersRepository;
    private final UserService userService;

    @Override
    public List<String> getAllUsersTickersNames() {
        return usersTickersRepository.findAllNames();
    }

    @Override
    public boolean save(UsersTickers userTicker, int userId) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            userTicker.setUser(userOptional.get());

            usersTickersRepository.save(userTicker);
            log.info("User ticker successfully saved!");
            return true;
        }

        return false;
    }
}
