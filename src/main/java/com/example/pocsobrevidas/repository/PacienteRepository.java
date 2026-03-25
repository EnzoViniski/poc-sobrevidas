package com.example.pocsobrevidas.repository;

import com.example.pocsobrevidas.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
