package com.lookfor.trading.services.implementations;

import com.lookfor.trading.exceptions.UserNotFoundException;
import com.lookfor.trading.models.User;
import com.lookfor.trading.models.UserTicker;
import com.lookfor.trading.repositories.UserTickerRepository;
import com.lookfor.trading.services.UserService;
import com.lookfor.trading.services.UserTickerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTickerServiceImpl implements UserTickerService {
    private final UserTickerRepository userTickerRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public List<UserTicker> findAllByUserId(int userId) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        return userTickerRepository.findAllByUser(userOptional.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllUserTickerNames(int userId) {
        return findAllByUserId(userId).stream()
                .map(UserTicker::getName)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUserAndName(int userId, String name) {
        return userTickerRepository.existsByUserAndName(userId, name);
    }

    @Override
    @Transactional
    public boolean save(UserTicker userTicker, int userId) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isPresent()) {
            userTicker.setUser(userOptional.get());
            userTicker.getTickersData().forEach(data -> data.setUserTicker(userTicker));
            userTickerRepository.save(userTicker);
            log.info("User ticker successfully saved!");
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserTicker> findUserTickerByUserIdAndName(int userId, String name) {
        Optional<User> userOptional = userService.findById(userId);

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        return userTickerRepository.findByUserAndName(userOptional.get(), name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserTicker> findUserTickerByName(String name) {
        return userTickerRepository.findByName(name);
    }
}
