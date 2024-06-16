package com.omni.minecraft.database.entity;

import lombok.Data;

import java.time.Instant;

@Data
public final class EntityMetadata {

    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant searchedAt;
    private final Instant deletedAt;

}