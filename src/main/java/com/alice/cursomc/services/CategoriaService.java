package com.alice.cursomc.services;

import com.alice.cursomc.domain.Categoria;
import com.alice.cursomc.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {
    @Autowired
    private CategoriaRepository categoriaRepository;
    public Categoria buscaPorId(Integer id){
        Optional<Categoria> optional = categoriaRepository.findById(id);
        return optional.orElse(null);
    }
}
