package model2.mvcboard;


import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;

import common.DBConnPool;
import model1.board.BoardDTO;

public class MVCBoardDAO extends DBConnPool{

	
	//기본생성자는 없어도 동작에는 영향이없다.
	public MVCBoardDAO() {		
		super();
	}

	public int selectCount(Map<String, Object> map) {
		//카운트 변수
		int totalCount = 0;  
		//쿼리문 작성
		String query = "SELECT COUNT(*) FROM mvcboard ";
		//검색어가 있는 경우 where절을 동적으로 추가한다. 
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%'";
		}

		try {
			//정적쿼리문(?가 없는 쿼리문) 실행을 위한 Statement객체 생성
			stmt = con.createStatement();    
			//select 쿼리문을 실행 후 ResultSet객체를 반환받음
			rs = stmt.executeQuery(query);   
			//커서를 이동시켜 결과데이터를 읽음
			rs.next();   
			//결과값을 변수에 저장
			totalCount = rs.getInt(1);  
		}
		catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}

		return totalCount; 
	}


	public List<MVCBoardDTO> selectListPage(Map<String, Object> map) { 

		
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();  
		
		String query = "SELECT * FROM ("
				+ "	SELECT Tb.*, ROWNUM rNum FROM ("
				+ "		SELECT * FROM mvcboard "; 
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY idx DESC "
				+ "		) Tb "
				+ ")"
				+ " WHERE rNum BETWEEN ? AND ?"; 

		try {
			psmt = con.prepareStatement(query); 
			psmt.setString(1, map.get("start").toString());
			psmt.setString(2, map.get("end").toString()); 
			rs= psmt.executeQuery();

			//추출된 결과에 따라 반복한다. 
			while (rs.next()) { 
				//하나의 레코드를 읽어서 DTO객체에 저장한다. 
				MVCBoardDTO dto = new MVCBoardDTO(); 

				dto.setIdx(rs.getString(1));          
				dto.setName(rs.getString(2));          
				dto.setTitle(rs.getString(3));      
				dto.setContent(rs.getString(4));   
				dto.setPostdate(rs.getDate(5));  
				dto.setOfile(rs.getString(6));  
				dto.setSfile(rs.getString(7));  
				dto.setDowncount(rs.getInt(8));  
				dto.setPass(rs.getString(9));           
				dto.setVisitcount(rs.getInt(10));  

				//리스트 컬렉션에 DTO객체를 추가한다. 
				board.add(dto);  
			}
		} 
		catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}

		return board;
	}
	
	public int insertWrite(MVCBoardDTO dto) {
        //입력결과 확인용 변수
    	int result = 0;
        
        try {
        	//인파라미터가 있는 쿼리문 작성(동적쿼리문)
            String query = "INSERT INTO mvcboard ( "
                         + " idx, name,title,content,ofile, sfile, pass) "
                         + " VALUES ( "
                         + " seq_board_num.NEXTVAL, ?, ?, ?, ?,?,?)";  
            //동적쿼리문 실행을 위한 prepared객체 생성
            psmt = con.prepareStatement(query);   
            //순서대로 인파라미터 설정
            psmt.setString(1, dto.getName());  
            psmt.setString(2, dto.getTitle());
            psmt.setString(3, dto.getContent());
            psmt.setString(4, dto.getOfile());
            psmt.setString(5, dto.getSfile());
            psmt.setString(6, dto.getPass());
            
            //쿼리문 실행 : 입력에 성공한다면 1이 반환된다. 실패시 0반환.
            result = psmt.executeUpdate(); 
        }
        catch (Exception e) {
            System.out.println("게시물 입력 중 예외 발생");
            e.printStackTrace();
        }
        
        return result;
    }
	
	 public MVCBoardDTO selectView(String idx) { 
	        
	    	MVCBoardDTO dto = new MVCBoardDTO();
	        
	        //join을 이용해서 member테이블의 name컬럼까지 가져온다. 
	        String query = "SELECT * " 
	                     + " FROM mvcboard " 
	                     + " WHERE idx=?";

	        try {
	            psmt = con.prepareStatement(query);
	            psmt.setString(1, idx);   
	            rs = psmt.executeQuery();  
	            //일련번호는 중복되지 않으므로 if문에서 처리하면 된다. 
	            if (rs.next()) {
	                dto.setIdx(rs.getString(1)); 
	                dto.setName(rs.getString(2));
	                dto.setTitle(rs.getString(3));
	                dto.setContent(rs.getString(4));
	                dto.setPostdate(rs.getDate(5));
	                dto.setOfile(rs.getString(6));
	                dto.setSfile(rs.getString(7));
	                dto.setDowncount(rs.getInt(8));
	                dto.setPass(rs.getString(9));
	                dto.setVisitcount(rs.getInt(10));
	            }
	        } 
	        catch (Exception e) {
	            System.out.println("게시물 상세보기 중 예외 발생");
	            e.printStackTrace();
	        }
	        
	        return dto; 
	    }
	
	
	
	
	
}
