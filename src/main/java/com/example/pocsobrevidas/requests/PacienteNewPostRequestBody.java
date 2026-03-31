package com.example.pocsobrevidas.requests;


import com.example.pocsobrevidas.domain.PacienteEnum;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PacienteNewPostRequestBody {
    private Long id;
    private LocalDateTime dataNascimento;
    private String cpf;
    private String cep;
    private String numCartaoSus;
    private String telefoneCelular;
    private String telefoneResponsavel;
    private String nomeMae;
    private String complemento;
    private String email;
    private String nome;
    @Enumerated(EnumType.STRING)
    private PacienteEnum sexo;
    private String endereco;
    private Integer numEndereco;
    private String estado;
    private String cidade;
    private Boolean ehTabagista;
    private Boolean ehEtilista;
    private Boolean temLesaoSuspeita;
    private String bairro;
    private Boolean participaSmartMonitor;
}
