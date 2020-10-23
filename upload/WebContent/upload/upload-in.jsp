<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../header.html" %>

<p>ファイル名を入力してください。</p>
<form action="Search.action" method="post">
<input type="text" name="filename">
<input type="submit" value="検索">
</form>
<hr>

<p>データリスト</p>
<table style="border-collapse:separate;border-spacing:10px;">
<c:forEach var="upload" items="${list}">
	<tr>
	<td>データ${upload.id}</td>
	<td>名前 : ${upload.name}</td>
	<td><img src="images/${upload.filename}" height="64"></td>
	<td>ファイル名：${upload.filename}</td>
	<td><a href="download?filename=${upload.filename}">ダウンロード</a></td>
	</tr>
</c:forEach>
</table>

<hr>

<p>アップロードするファイルを選択し、[アップロード]ボタンを押してください。</p>
<form action="Upload.action" method="post" enctype="multipart/form-data">
<input type="text" name="name"><br>
<input type="file" name="uploadfile"><br>
<input type="submit" value="登録">
</form>

<%@include file="../footer.html" %>
