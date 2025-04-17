### PRJ391 - Group Name: Strike The Frog - Project Name: Billiard Tournament
Tham khảo: [link](https://coccamco.web.app/register)
# Thành viên:
- SE196419 - Đỗ Phúc Duy
- SE196686 - Nguyễn Thế Minh
- SE196689 - Nguyễn Tuấn Anh
- SE196708 - Nguyễn Nhật Thông


# Hệ Thống Quản Lý Giải Đấu Billiard

## 1. Giới thiệu
Hệ thống quản lý giải đấu billiard là một nền tảng trực tuyến toàn diện giúp tổ chức và quản lý các giải đấu billiard một cách hiệu quả. Nó cung cấp các công cụ cho người dùng đăng ký tham gia, xem thông tin trận đấu và trạng thái đăng ký. Đồng thời, hệ thống cho phép admin quản lý và chỉnh sửa thông tin người dùng cũng như các khía cạnh khác của giải đấu.

## 2. Các Đối Tượng Sử Dụng
Hệ thống có hai loại người dùng chính:
**- User (Người dùng thông thường)**: Có quyền truy cập hạn chế, chủ yếu để đăng ký và xem thông tin giải đấu.
**- Admin (Quản trị viên)**: Có quyền truy cập đầy đủ để quản lý mọi khía cạnh của hệ thống và giải đấu.

---

## 3. Yêu cầu chức năng

### 3.1. Chức năng của User
Người dùng có thể thực hiện các chức năng sau:
- **Đăng ký tài khoản**: Cho phép người dùng tạo tài khoản mới.
- **Đăng nhập/Đăng xuất**: Ngăn chặn các hành động trái phép trong hệ thống.
- **Xem thông tin giải đấu**:
  - Danh sách các trận đấu của những giải đấu có sẵn.
  - Thời gian thi đấu.
  - Trạng thái đăng ký (đã đăng ký hay chưa).
  - Số bàn thi đấu.
- **Đăng ký tham gia giải đấu** (nếu thời hạn đăng ký vẫn còn).
- **Xem lịch sử giải đấu của mình**.

### 3.2. Chức năng của Admin
Admin có các quyền quản lý sau:
- **Quản lý người dùng**:
  - Xem danh sách người dùng.
  - Chỉnh sửa thông tin người chơi (họ tên, thời gian thi đấu, bàn thi đấu, trạng thái đăng ký).
  - Xóa người dùng khỏi danh sách đăng ký nếu cần.
- **Quản lý giải đấu/trận đấu**:
  - Thêm/Xoá/Sửa các giải đấu. 
  - Thêm/Xoá/Sửa các trận đấu.
  - Thêm/Xoá/Sửa các vòng loại.
  - Thêm/Xoá/Sửa bàn đấu
- **Xem thống kê**
  - Xem được số lượng người chơi, số lượng giải đấu tổng
  - Xem được thống kê về 3 giải đấu 3 gần đây nhất


### 4. Tính năng nổi bật
-**Cập nhật trực tiếp**: Kết quả và thống kê được cập nhật theo thời gian thực

-**Giao diện thân thiện**: Dễ dàng sử dụng cho cả người chơi và quản trị viên.

-**Hỗ trợ nhiều định dạng giải đấu**: Bao gồm vòng tròn, loại trực tiếp đơn, loại trực tiếp kép và các định dạng tùy chỉnh.

-**Tích hợp thanh toán**: Cho phép người chơi thanh toán phí đăng ký trực tuyến.

-**Báo cáo và phân tích**: Cung cấp thống kê chi tiết và báo cáo về hiệu suất giải đấu.

Hệ thống này sẽ giúp tổ chức và quản lý các giải đấu billiard một cách hiệu quả, mang lại trải nghiệm tốt cho cả người chơi và quản trị viên.

### 5. Bài học rút ra

-**Hiểu rõ nhu cầu người dùng**: Việc phân loại người dùng thành "User" và "Admin" giúp đáp ứng chính xác nhu cầu của từng nhóm. Điều này nhấn mạnh tầm quan trọng của việc nghiên cứu và lắng nghe ý kiến người dùng.

-**Quản lý dữ liệu hiệu quả**: Các tính năng thống kê và báo cáo chi tiết không chỉ giúp theo dõi hiệu suất mà còn hỗ trợ lập kế hoạch và cải thiện trong tương lai.

-**Cần đảm bảo rằng ID của các field luôn nhất quán khi chỉnh sửa**: Xác định rõ field nào được phép sửa, không được sửa, và với các field được sửa, cần giới hạn phạm vi để đảm bảo duy trì các ràng buộc liên quan.

-**Học được cách làm việc nhóm:** Quản lý thời gian hợp lý để phân bổ cho các công việc 1 cách hiệu quả, nâng cao khả năng đọc hiểu các requirement khi tranh luận với các bạn trong team để đưa ra solution hợp lý, biết cách giao task cân bằng,...

-**Áp dụng được những kiến thức đã học vào thực tiễn**: Sử dụng kiến trúc MVC2 vào trong project, sử dụng jdbc để query database lấy dữ liệu, thiết kế database phù hợp với requirement và các trường hợp trong thực tế, sử dụng taglib, session handling, kỹ thuật paging, file-upload, tạo mã qr để thanh toán trực tiếp,...


**Trang đăng nhập/ đăng ký**
![Hình Trang Đăng nhập](/asset/images/signup.png)
![Hình Trang Đăng ký](/asset/images/login.png)


**Trang homepage**
![Hình Trang Homepage](/asset/images/homepage.png)
![Hình Trang Homepage](/asset/images/homepage_2.png)
![Hình Trang Homepage](/asset/images/homepage_3.png)

**Trang đăng ký giải đấu**
![Hình Trang Chọn giải đấu](/asset/images/registerpage.png)

**Trang xem luật thi đấu**
![Hình Trang Chọn giải đấu](/asset/images/rulepage.png)

**Trang thanh toán để hoàn tất đăng ký**
![Hình Trang Thanh toán thành công](/asset/images/paymentstatus.png)


**Lịch sử đấu giải**
![Hình Trang Lịch Sử Thi Đấu](/asset/images/matchhistory.png)




## PHẦN ADMIN
**Trang Admin quản lý User**
![Hình Trang Admin Quản Lý User](/asset/images/user.png)
![Hình Trang Admin Quản Lý User](/asset/images/user-edit.png)

**Trang Admin quản lý Games**
![Hình Trang Admin Quản Lý Games](/asset/images/match.png)
![Hình Trang Admin Quản Lý Games](/asset/images/match-edit.png)

**Trang Admin quản lý Round**
![Hình Trang Admin Quản lý Round](/asset/images/round.png)
![Hình Trang Admin Quản lý Round](/asset/images/round-edit.png)

**Trang Admin quản lý Tournament**
![Hình Trang Admin Quản lý Tournament](/asset/images/tournament.png)
![Hình Trang Admin Quản lý Tournament](/asset/images/tournament-edit.png)


**Trang Admin quản lý Transaction**
![Hình Trang Admin Quản lý Tournament](/asset/images/transaction.png)
![Hình Trang Admin Quản lý Tournament](/asset/images/transaction-edit.png)

---
## ERD
![Hình minh hoạ ERD](/asset/images/erd.png)

## FLOWCHART
![Hình minh hoạ ERD](/asset/images/flowchart.png)



