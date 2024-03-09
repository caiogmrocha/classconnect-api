package com.classconnect.classconnectapi.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.Matricula;

public interface MatriculasRepository extends JpaRepository<Matricula, Long>{

}
