package com.myezen.myapp.persistance;

import java.util.ArrayList;
import java.util.HashMap;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.SearchCriteria;

public interface BoardService_Mapper {
	
	/*게시글 목록*/
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri);
	
	/*페이징 - 페이지 넘버바*/
	public int boardTotal(SearchCriteria scri);
	
	/*게시글 보기 -- 2023.04.14*/
	public BoardVo boardSelectOne(int bidx);
	
	/*조회수 증가 메소드*/
	public int boardViewCnt(int bidx);
	
	/*글 쓰기 메소드*/
	public int boardInsert(BoardVo bv);
	
	/*글 수정 메소드*/
	public int boardModify(BoardVo bv);
	
	/*글 삭제 메소드*/
	public int boardDelete(BoardVo bv);
	
	/*답변 기능 메소드*/
//	public int boardReplyUpdate(int originbidx, int depth);
	public int boardReplyUpdate(HashMap hm);
	public int boardReplyInsert(BoardVo bv);
	/*답변 기능 메소드 끝*/
}
