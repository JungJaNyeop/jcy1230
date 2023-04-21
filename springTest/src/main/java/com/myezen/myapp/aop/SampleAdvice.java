package com.myezen.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice {

	/*�α� ���*/
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
			/*���� ����	com.myezen.myapp.service.BoardService�� �����ϴ� ��� ��ü*/	
	@Before("execution(* com.myezen.myapp.service.BoardService*.*(..))")
	public void startLog() {
		
		logger.info("-------------");
		logger.info("aop �α��׽�Ʈ ��");
		logger.info("-------------");
		System.out.println("sysout �Դϴ�.");
		
	}
	
}