package com.alice.cursomc.services;

import com.alice.cursomc.domain.Cliente;
import com.alice.cursomc.domain.Pedido;
import com.alice.cursomc.repositories.PedidoRepository;

import com.alice.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    PedidoRepository pedidoRepository;
    public Pedido buscaPorID(Integer id){
        Optional<Pedido> optional = pedidoRepository.findById(id);
        return optional.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
    }
}
