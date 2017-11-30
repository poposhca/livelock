/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerMain;

import java.util.Scanner;
import livelockserver.Processes.*;
import livelockserver.queues.*;
import livelockserver.*;

/**
 *
 * @author poposhca
 */
public class main {
    
    public static void main(String[] args)
    {
        //Estadisticas
        ServerStats stats = new ServerStats();
        PrintStatistics front = new PrintStatistics(stats);
        SaveStats saveStats;
        try
        {
            saveStats = new SaveStats(stats, "/Users/icloud/Desktop/stats.csv");
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            saveStats = null;
        }
        
        //Colas
        IQueue InputQueue = new SimpleQueue();
        IQueue OutputQueue = new SimpleQueue();
        
        //Procesos
        OutputProcess out = new OutputProcess(5001, 5, stats);
        //LiveLockProcess p = new LiveLockProcess(5, InputQueue, OutputQueue, out, stats);
        //PollingProcess p = new PollingProcess(100, 5, InputQueue, OutputQueue, out, stats);
        PollingHybridProcess p = new PollingHybridProcess(100, 5, InputQueue, OutputQueue, out, stats);
        
        //Servidor
        LivelockServer server = new LivelockServer(stats, InputQueue);
        
        try
        {
            server.start();
            p.start();
            System.out.println("Kernel listo");
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
        
        //Cerrar programa
        try (Scanner scan = new Scanner(System.in)) {
            String n = scan.nextLine();
            if(saveStats != null) saveStats.CloseFile();
        }
    }
    
}
