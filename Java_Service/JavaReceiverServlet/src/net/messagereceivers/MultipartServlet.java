package net.messagereceivers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class MultipartServlet
 */
@WebServlet("/Multipart")
public class MultipartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MultipartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		Collection<Part> parts = request.getParts();
		Iterator<Part> itr = parts.iterator();
		while (itr.hasNext())			
        {
			Part actualPart = itr.next();
			if (actualPart.getName().equals("this is the name of the content"))
			{
				InputStream is = actualPart.getInputStream();
				String fileName = "";
				Collection<String> headerNames = actualPart.getHeaderNames();
				Iterator<String> headerNamesIterator = headerNames.iterator();
				while (headerNamesIterator.hasNext())
				{
				    String headerName = headerNamesIterator.next();
				    String headerValue = actualPart.getHeader(headerName);
				    if (headerName.equals("content-disposition"))
				    {
				    	String searchTerm = "filename=";
				    	int startIndex = headerValue.indexOf(searchTerm);
				    	int endIndex = headerValue.indexOf(";", startIndex);
				    	fileName = headerValue.substring(startIndex + searchTerm.length(), endIndex);
				    	
				    	
				    }
				}
				
				File file = new File("c:/" + fileName);
				
				// if file doesnt exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}
				
				OutputStream out = new FileOutputStream(file);
				byte buf[] = new byte[1024];
				int len;
				while ((len = is.read(buf)) > 0)
				{
				    out.write(buf, 0, len);
				}
			
				out.close();
			}
        }
		
	}

}
