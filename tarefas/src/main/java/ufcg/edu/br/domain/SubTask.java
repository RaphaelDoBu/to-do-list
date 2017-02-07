package ufcg.edu.br.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity(name="SubTask")
@Table(name="tb_subTask")
public class SubTask implements Serializable{
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private boolean marcado;
    @ManyToOne
    @JoinColumn(name = "TASK_ID")
	private Task task;
    
    public SubTask(){
    	
    }
    
    public SubTask(String title, String description){
    	this.title = title;
    	this.description = description;
    	this.marcado = false;
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
	
	public Task getTask() {
		return task;
	}
	public void setTask(Task task) {
		this.task = task;
	}
	
	public boolean isMarcado() {
		return marcado;
	}

	public void setMarcado(boolean marcado) {
		this.marcado = marcado;
	}
	
}
