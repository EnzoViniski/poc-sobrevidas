package com.example.pocsobrevidas.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Paciente {
    private LocalDate dataNascimento;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String cep;
    private String numCartaoSus;
    private String telefoneCelular;
    private String telefoneResponsavel;
    private String complemento;
    private String email;
    private String nome;
    @Enumerated(EnumType.STRING)
    private PacienteEnum sexo;
    private String endereco;
    private int numEndereco;
    private String estado;
    private String cidade;
    private boolean ehTabagista;
    private boolean ehEtilista;
    private boolean temLesaoSuspeita;
    private String bairro;
    private boolean participaSmartMonitor;
}
