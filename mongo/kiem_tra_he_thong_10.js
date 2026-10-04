use HeThongKiemDinh

print('=== Bộ tiêu chuẩn ===')
db.boTieuChuan.find({}, {_id:1, tenBoTieuChuan:1}).forEach(printjson)

print('=== Vai trò (kiểm tra không lặp tên) ===')
db.vaiTro.aggregate([
  {$group:{_id:'$tenVaiTro', soLuong:{$sum:1}, ma:{$push:'$_id'}}},
  {$sort:{_id:1}}
]).forEach(printjson)

print('=== CTĐT và năm phiên bản ===')
db.chuongTrinhDaoTao.find({}, {_id:1, tenCTDT:1, 'phienBan.namHoc':1, 'phienBan.maPhienBan':1, 'phienBan.fileDinhKem':1}).forEach(printjson)

print('=== Nhóm và Trưởng nhóm ===')
db.nhomTieuChi.find({}, {_id:1, tenNhom:1, maTieuChuan:1, maTruongNhom:1, thanhVien:1}).forEach(printjson)

print('=== Phân công ===')
db.phanCong.find({}, {_id:1, maNhomTieuChi:1, maNguoiDung:1, vaiTroTrongNhom:1, maTieuChi:1, trangThai:1}).forEach(printjson)

print('=== Báo cáo cấu trúc 4 phần ===')
db.baoCaoTieuChi.find({}, {_id:1, maTieuChi:1, noiDungBaoCao:1, diemManh:1, diemTonTai:1, keHoachHanhDong:1}).forEach(printjson)
