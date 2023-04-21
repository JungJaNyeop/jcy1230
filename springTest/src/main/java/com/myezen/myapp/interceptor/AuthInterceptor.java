package com.myezen.myapp.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {	/*2023.04.18 ���ͼ���*/
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { //�ڵ鷯�� �����ϱ����� ������
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") == null) { //�α����� ������ ��
			
			saveDest(request);	//�α��� �� �̵��� �ּҸ� ����
			
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
		}
		
		return true;
	}
	
	private void saveDest(HttpServletRequest request) {//���� �ִ� ������ �ּҸ� ã�� �޼ҵ�
		
		String root = request.getContextPath();	
		String uri = request.getRequestURI().substring(root.length());
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";
		}else {
			query = "?" + query;
		}
		
		if(request.getMethod().equals("GET")) {
			request.getSession().setAttribute("dest", uri + query); //���ǿ� ����
		}
		
	}
	
	//bean�� �ּ� ����ϱ�
	
}
