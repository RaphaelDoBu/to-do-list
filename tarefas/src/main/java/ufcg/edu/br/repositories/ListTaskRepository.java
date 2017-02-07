package ufcg.edu.br.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ufcg.edu.br.domain.ListTask;

public interface ListTaskRepository extends CrudRepository<ListTask, Long>{
    List<ListTask> findAll();

}
