package com.myezen.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Override
	public void postHandle(	/*�α��� �� ���Ŀ� ���� ��� ���*/								/*������ ��Ƽ� ȭ����� �������� Ŭ����*/
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		
		/*���ǿ� ������ ��� ��*/
		Object midx = modelAndView.getModel().get("midx");
		Object memberName = modelAndView.getModel().get("memberName");
				
		if(midx != null) {
			request.getSession().setAttribute("midx", midx);
			request.getSession().setAttribute("memberName", memberName);			
		}
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {	//���ǰ� �ʱ�ȭ
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute("midx") != null ) {	//�α����� ���ߴµ� ���ǿ� ������ ���������
			session.removeAttribute("midx");
			session.removeAttribute("memberName");
			session.invalidate();					//�ʱ�ȭ
		}
		
		return true;
	}
	//bean�� ��� �ϱ�
	
}
