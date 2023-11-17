package com.backend.clinicaodontologica.repository;

import com.backend.clinicaodontologica.model.Odontologo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOdontologoRepository extends JpaRepository<Odontologo, Long> {
}
