// Chạy bằng mongosh sau khi kết nối database HeThongKiemDinh.
// Script này KHÔNG xóa dữ liệu. Nó kiểm tra quy ước mã sau khi bạn đã nạp 8 tiêu chuẩn/52 tiêu chí.

print("Database: " + db.getName());
print("So tieu chuan: " + db.tieuChuan.countDocuments());
print("So tieu chi: " + db.tieuChi.countDocuments());

print("\nSo luong tieu chi theo tieu chuan:");
db.tieuChi.aggregate([
  { $group: { _id: "$maTieuChuan", soLuong: { $sum: 1 } } },
  { $sort: { _id: 1 } }
]).forEach(printjson);

print("\nMa tieu chuan khong dung H1-H8:");
db.tieuChuan.find({ _id: { $not: /^H[1-8]$/ } }, { _id: 1, tenTieuChuan: 1 }).forEach(printjson);

print("\nMa tieu chi khong dung quy uoc Hn.nn.nn:");
db.tieuChi.find({ _id: { $not: /^H[1-8]\.(0[1-8])\.\d{2}$/ } }, { _id: 1, maTieuChuan: 1 }).forEach(printjson);

print("\n10 tieu chi dieu kien:");
db.tieuChi.find({ tieuChiDieuKien: "Có" }, { _id: 1, maTieuChuan: 1 }).sort({ _id: 1 }).forEach(printjson);
