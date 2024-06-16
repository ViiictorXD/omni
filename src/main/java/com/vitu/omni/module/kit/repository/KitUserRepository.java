package com.vitu.omni.module.kit.repository;

import com.vitu.omni.module.kit.data.KitUser;

import java.util.List;
import java.util.UUID;

public interface KitUserRepository {

  KitUser findOne(UUID uniqueId);

  List<KitUser> findAll();

  void create(KitUser user);

  void update(KitUser user);

  void delete(KitUser user);

  void createTable();
  /*
    Table Content:

    UUID | Name | KitName | PickupDate | NextPickup | PickupSize

   */
}
