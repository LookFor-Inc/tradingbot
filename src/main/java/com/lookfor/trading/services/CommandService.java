package com.lookfor.trading.services;

import com.lookfor.trading.bot.Command;
import com.lookfor.trading.exceptions.CommandNotFoundException;

/**
 * Service interface for managing {@link com.lookfor.trading.bot.Command}
 */
public interface CommandService {
    /**
     * Find a command name from message
     *
     * @param message received text message
     * @return command to manage
     * @throws CommandNotFoundException incorrect (not found) command exp
     */
    Command findCommandInMessage(String message) throws CommandNotFoundException;
}
