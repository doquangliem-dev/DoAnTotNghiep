# HeThongKiemDinh24

Hệ thống quản lý hoạt động tự đánh giá và kiểm định chất lượng chương trình đào tạo theo Thông tư 04/2025/TT-BGDĐT.

## Công nghệ
- Java 17+
- Spring Boot 3.5.5
- Spring MVC + Thymeleaf
- Spring Data MongoDB
- MongoDB Atlas
- BCrypt cho mật khẩu

## Chạy project
Database: `HeThongKiemDinh`

PowerShell:
```powershell
$env:MONGODB_URI='mongodb+srv://doliem667:roby2000@cluster01.pjmul.mongodb.net/HeThongKiemDinh'
echo $env:MONGODB_URI
mvn spring-boot:run
```

Mở `http://localhost:8080/login`.

Tài khoản Admin mặc định được tạo một lần nếu chưa tồn tại:
```text
Email: admin@huit.edu.vn
Mật khẩu: 123456
```
Sau khi đăng nhập nên đổi mật khẩu trong môi trường sử dụng thật.

## Phân hệ theo vai trò
### 1. Quản trị
Đường dẫn `/admin`.
- Tổng quan.
- Quản lý CTĐT và phiên bản theo năm, mỗi phiên bản hiển thị thành một dòng riêng.
- Upload/thay/xóa file đính kèm phiên bản CTĐT; file được lưu bền vững ngoài source code.
- Quản lý Bộ tiêu chuẩn, Tiêu chuẩn H1-H8 và Tiêu chí.
- Quản lý nhóm nhân sự; vai trò hệ thống tách riêng với chức vụ trong nhóm.
- Phân công Tiêu chuẩn cho Trưởng nhóm.
- Xem/chỉnh sửa minh chứng, không thêm/xóa document minh chứng tại khu vực Admin.
- Xem báo cáo tự đánh giá.
- Quản trị tài khoản, vai trò, khóa/mở khóa; không xóa tài khoản.

### 2. Trưởng nhóm tiêu chí
Đường dẫn:
```text
/truong-nhom
/truong-nhom/phan-cong
/truong-nhom/minh-chung
/truong-nhom/tien-do
```
Chức năng:
- Xem Tiêu chuẩn Admin đã giao.
- Chia các Tiêu chí thuộc Tiêu chuẩn cho Thư ký/Thành viên trong nhóm.
- Thêm minh chứng cho Tiêu chí thuộc Tiêu chuẩn mình phụ trách.
- Duyệt minh chứng của Thành viên: Duyệt -> `Chờ Thư ký duyệt`, hoặc Từ chối.
- Theo dõi tiến độ theo Tiêu chí, số minh chứng và tình trạng báo cáo.

### 3. Thành viên nhóm
Đường dẫn:
```text
/thanh-vien
/thanh-vien/cong-viec
/thanh-vien/minh-chung
/thanh-vien/bao-cao
```
Chức năng:
- Xem Tiêu chí được Trưởng nhóm giao.
- Thêm minh chứng và upload file.
- Minh chứng mới vào trạng thái `Chờ Trưởng nhóm duyệt`.
- Soạn/cập nhật báo cáo tiêu chí theo 4 phần:
  1. Mô tả hiện trạng
  2. Điểm mạnh
  3. Điểm tồn tại
  4. Kế hoạch hành động
- Gửi báo cáo sang trạng thái `Chờ duyệt`.

### 4. Thư ký chương trình
Đường dẫn:
```text
/thu-ky
/thu-ky/ke-hoach
/thu-ky/minh-chung
/thu-ky/tong-hop
```
Chức năng:
- Lập/cập nhật kế hoạch tự đánh giá.
- Gửi kế hoạch sang Bộ phận khảo thí phê duyệt.
- Duyệt minh chứng sau bước Trưởng nhóm: Duyệt -> `Đã duyệt`, hoặc Từ chối.
- Tổng hợp báo cáo, minh chứng và kết quả theo H1-H8.

### 5. Hội đồng tự đánh giá
Đường dẫn:
```text
/hoi-dong
/hoi-dong/minh-chung
/hoi-dong/bao-cao
/hoi-dong/danh-gia
/hoi-dong/tong-hop
```
Chức năng:
- Tổng quan Hội đồng.
- Chỉ xem minh chứng đã hoàn tất quy trình duyệt Trưởng nhóm → Thư ký; Hội đồng không thay đổi trạng thái minh chứng.
- Xem báo cáo tiêu chí và thực hiện `Đã duyệt` / `Yêu cầu chỉnh sửa`; Hội đồng không sửa trực tiếp nội dung/tác giả báo cáo.
- Đánh giá từng Tiêu chí `Đạt` / `Không đạt` sau khi báo cáo đã được duyệt; số lần đánh giá, người đánh giá và thời gian được backend tự ghi nhận.
- Tổng hợp kết quả H1-H8, có thể lọc theo kế hoạch tự đánh giá và In/Lưu PDF.

### 6. Bộ phận khảo thí
Đường dẫn:
```text
/khao-thi
/khao-thi/ke-hoach
/khao-thi/tong-hop
```
Chức năng:
- Xem kế hoạch tự đánh giá.
- Phê duyệt hoặc yêu cầu chỉnh sửa kế hoạch do Thư ký gửi.
- Xem tổng hợp kết quả đánh giá theo H1-H8.

## Vai trò hệ thống và chức vụ trong nhóm
Vai trò hệ thống:
```text
Quản trị
Hội đồng tự đánh giá
Trưởng nhóm tiêu chí
Thành viên nhóm
Thư ký chương trình
Bộ phận khảo thí
```

Chức vụ trong nhóm:
```text
Trưởng nhóm
Thư ký
Thành viên
```
Chỉ tài khoản có vai trò hệ thống `Trưởng nhóm tiêu chí` hoặc `Thành viên nhóm` mới được thêm vào nhóm. Chức vụ `Trưởng nhóm` chỉ gán cho tài khoản có vai trò hệ thống `Trưởng nhóm tiêu chí`.

## Quy trình nghiệp vụ chính
```text
Admin tạo nhóm + thêm nhân sự
        ↓
Admin giao Tiêu chuẩn H1-H8 cho Trưởng nhóm
        ↓
Trưởng nhóm giao Tiêu chí cho Thành viên/Thư ký trong nhóm
        ↓
Thành viên thu thập minh chứng + viết báo cáo tiêu chí
        ↓
Trưởng nhóm duyệt minh chứng
        ↓
Thư ký duyệt minh chứng + tổng hợp
        ↓
Hội đồng duyệt báo cáo + đánh giá Đạt/Không đạt
        ↓
Bộ phận khảo thí phê duyệt kế hoạch và theo dõi kết quả
```

## Lưu trữ file bền vững
Mặc định:
```text
C:\Users\<user>\HeThongKiemDinhStorage\
  ctdt\
  minhchung\
  minh-chung-chung\
```
Có thể đổi vị trí:
```powershell
$env:APP_STORAGE_ROOT='D:\HeThongKiemDinhStorage'
```
MongoDB lưu đường dẫn web `/uploads/...`; file vật lý nằm trong kho trên nên vẫn xem được sau khi tắt/mở lại ứng dụng và giữa các tài khoản có quyền xem.

## Collection chính
```text
baoCaoTieuChi
boTieuChuan
chuongTrinhDaoTao
danhGiaMinhChung
danhGiaTieuChi
keHoachTuDanhGia
lichSuThaoTac
minhChung
minhChungChung
nguoiDung
nhomTieuChi
phanCong
thongBao
tienDo
tieuChi
tieuChuan
vaiTro
```

## Ghi chú bản 17
- Hoàn thiện đủ giao diện và luồng nghiệp vụ cho 6 vai trò.
- Đăng nhập tự chuyển về đúng khu vực theo vai trò.
- Bổ sung phân công Tiêu chí ở cấp Trưởng nhóm.
- Bổ sung thêm minh chứng ở Trưởng nhóm/Thành viên.
- Bổ sung duyệt minh chứng 2 bước Trưởng nhóm -> Thư ký.
- Bổ sung kế hoạch tự đánh giá ở Thư ký và phê duyệt kế hoạch ở Khảo thí.
- Bổ sung báo cáo tự đánh giá của Thành viên theo đúng cấu trúc 4 phần.

## Cập nhật giao diện HeThongKiemDinh24

Ba khu vực nghiệp vụ **Thành viên nhóm**, **Trưởng nhóm tiêu chí** và **Thư ký chương trình** đã được thiết kế lại theo cùng một hệ thống giao diện:

- Dashboard có khối tổng quan, KPI, thao tác nhanh và dữ liệu công việc gần nhất.
- Sidebar, tiêu đề, màu nhận diện và trạng thái được đồng bộ theo từng vai trò.
- Bảng dữ liệu có tìm kiếm/lọc, trạng thái trực quan và empty-state rõ ràng.
- Trưởng nhóm có giao diện phân công, duyệt minh chứng bằng modal và theo dõi tiến độ trực quan.
- Thành viên có giao diện nộp minh chứng, quản lý công việc và soạn báo cáo 4 phần dễ sử dụng.
- Thư ký có giao diện kế hoạch, duyệt minh chứng bằng modal và bảng tổng hợp H1-H8 có thanh tiến độ.

Ngoài giao diện, bản HeThongKiemDinh24 đã siết lại phân quyền API và workflow để tránh người dùng gọi trực tiếp endpoint ngoài quyền được giao.


## Cập nhật HeThongKiemDinh24

- Trưởng nhóm có thể chọn **nhiều tiêu chí cùng lúc** để giao cho một Thành viên/Thư ký.
- Giao diện dùng checkbox, có nút chọn tất cả tiêu chí đang hiển thị và bỏ chọn; không cần giữ Ctrl.
- Một thành viên có thể có nhiều bản ghi phân công, mỗi bản ghi ứng với một tiêu chí.
- Một tiêu chí vẫn chỉ có một phân công đang hoạt động trong cùng nhóm để tránh trùng trách nhiệm.
- Backend kiểm tra toàn bộ danh sách trước khi lưu để hạn chế phân công dở dang khi có tiêu chí không hợp lệ hoặc đã được giao.

## Cập nhật HeThongKiemDinh24

### Nhân sự hỗ trợ trong Nhóm tiêu chí
- Chức vụ trong nhóm gồm: **Trưởng nhóm, Thư ký, Thành viên, Nhân sự hỗ trợ**.
- Đây là **chức vụ trong nhóm**, không phải vai trò hệ thống mới.
- Danh sách tạo nhóm vẫn chỉ lấy người có vai trò hệ thống **Trưởng nhóm tiêu chí** hoặc **Thành viên nhóm**.

### Phân công tiêu chí
Trưởng nhóm chọn:
1. Nhóm
2. Người thực hiện chính (Thư ký hoặc Thành viên)
3. Có sử dụng nhân sự hỗ trợ hay không
4. Nếu Có, chọn một **Nhân sự hỗ trợ** bằng combobox riêng
5. Chọn một hoặc nhiều tiêu chí

Mỗi bản ghi `phanCong` có thêm trường:
```json
"maNhanSuHoTro": "NDxxx"
```

Nhân sự hỗ trợ có thể nhìn thấy công việc được hỗ trợ và nộp minh chứng cho tiêu chí, nhưng người viết báo cáo tự đánh giá vẫn là người được phân công chính.

## Sửa lỗi HeThongKiemDinh24

Đã sửa lỗi biên dịch tại `HeThongService.java` khi sử dụng:

```java
LocalDate start = ...
LocalDate end = ...
```

Nguyên nhân bản trước thiếu:

```java
import java.time.LocalDate;
```

Bản 21 đã bổ sung import này và giữ nguyên toàn bộ chức năng Nhân sự hỗ trợ.

## Cập nhật HeThongKiemDinh24

- Tiêu chí đã có phân công đang hoạt động trong nhóm sẽ hiển thị mờ và bị khóa checkbox trên màn hình Trưởng nhóm.
- Một tiêu chí có thể có nhiều Nhân sự hỗ trợ (`maNhanSuHoTroList`).
- Admin vẫn có thể gán chức vụ Nhân sự hỗ trợ khi tạo/sửa nhóm, nhưng bảng Quản lý nhóm chỉ hiển thị Trưởng nhóm và Thư ký.
- Khi Trưởng nhóm chọn nhiều tiêu chí và giao trong một lần, MongoDB vẫn lưu từng tiêu chí thành bản ghi riêng để giữ nghiệp vụ hiện có, nhưng tất cả có chung `maDotPhanCong`. Giao diện Phân công gom chúng thành một dòng.
- Dữ liệu cũ `maNhanSuHoTro` vẫn được đọc để tương thích với các document đã có.

## Sửa toàn diện luồng Thành viên - Hội đồng trong HeThongKiemDinh24

- Các API ghi dữ liệu quản trị dùng chung (`CTĐT`, bộ tiêu chuẩn, tiêu chuẩn/tiêu chí, người dùng, nhóm, phân công, minh chứng chung...) chỉ cho phép **Quản trị**.
- Thành viên và Trưởng nhóm upload minh chứng qua API theo vai trò; backend kiểm tra người tạo, phạm vi phân công và trạng thái trước khi thay file.
- Trạng thái minh chứng do backend quản lý, không cho Thành viên tự gửi trạng thái phê duyệt.
- Trưởng nhóm chỉ duyệt minh chứng ở `Chờ Trưởng nhóm duyệt`; Thư ký chỉ duyệt ở `Chờ Thư ký duyệt`; cả hai bước yêu cầu có file đính kèm.
- Hội đồng chỉ xem minh chứng `Đã duyệt`, không còn chức năng thêm/duyệt minh chứng để tránh bỏ qua workflow.
- Thành viên chỉ sửa báo cáo do chính mình viết; báo cáo `Chờ duyệt` và `Đã duyệt` bị khóa. Báo cáo `Yêu cầu chỉnh sửa` được mở lại cho tác giả cập nhật và gửi lại.
- Hội đồng chỉ duyệt hoặc yêu cầu chỉnh sửa báo cáo đang `Chờ duyệt`, không sửa trực tiếp báo cáo nên không làm thay đổi `maNguoiViet`.
- Đánh giá tiêu chí chỉ được tạo khi có báo cáo `Đã duyệt`; `lanDanhGia`, `maNguoiDanhGia`, `ngayDanhGia` do server tự sinh/ghi nhận.
- `MinhChung` và `BaoCaoTieuChi` bổ sung `maKeHoach`; phân công từ Trưởng nhóm kế thừa `maKeHoach` của phân công Tiêu chuẩn. Tổng hợp Hội đồng hỗ trợ lọc theo kế hoạch.
- Tỷ lệ `tyLeHoanThanh` của Hội đồng chỉ tính **báo cáo đã duyệt**, đồng thời vẫn trả `tyLeCoBaoCao` để phân biệt báo cáo đã có với báo cáo đã hoàn thành.
