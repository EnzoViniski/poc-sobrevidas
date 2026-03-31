package com.example.pocsobrevidas.controller;

import com.example.pocsobrevidas.domain.Paciente;
import com.example.pocsobrevidas.requests.PacienteNewPostRequestBody;
import com.example.pocsobrevidas.requests.PacientePutRequestBody;
import com.example.pocsobrevidas.service.PacienteService;
import com.example.pocsobrevidas.util.PacienteCreator;
import com.example.pocsobrevidas.util.PacienteNewPostRequestBodyCreator;
import com.example.pocsobrevidas.util.PacientePutRequestBodyCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@ExtendWith(SpringExtension.class)
class PacienteControllerTest {
    @InjectMocks
    private PacienteController pacienteController;
    @Mock
    private PacienteService pacienteServiceMock;

    @BeforeEach
    void setUp() {
        List<Paciente> pacienteList = List.of(PacienteCreator.createValidPaciente());
        BDDMockito.when(pacienteServiceMock.listAll())
                .thenReturn(pacienteList);

        BDDMockito.when(pacienteServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(PacienteCreator.createValidPaciente());

        BDDMockito.when(pacienteServiceMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(PacienteCreator.createValidPaciente());

        BDDMockito.when(pacienteServiceMock.save(ArgumentMatchers.any(PacienteNewPostRequestBody.class)))
                .thenReturn(PacienteCreator.createValidPaciente());

        BDDMockito.doNothing().when(pacienteServiceMock).replace(ArgumentMatchers.anyLong(), ArgumentMatchers.any(PacientePutRequestBody.class));

        BDDMockito.doNothing().when(pacienteServiceMock).delete(ArgumentMatchers.anyLong());

        BDDMockito.doNothing().when(pacienteServiceMock).importarCsv(ArgumentMatchers.any(MultipartFile.class));
    }

    @Test
    @DisplayName("ListAll returns list of paciente when successful")
    void listAll_ReturnsListOfPaciente_WhenSuccessful() {
        String expectedName = PacienteCreator.createValidPaciente().getNome();
        List<Paciente> pacienteList = pacienteController.listAll().getBody();
        Assertions.assertNotNull(pacienteList);
        Assertions.assertFalse(pacienteList.stream().toList().isEmpty());
        Assertions.assertEquals(expectedName, pacienteList.stream().toList().getFirst().getNome());
    }

    @Test
    @DisplayName("FindById returns paciente when successful")
    void findById_ReturnsPaciente_WhenSuccessful() {
        Long expectedId = PacienteCreator.createValidPaciente().getId();
        Paciente paciente = pacienteController.findById(1).getBody();
        Assertions.assertNotNull(paciente);
        Assertions.assertNotNull(paciente.getId());
        Assertions.assertEquals(paciente.getId(), expectedId);
    }

    @Test
    @DisplayName("FindByCpf returns paciente when successful")
    void findByCpf_ReturnsPaciente_WhenSuccessful() {
        String expectedCpf = PacienteCreator.createValidPaciente().getCpf();
        Paciente paciente = pacienteController.findByCpf("09113744186").getBody();
        Assertions.assertNotNull(paciente);
        Assertions.assertNotNull(paciente.getCpf());
        Assertions.assertEquals(paciente.getCpf(), expectedCpf);
    }

    @Test
    @DisplayName("Save returns paciente when successful")
    void save_ReturnsPaciente_WhenSuccessful() {
        Long expectedId = PacienteCreator.createValidPaciente().getId();
        Paciente paciente = pacienteController.save(PacienteNewPostRequestBodyCreator.createPacientePostRequestBody()).getBody();
        Assertions.assertNotNull(paciente);
        Assertions.assertEquals(paciente.getId(), expectedId);
    }

    @Test
    @DisplayName("Replace updates paciente when successful")
    void replace_UpdatesPaciente_WhenSuccessful() {
        ResponseEntity<Paciente> entity = pacienteController.replace(1L, PacientePutRequestBodyCreator.createPacientePutRequestBody());
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @DisplayName("Delete removes paciente when successful")
    void delete_RemovesPaciente_WhenSuccessful() {
        ResponseEntity<Void> entity = pacienteController.delete(1L);
        Assertions.assertNotNull(entity);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, entity.getStatusCode());
    }

    @Test
    @DisplayName("ImportarCsv imports file when successful")
    void importarCsv_ImportsFile_WhenSuccesful() {
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "teste.csv",
                "text/csv",
                "teste csv".getBytes()
        );
        ResponseEntity<String> response = pacienteController.importarArquivo(mockFile);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        String expectedMessage = "Arquivo importado com sucesso: teste.csv!";
        Assertions.assertEquals(expectedMessage, response.getBody());

    }


}