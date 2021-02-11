package com.ironhack.MidTerm.repository;

import com.ironhack.MidTerm.model.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    public Optional<Admin> findByUsername(String username);
}
