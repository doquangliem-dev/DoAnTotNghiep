package com.huit.kiemdinh.service;

import com.huit.kiemdinh.model.*;
import com.huit.kiemdinh.repository.*;
import com.huit.kiemdinh.security.AuthService;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
public class HeThongService {
    private final ChuongTrinhDaoTaoRepository ctdtRepo;
    private final BoTieuChuanRepository boRepo;
    private final TieuChuanRepository tcRepo;
    private final TieuChiRepository tchiRepo;
    private final NguoiDungRepository userRepo;
    private final VaiTroRepository roleRepo;
    private final NhomTieuChiRepository nhomRepo;
    private final PhanCongRepository pcRepo;
    private final MinhChungRepository mcRepo;
    private final MinhChungChungRepository mccRepo;
    private final BaoCaoTieuChiRepository bcRepo;
    private final BaoCaoNhomRepository bcnRepo;
    private final BaoCaoTuDanhGiaRepository bctdgRepo;
    private final BaoCaoPhanRepository bcpRepo;
    private final PhanCongBaoCaoRepository pcbcRepo;
    private final DanhGiaMinhChungRepository dgmcRepo;
    private final DanhGiaTieuChiRepository dgtcRepo;
    private final KeHoachTuDanhGiaRepository khRepo;
    private final LichSuThaoTacRepository logRepo;
    private final CodeGeneratorService codes;
    private final AuthService auth;
    private final FileStorageService storage;

    public HeThongService(ChuongTrinhDaoTaoRepository ctdtRepo, BoTieuChuanRepository boRepo,
            TieuChuanRepository tcRepo, TieuChiRepository tchiRepo, NguoiDungRepository userRepo,
            VaiTroRepository roleRepo, NhomTieuChiRepository nhomRepo, PhanCongRepository pcRepo,
            MinhChungRepository mcRepo, MinhChungChungRepository mccRepo, BaoCaoTieuChiRepository bcRepo, BaoCaoNhomRepository bcnRepo,
            BaoCaoTuDanhGiaRepository bctdgRepo, BaoCaoPhanRepository bcpRepo, PhanCongBaoCaoRepository pcbcRepo,
            DanhGiaMinhChungRepository dgmcRepo, DanhGiaTieuChiRepository dgtcRepo,
            KeHoachTuDanhGiaRepository khRepo, LichSuThaoTacRepository logRepo,
            CodeGeneratorService codes, AuthService auth, FileStorageService storage){
        this.ctdtRepo=ctdtRepo; this.boRepo=boRepo; this.tcRepo=tcRepo; this.tchiRepo=tchiRepo;
        this.userRepo=userRepo; this.roleRepo=roleRepo; this.nhomRepo=nhomRepo; this.pcRepo=pcRepo;
        this.mcRepo=mcRepo; this.mccRepo=mccRepo; this.bcRepo=bcRepo; this.bcnRepo=bcnRepo; this.bctdgRepo=bctdgRepo; this.bcpRepo=bcpRepo; this.pcbcRepo=pcbcRepo; this.dgmcRepo=dgmcRepo; this.dgtcRepo=dgtcRepo;
        this.khRepo=khRepo; this.logRepo=logRepo; this.codes=codes; this.auth=auth; this.storage=storage;
    }

    public Map<String,Object> dashboard(){
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("ctdt",ctdtRepo.count()); m.put("tieuChi",tchiRepo.count()); m.put("minhChung",mcRepo.count());
        m.put("phanCong",pcRepo.count()); m.put("nguoiDung",userRepo.count()); m.put("baoCao",bcRepo.count());
        List<PhanCong> assignments=pcRepo.findAll();
        List<Map<String,Object>> standards=new ArrayList<>();
        for(TieuChuan tc:tcRepo.findAll()){
            boolean assigned=assignments.stream().anyMatch(p -> {
                if("Hủy phân công".equalsIgnoreCase(p.getTrangThai())) return false;
                if(Objects.equals(tc.getId(),p.getMaTieuChuan())) return true;
                if((p.getMaTieuChuan()==null||p.getMaTieuChuan().isBlank()) && p.getMaTieuChi()!=null){
                    TieuChi legacy=tchiRepo.findById(p.getMaTieuChi()).orElse(null);
                    return legacy!=null && Objects.equals(tc.getId(),legacy.getMaTieuChuan());
                }
                return false;
            });
            Map<String,Object> row=new LinkedHashMap<>(); row.put("ma",tc.getId()); row.put("ten",tc.getTenTieuChuan());
            // Ở cấp Admin, tiến độ phân công được tính theo Tiêu chuẩn đã giao cho Trưởng nhóm.
            row.put("phanTram",assigned?100:0); standards.add(row);
        }
        standards.sort(Comparator.comparingInt(x->standardNumber(String.valueOf(x.get("ma"))))); m.put("tieuChuanTienDo",standards);
        List<LichSuThaoTac> logs=logRepo.findAll(Sort.by(Sort.Direction.DESC,"thoiGian"));
        if(logs.size()>6) logs=logs.subList(0,6); m.put("logs",logs); return m;
    }
    private int standardNumber(String s){ try{return Integer.parseInt(s.replaceAll("\\D",""));}catch(Exception e){return 999;} }

    // ===== CTDT: không xóa; chỉnh sửa chỉ metadata, phiên bản quản lý riêng =====
    public List<ChuongTrinhDaoTao> ctdt(){return ctdtRepo.findAll();}
    public ChuongTrinhDaoTao getCtdt(String id){return ctdtRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy CTĐT"));}
    public ChuongTrinhDaoTao saveCtdt(ChuongTrinhDaoTao x){
        if(x.getNamBatDau()!=null && x.getNamBatDau()%2!=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Năm bắt đầu/phiên bản CTĐT chỉ dùng năm chẵn: 2022, 2024, 2026...");
        boolean isNew=x.getId()==null||x.getId().isBlank();
        if(isNew){x.setId(codes.nextCTDT()); x.setNgayTao(LocalDateTime.now()); if(x.getPhienBan()==null)x.setPhienBan(new ArrayList<>());}
        else{
            ChuongTrinhDaoTao old=getCtdt(x.getId());
            // Không sửa nội dung/phiên bản đã ban hành từ form thông tin chung.
            x.setPhienBan(old.getPhienBan()); x.setNgayTao(old.getNgayTao());
            // Trạng thái được quản lý theo từng phiên bản, không đổi hàng loạt từ form CTĐT.
            x.setTrangThai(old.getTrangThai());
        }
        if(x.getTrangThai()==null||x.getTrangThai().isBlank())x.setTrangThai("Đang áp dụng");
        x.setNgayCapNhat(LocalDateTime.now()); return ctdtRepo.save(x);
    }
    public ChuongTrinhDaoTao addPhienBan(String id, PhienBanCTDT pb){
        ChuongTrinhDaoTao c=getCtdt(id);
        if(c.getPhienBan()==null)c.setPhienBan(new ArrayList<>());
        if(pb.getNamHoc()==null||pb.getNamHoc().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn năm phiên bản");
        String year=pb.getNamHoc().trim();
        if(!year.matches("\\d{4}"))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phiên bản CTĐT dùng năm chẵn, ví dụ 2022, 2024, 2026");
        int versionYear=Integer.parseInt(year);
        if(versionYear%2!=0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Theo quy ước dữ liệu hiện tại, năm phiên bản CTĐT phải là năm chẵn");
        boolean exists=c.getPhienBan().stream().anyMatch(v->year.equals(v.getNamHoc()));
        if(exists) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"CTĐT đã có phiên bản năm "+year);

        // Phiên bản được nhận diện bằng năm, không hiển thị số 1,2,3...
        pb.setNamHoc(year);
        pb.setMaPhienBan("PB"+year);
        pb.setNamPhienBan(versionYear);
        pb.setSoPhienBan(c.getPhienBan().size()+1);
        if(pb.getTenPhienBan()==null||pb.getTenPhienBan().isBlank())pb.setTenPhienBan("Phiên bản "+year);
        if(pb.getTrangThai()==null||pb.getTrangThai().isBlank())pb.setTrangThai("Đang áp dụng");
        c.getPhienBan().add(pb);
        c.getPhienBan().sort(Comparator.comparing(PhienBanCTDT::getNamHoc));
        c.setNgayCapNhat(LocalDateTime.now());
        return ctdtRepo.save(c);
    }
    public ChuongTrinhDaoTao updatePhienBanTrangThai(String id, String maPhienBan, String trangThai){
        ChuongTrinhDaoTao c=getCtdt(id);
        PhienBanCTDT pb=(c.getPhienBan()==null?List.<PhienBanCTDT>of():c.getPhienBan()).stream()
                .filter(v->Objects.equals(v.getMaPhienBan(),maPhienBan)).findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phiên bản CTĐT"));
        String status=Optional.ofNullable(trangThai).orElse("").trim();
        if(!List.of("Đang áp dụng","Đã ban hành","Ngừng áp dụng").contains(status))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Trạng thái phiên bản không hợp lệ");
        pb.setTrangThai(status);
        c.setNgayCapNhat(LocalDateTime.now());
        return ctdtRepo.save(c);
    }

    public ChuongTrinhDaoTao uploadCtdt(String id,String maPhienBan,MultipartFile file){
        if(file==null||file.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn file");
        String original=Optional.ofNullable(file.getOriginalFilename()).orElse("ctdt.pdf");
        String ext=original.contains(".")?original.substring(original.lastIndexOf('.')).toLowerCase(Locale.ROOT):"";
        if(!List.of(".pdf",".doc",".docx").contains(ext))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"File CTĐT chỉ hỗ trợ PDF, DOC hoặc DOCX");
        try{
            ChuongTrinhDaoTao c=getCtdt(id);
            PhienBanCTDT pb=(c.getPhienBan()==null?List.<PhienBanCTDT>of():c.getPhienBan()).stream()
                    .filter(v->Objects.equals(v.getMaPhienBan(),maPhienBan)).findFirst()
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phiên bản CTĐT"));
            storage.deleteByWebPath(pb.getFileDinhKem());
            pb.setFileDinhKem(storage.saveCtdt(id,maPhienBan,file));
            pb.setTenFileGoc(original);
            c.setNgayCapNhat(LocalDateTime.now());
            return ctdtRepo.save(c);
        }catch(IOException e){throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Không thể lưu file CTĐT",e);}
    }

    public ChuongTrinhDaoTao deleteCtdtFile(String id,String maPhienBan){
        ChuongTrinhDaoTao c=getCtdt(id);
        PhienBanCTDT pb=(c.getPhienBan()==null?List.<PhienBanCTDT>of():c.getPhienBan()).stream()
                .filter(v->Objects.equals(v.getMaPhienBan(),maPhienBan)).findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phiên bản CTĐT"));
        deleteCtdtPhysicalFile(pb.getFileDinhKem());
        pb.setFileDinhKem(null);
        pb.setTenFileGoc(null);
        c.setNgayCapNhat(LocalDateTime.now());
        return ctdtRepo.save(c);
    }

    private void deleteCtdtPhysicalFile(String webPath){ storage.deleteByWebPath(webPath); }

    public List<BoTieuChuan> boTieuChuan(){return boRepo.findAll();}
    public BoTieuChuan saveBo(BoTieuChuan x){
        String code=codes.codeBoTieuChuan(x.getId(),x.getTenBoTieuChuan());
        x.setId(code);
        if(x.getTrangThai()==null||x.getTrangThai().isBlank())x.setTrangThai("Đang áp dụng");
        return boRepo.save(x);
    }
    public void deleteBo(String id){
        if(tcRepo.findAll().stream().anyMatch(x->Objects.equals(x.getMaBoTieuChuan(),id)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể xóa bộ tiêu chuẩn đang có tiêu chuẩn");
        if(khRepo.findAll().stream().anyMatch(x->Objects.equals(x.getMaBoTieuChuan(),id)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể xóa bộ tiêu chuẩn đang được dùng trong kế hoạch tự đánh giá");
        boRepo.deleteById(id);
    }

    public List<TieuChuan> tieuChuan(){List<TieuChuan> a=tcRepo.findAll(); a.sort(Comparator.comparingInt(x->standardNumber(x.getId()))); return a;}
    public TieuChuan saveTieuChuan(TieuChuan x){
        if(x.getMaBoTieuChuan()==null||x.getMaBoTieuChuan().isBlank()||!boRepo.existsById(x.getMaBoTieuChuan()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn bộ tiêu chuẩn hợp lệ");
        if(x.getTenTieuChuan()==null||x.getTenTieuChuan().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng nhập tên tiêu chuẩn");
        if(x.getId()==null||x.getId().isBlank())x.setId(codes.nextTieuChuan(x.getMaBoTieuChuan()));
        else{
            TieuChuan old=tcRepo.findById(x.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy tiêu chuẩn"));
            if(!Objects.equals(old.getMaBoTieuChuan(),x.getMaBoTieuChuan()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không được chuyển Tiêu chuẩn sang bộ tiêu chuẩn khác; hãy tạo Tiêu chuẩn mới");
        }
        if(x.getSoTieuChuan()==null)x.setSoTieuChuan(standardNumber(x.getId())); if(x.getThuTu()==null)x.setThuTu(x.getSoTieuChuan());
        if(x.getTrangThai()==null||x.getTrangThai().isBlank())x.setTrangThai("Đang áp dụng"); return tcRepo.save(x);
    }
    public void deleteTieuChuan(String id){
        if(tchiRepo.findAll().stream().anyMatch(x->Objects.equals(x.getMaTieuChuan(),id)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể xóa tiêu chuẩn đang có tiêu chí");
        if(pcRepo.findAll().stream().anyMatch(x->Objects.equals(x.getMaTieuChuan(),id)&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể xóa tiêu chuẩn đang có phân công");
        tcRepo.deleteById(id);
    }

    public List<TieuChi> tieuChi(){List<TieuChi>a=tchiRepo.findAll(); a.sort(Comparator.comparing(TieuChi::getId)); return a;}
    public TieuChi saveTieuChi(TieuChi x){
        if(x.getMaTieuChuan()==null||x.getMaTieuChuan().isBlank()||!tcRepo.existsById(x.getMaTieuChuan()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn tiêu chuẩn hợp lệ");
        if(x.getTenTieuChi()==null||x.getTenTieuChi().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng nhập tên tiêu chí");
        boolean isNew=x.getId()==null||x.getId().isBlank();
        if(isNew){
            TieuChuan parent=tcRepo.findById(x.getMaTieuChuan()).orElseThrow();
            if("MOET".equalsIgnoreCase(parent.getMaBoTieuChuan())){
                Map<String,Integer> limit=Map.of("TC1",6,"TC2",7,"TC3",5,"TC4",7,"TC5",8,"TC6",6,"TC7",8,"TC8",5);
                int max=limit.getOrDefault(x.getMaTieuChuan(),0);
                long current=tchiRepo.findAll().stream().filter(t->Objects.equals(t.getMaTieuChuan(),x.getMaTieuChuan())).count();
                if(max>0 && current>=max) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bộ MOET đã đủ "+max+" tiêu chí cho "+x.getMaTieuChuan());
            }
            x.setId(codes.nextTieuChi(x.getMaTieuChuan())); x.setNgayTao(LocalDateTime.now());
        }
        else{
            TieuChi current=tchiRepo.findById(x.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy tiêu chí"));
            if(!Objects.equals(current.getMaTieuChuan(),x.getMaTieuChuan())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không được chuyển Tiêu chí sang Tiêu chuẩn khác; hãy tạo Tiêu chí mới");
            tchiRepo.findById(x.getId()).ifPresent(old->{x.setNgayTao(old.getNgayTao()); if(x.getChiBao()==null||x.getChiBao().isEmpty())x.setChiBao(old.getChiBao()); if(x.getMinhChungGoiY()==null||x.getMinhChungGoiY().isEmpty())x.setMinhChungGoiY(old.getMinhChungGoiY());});
        }
        if(!List.of("Có","Không").contains(x.getTieuChiDieuKien()))x.setTieuChiDieuKien("Không"); if(x.getThuTu()==null)x.setThuTu(1); if(x.getSoTieuChi()==null)x.setSoTieuChi(x.getThuTu());
        x.setNgayCapNhat(LocalDateTime.now()); return tchiRepo.save(x);
    }
    public void deleteTieuChi(String id){
        if(pcRepo.findAll().stream().anyMatch(x->Objects.equals(x.getMaTieuChi(),id)&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể xóa tiêu chí đang có phân công");
        if(mcRepo.findAll().stream().anyMatch(x->Objects.equals(x.getMaTieuChi(),id)) || bcRepo.findAll().stream().anyMatch(x->Objects.equals(x.getMaTieuChi(),id)) || dgtcRepo.findAll().stream().anyMatch(x->Objects.equals(x.getMaTieuChi(),id)))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể xóa tiêu chí đã phát sinh minh chứng, báo cáo hoặc đánh giá");
        tchiRepo.deleteById(id);
    }

    public List<NguoiDung> nguoiDung(){return userRepo.findAll();}

    public NguoiDung saveNguoiDung(NguoiDung x){
        boolean isNew = x.getId()==null || x.getId().isBlank();
        if(x.getHoTen()==null || x.getHoTen().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng nhập họ tên");
        if(x.getEmail()==null || x.getEmail().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email đồng thời là tên đăng nhập nên không được để trống");

        String email = x.getEmail().trim().toLowerCase(Locale.ROOT);
        x.setEmail(email);
        userRepo.findByEmailIgnoreCase(email).ifPresent(existing -> {
            if(isNew || !Objects.equals(existing.getId(), x.getId()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email này đã được dùng làm tài khoản đăng nhập");
        });

        if(x.getPhanQuyen()==null || x.getPhanQuyen().isEmpty() ||
           x.getPhanQuyen().stream().allMatch(p -> p==null || p.getMaVaiTro()==null || p.getMaVaiTro().isBlank()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn vai trò cho người dùng");

        for(PhanQuyen pq : x.getPhanQuyen()) {
            if(pq!=null && pq.getMaVaiTro()!=null && !pq.getMaVaiTro().isBlank() && !roleRepo.existsById(pq.getMaVaiTro()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vai trò không tồn tại: "+pq.getMaVaiTro());
        }

        if(isNew){
            x.setId(codes.nextNguoiDung());
            if(x.getMatKhau()==null || x.getMatKhau().length()<6)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Admin phải đặt mật khẩu ban đầu tối thiểu 6 ký tự");
            x.setMatKhau(auth.encodePassword(x.getMatKhau()));
        } else {
            NguoiDung old=userRepo.findById(x.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy người dùng"));
            if(x.getMatKhau()==null || x.getMatKhau().isBlank()) x.setMatKhau(old.getMatKhau());
            else if(!auth.isEncoded(x.getMatKhau())) {
                if(x.getMatKhau().length()<6) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mật khẩu mới tối thiểu 6 ký tự");
                x.setMatKhau(auth.encodePassword(x.getMatKhau()));
            }
        }

        Set<String> newRoleNames=x.getPhanQuyen().stream().filter(Objects::nonNull).map(PhanQuyen::getMaVaiTro).filter(Objects::nonNull)
                .map(id->roleRepo.findById(id).map(VaiTro::getTenVaiTro).orElse("")).map(this::normalizeText).collect(java.util.stream.Collectors.toSet());
        boolean hasLeaderRole=newRoleNames.stream().anyMatch(r->r.contains("truong nhom"));
        boolean hasMemberRole=newRoleNames.stream().anyMatch(r->r.contains("thanh vien"));
        for(NhomTieuChi g:nhomRepo.findAll()) if(g.getThanhVienChiTiet()!=null){
            for(ThanhVienNhom tv:g.getThanhVienChiTiet()) if(tv!=null&&Objects.equals(tv.getMaNguoiDung(),x.getId())){
                if("Trưởng nhóm".equals(tv.getChucVu())&&!hasLeaderRole)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể bỏ vai trò Trưởng nhóm tiêu chí khi người dùng đang là Trưởng nhóm của "+g.getId());
                if(!"Trưởng nhóm".equals(tv.getChucVu())&&!hasMemberRole)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể bỏ vai trò Thành viên nhóm khi người dùng vẫn thuộc "+g.getId());
            }
        }
        if(x.getTrangThai()==null||x.getTrangThai().isBlank())x.setTrangThai("Hoạt động");
        return userRepo.save(x);
    }

    public NguoiDung toggleUser(String id){NguoiDung u=userRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy người dùng")); u.setTrangThai("Hoạt động".equalsIgnoreCase(u.getTrangThai())?"Khóa":"Hoạt động"); return userRepo.save(u);}
    public List<VaiTro> vaiTro(){
        // Chỉ trả một dòng cho mỗi vai trò để tránh combobox bị lặp do dữ liệu cũ.
        Map<String,VaiTro> unique=new LinkedHashMap<>();
        List<VaiTro> all=roleRepo.findAll();
        all.sort(Comparator.comparing(VaiTro::getId,Comparator.nullsLast(String::compareTo)));
        for(VaiTro v:all){
            String key=normalizeText(v.getTenVaiTro());
            if(!key.isBlank()) unique.putIfAbsent(key,v);
        }
        return new ArrayList<>(unique.values());
    }
    private String normalizeText(String s){
        if(s==null)return "";
        return java.text.Normalizer.normalize(s,java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}","").replace('đ','d').replace('Đ','D').trim().toLowerCase(Locale.ROOT);
    }

    // ===== Nhóm nhân sự: tạo nhóm, thêm nhiều thành viên và gán chức vụ trong nhóm =====
    public List<NhomTieuChi> nhomTieuChi(){
        List<NhomTieuChi> all=nhomRepo.findAll();
        for(NhomTieuChi g:all){
            boolean changed=false;
            if(g.getThanhVienChiTiet()==null) g.setThanhVienChiTiet(new ArrayList<>());
            if(g.getThanhVienChiTiet().isEmpty() && g.getThanhVien()!=null && !g.getThanhVien().isEmpty()){
                for(String id:g.getThanhVien()) g.getThanhVienChiTiet().add(new ThanhVienNhom(id,"Thành viên"));
                changed=true;
            }
            for(ThanhVienNhom tv:g.getThanhVienChiTiet()){
                if(tv!=null&&"Thư ký".equals(tv.getChucVu())){ tv.setChucVu("Thư ký nhóm"); changed=true; }
            }
            List<String> ids=g.getThanhVienChiTiet().stream().filter(Objects::nonNull).map(ThanhVienNhom::getMaNguoiDung).filter(Objects::nonNull).distinct().toList();
            if(!Objects.equals(ids,g.getThanhVien())){ g.setThanhVien(new ArrayList<>(ids)); changed=true; }
            String leader=g.getThanhVienChiTiet().stream().filter(Objects::nonNull).filter(v->"Trưởng nhóm".equals(v.getChucVu())).map(ThanhVienNhom::getMaNguoiDung).findFirst().orElse(g.getMaTruongNhom());
            String secretary=g.getThanhVienChiTiet().stream().filter(Objects::nonNull).filter(v->"Thư ký nhóm".equals(v.getChucVu())).map(ThanhVienNhom::getMaNguoiDung).findFirst().orElse(g.getMaThuKyNhom());
            if(!Objects.equals(leader,g.getMaTruongNhom())){g.setMaTruongNhom(leader);changed=true;}
            if(!Objects.equals(secretary,g.getMaThuKyNhom())){g.setMaThuKyNhom(secretary);changed=true;}
            List<String> standards=pcRepo.findAll().stream().filter(pc->Objects.equals(pc.getMaNhomTieuChi(),g.getId())&&(pc.getMaTieuChi()==null||pc.getMaTieuChi().isBlank())&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(pc.getTrangThai()).orElse(""))).map(PhanCong::getMaTieuChuan).filter(Objects::nonNull).distinct().sorted(Comparator.comparingInt(this::standardNumber)).toList();
            if(!Objects.equals(standards,g.getTieuChuanPhuTrach())){g.setTieuChuanPhuTrach(new ArrayList<>(standards));changed=true;}
            if(changed) nhomRepo.save(g);
        }
        return all;
    }

    public NhomTieuChi saveNhom(NhomTieuChi x){
        boolean isNewGroup=x.getId()==null||x.getId().isBlank();
        if(isNewGroup){
            long activeGroups=nhomRepo.findAll().stream().filter(g->"Hoạt động".equalsIgnoreCase(Optional.ofNullable(g.getTrangThai()).orElse("Hoạt động"))).count();
            if(activeGroups>=5) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Theo góp ý tuần 7, hệ thống chỉ cho tối đa 5 nhóm hoạt động");
            x.setId(codes.nextNhomTieuChi());
        }
        if(x.getTrangThai()==null||x.getTrangThai().isBlank())x.setTrangThai("Hoạt động");
        if(x.getThanhVienChiTiet()==null)x.setThanhVienChiTiet(new ArrayList<>());

        Set<String> seen=new LinkedHashSet<>();
        List<ThanhVienNhom> clean=new ArrayList<>();
        int leaderCount=0;
        int secretaryCount=0;
        for(ThanhVienNhom tv:x.getThanhVienChiTiet()){
            if(tv==null||tv.getMaNguoiDung()==null||tv.getMaNguoiDung().isBlank())continue;
            NguoiDung member=userRepo.findById(tv.getMaNguoiDung())
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Thành viên không tồn tại: "+tv.getMaNguoiDung()));
            if(!"Hoạt động".equalsIgnoreCase(member.getTrangThai()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ tài khoản đang Hoạt động mới được thêm vào nhóm");

            List<String> systemRoles = member.getPhanQuyen()==null ? List.of() : member.getPhanQuyen().stream()
                    .filter(Objects::nonNull).map(PhanQuyen::getMaVaiTro).filter(Objects::nonNull)
                    .map(id->roleRepo.findById(id).map(VaiTro::getTenVaiTro).orElse(""))
                    .filter(name->!name.isBlank()).toList();
            boolean isLeaderRole=systemRoles.stream().anyMatch(r->normalizeText(r).contains("truong nhom"));
            boolean isMemberRole=systemRoles.stream().anyMatch(r->normalizeText(r).contains("thanh vien"));
            if(!isLeaderRole && !isMemberRole)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Chỉ người có vai trò hệ thống Trưởng nhóm tiêu chí hoặc Thành viên nhóm mới được thêm vào nhóm");

            if(!seen.add(tv.getMaNguoiDung()))continue;
            String cv=Optional.ofNullable(tv.getChucVu()).orElse("Thành viên").trim();
            if("Thư ký".equals(cv)) cv="Thư ký nhóm";
            if(!List.of("Trưởng nhóm","Thư ký nhóm","Thành viên","Nhân sự hỗ trợ").contains(cv))cv="Thành viên";
            if("Trưởng nhóm".equals(cv) && !isLeaderRole)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Chức vụ Trưởng nhóm chỉ được gán cho tài khoản có vai trò hệ thống Trưởng nhóm tiêu chí");
            if(!"Trưởng nhóm".equals(cv) && !isMemberRole)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Thư ký nhóm/Thành viên phải có vai trò hệ thống Thành viên nhóm");
            if("Trưởng nhóm".equals(cv))leaderCount++;
            if("Thư ký nhóm".equals(cv))secretaryCount++;
            clean.add(new ThanhVienNhom(tv.getMaNguoiDung(),cv));
        }
        if(clean.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhóm phải có ít nhất một thành viên");
        if(leaderCount!=1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mỗi nhóm phải có đúng một Trưởng nhóm");
        if(secretaryCount!=1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mỗi nhóm phải có đúng một Thư ký nhóm");

        // Một người chỉ được thuộc một nhóm hoạt động để tránh phân công nhầm.
        // Với dữ liệu cũ đã tồn tại chồng chéo, vẫn cho phép giữ thành viên cũ khi chỉnh sửa nhóm;
        // nhưng không cho thêm mới một người đang thuộc nhóm hoạt động khác.
        Set<String> oldMemberIds=new LinkedHashSet<>();
        if(x.getId()!=null&&!x.getId().isBlank()){
            nhomRepo.findById(x.getId()).ifPresent(old->{
                if(old.getThanhVienChiTiet()!=null) old.getThanhVienChiTiet().stream()
                        .filter(Objects::nonNull).map(ThanhVienNhom::getMaNguoiDung).filter(Objects::nonNull).forEach(oldMemberIds::add);
                else if(old.getThanhVien()!=null) oldMemberIds.addAll(old.getThanhVien());
            });
        }
        for(ThanhVienNhom tv:clean){
            String uid=tv.getMaNguoiDung();
            if(oldMemberIds.contains(uid)) continue;
            for(NhomTieuChi other:nhomRepo.findAll()){
                if(Objects.equals(other.getId(),x.getId())) continue;
                if(!"Hoạt động".equalsIgnoreCase(Optional.ofNullable(other.getTrangThai()).orElse("Hoạt động"))) continue;
                boolean belongs=(other.getThanhVienChiTiet()!=null && other.getThanhVienChiTiet().stream().filter(Objects::nonNull)
                        .anyMatch(m->Objects.equals(m.getMaNguoiDung(),uid)))
                        || (other.getThanhVien()!=null && other.getThanhVien().contains(uid));
                if(belongs)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Người dùng "+uid+" đã thuộc nhóm "+other.getId()+" - "+other.getTenNhom());
            }
        }

        x.setThanhVienChiTiet(clean);
        x.setThanhVien(clean.stream().map(ThanhVienNhom::getMaNguoiDung).toList());
        x.setMaTruongNhom(clean.stream().filter(v->"Trưởng nhóm".equals(v.getChucVu())).map(ThanhVienNhom::getMaNguoiDung).findFirst().orElse(null));
        x.setMaThuKyNhom(clean.stream().filter(v->"Thư ký nhóm".equals(v.getChucVu())).map(ThanhVienNhom::getMaNguoiDung).findFirst().orElse(null));
        // Không cho thay đổi thành viên làm mồ côi phân công đang hoạt động.
        Set<String> memberIds=clean.stream().map(ThanhVienNhom::getMaNguoiDung).collect(java.util.stream.Collectors.toSet());
        List<PhanCong> activeInGroup=pcRepo.findAll().stream().filter(p->Objects.equals(p.getMaNhomTieuChi(),x.getId())&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).toList();
        Map<String,String> dutyByUser=clean.stream().collect(java.util.stream.Collectors.toMap(ThanhVienNhom::getMaNguoiDung,ThanhVienNhom::getChucVu,(a,b)->a,LinkedHashMap::new));
        for(PhanCong p:activeInGroup){
            if(p.getMaNguoiDung()!=null&&!memberIds.contains(p.getMaNguoiDung()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể xóa thành viên đang có phân công hoạt động: "+p.getMaNguoiDung());
            if(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank()){
                if(!"Trưởng nhóm".equals(dutyByUser.get(p.getMaNguoiDung())))
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể đổi chức vụ Trưởng nhóm khi người này đang phụ trách Tiêu chuẩn");
            }else if(List.of("Trưởng nhóm","Nhân sự hỗ trợ").contains(dutyByUser.get(p.getMaNguoiDung()))){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể đổi chức vụ người đang thực hiện Tiêu chí sang Trưởng nhóm/Nhân sự hỗ trợ");
            }
            if(p.getMaNhanSuHoTroList()!=null && p.getMaNhanSuHoTroList().stream().anyMatch(id->!memberIds.contains(id)))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể xóa nhân sự hỗ trợ đang có phân công hoạt động");
        }
        if(!"Hoạt động".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse("Hoạt động"))&&!activeInGroup.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể ngừng hoạt động nhóm khi vẫn còn phân công đang hoạt động");
        if(x.getTieuChi()==null)x.setTieuChi(new ArrayList<>());
        List<String> assignedStandards=pcRepo.findAll().stream().filter(p->Objects.equals(p.getMaNhomTieuChi(),x.getId())&&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).map(PhanCong::getMaTieuChuan).filter(Objects::nonNull).distinct().sorted(Comparator.comparingInt(this::standardNumber)).toList();
        x.setTieuChuanPhuTrach(new ArrayList<>(assignedStandards));
        return nhomRepo.save(x);
    }

    public void deleteNhom(String id){
        NhomTieuChi g=nhomRepo.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy nhóm"));
        boolean hasAssignment=pcRepo.findAll().stream().anyMatch(p->Objects.equals(p.getMaNhomTieuChi(),id));
        if(hasAssignment)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Không thể xóa nhóm đã được phân công Tiêu chuẩn hoặc đã phát sinh phân công công việc. Hãy giữ nhóm để bảo toàn lịch sử.");
        nhomRepo.deleteById(g.getId());
    }

    public List<PhanCong> phanCong(){
        List<PhanCong> all=pcRepo.findAll();
        for(PhanCong p:all){
            if((p.getMaTieuChuan()==null||p.getMaTieuChuan().isBlank()) && p.getMaTieuChi()!=null && !p.getMaTieuChi().isBlank())
                tchiRepo.findById(p.getMaTieuChi()).ifPresent(t->p.setMaTieuChuan(t.getMaTieuChuan()));
        }
        return all;
    }

    public PhanCong savePhanCong(PhanCong x){
        if(x.getId()!=null&&!x.getId().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phân công cấp Tiêu chuẩn không sửa trực tiếp; hãy hủy và phân công lại");
        if(x.getMaNhomTieuChi()==null||x.getMaNhomTieuChi().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phải chọn nhóm trước khi phân công");
        NhomTieuChi g=nhomRepo.findById(x.getMaNhomTieuChi())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhóm không tồn tại"));
        if(!"Hoạt động".equalsIgnoreCase(Optional.ofNullable(g.getTrangThai()).orElse("Hoạt động")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhóm đã ngừng hoạt động");
        if(x.getMaNguoiDung()==null||x.getMaNguoiDung().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn Trưởng nhóm phụ trách");
        boolean isGroupLeader=g.getThanhVienChiTiet()!=null && g.getThanhVienChiTiet().stream()
                .anyMatch(v->v!=null && Objects.equals(v.getMaNguoiDung(),x.getMaNguoiDung()) && "Trưởng nhóm".equals(v.getChucVu()));
        if(!isGroupLeader)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người phụ trách phải được gán chức vụ Trưởng nhóm trong nhóm đã chọn");
        TieuChuan std=tcRepo.findById(Optional.ofNullable(x.getMaTieuChuan()).orElse(""))
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn Tiêu chuẩn hợp lệ"));
        if(x.getMaKeHoach()==null||x.getMaKeHoach().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn kế hoạch tự đánh giá");
        KeHoachTuDanhGia plan=khRepo.findById(x.getMaKeHoach())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kế hoạch tự đánh giá không tồn tại"));
        if(!"Đã phê duyệt".equalsIgnoreCase(Optional.ofNullable(plan.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ được phân công theo kế hoạch đã được Bộ phận khảo thí phê duyệt");
        if(plan.getMaBoTieuChuan()!=null&&!plan.getMaBoTieuChuan().isBlank()&&!Objects.equals(plan.getMaBoTieuChuan(),std.getMaBoTieuChuan()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tiêu chuẩn không thuộc bộ tiêu chuẩn của kế hoạch đã chọn");
        if(x.getNgayBatDau()==null)x.setNgayBatDau(plan.getNgayBatDau()!=null?plan.getNgayBatDau():LocalDate.now());
        if(x.getNgayKetThucDuKien()==null)x.setNgayKetThucDuKien(plan.getNgayKetThuc());
        if(plan.getNgayBatDau()!=null&&x.getNgayBatDau().isBefore(plan.getNgayBatDau()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ngày bắt đầu phân công không được trước ngày bắt đầu kế hoạch");
        if(plan.getNgayKetThuc()!=null&&x.getNgayKetThucDuKien()!=null&&x.getNgayKetThucDuKien().isAfter(plan.getNgayKetThuc()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Hạn phân công không được sau ngày kết thúc kế hoạch");
        boolean duplicate=pcRepo.findAll().stream().anyMatch(p->Objects.equals(p.getMaKeHoach(),x.getMaKeHoach())
                &&Objects.equals(p.getMaTieuChuan(),x.getMaTieuChuan())&&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())
                &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")));
        if(duplicate) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tiêu chuẩn này đã được phân công trong kế hoạch đã chọn");
        if(x.getNgayBatDau()!=null&&x.getNgayKetThucDuKien()!=null&&x.getNgayKetThucDuKien().isBefore(x.getNgayBatDau()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Hạn hoàn thành không được trước ngày bắt đầu");
        // Nhóm không bị khóa vào một Tiêu chuẩn. Tối đa 5 nhóm có thể chia nhau phụ trách 8 Tiêu chuẩn.
        x.setId(codes.nextPhanCong());
        x.setMaTieuChi(null);
        x.setVaiTroTrongNhom("Trưởng nhóm");
        x.setTrangThai("Đã phân công");
        PhanCong saved=pcRepo.save(x);
        syncGroupStandards(x.getMaNhomTieuChi());
        return saved;
    }
    public PhanCong cancelPhanCong(String id){
        PhanCong p=pcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phân công"));
        if("Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))) return p;
        p.setTrangThai("Hủy phân công");
        PhanCong saved=pcRepo.save(p);
        // Hủy phân công cấp Tiêu chuẩn thì các phân công Tiêu chí con cùng kế hoạch/nhóm/tiêu chuẩn cũng phải dừng.
        if(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank()){
            for(PhanCong child:pcRepo.findAll()){
                if(child.getMaTieuChi()!=null&&!child.getMaTieuChi().isBlank()
                        &&Objects.equals(child.getMaKeHoach(),p.getMaKeHoach())
                        &&Objects.equals(child.getMaNhomTieuChi(),p.getMaNhomTieuChi())
                        &&Objects.equals(child.getMaTieuChuan(),p.getMaTieuChuan())
                        &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(child.getTrangThai()).orElse(""))){
                    child.setTrangThai("Hủy phân công"); pcRepo.save(child);
                }
            }
        }
        syncGroupStandards(p.getMaNhomTieuChi());
        return saved;
    }

    private void syncGroupStandards(String groupId){
        if(groupId==null||groupId.isBlank())return;
        nhomRepo.findById(groupId).ifPresent(g->{
            List<String> standards=pcRepo.findAll().stream().filter(pc->Objects.equals(pc.getMaNhomTieuChi(),groupId)&&(pc.getMaTieuChi()==null||pc.getMaTieuChi().isBlank())&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(pc.getTrangThai()).orElse(""))).map(PhanCong::getMaTieuChuan).filter(Objects::nonNull).distinct().sorted(Comparator.comparingInt(this::standardNumber)).toList();
            g.setTieuChuanPhuTrach(new ArrayList<>(standards));nhomRepo.save(g);
        });
    }

    // ===== Minh chứng chia 2 loại =====
    public List<MinhChung> minhChung(String loai){
        List<MinhChung> a=mcRepo.findAll();
        // Tự chuẩn hóa dữ liệu minh chứng cũ để combobox Tiêu chuẩn/Tiêu chí lọc đúng.
        for(MinhChung x:a){
            if("CHUNG".equalsIgnoreCase(x.getLoaiMinhChung()))continue;
            boolean changed=false;
            if(x.getLoaiMinhChung()==null||x.getLoaiMinhChung().isBlank()){x.setLoaiMinhChung("TIEU_CHI");changed=true;}
            if("Chờ duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))){x.setTrangThai("Chờ Trưởng nhóm duyệt");changed=true;}
            if((x.getMaTieuChi()==null||x.getMaTieuChi().isBlank()) && x.getMaMinhChung()!=null){
                String code=x.getMaMinhChung().trim();
                int pos=code.lastIndexOf('.');
                if(pos>0){
                    String candidate=code.substring(0,pos);
                    // Tuần 7: H chỉ dùng cho mã minh chứng. Ví dụ H2.3.01 -> tiêu chí TC2.3.
                    if(candidate.matches("(?i)H\\d+(?:\\.\\d+)+")) candidate="TC"+candidate.substring(1);
                    if(tchiRepo.existsById(candidate)){x.setMaTieuChi(candidate);changed=true;}
                }
            }
            if((x.getMaTieuChuan()==null||x.getMaTieuChuan().isBlank()) && x.getMaTieuChi()!=null){
                TieuChi tc=tchiRepo.findById(x.getMaTieuChi()).orElse(null);
                if(tc!=null){x.setMaTieuChuan(tc.getMaTieuChuan());changed=true;}
            }
            if((x.getMaKeHoach()==null||x.getMaKeHoach().isBlank()) && x.getMaPhanCong()!=null && !x.getMaPhanCong().isBlank()){
                PhanCong pc=pcRepo.findById(x.getMaPhanCong()).orElse(null);
                if(pc!=null && pc.getMaKeHoach()!=null && !pc.getMaKeHoach().isBlank()){x.setMaKeHoach(pc.getMaKeHoach());changed=true;}
            }
            if(changed)mcRepo.save(x);
        }
        if(loai==null||loai.isBlank())return a;
        return a.stream().filter(x->loai.equalsIgnoreCase(Optional.ofNullable(x.getLoaiMinhChung()).orElse("TIEU_CHI"))).toList();
    }
    public MinhChung saveMinhChung(MinhChung x){
        boolean isNew=x.getId()==null||x.getId().isBlank();
        MinhChung old=null;
        if(isNew){
            x.setId(codes.nextMinhChungId());
            if(x.getNgayUpload()==null)x.setNgayUpload(LocalDateTime.now());
        }else{
            old=mcRepo.findById(x.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng"));
            // Các trường định danh/quy trình do backend quản lý; màn hình quản trị chỉ sửa metadata.
            x.setLoaiMinhChung(old.getLoaiMinhChung());
            x.setMaMinhChung(old.getMaMinhChung());
            x.setMaTieuChuan(old.getMaTieuChuan());
            x.setMaTieuChi(old.getMaTieuChi());
            x.setMaPhanCong(old.getMaPhanCong());
            x.setMaKeHoach(old.getMaKeHoach());
            x.setTrangThai(old.getTrangThai());
            x.setMaNguoiUpload(old.getMaNguoiUpload());
            x.setDuongDanFile(old.getDuongDanFile());
            x.setLoaiFile(old.getLoaiFile());
            x.setKichThuoc(old.getKichThuoc());
            x.setNgayUpload(old.getNgayUpload());
        }
        String loai=Optional.ofNullable(x.getLoaiMinhChung()).orElse("TIEU_CHI"); x.setLoaiMinhChung(loai);
        if("CHUNG".equalsIgnoreCase(loai)){
            x.setMaTieuChi(null); x.setMaTieuChuan(null); x.setMaPhanCong(null); x.setMaKeHoach(null); x.setMaMinhChung(null); x.setTrangThai(null);
            if(x.getCapMinhChung()==null||x.getCapMinhChung().isBlank())x.setCapMinhChung("Cấp trường");
        }else{
            if(x.getMaTieuChi()==null||x.getMaTieuChi().isBlank())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng tiêu chí phải chọn tiêu chí");
            TieuChi tc=tchiRepo.findById(x.getMaTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tiêu chí không tồn tại")); x.setMaTieuChuan(tc.getMaTieuChuan());
            if(x.getMaMinhChung()==null||x.getMaMinhChung().isBlank())x.setMaMinhChung(codes.nextMaMinhChung(x.getMaTieuChi()));
            if(x.getTrangThai()==null||x.getTrangThai().isBlank())x.setTrangThai("Chờ Trưởng nhóm duyệt");
        }
        return mcRepo.save(x);
    }
    public void deleteMinhChung(String id){MinhChung x=mcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng")); if("CHUNG".equalsIgnoreCase(x.getLoaiMinhChung()))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng chung không xóa trực tiếp; chỉ cập nhật thông tin nếu cần"); mcRepo.deleteById(id);}
    public MinhChung uploadMinhChung(String id, MultipartFile file){
        if(file==null||file.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn file minh chứng");
        MinhChung mc=mcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng"));
        if("Đã duyệt".equalsIgnoreCase(Optional.ofNullable(mc.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng đã duyệt và đã được khóa file");
        String original=Optional.ofNullable(file.getOriginalFilename()).orElse("minhchung.bin");
        String ext=original.contains(".")?original.substring(original.lastIndexOf('.')).toLowerCase(Locale.ROOT):"";
        List<String> allow=List.of(".pdf",".doc",".docx",".xls",".xlsx",".png",".jpg",".jpeg");
        if(!allow.contains(ext))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ hỗ trợ PDF, Word, Excel và ảnh PNG/JPG");
        try{
            removeEvidencePhysicalFile(mc.getDuongDanFile());
            mc.setDuongDanFile(storage.saveMinhChung(id,file));
            mc.setLoaiFile(ext.startsWith(".")?ext.substring(1):ext);
            mc.setKichThuoc(file.getSize());
            mc.setNgayUpload(LocalDateTime.now());
            return mcRepo.save(mc);
        }catch(IOException e){throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Không thể lưu file minh chứng",e);}
    }
    public MinhChung deleteMinhChungFile(String id){
        MinhChung mc=mcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng"));
        if("Đã duyệt".equalsIgnoreCase(Optional.ofNullable(mc.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng đã duyệt và đã được khóa file");
        removeEvidencePhysicalFile(mc.getDuongDanFile());
        mc.setDuongDanFile(null); mc.setLoaiFile(null); mc.setKichThuoc(null);
        return mcRepo.save(mc);
    }
    private void removeEvidencePhysicalFile(String path){ storage.deleteByWebPath(path); }

    // ===== Minh chứng chung: lưu ở collection riêng minhChungChung =====
    public List<MinhChungChung> minhChungChung(){
        List<MinhChungChung> a=mccRepo.findAll();
        a.sort(Comparator.comparing(MinhChungChung::getId, Comparator.nullsLast(String::compareTo)));
        return a;
    }
    public MinhChungChung saveMinhChungChung(MinhChungChung x){
        boolean isNew=x.getId()==null||x.getId().isBlank();
        MinhChungChung old=null;
        if(isNew){
            x.setId(codes.nextMinhChungChung());
            x.setMaMinhChung(x.getId());
            x.setNgayTao(LocalDateTime.now());
        }else{
            old=mccRepo.findById(x.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng cấp trường/khoa"));
            if(x.getMaMinhChung()==null||x.getMaMinhChung().isBlank())x.setMaMinhChung(old.getMaMinhChung()!=null?old.getMaMinhChung():x.getId());
            if(x.getDuongDanFile()==null)x.setDuongDanFile(old.getDuongDanFile());
            if(x.getTenFile()==null)x.setTenFile(old.getTenFile());
            if(x.getLoaiFile()==null)x.setLoaiFile(old.getLoaiFile());
            if(x.getMaNguoiTao()==null||x.getMaNguoiTao().isBlank())x.setMaNguoiTao(old.getMaNguoiTao());
            x.setNgayTao(old.getNgayTao());
        }
        if(x.getTenMinhChung()==null||x.getTenMinhChung().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng nhập tên minh chứng");
        String cap=Optional.ofNullable(x.getCapQuanLy()).orElse("Cấp Trường");
        if(!List.of("Cấp Trường","Cấp Khoa").contains(cap))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cấp minh chứng chỉ gồm Cấp Trường hoặc Cấp Khoa");
        x.setCapQuanLy(cap);
        x.setNgayCapNhat(LocalDateTime.now());
        return mccRepo.save(x);
    }
    public MinhChungChung uploadMinhChungChung(String id, MultipartFile file){
        if(file==null||file.isEmpty())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn file minh chứng chung");
        MinhChungChung mc=mccRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng chung"));
        String original=Optional.ofNullable(file.getOriginalFilename()).orElse("minhchungchung.bin");
        String ext=original.contains(".")?original.substring(original.lastIndexOf('.')).toLowerCase(Locale.ROOT):"";
        List<String> allow=List.of(".pdf",".doc",".docx",".xls",".xlsx",".png",".jpg",".jpeg");
        if(!allow.contains(ext))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ hỗ trợ PDF, Word, Excel và ảnh PNG/JPG");
        try{
            storage.deleteByWebPath(mc.getDuongDanFile());
            mc.setDuongDanFile(storage.saveMinhChungChung(id,file));
            mc.setTenFile(original);
            mc.setLoaiFile(ext.startsWith(".")?ext.substring(1):ext);
            mc.setNgayCapNhat(LocalDateTime.now());
            return mccRepo.save(mc);
        }catch(IOException e){throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Không thể lưu file minh chứng chung",e);}
    }
    public MinhChungChung deleteMinhChungChungFile(String id){
        MinhChungChung mc=mccRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng chung"));
        storage.deleteByWebPath(mc.getDuongDanFile());
        mc.setDuongDanFile(null); mc.setTenFile(null); mc.setLoaiFile(null); mc.setNgayCapNhat(LocalDateTime.now());
        return mccRepo.save(mc);
    }
    public void deleteMinhChungChung(String id){
        MinhChungChung mc=mccRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng cấp trường/khoa"));
        storage.deleteByWebPath(mc.getDuongDanFile());
        mccRepo.deleteById(id);
    }



    // Báo cáo vẫn có API lưu cho vai trò thực hiện; màn hình Admin chỉ xem danh sách/chi tiết.
    public List<BaoCaoTieuChi> baoCao(){
        List<BaoCaoTieuChi> rows=bcRepo.findAll();
        rows.forEach(this::normalizeBaoCao);
        return rows;
    }
    public BaoCaoTieuChi getBaoCao(String id){
        BaoCaoTieuChi x=bcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy báo cáo"));
        normalizeBaoCao(x);
        return x;
    }
    public BaoCaoTieuChi saveBaoCao(BaoCaoTieuChi x){
        LocalDateTime now=LocalDateTime.now();
        BaoCaoTieuChi old=null;
        if(x.getId()!=null&&!x.getId().isBlank()) old=bcRepo.findById(x.getId()).orElse(null);
        if(x.getId()==null||x.getId().isBlank())x.setId(codes.nextBaoCao());
        if(x.getTrangThai()==null||x.getTrangThai().isBlank())x.setTrangThai("Đang soạn");
        if((x.getNhanDeTieuChi()==null||x.getNhanDeTieuChi().isBlank())&&x.getMaTieuChi()!=null)
            tchiRepo.findById(x.getMaTieuChi()).ifPresent(t->x.setNhanDeTieuChi(t.getTenTieuChi()));
        if((x.getMaTieuChuan()==null||x.getMaTieuChuan().isBlank())&&x.getMaTieuChi()!=null)
            tchiRepo.findById(x.getMaTieuChi()).ifPresent(t->x.setMaTieuChuan(t.getMaTieuChuan()));

        // Tương thích dữ liệu cũ rồi chuẩn hóa về 4 phần mới.
        if((x.getMoTaHienTrang()==null||x.getMoTaHienTrang().isBlank()) && x.getNoiDungBaoCao()!=null)
            x.setMoTaHienTrang(x.getNoiDungBaoCao());
        if((x.getDiemTonTai()==null||x.getDiemTonTai().isBlank()) && x.getDiemYeu()!=null)
            x.setDiemTonTai(x.getDiemYeu());
        x.setNoiDungBaoCao(null);
        x.setDiemYeu(null);

        if(old!=null){
            if("Đã duyệt".equalsIgnoreCase(Optional.ofNullable(old.getTrangThai()).orElse("")))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo đã duyệt và đã được khóa nội dung");
            if(x.getNgayTao()==null)x.setNgayTao(old.getNgayTao());
            if((x.getMaPhanCong()==null||x.getMaPhanCong().isBlank()))x.setMaPhanCong(old.getMaPhanCong());
            if((x.getMaKeHoach()==null||x.getMaKeHoach().isBlank()))x.setMaKeHoach(old.getMaKeHoach());
            x.setMaNguoiViet(old.getMaNguoiViet());
            x.setMucDoDuyet(old.getMucDoDuyet());
            x.setCapDuyetHienTai(old.getCapDuyetHienTai());
            x.setDuongDanFile(old.getDuongDanFile());
            x.setTenFileBaoCao(old.getTenFileBaoCao());
            if(x.getDuyetBaoCao()==null||x.getDuyetBaoCao().isEmpty())x.setDuyetBaoCao(old.getDuyetBaoCao());
        }
        if(x.getNgayTao()==null)x.setNgayTao(now);
        x.setNgayCapNhat(now);
        BaoCaoTieuChi saved=bcRepo.save(x);
        normalizeBaoCao(saved);
        return saved;
    }

    private void normalizeBaoCao(BaoCaoTieuChi x){
        if(x==null)return;
        if((x.getMoTaHienTrang()==null||x.getMoTaHienTrang().isBlank()) && x.getNoiDungBaoCao()!=null)
            x.setMoTaHienTrang(x.getNoiDungBaoCao());
        if((x.getDiemTonTai()==null||x.getDiemTonTai().isBlank()) && x.getDiemYeu()!=null)
            x.setDiemTonTai(x.getDiemYeu());
        if((x.getMaTieuChuan()==null||x.getMaTieuChuan().isBlank()) && x.getMaTieuChi()!=null){
            TieuChi tc=tchiRepo.findById(x.getMaTieuChi()).orElse(null);
            if(tc!=null)x.setMaTieuChuan(tc.getMaTieuChuan());
            else{String[] a=x.getMaTieuChi().split("\\."); if(a.length>0)x.setMaTieuChuan(a[0]);}
        }
        if((x.getMaKeHoach()==null||x.getMaKeHoach().isBlank()) && x.getMaPhanCong()!=null && !x.getMaPhanCong().isBlank()){
            PhanCong pc=pcRepo.findById(x.getMaPhanCong()).orElse(null);
            if(pc!=null)x.setMaKeHoach(pc.getMaKeHoach());
        }
        if(x.getMucDoDuyet()==null){
            String st=Optional.ofNullable(x.getTrangThai()).orElse("");
            x.setMucDoDuyet("Đã duyệt".equalsIgnoreCase(st)?100:25);
        }
        if(x.getCapDuyetHienTai()==null||x.getCapDuyetHienTai().isBlank()) x.setCapDuyetHienTai("Thành viên");
    }


    // ===== BÁO CÁO NHÓM / THƯ KÝ NHÓM =====
    private boolean isSecretaryOfGroup(NhomTieuChi g,String userId){
        if(g==null||userId==null)return false;
        if(Objects.equals(g.getMaThuKyNhom(),userId))return true;
        return g.getThanhVienChiTiet()!=null && g.getThanhVienChiTiet().stream().filter(Objects::nonNull)
                .anyMatch(v->Objects.equals(v.getMaNguoiDung(),userId) && List.of("Thư ký nhóm","Thư ký").contains(v.getChucVu()));
    }
    private boolean isLeaderOfGroup(NhomTieuChi g,String userId){
        if(g==null||userId==null)return false;
        if(Objects.equals(g.getMaTruongNhom(),userId))return true;
        return g.getThanhVienChiTiet()!=null && g.getThanhVienChiTiet().stream().filter(Objects::nonNull)
                .anyMatch(v->Objects.equals(v.getMaNguoiDung(),userId) && "Trưởng nhóm".equals(v.getChucVu()));
    }
    public List<BaoCaoNhom> baoCaoNhom(){
        List<BaoCaoNhom> rows=bcnRepo.findAll();
        rows.sort(Comparator.comparing(BaoCaoNhom::getId,Comparator.nullsLast(String::compareTo)));
        return rows;
    }
    public List<BaoCaoNhom> baoCaoNhomCuaThuKy(String userId){
        Set<String> groupIds=nhomTieuChi().stream().filter(g->isSecretaryOfGroup(g,userId)).map(NhomTieuChi::getId).collect(java.util.stream.Collectors.toSet());
        return baoCaoNhom().stream().filter(x->groupIds.contains(x.getMaNhomTieuChi())).toList();
    }
    public List<PhanCong> phanCongNhomCuaThuKy(String userId){
        Set<String> groupIds=nhomTieuChi().stream().filter(g->isSecretaryOfGroup(g,userId)).map(NhomTieuChi::getId).collect(java.util.stream.Collectors.toSet());
        return pcRepo.findAll().stream().filter(p->groupIds.contains(p.getMaNhomTieuChi()))
                .filter(p->!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).toList();
    }
    public List<BaoCaoNhom> baoCaoNhomCuaTruongNhom(String userId){
        Set<String> groupIds=nhomTieuChi().stream().filter(g->isLeaderOfGroup(g,userId)).map(NhomTieuChi::getId).collect(java.util.stream.Collectors.toSet());
        return baoCaoNhom().stream().filter(x->groupIds.contains(x.getMaNhomTieuChi())).toList();
    }
    private String nextBaoCaoNhomId(){
        int max=0;
        for(BaoCaoNhom x:bcnRepo.findAll()) if(x.getId()!=null){
            try{max=Math.max(max,Integer.parseInt(x.getId().replaceAll("\\D","")));}catch(Exception ignored){}
        }
        return String.format("BCN%03d",max+1);
    }
    public BaoCaoNhom saveBaoCaoNhomThuKy(BaoCaoNhom input,String userId){
        if(input.getMaNhomTieuChi()==null||input.getMaNhomTieuChi().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn nhóm");
        if(input.getMaKeHoach()==null||input.getMaKeHoach().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn kế hoạch");
        if(input.getMaTieuChuan()==null||input.getMaTieuChuan().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn tiêu chuẩn cần tổng hợp");
        NhomTieuChi g=nhomRepo.findById(input.getMaNhomTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy nhóm"));
        if(!isSecretaryOfGroup(g,userId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không phải Thư ký nhóm này");
        final String requestedPlan=input.getMaKeHoach().trim(), requestedStandard=input.getMaTieuChuan().trim();
        boolean assigned=pcRepo.findAll().stream().anyMatch(p->Objects.equals(p.getMaNhomTieuChi(),g.getId())
                && Objects.equals(p.getMaKeHoach(),requestedPlan) && Objects.equals(p.getMaTieuChuan(),requestedStandard)
                && (p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())
                && !"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")));
        if(!assigned) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhóm chưa được Admin phân tiêu chuẩn "+requestedStandard+" trong kế hoạch "+requestedPlan);
        String plan=requestedPlan, standard=requestedStandard;
        BaoCaoNhom x;
        if(input.getId()==null||input.getId().isBlank()){
            boolean duplicate=bcnRepo.findAll().stream().anyMatch(r->Objects.equals(r.getMaNhomTieuChi(),g.getId())&&Objects.equals(r.getMaKeHoach(),requestedPlan)&&Objects.equals(r.getMaTieuChuan(),requestedStandard));
            if(duplicate) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhóm đã có báo cáo tổng hợp cho tiêu chuẩn này trong kế hoạch đã chọn");
            x=new BaoCaoNhom(); x.setId(nextBaoCaoNhomId()); x.setNgayTao(LocalDateTime.now());
        }else{
            x=bcnRepo.findById(input.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy báo cáo nhóm"));
            if(!Objects.equals(x.getMaNhomTieuChi(),g.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Báo cáo không thuộc nhóm của bạn");
            if(!List.of("Đang tổng hợp","Yêu cầu chỉnh sửa").contains(Optional.ofNullable(x.getTrangThai()).orElse("Đang tổng hợp")))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo đang chờ/đã được duyệt nên không thể sửa");
            plan=x.getMaKeHoach(); standard=x.getMaTieuChuan();
        }
        x.setMaKeHoach(plan); x.setMaNhomTieuChi(g.getId()); x.setMaTieuChuan(standard);
        x.setMaThuKyNhom(userId); x.setMaTruongNhom(g.getMaTruongNhom());
        x.setTenBaoCao(input.getTenBaoCao()==null||input.getTenBaoCao().isBlank()?"Báo cáo tổng hợp "+standard+" - "+g.getTenNhom():input.getTenBaoCao().trim());
        x.setMoTaTongHop(input.getMoTaTongHop()); x.setDiemManhTongHop(input.getDiemManhTongHop());
        x.setTonTaiTongHop(input.getTonTaiTongHop()); x.setKeHoachCaiTien(input.getKeHoachCaiTien());
        final String reportPlan=plan, reportStandard=standard;
        Set<String> criterionIds=pcRepo.findAll().stream()
                .filter(p->Objects.equals(p.getMaNhomTieuChi(),g.getId())&&Objects.equals(p.getMaKeHoach(),reportPlan)
                        &&Objects.equals(p.getMaTieuChuan(),reportStandard)&&p.getMaTieuChi()!=null&&!p.getMaTieuChi().isBlank()
                        &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")))
                .map(PhanCong::getMaTieuChi).collect(java.util.stream.Collectors.toSet());
        List<String> criterionReports=bcRepo.findAll().stream()
                .filter(b->Objects.equals(b.getMaKeHoach(),reportPlan)&&criterionIds.contains(b.getMaTieuChi()))
                .map(BaoCaoTieuChi::getId).filter(Objects::nonNull).toList();
        x.setDanhSachBaoCaoTieuChi(new ArrayList<>(criterionReports)); x.setTrangThai("Đang tổng hợp"); x.setNgayCapNhat(LocalDateTime.now());
        return bcnRepo.save(x);
    }
    public BaoCaoNhom guiBaoCaoNhom(String id,String userId){
        BaoCaoNhom x=bcnRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy báo cáo nhóm"));
        NhomTieuChi g=nhomRepo.findById(x.getMaNhomTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy nhóm"));
        if(!isSecretaryOfGroup(g,userId))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không phải Thư ký nhóm này");
        if(!List.of("Đang tổng hợp","Yêu cầu chỉnh sửa").contains(Optional.ofNullable(x.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Trạng thái báo cáo không cho phép gửi duyệt");
        if(Stream.of(x.getMoTaTongHop(),x.getDiemManhTongHop(),x.getTonTaiTongHop(),x.getKeHoachCaiTien()).anyMatch(v->v==null||v.isBlank()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo nhóm phải có đủ Mô tả tổng hợp, Điểm mạnh, Tồn tại và Kế hoạch cải tiến");
        Map<String,Object> source=nguonBaoCaoNhom(userId,x.getMaNhomTieuChi(),x.getMaKeHoach(),x.getMaTieuChuan());
        if(!Boolean.TRUE.equals(source.get("duDieuKien")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chưa thể gửi báo cáo nhóm: tất cả báo cáo tiêu chí thuộc tiêu chuẩn phải được Trưởng nhóm duyệt tối thiểu 50%");
        x.setTrangThai("Chờ Trưởng nhóm duyệt"); x.setNgayCapNhat(LocalDateTime.now()); return bcnRepo.save(x);
    }
    public BaoCaoNhom truongNhomDuyetBaoCaoNhom(String id,String ketQua,String nhanXet,String userId){
        BaoCaoNhom x=bcnRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy báo cáo nhóm"));
        NhomTieuChi g=nhomRepo.findById(x.getMaNhomTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy nhóm"));
        if(!isLeaderOfGroup(g,userId))throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không phải Trưởng nhóm của báo cáo này");
        if(!"Chờ Trưởng nhóm duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ báo cáo Chờ Trưởng nhóm duyệt mới được xử lý");
        if(!List.of("Đã duyệt","Yêu cầu chỉnh sửa").contains(ketQua))throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả không hợp lệ");
        x.setTrangThai(ketQua); x.setNhanXetTruongNhom(nhanXet); x.setNgayCapNhat(LocalDateTime.now()); return bcnRepo.save(x);
    }

    // ===== NHÓM HỘI ĐỒNG TỰ ĐÁNH GIÁ =====
    public Map<String,Object> hoiDongDashboard(){
        Map<String,Object> m=new LinkedHashMap<>();
        List<MinhChung> mcs=mcRepo.findAll();
        List<BaoCaoTieuChi> bcs=baoCaoHoiDong();
        List<DanhGiaTieuChi> dgs=dgtcRepo.findAll();
        m.put("tongTieuChi",tchiRepo.count());
        m.put("tongMinhChung",mcs.size());
        m.put("minhChungDaDuyet",mcs.stream().filter(x->"TIEU_CHI".equalsIgnoreCase(Optional.ofNullable(x.getLoaiMinhChung()).orElse("TIEU_CHI")) && "Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());
        m.put("baoCao",bcs.size());
        m.put("baoCaoChoDuyet",bcs.stream().filter(x->"Chờ Hội đồng cấp khoa duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());
        m.put("baoCaoHoanThanh",bcs.stream().filter(x->"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());
        Map<String,DanhGiaTieuChi> latest=latestEvaluations(dgs);
        m.put("tieuChiDaDanhGia",latest.size());
        m.put("tieuChiDat",latest.values().stream().filter(x->"Đạt".equalsIgnoreCase(x.getKetQua())).count());
        m.put("tieuChiKhongDat",latest.values().stream().filter(x->"Không đạt".equalsIgnoreCase(x.getKetQua())).count());
        m.put("tongHop",tongHopHoiDong(null).get("tieuChuan"));
        return m;
    }

    public List<BaoCaoTieuChi> baoCaoHoiDong(){
        Set<String> approvedPlans=khRepo.findAll().stream().filter(k->"Đã phê duyệt".equalsIgnoreCase(Optional.ofNullable(k.getTrangThai()).orElse("")))
                .map(KeHoachTuDanhGia::getId).collect(java.util.stream.Collectors.toSet());
        return baoCao().stream()
                .filter(x->List.of("Chờ Hội đồng cấp khoa duyệt","Chờ TT QLCL duyệt","Đã duyệt","Yêu cầu chỉnh sửa").contains(Optional.ofNullable(x.getTrangThai()).orElse("")))
                .filter(x->x.getMaKeHoach()==null||x.getMaKeHoach().isBlank()||approvedPlans.contains(x.getMaKeHoach())).toList();
    }
    public List<BaoCaoNhom> baoCaoNhomHoiDong(){
        return baoCaoNhom().stream().filter(x->"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).toList();
    }

    public List<MinhChung> minhChungHoiDong(){
        return minhChung("TIEU_CHI").stream()
                .filter(x->"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse("")))
                .toList();
    }

    public List<DanhGiaMinhChung> danhGiaMinhChung(){
        List<DanhGiaMinhChung> a=dgmcRepo.findAll();
        a.sort(Comparator.comparing(DanhGiaMinhChung::getNgayDuyet, Comparator.nullsLast(Comparator.reverseOrder())));
        return a;
    }

    public List<DanhGiaTieuChi> danhGiaTieuChi(){
        List<DanhGiaTieuChi> rows=dgtcRepo.findAll();
        rows.sort(Comparator.comparing(DanhGiaTieuChi::getNgayDanhGia,Comparator.nullsLast(Comparator.reverseOrder())));
        return rows;
    }

    public DanhGiaTieuChi saveDanhGiaTieuChi(DanhGiaTieuChi x,String userId){
        if(x.getMaTieuChi()==null||x.getMaTieuChi().isBlank()||!tchiRepo.existsById(x.getMaTieuChi()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn tiêu chí hợp lệ");
        if(!List.of("Đạt","Không đạt").contains(x.getKetQua()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả chỉ nhận Đạt hoặc Không đạt");
        if(x.getMaKeHoach()==null||x.getMaKeHoach().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn kế hoạch tự đánh giá trước khi đánh giá tiêu chí");
        KeHoachTuDanhGia evalPlan=khRepo.findById(x.getMaKeHoach())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kế hoạch tự đánh giá không tồn tại"));
        if(!"Đã phê duyệt".equalsIgnoreCase(Optional.ofNullable(evalPlan.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ được đánh giá tiêu chí thuộc kế hoạch đã phê duyệt");

        boolean reportApproved=bcRepo.findAll().stream().anyMatch(b->Objects.equals(b.getMaTieuChi(),x.getMaTieuChi())
                && (x.getMaKeHoach()==null||x.getMaKeHoach().isBlank()||Objects.equals(x.getMaKeHoach(),b.getMaKeHoach()))
                && "Đã duyệt".equalsIgnoreCase(Optional.ofNullable(b.getTrangThai()).orElse("")));
        if(!reportApproved)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ được đánh giá khi báo cáo tiêu chí đã hoàn tất cấp TT QLCL (100%)");

        List<DanhGiaTieuChi> old=dgtcRepo.findAll().stream().filter(d->Objects.equals(d.getMaTieuChi(),x.getMaTieuChi())
                && Objects.equals(Optional.ofNullable(d.getMaKeHoach()).orElse(""),Optional.ofNullable(x.getMaKeHoach()).orElse(""))).toList();
        int next=old.stream().map(DanhGiaTieuChi::getLanDanhGia).filter(Objects::nonNull).max(Integer::compareTo).orElse(0)+1;
        x.setId(codes.nextDanhGiaTieuChi());
        x.setLanDanhGia(next);
        x.setMaNguoiDanhGia(userId);
        x.setNgayDanhGia(LocalDateTime.now());
        return dgtcRepo.save(x);
    }

    private void addReportReview(BaoCaoTieuChi bc,String cap,String userId,String state,String nhanXet,String khuyenNghi,int percent){
        if(bc.getDuyetBaoCao()==null)bc.setDuyetBaoCao(new ArrayList<>());
        Map<String,Object> row=new LinkedHashMap<>();
        row.put("capDuyet",cap); row.put("vaiTroDuyet",cap); row.put("maNguoiDuyet",userId);
        row.put("trangThai",state); row.put("nhanXet",nhanXet); row.put("khuyenNghi",khuyenNghi);
        row.put("mucDoDuyet",percent); row.put("ngayDuyet",LocalDateTime.now()); bc.getDuyetBaoCao().add(row);
    }

    public BaoCaoTieuChi truongNhomDuyetBaoCaoTieuChi(String id,String ketQua,String nhanXet,String userId){
        BaoCaoTieuChi bc=getBaoCao(id);
        boolean allowed=baoCaoTruongNhom(userId).stream().anyMatch(x->Objects.equals(x.getId(),id));
        if(!allowed) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Báo cáo không thuộc phạm vi Trưởng nhóm của bạn");
        if(!"Chờ Trưởng nhóm duyệt".equalsIgnoreCase(Optional.ofNullable(bc.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ báo cáo Chờ Trưởng nhóm duyệt mới được xử lý");
        if(!List.of("Duyệt","Yêu cầu chỉnh sửa").contains(Optional.ofNullable(ketQua).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả không hợp lệ");
        if("Duyệt".equals(ketQua)){
            bc.setTrangThai("Chờ Hội đồng cấp khoa duyệt"); bc.setMucDoDuyet(50); bc.setCapDuyetHienTai("Trưởng nhóm");
            addReportReview(bc,"Trưởng nhóm",userId,"Duyệt",nhanXet,null,50);
        }else{
            bc.setTrangThai("Yêu cầu chỉnh sửa"); bc.setMucDoDuyet(25); bc.setCapDuyetHienTai("Thành viên");
            addReportReview(bc,"Trưởng nhóm",userId,"Yêu cầu chỉnh sửa",nhanXet,null,25);
        }
        bc.setNgayCapNhat(LocalDateTime.now()); return bcRepo.save(bc);
    }

    // Hội đồng cấp khoa là cấp duyệt 75% trong thang 25-50-75-100.
    public BaoCaoTieuChi duyetBaoCao(String id, String trangThai, String nhanXet, String khuyenNghi, String maNguoiDuyet){
        BaoCaoTieuChi bc=getBaoCao(id);
        String current=Optional.ofNullable(bc.getTrangThai()).orElse("");
        if(!List.of("Chờ Hội đồng cấp khoa duyệt","Chờ duyệt").contains(current))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo chưa qua bước Trưởng nhóm hoặc không ở trạng thái Hội đồng được xử lý");
        String tt=Optional.ofNullable(trangThai).orElse("").trim();
        if(!List.of("Duyệt","Đã duyệt","Yêu cầu chỉnh sửa").contains(tt))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Trạng thái duyệt báo cáo không hợp lệ");
        boolean approve=List.of("Duyệt","Đã duyệt").contains(tt);
        if(approve){
            boolean hasApprovedEvidence=mcRepo.findAll().stream().anyMatch(m->Objects.equals(m.getMaTieuChi(),bc.getMaTieuChi())
                    &&Objects.equals(Optional.ofNullable(m.getMaKeHoach()).orElse(""),Optional.ofNullable(bc.getMaKeHoach()).orElse(""))
                    &&"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(m.getTrangThai()).orElse("")));
            if(!hasApprovedEvidence) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không thể duyệt khi tiêu chí chưa có minh chứng đã duyệt");
            bc.setTrangThai("Chờ TT QLCL duyệt"); bc.setMucDoDuyet(75); bc.setCapDuyetHienTai("Hội đồng cấp khoa");
            addReportReview(bc,"Hội đồng cấp khoa",maNguoiDuyet,"Duyệt",nhanXet,khuyenNghi,75);
        }else{
            bc.setTrangThai("Yêu cầu chỉnh sửa"); bc.setMucDoDuyet(25); bc.setCapDuyetHienTai("Thành viên");
            addReportReview(bc,"Hội đồng cấp khoa",maNguoiDuyet,"Yêu cầu chỉnh sửa",nhanXet,khuyenNghi,25);
        }
        bc.setNgayCapNhat(LocalDateTime.now()); return bcRepo.save(bc);
    }

    public BaoCaoTieuChi qlclDuyetBaoCao(String id,String ketQua,String nhanXet,String userId){
        BaoCaoTieuChi bc=getBaoCao(id);
        if(!"Chờ TT QLCL duyệt".equalsIgnoreCase(Optional.ofNullable(bc.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo chưa đến bước Trung tâm quản lý chất lượng");
        if(!List.of("Duyệt","Yêu cầu chỉnh sửa").contains(Optional.ofNullable(ketQua).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả không hợp lệ");
        if("Duyệt".equals(ketQua)){
            bc.setTrangThai("Đã duyệt"); bc.setMucDoDuyet(100); bc.setCapDuyetHienTai("TT QLCL");
            addReportReview(bc,"TT QLCL",userId,"Đã duyệt",nhanXet,null,100);
            markCriterionAssignmentState(bc.getMaKeHoach(),bc.getMaTieuChi(),"Hoàn thành");
        }else{
            bc.setTrangThai("Yêu cầu chỉnh sửa"); bc.setMucDoDuyet(25); bc.setCapDuyetHienTai("Thành viên");
            addReportReview(bc,"TT QLCL",userId,"Yêu cầu chỉnh sửa",nhanXet,null,25);
            markCriterionAssignmentState(bc.getMaKeHoach(),bc.getMaTieuChi(),"Đang thực hiện");
        }
        bc.setNgayCapNhat(LocalDateTime.now()); return bcRepo.save(bc);
    }

    public BaoCaoTieuChi adminXuLyBaoCao(String id,String ketQua,String nhanXet,String userId){
        BaoCaoTieuChi bc=getBaoCao(id);
        String k=Optional.ofNullable(ketQua).orElse("Góp ý");
        if("Góp ý".equals(k)){
            addReportReview(bc,"Quản trị",userId,"Góp ý",nhanXet,null,Optional.ofNullable(bc.getMucDoDuyet()).orElse(25));
        }else if("Duyệt".equals(k)||"Đã duyệt".equals(k)){
            bc.setTrangThai("Đã duyệt"); bc.setMucDoDuyet(100); bc.setCapDuyetHienTai("Quản trị");
            addReportReview(bc,"Quản trị",userId,"Đã duyệt",nhanXet,null,100);
            markCriterionAssignmentState(bc.getMaKeHoach(),bc.getMaTieuChi(),"Hoàn thành");
        }else if("Yêu cầu chỉnh sửa".equals(k)){
            bc.setTrangThai("Yêu cầu chỉnh sửa"); bc.setMucDoDuyet(25); bc.setCapDuyetHienTai("Thành viên");
            addReportReview(bc,"Quản trị",userId,"Yêu cầu chỉnh sửa",nhanXet,null,25);
            markCriterionAssignmentState(bc.getMaKeHoach(),bc.getMaTieuChi(),"Đang thực hiện");
        }else throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả xử lý không hợp lệ");
        bc.setNgayCapNhat(LocalDateTime.now()); return bcRepo.save(bc);
    }


    public Map<String,Object> thongKeHeThong(String maKeHoach){
        Map<String,Object> out=new LinkedHashMap<>();
        List<BaoCaoTieuChi> reports=baoCao().stream().filter(x->samePlan(maKeHoach,x.getMaKeHoach())).toList();
        List<MinhChung> evidences=minhChung("TIEU_CHI").stream().filter(x->samePlan(maKeHoach,x.getMaKeHoach())).toList();
        List<MinhChungChung> shared=minhChungChung();
        long reportWithFile=reports.stream().filter(x->x.getDuongDanFile()!=null&&!x.getDuongDanFile().isBlank()).count();
        long reportComplete=reports.stream().filter(x->!isBlank(x.getMoTaHienTrang())&&!isBlank(x.getDiemManh())&&!isBlank(x.getDiemTonTai())&&!isBlank(x.getKeHoachHanhDong())).count();
        long evidenceWithFile=evidences.stream().filter(x->x.getDuongDanFile()!=null&&!x.getDuongDanFile().isBlank()).count();
        long sharedWithFile=shared.stream().filter(x->x.getDuongDanFile()!=null&&!x.getDuongDanFile().isBlank()).count();
        double avgApproval=reports.isEmpty()?0:reports.stream().mapToInt(x->Optional.ofNullable(x.getMucDoDuyet()).orElse(25)).average().orElse(0);
        out.put("tongBaoCao",reports.size()); out.put("baoCaoCoFile",reportWithFile); out.put("baoCaoDu4Phan",reportComplete);
        out.put("baoCaoDaDuyet",reports.stream().filter(x->"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());
        out.put("tongMinhChung",evidences.size()); out.put("minhChungCoFile",evidenceWithFile); out.put("minhChungDaDuyet",evidences.stream().filter(x->"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());
        out.put("minhChungDungChung",shared.size()); out.put("minhChungDungChungCoFile",sharedWithFile);
        out.put("mucHoanThienTrungBinh",(int)Math.round(avgApproval)); out.put("tongHop",tongHopHoiDong(maKeHoach));
        return out;
    }

    public Map<String,Object> tongHopHoiDong(){return tongHopHoiDong(null);}
    public Map<String,Object> tongHopHoiDong(String maKeHoach){
        KeHoachTuDanhGia plan=(maKeHoach==null||maKeHoach.isBlank())?null:khRepo.findById(maKeHoach).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy kế hoạch tự đánh giá"));
        List<TieuChuan> standards=tieuChuan().stream().filter(x->plan==null||plan.getMaBoTieuChuan()==null||plan.getMaBoTieuChuan().isBlank()||Objects.equals(plan.getMaBoTieuChuan(),x.getMaBoTieuChuan())).toList();
        Set<String> standardIds=standards.stream().map(TieuChuan::getId).collect(java.util.stream.Collectors.toSet());
        List<TieuChi> criteria=tchiRepo.findAll().stream().filter(x->plan==null||standardIds.contains(x.getMaTieuChuan())).toList();
        List<MinhChung> evidences=minhChung("TIEU_CHI").stream().filter(x->samePlan(maKeHoach,x.getMaKeHoach())).toList();
        List<BaoCaoTieuChi> allReports=bcRepo.findAll();
        allReports.forEach(this::normalizeBaoCao);
        List<BaoCaoTieuChi> reports=allReports.stream().filter(x->samePlan(maKeHoach,x.getMaKeHoach())).toList();
        List<DanhGiaTieuChi> evals=dgtcRepo.findAll().stream().filter(x->samePlan(maKeHoach,x.getMaKeHoach())).toList();
        List<Map<String,Object>> rows=new ArrayList<>();
        for(TieuChuan tc:standards){
            List<TieuChi> cs=criteria.stream().filter(x->Objects.equals(tc.getId(),x.getMaTieuChuan())).toList();
            Set<String> ids=cs.stream().map(TieuChi::getId).collect(java.util.stream.Collectors.toSet());
            long bcCount=reports.stream().filter(x->ids.contains(x.getMaTieuChi())).map(BaoCaoTieuChi::getMaTieuChi).filter(Objects::nonNull).distinct().count();
            long approvedReportCount=reports.stream().filter(x->ids.contains(x.getMaTieuChi())&&"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).map(BaoCaoTieuChi::getMaTieuChi).filter(Objects::nonNull).distinct().count();
            long mcCount=evidences.stream().filter(x->ids.contains(x.getMaTieuChi()) && "Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count();
            Map<String,DanhGiaTieuChi> latest=new HashMap<>();
            for(DanhGiaTieuChi d:evals){
                if(!ids.contains(d.getMaTieuChi()))continue;
                DanhGiaTieuChi old=latest.get(d.getMaTieuChi());
                if(old==null||Optional.ofNullable(d.getLanDanhGia()).orElse(0)>=Optional.ofNullable(old.getLanDanhGia()).orElse(0)) latest.put(d.getMaTieuChi(),d);
            }
            long dat=latest.values().stream().filter(x->"Đạt".equalsIgnoreCase(x.getKetQua())).count();
            long khongDat=latest.values().stream().filter(x->"Không đạt".equalsIgnoreCase(x.getKetQua())).count();
            Map<String,Object> r=new LinkedHashMap<>();
            r.put("maTieuChuan",tc.getId()); r.put("tenTieuChuan",tc.getTenTieuChuan()); r.put("tongTieuChi",cs.size());
            r.put("soBaoCao",bcCount); r.put("baoCaoDaDuyet",approvedReportCount); r.put("minhChungDaDuyet",mcCount); r.put("daDanhGia",latest.size()); r.put("dat",dat); r.put("khongDat",khongDat);
            r.put("tyLeCoBaoCao",cs.isEmpty()?0:(int)Math.round(bcCount*100.0/cs.size()));
            r.put("tyLeHoanThanh",cs.isEmpty()?0:(int)Math.round(approvedReportCount*100.0/cs.size()));
            rows.add(r);
        }
        Map<String,Object> out=new LinkedHashMap<>();
        out.put("maKeHoach",maKeHoach);
        out.put("tieuChuan",rows);
        out.put("tongTieuChi",criteria.size());
        out.put("tongBaoCao",reports.size());
        out.put("baoCaoDaDuyet",reports.stream().filter(x->"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());
        out.put("tongMinhChung",evidences.size());
        out.put("tongDanhGia",evals.size());
        return out;
    }

    private boolean samePlan(String requested,String actual){
        if(requested==null||requested.isBlank()) return true;
        return Objects.equals(requested,actual);
    }

    private Map<String,DanhGiaTieuChi> latestEvaluations(List<DanhGiaTieuChi> rows){
        Map<String,DanhGiaTieuChi> latest=new HashMap<>();
        for(DanhGiaTieuChi d:rows){
            if(d.getMaTieuChi()==null)continue;
            String key=Optional.ofNullable(d.getMaKeHoach()).orElse("")+"|"+d.getMaTieuChi();
            DanhGiaTieuChi old=latest.get(key);
            if(old==null||Optional.ofNullable(d.getLanDanhGia()).orElse(0)>Optional.ofNullable(old.getLanDanhGia()).orElse(0)) latest.put(key,d);
        }
        return latest;
    }

    // ===== CHỨC NĂNG THEO VAI TRÒ =====
    public List<NhomTieuChi> nhomCuaNguoiDung(String userId){
        return nhomTieuChi().stream().filter(g -> g.getThanhVienChiTiet()!=null && g.getThanhVienChiTiet().stream()
                .anyMatch(v -> v!=null && Objects.equals(v.getMaNguoiDung(),userId))).toList();
    }

    public List<PhanCong> congViecCuaNguoiDung(String userId){
        return phanCong().stream().filter(p -> (Objects.equals(p.getMaNguoiDung(),userId)
                || Objects.equals(p.getMaNhanSuHoTro(),userId)
                || (p.getMaNhanSuHoTroList()!=null&&p.getMaNhanSuHoTroList().contains(userId)))
                && !"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).toList();
    }

    public List<PhanCong> phanCongTrongPhamViTruongNhom(String userId){
        List<PhanCong> all=phanCong();
        List<PhanCong> parents=all.stream().filter(p->Objects.equals(p.getMaNguoiDung(),userId)&&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())).toList();
        Set<String> scopes=parents.stream().filter(p->!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")))
                .map(p->String.valueOf(p.getMaKeHoach())+"|"+String.valueOf(p.getMaNhomTieuChi())+"|"+String.valueOf(p.getMaTieuChuan())).collect(java.util.stream.Collectors.toSet());
        return all.stream().filter(p->{
            if(parents.stream().anyMatch(x->Objects.equals(x.getId(),p.getId()))) return true;
            if(Objects.equals(p.getMaNguoiTao(),userId)) return true;
            if(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank()) return false;
            String key=String.valueOf(p.getMaKeHoach())+"|"+String.valueOf(p.getMaNhomTieuChi())+"|"+String.valueOf(p.getMaTieuChuan());
            return scopes.contains(key);
        }).toList();
    }

    public Map<String,Object> roleDashboard(String userId){
        List<PhanCong> jobs=congViecCuaNguoiDung(userId);
        List<MinhChung> evidence=mcRepo.findAll().stream().filter(x->Objects.equals(x.getMaNguoiUpload(),userId)).toList();
        List<BaoCaoTieuChi> reports=bcRepo.findAll().stream().filter(x->Objects.equals(x.getMaNguoiViet(),userId)).toList();
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("phanCong",jobs.size());
        m.put("dangThucHien",jobs.stream().filter(x->!List.of("Hoàn thành","Hủy phân công").contains(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());
        m.put("minhChung",evidence.size());
        m.put("baoCao",reports.size());
        m.put("nhom",nhomCuaNguoiDung(userId).size());
        return m;
    }

    public List<PhanCong> truongNhomPhanCongNhieuTieuChi(Map<String,Object> body,String leaderId){
        String groupId=Objects.toString(body.get("maNhomTieuChi"),"").trim();
        String planId=Objects.toString(body.get("maKeHoach"),"").trim();
        String memberId=Objects.toString(body.get("maNguoiDung"),"").trim();
        List<String> supportIds=new ArrayList<>();
        Object rawSupport=body.get("maNhanSuHoTroList");
        if(rawSupport instanceof Collection<?> c){
            for(Object v:c){
                String id=Objects.toString(v,"").trim();
                if(!id.isBlank()&&!supportIds.contains(id)) supportIds.add(id);
            }
        }else{
            String oldSupport=Objects.toString(body.get("maNhanSuHoTro"),"").trim();
            if(!oldSupport.isBlank()) supportIds.add(oldSupport);
        }
        String content=Objects.toString(body.get("noiDungCongViec"),"").trim();
        String state="Đã phân công"; // trạng thái phân công do backend quản lý
        String startText=Objects.toString(body.get("ngayBatDau"),"").trim();
        String endText=Objects.toString(body.get("ngayKetThucDuKien"),"").trim();

        Object rawCriteria=body.get("maTieuChiList");
        List<String> criterionIds=new ArrayList<>();
        if(rawCriteria instanceof Collection<?> c){
            for(Object v:c){
                String id=Objects.toString(v,"").trim();
                if(!id.isBlank()&&!criterionIds.contains(id)) criterionIds.add(id);
            }
        }

        if(groupId.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn nhóm");
        if(planId.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn kế hoạch");
        if(memberId.isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn người thực hiện");
        if(criterionIds.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn ít nhất một tiêu chí");

        NhomTieuChi g=nhomRepo.findById(groupId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhóm không tồn tại"));

        boolean leader=g.getThanhVienChiTiet()!=null && g.getThanhVienChiTiet().stream()
                .anyMatch(v->v!=null&&Objects.equals(v.getMaNguoiDung(),leaderId)&&"Trưởng nhóm".equals(v.getChucVu()));
        if(!leader) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không phải Trưởng nhóm của nhóm này");

        ThanhVienNhom target=g.getThanhVienChiTiet().stream()
                .filter(v->v!=null&&Objects.equals(v.getMaNguoiDung(),memberId))
                .findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người được giao không thuộc nhóm"));

        if(List.of("Trưởng nhóm","Nhân sự hỗ trợ").contains(target.getChucVu()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người thực hiện chính phải là Thư ký nhóm hoặc Thành viên trong nhóm");

        for(String supportId:supportIds){
            ThanhVienNhom support=g.getThanhVienChiTiet().stream()
                    .filter(v->v!=null&&Objects.equals(v.getMaNguoiDung(),supportId))
                    .findFirst()
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhân sự hỗ trợ không thuộc nhóm: "+supportId));
            if(!"Nhân sự hỗ trợ".equals(support.getChucVu()))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người được chọn hỗ trợ phải có chức vụ Nhân sự hỗ trợ trong nhóm");
            if(Objects.equals(memberId,supportId))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người thực hiện chính và nhân sự hỗ trợ phải khác nhau");
        }

        List<PhanCong> allAssignments=pcRepo.findAll();
        long currentLoad=allAssignments.stream().filter(p->Objects.equals(p.getMaNhomTieuChi(),groupId)
                &&Objects.equals(p.getMaKeHoach(),planId)&&Objects.equals(p.getMaNguoiDung(),memberId)
                &&p.getMaTieuChi()!=null&&!p.getMaTieuChi().isBlank()
                &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).count();
        if(currentLoad+criterionIds.size()>2)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mỗi Thành viên/Thư ký nhóm được phân tối đa 2 tiêu chí trong cùng nhóm và kế hoạch. Hiện đã có "+currentLoad+" tiêu chí.");
        List<TieuChi> selected=new ArrayList<>();

        // Kiểm tra toàn bộ trước khi lưu để tránh trường hợp chỉ lưu được một phần danh sách.
        for(String criterionId:criterionIds){
            TieuChi criterion=tchiRepo.findById(criterionId)
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tiêu chí không tồn tại: "+criterionId));

            PhanCong parentAssignment=allAssignments.stream().filter(p->
                    Objects.equals(p.getMaNhomTieuChi(),g.getId())
                    && Objects.equals(p.getMaKeHoach(),planId)
                    && Objects.equals(p.getMaNguoiDung(),leaderId)
                    && Objects.equals(p.getMaTieuChuan(),criterion.getMaTieuChuan())
                    && (p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())
                    && !"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))
            ).findFirst().orElse(null);

            if(parentAssignment==null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Tiêu chí "+criterionId+" không thuộc tiêu chuẩn được Admin giao cho Trưởng nhóm");

            boolean duplicate=allAssignments.stream().anyMatch(p->
                    Objects.equals(p.getMaNhomTieuChi(),g.getId())
                    && Objects.equals(p.getMaKeHoach(),parentAssignment.getMaKeHoach())
                    && Objects.equals(p.getMaTieuChi(),criterionId)
                    && !"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))
            );

            if(duplicate)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Tiêu chí "+criterionId+" đã được phân công trong nhóm");

            selected.add(criterion);
        }

        LocalDate start=startText.isBlank()?LocalDate.now():LocalDate.parse(startText);
        LocalDate end=endText.isBlank()?null:LocalDate.parse(endText);
        if(end!=null&&end.isBefore(start)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Hạn hoàn thành không được trước ngày bắt đầu");

        String assignmentBatchId="DOT-"+UUID.randomUUID().toString().substring(0,8).toUpperCase();
        List<PhanCong> result=new ArrayList<>();
        for(TieuChi criterion:selected){
            PhanCong x=new PhanCong();
            // Lưu từng bản ghi sau khi kiểm tra hết; nextPhanCong() sẽ sinh mã kế tiếp dựa trên dữ liệu vừa lưu.
            x.setId(codes.nextPhanCong());
            PhanCong standardAssignment=allAssignments.stream().filter(p->Objects.equals(p.getMaNhomTieuChi(),g.getId())&&Objects.equals(p.getMaKeHoach(),planId)&&Objects.equals(p.getMaNguoiDung(),leaderId)&&Objects.equals(p.getMaTieuChuan(),criterion.getMaTieuChuan())&&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).findFirst().orElse(null);
            x.setMaKeHoach(standardAssignment==null?null:standardAssignment.getMaKeHoach());
            x.setMaNguoiDung(memberId);
            x.setMaTieuChuan(criterion.getMaTieuChuan());
            x.setMaTieuChi(criterion.getId());
            x.setMaNhomTieuChi(groupId);
            x.setVaiTroTrongNhom(target.getChucVu());
            x.setMaNhanSuHoTro(null);
            x.setMaNhanSuHoTroList(new ArrayList<>(supportIds));
            x.setMaDotPhanCong(assignmentBatchId);
            x.setNoiDungCongViec(content.isBlank()
                    ?"Thu thập minh chứng và viết báo cáo tự đánh giá cho "+criterion.getId()
                    :content);
            x.setNgayBatDau(start);
            x.setNgayKetThucDuKien(end);
            x.setTrangThai(state.isBlank()?"Đã phân công":state);
            x.setMaNguoiTao(leaderId);
            result.add(pcRepo.save(x));
            refreshParentAssignmentProgress(x.getMaKeHoach(),x.getMaNhomTieuChi(),x.getMaTieuChuan());
        }
        return result;
    }

    public PhanCong truongNhomPhanCongTieuChi(PhanCong x,String leaderId){
        if(x.getId()!=null&&!x.getId().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không sửa trực tiếp phân công tiêu chí; hãy hủy và giao lại");
        if(x.getMaNhomTieuChi()==null||x.getMaNhomTieuChi().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn nhóm");
        NhomTieuChi g=nhomRepo.findById(x.getMaNhomTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhóm không tồn tại"));
        boolean leader=g.getThanhVienChiTiet()!=null && g.getThanhVienChiTiet().stream().anyMatch(v->v!=null&&Objects.equals(v.getMaNguoiDung(),leaderId)&&"Trưởng nhóm".equals(v.getChucVu()));
        if(!leader) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không phải Trưởng nhóm của nhóm này");
        if(x.getMaTieuChi()==null||x.getMaTieuChi().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn tiêu chí");
        TieuChi criterion=tchiRepo.findById(x.getMaTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tiêu chí không tồn tại"));
        PhanCong standardAssignment=pcRepo.findAll().stream().filter(p->Objects.equals(p.getMaNhomTieuChi(),g.getId())&&Objects.equals(p.getMaNguoiDung(),leaderId)
                &&Objects.equals(p.getMaTieuChuan(),criterion.getMaTieuChuan())&&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())
                &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).findFirst().orElse(null);
        if(standardAssignment==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tiêu chí không thuộc tiêu chuẩn được Admin giao cho Trưởng nhóm");
        if(x.getMaNguoiDung()==null||x.getMaNguoiDung().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn thành viên");
        ThanhVienNhom target=g.getThanhVienChiTiet().stream().filter(v->v!=null&&Objects.equals(v.getMaNguoiDung(),x.getMaNguoiDung())).findFirst()
                .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người được giao không thuộc nhóm"));
        if(List.of("Trưởng nhóm","Nhân sự hỗ trợ").contains(target.getChucVu())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người thực hiện chính phải là Thư ký nhóm hoặc Thành viên trong nhóm");
        List<String> supportIds=x.getMaNhanSuHoTroList()==null?new ArrayList<>():new ArrayList<>(x.getMaNhanSuHoTroList());
        if(supportIds.isEmpty()&&x.getMaNhanSuHoTro()!=null&&!x.getMaNhanSuHoTro().isBlank()) supportIds.add(x.getMaNhanSuHoTro());
        for(String supportId:supportIds){
            ThanhVienNhom support=g.getThanhVienChiTiet().stream()
                    .filter(v->v!=null&&Objects.equals(v.getMaNguoiDung(),supportId))
                    .findFirst()
                    .orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhân sự hỗ trợ không thuộc nhóm"));
            if(!"Nhân sự hỗ trợ".equals(support.getChucVu())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người hỗ trợ phải có chức vụ Nhân sự hỗ trợ");
            if(Objects.equals(x.getMaNguoiDung(),supportId)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Người thực hiện chính và nhân sự hỗ trợ phải khác nhau");
        }
        x.setMaNhanSuHoTro(null);
        x.setMaNhanSuHoTroList(supportIds);
        if(x.getMaDotPhanCong()==null||x.getMaDotPhanCong().isBlank())
            x.setMaDotPhanCong("DOT-"+UUID.randomUUID().toString().substring(0,8).toUpperCase());
        List<PhanCong> allCriterionAssignments=pcRepo.findAll();
        boolean duplicate=allCriterionAssignments.stream().anyMatch(p->Objects.equals(p.getMaNhomTieuChi(),g.getId())&&Objects.equals(p.getMaKeHoach(),standardAssignment.getMaKeHoach())&&Objects.equals(p.getMaTieuChi(),criterion.getId())
                &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")));
        if(duplicate) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tiêu chí này đã được phân công trong nhóm");
        long currentLoad=allCriterionAssignments.stream().filter(p->Objects.equals(p.getMaNhomTieuChi(),g.getId())
                &&Objects.equals(p.getMaKeHoach(),standardAssignment.getMaKeHoach())&&Objects.equals(p.getMaNguoiDung(),x.getMaNguoiDung())
                &&p.getMaTieuChi()!=null&&!p.getMaTieuChi().isBlank()
                &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).count();
        if(currentLoad>=2) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Mỗi Thành viên/Thư ký nhóm được phân tối đa 2 tiêu chí trong cùng nhóm và kế hoạch");
        x.setId(codes.nextPhanCong());
        x.setMaKeHoach(standardAssignment.getMaKeHoach());
        x.setMaTieuChuan(criterion.getMaTieuChuan());
        x.setVaiTroTrongNhom(target.getChucVu());
        x.setMaNguoiTao(leaderId);
        if(x.getNgayBatDau()==null)x.setNgayBatDau(LocalDate.now());
        if(x.getNgayKetThucDuKien()!=null&&x.getNgayKetThucDuKien().isBefore(x.getNgayBatDau())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Hạn hoàn thành không được trước ngày bắt đầu");
        x.setTrangThai("Đã phân công");
        PhanCong saved=pcRepo.save(x);
        refreshParentAssignmentProgress(saved.getMaKeHoach(),saved.getMaNhomTieuChi(),saved.getMaTieuChuan());
        return saved;
    }

    public MinhChung luuMinhChungNguoiDung(MinhChung x,String userId,boolean leaderRole){
        boolean isNew=x.getId()==null||x.getId().isBlank();
        if(x.getMaTieuChi()==null||x.getMaTieuChi().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn tiêu chí");
        TieuChi tc=tchiRepo.findById(x.getMaTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Tiêu chí không tồn tại"));
        PhanCong assignment=null;
        if(x.getMaPhanCong()!=null&&!x.getMaPhanCong().isBlank()){
            PhanCong candidate=pcRepo.findById(x.getMaPhanCong()).orElse(null);
            if(candidate!=null&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(candidate.getTrangThai()).orElse(""))){
                boolean mine=leaderRole
                        ? Objects.equals(candidate.getMaNguoiDung(),userId)&&Objects.equals(candidate.getMaTieuChuan(),tc.getMaTieuChuan())&&(candidate.getMaTieuChi()==null||candidate.getMaTieuChi().isBlank())
                        : (Objects.equals(candidate.getMaNguoiDung(),userId)||Objects.equals(candidate.getMaNhanSuHoTro(),userId)||(candidate.getMaNhanSuHoTroList()!=null&&candidate.getMaNhanSuHoTroList().contains(userId)))&&Objects.equals(candidate.getMaTieuChi(),tc.getId());
                if(mine) assignment=candidate;
            }
        }
        if(assignment==null) assignment=findAssignmentForEvidence(userId,tc,leaderRole);
        if(assignment==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn chưa được phân công tiêu chí này");

        MinhChung old=null;
        if(!isNew){
            old=mcRepo.findById(x.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng"));
            if(!Objects.equals(old.getMaNguoiUpload(),userId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn chỉ được sửa minh chứng do chính mình tạo");
            if(!Objects.equals(old.getMaTieuChi(),x.getMaTieuChi())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không được đổi tiêu chí của minh chứng đã tạo");
            if(!List.of("Từ chối","Chờ Trưởng nhóm duyệt","Chờ Thư ký duyệt").contains(Optional.ofNullable(old.getTrangThai()).orElse("")))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng đã khóa, không thể chỉnh sửa");
            // Khi đang chờ duyệt cấp tiếp theo không cho sửa nội dung để tránh thay đổi sau khi đã được cấp trước phê duyệt.
            if("Chờ Thư ký duyệt".equalsIgnoreCase(old.getTrangThai()) && !leaderRole)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng đã được Trưởng nhóm duyệt, không thể sửa");
            x.setMaMinhChung(old.getMaMinhChung());
            x.setDuongDanFile(old.getDuongDanFile()); x.setLoaiFile(old.getLoaiFile()); x.setKichThuoc(old.getKichThuoc()); x.setNgayUpload(old.getNgayUpload());
        }
        x.setLoaiMinhChung("TIEU_CHI"); x.setMaNguoiUpload(userId); x.setMaTieuChuan(tc.getMaTieuChuan());
        x.setMaPhanCong(assignment.getId()); x.setMaKeHoach(assignment.getMaKeHoach());
        if(isNew) x.setTrangThai(leaderRole?"Chờ Thư ký duyệt":"Chờ Trưởng nhóm duyệt");
        else if("Từ chối".equalsIgnoreCase(Optional.ofNullable(old.getTrangThai()).orElse("")))
            x.setTrangThai(leaderRole?"Chờ Thư ký duyệt":"Chờ Trưởng nhóm duyệt");
        else x.setTrangThai(old.getTrangThai()); // trạng thái là dữ liệu do server quản lý
        MinhChung saved=saveMinhChung(x);
        markAssignmentInProgress(assignment.getId());
        return saved;
    }

    private void markAssignmentInProgress(String assignmentId){
        if(assignmentId==null||assignmentId.isBlank())return;
        pcRepo.findById(assignmentId).ifPresent(p->{
            if("Đã phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))){p.setTrangThai("Đang thực hiện");pcRepo.save(p);}
            refreshParentAssignmentProgress(p.getMaKeHoach(),p.getMaNhomTieuChi(),p.getMaTieuChuan());
        });
    }
    private void markCriterionAssignmentState(String maKeHoach,String maTieuChi,String state){
        Set<String> scopes=new LinkedHashSet<>();
        for(PhanCong p:pcRepo.findAll()){
            if(Objects.equals(p.getMaKeHoach(),maKeHoach)&&Objects.equals(p.getMaTieuChi(),maTieuChi)
                    &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))){
                p.setTrangThai(state);pcRepo.save(p);
                scopes.add(String.valueOf(p.getMaNhomTieuChi())+"|"+String.valueOf(p.getMaTieuChuan()));
            }
        }
        for(String scope:scopes){String[] a=scope.split("\\|",-1);refreshParentAssignmentProgress(maKeHoach,"null".equals(a[0])?null:a[0],"null".equals(a[1])?null:a[1]);}
    }
    private void refreshParentAssignmentProgress(String maKeHoach,String maNhom,String maTieuChuan){
        if(maNhom==null||maTieuChuan==null)return;
        List<PhanCong> all=pcRepo.findAll();
        List<PhanCong> children=all.stream().filter(p->Objects.equals(p.getMaKeHoach(),maKeHoach)&&Objects.equals(p.getMaNhomTieuChi(),maNhom)
                &&Objects.equals(p.getMaTieuChuan(),maTieuChuan)&&p.getMaTieuChi()!=null&&!p.getMaTieuChi().isBlank()
                &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).toList();
        if(children.isEmpty())return;
        Set<String> expected=tchiRepo.findAll().stream().filter(c->Objects.equals(c.getMaTieuChuan(),maTieuChuan)).map(TieuChi::getId).collect(java.util.stream.Collectors.toSet());
        Set<String> assigned=children.stream().map(PhanCong::getMaTieuChi).filter(Objects::nonNull).collect(java.util.stream.Collectors.toSet());
        boolean allCriteriaAssigned=!expected.isEmpty()&&assigned.containsAll(expected);
        boolean allCompleted=allCriteriaAssigned&&children.stream().allMatch(p->"Hoàn thành".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")));
        String parentState=allCompleted?"Hoàn thành":"Đang thực hiện";
        for(PhanCong p:all){
            if(Objects.equals(p.getMaKeHoach(),maKeHoach)&&Objects.equals(p.getMaNhomTieuChi(),maNhom)&&Objects.equals(p.getMaTieuChuan(),maTieuChuan)
                    &&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))){
                p.setTrangThai(parentState);pcRepo.save(p);
            }
        }
    }

    private PhanCong findAssignmentForEvidence(String userId,TieuChi tc,boolean leaderRole){
        return pcRepo.findAll().stream().filter(p->{
            if("Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))) return false;
            if(leaderRole) return Objects.equals(p.getMaNguoiDung(),userId)&&Objects.equals(p.getMaTieuChuan(),tc.getMaTieuChuan())&&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank());
            return (Objects.equals(p.getMaNguoiDung(),userId)||Objects.equals(p.getMaNhanSuHoTro(),userId)||(p.getMaNhanSuHoTroList()!=null&&p.getMaNhanSuHoTroList().contains(userId)))&&Objects.equals(p.getMaTieuChi(),tc.getId());
        }).findFirst().orElse(null);
    }

    public MinhChung uploadMinhChungNguoiDung(String id,MultipartFile file,String userId,boolean leaderRole){
        MinhChung mc=mcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng"));
        if(!Objects.equals(mc.getMaNguoiUpload(),userId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn chỉ được tải file cho minh chứng do chính mình tạo");
        TieuChi tc=tchiRepo.findById(mc.getMaTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng không gắn tiêu chí hợp lệ"));
        if(findAssignmentForEvidence(userId,tc,leaderRole)==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không còn được phân công tiêu chí này");
        String state=Optional.ofNullable(mc.getTrangThai()).orElse("");
        if("Đã duyệt".equalsIgnoreCase(state)||"Chờ Thư ký duyệt".equalsIgnoreCase(state)&&!leaderRole)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng đã khóa, không thể thay file");
        return uploadMinhChung(id,file);
    }

    public DanhGiaMinhChung truongNhomDuyetMinhChung(String id,String ketQua,String nhanXet,String leaderId){
        MinhChung mc=mcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng"));
        if(!"Chờ Trưởng nhóm duyệt".equalsIgnoreCase(Optional.ofNullable(mc.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng không ở bước chờ Trưởng nhóm duyệt");
        if(mc.getDuongDanFile()==null||mc.getDuongDanFile().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng chưa có file đính kèm");
        TieuChi tc=tchiRepo.findById(mc.getMaTieuChi()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng không gắn tiêu chí hợp lệ"));
        boolean allowed=pcRepo.findAll().stream().anyMatch(p->Objects.equals(p.getMaNguoiDung(),leaderId)&&Objects.equals(p.getMaTieuChuan(),tc.getMaTieuChuan())
                &&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())&&"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))==false);
        if(!allowed) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không phụ trách tiêu chuẩn của minh chứng này");
        if(!List.of("Duyệt","Từ chối").contains(Optional.ofNullable(ketQua).orElse(""))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả duyệt không hợp lệ");
        String state="Duyệt".equalsIgnoreCase(ketQua)?"Chờ Thư ký duyệt":"Từ chối";
        DanhGiaMinhChung dg=new DanhGiaMinhChung(); dg.setId(codes.nextDanhGiaMinhChung()); dg.setMaMinhChung(mc.getMaMinhChung()); dg.setMaTieuChi(mc.getMaTieuChi());
        dg.setCapDuyet("Trưởng nhóm"); dg.setMaNguoiDuyet(leaderId); dg.setTrangThai(state); dg.setNhanXet(nhanXet); dg.setNgayDuyet(LocalDateTime.now());
        mc.setTrangThai(state); mcRepo.save(mc); return dgmcRepo.save(dg);
    }

    public DanhGiaMinhChung thuKyDuyetMinhChung(String id,String ketQua,String nhanXet,String userId){
        MinhChung mc=mcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy minh chứng"));
        if(!"Chờ Thư ký duyệt".equalsIgnoreCase(Optional.ofNullable(mc.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng chưa được Trưởng nhóm duyệt hoặc đã được xử lý");
        if(mc.getDuongDanFile()==null||mc.getDuongDanFile().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Minh chứng chưa có file đính kèm");
        if(!List.of("Duyệt","Từ chối").contains(Optional.ofNullable(ketQua).orElse(""))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả duyệt không hợp lệ");
        String state="Duyệt".equalsIgnoreCase(ketQua)?"Đã duyệt":"Từ chối";
        DanhGiaMinhChung dg=new DanhGiaMinhChung(); dg.setId(codes.nextDanhGiaMinhChung()); dg.setMaMinhChung(mc.getMaMinhChung()); dg.setMaTieuChi(mc.getMaTieuChi());
        dg.setCapDuyet("Thư ký chương trình"); dg.setMaNguoiDuyet(userId); dg.setTrangThai(state); dg.setNhanXet(nhanXet); dg.setNgayDuyet(LocalDateTime.now());
        mc.setTrangThai(state); mcRepo.save(mc); return dgmcRepo.save(dg);
    }

    public List<BaoCaoTieuChi> baoCaoCuaNguoiDung(String userId){return baoCao().stream().filter(x->Objects.equals(x.getMaNguoiViet(),userId)).toList();}
    public BaoCaoTieuChi luuBaoCaoThanhVien(BaoCaoTieuChi x,String userId){
        if(x.getMaTieuChi()==null||x.getMaTieuChi().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn tiêu chí");
        PhanCong assignment=null;
        if(x.getMaPhanCong()!=null&&!x.getMaPhanCong().isBlank()){
            PhanCong candidate=pcRepo.findById(x.getMaPhanCong()).orElse(null);
            if(candidate!=null&&Objects.equals(candidate.getMaNguoiDung(),userId)&&Objects.equals(candidate.getMaTieuChi(),x.getMaTieuChi())&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(candidate.getTrangThai()).orElse(""))) assignment=candidate;
        }
        if(assignment==null) assignment=pcRepo.findAll().stream().filter(p->Objects.equals(p.getMaNguoiDung(),userId)&&Objects.equals(p.getMaTieuChi(),x.getMaTieuChi())&&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse(""))).findFirst().orElse(null);
        if(assignment==null) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn chưa được phân công tiêu chí này");
        // Việc chuyển sang "Chờ duyệt" chỉ được thực hiện qua endpoint gui-duyet.
        // Khi lưu nội dung, backend luôn giữ báo cáo ở trạng thái có thể chỉnh sửa để tránh
        // trường hợp request lưu trực tiếp làm bỏ qua các kiểm tra workflow.
        String requested=Optional.ofNullable(x.getTrangThai()).orElse("Đang soạn");
        if(!List.of("Đang soạn","Chờ duyệt","Yêu cầu chỉnh sửa").contains(requested))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Trạng thái báo cáo không hợp lệ");
        x.setTrangThai("Đang soạn");

        BaoCaoTieuChi old=null;
        if(x.getId()!=null&&!x.getId().isBlank()){
            old=bcRepo.findById(x.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy báo cáo"));
            if(!Objects.equals(old.getMaNguoiViet(),userId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn chỉ được sửa báo cáo do chính mình viết");
            if(!Objects.equals(old.getMaTieuChi(),x.getMaTieuChi())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Không được đổi tiêu chí của báo cáo");
            String oldState=Optional.ofNullable(old.getTrangThai()).orElse("Đang soạn");
            if("Đã duyệt".equalsIgnoreCase(oldState)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo đã duyệt và đã được khóa");
            if(oldState.startsWith("Chờ ")) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo đang ở quy trình duyệt, không thể chỉnh sửa");
            x.setDuyetBaoCao(old.getDuyetBaoCao());
        }else{
            String planId=assignment.getMaKeHoach();
            boolean duplicate=bcRepo.findAll().stream().anyMatch(b->Objects.equals(b.getMaNguoiViet(),userId)&&Objects.equals(b.getMaTieuChi(),x.getMaTieuChi())&&Objects.equals(Optional.ofNullable(b.getMaKeHoach()).orElse(""),Optional.ofNullable(planId).orElse("")));
            if(duplicate) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Bạn đã có báo cáo cho tiêu chí này, hãy cập nhật báo cáo hiện có");
        }
        x.setMaPhanCong(assignment.getId()); x.setMaKeHoach(assignment.getMaKeHoach()); x.setMaNguoiViet(userId);
        BaoCaoTieuChi saved=saveBaoCao(x);
        markAssignmentInProgress(assignment.getId());
        return saved;
    }

    public BaoCaoTieuChi guiBaoCaoThanhVien(String id,String userId){
        BaoCaoTieuChi old=bcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy báo cáo"));
        if(!Objects.equals(old.getMaNguoiViet(),userId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn chỉ được gửi duyệt báo cáo do chính mình viết");
        String state=Optional.ofNullable(old.getTrangThai()).orElse("Đang soạn");
        if("Đã duyệt".equalsIgnoreCase(state))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo đã được Hội đồng duyệt");
        if(state.startsWith("Chờ ")) return old;
        if(!List.of("Đang soạn","Yêu cầu chỉnh sửa").contains(state))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Trạng thái báo cáo không cho phép gửi duyệt");

        // Chỉ cho gửi khi đã có đủ 4 phần để tránh báo cáo rỗng đi vào luồng duyệt nhiều cấp.
        if(isBlank(old.getMoTaHienTrang()) || isBlank(old.getDiemManh()) || isBlank(old.getDiemTonTai()) || isBlank(old.getKeHoachHanhDong()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng hoàn thành đủ 4 phần báo cáo trước khi gửi duyệt");
        // Bước 1: Thành viên gửi lên Trưởng nhóm (25%). Các cấp sau lần lượt 50% - 75% - 100%.
        old.setTrangThai("Chờ Trưởng nhóm duyệt");
        old.setMucDoDuyet(25);
        old.setCapDuyetHienTai("Thành viên");
        if(old.getDuyetBaoCao()==null)old.setDuyetBaoCao(new ArrayList<>());
        Map<String,Object> sent=new LinkedHashMap<>(); sent.put("capDuyet","Thành viên"); sent.put("vaiTroDuyet","Thành viên"); sent.put("maNguoiDuyet",userId); sent.put("trangThai","Đã gửi Trưởng nhóm"); sent.put("mucDoDuyet",25); sent.put("ngayDuyet",LocalDateTime.now()); old.getDuyetBaoCao().add(sent);
        old.setNgayCapNhat(LocalDateTime.now());
        return bcRepo.save(old);
    }

    private boolean isBlank(String v){ return v==null || v.isBlank(); }


    public BaoCaoTieuChi uploadBaoCaoThanhVien(String id,MultipartFile file,String userId){
        if(file==null||file.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn file báo cáo");
        BaoCaoTieuChi bc=bcRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy báo cáo"));
        if(!Objects.equals(bc.getMaNguoiViet(),userId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn chỉ được tải file cho báo cáo của mình");
        if(Optional.ofNullable(bc.getTrangThai()).orElse("").startsWith("Chờ ")||"Đã duyệt".equalsIgnoreCase(bc.getTrangThai()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo đang/đã duyệt nên không thể thay file");
        String original=Optional.ofNullable(file.getOriginalFilename()).orElse("baocao.pdf");
        String ext=original.contains(".")?original.substring(original.lastIndexOf('.')).toLowerCase(Locale.ROOT):"";
        if(!List.of(".pdf",".doc",".docx").contains(ext)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"File báo cáo chỉ hỗ trợ PDF, DOC, DOCX");
        try{
            storage.deleteByWebPath(bc.getDuongDanFile());
            bc.setDuongDanFile(storage.saveBaoCao(id,file)); bc.setTenFileBaoCao(original); bc.setNgayCapNhat(LocalDateTime.now());
            return bcRepo.save(bc);
        }catch(IOException e){throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Không thể lưu file báo cáo",e);}
    }

    public List<MinhChung> minhChungThanhVien(String userId){
        // Giới hạn theo cả kế hoạch + tiêu chí để không lẫn minh chứng của đợt tự đánh giá khác.
        Set<String> scopes=congViecCuaNguoiDung(userId).stream()
                .filter(p->p.getMaTieuChi()!=null&&!p.getMaTieuChi().isBlank())
                .map(p->Optional.ofNullable(p.getMaKeHoach()).orElse("")+"|"+p.getMaTieuChi())
                .collect(java.util.stream.Collectors.toSet());
        return minhChung("TIEU_CHI").stream().filter(x->scopes.contains(Optional.ofNullable(x.getMaKeHoach()).orElse("")+"|"+x.getMaTieuChi())).toList();
    }
    public List<MinhChung> minhChungTruongNhom(String userId){
        // Trưởng nhóm chỉ xem minh chứng thuộc đúng đợt + tiêu chuẩn mà mình đang phụ trách.
        Set<String> scopes=pcRepo.findAll().stream()
                .filter(p->Objects.equals(p.getMaNguoiDung(),userId)&&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())
                        &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")))
                .map(p->Optional.ofNullable(p.getMaKeHoach()).orElse("")+"|"+Optional.ofNullable(p.getMaTieuChuan()).orElse(""))
                .collect(java.util.stream.Collectors.toSet());
        return minhChung("TIEU_CHI").stream().filter(x->scopes.contains(Optional.ofNullable(x.getMaKeHoach()).orElse("")+"|"+Optional.ofNullable(x.getMaTieuChuan()).orElse(""))).toList();
    }
    public List<MinhChung> minhChungChoThuKy(){return minhChung("TIEU_CHI").stream().filter(x->"Chờ Thư ký duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).toList();}
    public List<BaoCaoTieuChi> baoCaoTruongNhom(String userId){
        Set<String> scopes=pcRepo.findAll().stream()
                .filter(p->Objects.equals(p.getMaNguoiDung(),userId)&&(p.getMaTieuChi()==null||p.getMaTieuChi().isBlank())
                        &&!"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")))
                .map(p->Optional.ofNullable(p.getMaKeHoach()).orElse("")+"|"+Optional.ofNullable(p.getMaTieuChuan()).orElse(""))
                .collect(java.util.stream.Collectors.toSet());
        return baoCao().stream().filter(x->scopes.contains(Optional.ofNullable(x.getMaKeHoach()).orElse("")+"|"+Optional.ofNullable(x.getMaTieuChuan()).orElse(""))).toList();
    }

    public List<KeHoachTuDanhGia> keHoach(){
        List<KeHoachTuDanhGia>a=khRepo.findAll();
        for(KeHoachTuDanhGia x:a){if("Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))){x.setTrangThai("Đã phê duyệt");khRepo.save(x);}}
        a.sort(Comparator.comparing(KeHoachTuDanhGia::getId,Comparator.nullsLast(String::compareTo)));return a;
    }
    public KeHoachTuDanhGia saveKeHoach(KeHoachTuDanhGia x){
        boolean isNew=x.getId()==null||x.getId().isBlank();
        KeHoachTuDanhGia old=null;
        if(isNew) x.setId(codes.nextKeHoach());
        else{
            old=khRepo.findById(x.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy kế hoạch"));
            String oldState=Optional.ofNullable(old.getTrangThai()).orElse("Đang lập");
            if(!List.of("Đang lập","Yêu cầu chỉnh sửa").contains(oldState))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kế hoạch đang chờ/đã phê duyệt nên không thể chỉnh sửa");
        }
        if(x.getTenKeHoach()==null||x.getTenKeHoach().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng nhập tên kế hoạch");
        if(x.getMaCTDT()==null||x.getMaCTDT().isBlank()||!ctdtRepo.existsById(x.getMaCTDT())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn CTĐT hợp lệ");
        if(x.getMaBoTieuChuan()==null||x.getMaBoTieuChuan().isBlank()||!boRepo.existsById(x.getMaBoTieuChuan())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn bộ tiêu chuẩn hợp lệ");
        ChuongTrinhDaoTao planCtdt=getCtdt(x.getMaCTDT());
        if(x.getMaPhienBanCTDT()==null||x.getMaPhienBanCTDT().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn phiên bản CTĐT");
        PhienBanCTDT chosenVersion=(planCtdt.getPhienBan()==null?List.<PhienBanCTDT>of():planCtdt.getPhienBan()).stream().filter(v->Objects.equals(v.getMaPhienBan(),x.getMaPhienBanCTDT())).findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phiên bản CTĐT không thuộc chương trình đã chọn"));
        Integer y=chosenVersion.getNamPhienBan()!=null?chosenVersion.getNamPhienBan():(chosenVersion.getNamHoc()!=null&&chosenVersion.getNamHoc().matches("\\d{4}")?Integer.valueOf(chosenVersion.getNamHoc()):null);
        if(y==null||y%2!=0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kế hoạch chỉ được gắn với phiên bản CTĐT năm chẵn");
        if(x.getNgayBatDau()!=null&&x.getNgayKetThuc()!=null&&x.getNgayKetThuc().isBefore(x.getNgayBatDau())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Ngày kết thúc không được trước ngày bắt đầu");
        x.setTrangThai(old==null?"Đang lập":Optional.ofNullable(old.getTrangThai()).orElse("Đang lập"));
        if(old!=null) x.setQuyTrinhDuyet(old.getQuyTrinhDuyet());
        if(x.getQuyTrinhDuyet()==null)x.setQuyTrinhDuyet(new ArrayList<>());
        return khRepo.save(x);
    }
    public KeHoachTuDanhGia guiKeHoachKhaoThi(String id,String userId){
        KeHoachTuDanhGia x=khRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy kế hoạch"));
        String state=Optional.ofNullable(x.getTrangThai()).orElse("Đang lập");
        if(!List.of("Đang lập","Yêu cầu chỉnh sửa").contains(state))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kế hoạch không ở trạng thái cho phép gửi duyệt");
        if(x.getNgayBatDau()==null||x.getNgayKetThuc()==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng nhập đầy đủ thời gian kế hoạch trước khi gửi duyệt");
        x.setTrangThai("Chờ khảo thí duyệt");
        if(x.getQuyTrinhDuyet()==null)x.setQuyTrinhDuyet(new ArrayList<>());
        Map<String,Object> r=new LinkedHashMap<>();r.put("vaiTro","Thư ký chương trình");r.put("maNguoiDung",userId);r.put("trangThai","Gửi duyệt");r.put("ngay",LocalDateTime.now());x.getQuyTrinhDuyet().add(r);
        return khRepo.save(x);
    }
    public KeHoachTuDanhGia khaoThiDuyetKeHoach(String id,String ketQua,String nhanXet,String userId){
        KeHoachTuDanhGia x=khRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy kế hoạch"));
        if(!"Chờ khảo thí duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Chỉ kế hoạch đang Chờ khảo thí duyệt mới được xử lý");
        if(!List.of("Duyệt","Yêu cầu chỉnh sửa").contains(Optional.ofNullable(ketQua).orElse("")))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả duyệt kế hoạch không hợp lệ");
        String st="Duyệt".equalsIgnoreCase(ketQua)?"Đã phê duyệt":"Yêu cầu chỉnh sửa"; x.setTrangThai(st);
        if(x.getQuyTrinhDuyet()==null)x.setQuyTrinhDuyet(new ArrayList<>());
        Map<String,Object> r=new LinkedHashMap<>();r.put("vaiTro","Bộ phận khảo thí");r.put("maNguoiDung",userId);r.put("trangThai",st);r.put("nhanXet",nhanXet);r.put("ngay",LocalDateTime.now());x.getQuyTrinhDuyet().add(r);
        return khRepo.save(x);
    }


    // ===== BÁO CÁO TỰ ĐÁNH GIÁ TOÀN CHƯƠNG TRÌNH (4 PHẦN) =====
    public List<BaoCaoTuDanhGia> baoCaoTuDanhGia(){ return bctdgRepo.findAll(); }
    public List<BaoCaoPhan> baoCaoPhan(){ return bcpRepo.findAll(); }
    public List<PhanCongBaoCao> phanCongBaoCao(){ return pcbcRepo.findAll(); }

    public Map<String,Object> cauTrucBaoCao(String maKeHoach){
        String planId=maKeHoach;
        if((planId==null||planId.isBlank())&&!khRepo.findAll().isEmpty()) planId=khRepo.findAll().get(0).getId();
        final String selectedPlan=planId;
        BaoCaoTuDanhGia master=bctdgRepo.findAll().stream().filter(x->Objects.equals(x.getMaKeHoach(),selectedPlan)).findFirst().orElse(null);
        if(master==null && selectedPlan!=null && !selectedPlan.isBlank()) master=taoKhungBaoCaoChoKeHoach(selectedPlan);
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("baoCao",master);
        if(master==null){m.put("phanChung",List.of());m.put("phan2",List.of());m.put("phanCong",List.of());return m;}
        final BaoCaoTuDanhGia selectedMaster=master;
        m.put("phanChung",bcpRepo.findAll().stream().filter(x->Objects.equals(x.getMaBaoCaoTuDanhGia(),selectedMaster.getId())).sorted(Comparator.comparing(x->Optional.ofNullable(x.getSoPhan()).orElse(99))).toList());
        m.put("phan2",bcnRepo.findAll().stream().filter(x->Objects.equals(x.getMaKeHoach(),selectedMaster.getMaKeHoach())).sorted(Comparator.comparingInt(x->standardNumber(x.getMaTieuChuan()))).toList());
        m.put("phanCong",pcbcRepo.findAll().stream().filter(x->Objects.equals(x.getMaBaoCaoTuDanhGia(),selectedMaster.getId())).sorted(Comparator.comparing(x->Optional.ofNullable(x.getSoPhan()).orElse(99))).toList());
        return m;
    }


    private BaoCaoTuDanhGia taoKhungBaoCaoChoKeHoach(String planId){
        KeHoachTuDanhGia plan=khRepo.findById(planId).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kế hoạch không tồn tại"));
        ChuongTrinhDaoTao ctdt=getCtdt(plan.getMaCTDT());
        BaoCaoTuDanhGia x=new BaoCaoTuDanhGia();x.setId(codes.nextBaoCaoTuDanhGia());x.setMaKeHoach(planId);x.setMaCTDT(plan.getMaCTDT());x.setMaPhienBanCTDT(plan.getMaPhienBanCTDT());x.setTenBaoCao("Báo cáo tự đánh giá "+Optional.ofNullable(ctdt.getTenCTDT()).orElse(plan.getMaCTDT()));x.setPhienBanBaoCao(1);x.setTrangThai("Đang thực hiện");x.setMucDoHoanThanh(0);x.setNgayTao(LocalDateTime.now());x.setNgayCapNhat(LocalDateTime.now());
        List<Map<String,Object>> structure=new ArrayList<>();
        structure.add(new LinkedHashMap<>(Map.of("soPhan",1,"tenPhan","Khái quát","loai","PHAN_CHUNG","nguonDuLieu","baoCaoPhan")));
        structure.add(new LinkedHashMap<>(Map.of("soPhan",2,"tenPhan","Tự đánh giá theo các tiêu chuẩn, tiêu chí","loai","NHOM_CONG_TAC","nguonDuLieu","baoCaoNhom")));
        structure.add(new LinkedHashMap<>(Map.of("soPhan",3,"tenPhan","Kết luận","loai","PHAN_CHUNG","nguonDuLieu","baoCaoPhan")));
        structure.add(new LinkedHashMap<>(Map.of("soPhan",4,"tenPhan","Phụ lục","loai","PHAN_CHUNG","nguonDuLieu","baoCaoPhan")));
        x.setCauTruc(structure);Map<String,Object> source=new LinkedHashMap<>();source.put("collection","baoCaoNhom");source.put("maKeHoach",planId);x.setNguonPhan2(source);return bctdgRepo.save(x);
    }

    public PhanCongBaoCao savePhanCongBaoCao(PhanCongBaoCao x,String adminId){
        if(x.getSoPhan()==null||!List.of(1,3,4).contains(x.getSoPhan())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Admin chỉ phân công Phần 1, Phần 3 hoặc Phần 4; Phần 2 do các nhóm công tác thực hiện");
        if(x.getMaBaoCaoTuDanhGia()==null||!bctdgRepo.existsById(x.getMaBaoCaoTuDanhGia())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Báo cáo tự đánh giá không hợp lệ");
        BaoCaoTuDanhGia master=bctdgRepo.findById(x.getMaBaoCaoTuDanhGia()).orElseThrow();
        x.setMaKeHoach(master.getMaKeHoach());
        if(x.getMaNguoiDung()==null||x.getMaNguoiDung().isBlank()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng chọn nhân sự phụ trách");
        NguoiDung assignee=userRepo.findById(x.getMaNguoiDung()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhân sự không tồn tại"));
        boolean reportRole=assignee.getPhanQuyen()!=null&&assignee.getPhanQuyen().stream().filter(Objects::nonNull).map(PhanQuyen::getMaVaiTro).anyMatch("VT007"::equals);
        if(!reportRole) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phần 1/3/4 chỉ giao cho tài khoản 'Nhân sự phụ trách báo cáo' (VT007)");
        boolean inGroup=nhomRepo.findAll().stream().anyMatch(g->g.getThanhVien()!=null&&g.getThanhVien().contains(x.getMaNguoiDung()));
        if(inGroup) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nhân sự phụ trách Phần 1/3/4 phải nằm ngoài các nhóm công tác");
        boolean duplicate=pcbcRepo.findAll().stream().anyMatch(p->Objects.equals(p.getMaBaoCaoTuDanhGia(),x.getMaBaoCaoTuDanhGia())&&Objects.equals(p.getSoPhan(),x.getSoPhan())&&!Objects.equals(p.getId(),x.getId()));
        if(duplicate) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phần này đã có người phụ trách; hãy cập nhật phân công hiện tại");
        if(x.getId()==null||x.getId().isBlank()){x.setId(codes.nextPhanCongBaoCao());x.setNgayTao(LocalDateTime.now());}
        if(x.getTrangThai()==null||x.getTrangThai().isBlank())x.setTrangThai("Đang thực hiện");
        if(x.getPhanTramHoanThanh()==null)x.setPhanTramHoanThanh(0);
        x.setMaNguoiTao(adminId);x.setNgayCapNhat(LocalDateTime.now());
        PhanCongBaoCao saved=pcbcRepo.save(x);
        BaoCaoPhan part=bcpRepo.findAll().stream().filter(p->Objects.equals(p.getMaBaoCaoTuDanhGia(),master.getId())&&Objects.equals(p.getSoPhan(),x.getSoPhan())).findFirst().orElse(null);
        if(part==null){part=new BaoCaoPhan();part.setId(codes.nextBaoCaoPhan(x.getSoPhan()));part.setMaBaoCaoTuDanhGia(master.getId());part.setMaKeHoach(master.getMaKeHoach());part.setSoPhan(x.getSoPhan());part.setTenPhan(x.getSoPhan()==1?"Phần 1 - Khái quát":x.getSoPhan()==3?"Phần 3 - Kết luận":"Phần 4 - Phụ lục");part.setNgayTao(LocalDateTime.now());}
        part.setMaPhanCong(saved.getId());part.setMaNguoiPhuTrach(saved.getMaNguoiDung());part.setTrangThai("Đang soạn");part.setMucDoDuyet(25);part.setCapDuyetHienTai("Người phụ trách");part.setNgayCapNhat(LocalDateTime.now());bcpRepo.save(part);
        return saved;
    }

    public List<PhanCongBaoCao> congViecBaoCao(String userId){return pcbcRepo.findAll().stream().filter(x->Objects.equals(x.getMaNguoiDung(),userId)||(x.getMaNhanSuHoTroList()!=null&&x.getMaNhanSuHoTroList().contains(userId))).toList();}
    public List<BaoCaoPhan> baoCaoPhanCuaNguoiDung(String userId){Set<String> ids=congViecBaoCao(userId).stream().map(PhanCongBaoCao::getId).collect(java.util.stream.Collectors.toSet());return bcpRepo.findAll().stream().filter(x->ids.contains(x.getMaPhanCong())).toList();}

    public BaoCaoPhan saveBaoCaoPhan(BaoCaoPhan input,String userId){
        BaoCaoPhan old=bcpRepo.findById(input.getId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phần báo cáo"));
        PhanCongBaoCao job=pcbcRepo.findById(old.getMaPhanCong()).orElseThrow(()->new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phần báo cáo chưa có phân công"));
        if(!Objects.equals(job.getMaNguoiDung(),userId)&&!(job.getMaNhanSuHoTroList()!=null&&job.getMaNhanSuHoTroList().contains(userId))) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không được chỉnh sửa phần báo cáo này");
        if(Optional.ofNullable(old.getTrangThai()).orElse("").startsWith("Chờ ")||"Đã duyệt".equalsIgnoreCase(old.getTrangThai())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phần báo cáo đang/đã duyệt nên không thể chỉnh sửa");
        old.setNoiDung(input.getNoiDung());old.setDanhSachMinhChungCapTruong(input.getDanhSachMinhChungCapTruong());old.setDanhSachMinhChungCapKhoa(input.getDanhSachMinhChungCapKhoa());old.setDanhSachMinhChungTieuChi(input.getDanhSachMinhChungTieuChi());old.setTrangThai("Đang soạn");old.setMucDoDuyet(25);old.setCapDuyetHienTai("Người phụ trách");old.setNgayCapNhat(LocalDateTime.now());return bcpRepo.save(old);
    }

    public BaoCaoPhan guiBaoCaoPhan(String id,String userId){
        BaoCaoPhan x=bcpRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phần báo cáo"));
        PhanCongBaoCao job=pcbcRepo.findById(x.getMaPhanCong()).orElseThrow();
        if(!Objects.equals(job.getMaNguoiDung(),userId)) throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Chỉ người phụ trách chính được gửi duyệt");
        if(x.getNoiDung()==null||x.getNoiDung().isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Vui lòng nhập nội dung trước khi gửi duyệt");
        x.setTrangThai("Chờ Admin duyệt");x.setMucDoDuyet(25);x.setCapDuyetHienTai("Người phụ trách");x.setNgayCapNhat(LocalDateTime.now());return bcpRepo.save(x);
    }

    public BaoCaoPhan adminXuLyBaoCaoPhan(String id,String ketQua,String nhanXet,String adminId){
        BaoCaoPhan x=bcpRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phần báo cáo"));
        if(!List.of("Duyệt","Yêu cầu chỉnh sửa","Góp ý").contains(Optional.ofNullable(ketQua).orElse(""))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả xử lý không hợp lệ");
        if(x.getFeedback()==null)x.setFeedback(new ArrayList<>());Map<String,Object> fb=new LinkedHashMap<>();fb.put("capDuyet","Admin");fb.put("maNguoiDuyet",adminId);fb.put("ketQua",ketQua);fb.put("nhanXet",nhanXet);fb.put("ngay",LocalDateTime.now());x.getFeedback().add(fb);
        if("Duyệt".equals(ketQua)){x.setTrangThai("Chờ Hội đồng cấp Khoa duyệt");x.setMucDoDuyet(50);x.setCapDuyetHienTai("Admin");}else if("Yêu cầu chỉnh sửa".equals(ketQua)){x.setTrangThai("Yêu cầu chỉnh sửa");x.setMucDoDuyet(25);x.setCapDuyetHienTai("Người phụ trách");}
        x.setNgayCapNhat(LocalDateTime.now());return bcpRepo.save(x);
    }

    public List<BaoCaoPhan> baoCaoPhanChoHoiDong(){
        return bcpRepo.findAll().stream().filter(x->List.of("Chờ Hội đồng cấp Khoa duyệt","Chờ TT Quản lý chất lượng duyệt","Đã duyệt").contains(Optional.ofNullable(x.getTrangThai()).orElse(""))).toList();
    }
    public BaoCaoPhan hoiDongXuLyBaoCaoPhan(String id,String ketQua,String nhanXet,String userId){
        BaoCaoPhan x=bcpRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phần báo cáo"));
        if(!"Chờ Hội đồng cấp Khoa duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phần báo cáo chưa đến bước Hội đồng cấp Khoa");
        if(!List.of("Duyệt","Yêu cầu chỉnh sửa").contains(Optional.ofNullable(ketQua).orElse(""))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả xử lý không hợp lệ");
        if(x.getFeedback()==null)x.setFeedback(new ArrayList<>());Map<String,Object> fb=new LinkedHashMap<>();fb.put("capDuyet","Hội đồng cấp Khoa");fb.put("maNguoiDuyet",userId);fb.put("ketQua",ketQua);fb.put("nhanXet",nhanXet);fb.put("ngay",LocalDateTime.now());x.getFeedback().add(fb);
        if("Duyệt".equals(ketQua)){x.setTrangThai("Chờ TT Quản lý chất lượng duyệt");x.setMucDoDuyet(75);x.setCapDuyetHienTai("Hội đồng cấp Khoa");}else{x.setTrangThai("Yêu cầu chỉnh sửa");x.setMucDoDuyet(25);x.setCapDuyetHienTai("Người phụ trách");}
        x.setNgayCapNhat(LocalDateTime.now());return bcpRepo.save(x);
    }
    public List<BaoCaoPhan> baoCaoPhanChoQlcl(){
        return bcpRepo.findAll().stream().filter(x->List.of("Chờ TT Quản lý chất lượng duyệt","Đã duyệt").contains(Optional.ofNullable(x.getTrangThai()).orElse(""))).toList();
    }
    public BaoCaoPhan qlclXuLyBaoCaoPhan(String id,String ketQua,String nhanXet,String userId){
        BaoCaoPhan x=bcpRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy phần báo cáo"));
        if(!"Chờ TT Quản lý chất lượng duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Phần báo cáo chưa đến bước TT Quản lý chất lượng");
        if(!List.of("Duyệt","Yêu cầu chỉnh sửa").contains(Optional.ofNullable(ketQua).orElse(""))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Kết quả xử lý không hợp lệ");
        if(x.getFeedback()==null)x.setFeedback(new ArrayList<>());Map<String,Object> fb=new LinkedHashMap<>();fb.put("capDuyet","TT Quản lý chất lượng");fb.put("maNguoiDuyet",userId);fb.put("ketQua",ketQua);fb.put("nhanXet",nhanXet);fb.put("ngay",LocalDateTime.now());x.getFeedback().add(fb);
        if("Duyệt".equals(ketQua)){x.setTrangThai("Đã duyệt");x.setMucDoDuyet(100);x.setCapDuyetHienTai("TT Quản lý chất lượng");}else{x.setTrangThai("Yêu cầu chỉnh sửa");x.setMucDoDuyet(25);x.setCapDuyetHienTai("Người phụ trách");}
        x.setNgayCapNhat(LocalDateTime.now());return bcpRepo.save(x);
    }


    // ===== HỖ TRỢ THÀNH VIÊN / THƯ KÝ NHÓM =====
    public Map<String,Object> thongTinThuKyNhom(String userId){
        List<NhomTieuChi> groups=nhomTieuChi().stream().filter(g->isSecretaryOfGroup(g,userId)).toList();
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("laThuKyNhom",!groups.isEmpty());
        m.put("nhom",groups);
        m.put("soNhom",groups.size());
        return m;
    }

    public List<DanhGiaMinhChung> danhGiaMinhChungCuaNguoiDung(String userId){
        Set<String> codes=minhChungThanhVien(userId).stream()
                .map(MinhChung::getMaMinhChung).filter(Objects::nonNull)
                .collect(java.util.stream.Collectors.toSet());
        List<DanhGiaMinhChung> rows=dgmcRepo.findAll().stream()
                .filter(x->codes.contains(x.getMaMinhChung()))
                .sorted(Comparator.comparing(DanhGiaMinhChung::getNgayDuyet,
                        Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .toList();
        return rows;
    }

    public Map<String,Object> nguonBaoCaoNhom(String userId,String maNhom,String maKeHoach,String maTieuChuan){
        NhomTieuChi g=nhomRepo.findById(maNhom).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy nhóm"));
        if(!isSecretaryOfGroup(g,userId) && !isLeaderOfGroup(g,userId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Bạn không thuộc phạm vi tổng hợp báo cáo của nhóm này");
        List<PhanCong> criteriaAssignments=pcRepo.findAll().stream()
                .filter(p->Objects.equals(p.getMaNhomTieuChi(),maNhom)
                        && Objects.equals(p.getMaKeHoach(),maKeHoach)
                        && Objects.equals(p.getMaTieuChuan(),maTieuChuan)
                        && p.getMaTieuChi()!=null && !p.getMaTieuChi().isBlank()
                        && !"Hủy phân công".equalsIgnoreCase(Optional.ofNullable(p.getTrangThai()).orElse("")))
                .sorted(Comparator.comparing(PhanCong::getMaTieuChi,Comparator.nullsLast(String::compareTo)))
                .toList();
        List<Map<String,Object>> rows=new ArrayList<>();
        int ready=0;
        for(PhanCong p:criteriaAssignments){
            BaoCaoTieuChi bc=bcRepo.findAll().stream()
                    .filter(x->Objects.equals(x.getMaKeHoach(),maKeHoach)&&Objects.equals(x.getMaTieuChi(),p.getMaTieuChi()))
                    .findFirst().orElse(null);
            Map<String,Object> row=new LinkedHashMap<>();
            row.put("maTieuChi",p.getMaTieuChi()); row.put("maPhanCong",p.getId()); row.put("maNguoiDung",p.getMaNguoiDung());
            row.put("baoCaoId",bc==null?null:bc.getId()); row.put("trangThai",bc==null?"Chưa có báo cáo":bc.getTrangThai());
            row.put("mucDoDuyet",bc==null?0:Optional.ofNullable(bc.getMucDoDuyet()).orElse(25));
            row.put("nhanDe",bc==null?null:bc.getNhanDeTieuChi());
            boolean ok=bc!=null && Optional.ofNullable(bc.getMucDoDuyet()).orElse(25)>=50;
            row.put("sanSangTongHop",ok); if(ok)ready++;
            rows.add(row);
        }
        Map<String,Object> result=new LinkedHashMap<>();
        result.put("tieuChi",rows); result.put("tongTieuChi",rows.size()); result.put("sanSang",ready);
        result.put("duDieuKien",!rows.isEmpty()&&ready==rows.size());
        return result;
    }

    // ===== THEO DÕI BÁO CÁO DÀNH CHO THƯ KÝ CHƯƠNG TRÌNH =====
    public Map<String,Object> theoDoiBaoCaoThuKy(String maKeHoach){
        List<BaoCaoTieuChi> criteria=baoCao().stream().filter(x->samePlan(maKeHoach,x.getMaKeHoach())).toList();
        List<BaoCaoNhom> groups=baoCaoNhom().stream().filter(x->samePlan(maKeHoach,x.getMaKeHoach())).toList();
        List<BaoCaoPhan> parts=baoCaoPhan().stream().filter(x->samePlan(maKeHoach,x.getMaKeHoach())).toList();
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("baoCaoTieuChi",criteria); m.put("baoCaoNhom",groups); m.put("baoCaoPhan",parts);
        m.put("tongBaoCaoTieuChi",criteria.size());
        m.put("baoCaoTieuChiTu50",criteria.stream().filter(x->Optional.ofNullable(x.getMucDoDuyet()).orElse(25)>=50).count());
        m.put("baoCaoTieuChiHoanTat",criteria.stream().filter(x->Optional.ofNullable(x.getMucDoDuyet()).orElse(25)>=100).count());
        m.put("baoCaoNhomDaDuyet",groups.stream().filter(x->"Đã duyệt".equalsIgnoreCase(Optional.ofNullable(x.getTrangThai()).orElse(""))).count());
        m.put("baoCaoPhanHoanTat",parts.stream().filter(x->Optional.ofNullable(x.getMucDoDuyet()).orElse(25)>=100).count());
        return m;
    }

    public Map<String,Object> dashboardThuKy(){
        Map<String,Object> m=new LinkedHashMap<>();
        m.put("keHoach",keHoach().size());
        m.put("minhChungChoDuyet",minhChungChoThuKy().size());
        Map<String,Object> reports=theoDoiBaoCaoThuKy(null);
        m.put("baoCaoTieuChi",reports.get("tongBaoCaoTieuChi"));
        m.put("baoCaoHoanTat",reports.get("baoCaoTieuChiHoanTat"));
        m.putAll(tongHopThuKy());
        return m;
    }

    public Map<String,Object> tongHopThuKy(){return tongHopHoiDong();}

}
