package com.example.pocsobrevidas.util;

import com.example.pocsobrevidas.domain.Paciente;

public class PacienteCreator {
    public static Paciente createPacienteToBeSaved() {
        return Paciente.builder()
                .nome("Enzo Viniski")
                .cpf("09113744186")
                .build();
    }

    public static Paciente createValidPaciente() {
        return Paciente.builder()
                .nome("Enzo Viniski")
                .id(1L)
                .cpf("09113744186")
                .build();
    }

    public static Paciente createValidUpdatedPaciente() {
        return Paciente.builder()
                .nome("Enzo Viniski")
                .id(1L)
                .cpf("11111111111")
                .build();
    }
}
