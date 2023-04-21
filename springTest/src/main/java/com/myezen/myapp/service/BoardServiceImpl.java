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
	/*코드 간결화*/
	private BoardService_Mapper bsm; 	
	
	@Autowired
	public BoardServiceImpl(SqlSession sqlSession) {
		this.bsm = sqlSession.getMapper(BoardService_Mapper.class);
	}/*--*/
		
	/*게시글 목록*/
	@Override
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri) {
		
		ArrayList<BoardVo> blist = bsm.boardSelectAll(scri); /*mybatis 호출*/
		
		return blist;
	}/*게시글 목록 */
	
	/*페이징 - 페이지 넘버바*/
	@Override
	public int boardTotal(SearchCriteria scri) {
		
		int value = bsm.boardTotal(scri);
		
		return value;
	}
	
	/*게시글 보기*/
	@Override
	public BoardVo boardSelectOne(int bidx) {

		BoardVo bv = bsm.boardSelectOne(bidx);
		
		return bv;
	}

	/*조회수 증가 메소드*/
	@Override
	public int boardViewCnt(int bidx) {
		
		int value = bsm.boardViewCnt(bidx);
		
		return value;
	}
	
	/*글 쓰기 메소드*/
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
	
	@Transactional /*트랜잭션 : 쿼리가 모두 성공하면 커밋, 하나라도 실패하면 롤백*/
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
