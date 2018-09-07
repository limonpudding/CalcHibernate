package app.security;

import app.database.entities.Roles;
import app.database.entities.Users;
import app.database.entities.Userroles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
            roles.addAll(user.getUserroles());
            List<GrantedAuthority> grRoles = new LinkedList<>();
            for (Roles role:roles) {
                grRoles.add(new SimpleGrantedAuthority(role.toString()));
            }
            builder.authorities(grRoles);
        } else {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return builder.build();
    }
}
