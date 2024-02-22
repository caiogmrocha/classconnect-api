package com.classconnect.classconnectapi.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
    public Perfil findByEmail(String email);
}
