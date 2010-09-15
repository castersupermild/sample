<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>crud Index</title>
<link rel="stylesheet" type="text/css" href="/css/global.css" />
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
<script src="/js/update.js"></script>
</head>
<body>
<p>slim3 + gae のサンプルです。何か面白いネタはないものかなー。</p>
<p>名前、メールアドレスはダブルクリックで編集可能になります。Enterで更新。</p>
<span id="errMsg" class="error"></span>
<table cellspacing="0"  cellpadding="3" style="width: 600px">
<thead><tr><th>名前</th><th>メールアドレス</th><th>&nbsp;</th></tr></thead>
<tbody>
<c:forEach var="p" items="${persons}">
<tr >
	<td class="cell_update cell_name_update">${f:h(p.name)}</td>
	<td class="cell_update cell_email_update">${f:h(p.email)}</td>
	<td><a class="small" name="delete" href="../crud/delete?key=${f:h(p.key)}">削除</a><input type="hidden" name="key" value="${f:h(p.key)}" /></td>
</tr>
</c:forEach>
</tbody>
</table>
<br />
<a href="../crud/register">to register</a>
</body>
</html>
