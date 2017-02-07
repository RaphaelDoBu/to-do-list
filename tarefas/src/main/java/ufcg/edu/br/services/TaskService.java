package ufcg.edu.br.services;

import java.util.List;

import ufcg.edu.br.domain.ListTask;
import ufcg.edu.br.domain.Task;

public interface TaskService {
	
	void newTask(Task task, Long id);
	int filterToDo(String filter, String typeFilter);
	int filterCompleted(String filter, String typeFilter);
	Task taskToDoForCompleted(Long id, Long idTask);
	Task taskCompletedForToDo(Long id, Long idTask);
	Task taskSearch(Long id, Long idTask);
	void taskUpdate(Long id, Long idTask, Task taskUpdate);
	void removeTaskToDo(Long id, Long idTask);
	void removeTaskCompleted(Long id, Long idTask);
	void removeAllTaskToDo(Long id);
	void removeAllTaskCompleted(Long id);
	void removeAllList(List<ListTask> listTask);
}
