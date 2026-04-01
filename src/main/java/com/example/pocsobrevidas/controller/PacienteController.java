package com.example.pocsobrevidas.controller;

import com.example.pocsobrevidas.domain.Paciente;
import com.example.pocsobrevidas.requests.PacienteNewPostRequestBody;
import com.example.pocsobrevidas.requests.PacientePutRequestBody;
import com.example.pocsobrevidas.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @Operation(description = "Busca todos os pacientes")
    @GetMapping
    public ResponseEntity<List<Paciente>> listAll() {
        return ResponseEntity.ok(pacienteService.listAll());
    }

    @Operation(description = "Busca um paciente pelo ID")
    @GetMapping(path = "{id}")
    public ResponseEntity<Paciente> findById(@PathVariable long id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @Operation(description = "Busca um paciente pelo CPF")
    @GetMapping(path = "/cpf/{cpf}")
    public ResponseEntity<Paciente> findByCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(pacienteService.findByCpf(cpf));
    }

    @Operation(description = "Adiciona um novo paciente")
    @PostMapping
    public ResponseEntity<Paciente> save(@RequestBody PacienteNewPostRequestBody paciente) {
        return new ResponseEntity<>(pacienteService.save(paciente), HttpStatus.CREATED);
    }

    @Operation(description = "Importa o arquivo CSV pro banco de dados")
    @PostMapping(path = "/importar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importarArquivo(@RequestParam("file") MultipartFile file) {
        pacienteService.importarCsv(file);
        return ResponseEntity.ok("Arquivo importado com sucesso: " + file.getOriginalFilename() + "!");
    }

    @Operation(description = "Deleta um paciente pelo ID")
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        pacienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "Atualiza os dados de um paciente pelo ID")
    @PutMapping(path = "{id}")
    public ResponseEntity<Paciente> replace(@PathVariable long id, @RequestBody PacientePutRequestBody paciente) {
        pacienteService.replace(id, paciente);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
