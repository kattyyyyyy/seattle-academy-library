<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form"%>
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
        <h1>一括登録</h1>
        <form method="post" action="bulkRegist" enctype="multipart/form-data">
            <div class="bulk_form">
                <h2>CSVファイルをアップロードすることで書籍を一括で登録することができます。</h2>
                <div class="caution">
                    <p>
                        「書籍名,著者名,出版社,出版日,ISBN」の形式で記載してください。<br /> ※サムネイル画像は一括登録できません。編集画面で一冊単位で登録してください。
                    </p>
                </div>
                <div>
                    <input type="file" name="upload_file">
                </div>
                <c:if test="${!empty bulkErrorMessage}">
                    <div class="error">
                        <c:forEach var="errorMessage" items="${bulkErrorMessage}">
                            ${errorMessage}
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            <div class="edtDelBookBtn_box">
                <button type="submit" value="${bookDetailsInfo.bookId}" name="bookId" class="btn_rentBook">一括登録</button>
            </div>
        </form>
    </main>
</body>
</html>
