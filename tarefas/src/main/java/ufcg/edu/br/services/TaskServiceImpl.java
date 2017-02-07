package ufcg.edu.br.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufcg.edu.br.domain.ListTask;
import ufcg.edu.br.domain.SubTask;
import ufcg.edu.br.domain.Task;
import ufcg.edu.br.repositories.ListTaskRepository;
import ufcg.edu.br.repositories.SubTaskRepository;
import ufcg.edu.br.repositories.TaskRepository;

@Service("taskService")
public class TaskServiceImpl implements TaskService{

	@Autowired
	private ListTaskRepository listTaskRepository;
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public void newTask(Task task, Long id) {
		taskRepository.save(task);
		ListTask listaName = listTaskRepository.findOne(id);
		listaName.getListTaskToDo().add(task);
	}
	
	@Override
	public int filterToDo(String filter, String typeFilter) {
		List<ListTask> name = listTaskRepository.findAll();
		int count = 0;
		for (ListTask list : name) {
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
		List<ListTask> name = listTaskRepository.findAll();
		int count = 0;
		for (ListTask list : name) {
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
	public Task taskToDoForCompleted(Long id, Long idTask) {  
		ListTask listTask = listTaskRepository.findOne(id);
		Task achou = taskRepository.findOne(idTask);
		listTask.getListTaskToDo().remove(achou);
		listTask.getListTaskCompleted().add(achou);
		return achou;
	}
	
	@Override
	public Task taskCompletedForToDo(Long id, Long idTask) {
		ListTask listTask = listTaskRepository.findOne(id);
		Task achou = taskRepository.findOne(idTask);
		listTask.getListTaskCompleted().remove(achou);
		listTask.getListTaskToDo().add(achou);
		return achou;
	}
	
	@Override
	public Task taskSearch(Long id, Long idTask){
		Task achou = taskRepository.findOne(idTask);
		return achou;
	}

	@Override
	public void taskUpdate(Long id, Long idTask, Task taskUpdate) {
		Task achou = taskRepository.findOne(idTask);
		achou.setCategory(taskUpdate.getCategory());
		achou.setDescription(taskUpdate.getDescription());
		achou.setPriority(taskUpdate.getPriority());
		achou.setTitle(taskUpdate.getTitle());
	}

	@Override
	public void removeTaskToDo(Long id, Long idTask) {
		ListTask listTask = listTaskRepository.findOne(id);
		Task achou = taskRepository.findOne(idTask);
		listTask.getListTaskToDo().remove(achou);
		taskRepository.delete(idTask);

	}

	@Override
	public void removeTaskCompleted(Long id, Long idTask) {
		ListTask listTask = listTaskRepository.findOne(id);
		Task achou = taskRepository.findOne(idTask);
		listTask.getListTaskCompleted().remove(achou);
		taskRepository.delete(idTask);
	}
	
	@Override
	public void removeAllTaskToDo(Long id) {
		ListTask listTask = listTaskRepository.findOne(id);
		for(Task list: listTask.getListTaskToDo()){
			taskRepository.delete(list);
		}
		listTask.getListTaskToDo().clear();
	}
	
	@Override
	public void removeAllTaskCompleted(Long id) {
		ListTask listTask = listTaskRepository.findOne(id);
		for(Task list: listTask.getListTaskCompleted()){
			taskRepository.delete(list);
		}
		listTask.getListTaskCompleted().clear();
	}

	@Override
	public void removeAllList(List<ListTask> listTask) {
		for(ListTask listT: listTask){
			removeAllTaskToDo(listT.getId());
			removeAllTaskCompleted(listT.getId());
			listTaskRepository.delete(listT);
		}
	}

}
