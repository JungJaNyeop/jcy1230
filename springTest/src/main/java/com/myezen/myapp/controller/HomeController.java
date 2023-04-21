package com.myezen.myapp.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller /*��Ʈ�ѷ� �뵵�� ��ü���� ��û (bean��ϰ� ����)*/
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/*�����ο� �޼ҵ带 ���۽�Ű�� Ŭ���� RequestMapping*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );/*model(.addAtrribute) = java���� request.setAttribute�� ���� ����*/
		
		return "home"; /*views�� �ִ� jsp���Ͽ��� Ȯ���ڸ� �� �̸�*/
	}
	
}
