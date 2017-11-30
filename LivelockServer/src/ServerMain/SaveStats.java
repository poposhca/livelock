/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerMain;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;
import livelockserver.ServerStats;

/**
 *
 * @author poposhca
 */
public class SaveStats implements Observer
{
    
    private PrintWriter file;
    
    public SaveStats(Observable observable, String file) throws Exception
    {
        try
        {
            this.file = new PrintWriter(file);
            this.file.write("Mensajes recividos, Tamaño cola de entrada, Tamaño cola de salida, Total de confirmacines enviada\n");
            observable.addObserver(this);
        }
        catch(FileNotFoundException e)
        {
            throw new Exception("ERROR AL ABRIR ARCHIVO: " + e.getMessage());
        }
    }
    
    @Override
    public void update(Observable o, Object arg) 
    {
        ServerStats stats = (ServerStats)o;
        file.write(stats.getTotalIncoming() + "," 
                + stats.getSizeInputQueue() + ","
                + stats.getSizeOutputQueue() + ","
                + stats.getTotalSentMessages() + "\n");
    }
    
    public void CloseFile()
    {
        this.file.close();
    }
    
}
