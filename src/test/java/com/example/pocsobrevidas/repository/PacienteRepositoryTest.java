package com.example.pocsobrevidas.repository;

import com.example.pocsobrevidas.domain.Paciente;
import com.example.pocsobrevidas.util.PacienteCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Test for Paciente Repository")
class PacienteRepositoryTest {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Test
    @DisplayName("Save persists paciente when successful")
    void save_PersistPaciente_WhenSuccessful() {
        Paciente pacienteToBeSaved = PacienteCreator.createPacienteToBeSaved();
        Paciente savedPaciente = this.pacienteRepository.save(pacienteToBeSaved);
        Assertions.assertNotNull(savedPaciente);
        Assertions.assertNotNull(savedPaciente.getId());
    }

    @Test
    @DisplayName("Save update paciente when successful")
    void save_UpdatePaciente_WhenSuccessful() {
        Paciente pacienteToBeSaved = PacienteCreator.createPacienteToBeSaved();
        Paciente savedPaciente = this.pacienteRepository.save(pacienteToBeSaved);
        savedPaciente.setNome("Ayme");
        Assertions.assertNotNull(savedPaciente);
        Assertions.assertNotNull(savedPaciente.getId());
        Assertions.assertEquals("Ayme", pacienteToBeSaved.getNome());
    }

    @Test
    @DisplayName("Delete removes paciente when successful")
    void delete_RemovesPaciente_WhenSuccessful() {
        Paciente pacienteToBeSaved = PacienteCreator.createPacienteToBeSaved();
        Paciente savedPaciente = this.pacienteRepository.save(pacienteToBeSaved);
        this.pacienteRepository.delete(savedPaciente);

        Optional<Paciente> pacienteOptional = this.pacienteRepository.findById(savedPaciente.getId());
        Assertions.assertTrue(pacienteOptional.isEmpty());
    }

    @Test
    @DisplayName("Find By Cpf returns paciente when successful")
    void findByCpf_ReturnsPaciente_WhenSuccessful() {
        Paciente pacienteToBeSaved = PacienteCreator.createPacienteToBeSaved();
        Paciente savedPaciente = this.pacienteRepository.save(pacienteToBeSaved);
        String cpf = savedPaciente.getCpf();
        Optional<Paciente> paciente = this.pacienteRepository.findByCpf(cpf);
        Assertions.assertTrue(paciente.isPresent());
    }

    @Test
    @DisplayName("Find By Cpf returns empty paciente when no paciente is found")
    void findByCpf_ReturnsEmptyPaciente_WhenNoPacienteIsFound() {
        Optional<Paciente> paciente = this.pacienteRepository.findByCpf("000000000");
        Assertions.assertTrue(paciente.isEmpty());
    }

    @Test
    @DisplayName("Find By Id returns paciente when successful")
    void findById_ReturnsPaciente_WhenSuccessful() {
        Paciente pacienteToBeSaved = PacienteCreator.createPacienteToBeSaved();
        Paciente savedPaciente = this.pacienteRepository.save(pacienteToBeSaved);
        Long id = savedPaciente.getId();
        Optional<Paciente> paciente = this.pacienteRepository.findById(id);
        Assertions.assertFalse(paciente.isEmpty());
    }

    @Test
    @DisplayName("Find By Id returns empty paciente when no paciente is found")
    void findById_ReturnsEmptyPaciente_WhenNoPacienteIsFound() {
        Optional<Paciente> paciente = this.pacienteRepository.findById(0L);
        Assertions.assertTrue(paciente.isEmpty());
    }
}