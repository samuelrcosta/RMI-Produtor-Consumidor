/*
 * Produtor.java
 * Versão: 1.1
 * Data de Criação: 20/11/2017
 * Copyright (c) 2017 UFG - www.ufg.br
 * Todos os direitos reservados.
 *
 * Este software tem o propósito de mostrar o funcionamento
 * do algoritmo Produtor-Consumidor com a utilização da conexão
 * com o buffer via RMI.
 */
package produtorconsumidor;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que executa o produtor dentro do Buffer.
 * 
 * @author Samuel Costa, João Pedro, João Paulo, Osmar
 * @version 1.1 - 21/11/2017
 * @since 20/11/2017
 */
public class Produtor extends Thread{
    private final BufferInterface buffer;
    private final int mensagens;
    
    /**
    * Construtor da classe Produtor, recebe o Buffer.
    *
    * @param b BufferInterface
    * @param mensagens int para o número de mensagens a serem colocadas no buffer
    */
    public Produtor(BufferInterface b, int mensagens) {
        this.buffer = b;
        this.mensagens = mensagens;
    }

    /**
     * Este método adiciona números no buffer de acordo com o número de mensagens
     * definidas no construtor usando um for.
     * Faz a adição dos números e depois coloca a Thread para dormir (sleep)
     */
    @Override
    public void run() {
        for (int i = 1; i <= this.mensagens; i++) {
            try {
                //Executa o método que adiciona o valor i no buffer
                buffer.append(i);
            } catch (RemoteException ex) {
                Logger.getLogger(Produtor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //Exibe na tela o número inserido
            System.out.println("-----------------------\nPRODUTOR colocou: " + i);
            try {
                sleep((int)(Math.random() * 100));
            } catch (InterruptedException e) { }
        }
    }
}
