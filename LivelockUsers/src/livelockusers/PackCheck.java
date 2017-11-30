package livelockusers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author poposhca
 */
public class PackCheck extends Thread
{
    
    private Boolean run = true;
    private PackTable messgTable;
    
    public PackCheck(PackTable messgTable)
    {
        this.messgTable = messgTable;
    }
    
    @Override public void run()
    {
        RunServer();
    }
        
    public void RunServer()
    {
        BufferedReader in;
        ServerSocket socket = null;
        Socket messg;
        
        try
        {
            socket = new ServerSocket(5001);
        }
        catch(IOException e)
        {
            System.err.println("Error al abrir socket: "+ e.getMessage());
            System.exit(9);
        }
        
        System.out.println("Listo servidor para checar paquetes");
        
        while(run)
        {
            try
            {
                //Read message
                messg = socket.accept();
                in = new BufferedReader(new InputStreamReader(messg.getInputStream()));
                String resMessg = in.readLine();
                String strMessg[] = resMessg.split("-");
                String key = strMessg[0];
                String timeRecived = strMessg[1];
                //Check recived pack
                UserStatistics stat = messgTable.RemoveRecivedMessage(key);
                stat.confirmed = true;
                stat.timestapRecived = timeRecived;
                //Imprimir etadistica
                System.out.println(key + "," + stat.timestapSend + "," + stat.timestapRecived + "," + stat.confirmed + "," + messgTable.GetTableCount());
            }
            catch(IOException e)
            { 
                System.err.println("ERROR AL RECIBIR MENSAJE: "+ e.getMessage());
            }
        }
        
        //Cerrera server
        try
        {
            socket.close();
        }
        catch(IOException e)
        {
            System.err.println("ERROR AL CERRAR SOCKET: " + e.getMessage());
        }
    }
    
    public void StopServer()
    {
        this.run = false;
        System.out.println("Servidor detenido");
    }
    
}
