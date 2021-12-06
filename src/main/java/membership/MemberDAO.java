package membership;

import common.JDBConnect;

public class MemberDAO extends JDBConnect{

	//인자가 4개인 부모의 생성자를 호출하여 연결한다.
	public MemberDAO(String drv, String url, String id, String pw) {
		super(drv, url, id, pw);
		
	}
	/*
	 사용자가 입력한 아이디, 패스워드를 통해 회원테이블을 확인한 후 
	 존재하는 정보인 경우 DTO객체에 그 정보를 담아 반환한다.
	 */
	public MemberDTO getMemberDTO(String uid, String upass) {
		MemberDTO dto = new MemberDTO();
		String query = "SELECT * FROM member WHERE id=? AND pass=?";
		
		try {
			psmt = con.prepareStatement(query);
			//쿼리문에 아이디 패스워드 설정하고
			psmt.setString(1, uid);
			psmt.setString(2, upass);
			rs = psmt.executeQuery();
			//회원정보가 존재한다면 DTO에 회원정보를 저장한다.
			if(rs.next()) {
				dto.setId(rs.getString("id"));
				dto.setPass(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setRegidate(rs.getString(4));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return dto;
	}
	

}
