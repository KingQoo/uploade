package upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Upload;
import dao.UploadDAO;
import tool.Action;

public class SearchAction extends Action {
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session = request.getSession();

		String filename = request.getParameter("filename");
		if (filename == null) filename = "";

		UploadDAO dao=new UploadDAO();
		List<Upload> list=dao.search(filename);

		session.setAttribute("list", list);

		return "upload-in.jsp";
	}
}
