package com.classconnect.classconnectapi.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.classconnect.classconnectapi.negocio.entidades.Professor;

public interface PerfilRepository extends JpaRepository<Professor, Long> {
    public UserDetails findByEmail(String email);
}
