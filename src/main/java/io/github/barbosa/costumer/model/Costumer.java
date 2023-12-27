package io.github.barbosa.costumer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "costumers")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Costumer {
    @Id
    private String id;
    private String nome;
    private String endereco;
    @Email(message = "O e-mail deve ser válido")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", message = "Formato de e-mail inválido")
    private String email;
    private String telefone;

    public Costumer(String nome, String endereco, String email, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
    }
}
