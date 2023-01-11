package com.alice.cursomc.services;

import com.alice.cursomc.domain.Categoria;
import com.alice.cursomc.dto.CategoriaDTO;
import com.alice.cursomc.repositories.CategoriaRepository;
import com.alice.cursomc.services.exception.DataIntegrityException;
import com.alice.cursomc.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;
    public List<Categoria> listar(){
        List<Categoria> categorias = categoriaRepository.findAll();
        return categorias;
    }
    public Categoria buscaPorId(Integer id){

        Optional<Categoria> optional = categoriaRepository.findById(id);

        return optional.orElseThrow(()->new ObjectNotFoundException("Objeto não encontrado! Id:" + id + ", Tipo: " + Categoria.class.getName()));

    }
    public Categoria cadastrar(Categoria categoria){
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }
    public Categoria atualizar(Categoria categoria){
        Categoria categoriaAnt = buscaPorId(categoria.getId());
        categoriaAnt.setNome(categoria.getNome());
        return categoriaRepository.save(categoriaAnt);
    }
    public void excluir(Integer id){
        buscaPorId(id);
        try {
            categoriaRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("Não é possível deletar uma categoria que possui produtos");
        }

    }
    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page,linesPerPage, Sort.Direction.valueOf(direction),orderBy);
        return categoriaRepository.findAll(pageRequest);
    }
    public Categoria fromDTO(CategoriaDTO categoriaDTO){
        return new Categoria(categoriaDTO.getId(),categoriaDTO.getNome());
    }

}
