package ufcg.edu.br.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ufcg.edu.br.domain.SubTask;
import ufcg.edu.br.domain.Task;
import ufcg.edu.br.repositories.SubTaskRepository;
import ufcg.edu.br.repositories.TaskRepository;
import ufcg.edu.br.services.SubTaskServiceImpl;


@Controller
@Transactional
public class SubTaskController {
	
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private SubTaskRepository subTaskRepository;
	@Autowired
	private SubTaskServiceImpl subTaskServiceImpl;

	
	@RequestMapping(value="/task/subtask", method=RequestMethod.POST)
	public ModelAndView createSubTask(@RequestParam("titulo") String title, 
			@RequestParam("descricao") String description, @RequestParam("task_id") Long idTask,
			@RequestParam("lista_id") Long id, Model model){
		SubTask subTask = new SubTask(title, description);
		subTaskRepository.save(subTask);
		Task task = taskRepository.findOne(idTask);
		subTask.setTask(task);
		task.getSubTask().add(subTask);
		model.addAttribute("veri", subTask.isMarcado());
		return new ModelAndView("redirect:/list/"+ id +"/taskToDo/"+ idTask +"/detalhesTask" );
	}
	
	@RequestMapping(value = "/list/{id}/taskToDo/{idTask}/subTaskCompleted/{idSubTask}", method = RequestMethod.GET)
	public ModelAndView taskSubListCompleted(@PathVariable Long idTask, @PathVariable Long id, 
			@PathVariable Long idSubTask, Model model){
		SubTask sub = subTaskRepository.findOne(idSubTask);
		subTaskServiceImpl.contemSubTask(idSubTask);
		model.addAttribute("veri", sub.isMarcado());
		return new ModelAndView("redirect:/list/"+ id +"/taskToDo/"+ idTask +"/detalhesTask" );
	}
	
	@RequestMapping(value="/list/{id}/remove/taskToDo/{idTask}/subTask/{idSubTask}", method = RequestMethod.GET)
	public ModelAndView removeSubTask(@PathVariable Long idTask, @PathVariable Long id, 
			@PathVariable Long idSubTask, Model model){
		subTaskServiceImpl.removeSubTask(idTask, idSubTask);
		return new ModelAndView("redirect:/list/"+ id +"/taskToDo/"+ idTask +"/detalhesTask");
	}
	

}
