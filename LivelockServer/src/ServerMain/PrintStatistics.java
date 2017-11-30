/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerMain;

import java.util.Observable;
import java.util.Observer;
import livelockserver.ServerStats;

/**
 *
 * @author poposhca
 */
public class PrintStatistics implements Observer
{
    public PrintStatistics(Observable observable)
    {
        observable.addObserver(this);
    }
    
    @Override public void update(Observable o, Object arg)
    {
        ServerStats stats = (ServerStats)o;
        System.out.println("------------");
        System.out.println("Mensajes recividos: " + stats.getTotalIncoming());
        System.out.println("Tamaño cola de entrada: " + stats.getSizeInputQueue());
        System.out.println("Tamaño cola de salida: " + stats.getSizeOutputQueue());
        System.out.println("Total de confirmacines enviadas: " + stats.getTotalSentMessages());
        System.out.println("------------");
    }
    
}
