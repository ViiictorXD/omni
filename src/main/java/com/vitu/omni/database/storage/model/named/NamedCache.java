package com.vitu.omni.database.storage.model.named;

import com.vitu.omni.database.storage.cache.SaveCache;
import com.vitu.omni.database.storage.model.base.Repository;

public class NamedCache<E extends NamedEntity, R extends Repository<E>> extends SaveCache<E, R> {

    public NamedCache(R repository) {
        super(repository);
    }

    public E getIfMatch(String name) {
        for (E entity : cached)
            if (entity.match(name)) {
                entity.refreshLastCheck();
                return entity;
            }

        return null;
    }
}
