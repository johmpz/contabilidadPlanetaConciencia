package com.planeta.conciencia.contabilidad.repository;

import com.planeta.conciencia.contabilidad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
