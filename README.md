# Chinese Chess (Cờ Tướng)

 <!-- Thay bằng đường dẫn tới ảnh chụp màn hình thực tế của trò chơi -->

Chinese Chess (Cờ Tướng) là một trò chơi chiến lược hai người truyền thống của Trung Quốc, được tái hiện trong dự án này dưới dạng một ứng dụng Java. Trò chơi cung cấp giao diện trực quan, hỗ trợ chơi đơn (chống lại máy) hoặc chơi đôi (hai người), cùng với các tính năng như đếm ngược thời gian, lưu trò chơi, và đầu hàng. Dự án được phát triển với mục tiêu mang lại trải nghiệm chơi Cờ Tướng mượt mà và hấp dẫn.

## Tính năng
- **Chế độ chơi**: Chơi với máy hoặc chơi với người khác (đang phát triển).
- **Đếm ngược thời gian**: Mỗi người chơi có thời gian giới hạn cho lượt đi, được hiển thị bằng thanh đếm ngược trực quan.
- **Giao diện thân thiện**: Bàn cờ và quân cờ được thiết kế rõ ràng, dễ thao tác.
- **Lưu và tải trò chơi**: Lưu trạng thái trò chơi để tiếp tục chơi sau.
- **Đầu hàng và chơi lại**: Cho phép người chơi đầu hàng hoặc bắt đầu ván mới.
- **Hiệu ứng âm thanh**: Tăng trải nghiệm với âm thanh khi bắt đầu, kết thúc, hoặc di chuyển quân cờ.

## Yêu cầu hệ thống
- **Hệ điều hành**: Windows, macOS, hoặc Linux.
- **Java**: Java Development Kit (JDK) phiên bản 22.0.1 hoặc cao hơn.
- **RAM**: Tối thiểu 512MB.
- **Dung lượng đĩa**: Khoảng 200MB cho mã nguồn và tài nguyên.

## Hướng dẫn cài đặt

### 1. Tải mã nguồn
Clone repository về máy của bạn bằng lệnh sau:

```bash
git clone https://github.com/Mr-1504/Chinese-Chess.git
cd Chinese-Chess
```

Hoặc tải file ZIP từ GitHub và giải nén.

### 2. Cài đặt Java
Đảm bảo rằng bạn đã cài đặt JDK 8 hoặc cao hơn. Kiểm tra phiên bản Java bằng lệnh:

```bash
java --version
```

Nếu chưa cài đặt, tải và cài đặt JDK từ [trang chính thức của Oracle](https://www.oracle.com/java/technologies/javase-downloads.html) hoặc sử dụng OpenJDK.

### 3. Biên dịch và chạy
Dự án sử dụng các thư viện Java Swing để xây dựng giao diện. Không cần cài đặt thư viện bổ sung vì Swing được tích hợp sẵn trong JDK.

1. **Biên dịch mã nguồn**:
   Trong thư mục dự án, chạy lệnh sau để biên dịch:

   ```bash
   javac -d bin src/**/*.java
   ```

   Lệnh này sẽ biên dịch tất cả các file Java trong thư mục `src` và lưu các file `.class` vào thư mục `bin`.

2. **Chạy trò chơi**:
   Sau khi biên dịch, chạy chương trình bằng lệnh:

   ```bash
   java -cp bin view.Home
   ```

   Lớp `Home` là điểm bắt đầu của ứng dụng, hiển thị menu chính của trò chơi.

### 4. (Tùy chọn) Sử dụng IDE
Bạn có thể nhập dự án vào IDE như IntelliJ IDEA, Eclipse, hoặc NetBeans để biên dịch và chạy dễ dàng hơn:
- Mở IDE và nhập thư mục dự án.
- Thiết lập JDK trong IDE.
- Chạy lớp `view.Home` để khởi động trò chơi.

## Hướng dẫn chơi

### 1. Bắt đầu trò chơi
- Từ menu chính (`Home`), chọn chế độ chơi:
  - **Chơi với máy**: Chơi với AI đơn giản.
  - **Chơi với người**: Hai người chơi trên cùng một máy, luân phiên di chuyển.
- Nhấn nút "Bắt đầu" để vào bàn cờ.

### 2. Luật chơi cơ bản
Cờ Tướng (Xiangqi) là trò chơi chiến lược với bàn cờ 9x10, mỗi bên có 16 quân cờ (Tướng, Sĩ, Tượng, Xe, Pháo, Mã, Tốt). Mục tiêu là chiếu bí Tướng của đối phương. Một số luật cơ bản:
- **Tướng**: Chỉ di chuyển trong "cung" (3x3 ô ở giữa cuối bàn cờ), mỗi lần đi 1 ô.
- **Sĩ**: Di chuyển chéo 1 ô trong cung.
- **Tượng**: Di chuyển chéo 2 ô, không qua sông.
- **Xe**: Di chuyển ngang hoặc dọc bất kỳ số ô.
- **Pháo**: Di chuyển như Xe, nhưng cần "nhảy qua" một quân để ăn.
- **Mã**: Di chuyển hình chữ L (2 ô một hướng và 1 ô vuông góc), có thể bị "cản mã".
- **Tốt**: Đi thẳng 1 ô, sau khi qua sông có thể đi ngang.

Chi tiết luật chơi có thể tham khảo tại [Xiangqi Rules](https://en.wikipedia.org/wiki/Xiangqi).

### 3. Thao tác trong trò chơi
- **Di chuyển quân cờ**: Nhấp chuột trái vào quân cờ để chọn, sau đó nhấp vào ô đích để di chuyển. Các nước đi hợp lệ sẽ được tô sáng.
- **Đếm ngược thời gian**: Mỗi người chơi có thời gian giới hạn (mặc định: 10 phút). Thanh đếm ngược hiển thị trên avatar của người chơi.
- **Đầu hàng**: Nhấn nút "Đầu hàng" để kết thúc ván và hiển thị nút "Chơi lại".
- **Chơi lại**: Sau khi ván kết thúc (đầu hàng hoặc chiếu bí), nhấn "Chơi lại" để bắt đầu ván mới.
- **Quay về trang chủ**: Nhấn nút "Trang chủ" để thoát và quay lại menu.

### 4. Lưu và tải trò chơi
- **Lưu trò chơi**: Khi quay về trang chủ trong lúc ván đang diễn ra, trò chơi sẽ tự động được lưu.
- **Tải trò chơi**: Từ menu chính, chọn "Game trước" để tải ván đã lưu.

## Cấu trúc dự án
- **`src/controller`**: Chứa các lớp điều khiển trò chơi (`GameController`, `Notification`).
- **`src/model`**: Chứa các lớp mô hình như `ChessPiece`, `RoundedImageLabel`.
- **`src/view`**: Chứa các lớp giao diện như `Home`, `ChessBoard`.
- **`src/file`**: Chứa lớp `IOFile` để lưu và tải trò chơi.

## Đóng góp
Chúng tôi hoan nghênh mọi đóng góp để cải thiện trò chơi! Để đóng góp:
1. Fork repository này.
2. Tạo một nhánh mới (`git checkout -b feature/ten-tinh-nang`).
3. Thực hiện các thay đổi và commit (`git commit -m 'Thêm tính năng XYZ'`).
4. Push lên nhánh của bạn (`git push origin feature/ten-tinh-nang`).
5. Tạo một Pull Request trên GitHub.

Vui lòng đọc [CONTRIBUTING.md](CONTRIBUTING.md) để biết thêm chi tiết.

## Giấy phép
Dự án được phân phối theo [MIT LICENSE](LICENSE).

## Liên hệ
Nếu bạn có câu hỏi hoặc cần hỗ trợ, hãy mở một issue trên GitHub hoặc liên hệ qua email: [truongvan.minh1504@gmail.com](mailto:truongvan.minh1504@gmail.com).

---

**Tác giả**: Mr-1504  
**Repository**: [https://github.com/Mr-1504/Chinese-Chess](https://github.com/Mr-1504/Chinese-Chess)
