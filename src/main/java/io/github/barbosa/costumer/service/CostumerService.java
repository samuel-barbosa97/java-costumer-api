package io.github.barbosa.costumer.service;

import io.github.barbosa.costumer.controller.CostumerController;
import io.github.barbosa.costumer.model.Costumer;
import io.github.barbosa.costumer.model.UpdateCostumer;
import io.github.barbosa.costumer.repository.CostumerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CostumerService {

    private CostumerRepository costumerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CostumerService.class);

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

    public Costumer updateCostumer(UpdateCostumer updateCostumer, String id) throws ChangeSetPersister.NotFoundException {
        validateUpdateCostumer(updateCostumer);

        Costumer costumerToBeUpdate = costumerRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        costumerToBeUpdate.setNome(updateCostumer.getNome());
        costumerToBeUpdate.setEndereco(updateCostumer.getEndereco());
        costumerToBeUpdate.setEmail(updateCostumer.getEmail());
        costumerToBeUpdate.setTelefone(updateCostumer.getTelefone());

        return costumerRepository.save(costumerToBeUpdate);
    }

    private void validateUpdateCostumer(UpdateCostumer updateCostumer) {
        if (updateCostumer == null) {
            throw new IllegalArgumentException("Dados de atualização não podem ser nulos");
        }
    }

    public void deleteById(String id) {
        validateId(id);
        try {
            Costumer costumerToDelete = costumerRepository.findById(id)
                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
            costumerRepository.deleteById(id);
            logger.info("Costumer excluído com sucesso. ID: {}, Nome: {}", costumerToDelete.getId(), costumerToDelete.getNome());
        } catch (ChangeSetPersister.NotFoundException e) {
            logger.warn("Tentativa de excluir um Costumer que não existe. ID: {}", id);
        } catch (Exception e) {
            logger.error("Erro ao excluir Costumer. ID: {}", id, e);
        }
    }

    private void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
        }
    }

    public Optional<Costumer> findById(String id) {
        validateId(id);
        return costumerRepository.findById(id);
    }

}
