package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.MemberVo;



public interface MemberService_Mapper {
	
	//���̹�Ƽ���� ����� �޼ҵ带 �����Ѵ�
	
	//public MemberVo memberLogin(String id, String pwd);
	/*ȸ�� ���*/	
	public int memberInsert(MemberVo mv);
	/*ȸ�� ���*/
	public ArrayList<MemberVo> memberList();
	/*id�ߺ� üũ*/
	public int memberIdCheck(String memberId);
	/*�α���*/
	public MemberVo memberLogin(String memberId);
}
