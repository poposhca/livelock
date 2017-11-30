/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockserver.queues;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.Queue;

/**
 *
 * @author poposhca
 */
public class RoundRobinQueue implements IQueue
{

    private HashMap<String, Queue<String>> multipleQueue;
    private int actualIndex = 0;
    private int writers = 0;
    private int readers = 0;
    
    public RoundRobinQueue()
    {
        multipleQueue = new HashMap<>();
    }
    
    @Override
    public synchronized void Queue(String message) 
    {
        while(readers > 0 && writers > 0)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                System.err.println(e.getMessage());
            }
        }
        
        writers++;
        
        String clientNumber = message.split(":")[0];
        Queue<String> queue;
        if(multipleQueue.containsKey(clientNumber))
        {
            queue = multipleQueue.get(clientNumber);
        }
        else
        {
            queue = new LinkedList<>();
            multipleQueue.put(clientNumber, queue);
        }
        queue.add(message);
        
        writers--;
    }

    @Override
    public synchronized String UnQueue() 
    {
        while(readers > 0 && writers > 0)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                System.err.println(e.getMessage());
            }
        }
        
        writers++;
        
        if(actualIndex > multipleQueue.keySet().size() - 1)
            actualIndex = 0;
        String key = (String)(multipleQueue.keySet().toArray())[actualIndex];
        Queue<String> queue = multipleQueue.get(key);
        actualIndex = actualIndex + 1;
        while(queue.isEmpty())
        {
            if(actualIndex > multipleQueue.keySet().size() - 1)
                actualIndex = 0;
            key = (String)(multipleQueue.keySet().toArray())[actualIndex];
            queue = multipleQueue.get(key);
            actualIndex = actualIndex + 1;
        }
        String val = queue.poll();
        
        writers--;
        
        return val;
    }

    @Override
    public synchronized Boolean QueueHasElements() 
    {
        while(writers > 0)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e)
            {
                System.err.println(e.getMessage());
            }
        }
        
        readers++;
        
        if(multipleQueue.isEmpty())
            return false;
        Boolean emptyFlag = true;
        for(String key : multipleQueue.keySet())
            emptyFlag = emptyFlag & multipleQueue.get(key).isEmpty();
        
        readers--;
        
        return !emptyFlag;
    }
    
}
