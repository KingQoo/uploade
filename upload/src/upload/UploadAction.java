package upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.UploadDAO;
import tool.Action;

public class UploadAction extends Action {
	public String execute(
		HttpServletRequest request, HttpServletResponse response
	) throws Exception {


		// <input type="file" name="uploadfile">からMultipart形式のアップロードコンテンツの内容を取得
		Part part = request.getPart("uploadfile");
		String name = request.getParameter("name");



//		String filename = null;
		String filename = part.getSubmittedFileName();
		System.out.println(":"+filename+":");
//		for (String cd : part.getHeader("Content-Disposition").split(";")) {
//			cd = cd.trim();
//			if (cd.startsWith("filename")) {
//				// ファイル名は=の右側以降の文字列。
//				// ただし利用環境によってはだブルコーテーションが含まれているので、取り除く。
//				filename = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
//				System.out.println(":"+filename+":");
//				break;
//			}
//		}

		// アップロードしたファイルを書き出す
		String message = null;
		UploadDAO dao=new UploadDAO();
		if (!filename.isEmpty() && dao.search(filename).size() == 0) {
			// アップロードされたファイル名は、OS依存のファイルパスなどを含んでいるので置換する。
			// \は/に置換し、その後ファイル名のみ抽出する。
			filename = filename.replace("\\", "/");
			int pos = filename.lastIndexOf("/");
			if (pos >= 0) {
				filename = filename.substring(pos + 1);
			}
			// 実行パスの「images」フォルダにファイルをアップロードする場合の指定
			part.write(request.getServletContext().getRealPath(request.getServletContext().getContextPath()) + "/images/" + filename);

			// アップロードが完了した後はデータベースに登録する
			dao.insert(filename,name);

			message = "[ " + filename + " ]のアップロードが完了しました。";
		} else if(filename.isEmpty()){
			filename = "noimage.jpg";
			dao.insert(filename,name);
		}else {
			message = "アップロードが失敗しました。（すでにそのファイル名は使われています）";
		}

		request.setAttribute("message", message);

		return "upload-out.jsp";
	}
}
