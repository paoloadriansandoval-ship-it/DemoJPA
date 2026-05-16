package com.demojpa;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demojpa.models.Categoria;
import com.demojpa.repository.ICategoriaRepository;

@SpringBootApplication
public class DemoJpaApplication implements CommandLineRunner{
	
	@Autowired
	private ICategoriaRepository repoCategoria;

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaApplication.class, args);
	}
	
	private void testConexion() {
		if (repoCategoria != null)
			System.out.println("conexion exitosa: " +repoCategoria);
		else
			System.out.println("conexion fallida");
	}
	
	private void guardar() {
		Categoria categoria = new Categoria();
		categoria.setNombre("Pueblos");
		categoria.setDescripcion("Viajes a los pueblos de este hermoso país");
		repoCategoria.save(categoria);
	}
	private void buscarPorId() {
		Optional <Categoria> optional = repoCategoria.findById(1);
		if (optional.isPresent())
			System.out.println(optional.get().getNombre());
		else
			System.out.println("Categoria no encontrada");
	}
	private void modificar() {
		Optional <Categoria> optional = repoCategoria.findById(1);
		if (optional.isPresent()) {
			Categoria CatTemp = new Categoria();
			CatTemp = optional.get();
			CatTemp.setNombre("Volcanes");
			CatTemp.setDescripcion("Largas y exigentes caminatas por los volcanes de este hermoso país");
			repoCategoria.save(CatTemp);
			System.out.println(optional.get());
		}else
			System.out.println("Categoria no encontrada");
	}
	private void eliminarPorId() {
		repoCategoria.deleteById(1);
	}
	
	private void cantidadCategorias() {
		long cantidad = repoCategoria.count();
		System.out.println("Hay categorias " + cantidad + " actualmente.");
	}
	private void eliminarTodo() {
		repoCategoria.deleteAll();
	}
	private void encontrarPorIds() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(2);
		Iterable<Categoria> categoria = repoCategoria.findAllById(ids);
		for (Categoria cat:categoria)
			System.out.println(cat.getNombre() + "" + cat.getDescripcion());
	}
	private void encontrarTodos() {
		Iterable<Categoria> categoria = repoCategoria.findAll();
		for (Categoria cat:categoria)
			System.out.println(cat.getNombre() + "" + cat.getDescripcion());
	}
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		modificar();
	}

}
