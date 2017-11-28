/*
 * Buffer.java
 * Versão: 1.0
 * Data de Criação: 20/11/2017
 * Copyright (c) 2017 UFG - www.ufg.br
 * Todos os direitos reservados.
 *
 * Este software tem o propósito de mostrar o funcionamento
 * do algoritmo Produtor-Consumidor com a utilização da conexão
 * com o buffer via RMI.
 */
package produtorconsumidor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;


/**
 * Classe que implementa o Buffer usando a interface BufferInterface.
 * 
 * @author Samuel Costa, João Pedro, João Paulo, Osmar
 * @version 1.0 - 20/11/2017
 * @since 20/11/2017
 */
public class Buffer extends UnicastRemoteObject implements BufferInterface{
    
    //Tamanho do Buffer
    private static final int N = 5;
    
    //Inicialização do vetor de Buffer
    private int[] B = new int[N];
    
    //Fila de entrada
    private int InPtr = 0;
    
    //Fila de Saída
    private int OutPtr = 0;
    
    //Contador de números no buffer
    private int Count = 0;
    
    /**
    * Construtor da classe Buffer.
    * 
    * @throws RemoteException
    */
    public Buffer() throws RemoteException {
        super();
    } 
    
    /**
     * Este método pega o próximo número no vetor B (uso do Consumidor)
     * e notifica os Threads
     * 
     * @return int para o número retirado do vetor B
     * 
     * @throws RemoteException
     */
    @Override
    public synchronized int take() throws RemoteException {
        
        //Primeiro verifica se o buffer está vazio, caso esteja, aguarda.
        while (Count == 0) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        
        //Pega o valor de saída no vetor
        int I = B[OutPtr];
        
        //Define a próxima posição que irá sair do vetor
        //Nunca passa de 4 pelo cálculo com resto
        OutPtr = (OutPtr+1) % N;
        
        //Retira um do contador de números no buffer
        Count = Count-1;
        
        //Notifica e retorna o número retirado
        notifyAll();
        return I;
    }
    
    /**
     * Este método preenche o vetor com um número (uso do Produtor)
     * e notifica os Threads
     * 
     * @param value int para o número a ser colocado no vetor B
     * @throws RemoteException
     */
    @Override
    public synchronized void append(int value) throws RemoteException {
        
        //Primeiro verifica se o buffer não está cheio, caso esteja, aguarda.
        while (Count == N) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        
        //Coloca o valor inserido dentro do vetor de buffer
        B[InPtr] = value;
        
        //Define a próxima posição do vetor a ser preenchida
        //Nunca passará de 4 pelo cálculo com o resto
        InPtr = (InPtr+1) % N;
        
        //Incrementa 1 no contador de números no buffer e notifica
        Count = Count+1;
        notifyAll();
    }
      
    /**
     * Método que executa o Buffer e deixa na classe disponível no endereço:
     * rmi://localhost:1099/BUFFER
     * 
     * @param args
     * @throws MalformedURLException
     */
    public static void main(String args[]) throws MalformedURLException{
        try{
            //Define o security manager
            System.setSecurityManager(new RMISecurityManager());
            
            //Cria uma instância local da classe
            Buffer buffer = new Buffer();
            
            //Cria o registro da porta 1099
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            
            //Cria o endereço
            Naming.rebind("rmi://localhost:1099/BUFFER", buffer);  
            System.out.println("Buffer aguardando conexão.....");
        }
        catch (RemoteException re) {
            System.out.println("Remote exception: " + re.toString());
        }
    }

}