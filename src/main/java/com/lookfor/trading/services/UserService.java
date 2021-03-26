package com.lookfor.trading.services;

import com.lookfor.trading.models.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

/**
 * Service interface for managing {@link com.lookfor.trading.models.User}
 */
public interface UserService {
    /**
     * Save a User
     *
     * @param user entity to save
     */
    void save(User user);

    /**
     * Fetch a User by his telegram id
     *
     * @param id User's telegram id
     * @return persisted entity
     */
    Optional<User> findById(int id);

    /**
     * Save User's updates or create a new one
     *
     * @param message received Message
     */
    void saveUpdates(Message message);
}
