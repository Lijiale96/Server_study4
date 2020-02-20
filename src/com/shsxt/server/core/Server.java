package com.shsxt.server.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/*
 * Ŀ��:����404 505 ����ҳ
 *
 * 
 * 
 */
public class Server {
	private ServerSocket serverSocket;
	private boolean isRunning;
	public static void main(String[] args) {
	
		Server server = new Server();
		server.start();
		
	}
	//��������
	public void start() {
		
		try {
			serverSocket = new ServerSocket(8888);
			isRunning = true;
			 receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("����������ʧ��...");
			 stop();
		}		
	}
	//�������Ӵ���
    public void receive() {
    	while(isRunning) {
    	try {
			Socket client = serverSocket.accept();
			System.out.println("һ���ͻ��˽��������ӡ�����");
		//���̴߳���
			new Thread(new Dispatcher(client)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�ͻ��˴���");
		}
	}    
    }
    //ֹͣ����
    public void stop() {
		isRunning = false;
		try {
			this.serverSocket.close();
			System.out.println("��������ֹͣ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
