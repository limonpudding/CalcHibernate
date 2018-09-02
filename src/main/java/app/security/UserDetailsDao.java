package app.security;

import app.database.entities.Users;

public interface UserDetailsDao {
    Users findUserByUsername(String username);
}
