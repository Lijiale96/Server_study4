package com.shsxt.user;

import com.shsxt.server.core.Request;
import com.shsxt.server.core.Response;
import com.shsxt.server.core.Servlet;

public class RegisterServlet implements Servlet {

	@Override
	public void  service(Request request,Response response) {
		//��עҵ���߼�
		String uname = request.getParameter("uname");
		String[] favs = request.getParameterValues("fav");
		response.print("<html>");
		response.print("<head>");
		//response.print(" <meta http-equiv=\"content-type\"content=\"text/html;charset=utf-8\">");
		response.print("<title>");
		response.print("ע��ɹ�");
		response.print("</title>");
		response.print("<head>");
		response.print("<body>");
		response.println("��ע�����ϢΪ��"+uname);
		response.println("��ϲ��������Ϊ��");
		for (String v : favs) {
			if (v.equals("0")) {
				response.print("������");			
			}else if (v.equals("1")) {
				response.print("������");		
			}else if (v.equals("2")) {
				response.print("��Ů��");		
			}else if (v.equals("3")) {
				response.print("ɵ����");		
			}
		}
		response.print("<html>");
		response.print("<head>");
	}

}
