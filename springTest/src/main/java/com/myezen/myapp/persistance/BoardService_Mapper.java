package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;

public interface BoardService_Mapper {
	
	/*�Խñ� ���*/
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);
	
	/*����¡ - ������ �ѹ���*/
	public int boardTotal(SearchCriteria scri);
	
	/*�Խñ� ���� -- 2023.04.14*/
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
//	public int boardReplyUpdate(int originbidx, int depth);
	public int boardReplyUpdate(HashMap hm);
	public int boardReplyInsert(BoardVo bv);
	/*�亯 ��� �޼ҵ� ��*/
}
