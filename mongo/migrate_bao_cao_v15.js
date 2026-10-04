use HeThongKiemDinh;

// Sao lưu trước khi chuẩn hóa
db.baoCaoTieuChi.aggregate([{ $match: {} }, { $out: "backup_baoCaoTieuChi_v15" }]);

db.baoCaoTieuChi.find().forEach(function(bc) {
  const maTC = bc.maTieuChi || null;
  const maTChuan = bc.maTieuChuan || (maTC ? maTC.split(".")[0] : null);
  db.baoCaoTieuChi.updateOne(
    { _id: bc._id },
    {
      $set: {
        maTieuChuan: maTChuan,
        moTaHienTrang: bc.moTaHienTrang || bc.noiDungBaoCao || "",
        diemManh: bc.diemManh || "",
        diemTonTai: bc.diemTonTai || bc.diemYeu || "",
        keHoachHanhDong: bc.keHoachHanhDong || "",
        ngayTao: bc.ngayTao || new Date(),
        ngayCapNhat: new Date()
      },
      $unset: { noiDungBaoCao: "", diemYeu: "" }
    }
  );
});

print("Đã chuẩn hóa " + db.baoCaoTieuChi.countDocuments() + " báo cáo tự đánh giá.");
