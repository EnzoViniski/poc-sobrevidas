package com.example.pocsobrevidas.controller;

import com.example.pocsobrevidas.domain.Paciente;
import com.example.pocsobrevidas.requests.PacienteNewPostRequestBody;
import com.example.pocsobrevidas.service.PacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {
    private final PacienteService pacienteService;
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> list(){
        return ResponseEntity.ok(pacienteService.list());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Paciente> findById(@PathVariable long id){
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @GetMapping(path = "/cpf/{cpf}")
    public ResponseEntity<Paciente> findByCpf(@PathVariable String cpf){
        return ResponseEntity.ok(pacienteService.findByCpf(cpf));
    }

    @PostMapping
    public ResponseEntity<Paciente> save(@RequestBody PacienteNewPostRequestBody paciente) {
        return new ResponseEntity<>(pacienteService.save(paciente), HttpStatus.CREATED) ;
    }

    @PostMapping("/importar")
    public ResponseEntity<String> importarArquivo(@RequestParam("file") MultipartFile file) {
        pacienteService.importarCsv(file);
        return ResponseEntity.ok("Arquivo importado com sucesso: " + file.getOriginalFilename() + "!");
    }
}
