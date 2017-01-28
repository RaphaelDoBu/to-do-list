package ufcg.edu.br.order;
import java.util.Comparator;

import ufcg.edu.br.domain.Task;

public class OrderNameCrescent implements Comparator<Task> {
  
	@Override
	public int compare(Task um, Task dois) {
		 return um.getTitle().compareTo(dois.getTitle());
	}
}
