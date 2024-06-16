package com.vitu.omni.module.kit.repository.impl;

import com.vitu.omni.database.SqlConnection;
import com.vitu.omni.module.kit.data.KitUser;
import com.vitu.omni.module.kit.repository.KitUserRepository;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class KitUserMysqlRepository implements KitUserRepository {

  private final SqlConnection sqlConnection;

  @Override
  public KitUser findOne(UUID uniqueId) {

    return null;
  }

  @Override
  public List<KitUser> findAll() {
    return Collections.emptyList();
  }

  @Override
  public void create(KitUser user) {

  }

  @Override
  public void update(KitUser user) {

  }

  @Override
  public void delete(KitUser user) {

  }

  @Override
  public void createTable() {

  }
}
