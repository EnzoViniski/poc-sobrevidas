package com.example.pocsobrevidas.util;

import com.example.pocsobrevidas.domain.Paciente;
import com.example.pocsobrevidas.requests.PacienteNewPostRequestBody;

public class PacienteNewPostRequestBodyCreator {
    public static PacienteNewPostRequestBody createPacientePostRequestBody() {
        return PacienteNewPostRequestBody.builder()
                .nome(PacienteCreator.createPacienteToBeSaved().getNome())
                .build();
    }
}
