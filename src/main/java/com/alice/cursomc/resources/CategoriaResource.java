package com.alice.cursomc.resources;

import com.alice.cursomc.domain.Categoria;
import com.alice.cursomc.dto.CategoriaDTO;
import com.alice.cursomc.services.CategoriaService;
import com.alice.cursomc.services.exception.DataIntegrityException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService categoriaService;
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>>list(){
        List<Categoria> categorias =categoriaService.listar();
        return ResponseEntity.ok().body(CategoriaDTO.converter(categorias));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id){
        Categoria categoria = categoriaService.buscaPorId(id);
        return ResponseEntity.ok().body(categoria);
    }
    @PostMapping
    public ResponseEntity<Categoria> insert(@RequestBody @Valid CategoriaDTO categoriaDTO){
        Categoria categoria=categoriaService.fromDTO(categoriaDTO);
        categoriaService.cadastrar(categoria);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Valid CategoriaDTO categoriaDTO,@PathVariable Integer id){
        categoriaDTO.setId(id);
        Categoria categoria= categoriaService.fromDTO(categoriaDTO);
        categoriaService.atualizar(categoria);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){

        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();

    }
    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>>findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "linesPerPage",defaultValue = "24") Integer linesPerPage,
                                                      @RequestParam(value = "orderBy",defaultValue = "nome")String orderBy,
                                                      @RequestParam(value = "direction",defaultValue = "ASC")String direction){
        Page<Categoria> categoriaPage =categoriaService.findPage(page,linesPerPage,orderBy,direction);
        Page<CategoriaDTO> categoriaDTOPage=categoriaPage.map(CategoriaDTO::new);
        return ResponseEntity.ok().body(categoriaDTOPage);
    }
}
