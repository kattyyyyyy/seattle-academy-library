<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=utf8"%>
<%@ page import="java.util.*"%>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
<html>
<head>
<meta charset="UTF-8">
<title>書籍の一括登録｜シアトルライブラリ｜シアトルコンサルティング株式会社</title>
<link href="<c:url value="/resources/css/reset.css" />" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+JP" rel="stylesheet">
<link href="<c:url value="/resources/css/default.css" />" rel="stylesheet" type="text/css">
<link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
<link href="<c:url value="resources/css/home.css" />" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="resources/css/lightbox.css">
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script src="resources/js/lightbox.js" /></script>
</head>
<body class="wrapper">
    <header>
        <div class="left">
            <img class="mark" src="resources/img/logo.png" />
            <div class="logo">Seattle Library</div>
        </div>
        <div class="right">
            <ul>
                <li><a href="<%=request.getContextPath()%>/home" class="menu">Home</a></li>
                <li><a href="<%=request.getContextPath()%>/">ログアウト</a></li>
            </ul>
        </div>
    </header>
    <main>
        <h1>貸出履歴</h1>
        <div class="rent_table">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th scope="col">タイトル</th>
                        <th scope="col">貸出日</th>
                        <th scope="col">返却日</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="rentInfo" items="${rentList}">
                        <tr>
                            <td>
                                <form action="<%=request.getContextPath()%>/details" method="post" id="a${rentInfo.bookId}">
                                    <a href="javascript:void(0)" onclick="document.forms['a${rentInfo.bookId}'].submit();"> ${rentInfo.title}</a> <input type="hidden" name="bookId" value="${rentInfo.bookId}">
                                </form>
                            </td>
                            <td>${rentInfo.rentDate}</td>
                            <td>${rentInfo.returnDate}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </main>
</body>
</html>
