<%-- 
    Document   : transaction
    Created on : Mar 14, 2025, 12:11:19 PM
    Author     : MINH
--%>

<%@page import="java.sql.Timestamp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${requestScope.tournament.tournamentName}</title>
        <link rel="stylesheet" href="styles/transaction.css">
        <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&display=swap" rel="stylesheet">
        <link href="/dist/admin.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <div class="left-panel">
                <div class="pbs-open">PBS Open</div>
                <div class="logo">
                    <img src="${requestScope.logoFileName}" alt="${requestScope.logoFileName}" class="tournament-logo">
                </div>
                <div class="title">${requestScope.tournament.tournamentName.replace('Open', '<br> Open')}</div>
                <div class="date">
                    <span class="calendar-icon">📅</span>
                    <%
                        String date = (String) request.getAttribute("date");
                    %>
                    <span class="date-info">${date}</span>
                </div>
            </div>
            <div class="right-panel">
                <div class="transfer-info">
                    <h2 class="transfer-title">THÔNG TIN CHUYỂN KHOẢN</h2>
                    <div class="qr-code">
                        <img src="https://via.placeholder.com/150" alt="QR Code">
                    </div>
                    <table>
                        <tr>
                            <td class="label">Ngân hàng</td>
                            <td class="value">VIETCOMBANK Chi Nhánh Lê Văn Việt</td>
                        </tr>
                        <tr>
                            <td class="label">Người thụ hưởng</td>
                            <td class="value">Nguyễn Văn A</td>
                        </tr>
                        <tr>
                            <td class="label">Số tài khoản</td>
                            <td class="value">999349824910</td>
                        </tr>
                        <tr>
                            <td class="label">Nội dung chuyển khoản</td>
                            <td class="value">Strike The Frog + Đăng ký giải &lt;Tên giải&gt; + SĐT</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
