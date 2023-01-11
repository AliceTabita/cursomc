package com.alice.cursomc.resources;

import com.alice.cursomc.domain.Cliente;
import com.alice.cursomc.dto.ClienteCadDTO;
import com.alice.cursomc.dto.ClienteDTO;
import com.alice.cursomc.services.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    ClienteService clienteService;
    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Cliente cliente = clienteService.buscaPorId(id);
        return ResponseEntity.ok().body(cliente);
    }
    @GetMapping
    public ResponseEntity<List<ClienteDTO>>list(){
        List<Cliente> clientes =clienteService.listar();
        return ResponseEntity.ok().body(ClienteDTO.converter(clientes));
    }
    @PostMapping
    public ResponseEntity<Cliente> insert(@RequestBody @Valid ClienteCadDTO clienteCadDTO){
        Cliente cliente=clienteService.fromDTO(clienteCadDTO);
        clienteService.cadastrar(cliente);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid ClienteDTO clienteDTO,@PathVariable Integer id){
        clienteDTO.setId(id);
        Cliente cliente= clienteService.fromDTO(clienteDTO);
        clienteService.atualizar(cliente);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){

        clienteService.excluir(id);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/page")
    public ResponseEntity<Page<ClienteDTO>>findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "linesPerPage",defaultValue = "24") Integer linesPerPage,
                                                      @RequestParam(value = "orderBy",defaultValue = "nome")String orderBy,
                                                      @RequestParam(value = "direction",defaultValue = "ASC")String direction){
        Page<Cliente> clientePage =clienteService.findPage(page,linesPerPage,orderBy,direction);
        Page<ClienteDTO> clienteDTOPage=clientePage.map(ClienteDTO::new);
        return ResponseEntity.ok().body(clienteDTOPage);
    }

}
