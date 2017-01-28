package ufcg.edu.br.order;

import java.util.Comparator;

import ufcg.edu.br.domain.Task;

public class OrderPriorityLow implements Comparator<Task> {

	@Override
	public int compare(Task um, Task dois) {
		int levelTask1 = um.levelPriority();
		int levelTask2 = dois.levelPriority();

		return levelTask1 < levelTask2 ? -1 : (levelTask1 > levelTask2 ? +1 : 0);
	}
}
