package com.alice.cursomc.services;

import com.alice.cursomc.domain.Categoria;
import com.alice.cursomc.domain.Cliente;
import com.alice.cursomc.repositories.ClienteRepository;
import com.alice.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;

    public Cliente buscaPorId(Integer id){
        Optional<Cliente> optional = clienteRepository.findById(id);
        return optional.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
    }

}
