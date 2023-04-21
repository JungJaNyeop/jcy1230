package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.MemberVo;

public interface MemberService {/*메소드 이름만 설계만하고 구현은 하지않음*/
	
	/*회원가입*/
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone, String memberEmail, String memberGender, String memberAddr, String memberBirth);
	/*회원 목록*/
	public ArrayList<MemberVo> memberList();
	/*id 중복체크*/
	public int memberIdCheck(String memberId);
	/*로그인*/
	public MemberVo memberLogin(String memberId);
}
