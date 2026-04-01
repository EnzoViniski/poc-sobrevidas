package com.example.pocsobrevidas.service;

import com.example.pocsobrevidas.domain.Paciente;
import com.example.pocsobrevidas.repository.PacienteRepository;
import com.example.pocsobrevidas.util.PacienteCreator;
import com.example.pocsobrevidas.util.PacienteNewPostRequestBodyCreator;
import com.example.pocsobrevidas.util.PacientePutRequestBodyCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class PacienteServiceTest {
    @InjectMocks
    private PacienteService pacienteService;
    @Mock
    private PacienteRepository pacienteRepositoryMock;

    @BeforeEach
    void setUp() {
        List<Paciente> pacienteList = List.of(PacienteCreator.createValidPaciente());
        BDDMockito.when(pacienteRepositoryMock.findAll())
                .thenReturn(pacienteList);

        BDDMockito.when(pacienteRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(PacienteCreator.createValidPaciente()));

        BDDMockito.when(pacienteRepositoryMock.findByCpf(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(PacienteCreator.createValidPaciente()));

        BDDMockito.when(pacienteRepositoryMock.save(ArgumentMatchers.any(Paciente.class)))
                .thenReturn(PacienteCreator.createValidPaciente());

        BDDMockito.doNothing().when(pacienteRepositoryMock).delete(ArgumentMatchers.any(Paciente.class));

        BDDMockito.when(pacienteRepositoryMock.saveAll(ArgumentMatchers.anyIterable()))
                .thenReturn(pacienteList);
    }

    @Test
    @DisplayName("ListAll returns list of paciente when successful")
    void listAll_ReturnsListOfPaciente_WhenSuccessful() {
        String expectedName = PacienteCreator.createValidPaciente().getNome();
        List<Paciente> pacienteList = pacienteService.listAll();
        Assertions.assertNotNull(pacienteList);
        Assertions.assertFalse(pacienteList.stream().toList().isEmpty());
        Assertions.assertEquals(expectedName, pacienteList.stream().toList().getFirst().getNome());
    }

    @Test
    @DisplayName("FindById returns paciente when successful")
    void findById_ReturnsPaciente_WhenSuccessful() {
        Long expectedId = PacienteCreator.createValidPaciente().getId();
        Paciente paciente = pacienteService.findById(1);
        Assertions.assertNotNull(paciente);
        Assertions.assertNotNull(paciente.getId());
        Assertions.assertEquals(paciente.getId(), expectedId);
    }

    @Test
    @DisplayName("FindByCpf returns paciente when successful")
    void findByCpf_ReturnsPaciente_WhenSuccessful() {
        String expectedCpf = PacienteCreator.createValidPaciente().getCpf();
        Paciente paciente = pacienteService.findByCpf("09113744186");
        Assertions.assertNotNull(paciente);
        Assertions.assertNotNull(paciente.getCpf());
        Assertions.assertEquals(paciente.getCpf(), expectedCpf);
    }

    @Test
    @DisplayName("Save returns paciente when successful")
    void save_ReturnsPaciente_WhenSuccessful() {
        Long expectedId = PacienteCreator.createValidPaciente().getId();
        Paciente paciente = pacienteService.save(PacienteNewPostRequestBodyCreator.createPacientePostRequestBody());
        Assertions.assertNotNull(paciente);
        Assertions.assertEquals(paciente.getId(), expectedId);
    }

    @Test
    @DisplayName("Replace updates paciente when successful")
    void replace_UpdatesPaciente_WhenSuccessful() {
        Assertions.assertDoesNotThrow(() -> pacienteService.replace(1L, PacientePutRequestBodyCreator.createPacientePutRequestBody()));
    }

    @Test
    @DisplayName("Delete removes paciente when successful")
    void delete_RemovesPaciente_WhenSuccessful() {
        Assertions.assertDoesNotThrow(() -> pacienteService.delete(1L));

    }

    @Test
    @DisplayName("ImportarCsv imports file when successful")
    void importarCsv_ImportsFile_WhenSuccesful() {
        String csvContent = "cabecalho\n" +
                "\"2007-04-07 00:00:00\",,\"09113744186\",\"74333270\",,,,,,\"viniskienzo@gmail.com\",\"Enzo Viniski\",\"MASCULINO\",\"Av Afonso Pena\",\"123\",\"GO\",\"Goiânia\",\"false\",\"false\",\"false\",\"Setor Jardim Planalto\",\"true\"\n";
        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "teste.csv",
                "text/csv",
                csvContent.getBytes()
        );
        Assertions.assertDoesNotThrow(() -> pacienteService.importarCsv(mockFile));
        Mockito.verify(pacienteRepositoryMock, Mockito.times(1)).saveAll(ArgumentMatchers.anyIterable());

    }

    @Test
    @DisplayName("limparDado returns null when input is null, 'NULL' or empty quotes")
    void limparDado_ReturnsNull_WhenInputIsValid(){
        Assertions.assertNull(pacienteService.limparDado(null));
        Assertions.assertNull(pacienteService.limparDado("NULL"));
        Assertions.assertNull(pacienteService.limparDado("\"\""));
    }

    @Test
    @DisplayName("limparDado removes quotes and returns clean string when input is valid")
    void limparDado_ReturnsCleanString_WhenInputIsValid(){
        String inputComAspas = "\"Enzo Viniski\"";
        Assertions.assertEquals("Enzo Viniski", pacienteService.limparDado(inputComAspas));
        String inputLimpo = "Goiânia";
        Assertions.assertEquals("Goiânia", pacienteService.limparDado(inputLimpo));
    }
}