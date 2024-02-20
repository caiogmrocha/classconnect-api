package com.classconnect.classconnectapi.dados;

import org.springframework.data.jpa.repository.JpaRepository;

import com.classconnect.classconnectapi.negocio.entidades.Material;

public interface MateriaisRepository extends JpaRepository<Material, Long>{

}
