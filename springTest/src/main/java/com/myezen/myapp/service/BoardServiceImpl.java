package com.myezen.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;
import com.myezen.myapp.persistance.BoardService_Mapper;


@Service("boardServiceImpl")
public class BoardServiceImpl implements BoardService{
	/*�ڵ� ����ȭ*/
	private BoardService_Mapper bsm; 	
	
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {
		this.bsm = sqlSession.getMapper(BoardService_Mapper.class);
	}/*--*/
		
	/*�Խñ� ���*/
	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		
		ArrayList<BoardVo> blist = bsm.boardSelectAll(scri); /*mybatis ȣ��*/
		
		return blist;
	}/*�Խñ� ��� */
	
	/*����¡ - ������ �ѹ���*/
	@Override
	public int boardTotal(SearchCriteria scri) {
		
		int value = bsm.boardTotal(scri);
		
		return value;
	}
	
	/*�Խñ� ����*/
	@Override
	public BoardVo boardSelectOne(int bidx) {

		BoardVo bv = bsm.boardSelectOne(bidx);
		
		return bv;
	}

	/*��ȸ�� ���� �޼ҵ�*/
	@Override
	public int boardViewCnt(int bidx) {
		
		int value = bsm.boardViewCnt(bidx);
		
		return value;
	}
	
	/*�� ���� �޼ҵ�*/
	@Override
	public int boardInsert(BoardVo bv) {
		
		int value = bsm.boardInsert(bv);
		
		return value;
	}

	@Override
	public int boardModify(BoardVo bv) {
		
		int value = bsm.boardModify(bv);
						
		return value;
	}

	@Override
	public int boardDelete(BoardVo bv) {
		
		int value = bsm.boardDelete(bv);
		
		return value;
	}
	
	@Transactional /*Ʈ����� : ������ ��� �����ϸ� Ŀ��, �ϳ��� �����ϸ� �ѹ�*/
	@Override
	public int boardReply(BoardVo bv) {
		
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		System.out.println(bv.getOriginbidx());
		hm.put("originbidx", bv.getOriginbidx());
		hm.put("depth", bv.getDepth());
		
		bsm.boardReplyUpdate(hm);
		int value = bsm.boardReplyInsert(bv);		
		
		return value;
	}

	
}
