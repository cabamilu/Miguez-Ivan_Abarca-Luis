package com.backend.clinicaodontologica.repository;

import com.backend.clinicaodontologica.model.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITurnoRepository extends JpaRepository<Turno, Long> {
}
