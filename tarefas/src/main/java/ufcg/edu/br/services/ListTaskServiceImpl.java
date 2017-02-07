package ufcg.edu.br.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufcg.edu.br.domain.ListTask;
import ufcg.edu.br.repositories.ListTaskRepository;

@Service("listTaskService")
public class ListTaskServiceImpl implements ListTaskService {
	@Autowired
	private ListTaskRepository listTaskRepository;

	@Override
	public void createListTask(String name) {
		if (!name.equals("")) {
			ListTask listaName = new ListTask();
			listaName.setNome(name);
			listTaskRepository.save(listaName);
		}
	}

}
