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

	@Autowired /* (required = true) : ������ �ȵǴ��� null������ �ְ� ������ */
	BoardService bs;

	/* ����¡ */
	@Autowired(required = false)
	PageMaker pm;

	/* ���� ��� ���� servlet-context���� ��� ���� �� ���ֱ� */
	@Resource(name = "uploadPath") /* @Autowired�� ���� ����, Resource�� �̸����� ã�� */
	String uploadPath;

	/* �Խñ� ��� */
	@RequestMapping(value = "/boardList.do")
	public String boardList(SearchCriteria scri, /* ����¡ */
			Model model) {

		/* ����¡ - ������ �ѹ��� */
		int totalCount = bs.boardTotal(scri);
		pm.setScri(scri);
		pm.setTotalCount(totalCount);
		/* ����¡ - ������ �ѹ��� -- �� */

		ArrayList<BoardVo> blist = bs.boardSelectAll(scri);

		model.addAttribute("blist", blist);
		model.addAttribute("pm", pm); /* ����¡ - ������ �ѹ��� */

		return "board/boardList";
	}/* �Խñ� ��� �� */

	/* �Խñ� ���� */
	@RequestMapping(value = "/boardContents.do")
	public String boardContents(@RequestParam("bidx") int bidx, Model model) {

		BoardVo bv = bs.boardSelectOne(bidx);
		int value = bs.boardViewCnt(bidx);

		model.addAttribute("bv", bv);

		return "board/boardContents";
	}/* �Խñ� ���� �� */

	/* �� ���� �������� ���� */
	@RequestMapping(value = "/boardWrite.do", method = RequestMethod.GET)
	public String boardWrite() {

		return "board/boardWrite";
	}/* �� ���� �������� ���� �� */

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
		// System.out.println("���� ���� �̸�" + file.getOriginalFilename());

		String uploadedFileName = "";
		if (!file.getOriginalFilename().equals("")) {// ���� �̸��� �����Ѵٸ� ����
			uploadedFileName = UploadFileUtiles.uploadFile(uploadPath, /* ���� ��ġ - util��Ű�� �߰��ϰ� Controller��ܿ� ���ϰ�� ���� */
					file.getOriginalFilename(), /* ���� �̸� */
					file.getBytes()); /* ���� ũ�� */
		}
		String ip = InetAddress.getLocalHost().getHostAddress();

		int midx = 0;
		if (session.getAttribute("midx") != null) {
			midx = Integer.parseInt(session.getAttribute("midx").toString());
		}
		/* �Էµ� ���� ��� */
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
			path = "redirect:/board/boardList.do"; /* ���� �ۼ� �Ǹ� boardList�� */
		} else {
			path = "redirect:/board/boardWrite.do"; /* �����ϸ� boardWrite�� */
		}

		return path;
	}

	/* �ٸ� ��ġ�� �ִ� ���� �ٿ�ε� �����ϰ��ϴ� �޼ҵ� */
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
		/* �Էµ� ���� ��� */
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
