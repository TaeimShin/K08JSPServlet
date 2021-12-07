package model1.board;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.JDBConnect;

public class BoardDAO extends JDBConnect{
	//부모의 인자생성자를 호출한다. 이때 application내장객체를 매개변수로 전달한다.
	public BoardDAO(ServletContext application) {
		//내장객체를 통해 web.xml에 작성된 컨텍스트 초기화 파라미터를 얻어온다.
		super(application);
	}
	/*
	 board테이블에 저장된 게시물의 갯수를 카운트하기 위한 메서드.
	 카운트 한 결과값을 통해 목록에서 게시물의 순번을 출력한다.
	 */
	public int selectCount(Map<String, Object> map) {
		//카운트변수
		int totalCount = 0;
		
		String query = "SELECT COUNT(*) FROM board";
		//검색어가 있는경우 where절을 동적으로 추가한다.
		if(map.get("searchWord") != null) {
			query += " WHERE "+ map.get("searchField")+ " "
					+" LIKE '%" + map.get("searchWord") + "%'";
		}
		
		try {
			//정적 쿼리문 실행을 위한 statement객체 생성
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			//커서를 이동시켜 결과데이터를 읽음
			rs.next();
			totalCount = rs.getInt(1);
		}
		catch(Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return totalCount;
	}
	/*
	 목록에 출력할 게시물을 오라클로 부터 추출하기 위한 쿼리문 실행
	 (페이지 처리 없음)
	 */
	public List<BoardDTO> selectList(Map<String, Object> map){
		/*
		 board 테이블에서 select한 결과데이터를 저장하기 위한 리스트 컬렉션.
		 여러가지의 list컬렉션 중 동기화가 보장되는 vector를 사용한다.
		 */
		List<BoardDTO> bbs = new Vector<BoardDTO>();
		/*
		 목록에 출력할 게시물을 추출하기 위한 쿼리문으로 항상 일련번호의
		 역순(내림차순)으로 정렬해야 한다. 게시판의 목록은 최근 게시물이
		 제일 앞에 노출되기 때문이다.
		 */
		String query = "SELECT * FROM board";
		//검색어가 있는경우 where절을 추가한다.
		if(map.get("searchWord") != null) {
			query += " WHERE "+ map.get("searchField")+ " "
					+" LIKE '%" + map.get("searchWord") + "%'";
		}
		query += " ORDER BY num DESC";
		
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(query);
			
			while(rs.next()) {
				//하나의 레코드를 읽어서DTO객체에 저장한다.
				BoardDTO dto = new BoardDTO();
				
				dto.setNum(rs.getString("num"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setId(rs.getString("id"));
				dto.setVisitcount(rs.getString("visitcount"));
				
				//리스트 컬렉션에 DTO객체를 추가한다.
				bbs.add(dto);
				
			}
		}
		catch(Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		
		return bbs;
	}
	
	
	//사용자가 입력한 내용을 board테이블에 insert처리하는 메서드
	public int insertWrite(BoardDTO dto) { 
		//입력결과 확인용 변수
		int result = 0; 
		
		try {
			String query = "INSERT INTO board ( " 
							+ " num,title,content,id,visitcount) " 
							+ " VALUES ( " 
							+ "seq_board_num.NEXTVAL, ?, ?, ?, 0)"; 

			psmt = con.prepareStatement(query); 
			psmt.setString(1, dto.getTitle()); 
			psmt.setString(2, dto.getContent()); 
			psmt.setString(3, dto.getId()); 
			result = psmt.executeUpdate(); 
		} 
		catch (Exception e){ 
			System.out.println("게시물 입력 중 예외 발생"); 
			e.printStackTrace(); 
		} 
		
		return result; 

	}
	
	public BoardDTO selectView(String num) {
		BoardDTO dto = new BoardDTO(); 
		
		String query = "SELECT B.*, M.name " 
		+ " FROM member M INNER JOIN board B " 
		+ " ON M.id=13.id " 
		+ " WHERE num=?"; 

	try { 
		psmt = con.prepareStatement(query);
	
		psmt.setString(1, num);
		rs = psmt.executeQuery(); 

		if (rs.next()) { 
			dto.setNum(rs.getString(1)); 
			dto.setTitle(rs.getString(2)); 
			dto.setContent(rs.getString("content"));
			dto.setPostdate(rs.getDate("postdate")); 
			dto.setId(rs.getString("id"));
			dto.setVisitcount(rs.getString(6)); 
			dto.setName(rs.getString("name")); 
		}
	}
	catch (Exception e){
		System.out.println("게시물 상세보기 중 예외 발생"); 
		e.printStackTrace(); 
	}
	return dto; 

	}
	
	
	public void updateVisitCount(String num) { 
		
		String query = "UPDATE board SET " 
		+ " visitcount=visitcount+1 " 
				+ " WHERE num=?"; 
	try { 
		psmt = con.prepareStatement(query); 
		psmt.setString(1, num); 
		psmt.executeQuery(); 
	
	}
	catch (Exception e) { 
		System.out.println("게시물 조회수 증가 중 예외 발생");  
		e.printStackTrace(); 
		} 
	} 
}
