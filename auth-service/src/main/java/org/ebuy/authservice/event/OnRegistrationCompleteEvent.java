package org.ebuy.authservice.event;

import lombok.Getter;
import lombok.Setter;
import org.ebuy.authservice.entity.User;
import org.springframework.context.ApplicationEvent;

/*
* event for registrationComplete
* */

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private User user;

    public OnRegistrationCompleteEvent(User user){
        super(user);
        this.user = user;
    }
}
