package com.vitu.omni.database.storage.cache;

import com.vitu.omni.database.storage.model.base.BaseEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CacheManager implements Runnable {

    private List<Cache> cacheList;

    public CacheManager() {
        cacheList = new ArrayList<>();
    }

    public void addCache(Cache cache) {
        this.cacheList.add(cache);
    }

    public void stop() {
        for (Cache<BaseEntity> cache : cacheList)
            if (cache instanceof SaveCache)
                for (BaseEntity entity : cache.getCached())
                    cache.unloaded(entity);

        //Apenas para testar se esta tudo funcionando...
        //Se alguma entidade tiver 2 ou mais referencias, algo
        //Depois do clear, algo está erraado!!!
        clear();
        clear();
        clear();
        clear();
    }

    public void clear() {
        for (Cache<BaseEntity> cache : cacheList) {
            Iterator<BaseEntity> i = cache.getCached().iterator();

            while (i.hasNext()) {
                BaseEntity entity = i.next();

                if (entity.getReferences() > 1) {
                    continue;
                }

                entity.removeReference();
                i.remove();
            }
        }
    }

    @Override
    public void run() {
        int resources = 10;

        for (Cache<BaseEntity> cache : cacheList) {
            Iterator<BaseEntity> i = cache.getCached().iterator();

            while (i.hasNext() && resources != 0) {
                BaseEntity entity = i.next();

                if (entity.getLastCheck() < 25000) continue;

                if (entity.isModified()) {
                    resources--;
                    cache.saveModified(entity);
                    continue;
                }

                if (entity.getReferences() > 1) {
                    entity.refreshLastCheck();
                    continue;
                }

                //Remove da lista
                i.remove();
                //Processo de remover do baseCache
                cache.unloaded(entity);
                resources--;
            }
        }
    }

    public List<Cache> getCached() {
        return cacheList;
    }
}
