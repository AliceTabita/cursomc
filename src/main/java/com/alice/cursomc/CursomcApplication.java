package com.alice.cursomc;

import com.alice.cursomc.domain.*;
import com.alice.cursomc.domain.enums.TipoCliente;
import com.alice.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;
	@Autowired
	ProdutoRepository produtoRepository;
	@Autowired
	EstadoRepository estadoRepository;
	@Autowired
	CidadeRepository cidadeRepository;
	@Autowired
	ClienteRepository clienteRepository;
	@Autowired
	EnderecoRepository enderecoRepository;
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritório");
		Produto p1=new Produto(null,"Computador",2000.00);
		Produto p2=new Produto(null,"Impressora",800.00);
		Produto p3=new Produto(null,"Mouse",80.00);

		Estado est1= new Estado(null,"Minas Gerais");
		Estado est2=new Estado(null,"São Paulo");
		Cidade c1=new Cidade(null,"Uberlândia",est1);
		Cidade c2=new Cidade(null, "São Paulo",est2);
		Cidade c3=new Cidade(null,"Campinas",est2);

		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		est1.getCidades().addAll(Arrays.asList(c1));

		est2.getCidades().addAll(Arrays.asList(c2,c3));

		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));

		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));

		Cliente cli1=new Cliente(null,"Maria Silva","maria@gmail.com","36378912377", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));

		Endereco e1=new Endereco(null,"Rua Flores","300","Apto 203","Jardim","38220834",cli1,c1);
		Endereco e2= new Endereco(null,"Avenida Matos","105","Sala 800","Centro","38777012",cli1,c2);
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));

	}
}
