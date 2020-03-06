package resource.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import resource.db.ResourceBean;
import resource.db.ResourceDAOImpl;
import resource.service.ResourceServiceImpl;

public class ResourceController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String RESOURCE_REPO = "C:\\board\\resource_file";

	ResourceServiceImpl serv = null;
	ResourceDAOImpl rDao = null;
	ResourceBean rBean = null;

	int result = 0; // 상태를 나타낼 변수

	@Override
	public void init(ServletConfig sc) throws ServletException {
		serv = new ResourceServiceImpl();
		rBean = new ResourceBean();
	}

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String url = request.getRequestURI();
		String contextPath = request.getContextPath();
		String path = url.substring(contextPath.length());
		String nextPage = null;
		String paging = null;

		try {
			
		    // ##########자료실 페이지 리스트########## Start
			if (path.equals("/resourceList.bo")) {
				
				String opt = request.getParameter("opt");
				String condition = request.getParameter("condition");
				
				String _section=request.getParameter("section");
				String _pageNum=request.getParameter("pageNum");
				
				int section = Integer.parseInt(((_section==null)? "1":_section) );
				int pageNum = Integer.parseInt(((_pageNum==null)? "1":_pageNum));
				
				Map pagingMap=new HashMap();
				
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				
				Map resourcesMap=serv.listResource(pagingMap, opt, condition);
				resourcesMap.put("section", section);
				resourcesMap.put("pageNum", pageNum);
				resourcesMap.put("opt", opt);
				resourcesMap.put("condition", condition);
				
				request.setAttribute("resourcesMap", resourcesMap);
				
				nextPage = "/main.jsp";
				paging = "/pages/main/center/resource/resourceList.jsp";
				request.setAttribute("paging", paging);
				
			// ##########자료실 페이지 리스트########## End
	        // ##########자료실 내용보기 ########## Start
		    }else if(path.equals("/resourceView.bo")){
				int res_no = Integer.parseInt(request.getParameter("res_no"));
				rBean = serv.resourceView(res_no);
				request.setAttribute("rBean", rBean);	
				request.setAttribute("res_no", res_no);
				nextPage = "/main.jsp";
				paging= "/pages/main/center/resource/resourceView.jsp?res_no="+res_no;
				request.setAttribute("paging", paging);	
			
			// ##########자료실 내용보기########## End
            // ##########파일 다운로드########## Start
		    }else if(path.equals("/filedown.bo")){
				System.out.println("filedown.bo");
				int res_no = Integer.parseInt(request.getParameter("res_no"));
				
				rBean = serv.resourceView(res_no);	
				String res_filename = rBean.getRes_filename();
				FileDownloadController filedown = new FileDownloadController();
				filedown.download(response, RESOURCE_REPO + "\\" + res_no + "\\" + res_filename,res_filename);
				request.setAttribute("rBean", rBean);
			// ##########파일 다운로드########## End
            // ##########자료실 게시글 작성 페이지 이동########## Start
		    }else if(path.equals("/resourceWrite.bo")){
				nextPage = "/main.jsp";
				paging= "/pages/main/center/resource/resourceWrite.jsp";
				request.setAttribute("paging", paging);
			// ##########자료실 게시글 작성 페이지 이동########## End
            // ##########게시글 작성########## Start
		    }else if(path.equals("/addResource.bo")){				
				String email = (String)request.getSession().getAttribute("email");
				
				int res_no = 0;
				Map<String, String> resourceMap = upload(request, response);
				String res_title = resourceMap.get("res_title");
				String res_content = resourceMap.get("res_content");
				String res_filename = resourceMap.get("res_filename");

				rBean.setRes_parentno(0);
				rBean.setRes_email(email);
				rBean.setRes_title(res_title);
				rBean.setRes_content(res_content);
				rBean.setRes_filename(res_filename);
				res_no = serv.addResource(rBean);
				
				if (res_filename != null && res_filename.length() != 0) {
					File srcFile = new File(RESOURCE_REPO + "\\" + "temp" + "\\" + res_filename);
					File destDir = new File(RESOURCE_REPO + "\\" + res_no);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile,destDir, true);
				}
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('글쓰기를 완료했습니다.');" + " location.href='" + 
				"resourceList.bo';" + "</script>");
				return;
		
			// ##########게시글 작성########## End
            // ##########게시글 수정 페이지 이동########## Start
		    }else if(path.equals("/resourceModify.bo")){
				int res_no = Integer.parseInt(request.getParameter("res_no"));
				
				rBean = serv.resourceView(res_no);
				request.setAttribute("rBean", rBean);
				nextPage = "/main.jsp";
				paging= "/pages/main/center/resource/resourceModify.jsp?res_no="+res_no;
				request.setAttribute("paging", paging);	
				
			// ##########게시글 수정 페이지 이동########## End
	        // ##########게시글 수정########## Start
		    }else if(path.equals("/updateResource.bo")){
				rBean = getResourceBeanProperty(request, response);
				
				int res_no = rBean.getRes_no();
				Map<String, String> resourceMap = upload(request, response);
				String res_title = resourceMap.get("res_title");
				String res_content = resourceMap.get("res_content");
				String res_filename = resourceMap.get("res_filename");

				rBean.setRes_title(res_title);
				rBean.setRes_content(res_content);
				rBean.setRes_filename(res_filename);
				
				serv.modResource(rBean);
				
				if (res_filename != null && res_filename.length() != 0) {
					File srcFile = new File(RESOURCE_REPO + "\\" + "temp" + "\\" + res_filename);
					File destDir = new File(RESOURCE_REPO + "\\" + res_no);
					destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile,destDir, true);
				}
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + "  alert('수정되었습니다.');" + " location.href='" 
				+"resourceView.bo?res_no="+rBean.getRes_no()+"';" + "</script>");
				return;			
			// ##########게시글 수정동########## End
            // ##########게시글 검색########## Start
		    }else if(path.equals("/resourceSelect.bo")){
				String opt = request.getParameter("opt");
				String condition = request.getParameter("condition");
			
				List<ResourceBean> ResourceList = serv.resourceSelect(opt,condition);
				request.setAttribute("ResourceList", ResourceList);
				nextPage = "/main.jsp";
				paging= "/pages/main/center/resource/resourceSelect.jsp";
				request.setAttribute("paging", paging);
			
			// ##########게시글 검색########## End
            // ##########게시글 삭제########## Start
		    }else if(path.equals("/resourceDelete.bo")){ 	
				int res_no = Integer.parseInt(request.getParameter("res_no"));				
				serv.resourceDelete(res_no);				
				File resfile = new File(RESOURCE_REPO + "\\" + res_no);
				if(resfile.exists()){
					FileUtils.deleteDirectory(resfile);
				}
				
				PrintWriter pw = response.getWriter();
				pw.print("<script>" + " alert('글을 삭제했습니다.');" 
				         + " location.href='resourceList.bo'" + "</script>");
				return;	
			}
			// ##########게시글 삭제########## End
			
			if (nextPage != null) {
				RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
				dispatch.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> resourceMap = new HashMap<String, String>();
		String encoding = "utf-8";
		File currentDirPath = new File(RESOURCE_REPO);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024 * 1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			for (int i = 0; i < items.size(); i++) {
				FileItem fileItem = (FileItem) items.get(i);
				if (fileItem.isFormField()) {
					resourceMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
				} else {
					if (fileItem.getSize() > 0) {
						int idx = fileItem.getName().lastIndexOf("\\");
						if (idx == -1) {
							idx = fileItem.getName().lastIndexOf("/");
						}

						String fileName = fileItem.getName().substring(idx + 1);
						resourceMap.put(fileItem.getFieldName(), fileName);
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
						fileItem.write(uploadFile);

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resourceMap;
	}
	

	private ResourceBean getResourceBeanProperty(HttpServletRequest request, HttpServletResponse response) {
		int res_no = 0;
		String res_title = null;
		String res_email = null;
		String res_content = null;
		String res_filename = null;
		Date res_writedate = new Date(System.currentTimeMillis());

		if (request.getParameter("res_no") != null) {
			res_no = Integer.parseInt(request.getParameter("res_no"));
			rBean.setRes_no(res_no);
		}
		if (request.getParameter("res_title") != null) {
			res_title = request.getParameter("res_title");
			rBean.setRes_title(res_title);
		}
		if (request.getParameter("res_email") != null) {
			res_email = request.getParameter("res_email");
			rBean.setRes_email(res_email);
		}
		if (request.getParameter("res_content") != null) {
			res_content = request.getParameter("res_content");
			rBean.setRes_content(res_content);
		}
		if (request.getParameter("res_filename") != null) {
			res_filename = request.getParameter("res_filename");
			rBean.setRes_filename(res_filename);
		}
		rBean.setRes_writedate(res_writedate);
		return rBean;
	}
}
