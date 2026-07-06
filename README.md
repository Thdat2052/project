# Hướng Dẫn Khởi Chạy Dự Án (Room Management Web)

Dự án này là hệ thống Quản lý nhà trọ (Room Management Web) được xây dựng trên mô hình Microservices với **Spring Boot (Backend)**, **React + Vite + TypeScript + TailwindCSS (Frontend)** và **MySQL (Cơ sở dữ liệu)**.

---

## 🔑 Tài Khoản Đăng Nhập Mặc Định

Cơ sở dữ liệu đã được cấu hình sẵn 2 tài khoản mặc định để kiểm thử:

1. **Tài khoản Quản trị (Admin):**
   - **Tên đăng nhập:** `admin`
   - **Mật khẩu:** `admin123`
   - **Vai trò:** `ADMIN` (Truy cập được trang quản lý admin tại `/admin/dashboard`)

2. **Tài khoản Người thuê trọ (User):**
   - **Tên đăng nhập:** `user`
   - **Mật khẩu:** `user123`
   - **Vai trò:** `USER` (Truy cập được trang cá nhân tại `/profile`)

---

## 🐳 Cách 1: Khởi Chạy Bằng Docker Compose (Khuyên Dùng)

Đây là cách nhanh nhất và tiện lợi nhất để khởi chạy toàn bộ hệ thống (Frontend, Backend, Database) mà không cần cài đặt môi trường Java/Node.js trên máy local.

### Yêu cầu:
* Đã cài đặt **Docker** và **Docker Compose**.

### Các bước thực hiện:
1. Mở Terminal tại thư mục gốc của dự án (`D:\project`).
2. Khởi chạy toàn bộ hệ thống:
   ```bash
   docker compose up --build
   ```
   *(Thêm tham số `-d` ở cuối nếu bạn muốn chạy ngầm: `docker compose up -d --build`)*
3. Truy cập vào ứng dụng trên trình duyệt:
   * **Đăng nhập:** [http://localhost:5173/personal_project_front-end/login](http://localhost:5173/personal_project_front-end/login)

---

## 💻 Cách 2: Khởi Chạy Thủ Công Từng Phần (Local/Manual)

Sử dụng cách này nếu bạn muốn chạy Frontend độc lập ở local để tiện cho việc chỉnh sửa và debug code nhanh chóng.

### Bước 1: Khởi động Database (MySQL) bằng Docker
Chúng ta chạy riêng Database MySQL bằng file docker-compose thu nhỏ có sẵn trong BE:
```bash
cd D:\project\personal_project_back-end
docker compose up -d
```
*(MySQL sẽ chạy ở cổng `3306` với password root là `123456789`).*

### Bước 2: Khởi chạy Backend (Spring Boot Microservices)
Dự án backend là hệ thống gồm 5 microservices độc lập được quản lý bằng Maven.

1. **Build toàn bộ project:**
   ```bash
   cd D:\project\personal_project_back-end
   mvn clean install
   ```
2. **Khởi chạy các service cần thiết** (Mở từng cửa sổ terminal riêng biệt hoặc dùng IDE như IntelliJ IDEA để chạy):
   * **Auth Service** (Cổng `9996`):
     ```bash
     cd D:\project\personal_project_back-end\auth-service
     mvn spring-boot:run
     ```
   * **Main Service** (Cổng `9997`):
     ```bash
     cd D:\project\personal_project_back-end\main-service
     mvn spring-boot:run
     ```
   * *(Làm tương tự bằng lệnh `mvn spring-boot:run` cho các service còn lại như `file-service`, `payment-service`, `notify-service` nếu muốn sử dụng đầy đủ tính năng).*

### Bước 3: Khởi chạy Frontend (React + Vite)
1. Mở terminal mới và di chuyển tới thư mục FE:
   ```bash
   cd D:\project\personal_project_front-end
   ```
2. Cài đặt các package dependencies (nếu chạy lần đầu):
   ```bash
   npm install
   ```
3. Khởi chạy server phát triển (Development):
   ```bash
   npm run dev
   ```
   *Hoặc nếu gặp lỗi do thiếu script `sv` định nghĩa trong package.json:*
   ```bash
   npx vite --mode development
   ```
4. Truy cập giao diện:
   * Do Vite được cấu hình đường dẫn `base` (phục vụ deploy Github Pages), bạn cần truy cập chính xác:
     * **Link Đăng nhập:** [http://localhost:5173/personal_project_front-end/login](http://localhost:5173/personal_project_front-end/login)

---

## 🛠️ Một Số Lưu Ý & Sửa Lỗi Thường Gặp

### 1. Sửa cảnh báo "Vite base URL"
Nếu trình duyệt của bạn hiển thị thông báo khuyên bạn nên truy cập `/personal_project_front-end/login`, đó là do Vite đang chạy ở chế độ định tuyến có tiền tố. Bạn hãy truy cập trực tiếp bằng đường dẫn đầy đủ có tiền tố `/personal_project_front-end/` như hướng dẫn ở trên.

### 2. Thay đổi đường dẫn mặc định trên Local
Nếu bạn không muốn sử dụng tiền tố `/personal_project_front-end/` ở môi trường local nữa mà muốn truy cập ngắn gọn qua `http://localhost:5173/`:
1. Mở file vite.config.ts.
2. Sửa dòng `base: "/personal_project_front-end/",` thành `base: "/",`.
*(Lưu ý: Sửa lại đường dẫn cũ trước khi tiến hành deploy lên môi trường production hoặc Github Pages).*
