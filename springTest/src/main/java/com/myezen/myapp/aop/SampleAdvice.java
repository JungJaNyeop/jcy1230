package com.myezen.myapp.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice {

	/*로그 찍기*/
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
			/*범위 지정	com.myezen.myapp.service.BoardService로 시작하는 모든 객체*/	
	@Before("execution(* com.myezen.myapp.service.BoardService*.*(..))")
	public void startLog() {
		
		logger.info("-------------");
		logger.info("aop 로그테스트 중");
		logger.info("-------------");
		System.out.println("sysout 입니다.");
		
	}
	
}
