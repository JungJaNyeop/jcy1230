package com.myezen.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public void postHandle(	/*로그인 한 이후에 세션 담는 방법*/								/*정보를 담아서 화면까지 가져가는 클래스*/
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		
		/*세션에 정보를 담는 곳*/
		Object midx = modelAndView.getModel().get("midx");
		Object memberName = modelAndView.getModel().get("memberName");
				
		if(midx != null) {
			request.getSession().setAttribute("midx", midx);
			request.getSession().setAttribute("memberName", memberName);			
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {	//세션값 초기화
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") != null ) {	//로그인을 안했는데 세션에 정보가 담겨있으면
			session.removeAttribute("midx");
			session.removeAttribute("memberName");
			session.invalidate();					//초기화
		}
		
		return true;
	}
	//bean에 등록 하기
	
}
