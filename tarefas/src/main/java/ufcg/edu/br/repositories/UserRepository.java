package ufcg.edu.br.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ufcg.edu.br.domain.Task;
import ufcg.edu.br.domain.User;


public interface UserRepository extends CrudRepository<User, Long> {

	
}
