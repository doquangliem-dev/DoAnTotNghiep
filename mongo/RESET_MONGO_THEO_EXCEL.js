use HeThongKiemDinh

db.dropDatabase()

const collections = [
  "vaiTro","nguoiDung","boTieuChuan","tieuChuan","tieuChi",
  "chuongTrinhDaoTao","keHoachTuDanhGia","nhomTieuChi","phanCong",
  "minhChung","minhChungChung","danhGiaMinhChung","baoCaoTieuChi",
  "danhGiaTieuChi","baoCaoNhom","baoCaoPhan","phanCongBaoCao",
  "baoCaoTuDanhGia","tienDo","thongBao","lichSuThaoTac"
];

collections.forEach(name => db.createCollection(name));

const roles = [
  ["VT001","Quản trị","Quản lý nhân sự, CTĐT, nhóm và phân công"],
  ["VT002","Hội đồng tự đánh giá","Góp ý, duyệt báo cáo và đánh giá tiêu chí"],
  ["VT003","Trưởng nhóm tiêu chí","Điều hành nhóm, phân công tiêu chí và duyệt cấp nhóm"],
  ["VT004","Thành viên nhóm","Thu thập minh chứng và viết báo cáo tiêu chí; có thể là Thư ký nhóm"],
  ["VT005","Thư ký chương trình","Lập kế hoạch, quản lý minh chứng và tổng hợp hồ sơ"],
  ["VT006","Trung tâm Quản lý chất lượng","Phê duyệt kế hoạch, giám sát tiến độ, báo cáo và minh chứng tự đánh giá"],
  ["VT007","Nhân sự phụ trách báo cáo","Nhân sự ngoài nhóm phụ trách Phần 1, Phần 3 hoặc Phần 4"]
];

db.vaiTro.insertMany(roles.map(r => ({_id:r[0], tenVaiTro:r[1], moTa:r[2]})));

const users = [
  {_id:"ND001",hoTen:"Quản trị hệ thống",email:"quan.tri.he.thong@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Trường Đại học Công Thương TP.HCM",trinhDo:"Chưa cập nhật",chucVu:"Quản trị hệ thống",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT001"}]},
  {_id:"ND002",hoTen:"Hội đồng tự đánh giá",email:"hoi.dong.tu.danh.gia@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Hội đồng tự đánh giá",trinhDo:"Chưa cập nhật",chucVu:"Tài khoản nghiệp vụ Hội đồng",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT002"}]},
  {_id:"ND003",hoTen:"Trung tâm Quản lý chất lượng",email:"trung.tam.quan.ly.chat.luong@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Trung tâm Quản lý chất lượng",trinhDo:"Chưa cập nhật",chucVu:"Tài khoản nghiệp vụ TT QLCL",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT006"}]},
  {_id:"ND004",hoTen:"Nguyễn Hồng Vũ",email:"nguyen.hong.vu@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Ban Thư ký",trinhDo:"Chưa cập nhật",chucVu:"Trưởng Ban Thư ký",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT005"},{maVaiTro:"VT007"}]},
  {_id:"ND005",hoTen:"Trần Thị Vân Anh",email:"tran.thi.van.anh@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Ban Thư ký",trinhDo:"Chưa cập nhật",chucVu:"Thư ký tổng hợp",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT005"},{maVaiTro:"VT007"}]},
  {_id:"ND010",hoTen:"Phùng Thế Bảo",email:"phung.the.bao@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Trưởng bộ môn",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT003"},{maVaiTro:"VT004"}]},
  {_id:"ND011",hoTen:"Nguyễn Thị Huyền Trang",email:"nguyen.thi.huyen.trang@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND012",hoTen:"Nguyễn Văn Lễ",email:"nguyen.van.le@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Phó Trưởng bộ môn",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND013",hoTen:"Đinh Thị Mận",email:"dinh.thi.man@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND014",hoTen:"Bùi Công Danh",email:"bui.cong.danh@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND015",hoTen:"Huỳnh Thị Châu Lan",email:"huynh.thi.chau.lan@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng môn",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND016",hoTen:"Bùi Đức Nam",email:"bui.duc.nam@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND017",hoTen:"Vũ Đức Thịnh",email:"vu.duc.thinh@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Trưởng bộ môn",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT003"},{maVaiTro:"VT004"}]},
  {_id:"ND018",hoTen:"Đinh Thị Tâm",email:"dinh.thi.tam@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND019",hoTen:"Phạm Văn Đăng",email:"pham.van.dang@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Phó Trưởng khoa",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND020",hoTen:"Lâm Thị Họa Mi",email:"lam.thi.hoa.mi@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND021",hoTen:"Đoàn Thị Như Quỳnh",email:"doan.thi.nhu.quynh@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND022",hoTen:"Vũ Văn Vinh",email:"vu.van.vinh@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Phó Trưởng bộ môn",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND023",hoTen:"Đinh Nguyễn Trọng Nghĩa",email:"dinh.nguyen.trong.nghia@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND024",hoTen:"Nguyễn Thị Định",email:"nguyen.thi.dinh@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT003"},{maVaiTro:"VT004"}]},
  {_id:"ND025",hoTen:"Nguyễn Thị Thanh Thủy",email:"nguyen.thi.thanh.thuy@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND026",hoTen:"Nguyễn Hữu Khánh",email:"nguyen.huu.khanh@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND027",hoTen:"Huỳnh Thị Cẩm Dung",email:"huynh.thi.cam.dung@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND028",hoTen:"Lê Thị Thùy Lan",email:"le.thi.thuy.lan@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND029",hoTen:"Trần Đình Toàn",email:"tran.dinh.toan@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT003"},{maVaiTro:"VT004"}]},
  {_id:"ND030",hoTen:"Nguyễn Hải Yến",email:"nguyen.hai.yen@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND031",hoTen:"Trần Thanh Trâm",email:"tran.thanh.tram@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND032",hoTen:"Trần Đắc Tốt",email:"tran.dac.tot@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND033",hoTen:"Lữ Thị Cẩm Tú",email:"lu.thi.cam.tu@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND034",hoTen:"Dương Bảo Ninh",email:"duong.bao.ninh@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND035",hoTen:"Mạnh Thiên Lý",email:"manh.thien.ly@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND036",hoTen:"Ngô Dương Hà",email:"ngo.duong.ha@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Tiến sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT003"},{maVaiTro:"VT004"}]},
  {_id:"ND037",hoTen:"Nguyễn Thị Thùy Trang",email:"nguyen.thi.thuy.trang@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND038",hoTen:"Trần Văn Thọ",email:"tran.van.tho@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND039",hoTen:"Phan Thị Ngọc Mai",email:"phan.thi.ngoc.mai@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND040",hoTen:"Trần Như Ý",email:"tran.nhu.y@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND041",hoTen:"Nguyễn Thị Thu Tâm",email:"nguyen.thi.thu.tam@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Thạc sĩ",chucVu:"Giảng viên",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND042",hoTen:"Hồ Hải Quân",email:"ho.hai.quan@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND043",hoTen:"Dương Thị Mộng Thuỳ",email:"duong.thi.mong.thuy@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND044",hoTen:"Trần Việt Hùng",email:"tran.viet.hung@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND045",hoTen:"Huỳnh Thanh Tâm",email:"huynh.thanh.tam@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND046",hoTen:"Ngô Quốc Huy",email:"ngo.quoc.huy@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND047",hoTen:"Đinh Huy Hoàng",email:"dinh.huy.hoang@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND048",hoTen:"Nguyễn Phương Hạc",email:"nguyen.phuong.hac@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND049",hoTen:"Đỗ Minh Tiến",email:"do.minh.tien@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND050",hoTen:"Nguyễn Thế Hữu",email:"nguyen.the.huu@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND051",hoTen:"Lê Anh Tuấn",email:"le.anh.tuan@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự hỗ trợ",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"}]},
  {_id:"ND052",hoTen:"Trần Thị Bích Vân",email:"tran.thi.bich.van@huit.edu.vn",matKhau:"123456",soDienThoai:"",khoa:"Khoa Công nghệ Thông tin",trinhDo:"Chưa cập nhật",chucVu:"Nhân sự CSDL kiểm định",trangThai:"Hoạt động",phanQuyen:[{maVaiTro:"VT004"},{maVaiTro:"VT007"}]}
];

db.nguoiDung.insertMany(users);

db.boTieuChuan.insertOne({
  _id:"MOET",
  maBoTieuChuan:"MOET",
  tenBoTieuChuan:"Bộ tiêu chuẩn MOET đánh giá chất lượng chương trình đào tạo",
  tenVietTat:"MOET",
  tongSoTieuChuan:8,
  tongSoTieuChi:52,
  trangThai:"Đang áp dụng",
  moTa:"Bộ tiêu chuẩn MOET sử dụng trong hệ thống gồm 8 tiêu chuẩn và 52 tiêu chí."
});

const standards = [
  [
    "TC1",
    "1",
    "Mục tiêu và chuẩn đầu ra của chương trình đào tạo"
  ],
  [
    "TC2",
    "2",
    "Cấu trúc và nội dung chương trình đào tạo"
  ],
  [
    "TC3",
    "3",
    "Hoạt động dạy và học"
  ],
  [
    "TC4",
    "4",
    "Đánh giá kết quả học tập"
  ],
  [
    "TC5",
    "5",
    "Đội ngũ giảng viên, nghiên cứu viên"
  ],
  [
    "TC6",
    "6",
    "Các dịch vụ hỗ trợ người học"
  ],
  [
    "TC7",
    "7",
    "Cơ sở hạ tầng, cơ sở vật chất và trang thiết bị"
  ],
  [
    "TC8",
    "8",
    "Đầu ra và kết quả đầu ra"
  ]
];
db.tieuChuan.insertMany(standards.map(r => ({
  _id:r[0],
  maBoTieuChuan:"MOET",
  soTieuChuan:Number(r[1]),
  tenTieuChuan:r[2],
  moTa:`Tiêu chuẩn ${r[1]} thuộc bộ MOET`,
  thuTu:Number(r[1]),
  trangThai:"Đang áp dụng"
})));

const criteria = [
  [
    "TC1",
    "1",
    "Mục tiêu của chương trình đào tạo được xác định rõ ràng, phù hợp và gắn kết với sứ mạng, tầm nhìn, chiến lược phát triển của cơ sở đào tạo; phù hợp với mục tiêu của giáo dục đại học.",
    "Không"
  ],
  [
    "TC1",
    "2",
    "Chuẩn đầu ra của chương trình đào tạo được xây dựng rõ ràng; phù hợp với sứ mạng, tầm nhìn và mục tiêu chiến lược của cơ sở đào tạo và được phổ biến đến các bên liên quan.",
    "Không"
  ],
  [
    "TC1",
    "3",
    "Chuẩn đầu ra của chương trình đào tạo phù hợp với Khung trình độ quốc gia Việt Nam và chuẩn chương trình đào tạo nhóm ngành.",
    "Có"
  ],
  [
    "TC1",
    "4",
    "Chuẩn đầu ra của tất cả các học phần được xây dựng phù hợp và tương thích với chuẩn đầu ra của chương trình đào tạo đã công bố.",
    "Không"
  ],
  [
    "TC1",
    "5",
    "Chuẩn đầu ra của chương trình đào tạo phản ánh rõ yêu cầu của các bên liên quan, đặc biệt là các bên liên quan bên ngoài.",
    "Không"
  ],
  [
    "TC1",
    "6",
    "Chuẩn đầu ra của chương trình đào tạo được đo lường, đánh giá tại thời điểm người học tốt nghiệp.",
    "Có"
  ],
  [
    "TC2",
    "1",
    "Bản mô tả chương trình đào tạo và đề cương các học phần có đủ thông tin, được cập nhật, phê duyệt và công bố công khai.",
    "Không"
  ],
  [
    "TC2",
    "2",
    "Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển để bảo đảm người học đạt chuẩn đầu ra và có khối lượng học tập phù hợp.",
    "Có"
  ],
  [
    "TC2",
    "3",
    "Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển dựa trên phản hồi và nhu cầu của các bên liên quan.",
    "Không"
  ],
  [
    "TC2",
    "4",
    "Đóng góp của từng học phần trong việc đạt được chuẩn đầu ra của chương trình đào tạo là rõ ràng.",
    "Có"
  ],
  [
    "TC2",
    "5",
    "Chương trình đào tạo có cấu trúc logic, trình tự hợp lý, có tính linh hoạt và tích hợp.",
    "Không"
  ],
  [
    "TC2",
    "6",
    "Cấu trúc và nội dung chương trình đào tạo thể hiện rõ các học phần bắt buộc, tự chọn, lý thuyết, thực hành, trải nghiệm và nghiên cứu khoa học.",
    "Không"
  ],
  [
    "TC2",
    "7",
    "Cấu trúc và nội dung chương trình đào tạo được rà soát, đánh giá và cải tiến theo quy trình, bảo đảm tính cập nhật.",
    "Không"
  ],
  [
    "TC3",
    "1",
    "Triết lý giáo dục của cơ sở đào tạo được tuyên bố rõ ràng, phổ biến đến các bên liên quan và truyền tải vào hoạt động dạy và học.",
    "Không"
  ],
  [
    "TC3",
    "2",
    "Hoạt động dạy và học được thiết kế tương thích với chuẩn đầu ra của chương trình đào tạo.",
    "Có"
  ],
  [
    "TC3",
    "3",
    "Hoạt động dạy và học thể hiện việc học tập chủ động và phát triển khả năng học tập suốt đời của người học.",
    "Không"
  ],
  [
    "TC3",
    "4",
    "Hoạt động dạy và học thúc đẩy người học đưa ra ý tưởng mới, sáng kiến, đổi mới sáng tạo và tinh thần khởi nghiệp.",
    "Không"
  ],
  [
    "TC3",
    "5",
    "Quá trình dạy và học được cải tiến thường xuyên để đáp ứng yêu cầu của thị trường lao động và chuẩn đầu ra.",
    "Không"
  ],
  [
    "TC4",
    "1",
    "Các phương pháp đánh giá kết quả học tập của người học đa dạng, tương thích với chuẩn đầu ra của chương trình đào tạo.",
    "Không"
  ],
  [
    "TC4",
    "2",
    "Có các quy định rõ ràng về đánh giá kết quả học tập, quy trình phúc khảo, được phổ biến và triển khai nhất quán.",
    "Không"
  ],
  [
    "TC4",
    "3",
    "Các tiêu chuẩn và quy trình đánh giá kết quả học tập, công nhận kết quả học tập và xét tốt nghiệp được phổ biến, triển khai nhất quán.",
    "Không"
  ],
  [
    "TC4",
    "4",
    "Các phương pháp đánh giá được thể hiện qua ma trận, tiêu chí, mức độ đánh giá, đáp án, thang điểm và kế hoạch đánh giá.",
    "Không"
  ],
  [
    "TC4",
    "5",
    "Các phương pháp đánh giá bảo đảm đo lường được mức độ đạt chuẩn đầu ra học phần và chuẩn đầu ra chương trình đào tạo.",
    "Có"
  ],
  [
    "TC4",
    "6",
    "Kết quả đánh giá được phản hồi kịp thời cho người học để cải thiện việc học tập.",
    "Không"
  ],
  [
    "TC4",
    "7",
    "Việc đánh giá kết quả học tập được định kỳ rà soát và cải tiến để bảo đảm đo lường chuẩn đầu ra.",
    "Không"
  ],
  [
    "TC5",
    "1",
    "Kế hoạch phát triển đội ngũ giảng viên, nghiên cứu viên được thực hiện nhằm bảo đảm số lượng và chất lượng.",
    "Không"
  ],
  [
    "TC5",
    "2",
    "Số lượng và chất lượng đội ngũ giảng viên, nghiên cứu viên đáp ứng yêu cầu thực hiện chương trình đào tạo; khối lượng công việc được đo lường và giám sát.",
    "Có"
  ],
  [
    "TC5",
    "3",
    "Năng lực của đội ngũ giảng viên, nghiên cứu viên được xác định, đánh giá và thông tin tới các bên liên quan trực tiếp.",
    "Không"
  ],
  [
    "TC5",
    "4",
    "Giảng viên, nghiên cứu viên được phân công nhiệm vụ phù hợp với trình độ, năng lực và kinh nghiệm.",
    "Không"
  ],
  [
    "TC5",
    "5",
    "Việc bổ nhiệm, thăng tiến dựa trên hệ thống đánh giá năng lực và kết quả thực hiện nhiệm vụ.",
    "Không"
  ],
  [
    "TC5",
    "6",
    "Trách nhiệm, quyền hạn và nghĩa vụ của giảng viên, nghiên cứu viên được xác định rõ ràng và phổ biến.",
    "Không"
  ],
  [
    "TC5",
    "7",
    "Nhu cầu đào tạo, bồi dưỡng và phát triển chuyên môn được xác định có hệ thống và được triển khai đáp ứng nhu cầu.",
    "Không"
  ],
  [
    "TC5",
    "8",
    "Công tác quản lý để đánh giá chất lượng giảng dạy, nghiên cứu khoa học và phục vụ cộng đồng được triển khai theo quy định.",
    "Không"
  ],
  [
    "TC6",
    "1",
    "Chính sách, tiêu chí và quy trình tuyển sinh được xác định rõ ràng, công bố công khai và cập nhật.",
    "Có"
  ],
  [
    "TC6",
    "2",
    "Năng lực đội ngũ cung cấp dịch vụ hỗ trợ được xác định rõ ràng và được đánh giá để bảo đảm phù hợp nhu cầu các bên liên quan.",
    "Không"
  ],
  [
    "TC6",
    "3",
    "Kế hoạch ngắn hạn và dài hạn đối với dịch vụ hỗ trợ người học được xây dựng và triển khai thực hiện.",
    "Không"
  ],
  [
    "TC6",
    "4",
    "Có hệ thống quản lý đào tạo phù hợp để giám sát, ghi nhận tiến độ, kết quả và khối lượng học tập của người học.",
    "Không"
  ],
  [
    "TC6",
    "5",
    "Có các hoạt động tư vấn học tập, ngoại khóa, cuộc thi và dịch vụ hỗ trợ giúp cải thiện việc học và khả năng có việc làm.",
    "Không"
  ],
  [
    "TC6",
    "6",
    "Các dịch vụ hỗ trợ người học được định kỳ đánh giá, đối sánh và cải tiến chất lượng.",
    "Không"
  ],
  [
    "TC7",
    "1",
    "Có hệ thống phòng làm việc, phòng học và các phòng chức năng với trang thiết bị phù hợp để triển khai chương trình đào tạo.",
    "Không"
  ],
  [
    "TC7",
    "2",
    "Các phòng thí nghiệm, phòng thực hành và trang thiết bị đầy đủ, cập nhật và được sử dụng hiệu quả.",
    "Không"
  ],
  [
    "TC7",
    "3",
    "Có thư viện, thư viện số và nguồn học liệu được cập nhật đáp ứng nhu cầu đào tạo, nghiên cứu khoa học.",
    "Có"
  ],
  [
    "TC7",
    "4",
    "Có hệ thống công nghệ thông tin, hạ tầng mạng và máy tính dễ tiếp cận, đáp ứng nhu cầu giảng dạy, nghiên cứu và quản lý.",
    "Không"
  ],
  [
    "TC7",
    "5",
    "Môi trường tâm lý, xã hội và cảnh quan thiên nhiên tạo thuận lợi cho đào tạo, nghiên cứu và sự thoải mái của người học.",
    "Không"
  ],
  [
    "TC7",
    "6",
    "Các tiêu chuẩn về môi trường, sức khỏe, an toàn được xác định và triển khai phù hợp.",
    "Không"
  ],
  [
    "TC7",
    "7",
    "Năng lực đội ngũ hỗ trợ về cơ sở vật chất và trang thiết bị được xác định và đánh giá đáp ứng nhu cầu.",
    "Không"
  ],
  [
    "TC7",
    "8",
    "Chất lượng cơ sở vật chất phục vụ chương trình đào tạo được định kỳ đánh giá và cải tiến.",
    "Không"
  ],
  [
    "TC8",
    "1",
    "Tỉ lệ tốt nghiệp, thôi học và thời gian tốt nghiệp trung bình của người học được xác lập, giám sát, đối sánh để cải tiến chất lượng.",
    "Không"
  ],
  [
    "TC8",
    "2",
    "Tỉ lệ việc làm, tự tạo việc làm, khởi nghiệp và học tập nâng cao trình độ của người học tốt nghiệp được xác lập, giám sát và đối sánh.",
    "Có"
  ],
  [
    "TC8",
    "3",
    "Hoạt động nghiên cứu khoa học và sản phẩm sáng tạo của người học, giảng viên, nghiên cứu viên được xác lập, giám sát và đối sánh.",
    "Không"
  ],
  [
    "TC8",
    "4",
    "Dữ liệu về mức độ người học đạt chuẩn đầu ra của chương trình đào tạo được xác lập và giám sát để cải tiến chất lượng.",
    "Không"
  ],
  [
    "TC8",
    "5",
    "Mức độ hài lòng của các bên liên quan được xác lập, giám sát, đối sánh để cải tiến chất lượng.",
    "Không"
  ]
];
db.tieuChi.insertMany(criteria.map((r,i) => {
  const id = `${r[0]}.${r[1]}`;
  const d = new Date(Date.UTC(2026,8,1,8,0+i,0));
  return {
    _id:id,
    maTieuChuan:r[0],
    soTieuChi:Number(r[1]),
    tenTieuChi:r[2],
    moTa:`Tiêu chí ${id} thuộc bộ MOET`,
    tieuChiDieuKien:r[3],
    thuTu:Number(r[1]),
    chiBao:[],
    minhChungGoiY:[],
    maNguoiTao:"ND001",
    ngayTao:d,
    ngayCapNhat:d
  };
}));

db.chuongTrinhDaoTao.insertOne({
  _id:"CTDT001",
  tenCTDT:"Chương trình đào tạo đại học ngành Công nghệ thông tin",
  maNganh:"7480201",
  trinhDoDaoTao:"Đại học",
  loaiHinhDaoTao:"Chính quy",
  thoiGianDaoTao:"4 năm",
  moTa:"Chương trình đào tạo trình độ đại học ngành Công nghệ thông tin.",
  trangThai:"Đang áp dụng",
  namBatDau:2022,
  maNguoiTao:"ND001",
  phienBan:[
    {
      maPhienBan:"PB2022",namHoc:"2022",namPhienBan:2022,soPhienBan:1,
      tenPhienBan:"Phiên bản CTĐT năm 2022",noiDungCapNhat:"Phiên bản ban đầu",
      ngayBanHanh:ISODate("2022-08-15T00:00:00.000Z"),trangThai:"Đã ban hành",
      maNguoiCapNhat:"ND001",chuanDauRa:[]
    },
    {
      maPhienBan:"PB2024",namHoc:"2024",namPhienBan:2024,soPhienBan:2,
      tenPhienBan:"Phiên bản CTĐT năm 2024",noiDungCapNhat:"Rà soát, cập nhật CTĐT năm 2024",
      ngayBanHanh:ISODate("2024-08-15T00:00:00.000Z"),trangThai:"Đã ban hành",
      maNguoiCapNhat:"ND001",chuanDauRa:[]
    },
    {
      maPhienBan:"PB2026",namHoc:"2026",namPhienBan:2026,soPhienBan:3,
      tenPhienBan:"Phiên bản CTĐT năm 2026",noiDungCapNhat:"Rà soát, cập nhật CTĐT năm 2026",
      ngayBanHanh:ISODate("2026-08-15T00:00:00.000Z"),trangThai:"Đang áp dụng",
      maNguoiCapNhat:"ND001",
      chuanDauRa:[
        {maPLO:"PLO1",tenPLO:"Kiến thức chuyên môn",moTaPLO:"Vận dụng kiến thức chuyên môn để giải quyết vấn đề.",loaiPLO:"Kiến thức",capDo:4,thuTu:1},
        {maPLO:"PLO2",tenPLO:"Kỹ năng nghề nghiệp",moTaPLO:"Phân tích, thiết kế và triển khai giải pháp công nghệ thông tin.",loaiPLO:"Kỹ năng",capDo:4,thuTu:2},
        {maPLO:"PLO3",tenPLO:"Giao tiếp và làm việc nhóm",moTaPLO:"Giao tiếp và phối hợp hiệu quả trong môi trường nghề nghiệp.",loaiPLO:"Kỹ năng",capDo:4,thuTu:3},
        {maPLO:"PLO4",tenPLO:"Tự chủ và trách nhiệm",moTaPLO:"Có khả năng tự học, thích ứng và chịu trách nhiệm nghề nghiệp.",loaiPLO:"Mức tự chủ và trách nhiệm",capDo:4,thuTu:4}
      ]
    }
  ],
  ngayTao:ISODate("2026-09-01T08:00:00.000Z"),
  ngayCapNhat:ISODate("2026-09-01T08:00:00.000Z")
});

db.keHoachTuDanhGia.insertOne({
  _id:"KH001",
  tenKeHoach:"Kế hoạch tự đánh giá CTĐT ngành Công nghệ thông tin năm 2026",
  maCTDT:"CTDT001",
  maPhienBanCTDT:"PB2026",
  maBoTieuChuan:"MOET",
  ngayBatDau:ISODate("2026-09-01T00:00:00.000Z"),
  ngayKetThuc:ISODate("2026-12-31T00:00:00.000Z"),
  trangThai:"Đã phê duyệt",
  quyTrinhDuyet:[{
    capDuyet:"Trung tâm Quản lý chất lượng",
    maNguoiDuyet:"ND003",
    ketQua:"Duyệt",
    nhanXet:"Kế hoạch đáp ứng yêu cầu triển khai tự đánh giá.",
    ngayDuyet:ISODate("2026-09-02T08:30:00.000Z")
  }]
});

const groups = [
  {_id:"NT001",tenNhom:"Nhóm công tác 1",loaiNhom:"NHOM_CHINH",maTruongNhom:"ND010",maThuKyNhom:"ND011",tieuChuanPhuTrach:["TC1","TC2"],thanhVien:["ND010","ND011","ND012","ND013","ND014","ND015","ND016","ND042","ND043","ND044"],thanhVienChiTiet:[{maNguoiDung:"ND010",chucVu:"Trưởng nhóm"},{maNguoiDung:"ND011",chucVu:"Thư ký nhóm"},{maNguoiDung:"ND012",chucVu:"Thành viên"},{maNguoiDung:"ND013",chucVu:"Thành viên"},{maNguoiDung:"ND014",chucVu:"Thành viên"},{maNguoiDung:"ND015",chucVu:"Thành viên"},{maNguoiDung:"ND016",chucVu:"Thành viên"},{maNguoiDung:"ND042",chucVu:"Nhân sự hỗ trợ"},{maNguoiDung:"ND043",chucVu:"Nhân sự hỗ trợ"},{maNguoiDung:"ND044",chucVu:"Nhân sự hỗ trợ"}],tieuChi:["TC1.1","TC1.2","TC1.3","TC1.4","TC1.5","TC1.6","TC2.1","TC2.2","TC2.3","TC2.4","TC2.5","TC2.6","TC2.7"],trangThai:"Hoạt động"},
  {_id:"NT002",tenNhom:"Nhóm công tác 2",loaiNhom:"NHOM_CHINH",maTruongNhom:"ND017",maThuKyNhom:"ND018",tieuChuanPhuTrach:["TC3","TC4"],thanhVien:["ND017","ND018","ND019","ND020","ND021","ND022","ND023","ND045","ND046"],thanhVienChiTiet:[{maNguoiDung:"ND017",chucVu:"Trưởng nhóm"},{maNguoiDung:"ND018",chucVu:"Thư ký nhóm"},{maNguoiDung:"ND019",chucVu:"Thành viên"},{maNguoiDung:"ND020",chucVu:"Thành viên"},{maNguoiDung:"ND021",chucVu:"Thành viên"},{maNguoiDung:"ND022",chucVu:"Thành viên"},{maNguoiDung:"ND023",chucVu:"Thành viên"},{maNguoiDung:"ND045",chucVu:"Nhân sự hỗ trợ"},{maNguoiDung:"ND046",chucVu:"Nhân sự hỗ trợ"}],tieuChi:["TC3.1","TC3.2","TC3.3","TC3.4","TC3.5","TC4.1","TC4.2","TC4.3","TC4.4","TC4.5","TC4.6","TC4.7"],trangThai:"Hoạt động"},
  {_id:"NT003",tenNhom:"Nhóm công tác 3",loaiNhom:"NHOM_CHINH",maTruongNhom:"ND024",maThuKyNhom:"ND025",tieuChuanPhuTrach:["TC5"],thanhVien:["ND024","ND025","ND026","ND027","ND028","ND047"],thanhVienChiTiet:[{maNguoiDung:"ND024",chucVu:"Trưởng nhóm"},{maNguoiDung:"ND025",chucVu:"Thư ký nhóm"},{maNguoiDung:"ND026",chucVu:"Thành viên"},{maNguoiDung:"ND027",chucVu:"Thành viên"},{maNguoiDung:"ND028",chucVu:"Thành viên"},{maNguoiDung:"ND047",chucVu:"Nhân sự hỗ trợ"}],tieuChi:["TC5.1","TC5.2","TC5.3","TC5.4","TC5.5","TC5.6","TC5.7","TC5.8"],trangThai:"Hoạt động"},
  {_id:"NT004",tenNhom:"Nhóm công tác 4",loaiNhom:"NHOM_CHINH",maTruongNhom:"ND029",maThuKyNhom:"ND030",tieuChuanPhuTrach:["TC6","TC7"],thanhVien:["ND029","ND030","ND031","ND032","ND033","ND034","ND035","ND048","ND049","ND050"],thanhVienChiTiet:[{maNguoiDung:"ND029",chucVu:"Trưởng nhóm"},{maNguoiDung:"ND030",chucVu:"Thư ký nhóm"},{maNguoiDung:"ND031",chucVu:"Thành viên"},{maNguoiDung:"ND032",chucVu:"Thành viên"},{maNguoiDung:"ND033",chucVu:"Thành viên"},{maNguoiDung:"ND034",chucVu:"Thành viên"},{maNguoiDung:"ND035",chucVu:"Thành viên"},{maNguoiDung:"ND048",chucVu:"Nhân sự hỗ trợ"},{maNguoiDung:"ND049",chucVu:"Nhân sự hỗ trợ"},{maNguoiDung:"ND050",chucVu:"Nhân sự hỗ trợ"}],tieuChi:["TC6.1","TC6.2","TC6.3","TC6.4","TC6.5","TC6.6","TC7.1","TC7.2","TC7.3","TC7.4","TC7.5","TC7.6","TC7.7","TC7.8"],trangThai:"Hoạt động"},
  {_id:"NT005",tenNhom:"Nhóm công tác 5",loaiNhom:"NHOM_CHINH",maTruongNhom:"ND036",maThuKyNhom:"ND037",tieuChuanPhuTrach:["TC8"],thanhVien:["ND036","ND037","ND038","ND039","ND040","ND041","ND051","ND052"],thanhVienChiTiet:[{maNguoiDung:"ND036",chucVu:"Trưởng nhóm"},{maNguoiDung:"ND037",chucVu:"Thư ký nhóm"},{maNguoiDung:"ND038",chucVu:"Thành viên"},{maNguoiDung:"ND039",chucVu:"Thành viên"},{maNguoiDung:"ND040",chucVu:"Thành viên"},{maNguoiDung:"ND041",chucVu:"Thành viên"},{maNguoiDung:"ND051",chucVu:"Nhân sự hỗ trợ"},{maNguoiDung:"ND052",chucVu:"Nhân sự hỗ trợ"}],tieuChi:["TC8.1","TC8.2","TC8.3","TC8.4","TC8.5"],trangThai:"Hoạt động"}
];

db.nhomTieuChi.insertMany(groups);

const assignments = [
  {_id:"PC001",maKeHoach:"KH001",maNguoiDung:"ND010",maTieuChuan:"TC1",maTieuChi:null,maNhomTieuChi:"NT001",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_TC1",noiDungCongViec:"Phụ trách Tiêu chuẩn 1 theo bảng phân công chính thức",ngayBatDau:ISODate("2026-09-03T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND001"},
  {_id:"PC002",maKeHoach:"KH001",maNguoiDung:"ND010",maTieuChuan:"TC2",maTieuChi:null,maNhomTieuChi:"NT001",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_TC2",noiDungCongViec:"Phụ trách Tiêu chuẩn 2 theo bảng phân công chính thức",ngayBatDau:ISODate("2026-09-03T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND001"},
  {_id:"PC003",maKeHoach:"KH001",maNguoiDung:"ND017",maTieuChuan:"TC3",maTieuChi:null,maNhomTieuChi:"NT002",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_TC3",noiDungCongViec:"Phụ trách Tiêu chuẩn 3 theo bảng phân công chính thức",ngayBatDau:ISODate("2026-09-03T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND001"},
  {_id:"PC004",maKeHoach:"KH001",maNguoiDung:"ND017",maTieuChuan:"TC4",maTieuChi:null,maNhomTieuChi:"NT002",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_TC4",noiDungCongViec:"Phụ trách Tiêu chuẩn 4 theo bảng phân công chính thức",ngayBatDau:ISODate("2026-09-03T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND001"},
  {_id:"PC005",maKeHoach:"KH001",maNguoiDung:"ND024",maTieuChuan:"TC5",maTieuChi:null,maNhomTieuChi:"NT003",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_TC5",noiDungCongViec:"Phụ trách Tiêu chuẩn 5 theo bảng phân công chính thức",ngayBatDau:ISODate("2026-09-03T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND001"},
  {_id:"PC006",maKeHoach:"KH001",maNguoiDung:"ND029",maTieuChuan:"TC6",maTieuChi:null,maNhomTieuChi:"NT004",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_TC6",noiDungCongViec:"Phụ trách Tiêu chuẩn 6 theo bảng phân công chính thức",ngayBatDau:ISODate("2026-09-03T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND001"},
  {_id:"PC007",maKeHoach:"KH001",maNguoiDung:"ND029",maTieuChuan:"TC7",maTieuChi:null,maNhomTieuChi:"NT004",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_TC7",noiDungCongViec:"Phụ trách Tiêu chuẩn 7 theo bảng phân công chính thức",ngayBatDau:ISODate("2026-09-03T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND001"},
  {_id:"PC008",maKeHoach:"KH001",maNguoiDung:"ND036",maTieuChuan:"TC8",maTieuChi:null,maNhomTieuChi:"NT005",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_TC8",noiDungCongViec:"Phụ trách Tiêu chuẩn 8 theo bảng phân công chính thức",ngayBatDau:ISODate("2026-09-03T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND001"},
  {_id:"PC009",maKeHoach:"KH001",maNguoiDung:"ND015",maTieuChuan:"TC1",maTieuChi:"TC1.1",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_1_1",noiDungCongViec:"Phụ trách tiêu chí 1.1 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC010",maKeHoach:"KH001",maNguoiDung:"ND015",maTieuChuan:"TC1",maTieuChi:"TC1.2",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND042"],maDotPhanCong:"DOT_1_2",noiDungCongViec:"Phụ trách tiêu chí 1.2 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC011",maKeHoach:"KH001",maNguoiDung:"ND010",maTieuChuan:"TC1",maTieuChi:"TC1.3",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:["ND043"],maDotPhanCong:"DOT_1_3",noiDungCongViec:"Phụ trách tiêu chí 1.3 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC012",maKeHoach:"KH001",maNguoiDung:"ND011",maTieuChuan:"TC1",maTieuChi:"TC1.4",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thư ký nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_1_4",noiDungCongViec:"Phụ trách tiêu chí 1.4 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC013",maKeHoach:"KH001",maNguoiDung:"ND013",maTieuChuan:"TC1",maTieuChi:"TC1.5",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_1_5",noiDungCongViec:"Phụ trách tiêu chí 1.5 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC014",maKeHoach:"KH001",maNguoiDung:"ND014",maTieuChuan:"TC1",maTieuChi:"TC1.6",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND043"],maDotPhanCong:"DOT_1_6",noiDungCongViec:"Phụ trách tiêu chí 1.6 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC015",maKeHoach:"KH001",maNguoiDung:"ND011",maTieuChuan:"TC2",maTieuChi:"TC2.1",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thư ký nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_2_1",noiDungCongViec:"Phụ trách tiêu chí 2.1 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC016",maKeHoach:"KH001",maNguoiDung:"ND013",maTieuChuan:"TC2",maTieuChi:"TC2.2",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND016", "ND044"],maDotPhanCong:"DOT_2_2",noiDungCongViec:"Phụ trách tiêu chí 2.2 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC017",maKeHoach:"KH001",maNguoiDung:"ND015",maTieuChuan:"TC2",maTieuChi:"TC2.3",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND044"],maDotPhanCong:"DOT_2_3",noiDungCongViec:"Phụ trách tiêu chí 2.3 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC018",maKeHoach:"KH001",maNguoiDung:"ND012",maTieuChuan:"TC2",maTieuChi:"TC2.4",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND016", "ND042"],maDotPhanCong:"DOT_2_4",noiDungCongViec:"Phụ trách tiêu chí 2.4 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC019",maKeHoach:"KH001",maNguoiDung:"ND014",maTieuChuan:"TC2",maTieuChi:"TC2.5",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_2_5",noiDungCongViec:"Phụ trách tiêu chí 2.5 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC020",maKeHoach:"KH001",maNguoiDung:"ND012",maTieuChuan:"TC2",maTieuChi:"TC2.6",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_2_6",noiDungCongViec:"Phụ trách tiêu chí 2.6 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC021",maKeHoach:"KH001",maNguoiDung:"ND010",maTieuChuan:"TC2",maTieuChi:"TC2.7",maNhomTieuChi:"NT001",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_2_7",noiDungCongViec:"Phụ trách tiêu chí 2.7 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND010"},
  {_id:"PC022",maKeHoach:"KH001",maNguoiDung:"ND018",maTieuChuan:"TC3",maTieuChi:"TC3.1",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thư ký nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_3_1",noiDungCongViec:"Phụ trách tiêu chí 3.1 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC023",maKeHoach:"KH001",maNguoiDung:"ND017",maTieuChuan:"TC3",maTieuChi:"TC3.2",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:["ND045"],maDotPhanCong:"DOT_3_2",noiDungCongViec:"Phụ trách tiêu chí 3.2 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC024",maKeHoach:"KH001",maNguoiDung:"ND019",maTieuChuan:"TC3",maTieuChi:"TC3.3",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND046"],maDotPhanCong:"DOT_3_3",noiDungCongViec:"Phụ trách tiêu chí 3.3 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC025",maKeHoach:"KH001",maNguoiDung:"ND019",maTieuChuan:"TC3",maTieuChi:"TC3.4",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND046"],maDotPhanCong:"DOT_3_4",noiDungCongViec:"Phụ trách tiêu chí 3.4 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC026",maKeHoach:"KH001",maNguoiDung:"ND020",maTieuChuan:"TC3",maTieuChi:"TC3.5",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND021"],maDotPhanCong:"DOT_3_5",noiDungCongViec:"Phụ trách tiêu chí 3.5 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC027",maKeHoach:"KH001",maNguoiDung:"ND020",maTieuChuan:"TC4",maTieuChi:"TC4.1",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND021"],maDotPhanCong:"DOT_4_1",noiDungCongViec:"Phụ trách tiêu chí 4.1 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC028",maKeHoach:"KH001",maNguoiDung:"ND023",maTieuChuan:"TC4",maTieuChi:"TC4.2",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_4_2",noiDungCongViec:"Phụ trách tiêu chí 4.2 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC029",maKeHoach:"KH001",maNguoiDung:"ND023",maTieuChuan:"TC4",maTieuChi:"TC4.3",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_4_3",noiDungCongViec:"Phụ trách tiêu chí 4.3 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC030",maKeHoach:"KH001",maNguoiDung:"ND018",maTieuChuan:"TC4",maTieuChi:"TC4.4",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thư ký nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_4_4",noiDungCongViec:"Phụ trách tiêu chí 4.4 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC031",maKeHoach:"KH001",maNguoiDung:"ND022",maTieuChuan:"TC4",maTieuChi:"TC4.5",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND045"],maDotPhanCong:"DOT_4_5",noiDungCongViec:"Phụ trách tiêu chí 4.5 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC032",maKeHoach:"KH001",maNguoiDung:"ND022",maTieuChuan:"TC4",maTieuChi:"TC4.6",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_4_6",noiDungCongViec:"Phụ trách tiêu chí 4.6 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC033",maKeHoach:"KH001",maNguoiDung:"ND017",maTieuChuan:"TC4",maTieuChi:"TC4.7",maNhomTieuChi:"NT002",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_4_7",noiDungCongViec:"Phụ trách tiêu chí 4.7 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND017"},
  {_id:"PC034",maKeHoach:"KH001",maNguoiDung:"ND024",maTieuChuan:"TC5",maTieuChi:"TC5.1",maNhomTieuChi:"NT003",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_5_1",noiDungCongViec:"Phụ trách tiêu chí 5.1 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND024"},
  {_id:"PC035",maKeHoach:"KH001",maNguoiDung:"ND024",maTieuChuan:"TC5",maTieuChi:"TC5.2",maNhomTieuChi:"NT003",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_5_2",noiDungCongViec:"Phụ trách tiêu chí 5.2 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND024"},
  {_id:"PC036",maKeHoach:"KH001",maNguoiDung:"ND025",maTieuChuan:"TC5",maTieuChi:"TC5.3",maNhomTieuChi:"NT003",vaiTroTrongNhom:"Thư ký nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_5_3",noiDungCongViec:"Phụ trách tiêu chí 5.3 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND024"},
  {_id:"PC037",maKeHoach:"KH001",maNguoiDung:"ND026",maTieuChuan:"TC5",maTieuChi:"TC5.4",maNhomTieuChi:"NT003",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_5_4",noiDungCongViec:"Phụ trách tiêu chí 5.4 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND024"},
  {_id:"PC038",maKeHoach:"KH001",maNguoiDung:"ND026",maTieuChuan:"TC5",maTieuChi:"TC5.5",maNhomTieuChi:"NT003",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND047"],maDotPhanCong:"DOT_5_5",noiDungCongViec:"Phụ trách tiêu chí 5.5 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND024"},
  {_id:"PC039",maKeHoach:"KH001",maNguoiDung:"ND027",maTieuChuan:"TC5",maTieuChi:"TC5.6",maNhomTieuChi:"NT003",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_5_6",noiDungCongViec:"Phụ trách tiêu chí 5.6 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND024"},
  {_id:"PC040",maKeHoach:"KH001",maNguoiDung:"ND027",maTieuChuan:"TC5",maTieuChi:"TC5.7",maNhomTieuChi:"NT003",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND047"],maDotPhanCong:"DOT_5_7",noiDungCongViec:"Phụ trách tiêu chí 5.7 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND024"},
  {_id:"PC041",maKeHoach:"KH001",maNguoiDung:"ND028",maTieuChuan:"TC5",maTieuChi:"TC5.8",maNhomTieuChi:"NT003",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_5_8",noiDungCongViec:"Phụ trách tiêu chí 5.8 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND024"},
  {_id:"PC042",maKeHoach:"KH001",maNguoiDung:"ND029",maTieuChuan:"TC6",maTieuChi:"TC6.1",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:["ND048"],maDotPhanCong:"DOT_6_1",noiDungCongViec:"Phụ trách tiêu chí 6.1 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC043",maKeHoach:"KH001",maNguoiDung:"ND029",maTieuChuan:"TC6",maTieuChi:"TC6.2",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:["ND048"],maDotPhanCong:"DOT_6_2",noiDungCongViec:"Phụ trách tiêu chí 6.2 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC044",maKeHoach:"KH001",maNguoiDung:"ND033",maTieuChuan:"TC6",maTieuChi:"TC6.3",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND048"],maDotPhanCong:"DOT_6_3",noiDungCongViec:"Phụ trách tiêu chí 6.3 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC045",maKeHoach:"KH001",maNguoiDung:"ND033",maTieuChuan:"TC6",maTieuChi:"TC6.4",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND048"],maDotPhanCong:"DOT_6_4",noiDungCongViec:"Phụ trách tiêu chí 6.4 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC046",maKeHoach:"KH001",maNguoiDung:"ND031",maTieuChuan:"TC6",maTieuChi:"TC6.5",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND049"],maDotPhanCong:"DOT_6_5",noiDungCongViec:"Phụ trách tiêu chí 6.5 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC047",maKeHoach:"KH001",maNguoiDung:"ND031",maTieuChuan:"TC6",maTieuChi:"TC6.6",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND049"],maDotPhanCong:"DOT_6_6",noiDungCongViec:"Phụ trách tiêu chí 6.6 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC048",maKeHoach:"KH001",maNguoiDung:"ND032",maTieuChuan:"TC7",maTieuChi:"TC7.1",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND049"],maDotPhanCong:"DOT_7_1",noiDungCongViec:"Phụ trách tiêu chí 7.1 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC049",maKeHoach:"KH001",maNguoiDung:"ND032",maTieuChuan:"TC7",maTieuChi:"TC7.2",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND049"],maDotPhanCong:"DOT_7_2",noiDungCongViec:"Phụ trách tiêu chí 7.2 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC050",maKeHoach:"KH001",maNguoiDung:"ND030",maTieuChuan:"TC7",maTieuChi:"TC7.3",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thư ký nhóm",maNhanSuHoTroList:["ND050"],maDotPhanCong:"DOT_7_3",noiDungCongViec:"Phụ trách tiêu chí 7.3 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC051",maKeHoach:"KH001",maNguoiDung:"ND030",maTieuChuan:"TC7",maTieuChi:"TC7.4",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thư ký nhóm",maNhanSuHoTroList:["ND050"],maDotPhanCong:"DOT_7_4",noiDungCongViec:"Phụ trách tiêu chí 7.4 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC052",maKeHoach:"KH001",maNguoiDung:"ND034",maTieuChuan:"TC7",maTieuChi:"TC7.5",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND050"],maDotPhanCong:"DOT_7_5",noiDungCongViec:"Phụ trách tiêu chí 7.5 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC053",maKeHoach:"KH001",maNguoiDung:"ND034",maTieuChuan:"TC7",maTieuChi:"TC7.6",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND050"],maDotPhanCong:"DOT_7_6",noiDungCongViec:"Phụ trách tiêu chí 7.6 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC054",maKeHoach:"KH001",maNguoiDung:"ND035",maTieuChuan:"TC7",maTieuChi:"TC7.7",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND050"],maDotPhanCong:"DOT_7_7",noiDungCongViec:"Phụ trách tiêu chí 7.7 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC055",maKeHoach:"KH001",maNguoiDung:"ND035",maTieuChuan:"TC7",maTieuChi:"TC7.8",maNhomTieuChi:"NT004",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND050"],maDotPhanCong:"DOT_7_8",noiDungCongViec:"Phụ trách tiêu chí 7.8 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND029"},
  {_id:"PC056",maKeHoach:"KH001",maNguoiDung:"ND040",maTieuChuan:"TC8",maTieuChi:"TC8.1",maNhomTieuChi:"NT005",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND039"],maDotPhanCong:"DOT_8_1",noiDungCongViec:"Phụ trách tiêu chí 8.1 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND036"},
  {_id:"PC057",maKeHoach:"KH001",maNguoiDung:"ND036",maTieuChuan:"TC8",maTieuChi:"TC8.2",maNhomTieuChi:"NT005",vaiTroTrongNhom:"Trưởng nhóm",maNhanSuHoTroList:[],maDotPhanCong:"DOT_8_2",noiDungCongViec:"Phụ trách tiêu chí 8.2 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND036"},
  {_id:"PC058",maKeHoach:"KH001",maNguoiDung:"ND038",maTieuChuan:"TC8",maTieuChi:"TC8.3",maNhomTieuChi:"NT005",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_8_3",noiDungCongViec:"Phụ trách tiêu chí 8.3 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND036"},
  {_id:"PC059",maKeHoach:"KH001",maNguoiDung:"ND037",maTieuChuan:"TC8",maTieuChi:"TC8.4",maNhomTieuChi:"NT005",vaiTroTrongNhom:"Thư ký nhóm",maNhanSuHoTroList:["ND051"],maDotPhanCong:"DOT_8_4",noiDungCongViec:"Phụ trách tiêu chí 8.4 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND036"},
  {_id:"PC060",maKeHoach:"KH001",maNguoiDung:"ND041",maTieuChuan:"TC8",maTieuChi:"TC8.5",maNhomTieuChi:"NT005",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:[],maDotPhanCong:"DOT_8_5",noiDungCongViec:"Phụ trách tiêu chí 8.5 theo file Excel phân công",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-15T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND036"},
  {_id:"PC061",maKeHoach:"KH001",maNguoiDung:"ND039",maTieuChuan:"TC8",maTieuChi:null,maNhomTieuChi:"NT005",vaiTroTrongNhom:"Thành viên",maNhanSuHoTroList:["ND040", "ND052"],maDotPhanCong:"DOT_CSDL_KIEM_DINH",noiDungCongViec:"Phụ trách CSDL kiểm định theo biểu mẫu trong Báo cáo tự đánh giá - Phần IV",ngayBatDau:ISODate("2026-09-05T00:00:00.000Z"),ngayKetThucDuKien:ISODate("2026-11-30T00:00:00.000Z"),trangThai:"Đang thực hiện",maNguoiTao:"ND036"}
];
const criterionAssignment = {};
criterionAssignment["TC1.1"] = assignments.find(x => x._id === "PC009");
criterionAssignment["TC1.2"] = assignments.find(x => x._id === "PC010");
criterionAssignment["TC1.3"] = assignments.find(x => x._id === "PC011");
criterionAssignment["TC1.4"] = assignments.find(x => x._id === "PC012");
criterionAssignment["TC1.5"] = assignments.find(x => x._id === "PC013");
criterionAssignment["TC1.6"] = assignments.find(x => x._id === "PC014");
criterionAssignment["TC2.1"] = assignments.find(x => x._id === "PC015");
criterionAssignment["TC2.2"] = assignments.find(x => x._id === "PC016");
criterionAssignment["TC2.3"] = assignments.find(x => x._id === "PC017");
criterionAssignment["TC2.4"] = assignments.find(x => x._id === "PC018");
criterionAssignment["TC2.5"] = assignments.find(x => x._id === "PC019");
criterionAssignment["TC2.6"] = assignments.find(x => x._id === "PC020");
criterionAssignment["TC2.7"] = assignments.find(x => x._id === "PC021");
criterionAssignment["TC3.1"] = assignments.find(x => x._id === "PC022");
criterionAssignment["TC3.2"] = assignments.find(x => x._id === "PC023");
criterionAssignment["TC3.3"] = assignments.find(x => x._id === "PC024");
criterionAssignment["TC3.4"] = assignments.find(x => x._id === "PC025");
criterionAssignment["TC3.5"] = assignments.find(x => x._id === "PC026");
criterionAssignment["TC4.1"] = assignments.find(x => x._id === "PC027");
criterionAssignment["TC4.2"] = assignments.find(x => x._id === "PC028");
criterionAssignment["TC4.3"] = assignments.find(x => x._id === "PC029");
criterionAssignment["TC4.4"] = assignments.find(x => x._id === "PC030");
criterionAssignment["TC4.5"] = assignments.find(x => x._id === "PC031");
criterionAssignment["TC4.6"] = assignments.find(x => x._id === "PC032");
criterionAssignment["TC4.7"] = assignments.find(x => x._id === "PC033");
criterionAssignment["TC5.1"] = assignments.find(x => x._id === "PC034");
criterionAssignment["TC5.2"] = assignments.find(x => x._id === "PC035");
criterionAssignment["TC5.3"] = assignments.find(x => x._id === "PC036");
criterionAssignment["TC5.4"] = assignments.find(x => x._id === "PC037");
criterionAssignment["TC5.5"] = assignments.find(x => x._id === "PC038");
criterionAssignment["TC5.6"] = assignments.find(x => x._id === "PC039");
criterionAssignment["TC5.7"] = assignments.find(x => x._id === "PC040");
criterionAssignment["TC5.8"] = assignments.find(x => x._id === "PC041");
criterionAssignment["TC6.1"] = assignments.find(x => x._id === "PC042");
criterionAssignment["TC6.2"] = assignments.find(x => x._id === "PC043");
criterionAssignment["TC6.3"] = assignments.find(x => x._id === "PC044");
criterionAssignment["TC6.4"] = assignments.find(x => x._id === "PC045");
criterionAssignment["TC6.5"] = assignments.find(x => x._id === "PC046");
criterionAssignment["TC6.6"] = assignments.find(x => x._id === "PC047");
criterionAssignment["TC7.1"] = assignments.find(x => x._id === "PC048");
criterionAssignment["TC7.2"] = assignments.find(x => x._id === "PC049");
criterionAssignment["TC7.3"] = assignments.find(x => x._id === "PC050");
criterionAssignment["TC7.4"] = assignments.find(x => x._id === "PC051");
criterionAssignment["TC7.5"] = assignments.find(x => x._id === "PC052");
criterionAssignment["TC7.6"] = assignments.find(x => x._id === "PC053");
criterionAssignment["TC7.7"] = assignments.find(x => x._id === "PC054");
criterionAssignment["TC7.8"] = assignments.find(x => x._id === "PC055");
criterionAssignment["TC8.1"] = assignments.find(x => x._id === "PC056");
criterionAssignment["TC8.2"] = assignments.find(x => x._id === "PC057");
criterionAssignment["TC8.3"] = assignments.find(x => x._id === "PC058");
criterionAssignment["TC8.4"] = assignments.find(x => x._id === "PC059");
criterionAssignment["TC8.5"] = assignments.find(x => x._id === "PC060");

db.phanCong.insertMany(assignments);

const commonEvidence = [
  ["MCC001","Hồ sơ hoạt động ngoại khóa cấp Trường","Cấp Trường","Trường Đại học Công Thương Thành phố Hồ Chí Minh"],
  ["MCC002","Hồ sơ hoạt động thể thao sinh viên cấp Trường","Cấp Trường","Trường Đại học Công Thương Thành phố Hồ Chí Minh"],
  ["MCC003","Hồ sơ IT Cup","Cấp Khoa","Khoa Công nghệ Thông tin"],
  ["MCC004","Hồ sơ hội trại sinh viên","Cấp Khoa","Khoa Công nghệ Thông tin"],
  ["MCC005","Hồ sơ seminar chuyên môn","Cấp Khoa","Khoa Công nghệ Thông tin"],
  ["MCC006","Đề cương học phần","Cấp Khoa","Khoa Công nghệ Thông tin"],
  ["MCC007","Giáo án giảng dạy","Cấp Khoa","Khoa Công nghệ Thông tin"],
  ["MCC008","Giáo trình phục vụ chương trình đào tạo","Cấp Khoa","Khoa Công nghệ Thông tin"],
  ["MCC009","Hồ sơ giảng viên","Cấp Khoa","Khoa Công nghệ Thông tin"],
  ["MCC010","Quy định giảng dạy của Khoa","Cấp Khoa","Khoa Công nghệ Thông tin"]
];

db.minhChungChung.insertMany(commonEvidence.map(r => ({
  _id:r[0],maMinhChung:r[0],tenMinhChung:r[1],capQuanLy:r[2],donViBanHanh:r[3],
  moTa:"Dữ liệu minh chứng dùng chung phục vụ tự đánh giá.",maNguoiTao:"ND001",
  ngayTao:ISODate("2026-09-01T08:00:00.000Z"),
  ngayCapNhat:ISODate("2026-09-01T08:00:00.000Z")
})));

const reportStates = [
  ["Đang soạn",25,"Thành viên"],
  ["Chờ Trưởng nhóm duyệt",25,"Thành viên"],
  ["Chờ Hội đồng cấp khoa duyệt",50,"Trưởng nhóm"],
  ["Đã Hội đồng duyệt",75,"Hội đồng cấp khoa"],
  ["Đã duyệt",100,"TT QLCL"]
];

const evidences=[];
const criterionReports=[];

criteria.forEach((c,index) => {
  const i=index+1;
  const critId=`${c[0]}.${c[1]}`;
  const pc=criterionAssignment[critId];
  evidences.push({
    _id:`MC${String(i).padStart(3,"0")}`,
    loaiMinhChung:"TIEU_CHI",
    maMinhChung:`H${Number(c[0].substring(2))}.${c[1]}.01`,
    maTieuChuan:c[0],maTieuChi:critId,maPhanCong:pc._id,maKeHoach:"KH001",
    tenMinhChung:`Minh chứng phục vụ ${critId}`,
    moTa:`Minh chứng demo phục vụ quá trình tự đánh giá ${critId}.`,
    ngayUpload:new Date(Date.UTC(2026,8,20,8,i,0)),
    maNguoiUpload:pc.maNguoiDung,
    duongDanFile:"/demo/minh-chung-mau.pdf",loaiFile:"pdf",kichThuoc:NumberLong("1024"),
    trangThai:i%3===0?"Đã duyệt":(i%3===1?"Chờ Trưởng nhóm duyệt":"Chờ Thư ký duyệt")
  });

  const st=reportStates[index%reportStates.length];
  const d=new Date(Date.UTC(2026,8,25,8,i,0));
  criterionReports.push({
    _id:`BC${String(i).padStart(3,"0")}`,
    maPhanCong:pc._id,maKeHoach:"KH001",maTieuChuan:c[0],maTieuChi:critId,
    nhanDeTieuChi:`${critId} - ${c[2]}`,
    moTaHienTrang:`Nhóm công tác đã rà soát hiện trạng, hồ sơ quản lý, tài liệu chuyên môn và các minh chứng liên quan đến ${critId}.`,
    diemManh:"Đơn vị đã triển khai các hoạt động liên quan và có dữ liệu phục vụ tự đánh giá.",
    diemTonTai:"Một số dữ liệu cần tiếp tục được cập nhật, chuẩn hóa và hoàn thiện.",
    keHoachHanhDong:"Tiếp tục rà soát, bổ sung minh chứng và thực hiện các hoạt động cải tiến trong chu kỳ tiếp theo.",
    trangThai:st[0],mucDoDuyet:st[1],capDuyetHienTai:st[2],maNguoiViet:pc.maNguoiDung,
    duyetBaoCao:[],ngayTao:d,ngayCapNhat:d
  });
});

db.minhChung.insertMany(evidences);
db.baoCaoTieuChi.insertMany(criterionReports);

const groupReports=[];
let bcnNo=1;
groups.forEach(g => {
  g.tieuChuanPhuTrach.forEach(std => {
    const reportIds=criterionReports.filter(x=>x.maTieuChuan===std).map(x=>x._id);
    const stdName=standards.find(x=>x[0]===std)[2];
    groupReports.push({
      _id:`BCN${String(bcnNo++).padStart(3,"0")}`,
      maKeHoach:"KH001",maNhomTieuChi:g._id,maTieuChuan:std,
      tenBaoCao:`Báo cáo tổng hợp ${std} - ${stdName}`,
      danhSachBaoCaoTieuChi:reportIds,
      moTaTongHop:`Tổng hợp hiện trạng và kết quả tự đánh giá các tiêu chí thuộc ${std}.`,
      diemManhTongHop:"Các tiêu chí được phân công theo file Excel và có nội dung tự đánh giá tương ứng.",
      tonTaiTongHop:"Một số dữ liệu và minh chứng cần tiếp tục hoàn thiện.",
      keHoachCaiTien:"Tiếp tục rà soát, cập nhật và hoàn thiện các nội dung theo phản hồi.",
      maThuKyNhom:g.maThuKyNhom,maTruongNhom:g.maTruongNhom,trangThai:"Đang tổng hợp",
      ngayTao:ISODate("2026-10-01T08:00:00.000Z"),
      ngayCapNhat:ISODate("2026-10-01T08:00:00.000Z")
    });
  });
});
db.baoCaoNhom.insertMany(groupReports);

db.baoCaoTuDanhGia.insertOne({
  _id:"BCTDG001",
  maKeHoach:"KH001",maCTDT:"CTDT001",maPhienBanCTDT:"PB2026",
  tenBaoCao:"Báo cáo tự đánh giá chương trình đào tạo ngành Công nghệ thông tin năm 2026",
  phienBanBaoCao:1,
  cauTruc:[
    {soPhan:1,tenPhan:"Khái quát",loai:"PHAN_CHUNG",nguonDuLieu:"baoCaoPhan"},
    {soPhan:2,tenPhan:"Tự đánh giá theo các tiêu chuẩn, tiêu chí",loai:"NHOM_CONG_TAC",nguonDuLieu:"baoCaoNhom"},
    {soPhan:3,tenPhan:"Kết luận",loai:"PHAN_CHUNG",nguonDuLieu:"baoCaoPhan"},
    {soPhan:4,tenPhan:"Phụ lục",loai:"PHAN_CHUNG",nguonDuLieu:"baoCaoPhan"}
  ],
  nguonPhan2:{collection:"baoCaoNhom",maKeHoach:"KH001"},
  trangThai:"Đang thực hiện",mucDoHoanThanh:45,maNguoiTao:"ND001",
  ngayTao:ISODate("2026-10-01T08:00:00.000Z"),
  ngayCapNhat:ISODate("2026-10-01T08:00:00.000Z")
});

const reportJobs=[
  ["PCB001",1,"Biên soạn Phần 1 - Khái quát","ND004",["ND005"],"Tổng hợp đặt vấn đề, tổng quan cơ sở giáo dục, đơn vị đào tạo, CTĐT và quá trình tự đánh giá.",60],
  ["PCB002",3,"Biên soạn Phần 3 - Kết luận","ND005",["ND004"],"Tổng hợp điểm mạnh, tồn tại, kế hoạch cải tiến và kết quả tự đánh giá.",40],
  ["PCB003",4,"Biên soạn Phần 4 - Phụ lục","ND052",["ND039","ND040"],"Tổng hợp CSDL kiểm định, bảng kết quả, quyết định, văn bản và danh mục minh chứng.",35]
];

db.phanCongBaoCao.insertMany(reportJobs.map(r => ({
  _id:r[0],maBaoCaoTuDanhGia:"BCTDG001",maKeHoach:"KH001",soPhan:r[1],
  tenCongViec:r[2],maNguoiDung:r[3],maNhanSuHoTroList:r[4],noiDungCongViec:r[5],
  ngayBatDau:ISODate("2026-10-01T00:00:00.000Z"),
  ngayKetThucDuKien:ISODate("2026-11-20T00:00:00.000Z"),
  trangThai:"Đang thực hiện",phanTramHoanThanh:r[6],maNguoiTao:"ND001",
  ngayTao:ISODate("2026-10-01T08:00:00.000Z"),
  ngayCapNhat:ISODate("2026-10-01T08:00:00.000Z")
})));

const partBase={
  trangThai:"Đang soạn",mucDoDuyet:25,capDuyetHienTai:"Người phụ trách",feedback:[],
  ngayTao:ISODate("2026-10-01T08:00:00.000Z"),
  ngayCapNhat:ISODate("2026-10-01T08:00:00.000Z")
};

db.baoCaoPhan.insertMany([
  {
    _id:"BCP001",maBaoCaoTuDanhGia:"BCTDG001",maKeHoach:"KH001",soPhan:1,
    tenPhan:"Phần 1 - Khái quát",maPhanCong:"PCB001",maNguoiPhuTrach:"ND004",
    noiDung:{
      datVanDe:"Chương trình đào tạo thực hiện tự đánh giá nhằm rà soát mức độ đáp ứng các yêu cầu bảo đảm chất lượng.",
      tongQuanCoSoGiaoDuc:"Nội dung tổng quan về cơ sở giáo dục.",
      tongQuanDonViDaoTao:"Nội dung giới thiệu Khoa Công nghệ Thông tin.",
      tongQuanCTDT:"Nội dung tổng quan chương trình đào tạo.",
      phuongPhapTuDanhGia:"Thu thập dữ liệu, minh chứng, phân tích hiện trạng, điểm mạnh, tồn tại và kế hoạch cải tiến."
    },
    ...partBase
  },
  {
    _id:"BCP003",maBaoCaoTuDanhGia:"BCTDG001",maKeHoach:"KH001",soPhan:3,
    tenPhan:"Phần 3 - Kết luận",maPhanCong:"PCB002",maNguoiPhuTrach:"ND005",
    noiDung:{
      tongHopDiemManh:"Tổng hợp điểm mạnh từ các tiêu chuẩn.",
      tongHopTonTai:"Tổng hợp tồn tại và nội dung cần cải tiến.",
      tongHopKeHoachCaiTien:"Tổng hợp kế hoạch cải tiến chất lượng chương trình đào tạo.",
      ketQuaTuDanhGia:{tongSoTieuChi:52,daDanhGia:0,dat:0,khongDat:0}
    },
    ...partBase
  },
  {
    _id:"BCP004",maBaoCaoTuDanhGia:"BCTDG001",maKeHoach:"KH001",soPhan:4,
    tenPhan:"Phần 4 - Phụ lục",maPhanCong:"PCB003",maNguoiPhuTrach:"ND052",
    noiDung:{bangTongHopKetQua:true,coSoDuLieuKiemDinh:true,danhMucMinhChung:true,cacQuyetDinhLienQuan:true},
    danhSachMinhChungCapTruong:commonEvidence.filter(x=>x[2]==="Cấp Trường").map(x=>x[0]),
    danhSachMinhChungCapKhoa:commonEvidence.filter(x=>x[2]==="Cấp Khoa").map(x=>x[0]),
    danhSachMinhChungTieuChi:evidences.map(x=>x._id),
    ...partBase
  }
]);

db.tienDo.insertMany(assignments
  .slice()
  .sort((a,b)=>a._id.localeCompare(b._id))
  .map((pc,i)=>({
    _id:`TD${String(i+1).padStart(3,"0")}`,
    maKeHoach:pc.maKeHoach,
    maPhanCong:pc._id,
    phanTramHoanThanh:pc.maTieuChi===null?50:30,
    trangThai:pc.trangThai
  })));

const evalNums=[15,30,45];
db.danhGiaTieuChi.insertMany(evalNums.map((num,index)=>{
  const no=index+1;
  const bc=criterionReports[num-1];
  return {
    _id:`DGTC${String(no).padStart(3,"0")}`,
    maKeHoach:"KH001",maTieuChi:bc.maTieuChi,lanDanhGia:1,
    ketQua:no===3?"Không đạt":"Đạt",
    nhanXet:"Hội đồng đã xem xét báo cáo và minh chứng của tiêu chí.",
    maNguoiDanhGia:"ND002",
    ngayDanhGia:new Date(Date.UTC(2026,9,5,8,no,0))
  };
}));

db.lichSuThaoTac.insertMany([
  {_id:"LS001",maNguoiDung:"ND001",hanhDong:"Khởi tạo dữ liệu",doiTuongTacDong:"Hệ thống",maBanGhi:"MOET",noiDung:"Khởi tạo bộ MOET gồm 8 tiêu chuẩn và 52 tiêu chí.",thoiGian:ISODate("2026-10-01T08:00:00.000Z")},
  {_id:"LS002",maNguoiDung:"ND004",hanhDong:"Lập kế hoạch",doiTuongTacDong:"Kế hoạch tự đánh giá",maBanGhi:"KH001",noiDung:"Ban Thư ký lập kế hoạch tự đánh giá.",thoiGian:ISODate("2026-10-01T09:00:00.000Z")},
  {_id:"LS003",maNguoiDung:"ND003",hanhDong:"Phê duyệt",doiTuongTacDong:"Kế hoạch tự đánh giá",maBanGhi:"KH001",noiDung:"Trung tâm Quản lý chất lượng phê duyệt kế hoạch.",thoiGian:ISODate("2026-10-01T10:00:00.000Z")},
  {_id:"LS004",maNguoiDung:"ND001",hanhDong:"Phân công",doiTuongTacDong:"Nhóm công tác",maBanGhi:"NT001",noiDung:"Phân công tiêu chuẩn cho 5 nhóm theo file Excel.",thoiGian:ISODate("2026-10-01T11:00:00.000Z")},
  {_id:"LS005",maNguoiDung:"ND010",hanhDong:"Phân công",doiTuongTacDong:"Tiêu chí",maBanGhi:"TC1.1",noiDung:"Phân công tiêu chí theo bảng phân công chính thức.",thoiGian:ISODate("2026-10-01T12:00:00.000Z")},
  {_id:"LS006",maNguoiDung:"ND015",hanhDong:"Nộp minh chứng",doiTuongTacDong:"Minh chứng",maBanGhi:"MC001",noiDung:"Nộp minh chứng phục vụ báo cáo tiêu chí.",thoiGian:ISODate("2026-10-01T13:00:00.000Z")}
]);

db.nguoiDung.createIndex({email:1},{unique:true});
db.phanCong.createIndex({maKeHoach:1,maNhomTieuChi:1,maTieuChuan:1,maTieuChi:1});
db.baoCaoTieuChi.createIndex({maKeHoach:1,maTieuChi:1});
db.minhChung.createIndex({maKeHoach:1,maTieuChi:1});

print("\n===== KẾT QUẢ TẠO LẠI DATABASE =====");
const expected = {
  vaiTro:7,
  nguoiDung:48,
  boTieuChuan:1,
  tieuChuan:8,
  tieuChi:52,
  chuongTrinhDaoTao:1,
  keHoachTuDanhGia:1,
  nhomTieuChi:5,
  phanCong:61,
  minhChung:52,
  minhChungChung:10,
  danhGiaMinhChung:0,
  baoCaoTieuChi:52,
  danhGiaTieuChi:3,
  baoCaoNhom:8,
  baoCaoPhan:3,
  phanCongBaoCao:3,
  baoCaoTuDanhGia:1,
  tienDo:61,
  thongBao:0,
  lichSuThaoTac:6
};

let ok=true;
Object.keys(expected).forEach(name => {
  const count=db.getCollection(name).countDocuments({});
  const pass=count===expected[name];
  if(!pass) ok=false;
  print(`${pass?"OK ":"LOI"} ${name.padEnd(24)} : ${count} / ${expected[name]}`);
});


print("\n===== KIỂM TRA PHÂN CÔNG THEO EXCEL =====");
const checkGroups = [
  ["NT001",["TC1","TC2"],"ND010"],
  ["NT002",["TC3","TC4"],"ND017"],
  ["NT003",["TC5"],"ND024"],
  ["NT004",["TC6","TC7"],"ND029"],
  ["NT005",["TC8"],"ND036"]
];
checkGroups.forEach(([gid,stds,leader])=>{
  const g=db.nhomTieuChi.findOne({_id:gid});
  const got=(g?.tieuChuanPhuTrach||[]).slice().sort().join(",");
  const exp=stds.slice().sort().join(",");
  print((g && got===exp && g.maTruongNhom===leader ? "OK  " : "LOI ") + gid + " -> " + got + " | Trưởng nhóm=" + (g?.maTruongNhom||"NULL"));
});
[["TC1.3","ND010"],["TC2.7","ND010"],["TC3.2","ND017"],["TC4.7","ND017"],["TC5.1","ND024"],["TC6.1","ND029"],["TC8.2","ND036"]].forEach(([tc,uid])=>{
  const a=db.phanCong.findOne({maTieuChi:tc});
  print((a && a.maNguoiDung===uid ? "OK  " : "LOI ") + tc + " -> " + (a?.maNguoiDung||"NULL") + " (mong đợi " + uid + ")");
});

print("\nTổng collection: " + db.getCollectionNames().length);
print(ok ? "\nHOÀN TẤT: DATA ĐÃ KHỚP FILE EXCEL PHÂN CÔNG VÀ SCHEMA HỆ THỐNG HIỆN TẠI." : "\nCÓ SAI LỆCH: kiểm tra các dòng LOI ở trên.");
print("Tài khoản mẫu dùng mật khẩu: 123456");
