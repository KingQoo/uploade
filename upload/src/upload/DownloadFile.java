package upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/upload/download")
public class DownloadFile extends HttpServlet {
	public void doPost(
			HttpServletRequest request, HttpServletResponse response
		) throws ServletException, IOException {
		// ファイル名を取り出す
		String filename = request.getParameter("filename");

		// もしファイル名が取得できなかった場合は、メッセージ表示画面へ。
		if (filename == null || filename.equals("")) {
			request.setAttribute("message", "ファイルは添付されていません");
			request.getRequestDispatcher("/upload/upload-out.jsp").forward(request, response);

			// ここで処理終了
			return;
		}

		// 添付ファイルの保存ディレクトリ→アップロードディレクトリ
		File downloadFile = new File(request.getServletContext().getRealPath(request.getServletContext().getContextPath()) + "/images/" + filename);
		FileInputStream fis = new FileInputStream(downloadFile);
		BufferedInputStream buf = new BufferedInputStream(fis);

		// 全角ファイル
		filename = URLEncoder.encode(filename, "utf-8");

		response.setContentType("application/octet-stream; charset=\"utf-8\"");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

		// サーブレットの出力（レスポンス）を取得する。
		ServletOutputStream out = response.getOutputStream();

		int length = 0;
		byte[] buffer = new byte[1024];
		while ((length = buf.read(buffer)) >= 0) {
			out.write(buffer, 0, length);
		}

		out.close();
		out.flush();

		buf.close();

	}

	public void doGet(
			HttpServletRequest request, HttpServletResponse response
		) throws ServletException, IOException {
			doPost(request, response);
	}
}
