package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.MemberVo;

public interface MemberService {/*�޼ҵ� �̸��� ���踸�ϰ� ������ ��������*/
	
	/*ȸ������*/
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone, String memberEmail, String memberGender, String memberAddr, String memberBirth);
	/*ȸ�� ���*/
	public ArrayList<MemberVo> memberList();
	/*id �ߺ�üũ*/
	public int memberIdCheck(String memberId);
	/*�α���*/
	public MemberVo memberLogin(String memberId);
}
