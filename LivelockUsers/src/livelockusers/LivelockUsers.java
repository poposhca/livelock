/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package livelockusers;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author poposhca
 */
public class LivelockUsers 
{
    public static void main(String[] args) 
    {
        int port = 5000;
        int numUsers = 50;
        int numMessages = 20;
        int idleTime = 10;
        int rate = 6;
        PackTable messgTable = new PackTable();
        
        
        //Enciende servidor para checar paquetes
        PackCheck checker = new PackCheck(messgTable);
        checker.start();
        
        for(int j = 0; j < rate; j++)
        {
        
            //Enciende usuarios
            List<User> users = new ArrayList<>();
            for(int i = 0; i < numUsers; i++)
            {
                User u = new User(i, port, numMessages, idleTime, messgTable);
                users.add(u);
                u.start();
            }

            //Joind de todos los usuarios
            for(int i = 0; i < numUsers; i++)
            {
                try
                {
                    users.get(i).join();
                }
                catch(InterruptedException e)
                {
                    System.err.println("ERROR JOIN THREADS: " + e.getMessage());
                }
            }

            //Imprimir mensajes perdidod
            System.out.println("Se terminó de mandar mensajes");
        
        }
        
    }
    
}
