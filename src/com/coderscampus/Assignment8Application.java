package com.coderscampus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

public class Assignment8Application {

	List<Integer> numbersList = Collections.synchronizedList(new ArrayList<Integer>());

	@Test
	public void getData() throws InterruptedException {
		Assignment8 assignment = new Assignment8();
		List<CompletableFuture<Void>> taskList = new ArrayList<>();
		ExecutorService pool = Executors.newCachedThreadPool();

		for (int i = 0; i < 1000; i++) {
			CompletableFuture<Void> task = CompletableFuture.supplyAsync(() -> assignment.getNumbers(), pool)
					                                        .thenAcceptAsync(numbers -> numbersList.addAll(numbers));
			taskList.add(task);
			/*Synchronous way
			 * List<Integer> numbersList = assignment.getNumbers();
			 * System.out.println(numbersList);
			 */
		}
		while (taskList.stream().filter(CompletableFuture::isDone).count() < 1000) {		}
		System.out.println("numbersList.size() : " + numbersList.size());
		System.out.println("taskList.size() : " + taskList.size());
		
		//Display Numbers appear on list
		Map<Integer,Integer> num_occurence = new HashMap<>();
				numbersList.stream().forEach(number -> {
                	if(!num_occurence.containsKey(number)) {
                		num_occurence.put(number,1);
                	}
                	else {
                		num_occurence.put(number,num_occurence.get(number)+1);
                	}
                });
				                                        
		System.out.println(num_occurence);		                                  

	}
}
