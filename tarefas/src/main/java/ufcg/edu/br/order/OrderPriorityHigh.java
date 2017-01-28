package ufcg.edu.br.order;

import java.util.Comparator;

import ufcg.edu.br.domain.Task;

public class OrderPriorityHigh implements Comparator<Task> {

	@Override
	public int compare(Task um, Task dois) {
		int levelTask1 = um.levelPriority();
		int levelTask2 = dois.levelPriority();
		
		 return levelTask2 < levelTask1 ? -1 : (levelTask2 > levelTask1 ? +1 : 0);
	}
}
