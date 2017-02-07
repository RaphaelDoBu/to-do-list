package ufcg.edu.br.controller;

import java.util.List;

import javax.transaction.Transactional;

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
import ufcg.edu.br.repositories.ListTaskRepository;
import ufcg.edu.br.repositories.TaskRepository;
import ufcg.edu.br.services.ListTaskServiceImpl;
import ufcg.edu.br.services.TaskServiceImpl;

@Controller
@Transactional
public class ListTaskController {
	@Autowired
	private ListTaskServiceImpl listTaskServiceImpl;
	@Autowired
	private ListTaskRepository listTaskRepository;
	@Autowired
	private TaskServiceImpl taskServiceImpl;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String view(Model model) {
		model.addAttribute("listTask", listTaskRepository.findAll());
		return "listTask";
	}

	@RequestMapping(value = "/newList/create", method = RequestMethod.POST)
	public ModelAndView createList(@RequestParam("name") String name) {
		listTaskServiceImpl.createListTask(name);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/newList/create/{id}", method = RequestMethod.GET)
	public String listView(@PathVariable long id, Model model) {
		ListTask list = listTaskRepository.findOne(id);
		model.addAttribute("lista", list);
		model.addAttribute("nameTask", list.getListTaskToDo());
		model.addAttribute("listaSize", list.getListTaskToDo().size());
		model.addAttribute("taskCompletedSize", list.getListTaskCompleted().size());
		model.addAttribute("taskComplet", list.getListTaskCompleted());
		return "addTask";
	}

	@RequestMapping(value = "/list/delete/{id}", method = RequestMethod.GET)
	public ModelAndView listDelete(@PathVariable long id, Model model) {
		ListTask list = listTaskRepository.findOne(id);
		taskServiceImpl.removeAllTaskToDo(id);
		taskServiceImpl.removeAllTaskCompleted(id);
		listTaskRepository.delete(list);
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/list/filterPriority", method = RequestMethod.GET)
	public String filterPriority(@RequestParam("filter") String typeFilter, Model model) {
		int filterToDo = taskServiceImpl.filterToDo("priority", typeFilter);
		int filterCompleted = taskServiceImpl.filterCompleted("priority", typeFilter);
		String filter = "Prioridade";
		model.addAttribute("quantToDo", filterToDo);
		model.addAttribute("quantCompleted", filterCompleted);
		model.addAttribute("typeFilter", filter + " " + typeFilter);
		return "filter";
	}

	@RequestMapping(value = "/list/filterCategory", method = RequestMethod.GET)
	public String filterCategory(@RequestParam("filter") String typeFilter, Model model) {
		int filterToDo = taskServiceImpl.filterToDo("category", typeFilter);
		int filterCompleted = taskServiceImpl.filterCompleted("category", typeFilter);
		String filter = "Categoria";
		model.addAttribute("quantToDo", filterToDo);
		model.addAttribute("quantCompleted", filterCompleted);
		model.addAttribute("typeFilter", filter + " " + typeFilter);
		return "filter";
	}
	
	@RequestMapping(value = "/removeAllList", method = RequestMethod.GET)
	public ModelAndView removeAllList() {
		List<ListTask> listTask = listTaskRepository.findAll();
		taskServiceImpl.removeAllList(listTask);
		return new ModelAndView("redirect:");
	}

}
