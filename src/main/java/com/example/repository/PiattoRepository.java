package com.example.repository;

import com.example.model.Piatto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.*;

@Repository
public interface PiattoRepository extends JpaRepository<Piatto, Long> {

    @Override
    @EntityGraph(attributePaths = "ingredienti")
    List<Piatto> findAll();

    @Override
    @EntityGraph(attributePaths = "ingredienti")
    Optional<Piatto> findById(Long id);

    @EntityGraph(attributePaths = "ingredienti")
    List<Piatto> findByCategoriaIgnoreCase(String categoria);

    @EntityGraph(attributePaths = "ingredienti")
    @Query("select p from Piatto p")
    List<Piatto> findAllWithIngredienti();
}
