package com.lookfor.trading.services.implementations;

import com.lookfor.trading.bot.Command;
import com.lookfor.trading.exceptions.CommandNotFoundException;
import com.lookfor.trading.services.CommandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.lookfor.trading.utils.TextMessageUtil.getCommandFromTextMessage;

@Slf4j
@Service
public class CommandServiceImpl implements CommandService {
    @Override
    public Command findCommandInMessage(String message) throws CommandNotFoundException {
        String textCmd = getCommandFromTextMessage(message);
        if (textCmd.isEmpty()) {
            return null;
        }
        Command cmd = Command.getByName(textCmd);
        log.info(
                String.format("Command '%s' (%s) was found in message '%s'",
                        textCmd, cmd, message)
        );
        if (cmd == null) {
            throw new CommandNotFoundException(textCmd);
        }
        return cmd;
    }
}
