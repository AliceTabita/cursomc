package com.alice.cursomc.repositories;

import com.alice.cursomc.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria,Integer> {

}
