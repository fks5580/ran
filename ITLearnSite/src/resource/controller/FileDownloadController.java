package resource.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadController {
	public HttpServletResponse download(HttpServletResponse response, String filePath,String fileName) throws Exception {
		File downfile = new File(filePath);		
		if(!downfile.exists()) {
		        throw new FileNotFoundException();
		}
		ServletOutputStream outStream = null;
		FileInputStream inputStream = null;
		try 
		{
				outStream = response.getOutputStream();
				inputStream = new FileInputStream(downfile);               
				//Setting Resqponse Header
				response.setContentType("application/octet-stream");
		        response.setHeader("Content-Disposition", 
		        					"attachment;filename=\""+new String(fileName.getBytes("euc-kr"),"8859_1")+"\"");

		        //Writing InputStream to OutputStream
		        byte[] outByte = new byte[4096];
		        while(inputStream.read(outByte, 0, 4096) != -1)
		        {
		          outStream.write(outByte, 0, 4096);
		        }
		}
		catch (Exception e) 
		{

		        throw new IOException();
		} 
		finally 
		{
		        inputStream.close();
		        outStream.flush();
		        outStream.close();
		}
		return response;
		}
}
