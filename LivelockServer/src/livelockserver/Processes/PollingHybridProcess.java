/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockserver.Processes;

import livelockserver.ServerStats;
import livelockserver.queues.IQueue;

/**
 *
 * @author poposhca
 */
public class PollingHybridProcess extends Thread implements IProcess
{
    private Boolean run = true;
    private final int pollingTime;
    private final int sleepTime;
    private final IQueue inputQueue;
    private final IQueue outputQueue;
    private final OutputProcess out;
    private final ServerStats stats;
    
    public PollingHybridProcess(int pollingTime, int sleepTime, IQueue inputQueue, IQueue outputQueue, OutputProcess out, ServerStats stats)
    {
        this.pollingTime = pollingTime;
        this.sleepTime = sleepTime;
        this.inputQueue = inputQueue;
        this.outputQueue = outputQueue;
        this.out = out;
        this.stats = stats;
    }

    @Override
    public void run()
    {
        runProcess();
    }
    
    @Override
    public void runProcess() 
    {
        //System.out.println("Proceso de polleado inciado");
        Boolean processState = false;
        while(run)
        {
            //System.out.println("Input tiene elementos? : " + inputQueue.QueueHasElements());
            System.out.print("");
            if(inputQueue.QueueHasElements())
            {
                long t = 0;
                while(t  < pollingTime)
                {
                    //Procesar Output queue
                    if(processState && outputQueue.QueueHasElements())
                    {
                        String s = outputQueue.UnQueue();
                        if(stats != null) stats.decreseOutputQueueSize();
                        out.SendMessage(s);
                    }
                    //Procesar Input Queue
                    else if(!processState && inputQueue.QueueHasElements())
                    {
                        String s = inputQueue.UnQueue();
                        if(stats != null) stats.decreseInputQueueSize();
                        try
                        {
                            Thread.sleep(sleepTime);
                        }
                        catch(InterruptedException e)
                        {
                            System.err.println(e.getMessage());
                        }
                        outputQueue.Queue(s);
                        if(stats != null) stats.incrementOutputQueueSize();
                    }
                    t++;
                }
                processState = !processState;
            }
            else if(outputQueue.QueueHasElements())
            {
                String s = outputQueue.UnQueue();
                if(stats != null) stats.decreseOutputQueueSize();
                out.SendMessage(s);
            }
        }
        System.out.println("Proceso de polleado terminado");
    }
    
}
