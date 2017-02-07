package ufcg.edu.br.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Task")
@Table(name = "tb_task")
public class Task implements Serializable {

	@Id
	@Column(name = "TASK_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private String priority;
	@Column
	private String category;
	@OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
	private List<SubTask> subTask;

	public List<SubTask> getSubTask() {
		return subTask;
	}

	public void setSubTask(List<SubTask> subTask) {
		this.subTask = subTask;
	}

	public Task() {

	}

	public Task(String titulo, String descricao, String prioridade, String categoria) {
		this.title = titulo;
		this.description = descricao;
		this.priority = prioridade;
		this.category = categoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int levelPriority() {
		int level = 0;
		if (getPriority().equalsIgnoreCase("Alta")) {
			level = 3;
		} else if (getPriority().equalsIgnoreCase("Media")) {
			level = 2;
		} else if (getPriority().equalsIgnoreCase("Baixa")) {
			level = 1;
		}
		return level;
	}
}
