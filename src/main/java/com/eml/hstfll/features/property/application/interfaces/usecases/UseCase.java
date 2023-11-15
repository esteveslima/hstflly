package com.eml.hstfll.features.property.application.interfaces.usecases;

import org.springframework.stereotype.Component;

@Component
public interface UseCase<P, R> {
    public R execute(P params);
}
