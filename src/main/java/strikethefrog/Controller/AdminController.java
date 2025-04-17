/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package strikethefrog.Controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import strikethefrog.Match.MatchDAO;
import strikethefrog.Match.MatchDTO;
import strikethefrog.Player.PlayerDAO;
import strikethefrog.Player.PlayerDTO;
import strikethefrog.Round.RoundDAO;
import strikethefrog.Round.RoundDTO;
import strikethefrog.Table.TableDAO;
import strikethefrog.Table.TableDTO;
import strikethefrog.Tournament.TournamentDAO;
import strikethefrog.Tournament.TournamentDTO;
import strikethefrog.User.UserDAO;
import strikethefrog.User.UserDTO;
import strikethefrog.utils.TournamentTools;
import javax.servlet.annotation.MultipartConfig;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import strikethefrog.Transaction.TransactionDAO;
import strikethefrog.Transaction.TransactionDTO;
import strikethefrog.utils.DBUtils;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50 // 50MB
)

/**
 *
 * @author MINH
 */
public class AdminController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = "admin.jsp";
        String action = request.getParameter("action");

        MatchDAO mdao = new MatchDAO();
        TournamentDAO tdao = new TournamentDAO();
        RoundDAO rdao = new RoundDAO();
        TableDAO tadao = new TableDAO();
        UserDAO udao = new UserDAO();
        PlayerDAO pdao = new PlayerDAO();
        TournamentTools tt = new TournamentTools();

        try {

            if (action == null) {
                url = "admin.jsp";
            } else if (action.equals("home")) {
                request.setAttribute("totalTournament", tdao.getTotalTournaments());
                request.setAttribute("totalPlayer", pdao.getTotalPlayer());
                request.setAttribute("endTournament", tdao.getTotalEndTournament());
                request.setAttribute("upcomingTournament", tdao.getTotalUpcomingTournaments());

                // Count ongoing tournaments
                List<TournamentDTO> ongoingTournaments = tdao.getOngoingTournaments();
                request.setAttribute("ongoingTournaments", ongoingTournaments);

                // Map to store player counts for each tournament
                Map<Integer, Integer> tournamentPlayerCounts = new HashMap<>();

                // Player count for each tournament
                if (ongoingTournaments != null && !ongoingTournaments.isEmpty()) {
                    for (TournamentDTO tournament : ongoingTournaments) {
                        try {
                            int playerCount = pdao.getPlayerCountByTournamentID(tournament.getTournamentID());
                            tournamentPlayerCounts.put(tournament.getTournamentID(), playerCount);
                        } catch (Exception e) {
                            System.out.println("Error getting player count for tournament ID "
                                    + tournament.getTournamentID() + ": " + e.getMessage());
                            tournamentPlayerCounts.put(tournament.getTournamentID(), 0);
                        }
                    }
                }

                request.setAttribute("tournamentPlayerCounts", tournamentPlayerCounts);
                url = "admin.jsp";
            } else if (action.equals("match")) {
                String mID = request.getParameter("mID");
                String tName = request.getParameter("tName");
                List<MatchDTO> list = mdao.list(mID, tName);
                request.setAttribute("list", list);
                url = "admin-match.jsp";

            } else if (action.equals("table")) {
                String searchTableName = request.getParameter("searchTableName");
                List<TableDTO> taList = tadao.list(searchTableName);
                if (taList != null && !taList.isEmpty()) {
                    request.setAttribute("taList", taList);
                } else {
                    request.setAttribute("error", "No table matched found.");
                }

                url = "admin-table.jsp";
            } else if (action.equals("user")) {

                String searchname = request.getParameter("searchname");
                List<UserDTO> list = udao.listUsers(searchname);

                if (list != null && !list.isEmpty()) {
                    request.setAttribute("list", list);
                } else {
                    request.setAttribute("error", "No user matched.");
                }
                url = "admin-user.jsp";

            } else if (action.equals("delete-user")) {
                HttpSession session = request.getSession(false);
                if (session == null) {
                    url = "login.jsp";
                    return;
                }
                try {
                    int id = Integer.parseInt(request.getParameter("user_delete_id"));
                    UserDAO dao = new UserDAO();

                    if (id != 0) {
                        try {
                            if (dao.deleteUser(id)) {
                                request.setAttribute("error", "User deleted successfully.");
                            } else {
                                request.setAttribute("error", "Failed to delete user. User not found.");
                            }
                        } catch (SQLException e) {
                            request.setAttribute("error", "Database error: " + e.getMessage());
                        }
                    } else {
                        request.setAttribute("error", "Invalid user ID.");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid user ID format.");
                }

                UserDAO dao = new UserDAO();
                List<UserDTO> list = dao.listUsers(null);
                request.setAttribute("list", list);

                url = "admin-user.jsp";

            } else if (action.equals("add-user")) {
                url = "admin-user-add.jsp";

            } else if (action.equals("edit-user")) {
                try {
                    int id = Integer.parseInt(request.getParameter("user_edit_id"));
                    UserDAO dao = new UserDAO();
                    UserDTO user = dao.load(id);

                    if (user != null) {
                        request.setAttribute("user", user);
                        url = "admin-user-edit.jsp";
                    } else {
                        request.setAttribute("error", "Không tìm thấy người dùng với ID: " + id);
                        url = "admin-user.jsp";
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "ID người dùng không hợp lệ");
                    url = "admin-user.jsp";
                }

            } else if (action.equals("save-user")) {

                String fullname = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phonenumber = request.getParameter("phoneNumber");
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                UserDTO user = new UserDTO();
                user.setFullname(fullname);
                user.setEmail(email);
                user.setPhonenumber(phonenumber);
                user.setUsername(username);
                user.setPassword(password);

                UserDAO dao = new UserDAO();
                try {
                    if (dao.isEmailTaken(email)) {
                        request.setAttribute("error", "email already in use");
                        url = "admin-user.jsp";
                        return;
                    }
                    
                    if (dao.isUsernameTaken(username)) {
                        request.setAttribute("error", "Username is already in use");
                        request.setAttribute("user", user); 
                        url = "admin-user-add.jsp";
                        return;
                    }

                    if (dao.insertUser(user)) {
                        request.setAttribute("success", "User added successfully");
                        List<UserDTO> list = dao.listUsers(null);
                        request.setAttribute("list", list);
                        url = "admin-user.jsp";
                    } else {
                        request.setAttribute("error", "Failed to add user. Please try again.");
                        request.setAttribute("user", user); 
                        url = "admin-user-add.jsp";
                    }
                } catch (SQLException e) {
                    request.setAttribute("error", "Database error: " + e.getMessage());
                    url = "admin-user-add.jsp";
                }

            } else if (action.equals("save-update-user")) {
                try {
                    int userID = Integer.parseInt(request.getParameter("userID"));
                    String fullname = request.getParameter("fullName");
                    String email = request.getParameter("email");
                    String phonenumber = request.getParameter("phoneNumber");
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");

                    UserDTO user = new UserDTO();
                    user.setUserID(userID);
                    user.setFullname(fullname);
                    user.setEmail(email);
                    user.setPhonenumber(phonenumber);
                    user.setUsername(username);
                    user.setPassword(password);

                    UserDAO dao = new UserDAO();

                    if (dao.isEmailTaken(email)) {
                        request.setAttribute("error", "Email already in use by another account");
                        request.setAttribute("user", user);
                        url = "admin-user.jsp";
                    } else if (dao.isUsernameTaken(username)) {
                        request.setAttribute("error", "Username already in use by another account");
                        request.setAttribute("user", user);
                        url = "admin-user.jsp";
                    } else if (dao.updateUser(user)) {
                        request.setAttribute("error", "User updated successfully");
                        List<UserDTO> list = dao.listUsers(null);
                        request.setAttribute("list", list);
                        url = "admin-user.jsp";
                    } else {
                        request.setAttribute("error", "Failed to update user");
                        request.setAttribute("user", user);
                        url = "admin-user-edit.jsp";
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid user ID");
                    url = "admin-user.jsp";
                } catch (SQLException e) {
                    request.setAttribute("error", "Database error: " + e.getMessage());
                    url = "admin-user.jsp";
                }

                //round controller
            } else if (action.equals("round")) {

                String searchRoundName = request.getParameter("searchRoundName");
                List<RoundDTO> list = rdao.searchRoundsByName(searchRoundName);
                if (list != null && !list.isEmpty()) {
                    request.setAttribute("list", list);
                } else {
                    request.setAttribute("error", "No round matched.");
                }

                url = "admin-round.jsp";

            } else if (action.equals("delete-round")) {
                try {
                    String roundID = request.getParameter("round_delete_id");
                    RoundDAO dao = new RoundDAO();

                    if (roundID != null && !roundID.isEmpty()) {
                        try {
                            if (dao.deleteRound(roundID)) {
                                request.setAttribute("error", "Round deleted successfully.");
                            } else {
                                request.setAttribute("error", "Failed to delete round. Round is happening.");
                            }
                        } catch (Exception e) {
                            request.setAttribute("error", "Database error: " + e.getMessage());
                        }
                    } else {
                        request.setAttribute("error", "Invalid round ID.");
                    }
                } catch (Exception e) {
                    request.setAttribute("error", "Invalid round ID format.");
                }

                RoundDAO dao = new RoundDAO();
                List<RoundDTO> list = dao.searchRoundsByName("");
                request.setAttribute("list", list);
                url = "admin-round.jsp";
            } else if (action.equals("add-round")) {
                url = "admin-round-add.jsp";

            } else if (action.equals("edit-round")) {
                try {
                    String roundID = request.getParameter("round_edit_id");

                    if (roundID != null && !roundID.isEmpty()) {
                        RoundDAO dao = new RoundDAO();
                        RoundDTO round = dao.loadRound(roundID);

                        if (round != null) {
                            request.setAttribute("round", round);
                            url = "admin-round-edit.jsp";
                        } else {
                            request.setAttribute("error", "Round with ID: " + roundID + " not found");
                            url = "admin-round.jsp";
                        }
                    } else {
                        request.setAttribute("error", "Invalid Round ID");
                        url = "admin-round.jsp";
                    }
                } catch (Exception e) {
                    request.setAttribute("error", "Error loading round: " + e.getMessage());
                    url = "admin-round.jsp";
                }
            } else if (action.equals("save-round")) {
                String roundName = request.getParameter("roundName");

                RoundDTO round = new RoundDTO();
                round.setRoundname(roundName);

                RoundDAO dao = new RoundDAO();
                try {

                    if (dao.isRoundNameTaken(roundName)) {
                        request.setAttribute("error", "Round name already in use");
                        url = "admin-round.jsp";
                        return;
                    } else if (dao.addRound(round)) {
                        request.setAttribute("message", "Successfully added round");
                        List<RoundDTO> list = dao.searchRoundsByName("");
                        request.setAttribute("list", list);
                        url = "admin-round.jsp";
                    } else {
                        request.setAttribute("error", "Error adding round");
                        url = "admin-round.jsp";
                    }
                } catch (SQLException e) {
                    request.setAttribute("error", "Database error: " + e.getMessage());
                    url = "admin-round.jsp";
                }
            } else if (action.equals("save-update-round")) {
                try {
                    String roundID = request.getParameter("roundID");
                    String roundName = request.getParameter("roundName");
                    if (roundID != null && !roundID.isEmpty() && roundName != null && !roundName.isEmpty()) {
                        RoundDTO round = new RoundDTO();
                        round.setRoundid(roundID);
                        round.setRoundname(roundName);

                        RoundDAO dao = new RoundDAO();

                        if (dao.isRoundNameTaken(roundName)) {
                            request.setAttribute("error", "Round name already exists");
                            request.setAttribute("round", round);
                            url = "admin-round.jsp";
                        } else if (dao.updateRound(round)) {
                            request.setAttribute("error", "Round updated successfully");
                            List<RoundDTO> list = dao.searchRoundsByName("");
                            request.setAttribute("list", list);
                            url = "admin-round.jsp";
                        } else {
                            request.setAttribute("error", "Failed to update round");
                            request.setAttribute("round", round);
                            url = "admin-round.jsp";
                        }
                    } else {
                        request.setAttribute("error", "Round ID and Round Name cannot be empty");
                        url = "admin-round.jsp";
                    }
                } catch (Exception e) {
                    request.setAttribute("error", "Error updating round: " + e.getMessage());
                    url = "admin-round.jsp";
                }

            } else if (action.equals("create-table")) {

                TableDTO table = new TableDTO();
                request.setAttribute("table", table);
                request.setAttribute("nextaction", "insert-table");
                request.setAttribute("pageTitle", "Add new Table");
                request.setAttribute("pageHeading", "Add new Table");
                url = "admin-detail-table.jsp";

            } else if (action.equals("insert-table")) {
                String tableName = request.getParameter("tableName");

                TableDTO table = new TableDTO();
                table.setTableName(tableName);

                tadao.insert(table);

                request.setAttribute("message", "Successfully added.");
                request.setAttribute("table", table);
                url = "admin-detail-table.jsp";
            } else if (action.equals("edit-table")) {
                Integer tableID = null;
                try {
                    tableID = Integer.parseInt(request.getParameter("tableID"));
                } catch (NumberFormatException ex) {
                    log("Table ID is in wrong format.");
                }

                TableDTO table = tadao.load(tableID);

                request.setAttribute("table", table);
                request.setAttribute("nextaction", "update-table");
                request.setAttribute("pageTitle", "Edit Table Details");
                request.setAttribute("pageHeading", "Edit Table Details");
                url = "admin-detail-table.jsp";

            } else if (action.equals("update-table")) {

                Integer tableID = null;
                try {
                    tableID = Integer.parseInt(request.getParameter("tableID"));
                } catch (NumberFormatException ex) {
                    log("Table ID is in wrong format.");
                }

                String tableName = request.getParameter("tableName");

                TableDTO table = null;
                if (tableID != null) {
                    table = tadao.load(tableID);
                }

                if (table != null) {
                    table.setTableName(tableName);

                    tadao.update(table);
                    request.setAttribute("table", table);
                    request.setAttribute("message", "Successfully updated.");
                }

                url = "admin-detail-table.jsp";

            } else if (action.equals("delete-table")) {
                Integer tableID = null;
                try {
                    tableID = Integer.parseInt(request.getParameter("tableID"));
                } catch (NumberFormatException ex) {
                    log("Table ID is in wrong format.");
                }
                if (tableID != null) {
                    tadao.delete(tableID);
                    request.setAttribute("message", "Successfully deleted.");
                } else {
                    request.setAttribute("error", "Could not delete this table.");
                }

                String searchTableName = request.getParameter("searchTableName");
                List<TableDTO> taList = tadao.list(searchTableName);
                request.setAttribute("taList", taList);

                url = "admin-table.jsp";
                
            } 
            
            else if (action.equals("tournament")) {

                String searchTourName = request.getParameter("searchTourName");
                List<TournamentDTO> tList = tdao.list(searchTourName);

                if (tList != null && !tList.isEmpty()) {
                    request.setAttribute("tList", tList);
                } else {
                    request.setAttribute("error", "No tournament matched.");
                }

                url = "admin-tournament.jsp";

            } 
            
            else if (action.equals("edit-tournament")) {
                Integer tournamentID = null;
                try {
                    tournamentID = Integer.parseInt(request.getParameter("tournamentID"));
                } catch (NumberFormatException ex) {
                    log("Tournament ID is in wrong format.");
                }

                TournamentDTO tournament = tdao.load(tournamentID);
                request.setAttribute("tournament", tournament);
                request.setAttribute("nextaction", "update-tournament");
                request.setAttribute("pageTitle", "Edit Tournament Details");
                request.setAttribute("pageHeading", "Edit Tournament Details");
                url = "admin-detail-tournament.jsp";

            } 
            
            else if (action.equals("update-tournament")) {
                Integer tournamentID = null;
                try {
                    tournamentID = Integer.parseInt(request.getParameter("tournamentID"));
                } catch (NumberFormatException ex) {
                    request.setAttribute("error", "Invalid Tournament ID format.");
                    url = "admin-detail-tournament.jsp";
                    return;
                }

                String tournamentName = request.getParameter("tournamentName");
                String description = request.getParameter("description");
                String location = request.getParameter("location");

                String startTimeStr = request.getParameter("startTime");
                String endTimeStr = request.getParameter("endTime");
                Timestamp startTime;
                Timestamp endTime;

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    startTime = Timestamp.valueOf(LocalDateTime.parse(startTimeStr, formatter));
                    endTime = Timestamp.valueOf(LocalDateTime.parse(endTimeStr, formatter));
                    if (tt.isStartTimeAfterEndTime(startTime, endTime)) {
                        request.setAttribute("error", "Start Time should not be after End Time.");
                        request.setAttribute("tournament", tdao.load(tournamentID));
                        url = "admin-detail-tournament.jsp";
                        return;
                    }
                } catch (DateTimeParseException ex) {
                    request.setAttribute("error", "Invalid date format. Please use yyyy-MM-ddTHH:mm.");
                    request.setAttribute("tournament", tdao.load(tournamentID));
                    url = "admin-detail-tournament.jsp";
                    return;
                }

                String imgFileName = null;

                try {
                    Part filePart = request.getPart("logo");
                    if (filePart != null && filePart.getSize() > 0 && tournamentName != null && !tournamentName.trim().isEmpty()) {
                        tdao.clearImgFile(tournamentID);

                        String fileName = tournamentName + "-logo.png";
                        
                        // Hardcoded path to save images
                        String uploadPath = "D:/Project/StrikeTheFrog/prj301-25sp-se1840-08/src/main/webapp/assets/images/tournamentLogo";

                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        InputStream input = filePart.getInputStream();
                        File outputFile = new File(uploadPath + File.separator + fileName);

                        OutputStream output = new FileOutputStream(outputFile);

                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = input.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }

                        input.close();
                        output.close();

                        imgFileName = "assets/images/tournamentLogo/" + fileName;

                        System.out.println("File successfully uploaded to: " + outputFile.getAbsolutePath());
                    }
                } catch (IOException | ServletException ex) {
                    request.setAttribute("error", "Failed to upload logo. Please try again.");
                    url = "admin-detail-tournament.jsp";
                    return;
                }

                TournamentDTO tournament = tdao.load(tournamentID);
                if (tournament != null) {
                    tournament.setTournamentName(tournamentName);
                    tournament.setDescription(description);
                    tournament.setLocation(location);
                    tournament.setStartTime(startTime);
                    tournament.setEndTime(endTime);
                    if (imgFileName != null) {
                        tournament.setImgFile(imgFileName);
                    }
                    tdao.update(tournament);
                    request.setAttribute("message", "Successfully updated.");
                }

                request.setAttribute("tournament", tournament);
                url = "admin-detail-tournament.jsp";

            } else if (action.equals("create-tournament")) {
                TournamentDTO tournament = new TournamentDTO();
                request.setAttribute("tournament", tournament);
                request.setAttribute("nextaction", "insert-tournament");
                request.setAttribute("pageTitle", "Add new Tournament");
                request.setAttribute("pageHeading", "Add new Tournament");
                url = "admin-detail-tournament.jsp";
            } else if (action.equals("insert-tournament")) {
                String tournamentName = request.getParameter("tournamentName");
                String description = request.getParameter("description");
                String location = request.getParameter("location");
                String startTimeStr = request.getParameter("startTime");
                String endTimeStr = request.getParameter("endTime");

                Timestamp startTime;
                Timestamp endTime;
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                    startTime = Timestamp.valueOf(LocalDateTime.parse(startTimeStr, formatter));
                    endTime = Timestamp.valueOf(LocalDateTime.parse(endTimeStr, formatter));
                    if (tt.isStartTimeAfterEndTime(startTime, endTime)) {
                        request.setAttribute("error", "Start Time should not be after End Time.");
                        url = "admin-detail-tournament.jsp";
                        return;
                    }
                } catch (DateTimeParseException ex) {
                    request.setAttribute("error", "Invalid date format. Please use yyyy-MM-ddTHH:mm.");
                    url = "admin-detail-tournament.jsp";
                    return;
                }

                String imgFileName = null;
                Part filePart = request.getPart("logo");

                if (filePart != null && filePart.getSize() > 0 && tournamentName != null && !tournamentName.trim().isEmpty()) {
                    try {
                        String fileName = tournamentName + "-logo.png";
                        
                        // Hardcoded path to save images
                        String uploadPath = "D:/Project/StrikeTheFrog/prj301-25sp-se1840-08/src/main/webapp/assets/images/tournamentLogo";
                        
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        InputStream input = filePart.getInputStream();
                        File outputFile = new File(uploadPath + File.separator + fileName);
                        
                        OutputStream output = new FileOutputStream(outputFile);
                        
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = input.read(buffer)) > 0) {
                            output.write(buffer, 0, length);
                        }
                        
                        input.close();
                        output.close();
                        
                        imgFileName = "assets/images/tournamentLogo/" + fileName;
                        
                        System.out.println("File successfully uploaded to: " + outputFile.getAbsolutePath());
                        System.out.println("Database path set to: " + imgFileName);
                    } catch (IOException ex) {
                        log("Error writing file: " + ex.getMessage());
                        request.setAttribute("error", "Failed to upload logo: " + ex.getMessage());
                        url = "admin-detail-tournament.jsp";
                        return;
                    }
                }

                TournamentDTO tournament = new TournamentDTO();
                tournament.setTournamentName(tournamentName);
                tournament.setDescription(description);
                tournament.setLocation(location);
                tournament.setStartTime(startTime);
                tournament.setEndTime(endTime);
                tournament.setImgFile(imgFileName);

                tdao.insert(tournament);
                request.setAttribute("message", "Successfully added tournament");
                request.setAttribute("tournament", tournament);
                url = "admin-detail-tournament.jsp";
            } else if (action.equals("delete-tournament")) {
                Integer tournamentID = null;
                try {
                    tournamentID = Integer.parseInt(request.getParameter("tournamentID"));
                } catch (NumberFormatException ex) {
                    log("Tournament ID is in wrong format.");
                }

                if (tournamentID != null) {
                    tdao.delete(tournamentID);
                }

                String searchTourName = request.getParameter("searchTourName");
                List<TournamentDTO> tList = tdao.list(searchTourName);
                request.setAttribute("tList", tList);
                request.setAttribute("message", "Successfully deleted.");
                url = "/Dashboard?action=tournament";

            } else if (action.equals("search-admin-match")) {
                String tName = request.getParameter("tournamentName");
                String mID = request.getParameter("matchID");
                MatchDAO matchDAO = new MatchDAO();
                try {
                    // Validate matchID first
                    if (mID != null && !mID.isEmpty()) {
                        Integer.parseInt(mID);
                    }
                    List<MatchDTO> list = matchDAO.list(mID, tName);
                    if (list != null && !list.isEmpty()) {
                        request.setAttribute("list", list);
                    } else {
                        request.setAttribute("error", "No matches found");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Error! MatchID must be a number");
                } catch (SQLException e) {
                    request.setAttribute("error", "Error! Invalid search parameters");
                }
                url = "admin-match.jsp";

            } else if (action.equals("upload-logo")) {
                String tournamentName = request.getParameter("tournamentName");
                Part filePart = request.getPart("logo");
                if (filePart != null && tournamentName != null && !tournamentName.trim().isEmpty()) {
                    String fileName = tournamentName + "-logo.png";
                    String uploadPath = getServletContext().getRealPath("") + File.separator + "assets" + File.separator + "images" + File.separator + "tournamentLogo";

                    File uploadDir = new File(uploadPath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    filePart.write(uploadPath + File.separator + fileName);
                    request.setAttribute("message", "Logo uploaded successfully as " + fileName);
                } else {
                    request.setAttribute("error", "Failed to upload logo.");
                }

                url = "admin-detail-tournament.jsp";
            } else if (action.equals("edit-match")) {
                String tName = request.getParameter("tournamentName");
                int mID = 0;
                try {
                    mID = Integer.parseInt(request.getParameter("matchID"));
                } catch (NumberFormatException e) {
                    System.out.println("Error in edit-match action. Error mID");
                }
                MatchDAO matchDAO = new MatchDAO();
                try {
                    MatchDTO match = matchDAO.getByMatchIDAndTournamentName(mID, tName);
                    if (match != null) {
                        request.setAttribute("match", match);
                    } else {
                        request.setAttribute("error", "Error! Cannot find match with that MatchID and TournamentName");
                    }
                    url = "admin-match.jsp";
                } catch (SQLException e) {
                    log("Error searching matches: " + e.getMessage());
                }
                request.setAttribute("readonly", true);
                request.setAttribute("nextaction", "update-match");
                url = "admin-detail-match.jsp";
            } else if (action.equals("logout")) {
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.invalidate();
                }
                request.setAttribute("error", "logout admin successfully");
                url = "login.jsp";
            } else if (action.equals("get-player")) {
                try {

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();

                    PlayerDAO playerDAO = new PlayerDAO();
                    int playerID = Integer.parseInt(request.getParameter("playerID"));
                    PlayerDTO player = playerDAO.load(playerID);

                    if (player != null) {

                        String jsonResponse = "{\"playerName\":\"" + player.getFullName() + "\"}";
                        out.print(jsonResponse);
                    } else {
                        out.print("{\"error\":\"Player not found\"}");
                    }

                    out.flush();
                    return;
                } catch (Exception e) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print("{\"error\":\"Error: " + e.getMessage() + "\"}");
                    out.flush();
                    e.printStackTrace();
                    return;
                }
            } else if (action.equals("update-match")) {
                try {
                    // Lấy thông tin từ form
                    int matchID = 0;
                    int roundID = 0;
                    int tableID = 0;
                    int player1ID = 0;
                    int player2ID = 0;
                    int scoreP1 = 0;
                    int scoreP2 = 0;

                    // Xử lý an toàn các giá trị số
                    try {
                        matchID = Integer.parseInt(request.getParameter("matchID"));
                    } catch (NumberFormatException e) {
                        log("Invalid matchID: " + request.getParameter("matchID"));
                        throw new NumberFormatException("Invalid Match ID");
                    }

                    try {
                        roundID = Integer.parseInt(request.getParameter("roundID"));
                    } catch (NumberFormatException e) {
                        log("Invalid roundID: " + request.getParameter("roundID"));
                        throw new NumberFormatException("Invalid Round ID");
                    }

                    try {
                        tableID = Integer.parseInt(request.getParameter("tableID"));
                    } catch (NumberFormatException e) {
                        log("Invalid tableID: " + request.getParameter("tableID"));
                        throw new NumberFormatException("Invalid Table ID");
                    }

                    try {
                        player1ID = Integer.parseInt(request.getParameter("player1ID"));
                    } catch (NumberFormatException e) {
                        log("Invalid player1ID: " + request.getParameter("player1ID"));
                        throw new NumberFormatException("Invalid Player 1 ID");
                    }

                    try {
                        player2ID = Integer.parseInt(request.getParameter("player2ID"));
                    } catch (NumberFormatException e) {
                        log("Invalid player2ID: " + request.getParameter("player2ID"));
                        throw new NumberFormatException("Invalid Player 2 ID");
                    }

                    try {
                        scoreP1 = Integer.parseInt(request.getParameter("scoreP1"));
                    } catch (NumberFormatException e) {
                        log("Invalid scoreP1: " + request.getParameter("scoreP1"));
                        throw new NumberFormatException("Invalid Score for Player 1");
                    }

                    try {
                        scoreP2 = Integer.parseInt(request.getParameter("scoreP2"));
                    } catch (NumberFormatException e) {
                        log("Invalid scoreP2: " + request.getParameter("scoreP2"));
                        throw new NumberFormatException("Invalid Score for Player 2");
                    }

                    String gameStatus = request.getParameter("status");

                    String matchTimeStr = request.getParameter("matchtime");
                    Timestamp matchTime = null;
                    if (matchTimeStr != null && !matchTimeStr.isEmpty()) {
                        try {
                            // Chuyển đổi từ định dạng datetime-local (yyyy-MM-ddTHH:mm) sang Timestamp
                            LocalDateTime localDateTime = LocalDateTime.parse(matchTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                            matchTime = Timestamp.valueOf(localDateTime);
                        } catch (Exception e) {
                            url = "admin-detail-match.jsp";
                            log("Error parsing match time: " + e.getMessage());
                            matchTime = new Timestamp(System.currentTimeMillis());
                            return;
                        }
                    } else {
                        matchTime = new Timestamp(System.currentTimeMillis());
                    }

                    int winnerPlayerID = 0;
                    if (scoreP1 > scoreP2) {
                        winnerPlayerID = player1ID;
                    } else if (scoreP2 > scoreP1) {
                        winnerPlayerID = player2ID;
                    }

                    MatchDAO matchDAO = new MatchDAO();
                    String tournamentName = request.getParameter("tournamentName");
                    MatchDTO match = matchDAO.getByMatchIDAndTournamentName(matchID, tournamentName);

                    if (match != null) {
                        // Cập nhật thông tin mới
                        match.setRoundID(roundID);
                        match.setTableID(tableID);
                        match.setPlayer1ID(player1ID);
                        match.setPlayer2ID(player2ID);
                        match.setScoreP1(scoreP1);
                        match.setScoreP2(scoreP2);
                        match.setGameStatus(gameStatus);
                        // Trong phần update-match, sửa đoạn code xác thực thời gian
                        // In the create-match or update-match action, add this validation before inserting/updating
                        int tournamentID = tdao.getIDByName(tournamentName);
                        if (matchTime != null && tournamentID > 0) {
                            TournamentDAO.ValidationResult validationResult = tdao.validateMatchTime(tournamentID, matchTime);
                            if (!validationResult.isValid()) {
                                request.setAttribute("error", validationResult.getMessage());
                                // Giữ lại thông tin trận đấu để hiển thị lại form
                                request.setAttribute("match", match);
                                request.setAttribute("readonly", false);
                                request.setAttribute("nextaction", "update-match");
                                url = "admin-detail-match.jsp";
                                RequestDispatcher rd = request.getRequestDispatcher(url);
                                rd.forward(request, response);
                                return;
                            }
                        }

                        match.setMatchTime(matchTime);
                        match.setWinnerPlayerID(winnerPlayerID);
                        boolean updated = matchDAO.update(match);

                        if (updated) {
                            request.setAttribute("success", "Match updated successfully");
                        } else {
                            request.setAttribute("error", "Failed to update match");
                        }
                    } else {
                        request.setAttribute("error", "Match not found");
                    }

                    String mID = request.getParameter("mID");
                    String tName = request.getParameter("tName");
                    List<MatchDTO> list = mdao.list(mID, tName);
                    request.setAttribute("list", list);

                    url = "admin-match.jsp";

                } catch (NumberFormatException e) {
                    log("Error in update-match action: " + e.getMessage());
                    request.setAttribute("error", "Invalid number format in form data");
                    url = "admin-match.jsp";
                } catch (SQLException e) {
                    log("Database error in update-match action: " + e.getMessage());
                    request.setAttribute("error", "Database error: " + e.getMessage());
                    url = "admin-match.jsp";
                } catch (Exception e) {
                    log("Unexpected error in update-match action: " + e.getMessage());
                    request.setAttribute("error", "Unexpected error: " + e.getMessage());
                    url = "admin-match.jsp";
                }
            } else if (action.equals("delete-match")) {
                List<MatchDTO> list = new ArrayList<>();
                try {
                    String tournamentName = request.getParameter("tournamentName");
                    String mIDStr = request.getParameter("matchID");
                    int mID = Integer.parseInt(mIDStr);
                    int tournamentID = tdao.getIDByName(tournamentName);
                    boolean deleteState = mdao.delete(mID, tournamentID);
                    if (deleteState) {
                        request.setAttribute("message", "Delete successfully MatchID: " + mIDStr + " AND Tournament with Name: " + tournamentName);
                    } else {
                        request.setAttribute("error", "Delete failed!");
                    }
                    list = mdao.list(mIDStr, tournamentName);
                } catch (NumberFormatException | SQLException e) {
                    log("Error in delete match action: " + e.getMessage());
                    request.setAttribute("error", "Error in delete match action");
                }
                request.setAttribute("list", list);
                url = "Dashboard?action=match";
            } else if (action.equals("navigate-to-create-match")) {
                MatchDTO newMatch = new MatchDTO();
                newMatch.setScoreP1(0);
                newMatch.setScoreP2(0);
                newMatch.setGameStatus("Pending");
                newMatch.setMatchTime(new Timestamp(System.currentTimeMillis()));

                request.setAttribute("match", newMatch);
                request.setAttribute("readonly", false);
                request.setAttribute("nextaction", "create-match");
                url = "admin-detail-match.jsp";
            } else if (action.equals("create-match")) {
                try {
                    // Get tournament ID from tournament name
                    String tournamentName = request.getParameter("tournamentName");
                    int tournamentID = tdao.getIDByName(tournamentName);

                    if (tournamentID <= 0) {
                        request.setAttribute("error", "Tournament not found: " + tournamentName);
                        url = "admin-match.jsp";
                        List<MatchDTO> list = mdao.list(null, null);
                        request.setAttribute("list", list);
                        return;
                    }

                    // Create new match object
                    MatchDTO match = new MatchDTO();

                    // Set tournament info
                    match.setTournamentName(tournamentName);
                    match.setTournamentID(tournamentID);

                    // Set other match details
                    int matchID = Integer.parseInt(request.getParameter("matchID"));
                    int roundID = Integer.parseInt(request.getParameter("roundID"));
                    int tableID = Integer.parseInt(request.getParameter("tableID"));
                    int player1ID = Integer.parseInt(request.getParameter("player1ID"));
                    int player2ID = Integer.parseInt(request.getParameter("player2ID"));
                    int scoreP1 = Integer.parseInt(request.getParameter("scoreP1"));
                    int scoreP2 = Integer.parseInt(request.getParameter("scoreP2"));
                    String gameStatus = request.getParameter("status");

                    // Determine winner based on scores
                    int winnerPlayerID = 0;
                    if (scoreP1 > scoreP2) {
                        winnerPlayerID = player1ID;
                        match.setWinnerPlayerID(winnerPlayerID);
                    } else if (scoreP2 > scoreP1) {
                        winnerPlayerID = player2ID;
                        match.setWinnerPlayerID(winnerPlayerID);
                    }

                    // Parse match time
                    String matchTimeStr = request.getParameter("matchtime");
                    Timestamp matchTime = null;
                    if (matchTimeStr != null && !matchTimeStr.isEmpty()) {
                        try {
                            LocalDateTime localDateTime = LocalDateTime.parse(matchTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                            matchTime = Timestamp.valueOf(localDateTime);
                        } catch (Exception e) {
                            // Xử lý lỗi định dạng thời gian
                            request.setAttribute("error", "Invalid date format: " + e.getMessage());
                            request.setAttribute("match", match); // Giữ lại thông tin đã nhập
                            request.setAttribute("readonly", false);
                            request.setAttribute("nextaction", "create-match");
                            url = "admin-detail-match.jsp";
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                            return;
                        }
                    }

                    if (matchTime != null && tournamentID > 0) {
                        TournamentDAO.ValidationResult validationResult = tdao.validateMatchTime(tournamentID, matchTime);
                        if (!validationResult.isValid()) {
                            request.setAttribute("error", validationResult.getMessage());
                            // Giữ lại thông tin trận đấu để hiển thị lại form
                            request.setAttribute("match", match);
                            request.setAttribute("readonly", false);
                            request.setAttribute("nextaction", "create-match");
                            url = "admin-detail-match.jsp";
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);
                            return;
                        }
                    }

                    // Set all values to match object
                    match.setRoundID(roundID);
                    match.setTableID(tableID);
                    match.setMatchID(matchID);
                    match.setPlayer1ID(player1ID);
                    match.setPlayer2ID(player2ID);
                    match.setScoreP1(scoreP1);
                    match.setScoreP2(scoreP2);
                    match.setGameStatus(gameStatus);
                    match.setMatchTime(matchTime);

                    try {
                        System.out.println("Attempting to insert match: " + match.toString());
                        boolean success = mdao.insert(match);

                        if (success) {
                            request.setAttribute("message", "Match added successfully");
                        } else {
                            request.setAttribute("error", "Failed to add match - Database operation returned false");
                            System.out.println("Insert operation returned false");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        request.setAttribute("error", "Database error: " + e.getMessage());
                        System.out.println("SQL Exception during insert: " + e.getMessage());
                    }

                    // Refresh match list
                    List<MatchDTO> list = mdao.list(null, null);
                    request.setAttribute("list", list);
                    url = "admin-match.jsp";

                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid number format: " + e.getMessage());
                    url = "admin-match.jsp";
                } catch (Exception e) {
                    request.setAttribute("error", "Error adding match: " + e.getMessage());
                    url = "admin-match.jsp";
                }
            } else if (action.equals("transaction")) {
                TransactionDAO transactionDAO = new TransactionDAO();
                String playerIDStr = request.getParameter("playerID");
                String tournamentIDStr = request.getParameter("tournamentID");
                String paymentStatus = request.getParameter("paymentStatus");

                try {
                    Integer playerID = (playerIDStr != null && !playerIDStr.isEmpty()) ? Integer.parseInt(playerIDStr) : -1;
                    Integer tournamentID = (tournamentIDStr != null && !tournamentIDStr.isEmpty()) ? Integer.parseInt(tournamentIDStr) : -1;

                    List<TransactionDTO> list;
                    if (paymentStatus != null && !paymentStatus.isEmpty()) {
                        list = transactionDAO.listTransactionsByPaymentStatus(paymentStatus);
                    } else {
                        list = transactionDAO.listTransactions(playerID, tournamentID);
                    }

                    if (list != null && !list.isEmpty()) {
                        request.setAttribute("list", list);
                    } else {
                        request.setAttribute("error", "No transactions found.");
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid filter parameters: Player ID or Tournament ID must be numbers.");
                } catch (Exception e) {
                    request.setAttribute("error", "Error retrieving transactions: " + e.getMessage());
                    e.printStackTrace();
                }
                url = "admin-transaction.jsp";
            } else if (action.equals("add-transaction")) {
                url = "admin-transaction-add.jsp";
            } else if (action.equals("save-new-transaction")) {
                TransactionDAO transactionDAO = new TransactionDAO();
                try {
                    int playerID = Integer.parseInt(request.getParameter("playerID"));
                    int tournamentID = Integer.parseInt(request.getParameter("tournamentID"));
                    String paymentStatus = request.getParameter("paymentStatus");
                    String creationTimeStr = request.getParameter("creationTime");
                    String expirationDateStr = request.getParameter("expirationDate");

                    Timestamp creationTime = Timestamp.valueOf(creationTimeStr.replace("T", " ") + ":00");
                    Timestamp expirationDate = Timestamp.valueOf(expirationDateStr.replace("T", " ") + ":00");

                    TransactionDTO transaction = new TransactionDTO();
                    transaction.setPlayerID(playerID);
                    transaction.setTournamentID(tournamentID);
                    transaction.setPaymentStatus(paymentStatus);
                    transaction.setCreationTime(creationTime);
                    transaction.setExpirationDate(expirationDate);
                    if (transactionDAO.transactionExists(playerID, tournamentID)) {
                        request.setAttribute("error", "A transaction already exists for this Player and Tournament.");
                        url = "admin-transaction-add.jsp";
                    } else if (transactionDAO.insertTransaction(transaction)) {
                        request.setAttribute("error", "Transaction added successfully");
                        List<TransactionDTO> list = transactionDAO.listTransactions(-1, -1);
                        request.setAttribute("list", list);
                        url = "admin-transaction.jsp";
                    } else {
                        request.setAttribute("error", "Failed to add transaction");
                        url = "admin-transaction-add.jsp";
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid Player ID or Tournament ID");
                    url = "admin-transaction-add.jsp";
                } catch (IllegalArgumentException e) {
                    request.setAttribute("error", "Invalid date format");
                    url = "admin-transaction-add.jsp";
                }
            } else if (action.equals("delete-transaction")) {
                TransactionDAO transactionDAO = new TransactionDAO();
                try {
                    int transactionID = Integer.parseInt(request.getParameter("transaction_delete_id"));

                    if (transactionDAO.deleteTransaction(transactionID)) {
                        request.setAttribute("error", "Transaction deleted successfully");
                    } else {
                        request.setAttribute("error", "Failed to delete transaction. Transaction not found.");
                    }

                    // Refresh the list with current filters
                    String playerIDStr = request.getParameter("playerID");
                    String tournamentIDStr = request.getParameter("tournamentID");
                    String paymentStatus = request.getParameter("paymentStatus");
                    Integer playerID = (playerIDStr != null && !playerIDStr.isEmpty()) ? Integer.parseInt(playerIDStr) : -1;
                    Integer tournamentID = (tournamentIDStr != null && !tournamentIDStr.isEmpty()) ? Integer.parseInt(tournamentIDStr) : -1;

                    List<TransactionDTO> list;
                    if (paymentStatus != null && !paymentStatus.isEmpty()) {
                        list = transactionDAO.listTransactionsByPaymentStatus(paymentStatus);
                    } else {
                        list = transactionDAO.listTransactions(playerID, tournamentID);
                    }
                    if (list != null && !list.isEmpty()) {
                        request.setAttribute("list", list);
                    }
                    url = "admin-transaction.jsp";
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid transaction ID");
                    url = "admin-transaction.jsp";
                }
            } else if (action.equals("edit-transaction")) {
                TransactionDAO transactionDAO = new TransactionDAO();
                try {
                    int transactionID = Integer.parseInt(request.getParameter("transaction_edit_id"));
                    TransactionDTO transaction = transactionDAO.getTransactionById(transactionID);

                    if (transaction != null) {
                        request.setAttribute("transaction", transaction);
                        url = "admin-transaction-edit.jsp";
                    } else {
                        request.setAttribute("error", "Transaction not found with ID: " + transactionID);
                        url = "admin-transaction.jsp";
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid transaction ID");
                    url = "admin-transaction.jsp";
                } catch (Exception e) {
                    request.setAttribute("error", "Error retrieving transaction: " + e.getMessage());
                    e.printStackTrace();
                    url = "admin-transaction.jsp";
                }
            } else if (action.equals("save-edit-transaction")) {
                TransactionDAO transactionDAO = new TransactionDAO();
                try {
                    int transactionID = Integer.parseInt(request.getParameter("transactionID"));
                    int playerID = Integer.parseInt(request.getParameter("playerID"));
                    int tournamentID = Integer.parseInt(request.getParameter("tournamentID"));
                    String paymentStatus = request.getParameter("paymentStatus");
                    String creationTimeStr = request.getParameter("creationTime");
                    String expirationDateStr = request.getParameter("expirationDate");

                    // Convert datetime-local strings to Timestamps
                    Timestamp creationTime = Timestamp.valueOf(creationTimeStr.replace("T", " ") + ":00");
                    Timestamp expirationDate = Timestamp.valueOf(expirationDateStr.replace("T", " ") + ":00");

                    TransactionDTO transaction = new TransactionDTO();
                    transaction.setTransactionID(transactionID);
                    transaction.setPlayerID(playerID);
                    transaction.setTournamentID(tournamentID);
                    transaction.setPaymentStatus(paymentStatus);
                    transaction.setCreationTime(creationTime);
                    transaction.setExpirationDate(expirationDate);

                    // Check for duplicate PlayerID/TournamentID combination (excluding current transaction)
                    String sql = "SELECT COUNT(*) FROM Transactions WHERE PlayerID = ? AND TournamentID = ? AND TransactionID != ?";
                    try (Connection conn = DBUtils.getConnection();
                            PreparedStatement stmt = conn.prepareStatement(sql)) {
                        stmt.setInt(1, playerID);
                        stmt.setInt(2, tournamentID);
                        stmt.setInt(3, transactionID);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next() && rs.getInt(1) > 0) {
                            request.setAttribute("error", "Another transaction already exists for this Player and Tournament.");
                            request.setAttribute("transaction", transaction);
                            url = "admin-transaction-edit.jsp";
                        } else if (transactionDAO.updateTransaction(transaction)) {
                            request.setAttribute("error", "Transaction updated successfully");
                            // Refresh the list with current filters
                            String playerIDStr = request.getParameter("playerIDFilter");
                            String tournamentIDStr = request.getParameter("tournamentIDFilter");
                            String paymentStatusFilter = request.getParameter("paymentStatusFilter");
                            Integer playerIDFilter = (playerIDStr != null && !playerIDStr.isEmpty()) ? Integer.parseInt(playerIDStr) : -1;
                            Integer tournamentIDFilter = (tournamentIDStr != null && !tournamentIDStr.isEmpty()) ? Integer.parseInt(tournamentIDStr) : -1;
                            List<TransactionDTO> list = (paymentStatusFilter != null && !paymentStatusFilter.isEmpty())
                                    ? transactionDAO.listTransactionsByPaymentStatus(paymentStatusFilter)
                                    : transactionDAO.listTransactions(playerIDFilter, tournamentIDFilter);
                            request.setAttribute("list", list);
                            url = "admin-transaction.jsp";
                        } else {
                            request.setAttribute("error", "Failed to update transaction");
                            request.setAttribute("transaction", transaction);
                            url = "admin-transaction-edit.jsp";
                        }
                    }
                } catch (NumberFormatException e) {
                    request.setAttribute("error", "Invalid numeric input (ID or date)");
                    url = "admin-transaction-edit.jsp";
                } catch (IllegalArgumentException e) {
                    request.setAttribute("error", "Invalid date format");
                    url = "admin-transaction-edit.jsp";
                }
            }
        } catch (Exception e) {
            System.out.println("Error at AdminController. Details: " + e.getMessage());
        } finally {
            if (!response.isCommitted()) {
                request.getRequestDispatcher(url).forward(request, response);
            }
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
