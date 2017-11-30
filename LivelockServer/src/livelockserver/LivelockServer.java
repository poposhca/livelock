/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockserver;

import livelockserver.queues.IQueue;
import java.net.*;
import java.io.*;

/**
 *
 * @author poposhca
 */
public class LivelockServer extends Thread
{
    private final IQueue inputQueue;
    private final ServerStats stats;
    private Boolean run = true;
    
    public LivelockServer(ServerStats stats, IQueue inputQueue)
    {
        this.inputQueue = inputQueue;
        this.stats = stats;
    }
    
    @Override public void run()
    {
        RunServer();
    }
    
    public void RunServer()
    {
        BufferedReader in;
        ServerSocket socket = null;
        Socket messg;
        
        try
        {
            socket = new ServerSocket(5000);
        }
        catch(IOException e)
        {
            System.err.println("Error en socket: "+ e.getMessage());
            System.exit(9);
        }
        
        //System.out.println("Listo servidor");
        
        while(run)
        {
            try
            {
                //Recive message
                messg = socket.accept();
                if(this.stats != null)
                {
                    this.stats.incrementIncoming();
                    this.stats.incrementInputQueueSize();
                }

                //Read message
                in = new BufferedReader(new InputStreamReader(messg.getInputStream()));
                String strMessg = in.readLine();
                //System.out.println(strMessg);
                inputQueue.Queue(strMessg);
            }
            catch(Exception e)
            { 
                System.err.println("Error mensaje: "+ e.getMessage());
            }
        }
        
        //Cerrera server
        try
        {
            socket.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    
    public void StopServer()
    {
        this.run = false;
    }
    
}
