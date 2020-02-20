package com.shsxt.server.core;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

//import com.sxt.server.basic.servlet.Servlet;
//import com.sxt.server.basic.servlet.WebContext;

public class WebApp {
	private static WebContext webContext;
   static {
	   try {
		 //SAX����
			 //1����ȡ��������
			 SAXParserFactory factory = SAXParserFactory.newInstance();
			 //2���ӽ���������ȡ������
			 SAXParser parse = factory.newSAXParser();
			 //3����д������
			 //4�������ĵ�Documentע�ᴦ����
			 WebHandler handler= new WebHandler();
			 //5������
			parse.parse(Thread.currentThread().getContextClassLoader().
					getResourceAsStream("web.xml"), handler);
			//��ȡ����
			 webContext = new WebContext(handler.getEntitys(),handler.getMappings());
	   }catch(Exception e) {
		   System.out.println("���������ļ�����");
	   }
   }
   /**
    * ͨ��url��ȡ�����ļ���Ӧ��servlet
    * @param url
    * @return
    */
     public static Servlet getServletFromUrl(String url) {
    		//������������ /login
    		String className = webContext.getClz("/"+url);
    		Class clz;
			try {
				System.out.println(url+"--->"+className+"--->");
				clz = Class.forName(className);
				 Servlet servlet = (Servlet)clz.getConstructor().newInstance();
				 return servlet;
			} catch (Exception e) {
				
			}
    		return null;
    	  
      }
}
