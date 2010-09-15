<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>crud Register</title>
<link rel="stylesheet" type="text/css" href="/css/global.css" />
<link rel="stylesheet" type="text/css" href="/css/style.css" />
</head>
<body>
<p>登録画面</p>
<p>名前、メールアドレスはそれぞれユニークではなくていけません。<br/>手抜きなのでメールアドレスの正規表現チェックはしていません。</p>
<c:forEach var="m" items="${errMsg}">
<span class="error">${f:h(m)}</span>
</c:forEach>
<form method="post" action="create">
<div><span style="margin-right: 69px;">名前:</span><input type="text" name="name"  value="${person.name}"/></div>
<div><span style="margin-right: 5px;">メールアドレス:</span><input type="text" name="email" value="${person.email}"/></div>
<br />
<input type="submit" value="create account"/>
</form>
<br />
<a href="../crud">戻る</a>
</body>
</html>
