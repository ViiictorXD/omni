package com.omni.minecraft.database.entity;

import lombok.Data;

@Data
public abstract class Entity<I>  {

    private final I id;
    private final EntityMetadata metadata;

}