package ufcg.edu.br.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "ListName")
@Table(name = "tb_listName")
public class ListTask implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String name;
	@OneToMany
	private List<Task> listTaskToDo;
	@OneToMany
	private List<Task> listTaskCompleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}

	public List<Task> getListTaskToDo() {
		return listTaskToDo;
	}

	public void setListTaskToDo(List<Task> listTaskToDo) {
		this.listTaskToDo = listTaskToDo;
	}

	public List<Task> getListTaskCompleted() {
		return listTaskCompleted;
	}

	public void setListTaskCompleted(List<Task> listTaskCompleted) {
		this.listTaskCompleted = listTaskCompleted;
	}

}
