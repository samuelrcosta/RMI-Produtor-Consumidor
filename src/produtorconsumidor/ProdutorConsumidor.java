/*
 * ProdutorCosumidor.java
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

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/**
 * Classe que executa o Consumidor e o Produtor na classe Buffer via RMI.
 * 
 * @author Samuel Costa, João Pedro, João Paulo, Osmar
 * @version 1.1 - 21/11/2017
 * @since 20/11/2017
 */
public class ProdutorConsumidor {
    
    /**
    * Construtor da classe ProdutorConsumidor.
    * 
    * @throws RemoteException
    */
    private ProdutorConsumidor() {}

    /**
     * Método que executa o Consumidor e o Produtor na classe Buffer na porta 1099
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            //Inincializa a variável de Scanner e a variável que armazenará o número de mensagens
            //a serem trocadas
            Scanner scan = new Scanner(System.in);
            int mensagens = 0;
            
            //Instancia a classe buffer que está na porta 1099 no registro BUFFER
            Registry registry = LocateRegistry.getRegistry(1099);
            BufferInterface b = (BufferInterface)registry.lookup("BUFFER");
            
            //Pega do usuário o número de mensagens a serem trocadas entre o
            //consumidor e o produtor
            System.out.println("--- PRODUTOR - CONSUMIDOR ---");
            System.out.println("Digite o número de mensagens a serem enviadas pelo produtor");
            System.out.print("e que serão retiradas pelo consumidor: ");
            mensagens = scan.nextInt();
            
            //Intancia as classes produtor e consumidor
            Produtor p = new Produtor(b, mensagens);
            Consumidor c = new Consumidor(b, mensagens);
            
            //Incia os Threads
            p.start();
            c.start();
            
        } catch (NotBoundException | RemoteException e) {
            System.err.println("Client exception: " + e.toString());
        }
    }
}