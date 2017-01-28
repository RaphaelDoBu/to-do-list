package ufcg.edu.br.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufcg.edu.br.domain.ListName;
import ufcg.edu.br.domain.Task;
import ufcg.edu.br.repositories.ListNameRepository;
import ufcg.edu.br.repositories.TaskRepository;

@Service("listNameService")
public class ListNameServiceImpl implements ListNameService{

	@Autowired
	private ListNameRepository listNameRepository;
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public int filterToDo(String filter, String typeFilter) {
		List<ListName> name = listNameRepository.findAll();
		int count = 0;
		for (ListName list : name) {
			for (int i = 0; i < list.getListTaskToDo().size(); i++) {
				if(filter.equalsIgnoreCase("priority")){
					if (typeFilter.equalsIgnoreCase(list.getListTaskToDo().get(i).getPriority())) {
						count ++;
					}
				}
				else{
					if (typeFilter.equalsIgnoreCase(list.getListTaskToDo().get(i).getCategory())) {
						count ++;
					}
				}
			}
		}
		return count;
	}

	@Override
	public int filterCompleted(String filter, String typeFilter) {
		List<ListName> name = listNameRepository.findAll();
		int count = 0;
		for (ListName list : name) {
			for (int i = 0; i < list.getListTaskCompleted().size(); i++) {
				if(filter.equalsIgnoreCase("priority")){
					if (typeFilter.equalsIgnoreCase(list.getListTaskCompleted().get(i).getPriority())) {
						count ++;
					}
				}
				else{
					if (typeFilter.equalsIgnoreCase(list.getListTaskCompleted().get(i).getCategory())) {
						count ++;
					}
				}
			}
		}
		return count;
	}
	
	@Override
	public Task taskCompleted(Long id, Long idTask) {
		ListName listName = listNameRepository.findOne(id);
		Task achou = null;
		for(Task list: listName.getListTaskToDo()){
			if(list.getId().equals(idTask)){
				achou = list;
			}
		}
		listName.getListTaskToDo().remove(achou);
		listName.getListTaskCompleted().add(achou);
		listNameRepository.save(listName);
		return achou;
	}
	
	@Override
	public Task taskSearch(Long id, Long idTask){
		ListName listName = listNameRepository.findOne(id);
		Task achou = null;
		for(Task list: listName.getListTaskToDo()){
			if(list.getId().equals(idTask)){
				achou = list;
			}
		}
		return achou;
	}

	@Override
	public void taskUpdate(Long id, Long idTask, Task taskUpdate) {
		ListName listName = listNameRepository.findOne(id);
		Task achou = null;
		int index= 0;
		for (int i = 0; i < listName.getListTaskToDo().size(); i++) {
			if(listName.getListTaskToDo().get(i).getId().equals(idTask)){
				listName.getListTaskToDo().get(i).setCategory(taskUpdate.getCategory());
				listName.getListTaskToDo().get(i).setDescription(taskUpdate.getDescription());
				listName.getListTaskToDo().get(i).setPriority(taskUpdate.getPriority());
				listName.getListTaskToDo().get(i).setTitle(taskUpdate.getTitle());
				listNameRepository.save(listName);
				System.out.println("entrou aq");
			}
		}
	}

	@Override
	public void removeTask(Long id, Long idTask) {
		ListName listName = listNameRepository.findOne(id);
		Task achou = null;
		for(Task list: listName.getListTaskToDo()){
			if(list.getId().equals(idTask)){
				achou = list;
			}
		}
		listName.getListTaskToDo().remove(achou);
		taskRepository.delete(idTask);
		listNameRepository.save(listName);
	}

	@Override
	public void removeAllTaskToDo(Long id) {
		ListName listName = listNameRepository.findOne(id);
		for(Task list: listName.getListTaskToDo()){
			taskRepository.delete(list);
		}
		listName.getListTaskToDo().clear();
	}
	
	@Override
	public void removeAllTaskCompleted(Long id) {
		ListName listName = listNameRepository.findOne(id);
		for(Task list: listName.getListTaskCompleted()){
			taskRepository.delete(list);
		}
		listName.getListTaskCompleted().clear();
	}
	
}
