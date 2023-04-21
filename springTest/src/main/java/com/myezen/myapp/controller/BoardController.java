package com.myezen.myapp.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myezen.myapp.domain.BoardVo;
import com.myezen.myapp.domain.PageMaker;
import com.myezen.myapp.domain.SearchCriteria;
import com.myezen.myapp.service.BoardService;
import com.myezen.myapp.util.MediaUtils;
import com.myezen.myapp.util.UploadFileUtiles;

@Controller
@RequestMapping(value = "/board")
public class BoardController {

	@Autowired /* (required = true) : 주입이 안되더라도 null값으로 넣고 싶으면 */
	BoardService bs;

	/* 페이징 */
	@Autowired(required = false)
	PageMaker pm;

	/* 파일 경로 주입 servlet-context에서 경로 지정 꼭 해주기 */
	@Resource(name = "uploadPath") /* @Autowired와 같은 역할, Resource는 이름으로 찾음 */
	String uploadPath;

	/* 게시글 목록 */
	@RequestMapping(value = "/boardList.do")
	public String boardList(SearchCriteria scri, /* 페이징 */
			Model model) {

		/* 페이징 - 페이지 넘버바 */
		int totalCount = bs.boardTotal(scri);
		pm.setScri(scri);
		pm.setTotalCount(totalCount);
		/* 페이징 - 페이지 넘버바 -- 끝 */

		ArrayList<BoardVo> blist = bs.boardSelectAll(scri);

		model.addAttribute("blist", blist);
		model.addAttribute("pm", pm); /* 페이징 - 페이지 넘버바 */

		return "board/boardList";
	}/* 게시글 목록 끝 */

	/* 게시글 보기 */
	@RequestMapping(value = "/boardContents.do")
	public String boardContents(@RequestParam("bidx") int bidx, Model model) {

		BoardVo bv = bs.boardSelectOne(bidx);
		int value = bs.boardViewCnt(bidx);

		model.addAttribute("bv", bv);

		return "board/boardContents";
	}/* 게시글 보기 끝 */

	/* 글 쓰기 페이지로 가기 */
	@RequestMapping(value = "/boardWrite.do", method = RequestMethod.GET)
	public String boardWrite() {

		return "board/boardWrite";
	}/* 글 쓰기 페이지로 가기 끝 */

	@RequestMapping(value = "/boardWriteAction.do")
	public String boardWriteAction(@RequestParam("subject") String subject, 
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer, 
			@RequestParam("pwd") String pwd,
			@RequestParam("filename") MultipartFile filename, 
			RedirectAttributes rttr,
			HttpSession session) throws Exception {

		String path = "";
		MultipartFile file = filename;
		// System.out.println("파일 원본 이름" + file.getOriginalFilename());

		String uploadedFileName = "";
		if (!file.getOriginalFilename().equals("")) {// 파일 이름이 존재한다면 실행
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, /* 파일 위치 - util패키지 추가하고 Controller상단에 파일경로 지정 */
					file.getOriginalFilename(), /* 파일 이름 */
					file.getBytes()); /* 파일 크기 */
		}
		String ip = InetAddress.getLocalHost().getHostAddress();

		int midx = 0;
		if (session.getAttribute("midx") != null) {
			midx = Integer.parseInt(session.getAttribute("midx").toString());
		}
		/* 입력된 정보 담기 */
		BoardVo bv = new BoardVo();
		bv.setSubject(subject);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setIp(ip);
		bv.setMidx(midx);
		bv.setPwd(pwd);
		bv.setFilename(uploadedFileName);

		int value = bs.boardInsert(bv);

		if (value == 1) {
			path = "redirect:/board/boardList.do"; /* 글이 작성 되면 boardList로 */
		} else {
			path = "redirect:/board/boardWrite.do"; /* 실패하면 boardWrite로 */
		}

		return path;
	}

	/* 다른 위치에 있는 파일 다운로드 가능하게하는 메소드 */
	@ResponseBody
	@RequestMapping(value = "/displayFile.do", method = RequestMethod.GET)
	public ResponseEntity<byte[]> displayFile(String fileName,
			@RequestParam(value = "down", defaultValue = "0") int down) throws Exception {

		// System.out.println("fileName:"+fileName);

		InputStream in = null;
		ResponseEntity<byte[]> entity = null;

		// logger.info("FILE NAME :"+fileName);

		try {
			String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
			MediaType mType = MediaUtils.getMediaType(formatName);

			HttpHeaders headers = new HttpHeaders();

			in = new FileInputStream(uploadPath + fileName);

			if (mType != null) {

				if (down == 1) {
					fileName = fileName.substring(fileName.indexOf("_") + 1);
					headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
					headers.add("Content-Disposition",
							"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");

				} else {
					headers.setContentType(mType);
				}

			} else {

				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition",
						"attachment; filename=\"" + new String(fileName.getBytes("UTF-8"), "ISO-8859-1") + "\"");
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			in.close();
		}
		return entity;
	}

	@RequestMapping(value = "/boardModify.do")
	public String boardModify(@RequestParam("bidx") int bidx, Model model) {

		BoardVo bv = bs.boardSelectOne(bidx);

		model.addAttribute("bv", bv);

		return "board/boardModify";
	}

	@RequestMapping(value = "/boardModifyAction.do")
	public String boardModifyAction(@RequestParam("bidx") int bidx, @RequestParam("subject") String subject,
			@RequestParam("contents") String contents, @RequestParam("writer") String writer,
			@RequestParam("pwd") String pwd, @RequestParam("filename") MultipartFile filename, HttpSession session)
			throws Exception {

		MultipartFile file = filename;

		String uploadedFileName = "";
		if (!file.getOriginalFilename().equals("")) {
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		String ip = InetAddress.getLocalHost().getHostAddress();

		int midx = 0;
		if (session.getAttribute("midx") != null) {
			midx = (int) session.getAttribute("midx");
		}
		/* 입력된 정보 담기 */
		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setSubject(subject);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setIp(ip);
		bv.setMidx(midx);
		bv.setPwd(pwd);
		bv.setFilename(uploadedFileName);

		int value = bs.boardModify(bv);

		String path = "";
		if (value == 1) {
			path = "redirect:/board/boardContents.do?bidx=" + bidx;
		} else {
			path = "redirect:/board/boardModify.do?bidx=" + bidx;
		}

		return path;
	}

	@RequestMapping(value = "/boardDelete.do")
	public String boardDelete(@RequestParam("bidx") int bidx, Model model) {

		BoardVo bv = bs.boardSelectOne(bidx);

		model.addAttribute("bv", bv);

		return "board/boardDelete";
	}

	@RequestMapping(value = "/boardDeleteAction.do")
	public String boardDeleteAction(
			@RequestParam("bidx") int bidx, 
			@RequestParam("pwd") String pwd
			) {

		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setPwd(pwd);

		int value = bs.boardDelete(bv);
		// System.out.println(value);
		String path = "";

		if (value == 1) {
			path = "redirect:/board/boardList.do";
		} else {
			path = "redirect:/board/boardDelete.do?bidx=" + bidx;
		}
		return path;
	}

	@RequestMapping(value = "/boardReply.do", method = RequestMethod.GET)
	public String boardReply(
			@RequestParam("bidx") int bidx,
			@RequestParam("originbidx") int originbidx,
			@RequestParam("depth") int depth,
			@RequestParam("level_") int level_,
			Model model
			) {

		BoardVo bv = new BoardVo();
		bv.setBidx(bidx);
		bv.setOriginbidx(originbidx);
		bv.setDepth(depth);
		bv.setLevel_(level_);
	//	System.out.println(originbidx);
		
		model.addAttribute("bv", bv);
		
		return "board/boardReply";
	}

	@RequestMapping(value = "/boardReplyAction.do", method = RequestMethod.POST)
	public String boardReplyAction(
			@RequestParam("bidx") int bidx,
			@RequestParam("subject") String subject,
			@RequestParam("originbidx") int originbidx,
			@RequestParam("depth") int depth,
			@RequestParam("level_") int level_,
			@RequestParam("contents") String contents,
			@RequestParam("writer") String writer,
			@RequestParam("pwd") String pwd,
			@RequestParam("filename") MultipartFile filename,
			Model model,
			HttpSession session
			) throws Exception {
		
		MultipartFile file = filename;

		String uploadedFileName = "";
		if (!file.getOriginalFilename().equals("")) {
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		}
		
		String ip = InetAddress.getLocalHost().getHostAddress();

		int midx = 0;
		if (session.getAttribute("midx") != null) {
			midx = (int) session.getAttribute("midx");
		}
		
		BoardVo bv = new BoardVo();
		bv.setOriginbidx(originbidx);
		bv.setDepth(depth);
		bv.setLevel_(level_);
		bv.setContents(contents);
		bv.setWriter(writer);
		bv.setIp(ip);
		bv.setMidx(midx);
		bv.setPwd(pwd);
		bv.setFilename(uploadedFileName);
		bv.setSubject(subject);
		
		int value = bs.boardReply(bv);
		
		return "redirect:/board/boardList.do";
	}
	 
	 

}
