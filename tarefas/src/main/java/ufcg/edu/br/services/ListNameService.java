package ufcg.edu.br.services;

import java.util.List;

import ufcg.edu.br.domain.ListName;
import ufcg.edu.br.domain.Task;

public interface ListNameService {
	
	int filterToDo(String filter, String typeFilter);
	int filterCompleted(String filter, String typeFilter);
	Task taskCompleted(Long id, Long idTask);
	Task taskSearch(Long id, Long idTask);
	void taskUpdate(Long id, Long idTask, Task taskUpdate);
	void removeTask(Long id, Long idTask);
	void removeAllTaskToDo(Long id);
	void removeAllTaskCompleted(Long id);
}
