/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockserver.Processes;
import livelockserver.queues.IQueue;
import livelockserver.ServerStats;

/**
 *
 * @author poposhca
 */
public class LiveLockProcess extends Thread implements IProcess
{
    private Boolean run = true;
    private final int sleepTime;
    private final IQueue inputQueue;
    private final IQueue outputQueue;
    private final OutputProcess out;
    private final ServerStats stats;
    
    public LiveLockProcess(int sleepTime, IQueue inputQueue, IQueue outputQueue, OutputProcess out, ServerStats stats)
    {
        this.sleepTime = sleepTime;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.out = out;
        this.stats = stats;
    }
    
    @Override public void run()
    {
        runProcess();
    }
    
    @Override
    public void runProcess()
    {
        System.out.println("Proceso de paquetes inciado");
        while(run)
        {
            //System.out.println("Output tiene elementos: " + outputQueue.QueueHasElements());
            System.out.print("");
            if(inputQueue.QueueHasElements())
            {
                String m = inputQueue.UnQueue();
                if(stats != null) stats.decreseInputQueueSize();
                try
                {
                    Thread.sleep(sleepTime);
                }
                catch(Exception e)
                {
                    System.err.println(e.getMessage());
                }
                outputQueue.Queue(m);
                if(stats != null) stats.incrementOutputQueueSize();
            }
            else if(outputQueue.QueueHasElements())
            {
                String m = outputQueue.UnQueue();
                if(stats != null) stats.decreseOutputQueueSize();
                out.SendMessage(m);
            }
        }
    }
            
}
