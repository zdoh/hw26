package ru.zdoher.otus.hw26;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.zdoher.otus.hw26.domain.Bug;

@MessagingGateway
public interface Work {

    @Gateway(requestChannel = "problemChannel", replyChannel = "workChannel")
    Bug process(Bug bug);
}
