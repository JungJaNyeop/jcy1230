package com.myezen.myapp.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthInterceptor extends HandlerInterceptorAdapter {	/*2023.04.18 인터셉터*/
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { //핸들러가 시작하기전에 동작함
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") == null) { //로그인을 안했을 때
			
			saveDest(request);	//로그인 후 이동할 주소를 담음
			
			response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
		}
		
		return true;
	}
	
	private void saveDest(HttpServletRequest request) {//원래 있던 페이지 주소를 찾는 메소드
		
		String root = request.getContextPath();	
		String uri = request.getRequestURI().substring(root.length());
		String query = request.getQueryString();
		
		if(query == null || query.equals("null")) {
			query = "";
		}else {
			query = "?" + query;
		}
		
		if(request.getMethod().equals("GET")) {
			request.getSession().setAttribute("dest", uri + query); //세션에 담음
		}
		
	}
	
	//bean에 주소 등록하기
	
}
