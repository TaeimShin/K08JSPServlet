package fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

public class FileUtil {
	
    // 파일 업로드(multipart/form-data 요청) 처리
    public static MultipartRequest uploadFile(HttpServletRequest req,
            String saveDirectory, int maxPostSize) {
        try {
            /*
             MultipartRequest(request내장객체, 디렉토리의 물리적경로, 파일제한용량, 인코딩방식)
             객체를 생성함과 동시에 파일은 업로드가 완료되어 서버에 저장된다.
             업로드가 성공하면 객체의 참조값을 반환한다.
             * */
            return new MultipartRequest(req, saveDirectory, maxPostSize, "UTF-8");
        }
        catch (Exception e) {
            /*
             업로드에 실패하는 경우는 용량초과, 디렉토리 경로 오류등이 대부분이다.
             이때는 null값을 반환한다.
             */
            e.printStackTrace();
            return null;
        }
    }

    // 명시한 파일을 찾아 다운로드합니다.
    public static void download(HttpServletRequest req, HttpServletResponse resp,
            String directory, String sfileName, String ofileName) {
        String sDirectory = req.getServletContext().getRealPath(directory);
        try {
            // 파일을 찾아 입력 스트림 생성
            File file = new File(sDirectory, sfileName);
            InputStream iStream = new FileInputStream(file);

            // 한글 파일명 깨짐 방지
            String client = req.getHeader("User-Agent");
            if (client.indexOf("WOW64") == -1) {
                ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8859-1");
            }
            else {
                ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8859-1");
            }

            // 파일 다운로드용 응답 헤더 설정
            resp.reset();
            resp.setContentType("application/octet-stream");
            resp.setHeader("Content-Disposition",
                           "attachment; filename=\"" + ofileName + "\"");
            resp.setHeader("Content-Length", "" + file.length() );

            //out.clear();  // 출력 스트림 초기화

            // response 내장 객체로부터 새로운 출력 스트림 생성
            OutputStream oStream = resp.getOutputStream();

            // 출력 스트림에 파일 내용 출력
            byte b[] = new byte[(int)file.length()];
            int readBuffer = 0;
            while ( (readBuffer = iStream.read(b)) > 0 ) {
                oStream.write(b, 0, readBuffer);
            }

            // 입/출력 스트림 닫음
            iStream.close();
            oStream.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("파일을 찾을 수 없습니다.");
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("예외가 발생하였습니다.");
            e.printStackTrace();
        }
    }

    // 지정한 위치의 파일을 삭제합니다.
    public static void deleteFile(HttpServletRequest req,
            String directory, String filename) {
        String sDirectory = req.getServletContext().getRealPath(directory);
        File file = new File(sDirectory + File.separator + filename);
        if (file.exists()) {
            file.delete();
        }
    }
}
