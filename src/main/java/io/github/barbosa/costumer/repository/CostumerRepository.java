package io.github.barbosa.costumer.repository;

import io.github.barbosa.costumer.model.Costumer;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface CostumerRepository extends MongoRepository<Costumer, String> {
}
