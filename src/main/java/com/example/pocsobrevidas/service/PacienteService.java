package com.example.pocsobrevidas.service;

import com.example.pocsobrevidas.domain.Paciente;
import com.example.pocsobrevidas.domain.PacienteEnum;
import com.example.pocsobrevidas.mapper.PacienteMapper;
import com.example.pocsobrevidas.repository.PacienteRepository;
import com.example.pocsobrevidas.requests.PacienteNewPostRequestBody;
import com.example.pocsobrevidas.requests.PacientePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository pacienteRepository;

    public void importarCsv(MultipartFile file) {
        String line;
        List<Paciente> dataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                Paciente paciente = new Paciente();
                String[] data = line.split(",");
                String dataString = limparDado(data[0]);
                if (dataString != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    paciente.setDataNascimento(LocalDateTime.parse(dataString, formatter));
                }
                paciente.setCpf(limparDado(data[2]));
                paciente.setCep(limparDado(data[3]));
                paciente.setNumCartaoSus(limparDado(data[4]));
                paciente.setTelefoneCelular(limparDado(data[5]));
                paciente.setTelefoneResponsavel(limparDado(data[6]));
                paciente.setNomeMae(limparDado(data[7]));
                paciente.setComplemento(limparDado(data[8]));
                paciente.setEmail(limparDado(data[9]));
                paciente.setNome(limparDado(data[10]));
                String sexoLimpo = limparDado(data[11]);
                if (sexoLimpo != null) {
                    if (sexoLimpo.equals("MASCULINO")) {
                        paciente.setSexo(PacienteEnum.MASCULINO);
                    }
                    if (sexoLimpo.equals("FEMININO")) {
                        paciente.setSexo(PacienteEnum.FEMININO);
                    }
                }
                paciente.setEndereco(limparDado(data[12]));
                String numEnderecoLimpo = limparDado(data[13]);
                if (numEnderecoLimpo != null) {
                    paciente.setNumEndereco(Integer.parseInt(numEnderecoLimpo));
                }
                paciente.setEstado(limparDado(data[14]));
                paciente.setCidade(limparDado(data[15]));
                String tabagistaLimpo = limparDado(data[16]);
                if (tabagistaLimpo != null) {
                    paciente.setEhTabagista(Boolean.parseBoolean(tabagistaLimpo));
                }
                String etilistaLimpo = limparDado(data[17]);
                if (etilistaLimpo != null) {
                    paciente.setEhEtilista(Boolean.parseBoolean(etilistaLimpo));
                }
                String temLesaoLimpo = limparDado(data[18]);
                if (temLesaoLimpo != null) {
                    paciente.setTemLesaoSuspeita(Boolean.parseBoolean(temLesaoLimpo));
                }
                paciente.setBairro(limparDado(data[19]));
                String participaSmartMonitorLimpo = limparDado(data[20]);
                if (participaSmartMonitorLimpo != null) {
                    paciente.setParticipaSmartMonitor(Boolean.parseBoolean(participaSmartMonitorLimpo));
                }
                dataList.add(paciente);
            }
            pacienteRepository.saveAll(dataList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String limparDado(String texto) {
        if (texto == null || texto.equals("NULL") || texto.equals("\"\"")) {
            return null;
        }
        String regex = "\"";
        texto = texto.replaceAll(regex, "");
        return texto;
    }

    public Paciente findById(long id){
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente não Encontrado"));
    }

    public Paciente findByCpf(String cpf){
        return pacienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Paciente não Encontrado"));
    }

    public List<Paciente> list(){
        return pacienteRepository.findAll();
    }

    public Paciente save(PacienteNewPostRequestBody pacienteNewPostRequestBody){
        return pacienteRepository.save(PacienteMapper.INSTANCE.toPaciente(pacienteNewPostRequestBody));
    }

    public void delete(long id){
        pacienteRepository.delete(findById(id));
    }

    public void replace(long id, PacientePutRequestBody pacientePutRequestBody){
        Paciente savedPaciente = findById(id);
        Paciente paciente = PacienteMapper.INSTANCE.toPaciente(pacientePutRequestBody);
        paciente.setId(savedPaciente.getId());
        pacienteRepository.save(paciente);
    }
}
