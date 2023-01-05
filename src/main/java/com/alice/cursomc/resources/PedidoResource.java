package com.alice.cursomc.resources;

import com.alice.cursomc.domain.Cliente;
import com.alice.cursomc.domain.Pedido;
import com.alice.cursomc.services.ClienteService;
import com.alice.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
    @Autowired
    PedidoService pedidoService;
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Pedido pedido = pedidoService.buscaPorID(id);
        return ResponseEntity.ok().body(pedido);
    }
}
