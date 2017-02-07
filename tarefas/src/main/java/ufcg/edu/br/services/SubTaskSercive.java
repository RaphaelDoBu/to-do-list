package ufcg.edu.br.services;

public interface SubTaskSercive {

	void removeSubTask(Long idTask, Long idSubTask);
	boolean contemSubTask(Long idSubTask);
	void subTaskAllCompleted(Long idTask);
}
