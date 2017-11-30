/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockusers;

/**
 *
 * @author poposhca
 */
public class UserStatistics 
{
    public String timestapSend;
    public String timestapRecived;
    public Boolean confirmed;
    
    public UserStatistics(String timestapSend)
    {
        this.timestapSend = timestapSend;
        this.timestapRecived = "";
        this.confirmed = false;
    }
}
