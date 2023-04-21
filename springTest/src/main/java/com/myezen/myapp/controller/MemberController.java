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
	
	@Autowired /*아래 선언한 변수에 주입시킴*/
	MemberService ms;
	
	@Autowired/*비밀번호 암호화*/
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/*회원가입 페이지 이동 시작*/
	@RequestMapping(value="/MemberJoin.do")
	public String memberJoin() {
		

		return "member/MemberJoin";
	}/*회원가입 페이지 이동 끝*/
	
	/*회원가입 시작*/
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
		
		return "redirect:/"; /*sendRedirect방식*/
	}/*회원가입 끝*/
	
	/*회원 목록보기*/
	@RequestMapping(value="/memberList.do")
	public String memberList(Model model) {
		
		ArrayList<MemberVo> alist = ms.memberList();
		
		model.addAttribute("alist", alist);
				
		return "member/memberList"; /*forward 방식*/
	}/*회원 목록보기 끝*/
	
	/*id 중복체크*/
	@ResponseBody /*리턴값 객체로 리스폰스 함*/
	@RequestMapping(value="/memberIdCheck.do")
	public String memberIdCheck(@RequestParam("memberId") String memberId) {		
		String str = null;
		int value=0;
		
		value = ms.memberIdCheck(memberId);
		
		/*json형태로 맞추기*/
		str = "{\"value\":\"" + value + "\"}";
		return str;
	}
	
	/*로그인 페이지 이동*/
	@RequestMapping(value="/memberLogin.do")
	public String memberLogin() {
		

		return "member/memberLogin"; 
	}
	/*로그인*/
	@RequestMapping(value="/memberLoginAction.do")
	public String memberLoginAction(
			@RequestParam("memberId") String memberId,
			@RequestParam("memberPwd") String memberPwd,
			HttpSession session,
			RedirectAttributes rttr /*1회성*/

			) {
		
		MemberVo mv = ms.memberLogin(memberId);
		//System.out.println("mv"+mv);
		String path = "";
						/*암호화된 비밀번호와 비교*/		/*입력된 비밀번호, db에 담긴 비밀번호*/
		if(mv!=null && bcryptPasswordEncoder.matches(memberPwd, mv.getMemberpwd())) {
			/*session에 담는 정보*/
			rttr.addAttribute("midx", mv.getMidx());
			rttr.addAttribute("memberName", mv.getMembername());
			
		//	System.out.println("dest" + session.getAttribute("dest"));
			
			if(session.getAttribute("dest") == null) {
				path = "redirect:/";
			}else {
				String dest = (String)session.getAttribute("dest");
				
				path = "redirect:" + dest;	//세션 값이 존재하면
			}
			
		// path = "redirect:/"; /*비밀번호가 같으면 메인페이지로*/
		}else{
			rttr.addFlashAttribute("msg","아이디와 비밀번호를 확인해주세요.");
			path = "redirect:/member/memberLogin.do";/*비밀번호 같으면 그대로 로그인 페이지*/
		}
				
		return path;
	}/*로그인 끝*/
	
	/*로그아웃*/
	@RequestMapping(value="/memberLogOut.do")
	public String memberLogOut(HttpSession session) {
		session.removeAttribute("midx");		//  저장된 값
		session.removeAttribute("memberName");	//	      지우기
		session.invalidate();	/*세션 사라지게 초기화*/
		
		return "redirect:/";
	}/*로그아웃 끝*/

		
}
