package realexam;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import membership.MemberDAO;
import membership.MemberDTO;

@WebServlet("/realexam/postLogin.do")
public class PostLogin extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//폼값받기
		String id = req.getParameter("user_id");
		String pw = req.getParameter("user_pw");
		
		//DAO객체 생성 및 커넥션풀을 통한 연결
		ServletContext application = this.getServletContext();
		MemberDAO dao = new MemberDAO(application); 
		MemberDTO dto = dao.getMemberDTO(id, pw);
		JSONObject json = new JSONObject();
		/*
			회원 인증 결과에 따른 JSON객체를 생성한다. 
		*/
		if(dto.getName()!=null){
			json.put("result", 1);//성공인 경우는 1로 설정
			json.put("message", dto.getName()+"님, 로그인 성공입니다^^*");
			
			/*
			콜백 데이터에는 모든 형식의 문자열을 사용할 수 있다.
			여기서는 HTML태그를 콜백데이터의 value로 설정한다. 
			*/
			String html = "";
			html+="<table class='table table-bordered' style='width:300px;'>";
			html+="  <tr>";
			html+="    <td>회원님, 반갑습니다^^*</td>";
			html+="  </tr>";
			html+="</table>";	
			json.put("html", html);
		}
		else{
			json.put("result", 0);//실패인 경우는 0으로 설정
			json.put("message", "로그인 실패입니다.ㅜㅜ;");
		}

		dao.close();
		String jsonStr = json.toJSONString();
		resp.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = resp.getWriter();
        writer.println(jsonStr);
        writer.close();
	}
	
}
