// Đồng bộ phân công chính thức từ Excel - chạy trong Mongosh, database HeThongKiemDinh.
// Không xóa báo cáo/minh chứng/lịch sử. Kiểm tra đủ người, nhóm, tiêu chí trước khi ghi.
(function(){
 const dbx=db.getSiblingDB("HeThongKiemDinh");
 const groups={
  "1": {
    "leader": "ND010",
    "secretary": "ND011",
    "members": [
      "ND010",
      "ND011",
      "ND012",
      "ND013",
      "ND014",
      "ND015",
      "ND016",
      "ND044",
      "ND043",
      "ND042"
    ],
    "standards": [
      "TC1",
      "TC2"
    ]
  },
  "2": {
    "leader": "ND017",
    "secretary": "ND018",
    "members": [
      "ND017",
      "ND018",
      "ND019",
      "ND020",
      "ND021",
      "ND022",
      "ND023",
      "ND046",
      "ND045"
    ],
    "standards": [
      "TC3",
      "TC4"
    ]
  },
  "3": {
    "leader": "ND024",
    "secretary": "ND025",
    "members": [
      "ND024",
      "ND025",
      "ND026",
      "ND027",
      "ND028",
      "ND047"
    ],
    "standards": [
      "TC5"
    ]
  },
  "4": {
    "leader": "ND029",
    "secretary": "ND030",
    "members": [
      "ND029",
      "ND030",
      "ND031",
      "ND032",
      "ND033",
      "ND034",
      "ND035",
      "ND048",
      "ND049",
      "ND050"
    ],
    "standards": [
      "TC6",
      "TC7"
    ]
  },
  "5": {
    "leader": "ND036",
    "secretary": "ND037",
    "members": [
      "ND036",
      "ND037",
      "ND038",
      "ND039",
      "ND040",
      "ND041",
      "ND052",
      "ND051"
    ],
    "standards": [
      "TC8"
    ]
  }
};
 const assignments={
  "TC1.1": {
    "primary": "ND015",
    "co": [],
    "support": []
  },
  "TC1.2": {
    "primary": "ND015",
    "co": [
      "ND042"
    ],
    "support": []
  },
  "TC1.3": {
    "primary": "ND010",
    "co": [],
    "support": [
      "ND043"
    ]
  },
  "TC1.4": {
    "primary": "ND011",
    "co": [],
    "support": []
  },
  "TC1.5": {
    "primary": "ND013",
    "co": [],
    "support": []
  },
  "TC1.6": {
    "primary": "ND014",
    "co": [],
    "support": [
      "ND043"
    ]
  },
  "TC2.1": {
    "primary": "ND011",
    "co": [],
    "support": []
  },
  "TC2.2": {
    "primary": "ND013",
    "co": [
      "ND016"
    ],
    "support": [
      "ND044"
    ]
  },
  "TC2.3": {
    "primary": "ND015",
    "co": [],
    "support": [
      "ND044"
    ]
  },
  "TC2.4": {
    "primary": "ND012",
    "co": [
      "ND016"
    ],
    "support": [
      "ND042"
    ]
  },
  "TC2.5": {
    "primary": "ND014",
    "co": [],
    "support": []
  },
  "TC2.6": {
    "primary": "ND012",
    "co": [],
    "support": []
  },
  "TC2.7": {
    "primary": "ND010",
    "co": [],
    "support": []
  },
  "TC3.1": {
    "primary": "ND018",
    "co": [],
    "support": []
  },
  "TC3.2": {
    "primary": "ND017",
    "co": [
      "ND045"
    ],
    "support": []
  },
  "TC3.3": {
    "primary": "ND019",
    "co": [],
    "support": [
      "ND046"
    ]
  },
  "TC3.4": {
    "primary": "ND019",
    "co": [],
    "support": [
      "ND046"
    ]
  },
  "TC3.5": {
    "primary": "ND020",
    "co": [
      "ND021"
    ],
    "support": []
  },
  "TC4.1": {
    "primary": "ND020",
    "co": [
      "ND021"
    ],
    "support": []
  },
  "TC4.2": {
    "primary": "ND023",
    "co": [],
    "support": []
  },
  "TC4.3": {
    "primary": "ND023",
    "co": [],
    "support": []
  },
  "TC4.4": {
    "primary": "ND018",
    "co": [],
    "support": []
  },
  "TC4.5": {
    "primary": "ND022",
    "co": [],
    "support": [
      "ND045"
    ]
  },
  "TC4.6": {
    "primary": "ND022",
    "co": [],
    "support": []
  },
  "TC4.7": {
    "primary": "ND017",
    "co": [],
    "support": []
  },
  "TC5.1": {
    "primary": "ND024",
    "co": [],
    "support": []
  },
  "TC5.2": {
    "primary": "ND024",
    "co": [],
    "support": []
  },
  "TC5.3": {
    "primary": "ND025",
    "co": [],
    "support": []
  },
  "TC5.4": {
    "primary": "ND026",
    "co": [],
    "support": []
  },
  "TC5.5": {
    "primary": "ND026",
    "co": [],
    "support": [
      "ND047"
    ]
  },
  "TC5.6": {
    "primary": "ND027",
    "co": [],
    "support": []
  },
  "TC5.7": {
    "primary": "ND027",
    "co": [],
    "support": [
      "ND047"
    ]
  },
  "TC5.8": {
    "primary": "ND028",
    "co": [],
    "support": []
  },
  "TC6.1": {
    "primary": "ND029",
    "co": [],
    "support": [
      "ND048"
    ]
  },
  "TC6.2": {
    "primary": "ND029",
    "co": [],
    "support": [
      "ND048"
    ]
  },
  "TC6.3": {
    "primary": "ND033",
    "co": [],
    "support": [
      "ND048"
    ]
  },
  "TC6.4": {
    "primary": "ND033",
    "co": [],
    "support": [
      "ND048"
    ]
  },
  "TC6.5": {
    "primary": "ND031",
    "co": [],
    "support": [
      "ND049"
    ]
  },
  "TC6.6": {
    "primary": "ND031",
    "co": [],
    "support": [
      "ND049"
    ]
  },
  "TC7.1": {
    "primary": "ND032",
    "co": [],
    "support": [
      "ND049"
    ]
  },
  "TC7.2": {
    "primary": "ND032",
    "co": [],
    "support": [
      "ND049"
    ]
  },
  "TC7.3": {
    "primary": "ND030",
    "co": [],
    "support": [
      "ND050"
    ]
  },
  "TC7.4": {
    "primary": "ND030",
    "co": [],
    "support": [
      "ND050"
    ]
  },
  "TC7.5": {
    "primary": "ND034",
    "co": [],
    "support": [
      "ND050"
    ]
  },
  "TC7.6": {
    "primary": "ND034",
    "co": [],
    "support": [
      "ND050"
    ]
  },
  "TC7.7": {
    "primary": "ND035",
    "co": [],
    "support": [
      "ND050"
    ]
  },
  "TC7.8": {
    "primary": "ND035",
    "co": [],
    "support": [
      "ND050"
    ]
  },
  "TC8.1": {
    "primary": "ND040",
    "co": [
      "ND039"
    ],
    "support": []
  },
  "TC8.2": {
    "primary": "ND036",
    "co": [],
    "support": []
  },
  "TC8.3": {
    "primary": "ND038",
    "co": [],
    "support": []
  },
  "TC8.4": {
    "primary": "ND037",
    "co": [
      "ND051"
    ],
    "support": []
  },
  "TC8.5": {
    "primary": "ND041",
    "co": [],
    "support": []
  }
};
 const summaryHelpers={"TC2.2": ["ND044"], "TC2.3": ["ND044"], "TC1.3": ["ND043"], "TC1.6": ["ND043"], "TC1.2": ["ND042"], "TC3.3": ["ND046"], "TC3.4": ["ND046"], "TC3.2": ["ND045"], "TC4.5": ["ND045"], "TC5.4": ["ND047", "ND048"], "TC5.5": ["ND047"], "TC5.1": ["ND048"], "TC5.2": ["ND048"], "TC5.3": ["ND048"], "TC6.5": ["ND049"], "TC6.6": ["ND049"], "TC7.1": ["ND049"], "TC7.2": ["ND049"], "TC7.3": ["ND050"], "TC7.4": ["ND050"], "TC7.5": ["ND050"], "TC7.6": ["ND050"], "TC7.7": ["ND050"], "TC7.8": ["ND050"], "TC8.4": ["ND051"]};
 for(const [code,ids] of Object.entries(summaryHelpers)){ if(!assignments[code]) throw new Error("Tiêu chí hỗ trợ không tồn tại: "+code); const a=assignments[code]; a.support=[...new Set([...(a.support||[]),...ids])]; a.co=(a.co||[]).filter(id=>!a.support.includes(id)); }
 const standards={"TC1":"NT001","TC2":"NT001","TC3":"NT002","TC4":"NT002","TC5":"NT003","TC6":"NT004","TC7":"NT004","TC8":"NT005"};
 const ids=[...new Set([...Object.values(groups).flatMap(g=>[g.leader,g.secretary,...g.members]),...Object.values(assignments).flatMap(a=>[a.primary,...a.co,...a.support])])];
 const found=new Set(dbx.nguoiDung.find({_id:{$in:ids}},{_id:1}).toArray().map(x=>x._id));
 const missing=ids.filter(x=>!found.has(x)); if(missing.length) throw new Error("Thiếu tài khoản: "+missing.join(","));
 const tcCount=dbx.tieuChi.countDocuments({}); if(tcCount!==52) throw new Error("Số tiêu chí không phải 52: "+tcCount);
 for(const code of Object.keys(assignments)){if(!dbx.phanCong.findOne({maTieuChi:code})) throw new Error("Thiếu phân công cho "+code); if(!dbx.baoCaoTieuChi.findOne({maTieuChi:code})) print("CẢNH BÁO: chưa có báo cáo "+code);}
 for(let n=1;n<=5;n++){const g=groups[n], id="NT00"+n; if(!dbx.nhomTieuChi.findOne({_id:id})) throw new Error("Thiếu nhóm "+id);}
 // Cập nhật nhóm; người hỗ trợ/cung cấp minh chứng được thêm với vai trò rõ ràng.
 for(let n=1;n<=5;n++){const g=groups[n],id="NT00"+n; const details=g.members.map(uid=>({maNguoiDung:uid,chucVu:uid===g.leader?"Trưởng nhóm":uid===g.secretary?"Thư ký nhóm":"Thành viên / hỗ trợ"})); dbx.nhomTieuChi.updateOne({_id:id},{$set:{tenNhom:"Nhóm công tác "+n,maTruongNhom:g.leader,maThuKyNhom:g.secretary,tieuChuanPhuTrach:g.standards,thanhVien:g.members,thanhVienChiTiet:details,tieuChi:dbx.tieuChi.find({maTieuChuan:{$in:g.standards}}).sort({thuTu:1}).toArray().map(x=>x._id),nhiemVuBoSung:n===5?["CSDL kiểm định"]:[],trangThai:"Hoạt động"}});}
 // Cập nhật phân công cấp tiêu chuẩn về đúng nhóm và trưởng nhóm.
 for(let n=1;n<=8;n++){const tc="TC"+n,id="PC"+String(n).padStart(3,"0");dbx.phanCong.updateOne({_id:id},{$set:{maNguoiDung:groups[Number(standards[tc].slice(-1))].leader,maTieuChuan:tc,maNhomTieuChi:standards[tc]}});}
 // Cập nhật từng tiêu chí theo người phụ trách, đồng phụ trách và hỗ trợ đúng phân biệt Excel.
 for(const [code,a] of Object.entries(assignments)){const standard=code.split(".")[0],group=standards[standard];const pc=dbx.phanCong.findOne({maTieuChi:code});dbx.phanCong.updateOne({_id:pc._id},{$set:{maNguoiDung:a.primary,maTieuChuan:standard,maNhomTieuChi:group,maNguoiPhoiHopList:a.co,maNhanSuHoTroList:a.support,maNhanSuHoTro:a.support[0]||null}});dbx.baoCaoTieuChi.updateMany({maTieuChi:code},{$set:{maNguoiViet:a.primary}});}
 // Đồng bộ nhóm/tiêu chuẩn trên báo cáo nhóm hiện có, không thay nội dung báo cáo.
 for(let n=1;n<=5;n++){const g=groups[n],id="NT00"+n;dbx.baoCaoNhom.updateMany({maNhomTieuChi:id},{$set:{maTruongNhom:g.leader,maThuKyNhom:g.secretary}});}
 print("ĐÃ ĐỒNG BỘ 5 NHÓM, 8 TIÊU CHUẨN, 52 PHÂN CÔNG TIÊU CHÍ.");
 print("Số phân công tiêu chí: "+dbx.phanCong.countDocuments({maTieuChi:{$exists:true,$ne:null}}));
 print("Số báo cáo tiêu chí: "+dbx.baoCaoTieuChi.countDocuments({}));
 print("Số minh chứng giữ nguyên: "+dbx.minhChung.countDocuments({}));
})();
