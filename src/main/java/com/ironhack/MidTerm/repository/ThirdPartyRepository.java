package com.ironhack.MidTerm.repository;

import com.ironhack.MidTerm.model.users.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
}
