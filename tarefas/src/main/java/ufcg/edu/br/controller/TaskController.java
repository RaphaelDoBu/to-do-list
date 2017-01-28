package ufcg.edu.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.stylesheets.LinkStyle;

import ufcg.edu.br.domain.ListName;
import ufcg.edu.br.domain.Task;
import ufcg.edu.br.domain.User;
import ufcg.edu.br.order.OrderNameCrescent;
import ufcg.edu.br.order.OrderNameDecrescent;
import ufcg.edu.br.order.OrderPriorityHigh;
import ufcg.edu.br.order.OrderPriorityLow;
import ufcg.edu.br.repositories.ListNameRepository;
import ufcg.edu.br.repositories.TaskRepository;
import ufcg.edu.br.repositories.UserRepository;
import ufcg.edu.br.services.ListNameServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

@Controller
@Transactional
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private ListNameRepository listNameRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ListNameServiceImpl listNameServiceImpl;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String view(Model model) {
		model.addAttribute("listTask", listNameRepository.findAll());
		return "listTask";
	}

	@RequestMapping(value = "/newList/create", method = RequestMethod.POST)
	public ModelAndView createList(@RequestParam("name") String name) {
		ListName listaName = new ListName();
		listaName.setNome(name);
		// Talvez add em user a listaName
		listNameRepository.save(listaName);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/newList/create/{id}", method = RequestMethod.GET)
	public String listView(@PathVariable long id, Model model) {
		ListName list = listNameRepository.findOne(id);
		model.addAttribute("lista", list);
		model.addAttribute("nameTask", list.getListTaskToDo());
		model.addAttribute("taskComplet", list.getListTaskCompleted());
		return "addTask";
	}

	@RequestMapping(value = "/list/delete/{id}", method = RequestMethod.GET)
	public ModelAndView listDelete(@PathVariable long id, Model model) {
		ListName list = listNameRepository.findOne(id);
		listNameServiceImpl.removeAllTaskToDo(id);
		listNameServiceImpl.removeAllTaskCompleted(id);
		listNameRepository.delete(list);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/list/newTask", method = RequestMethod.POST)
	public ModelAndView newTask(@RequestParam("lista_id") long id, @RequestParam("titulo") String titulo,
			@RequestParam("descricao") String descricao, @RequestParam("prioridade") String prioridade,
			@RequestParam("categoria") String categoria, Model model) {
		Task task = new Task(titulo, descricao, prioridade, categoria);
		taskRepository.save(task);
		ListName listaName = listNameRepository.findOne(id);
		listaName.getListTaskToDo().add(task);
		listNameRepository.save(listaName);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/list/filterPriority", method = RequestMethod.GET)
	public String filterPriority(@RequestParam("filter") String typeFilter, Model model) {
		int filterToDo = listNameServiceImpl.filterToDo("priority", typeFilter);
		int filterCompleted = listNameServiceImpl.filterCompleted("priority", typeFilter);
		String filter = "Prioridade";
		model.addAttribute("quantToDo", filterToDo);
		model.addAttribute("quantCompleted", filterCompleted);
		model.addAttribute("typeFilter", filter + " " + typeFilter);
		return "filter";

	}

	@RequestMapping(value = "/list/filterCategory", method = RequestMethod.GET)
	public String filterCategory(@RequestParam("filter") String typeFilter, Model model) {
		int filterToDo = listNameServiceImpl.filterToDo("category", typeFilter);
		int filterCompleted = listNameServiceImpl.filterCompleted("category", typeFilter);
		String filter = "Categoria";
		model.addAttribute("quantToDo", filterToDo);
		model.addAttribute("quantCompleted", filterCompleted);
		model.addAttribute("typeFilter", filter + " " + typeFilter);
		return "filter";

	}

	@RequestMapping(value = "/list/{id}/taskCompleted/{idTask}", method = RequestMethod.GET)
	public ModelAndView taskCompleted(@PathVariable Long idTask, @PathVariable Long id, Model model) {
		listNameServiceImpl.taskCompleted(id, idTask);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/list/{id}/edit/task/{idTask}", method = RequestMethod.GET)
	public String editTask(@PathVariable Long id, @PathVariable Long idTask, Model model) {
		Task task = listNameServiceImpl.taskSearch(id, idTask);
		ListName list = listNameRepository.findOne(id);
		model.addAttribute("task", task);
		model.addAttribute("lista", list);
		return "edit";
	}

	@RequestMapping(value = "/list/{id}/remove/task/{idTask}", method = RequestMethod.GET)
	public ModelAndView removeTask(@PathVariable Long id, @PathVariable Long idTask) {
		listNameServiceImpl.removeTask(id, idTask);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/removeAllTaskToDo", method = RequestMethod.GET)
	public ModelAndView removeAllTaskToDo(@RequestParam("lista_id") Long id) {
		listNameServiceImpl.removeAllTaskToDo(id);
		return new ModelAndView("redirect:/newList/create/" + id);
	}
	
	@RequestMapping(value = "/removeAllTaskCompleted", method = RequestMethod.GET)
	public ModelAndView removeAllTaskCompleted(@RequestParam("lista_id") Long id) {
		listNameServiceImpl.removeAllTaskCompleted(id);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/taskUpdate", method = RequestMethod.POST)
	public ModelAndView taskUpdate(@RequestParam("task_id") Long idTask, @RequestParam("lista_id") Long id,
			@RequestParam("titulo") String titulo, @RequestParam("descricao") String descricao,
			@RequestParam("prioridade") String prioridade, @RequestParam("categoria") String categoria) {
		Task taskUpdate = new Task(titulo, descricao, prioridade, categoria);
		listNameServiceImpl.taskUpdate(id, idTask, taskUpdate);
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/orderTaskNameCrescent", method = RequestMethod.GET)
	public ModelAndView orderNameCrescent(@RequestParam("lista_id") Long id) {
		ListName list = listNameRepository.findOne(id);
		Collections.sort(list.getListTaskToDo(), new OrderNameCrescent());
		return new ModelAndView("redirect:/newList/create/" + id);
	}
	
	@RequestMapping(value = "/orderTaskNameDecrescent", method = RequestMethod.GET)
	public ModelAndView orderNameDecrecent(@RequestParam("lista_id") Long id) {
		ListName list = listNameRepository.findOne(id);
		Collections.sort(list.getListTaskToDo(), new OrderNameDecrescent());
		return new ModelAndView("redirect:/newList/create/" + id);
	}

	@RequestMapping(value = "/orderPriorityHigh", method = RequestMethod.GET)
	public ModelAndView orderPriorityHigh(@RequestParam("lista_id") Long id) {
		ListName list = listNameRepository.findOne(id);
		Collections.sort(list.getListTaskToDo(), new OrderPriorityHigh());
		return new ModelAndView("redirect:/newList/create/" + id);
	}
	
	@RequestMapping(value = "/orderPriorityLow", method = RequestMethod.GET)
	public ModelAndView orderPriorityLow(@RequestParam("lista_id") Long id) {
		ListName list = listNameRepository.findOne(id);
		Collections.sort(list.getListTaskToDo(), new OrderPriorityLow());
		return new ModelAndView("redirect:/newList/create/" + id);
	}
}
