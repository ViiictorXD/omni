package com.omni.minecraft.repository;

import com.omni.minecraft.api.user.User;
import com.omni.minecraft.database.storage.model.named.*;
import com.vitu.omni.database.storage.model.named.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class UserRepository extends NamedRepository<User,
  UserRepository.UserDTO,
  UserRepository.UserDAO,
  UserRepository.UserCache,
  UserRepository.UserLoader> {

  public UserRepository(SqlConnection connection) throws SQLException {
    super(connection);
  }

  @Override
  protected UserDAO initDao() throws SQLException {
    return new UserDAO(connection);
  }

  @Override
  protected UserLoader initLoader() {
    return new UserLoader();
  }

  @Override
  protected UserCache initCache() {
    return new UserCache(this);
  }

  public User findOne(UUID uuid) throws SQLException {
    User user = cache.getIfMatch(uuid);

    if (user != null) return user;

    UserDTO dto = dao.findOne(uuid);

    return fromDTO(dto);
  }

  public User findOrCreate(UUID uuid) throws SQLException {
    User user = findOne(uuid);

    if (user != null) return user;

    UserDTO dto = new UserDTO();

    dto.uniqueId = uuid;

    user = create(dto);

    return user;
  }

  class UserDTO extends NamedDTO {

    private UUID uniqueId;
  }

  class UserDAO extends NamedDAO<UserDTO> {

    private final String findOneByUUID = "SELECT id FROM omni_users WHERE uniqueId = ?";

    protected UserDAO(SqlConnection connection) throws SQLException {
      super(connection, "omni_users", UserDTO.class);
    }

    public UserDTO findOne(UUID uniqueId) throws SQLException {
      PreparedStatement statement = prepareStatement(findOneByUUID);
      statement.setString(1, uniqueId.toString());

      ResultSet result = statement.executeQuery();

      UserDTO dto = result.next() ? fromResult(result) : null;

      result.close();

      return dto;
    }

    @Override
    protected UserDTO newInstance() {
      return new UserDTO();
    }
  }

  class UserCache extends NamedCache<User, UserRepository> {

    public UserCache(UserRepository repository) {
      super(repository);
    }

    public User getIfMatch(UUID uniqueId) {
      for (User user : getCached()) {
        if (user.getUniqueId().equals(uniqueId)) {
          return user;
        }
      }
      return null;
    }
  }

  class UserLoader extends NamedLoader<User, UserDTO> {

    @Override
    public User newEntity() {
      return new User();
    }

    @Override
    public UserDTO newDTO() {
      return new UserDTO();
    }

    @Override
    public User fromDTO(UserDTO dto) throws SQLException {
      User entity = super.fromDTO(dto);

      entity.setName(dto.name);
      entity.setUniqueId(dto.uniqueId);

      return entity;
    }

    @Override
    public UserDTO toDTO(User entity) throws SQLException {
      UserDTO dto = super.toDTO(entity);

      dto.uniqueId = entity.getUniqueId();

      return dto;
    }
  }
}
