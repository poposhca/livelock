/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockserver;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author poposhca
 */
public class ServerStats extends Observable
{
    private int TotalIncoming = 0;
    private int SizeInputQueue = 0;
    private int SizeOutputQueue = 0;
    private int TotalSentMessages = 0;
    
    public int getTotalIncoming()
    {
        return this.TotalIncoming;
    }
    
    public int getSizeInputQueue()
    {
        return this.SizeInputQueue;
    }
    
    public int getSizeOutputQueue()
    {
        return this.SizeOutputQueue;
    }
    
    public int getTotalSentMessages()
    {
        return this.TotalSentMessages;
    }
    
    public void incrementIncoming()
    {
        this.TotalIncoming++;
        HasChaneged();
    }
    
    public void incrementInputQueueSize()
    {
        this.SizeInputQueue++;
        HasChaneged();
    }
    
    public void decreseInputQueueSize()
    {
        this.SizeInputQueue--;
        HasChaneged();
    }
    
    public void incrementOutputQueueSize()
    {
        this.SizeOutputQueue++;
        HasChaneged();
    }
    
    public void decreseOutputQueueSize()
    {
        this.SizeOutputQueue--;
        HasChaneged();
    }
    
    public void incrementOutcomming()
    {
        this.TotalSentMessages++;
        HasChaneged();
    }
    
    private void HasChaneged()
    {
        setChanged();
        notifyObservers();
    }
    
}
