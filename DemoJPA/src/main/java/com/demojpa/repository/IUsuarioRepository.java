package com.demojpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demojpa.models.Usuario;

public interface IUsuarioRepository  extends JpaRepository<Usuario, Integer>{

}
