package com.justinmtech.user_api.repositories;

import com.justinmtech.user_api.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
