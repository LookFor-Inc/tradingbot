package com.lookfor.trading.services.implementations;

import com.lookfor.trading.models.User;
import com.lookfor.trading.repositories.UserRepository;
import com.lookfor.trading.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Collections;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Integer telegramUserId) {
        return userRepository.findById(telegramUserId);
    }

    @Override
    public void saveUpdates(Message message) {
        var userFrom = message.getFrom();
        Integer userId = userFrom.getId();
        String username = userFrom.getUserName();
        if (username == null) {
            username = userFrom.getFirstName();
        }
        log.info(String.format(
                "From @%s (%s): '%s'", username, userId, message.getText())
        );

        Optional<User> userOptional = findById(userId);
        if (userOptional.isEmpty()) {
            User user = User.builder()
                    .id(userId)
                    .username(username)
                    .build();
            save(user);
        }
    }
}
