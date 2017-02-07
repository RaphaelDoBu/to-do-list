package ufcg.edu.br.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity(name="User")
@Table(name="tb_user")
public class User  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private ListTask listTask;

    public ListTask getListTask() {
		return listTask;
	}

	public void setListTask(ListTask listTask) {
		this.listTask = listTask;
	}


	public User(){
    	
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
