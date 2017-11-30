/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockserver.queues;
import java.util.Deque;
import java.util.LinkedList;

/**
 *
 * @author poposhca
 */
public class SimpleQueue implements IQueue
{

    private Deque<String> queue = new LinkedList<>();
    private int writers = 0;
    private int readers = 0;
        
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
        
        readers ++;
        
        Boolean val = !queue.isEmpty();
        
        readers--;
        
        return val;
    }
    
}
