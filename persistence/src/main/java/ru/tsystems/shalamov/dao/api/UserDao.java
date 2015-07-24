package ru.tsystems.shalamov.dao.api;

import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.entities.User;

/**
 * Created by viacheslav on 24.07.2015.
 */
public interface UserDao {
    User findByusername(String username) throws DataAccessLayerException;
}
