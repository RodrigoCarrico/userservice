package com.demo.userservice.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

public abstract class DomainCommandEvents {
    @JsonIgnore
    @Transient
    private List<DomainCommand> eventsComnand = new ArrayList<>();

    public void addEvent(DomainCommand comando) {
        eventsComnand.add(comando);
    }

    public void clean() {
        eventsComnand.clear();
    }

    public List<DomainCommand> getEvents() {
        return eventsComnand;
    }
}
