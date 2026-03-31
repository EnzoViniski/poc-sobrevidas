package com.example.pocsobrevidas.util;

import com.example.pocsobrevidas.requests.PacientePutRequestBody;

public class PacientePutRequestBodyCreator {
    public static PacientePutRequestBody createPacientePutRequestBody() {
        return PacientePutRequestBody.builder()
                .id(PacienteCreator.createValidPaciente().getId())
                .nome(PacienteCreator.createValidPaciente().getNome())
                .build();
    }
}
