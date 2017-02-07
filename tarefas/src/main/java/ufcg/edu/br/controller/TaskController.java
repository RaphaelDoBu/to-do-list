package ufcg.edu.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ufcg.edu.br.domain.ListTask;
import ufcg.edu.br.domain.Task;
import ufcg.edu.br.order.OrderNameCrescent;
import ufcg.edu.br.order.OrderNameDecrescent;
import ufcg.edu.br.order.OrderPriorityHigh;
import ufcg.edu.br.order.OrderPriorityLow;
import ufcg.edu.br.repositories.ListTaskRepository;
import ufcg.edu.br.repositories.TaskRepository;
import ufcg.edu.br.services.SubTaskServiceImpl;
import ufcg.edu.br.services.TaskServiceImpl;

import java.util.Collections;

import javax.transaction.Transactional;

@Controller
@Transactional
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ListTaskRepository listTaskRepository;
	@Autowired
	private TaskServiceImpl taskServiceImpl;
	@Autowired
	private SubTaskServiceImpl subTaskServiceImpl;
	
	@RequestMapping(value = "/list/newTask", method = RequestMethod.POST)
	public ModelAndView newTask(@RequestParam("lista_id") long id, @RequestParam("titulo") String titulo,
			@RequestParam("descricao") String descricao, @RequestParam("prioridade") String prioridade,
			@RequestParam("categoria") String categoria, Model model) {
		Task task = new Task(titulo, descricao, prioridade, categoria);
		taskServiceImpl.newTask(task, id);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/list/{id}/taskForCompleted/{idTask}", method = RequestMethod.GET)
	public ModelAndView taskToDoForCompleted(@PathVariable Long idTask, @PathVariable Long id) {
		taskServiceImpl.taskToDoForCompleted(id, idTask);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/list/{id}/taskToDo/{idTask}/detalhesTask", method = RequestMethod.GET)
	public String taskToDoDetalhes(@PathVariable Long idTask, @PathVariable Long id, Model model) {
		Task task = taskRepository.findOne(idTask);
		model.addAttribute("task", task);
		model.addAttribute("idList", id);
		model.addAttribute("subTask", task.getSubTask());
		return "detailsTaskToDo";
	}

	@RequestMapping(value = "/list/{id}/taskCompleted/{idTask}/detalhesTask", method = RequestMethod.GET)
	public String taskCompletedDetalhes(@PathVariable Long idTask, @PathVariable Long id, Model model) {
		Task task = taskRepository.findOne(idTask);
		subTaskServiceImpl.subTaskAllCompleted(idTask);
		model.addAttribute("task", task);
		model.addAttribute("idList", id);
		model.addAttribute("subTask", task.getSubTask());
		return "detailsTaskCompleted";
	}

	@RequestMapping(value = "/list/{id}/taskForToDo/{idTask}", method = RequestMethod.GET)
	public ModelAndView taskCompletedForToDo(@PathVariable Long idTask, @PathVariable Long id, Model model) {
		taskServiceImpl.taskCompletedForToDo(id, idTask);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/list/{id}/edit/task/{idTask}", method = RequestMethod.GET)
	public String editTask(@PathVariable Long id, @PathVariable Long idTask, Model model) {
		Task task = taskServiceImpl.taskSearch(id, idTask);
		ListTask list = listTaskRepository.findOne(id);
		model.addAttribute("task", task);
		model.addAttribute("lista", list);
		return "edit";
	}

	@RequestMapping(value = "/list/{id}/remove/taskToDo/{idTask}", method = RequestMethod.GET)
	public ModelAndView removeTaskToDo(@PathVariable Long id, @PathVariable Long idTask) {
		taskServiceImpl.removeTaskToDo(id, idTask);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/list/{id}/remove/taskCompleted/{idTask}", method = RequestMethod.GET)
	public ModelAndView removeTaskCompleted(@PathVariable Long id, @PathVariable Long idTask) {
		taskServiceImpl.removeTaskCompleted(id, idTask);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/removeAllTaskToDo", method = RequestMethod.GET)
	public ModelAndView removeAllTaskToDo(@RequestParam("lista_id") Long id) {
		taskServiceImpl.removeAllTaskToDo(id);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/removeAllTaskCompleted", method = RequestMethod.GET)
	public ModelAndView removeAllTaskCompleted(@RequestParam("lista_id") Long id) {
		taskServiceImpl.removeAllTaskCompleted(id);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/taskUpdate", method = RequestMethod.POST)
	public ModelAndView taskUpdate(@RequestParam("task_id") Long idTask, @RequestParam("lista_id") Long id,
			@RequestParam("titulo") String titulo, @RequestParam("descricao") String descricao,
			@RequestParam("prioridade") String prioridade, @RequestParam("categoria") String categoria) {
		Task taskUpdate = new Task(titulo, descricao, prioridade, categoria);
		taskServiceImpl.taskUpdate(id, idTask, taskUpdate);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/orderTaskNameCrescent", method = RequestMethod.GET)
	public ModelAndView orderNameCrescent(@RequestParam("lista_id") Long id) {
		ListTask list = listTaskRepository.findOne(id);
		Collections.sort(list.getListTaskToDo(), new OrderNameCrescent());
		Collections.sort(list.getListTaskCompleted(), new OrderNameCrescent());
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/orderTaskNameDecrescent", method = RequestMethod.GET)
	public ModelAndView orderNameDecrecent(@RequestParam("lista_id") Long id) {
		ListTask list = listTaskRepository.findOne(id);
		Collections.sort(list.getListTaskToDo(), new OrderNameDecrescent());
		Collections.sort(list.getListTaskCompleted(), new OrderNameDecrescent());
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/orderPriorityHigh", method = RequestMethod.GET)
	public ModelAndView orderPriorityHigh(@RequestParam("lista_id") Long id) {
		ListTask list = listTaskRepository.findOne(id);
		Collections.sort(list.getListTaskToDo(), new OrderPriorityHigh());
		Collections.sort(list.getListTaskCompleted(), new OrderPriorityHigh());
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/orderPriorityLow", method = RequestMethod.GET)
	public ModelAndView orderPriorityLow(@RequestParam("lista_id") Long id) {
		ListTask list = listTaskRepository.findOne(id);
		Collections.sort(list.getListTaskToDo(), new OrderPriorityLow());
		Collections.sort(list.getListTaskCompleted(), new OrderPriorityLow());
		return new ModelAndView("redirect:/newList/create/" + id);
	}
}
