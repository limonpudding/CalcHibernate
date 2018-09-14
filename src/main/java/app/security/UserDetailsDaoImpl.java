package app.security;

import app.database.entities.Users;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsDaoImpl implements UserDetailsDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Users findUserByUsername(String username) {
        return sessionFactory.getCurrentSession().get(Users.class,username);
    }
}
