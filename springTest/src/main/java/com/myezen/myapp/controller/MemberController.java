package com.myezen.myapp.controller;

import java.util.ArrayList;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myezen.myapp.domain.MemberVo;
import com.myezen.myapp.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {
	
	@Autowired /*�Ʒ� ������ ������ ���Խ�Ŵ*/
	MemberService ms;
	
	@Autowired/*��й�ȣ ��ȣȭ*/
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/*ȸ������ ������ �̵� ����*/
	@RequestMapping(value="/MemberJoin.do")
	public String memberJoin() {
		

		return "member/MemberJoin";
	}/*ȸ������ ������ �̵� ��*/
	
	/*ȸ������ ����*/
	@RequestMapping(value="/memberJoinAction.do")
	public String memberJoinAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,
			@RequestParam("memberName") String memberName,
			@RequestParam("memberPhone") String memberPhone,
			@RequestParam("memberEmail") String memberEmail,
			@RequestParam("memberGender") String memberGender,
			@RequestParam("memberAddr") String memberAddr,
			@RequestParam("memberBirth") String memberBirth			
			) {
			
			String memberPwd2 = bcryptPasswordEncoder.encode(memberPwd);
			int value = ms.memberInsert(memberId, memberPwd2, memberName, memberPhone, memberEmail, memberGender, memberAddr, memberBirth);
		
		return "redirect:/"; /*sendRedirect���*/
	}/*ȸ������ ��*/
	
	/*ȸ�� ��Ϻ���*/
	@RequestMapping(value="/memberList.do")
	public String memberList(Model model) {
		
		ArrayList<MemberVo> alist = ms.memberList();
		
		model.addAttribute("alist", alist);
				
		return "member/memberList"; /*forward ���*/
	}/*ȸ�� ��Ϻ��� ��*/
	
	/*id �ߺ�üũ*/
	@ResponseBody /*���ϰ� ��ü�� �������� ��*/
	@RequestMapping(value="/memberIdCheck.do")
	public String memberIdCheck(@RequestParam("memberId") String memberId) {		
		String str = null;
		int value=0;
		
		value = ms.memberIdCheck(memberId);
		
		/*json���·� ���߱�*/
		str = "{\"value\":\"" + value + "\"}";
		return str;
	}
	
	/*�α��� ������ �̵�*/
	@RequestMapping(value="/memberLogin.do")
	public String memberLogin() {
		

		return "member/memberLogin"; 
	}
	/*�α���*/
	@RequestMapping(value="/memberLoginAction.do")
	public String memberLoginAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,
			HttpSession session,
			RedirectAttributes rttr /*1ȸ��*/

			) {
		
		MemberVo mv = ms.memberLogin(memberId);
		//System.out.println("mv"+mv);
		String path = "";
						/*��ȣȭ�� ��й�ȣ�� ��*/		/*�Էµ� ��й�ȣ, db�� ��� ��й�ȣ*/
		if(mv!=null && bcryptPasswordEncoder.matches(memberPwd, mv.getMemberpwd())) {
			/*session�� ��� ����*/
			rttr.addAttribute("midx", mv.getMidx());
			rttr.addAttribute("memberName", mv.getMembername());
			
		//	System.out.println("dest" + session.getAttribute("dest"));
			
			if(session.getAttribute("dest") == null) {
				path = "redirect:/";
			}else {
				String dest = (String)session.getAttribute("dest");
				
				path = "redirect:" + dest;	//���� ���� �����ϸ�
			}
			
		// path = "redirect:/"; /*��й�ȣ�� ������ ������������*/
		}else{
			rttr.addFlashAttribute("msg","���̵�� ��й�ȣ�� Ȯ�����ּ���.");
			path = "redirect:/member/memberLogin.do";/*��й�ȣ ������ �״�� �α��� ������*/
		}
				
		return path;
	}/*�α��� ��*/
	
	/*�α׾ƿ�*/
	@RequestMapping(value="/memberLogOut.do")
	public String memberLogOut(HttpSession session) {
		session.removeAttribute("midx");		//  ����� ��
		session.removeAttribute("memberName");	//	      �����
		session.invalidate();	/*���� ������� �ʱ�ȭ*/
		
		return "redirect:/";
	}/*�α׾ƿ� ��*/

		
}
