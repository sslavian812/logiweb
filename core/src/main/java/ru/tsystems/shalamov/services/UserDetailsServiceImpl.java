package ru.tsystems.shalamov.services;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tsystems.shalamov.dao.DataAccessLayerException;
import ru.tsystems.shalamov.dao.api.UserDao;
import ru.tsystems.shalamov.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viacheslav on 24.07.2015.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userDao;

    private static final Logger LOG = Logger.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        try {
            User user = userDao.findByusername(userName);
            if (user == null) {
                throw new UsernameNotFoundException(userName);
            } else {
                return buildUserForAuthentication(user);
            }
        } catch (DataAccessLayerException e) {
            LOG.warn("Something unexpected happend.", e);
            throw new UsernameNotFoundException(userName);
        }
    }

    /**
     * Converts ru.tsystems.shalamov.entities.User user to
     * org.springframework.security.core.userdetails.User
     */
    private org.springframework.security.core.userdetails.User
    buildUserForAuthentication(ru.tsystems.shalamov.entities.User user) {
        GrantedAuthority userRole = new SimpleGrantedAuthority(user.getAuthority().name());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(userRole);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.isEnabled(),
                true, true, true, authorities);
    }
}
