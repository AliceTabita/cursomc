package com.alice.cursomc.services;

import com.alice.cursomc.domain.Categoria;
import com.alice.cursomc.domain.Cidade;
import com.alice.cursomc.domain.Cliente;
import com.alice.cursomc.domain.Endereco;
import com.alice.cursomc.domain.enums.TipoCliente;
import com.alice.cursomc.dto.CategoriaDTO;
import com.alice.cursomc.dto.ClienteCadDTO;
import com.alice.cursomc.dto.ClienteDTO;
import com.alice.cursomc.repositories.ClienteRepository;
import com.alice.cursomc.repositories.EnderecoRepository;
import com.alice.cursomc.services.exception.DataIntegrityException;
import com.alice.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EnderecoRepository enderecoRepository;

    public Cliente buscaPorId(Integer id){
        Optional<Cliente> optional = clienteRepository.findById(id);
        return optional.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id:" + id + ", Tipo: " + Cliente.class.getName()));
    }
    public List<Cliente> listar(){
        return clienteRepository.findAll();
    }
    @Transactional
    public Cliente cadastrar(Cliente cliente){
        cliente.setId(null);
        cliente = clienteRepository.save(cliente);
        enderecoRepository.saveAll(cliente.getEnderecos());
        return cliente;
    }
    public Cliente atualizar(Cliente cliente){
        Cliente clienteAnt = buscaPorId(cliente.getId());
        clienteAnt.setNome(cliente.getNome());
        clienteAnt.setEmail(cliente.getEmail());
        return clienteRepository.save(clienteAnt);
    }
    public void excluir(Integer id){
        buscaPorId(id);
        try {
            clienteRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível deletar um cliente que possui pedidos");
        }

    }
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return clienteRepository.findAll(pageRequest);
    }
    public Cliente fromDTO(ClienteDTO clienteDTO){
        return new Cliente(clienteDTO.getId(),clienteDTO.getNome(),clienteDTO.getEmail(),null,null);
    }
    public Cliente fromDTO(ClienteCadDTO clienteCadDTO){
        Cliente cli = new Cliente(null,clienteCadDTO.getNome(),clienteCadDTO.getEmail(),clienteCadDTO.getCpfOuCnpj(), TipoCliente.toEnum(clienteCadDTO.getTipoCliente()));
        Cidade cid = new Cidade(clienteCadDTO.getCidadeId(),null,null );
        Endereco end = new Endereco(null,clienteCadDTO.getLogradouro(), clienteCadDTO.getNumero(), clienteCadDTO.getComplemento(), clienteCadDTO.getBairro(), clienteCadDTO.getCep(), cli,cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(clienteCadDTO.getTelefone1());
        if(clienteCadDTO.getTelefone2()!=null){
            cli.getTelefones().add(clienteCadDTO.getTelefone2());
        }
        if(clienteCadDTO.getTelefone3()!=null){
            cli.getTelefones().add(clienteCadDTO.getTelefone3());
        }
        return cli;
    }


}
