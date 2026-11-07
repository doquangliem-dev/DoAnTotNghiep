db = db.getSiblingDB("HeThongKiemDinh");
function countMissing(col, field) {
    return db.getCollection(col).countDocuments({ $or: [{ [field]: { $exists: false } }, { [field]: null }, { [field]: "" }] });
}
print("=== KIEM TRA DATA/FILE HE THONG KIEM DINH ===");
printjson({
    tieuChuan: db.tieuChuan.countDocuments({}),
    tieuChi: db.tieuChi.countDocuments({}),
    minhChung: db.minhChung.countDocuments({}),
    minhChungThieuFile: countMissing("minhChung", "duongDanFile"),
    minhChungChung: db.minhChungChung.countDocuments({}),
    minhChungChungThieuFile: countMissing("minhChungChung", "duongDanFile"),
    baoCaoTieuChi: db.baoCaoTieuChi.countDocuments({}),
    baoCaoTieuChiThieuFile: countMissing("baoCaoTieuChi", "duongDanFile"),
    baoCaoNhom: db.baoCaoNhom.countDocuments({}),
    baoCaoNhomThieuFile: countMissing("baoCaoNhom", "duongDanFile"),
    baoCaoPhan: db.baoCaoPhan.countDocuments({}),
    baoCaoPhanThieuFile: countMissing("baoCaoPhan", "duongDanFile")
});
print("Bao cao tieu chi rong noi dung:", db.baoCaoTieuChi.countDocuments({ $or: [
        { moTaHienTrang: { $in: [null, ""] } }, { diemManh: { $in: [null, ""] } },
        { diemTonTai: { $in: [null, ""] } }, { keHoachHanhDong: { $in: [null, ""] } }
    ] }));
print("Bao cao nhom rong noi dung:", db.baoCaoNhom.countDocuments({ $or: [
        { moTaTongHop: { $in: [null, ""] } }, { diemManhTongHop: { $in: [null, ""] } },
        { tonTaiTongHop: { $in: [null, ""] } }, { keHoachCaiTien: { $in: [null, ""] } }
    ] }));
