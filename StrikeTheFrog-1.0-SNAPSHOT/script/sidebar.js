/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


document.addEventListener('DOMContentLoaded', function () {
    const championshipMenuItem = document.getElementById('championship-menu-item');

    championshipMenuItem.addEventListener('click', function (e) {
        // Toggle class 'open' để hiển thị/ẩn dropdown
        this.classList.toggle('open');

        // Ngăn sự kiện click lan ra các phần tử cha
        e.stopPropagation();
    });

    // Xử lý click vào các mục con trong dropdown
    const dropdownItems = document.querySelectorAll('.dropdown-menu-item');
    dropdownItems.forEach(item => {
        item.addEventListener('click', function (e) {
            // Xử lý khi click vào mục con
            console.log('Clicked on:', this.textContent);
            // Thêm code điều hướng hoặc xử lý khác ở đây

            // Ngăn sự kiện click lan ra các phần tử cha
            e.stopPropagation();
        });
    });

    // Đóng dropdown khi click ra ngoài
    document.addEventListener('click', function () {
        championshipMenuItem.classList.remove('open');
    });
});