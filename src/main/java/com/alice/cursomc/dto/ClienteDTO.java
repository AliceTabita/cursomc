package com.alice.cursomc.dto;

import com.alice.cursomc.domain.Cliente;
import com.alice.cursomc.services.validation.ClienteUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
@ClienteUpdate
public class ClienteDTO implements Serializable {
    @Serial
    private static final long serialVersionUID=1L;
    private Integer id;
    @NotEmpty(message = "Preenchimento Obrigatório")
    @Length(min=5,max=80,message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;
    @NotEmpty(message = "Preenchimento Obrigatório")
    @Email(message="Email inválido")
    private String email;

    public ClienteDTO() {
    }

    public ClienteDTO(Cliente cliente) {
        this.id = cliente.getId();
        this.nome = cliente.getNome();
        this.email= cliente.getEmail();
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    public static List<ClienteDTO> converter(List<Cliente> clientes) {
        return clientes.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }
}