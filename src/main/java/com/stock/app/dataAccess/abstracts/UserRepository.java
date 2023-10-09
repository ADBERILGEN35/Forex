package com.stock.app.dataAccess.abstracts;

import com.stock.app.entities.concretes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    @Query("SELECT r FROM User r WHERE r.email LIKE %:email%")
    List<User> findByEmail(String email);
    @Query("SELECT r FROM User r WHERE r.userName LIKE %:userName%")
    User findByUserName(@Param("userName") String userName);

}