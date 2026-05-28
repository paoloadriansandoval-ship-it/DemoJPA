package com.demojpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demojpa.models.Trip;

public interface ITripRepository extends JpaRepository<Trip, Integer>{
	List<Trip> findByEstatus(String estatus);
    List<Trip> findByDestacadoAndEstatusOrderByIdDesc(int destacado, String estatus);

    List<Trip> findByCostoBetween(double costo1, double costo2);

    List<Trip> findByEstatusIn(String[] estatus);
}
