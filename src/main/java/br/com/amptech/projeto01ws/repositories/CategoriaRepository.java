package br.com.amptech.projeto01ws.repositories;


import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.amptech.projeto01ws.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}
