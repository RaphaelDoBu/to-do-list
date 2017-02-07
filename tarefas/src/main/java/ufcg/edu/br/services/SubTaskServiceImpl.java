package ufcg.edu.br.services;

import org.hibernate.hql.internal.ast.tree.BooleanLiteralNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufcg.edu.br.domain.*;
import ufcg.edu.br.repositories.SubTaskRepository;
import ufcg.edu.br.repositories.TaskRepository;

@Service("SubTaskService")
public class SubTaskServiceImpl implements SubTaskSercive{

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private SubTaskRepository subTaskRepository;

	@Override
	public void removeSubTask(Long idTask, Long idSubTask) {
		Task task = taskRepository.findOne(idTask);
		SubTask subTask = subTaskRepository.findOne(idSubTask);
		task.getSubTask().remove(subTask);
		subTaskRepository.delete(subTask);
	}
	
	@Override
	public boolean contemSubTask(Long idSubTask) {
		SubTask subTask = subTaskRepository.findOne(idSubTask);
		boolean verifica = true;

		if (subTask.isMarcado()) {
			subTask.setMarcado(false);
		} else {
			subTask.setMarcado(true);
		}
		return verifica;
	}
	
	@Override
	public void subTaskAllCompleted(Long idTask) {
		Task task = taskRepository.findOne(idTask);
		for (SubTask subTask : task.getSubTask()) {
			subTask.isMarcado();
		}
	}
}
