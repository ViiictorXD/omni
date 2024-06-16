package com.vitu.omni.database.storage.model.base;

import com.vitu.omni.database.SqlConnection;
import com.vitu.omni.database.storage.cache.Cache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseRepository<E extends BaseEntity,
        O extends DTO,
        D extends DAO<O>,
        C extends Cache<E>,
        L extends Loader<E, O>>
        implements Repository<E> {

    protected final SqlConnection connection;
    protected D dao;
    protected C cache;
    protected L loader;

    public BaseRepository(SqlConnection connection) throws SQLException {
        this.connection = connection;
    }

    public void init() throws SQLException {
        this.dao = initDao();
        this.cache = initCache();
        this.loader = initLoader();
    }

    protected abstract D initDao() throws SQLException;

    protected abstract L initLoader();

    protected abstract C initCache();

    public synchronized E findOne(Integer id) throws SQLException {
        E e = cache.getIfMatch(id);

        if (e != null) return e;

        O o = dao.findOne(id);

        if (o == null) return null;

        return fromDTO(o);
    }

    public synchronized List<E> findByIdIn(List<Integer> ids) throws SQLException {
        List<E> list = new ArrayList<>();

        if (ids.isEmpty()) return list;

        Iterator<Integer> i = ids.iterator();

        while (i.hasNext()) {
            E e = cache.getIfMatch(i.next());

            if (e == null) continue;

            list.add(e);
            i.remove();
        }

        if (!ids.isEmpty()) list.addAll(fromDTO(dao.findByIdIn(ids)));

        return list;
    }

    public synchronized List<E> findAll() throws SQLException {
        return findByIdIn(dao.findAll());
    }

    public void save(E entity) throws SQLException {
        O dto = toDTO(entity);

        entity.setModified(false);

        dao.save(dto);
    }

    public E create(O o) throws SQLException {
        return fromDTO(dao.create(o));
    }

    protected E fromDTO(O dto) throws SQLException {
        if (dto == null) return null;

        E entity = loader.fromDTO(dto);

        entity.setModified(false);

        cache.add(entity);

        return entity;
    }

    protected List<E> fromDTO(List<O> dtos) throws SQLException {
        List<E> entities = new ArrayList<>();

        for (O dto : dtos) entities.add(fromDTO(dto));

        return entities;
    }

    protected O toDTO(E entity) throws SQLException {
        return loader.toDTO(entity);
    }

    public void delete(E entity) throws SQLException {
        dao.delete(entity.getId());
    }

    public C getCache() {
        return cache;
    }
}
