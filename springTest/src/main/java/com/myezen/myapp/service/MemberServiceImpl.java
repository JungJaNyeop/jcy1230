package com.myezen.myapp.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;	/*ibatis = mybatis*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myezen.myapp.domain.MemberVo;
import com.myezen.myapp.persistance.MemberService_Mapper;

@Service("memberServiceImpl") /*���� �뵵�� ���۳�Ʈ �� ���*/
public class MemberServiceImpl implements MemberService{/*�����ϴ� Ŭ����*/
	
	/*�ڵ� ����ȭ*/
	private MemberService_Mapper msm; 	
			
	@Autowired
	public MemberServiceImpl(SqlSession sqlSession) {
		this.msm = sqlSession.getMapper(MemberService_Mapper.class);
	}
	/*�޼ҵ帶�� ����� MemberService_Mapper�� ��������ȭ(?)��Ŵ*/
	
	/*ȸ������*/
	@Override
	public int memberInsert(String memberId, String memberPwd, String memberName, String memberPhone,
			String memberEmail, String memberGender, String memberAddr, String memberBirth) {
		
		MemberVo mv = new MemberVo();
		mv.setMemberid(memberId);
		mv.setMemberpwd(memberPwd);
		mv.setMembername(memberName);
		mv.setMemberphone(memberPhone);
		mv.setMemberemail(memberEmail);
		mv.setMembergender(memberGender);
		mv.setMemberaddr(memberAddr);
		mv.setMemberbirth(memberBirth);
		
		int value = msm.memberInsert(mv);
		
		return value;
	}
	
	/*ȸ�� ���*/
	@Override
	public ArrayList<MemberVo> memberList(){

		ArrayList<MemberVo> alist = msm.memberList();
		
		return alist;
	}

	@Override
	public int memberIdCheck(String memberId) {
		
		int value = msm.memberIdCheck(memberId);
		
		return value;
	}

	@Override
	public MemberVo memberLogin(String memberId) {
		
		MemberVo mv = msm.memberLogin(memberId);
		
		return mv;
	}
	
}
