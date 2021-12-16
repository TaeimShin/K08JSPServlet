package real.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
/*
라이브러리 다운로드
https://mvnrepository.com/artifact/org.jsoup/jsoup/1.11.3

 */
@WebServlet("/Covid19DataRead.do")
public class Covid19DataRead extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String dataPortal = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson";
		dataPortal += "?serviceKey=b%2Fype5nEeO41GEZc7dXs5Hd%2FwFaGqj047kN9QW1Xz%2B1Ap5JOVN41%2BFcQiIzAk1BsyVYr8cn5ZekIJdW%2FRMHnoA%3D%3D";
		dataPortal += "&pageNo=1";
		dataPortal += "&numOfRows=10";
		dataPortal += "&startCreateDt="+req.getParameter("startCreateDt");
		dataPortal += "&endCreateDt="+req.getParameter("endCreateDt");
		
		String s = crawling01(dataPortal);
		System.out.println(s);
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter writer = resp.getWriter();		
		writer.println(s);
		writer.close();		
	}	
	
	public String crawling01(String addr) {
		URL url;
		StringBuffer sourceCode = new StringBuffer();
		try {
			url = new URL(addr);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String sourceLine = "";
			while((sourceLine=br.readLine()) != null) {
				sourceCode.append(sourceLine+"\n");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return sourceCode.toString();
	}	
}
