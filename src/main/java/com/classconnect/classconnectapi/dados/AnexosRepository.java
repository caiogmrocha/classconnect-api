package com.classconnect.classconnectapi.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.Anexo;

public interface AnexosRepository extends JpaRepository<Anexo, Long> {

}
