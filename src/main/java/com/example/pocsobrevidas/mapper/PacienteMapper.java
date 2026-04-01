package com.example.pocsobrevidas.mapper;

import com.example.pocsobrevidas.domain.Paciente;
import com.example.pocsobrevidas.requests.PacienteNewPostRequestBody;
import com.example.pocsobrevidas.requests.PacientePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PacienteMapper {
    public static final PacienteMapper INSTANCE = Mappers.getMapper(PacienteMapper.class);

    public abstract Paciente toPaciente(PacienteNewPostRequestBody pacienteNewPostRequestBody);

    public abstract Paciente toPaciente(PacientePutRequestBody pacientePutRequestBody);
}

