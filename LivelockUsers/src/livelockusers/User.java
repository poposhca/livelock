/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockusers;

import java.net.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author poposhca
 */
public class User extends Thread
{
    private final int userID;
    private final int port;
    private final int idleTime;
    private final int totalTime;
    private final PackTable messgTable;
    
    public User(int userID, int port, int totalTime, int idleTime, PackTable messgTable)
    {
        this.userID = userID;
        this.port = port;
        this.idleTime = idleTime;
        this.totalTime = totalTime;
        this.messgTable = messgTable;
    }
    
    @Override public void run()
    {
        int i = 0;
        while(i < this.totalTime)
        {
            SendMessage(i);
            try
            {
                Thread.sleep(this.idleTime);
            }
            catch(InterruptedException e)
            {
                System.err.println("ERROR EN THREAD: " + e.getMessage());
            }
            finally
            {
                i++;
            }
        }
    }
    
    public void SendMessage(int messageID)
    {
        Boolean sent = false;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm.ss");
        String mssg = userID + ":" + messageID; 
        String time = timeFormat.format(cal.getTime());
        try
        {

            try (Socket socket = new Socket("127.0.0.1", port)) 
            {
                PrintWriter sw = new PrintWriter(socket.getOutputStream(), true);
                sw.println(mssg);
                messgTable.InsertMessage(mssg, time);
            }
        }
        catch(IOException e)
        {
            System.out.println(mssg+","+time+"0,false");
            //System.err.println("ERROR AL MANDAR MENSAJE: " + e.getMessage());
        }
    }
    
}
