package com.classconnect.classconnectapi.negocio.entidades;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Professor extends Perfil {
    @OneToMany(mappedBy = "professor")
    private List<Sala> salas;
}
