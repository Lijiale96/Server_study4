package com.shsxt.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.file.Files;

/**
 * �ַ���������״̬���ݴ��� 404 505 ��ҳ
 * @author A
 *
 */
public class Dispatcher implements Runnable{
	private Socket client;
	private Request request;
	private Response response;
	
  public Dispatcher(Socket client) {
	  this.client=client;
	  try {
			//��ȡ����Э��
			//��ȡ��ӦЭ��
		request = new Request(client);
		  response = new Response(client);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	this.release();
	}
	
  }
	@Override
	public void run() {
	
	   try {
			if (null==request.getUrl()||request.getUrl().equals("")) {
				InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
				//response.print((new String(is.read()))); //
				response.pushToBrowser(200);
				is.close();
				return ;
			}
		 Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
		 if (null!=servlet) {
			servlet.service(request, response);
			 //��ע��״̬��
			response.pushToBrowser(200);
		}else {
			//���󡣡���
		InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
		//response.print((new String(is.readALLBytes()))); //
	    response.pushToBrowser(404);
		}
	   }catch(Exception e) {
		   try {
			   response.println("����Ҳ��ã��һ����Ϻ�");
			response.pushToBrowser(500);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
	   }
	   release();
	}

//�ͷ���Դ	
	private void release() {
		try {
			client.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
