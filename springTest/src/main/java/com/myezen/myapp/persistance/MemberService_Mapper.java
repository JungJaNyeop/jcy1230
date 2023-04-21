package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.MemberVo;



public interface MemberService_Mapper {
	
	//마이바티스의 사용할 메소드를 정의한다
	
	//public MemberVo memberLogin(String id, String pwd);
	/*회원 등록*/	
	public int memberInsert(MemberVo mv);
	/*회원 목록*/
	public ArrayList<MemberVo> memberList();
	/*id중복 체크*/
	public int memberIdCheck(String memberId);
	/*로그인*/
	public MemberVo memberLogin(String memberId);
}
