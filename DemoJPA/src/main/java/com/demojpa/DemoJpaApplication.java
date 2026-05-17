package com.demojpa;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demojpa.models.Categoria;
import com.demojpa.models.Perfil;
import com.demojpa.models.Trip;
import com.demojpa.models.Usuario;
import com.demojpa.repository.ICategoriaRepository;
import com.demojpa.repository.IPerfilRepository;
import com.demojpa.repository.ITripRepository;
import com.demojpa.repository.IUsuarioRepository;

@SpringBootApplication
public class DemoJpaApplication implements CommandLineRunner{
	@Autowired
	private ICategoriaRepository repoCategoria;
	@Autowired
	private ITripRepository repoTrip;
	@Autowired
	private IPerfilRepository repoPerfil;	
	@Autowired
	private IUsuarioRepository repoUsuario;
	
	private void buscarTripsPorEstosEstatus() {
		String[] estatus = new String[] {"Aprobada","Reporbada"};
		List<Trip> lista = repoTrip.findByEstatusIn(estatus);
		for (Trip t : lista)
			System.out.println(t.getId() + ": " + t.getNombre() + " Estatus: " + t.getStatus());
	}
	private void buscarTripsPorEstatus() {
		List<Trip> lista = repoTrip.findByEstatus("Aprovada");
		for (Trip t : lista)
			System.out.println(t.getId() + ": " + t.getNombre() + " Estatus: " + t.getStatus());
	}
	private void Novoyaponerelnombrecompleto() {
		List<Trip> lista = repoTrip.findByDestacadoAndEstatusOrderByIdDesc(0, "Aprovada");
		for (Trip t : lista)
			System.out.println(t.getId() + ": " + t.getNombre() + " Estatus: " + t.getStatus() + " Destacado: " + t.getDestacado());
	}
	private void BuscarTripsentrecostos() {
		List<Trip> lista = repoTrip.findByCostoBetween(10, 20);
		for (Trip t : lista)
			System.out.println(t.getId() + ": " + t.getNombre() + " Costo: " + t.getCosto());
	}
	private void getusuarios() {
		Optional <Usuario> usuario = repoUsuario.findById(4);
		if (usuario.isPresent()) {
			Usuario usu = usuario.get();
			System.out.println("Usuario: " + usu.getNombre());
			System.out.println("Perfiles del Usuario: ");
			for (Perfil p: usu.getPerfiles()) {
				System.out.println(p.getNombre());
			}
		}else {
			System.out.println("Usuario sin perfiles");
		}
	}
	private void crearUsuarioconDosPerfiles() {
		Usuario usuario = new Usuario();
		usuario.setNombre("Cesar Sanchez");
		usuario.setEmail("correo@correo.com");
		usuario.setUsername("csanchez");
		usuario.setPassword("123");
		usuario.setEstatus("Activo");
		Perfil perfil1 = new Perfil();
		perfil1.setId(1);
		Perfil perfil2 = new Perfil();
		perfil2.setId(2);
		usuario.agregarPerfil(perfil1);
		usuario.agregarPerfil(perfil2);
		repoUsuario.save(usuario);
	}
	private List<Perfil> getListaPerfiles(){
		List<Perfil> lista = new LinkedList<Perfil>();
		Perfil perfil1 = new Perfil();
		perfil1.setNombre("SuperAdministrador");
		Perfil perfil2 = new Perfil();
		perfil2.setNombre("Administrador");
		Perfil perfil3 = new Perfil();
		perfil3.setNombre("Visitante");
		lista.add(perfil1);
		lista.add(perfil2);
		lista.add(perfil3);
		return lista;
	}
	private void crearperfiles() {
		repoPerfil.saveAll(getListaPerfiles());
	}
	private void guardarTrip() {
		Trip trip = new Trip();
		trip.setNombre("Caminata en la playa");
		trip.setDescripcion("Hermosa caminata en la playa de San Marcelino");
		trip.setFecha(new Date());
		trip.setCosto(15.0);
		trip.setStatus("Aprobada");
		trip.setDestacado(0);
		trip.setImagen("trip1.png");
		trip.setDetalles("Detalles del trip");
		Categoria categoria = new Categoria();
		categoria.setId(3);
		trip.setCategoria(categoria);
		repoTrip.save(trip);
	}
	private void BuscarTrips() {
		List<Trip> lista = repoTrip.findAll();
		for (Trip trip : lista)
			System.out.println(trip.getNombre() + "" + trip.getDescripcion());
	}
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
		categoria.setNombre("Playas");
		categoria.setDescripcion("Viajes a las playas de este hermoso país");
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
	private void existeId() {
		boolean existencia = repoCategoria.existsById(3);
		System.out.println("¿Existe la categoria? " + existencia);
	}
	private List<Categoria> getCategoria(){
		List<Categoria> lista = new LinkedList<Categoria>();
		Categoria cat1 = new Categoria();
		cat1.setNombre("Viaje 1");
		cat1.setDescripcion("Descripción del viaje 1");
		repoCategoria.save(cat1);
		Categoria cat2 = new Categoria();
		cat2.setNombre("Viaje 2");
		cat2.setDescripcion("Descripción del viaje 2");
		repoCategoria.save(cat2);
		lista.add(cat1);
		lista.add(cat2);
		return lista;
	}
	private void guardartodas() {
		List<Categoria> lista = getCategoria();
		repoCategoria.saveAll(lista);
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		getusuarios();
	}

}
