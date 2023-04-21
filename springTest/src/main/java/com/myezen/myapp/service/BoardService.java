package com.myezen.myapp.service;

import java.util.ArrayList;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;

public interface BoardService {
	
	/*�Խñ� ���*/
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);
	
	/*����¡ - ������ �ѹ���*/
	public int boardTotal(SearchCriteria scri);
	
	/*�Խñ� ����*/
	public BoardVo boardSelectOne(int bidx);
	
	/*��ȸ�� ���� �޼ҵ�*/
	public int boardViewCnt(int bidx);
	
	/*�� ���� �޼ҵ�*/
	public int boardInsert(BoardVo bv);
	
	/*�� ���� �޼ҵ�*/
	public int boardModify(BoardVo bv);
	
	/*�� ���� �޼ҵ�*/
	public int boardDelete(BoardVo bv);
	
	/*�亯 ��� �޼ҵ�*/
	public int boardReply(BoardVo bv);
}
