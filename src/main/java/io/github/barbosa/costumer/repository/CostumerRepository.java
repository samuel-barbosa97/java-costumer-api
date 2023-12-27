package io.github.barbosa.costumer.repository;

import io.github.barbosa.costumer.model.Costumer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostumerRepository extends MongoRepository<Costumer, String> {
}
