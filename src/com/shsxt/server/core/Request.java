package com.shsxt.server.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��װ����Э�飺��װ�������ΪMap
 * 
 * @author A
 *
 */
public class Request {
	//Э����Ϣ
	private String requestInfo;
	//����ʽ
	private String method;
	//����uri
	private String url;
	//�������
	private final String CRLF ="\r\n";
	//����ʽ
	private String queryStr;
	//�洢����
	private Map<String,List<String>>parameterMap;
	
	 public Request(Socket client) throws IOException {
	    	this(client.getInputStream());
	    }
    public Request(InputStream is) {
    	parameterMap = new HashMap<String,List<String>>();
    	
		byte[] datas = new byte[1024*1024*1024];
		int len;
		try {
			len= is.read(datas);
			this.requestInfo = new String(datas,0,len);
			
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		}
	//�ֽ��ַ���
		parseRequsetInfo();
    }
    
    private void parseRequsetInfo() {
    	System.out.println("-------�ֽ�------");
    	System.out.println("---1����ȡ����ʽ:��ͷ����һ��/-----");
    	this.method=this.requestInfo.substring(0,this.requestInfo.indexOf("/")).toLowerCase();
    	this.method.trim();
    	System.out.println("---2����ȡ����uri:��һ��/ �� HTTP/-----");
    	System.out.println("---���ܰ������������ǰ���Ϊurl-----");
    	//1������ȡ/��λ��
    	int startIdx= this.requestInfo.indexOf("/")+1;
    	//2������ȡHTTP/��λ��
    	int endIdx= this.requestInfo.indexOf("HTTP/");
        //3�����ָ��ַ���
    	this.url = this.requestInfo.substring(startIdx,endIdx).trim(); 	
    	//4)����ȡ����λ��
    	int queryIdx =this.url.indexOf("?");
    	if (queryIdx>=0) {//��ʾ�����������
    		String[] urlArray = this.url.split("\\?");
    		this.url = urlArray[0];
    		queryStr = urlArray[1];
		}
    	System.out.println(this.url);
    	
    	System.out.println("---3����ȡ�������:���Get�Ѿ���ȡ�������post��������������----");
    	
    
    	if (method.equals("post")) {
			String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
			System.out.println(qStr+"-->");
		if (null==queryStr) {
			queryStr =qStr;
		}else {
			queryStr+="&"+qStr;
		}		
	   }
    	queryStr=null==queryStr?"":queryStr;
    	System.out.println(method.trim()+"->"+url+"->"+queryStr);
    	//ת��Map fav=1&fav=2&uname=shsxt&age=18 
    	convertMap();
    	
     }
    //�����������ΪMap
    private void convertMap() {
    	//1���ָ��ַ��� &
    	String[] keyValues = this.queryStr.split("&");
    	for (String queryStr : keyValues) {
		//2���ٴηָ��ַ��� = 
    	String[] kv = queryStr.split("=");
    	kv = Arrays.copyOf(kv, 2);
    	//��ȡkey��value
    	String key = kv[0];
    	String value=kv[1]==null?null:decode(kv[1],"utf-8");
    	//�洢��map��
    	if (!parameterMap.containsKey(key)) {//��һ��
    		parameterMap.put(key, new ArrayList<String>());
			
		}
    	parameterMap.get(key).add(value);

		}
    }
    /**
     * ��������
     * @return
     */
    private String decode(String value,String enc) {
    	try {
			return java.net.URLDecoder.decode(value, enc);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    /**
     * ͨ��name��ȡ�Ķ�Ӧ�Ķ��ֵ
     * 
     * @param key
     * @return
     */
    public String[] getParameterValues(String key) {
    	List<String> list = this.parameterMap.get(key);
    	if (null==list||list.size()<1) {
			return null;
		}
    	return list.toArray(new String[0]);
    }
    /**
     * ͨ��name��ȡ�Ķ�Ӧ��һ��ֵ
     * @param key
     * @return
     */
    public String getParameter(String key) {
    	String[] values = getParameterValues(key);
    	return values ==null?null:values[0];
    }
	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getQueryStr() {
		return queryStr;
	}

}
