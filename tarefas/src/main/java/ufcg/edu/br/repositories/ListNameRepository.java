package ufcg.edu.br.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ufcg.edu.br.domain.ListName;

public interface ListNameRepository extends CrudRepository<ListName, Long>{
    List<ListName> findAll();

}
