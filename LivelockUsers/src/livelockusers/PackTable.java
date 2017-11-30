/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockusers;
import java.util.HashMap;

/**
 *
 * @author poposhca
 */
public class PackTable 
{
    private HashMap <String, UserStatistics> table;
    int writters = 0;
    
    public PackTable()
    {
        table = new HashMap<>();
    }
    
    public int GetTableCount()
    {
        return table.size();
    }
    
    public synchronized void InsertMessage(String mssg, String timeSend)
    {
        if(writters > 0)
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
        writters++;
        
        table.put(mssg, new UserStatistics(timeSend));
        
        writters --;
    }
    
    public synchronized UserStatistics RemoveRecivedMessage(String mssg)
    {
        if(writters > 0)
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
        writters++;
        
        UserStatistics val = null;
        if(table.get(mssg) != null)
            val = table.remove(mssg);
        
        writters--;
        
        return val;
        
    }
}
