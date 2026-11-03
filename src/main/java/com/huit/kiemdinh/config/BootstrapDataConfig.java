package com.huit.kiemdinh.config;

import com.huit.kiemdinh.model.NguoiDung;
import com.huit.kiemdinh.model.PhanQuyen;
import com.huit.kiemdinh.model.VaiTro;
import com.huit.kiemdinh.repository.NguoiDungRepository;
import com.huit.kiemdinh.repository.VaiTroRepository;
import com.huit.kiemdinh.security.AuthService;
import com.huit.kiemdinh.service.CodeGeneratorService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Component
@Order(1)
public class BootstrapDataConfig implements ApplicationRunner {
    private final VaiTroRepository roleRepo;
    private final NguoiDungRepository userRepo;
    private final CodeGeneratorService codes;
    private final AuthService auth;
    private final String defaultAdminEmail;
    private final String defaultAdminPassword;

    public BootstrapDataConfig(VaiTroRepository roleRepo, NguoiDungRepository userRepo,
                               CodeGeneratorService codes, AuthService auth,
                               @Value("${app.default-admin.email:admin@huit.edu.vn}") String defaultAdminEmail,
                               @Value("${app.default-admin.password:123456}") String defaultAdminPassword){
        this.roleRepo=roleRepo;
        this.userRepo=userRepo;
        this.codes=codes;
        this.auth=auth;
        this.defaultAdminEmail=defaultAdminEmail;
        this.defaultAdminPassword=defaultAdminPassword;
    }

    @Override public void run(ApplicationArguments args){
        // Bản 28 không tự migrate Bộ tiêu chuẩn/TC/nhóm. MongoDB là nguồn dữ liệu nghiệp vụ đã chốt.
        ensureRoles();
        ensureDefaultAdmin();
    }

    private void ensureRoles(){
        List<VaiTro> defaults=List.of(
            role("VT001","Quản trị","Quản lý nhân sự, CTĐT, nhóm và phân công"),
            role("VT002","Hội đồng tự đánh giá","Góp ý, duyệt báo cáo và đánh giá tiêu chí"),
            role("VT003","Trưởng nhóm tiêu chí","Điều hành nhóm, phân công tiêu chí và duyệt cấp nhóm"),
            role("VT004","Thành viên nhóm","Thu thập minh chứng và viết báo cáo tiêu chí; có thể là Thư ký nhóm"),
            role("VT005","Thư ký chương trình","Lập kế hoạch, quản lý minh chứng và tổng hợp hồ sơ"),
            role("VT006","Trung tâm Quản lý chất lượng","Phê duyệt kế hoạch, giám sát tiến độ, báo cáo và minh chứng tự đánh giá"),
            role("VT007","Nhân sự phụ trách báo cáo","Nhân sự ngoài nhóm phụ trách Phần 1, Phần 3 hoặc Phần 4")
        );
        for(VaiTro d:defaults){
            VaiTro current=roleRepo.findById(d.getId()).orElse(null);
            if(current==null) roleRepo.save(d);
            else if(!Objects.equals(current.getTenVaiTro(),d.getTenVaiTro()) || !Objects.equals(current.getMoTa(),d.getMoTa())){
                current.setTenVaiTro(d.getTenVaiTro()); current.setMoTa(d.getMoTa()); roleRepo.save(current);
            }
        }
    }

    private VaiTro role(String id,String name,String desc){VaiTro v=new VaiTro();v.setId(id);v.setTenVaiTro(name);v.setMoTa(desc);return v;}

    private void ensureDefaultAdmin(){
        if(roleRepo.findById("VT001").isEmpty()) return;
        Optional<NguoiDung> existing=userRepo.findByEmailIgnoreCase(defaultAdminEmail);
        if(existing.isEmpty()){
            NguoiDung u=new NguoiDung();u.setId(codes.nextNguoiDung());u.setHoTen("Nguyễn Văn Quản Trị");u.setEmail(defaultAdminEmail);u.setMatKhau(auth.encodePassword(defaultAdminPassword));u.setTrangThai("Hoạt động");u.setPhanQuyen(new ArrayList<>(List.of(new PhanQuyen("VT001"))));userRepo.save(u);return;
        }
        NguoiDung u=existing.get();boolean changed=false;
        if(u.getHoTen()==null||u.getHoTen().isBlank()||"Quản trị hệ thống".equalsIgnoreCase(u.getHoTen())){u.setHoTen("Nguyễn Văn Quản Trị");changed=true;}
        if(u.getMatKhau()==null||u.getMatKhau().isBlank()){u.setMatKhau(auth.encodePassword(defaultAdminPassword));changed=true;}
        if(u.getTrangThai()==null||u.getTrangThai().isBlank()){u.setTrangThai("Hoạt động");changed=true;}
        if(u.getPhanQuyen()==null)u.setPhanQuyen(new ArrayList<>());
        if(u.getPhanQuyen().stream().noneMatch(p->p!=null&&"VT001".equals(p.getMaVaiTro()))){u.getPhanQuyen().add(new PhanQuyen("VT001"));changed=true;}
        if(changed)userRepo.save(u);
    }
}
