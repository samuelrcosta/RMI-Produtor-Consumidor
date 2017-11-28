/*
 * BufferInterface.java
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

import java.rmi.Remote;
import java.rmi.RemoteException; 

/**
 * Classe que implementa a interface do Buffer.
 * 
 * @author Samuel Costa, João Pedro, João Paulo, Osmar
 * @version 1.0 - 20/11/2017
 * @since 20/11/2017
 */
public interface BufferInterface extends Remote{
    static final int N = 5;
    int[] B = new int[N];
    int InPtr=0, OutPtr=0;
    int Count=0;
    
    public int take() throws RemoteException;
    public void append(int value) throws RemoteException;
}
