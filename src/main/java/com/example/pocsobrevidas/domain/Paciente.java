package com.example.pocsobrevidas.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Paciente {
    private LocalDateTime dataNascimento;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
