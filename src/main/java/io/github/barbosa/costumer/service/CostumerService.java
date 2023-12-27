package io.github.barbosa.costumer.service;

import io.github.barbosa.costumer.model.Costumer;
import io.github.barbosa.costumer.model.UpdateCostumer;
import io.github.barbosa.costumer.repository.CostumerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CostumerService {

    private CostumerRepository costumerRepository;

    public Costumer createCostumer(Costumer costumer) {
        return costumerRepository.save(costumer);
    }

    public List<Costumer> listAllCostumers() {
        return costumerRepository.findAll();
    }

    public Costumer listCostumerById(String id) throws ChangeSetPersister.NotFoundException {
        Optional<Costumer> costumerOptional = costumerRepository.findById(id);
        if (costumerOptional.isPresent()) {
            return costumerOptional.get();
        } else {
            throw new ChangeSetPersister.NotFoundException();
        }
    }

    public Costumer updateCostumer(UpdateCostumer updateCostumer, String id) throws Exception {
        Costumer costumerToBeUpdate = costumerRepository.findById(id)
                .orElseThrow(() -> new Exception("O ID não existe no banco de dados"));
        costumerToBeUpdate.setNome(updateCostumer.getNome());
        costumerToBeUpdate.setEndereco(updateCostumer.getEndereco());
        costumerToBeUpdate.setEmail(updateCostumer.getEmail());
        costumerToBeUpdate.setTelefone(updateCostumer.getTelefone());
        return costumerRepository.save(costumerToBeUpdate);

    }
}
