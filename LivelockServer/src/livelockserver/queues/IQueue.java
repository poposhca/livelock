/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockserver.queues;

/**
 *
 * @author poposhca
 */
public interface IQueue
{
    public void Queue(String message);
    public String UnQueue();
    public Boolean QueueHasElements();
}
