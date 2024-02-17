package com.cms.world.utils;


import org.springframework.stereotype.Service;

@Service
public class IdService {

    private final SnowflakeIdGenerator idGenerator;

    public IdService(SnowflakeIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    public long generateId() {
        return idGenerator.nextId();
    }
}
