/*CPU Process Scheduler!
This program simulates a Linux CPU Scheduler
A binary heap is created with 5000 integers inserted then they are remeved sequentially 
everytime a node is removed a new random node is added to another binary heap
when the first heap is empty, the heap's swap and the process begins again
Author: James Gouveia Csc 130 T/Th
April 2020 */


import java.util.Random;
import java.util.Scanner;
import java.time.ZonedDateTime;
import java.util.Date;

public class BinaryHeap
{
    int[] heap;
    int heapSize;
    int maxHeapSize;

    public BinaryHeap(int size)
    {
        heapSize = 0;
        heap = new int[size + 1];
        maxHeapSize = size + 1;
    }

    public void insert(int value)
    {
    	if(heapSize >= maxHeapSize)
    	{
    		System.out.println("Heap overflow");
    	}
    	heap[++heapSize] = value;

    	int pos = heapSize;
    	//percholate up
    	for(; pos > 1 && value < heap[pos/2]; pos = pos/2 )
    	{
     		heap[pos] = heap[pos/2];
    		heap[pos/2] = value;
    	}
    } 

    public void deleteMin()
    {	
    	if(heapSize == 0)
    	{
    		System.out.println("The binary heap is empty!\n");
    	}
    	else
    	{
    		int value = heap[1];
    		heap[1] = heap[heapSize];
        	heapSize --;

	    	int child;
	    	int hole = 1;
	    	int tmp = heap[hole];
	    	//percholate down
	    	for(; hole * 2 <= heapSize; hole = child)
	    		{   
	    			child = hole * 2;
	    			//testing if still in array and which is max child   
	    			if (child != heapSize && heap[child + 1] < heap[child])
	    				child++;
	    			if(heap[child] < tmp)
	    			{
	    				heap[hole] = heap[child];
	    			}
	    			else
	    			{
	    				break;
	    			}
	    		}
	    		heap[hole] = tmp;
	    				
    	}
    	    	
    }

    private int getParentIndex(int nodeIndex)
    {
    	return (nodeIndex-1)/2;
    }

    public int returnMinValue()
    {
    	return heap[1];
    }

    public static void main(String[] args)
    {
    	BinaryHeap minHeap = new BinaryHeap(5000);
    	BinaryHeap minHeap2 = new BinaryHeap(5000);
    	
    	int[] processArray = new int[5000];
    	Random rand = new Random();
    	
    	int minValue;

        for(int i = 0; i < 5000; i++)
        {
        	processArray[i] = rand.nextInt(4098);
        	minHeap.insert(processArray[i]);
        }
        long start = System.nanoTime();
	 	do
	 	{
		 	minValue = minHeap.returnMinValue();
		 	System.out.format("The process with a priority of %d is now scheduled to run!\n\n", minValue);
		 	minHeap.deleteMin();

		 	minHeap2.insert(rand.nextInt(4098));
		 	System.out.format("The process with a priority of %d has run out of it's timeslice!\n\n", minValue);
		 	
		 	
		 	if(minHeap.heapSize == 0)
		 	{
				long finish = System.nanoTime();
				long timeElapsed = finish - start;
		 		System.out.format("It took %d msecs for all processes to run out of their timelieces.\n\n", timeElapsed/ 1000000);

		 		System.out.println("Please press \"Enter\" to start the next round!\n");
		 		
	     		Scanner scanner = new Scanner(System.in);
				String readString = scanner.nextLine(); 

				minHeap = minHeap2;
				minHeap2 = new BinaryHeap(5000);
		 	} 
		 	
	 	}while(true);
          
    }

}