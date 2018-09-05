package app.security;

import app.database.JDBC;
import app.database.entities.Roles;
import app.database.entities.Users;
import app.database.entities.dao.Userroles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.security.core.userdetails.User.*;


@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsDao userDetailsDao;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userDetailsDao.findUserByUsername(username);
        UserBuilder builder;
        if (user != null) {
            builder = User.withUsername(username);
            builder.password(user.getPassword());
            List<Roles> roles = new LinkedList<>();
            for (Userroles userrole : user.getUserroles()) {
                roles.add(userrole.getRole());
            }
            builder.authorities(roles::toString);
        } else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return builder.build();
    }
}
