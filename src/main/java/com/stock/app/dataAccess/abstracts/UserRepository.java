package com.stock.app.dataAccess.abstracts;

import com.stock.app.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}