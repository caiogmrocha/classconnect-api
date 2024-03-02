package com.classconnect.classconnectapi.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.Professor;

public interface ProfessoresRepository extends JpaRepository<Professor, Long> {
}
