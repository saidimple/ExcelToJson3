import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

//$Id$
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
      )
public class ExcelAction extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String fp = "/home/local/ZOHOCORP/sai-11750/Downloads/apache-tomcat-9.0.63/webapps/ExcelToJson/";
        PrintWriter out = resp.getWriter();
        try {
            MultipartRequest m = new MultipartRequest(req, fp);
        Enumeration files = m.getFileNames();
        String fileSystemName = "";
        while(files.hasMoreElements() ){
          String fileName = (String)files.nextElement();
          fileSystemName= m.getFilesystemName(fileName );
         }
        out.println(ExcelUtil.excelToJson(new File(fp + fileSystemName)));
        } catch (Exception e) {
            out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    
    

}
