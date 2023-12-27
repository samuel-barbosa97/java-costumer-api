package io.github.barbosa.costumer.controller;

import io.github.barbosa.costumer.model.Costumer;
import io.github.barbosa.costumer.model.UpdateCostumer;
import io.github.barbosa.costumer.service.CostumerService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/costumers")
@AllArgsConstructor
public class CostumerController {

    private CostumerService costumerService;

    private boolean isValidId(String id) {
        return id.matches("[a-zA-Z0-9]+");
    }

    private static final Logger logger = LoggerFactory.getLogger(CostumerController.class);

    @PostMapping
    public ResponseEntity<Costumer> createCostumer(@Valid @RequestBody Costumer costumer) {
        try {
            logger.info("Recebendo solicitação para criar costumer: {}", costumer);
            Costumer createCostumer = costumerService.createCostumer(costumer);
            logger.info("Costumer criado com sucesso. ID: {}, Nome: {}", createCostumer.getId(), createCostumer.getNome());
            return ResponseEntity.status(HttpStatus.CREATED).body(createCostumer);
        } catch (Exception e) {
            logger.error("Erro ao criar costumer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Costumer>> listAllCostumers() {
        try {
            logger.info("Recebendo solicitação para listar costumers.");
            List<Costumer> costumers = costumerService.listAllCostumers();
            if (costumers.isEmpty()) {
                logger.info("Nenhum costumer encontrado");
                return ResponseEntity.noContent().build();
            } else {
                logger.info("Listagem de costumers bem-sucedida. Total de costumers: {}", costumers.size());
                return ResponseEntity.status(HttpStatus.OK).body(costumers);
            }
        } catch (Exception e) {
            logger.error("Erro ao listar costumers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Costumer> listCostumerById(@PathVariable("id") String id) {
        if (!isValidId(id)) {
            logger.warn("ID inválido: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            logger.info("Buscando costumer por ID: {}", id);
            Costumer costumer = costumerService.listCostumerById(id);
            logger.info("Costumer encontrado: {}", costumer);
            return ResponseEntity.status(HttpStatus.OK).body(costumer);
        } catch (ChangeSetPersister.NotFoundException e) {
            logger.warn("Costumer não encontrado com ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Erro ao buscar costumer por ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Costumer> updateCostumer(@RequestBody @Valid UpdateCostumer updateCostumer, @PathVariable("id") String id) {
        try {
            logger.info("Recebendo a solicitação para atualizar costumer com ID: {}. Dados de Atualização: {}", id, updateCostumer);
            Costumer updatedCostumer = costumerService.updateCostumer(updateCostumer, id);
            logger.info("Atualização de costumer bem-sucedida. ID:{}, Costumer Atualizado: {}", id, updatedCostumer);
            return ResponseEntity.status(HttpStatus.OK).body(updatedCostumer);
        } catch (ValidationException e) {
            logger.warn("Dados de atualização inválidos. ID: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (ChangeSetPersister.NotFoundException e) {
            logger.warn("Costumer não encontrado com ID: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Erro ao processar a solicitação de atualização de costumer. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Costumer> deleteById(@PathVariable("id") String id) {
        try {
            logger.info("Recebendo solicitação para excluir costumer com ID: {}", id);

            validateId(id);

            Optional<Costumer> costumerToDelete = costumerService.findById(id);

            if (costumerToDelete.isPresent()) {
                costumerService.deleteById(id);
                logger.info("Exclusão de costumer bem-sucedido. ID: {}, Nome: {}", costumerToDelete.get().getId(), costumerToDelete.get().getNome());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                logger.warn("Tentativa de excluir um Costumer que não existe. ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IllegalArgumentException e) {
            logger.warn("ID inválido fornecido para exclusão de Costumer. ID: {}", id);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            logger.error("Erro ao processar solicitação de exclusão de costumer. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private void validateId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID não pode ser nulo ou vazio");
        }
    }

}
