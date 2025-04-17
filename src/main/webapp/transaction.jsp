<%@page import="strikethefrog.Tournament.TournamentDAO"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="strikethefrog.User.UserDTO"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="strikethefrog.Tournament.TournamentDTO"%>

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
                    <a href="TournamentController?action=list" 
                       style="padding: 8px 16px; background: #f0f0f0; border-radius: 4px; text-decoration: none; margin-bottom: 20px; display: inline-block;">
                        ← Quay lại trang chủ
                    </a>
                    <div class="qr-code">
                        <%
                            TournamentDTO tournament = (TournamentDTO) request.getAttribute("tournament");
                            UserDTO user = (UserDTO) request.getAttribute("userP");
                            String qrContent = "StrikeTheFrog_" + tournament.getTournamentID() + "_" + 
                                           URLEncoder.encode(tournament.getTournamentName(), "UTF-8");
                        %>
                        <img src="https://img.vietqr.io/image/970436-9888789001-print.png?amount=<%= tournament.getFee() != null ? tournament.getFee().toPlainString() : "" %>&addInfo=<%= qrContent %>&accountName=NGUYEN%20THE%20MINH" 
                             alt="QR Code for <%= tournament.getTournamentName() %>">
                    </div>
                    <table>
                        <tr>
                            <td class="label">Ngân hàng</td>
                            <td class="value">VIETCOMBANK Chi Nhánh Lê Văn Việt</td>
                        </tr>
                        <tr>
                            <td class="label">Người thụ hưởng</td>
                            <td class="value">Nguyen The Minh</td>
                        </tr>
                        <tr>
                            <td class="label">Số tài khoản</td>
                            <td class="value">9888789001</td>
                        </tr>
                        <tr>
                            <td class="label">Lệ phí tham gia</td>
                            <td class="value"><%= tournament.getFee() != null ? tournament.getFee().toPlainString() : "null" %></td>
                        </tr>
                        <tr>
                            <td class="label">Nội dung chuyển khoản</td>
                            <td class="value">Strike The Frog + ID Tài Khoản + Tên Giải</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
