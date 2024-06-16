package com.omni.minecraft.database.storage.cache;

import com.omni.minecraft.database.storage.model.base.BaseEntity;

import java.util.ArrayList;
import java.util.List;

public class Cache<E extends BaseEntity> {

    protected List<E> cached;

    public Cache() {
        this.cached = new ArrayList<>();
    }

    public E getIfMatch(Integer id) {
        for (E e : cached)
            if (e.match(id)) {
                e.refreshLastCheck();
                return e;
            }
        return null;
    }

    public void add(E e) {
        e.setReferences(0);
        e.addReference();
        e.refreshLastCheck();

        //TEMP
        for (E exists : cached) {
            if (exists.match(e.getId())) {
                System.out.println("Duplicada...");
                System.out.println(exists.getClass().getSimpleName());
            }
        }
        //TEMP

        cached.add(e);
    }

    public List<E> getCached() {
        return cached;
    }

    public void unloaded(E entity) {
        entity.removeReference();
    }

    public void saveModified(E entity) {
    }
}
