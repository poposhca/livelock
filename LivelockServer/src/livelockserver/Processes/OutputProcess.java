/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockserver.Processes;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.net.Socket;
import livelockserver.ServerStats;

/**
 *
 * @author poposhca
 */
public class OutputProcess
{
    private final int outputport;
    private final int sleepTime;
    private final ServerStats stats;
    
    public OutputProcess(int outputport, int sleepTime, ServerStats stats)
    {
        this.outputport = outputport;
        this.sleepTime = sleepTime;
        this.stats = stats;
    }
     
    public void SendMessage(String mssg)
    {
        try
        {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("mm.ss");
            try (Socket socket = new Socket("127.0.0.1", outputport)) 
            {
                String time = timeFormat.format(cal.getTime());
                PrintWriter sw = new PrintWriter(socket.getOutputStream(), true);
                sw.println(mssg + "-" + time);
                if(stats != null) stats.incrementOutcomming();
                Thread.sleep(sleepTime);
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
    
}
