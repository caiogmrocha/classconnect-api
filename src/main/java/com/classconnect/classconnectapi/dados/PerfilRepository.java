package com.classconnect.classconnectapi.dados;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.classconnect.classconnectapi.negocio.entidades.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    public UserDetails findByEmail(String email);
}
