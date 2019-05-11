package com.friday.colini.attach.utils;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
class ApplicationContextProvider implements ApplicationContextAware {
    @Getter(AccessLevel.PACKAGE)
    private static ApplicationContext context;

    //
    //
    //

    @Override
    public void setApplicationContext(@NonNull final ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
