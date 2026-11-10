package com.huit.kiemdinh.controller;

import org.springframework.stereotype.Controller;

import com.huit.kiemdinh.security.AuthService;

import com.huit.kiemdinh.service.HeThongService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

import java.nio.charset.StandardCharsets;

@Controller
public class PageController {
    private final HeThongService service;
    private final AuthService auth;
    public PageController(HeThongService service, AuthService auth) { this.service=service; this.auth=auth; }
    @GetMapping("/") public String root() {
        return "redirect:/login";
    }
    @GetMapping("/admin") public String dashboard() {
        return "admin/dashboard";
    }
    @GetMapping("/admin/ctdt") public String ctdt() {
        return "admin/ctdt";
    }
    @GetMapping("/admin/tieu-chuan") public String tc() {
        return "admin/tieu-chuan";
    }
    @GetMapping("/admin/nhom") public String nhom() {
        return "admin/nhom";
    }
    @GetMapping("/admin/phan-cong") public String pc() {
        return "admin/phan-cong";
    }
    @GetMapping("/admin/minh-chung") public String mc() {
        return "admin/minh-chung";
    }
    @GetMapping("/admin/bao-cao") public String bc() {
        return "admin/bao-cao";
    }
    @GetMapping("/admin/he-thong") public String ht() {
        return "admin/he-thong";
    }
    @GetMapping("/admin/thong-ke") public String thongKe() {
        return "admin/thong-ke";
    }
    @GetMapping("/hoi-dong") public String hd() {
        return "hoi-dong/dashboard";
    }
    @GetMapping("/hoi-dong/minh-chung") public String hdMc() {
        return "hoi-dong/minh-chung";
    }
    @GetMapping("/hoi-dong/bao-cao") public String hdBc() {
        return "hoi-dong/bao-cao";
    }
    @GetMapping("/hoi-dong/danh-gia") public String hdDg() {
        return "hoi-dong/danh-gia";
    }
    @GetMapping("/hoi-dong/tong-hop") public String hdTh() {
        return "hoi-dong/tong-hop";
    }
    @GetMapping("/truong-nhom") public String tn() {
        return "truong-nhom/dashboard";
    }
    @GetMapping("/truong-nhom/thanh-vien") public String tnTv() {
        return "truong-nhom/thanh-vien";
    }
    @GetMapping("/truong-nhom/phan-cong") public String tnPc() {
        return "truong-nhom/phan-cong";
    }
    @GetMapping("/truong-nhom/minh-chung") public String tnMc() {
        return "truong-nhom/minh-chung";
    }
    @GetMapping("/truong-nhom/tien-do") public String tnTd() {
        return "truong-nhom/tien-do";
    }
    @GetMapping("/truong-nhom/bao-cao") public String tnBc() {
        return "truong-nhom/bao-cao";
    }
    @GetMapping("/truong-nhom/bao-cao-nhom") public String tnBcn() {
        return "truong-nhom/bao-cao-nhom";
    }
    @GetMapping("/thanh-vien") public String tv() {
        return "thanh-vien/dashboard";
    }
    @GetMapping("/thanh-vien/cong-viec") public String tvCv() {
        return "thanh-vien/cong-viec";
    }
    @GetMapping("/thanh-vien/minh-chung") public String tvMc() {
        return "thanh-vien/minh-chung";
    }
    @GetMapping("/thanh-vien/bao-cao") public String tvBc() {
        return "thanh-vien/bao-cao";
    }
    @GetMapping("/thanh-vien/tong-hop-nhom") public String tvBcn(HttpSession session) {
        String userId=(String)session.getAttribute(AuthService.SESSION_USER_ID);
        return service.laThuKyCuaNhom(userId) ? "thanh-vien/tong-hop-nhom" : "redirect:/thanh-vien";
    }
    @GetMapping("/thu-ky") public String tk() {
        return "thu-ky/dashboard";
    }
    @GetMapping("/thu-ky/ke-hoach") public String tkKh() {
        return "thu-ky/ke-hoach";
    }
    @GetMapping("/thu-ky/minh-chung") public String tkMc() {
        return "thu-ky/minh-chung";
    }
    @GetMapping("/thu-ky/tong-hop") public String tkTh() {
        return "thu-ky/tong-hop";
    }
    @GetMapping("/thu-ky/bao-cao") public String tkBc() {
        return "thu-ky/bao-cao";
    }
    @GetMapping("/khao-thi") @ResponseBody public ResponseEntity<String> kt() throws IOException {
        return qlclPage("dashboard");
    }
    @GetMapping("/khao-thi/ctdt") @ResponseBody public ResponseEntity<String> ktCtdt() throws IOException {
        return qlclPage("ctdt");
    }
    @GetMapping("/khao-thi/ke-hoach") @ResponseBody public ResponseEntity<String> ktKh() throws IOException {
        return qlclPage("ke-hoach");
    }
    @GetMapping("/khao-thi/tien-do") @ResponseBody public ResponseEntity<String> ktTienDo() throws IOException {
        return qlclPage("tien-do");
    }
    @GetMapping("/khao-thi/bao-cao") @ResponseBody public ResponseEntity<String> ktBaoCao() throws IOException {
        return qlclPage("bao-cao");
    }
    @GetMapping("/khao-thi/minh-chung") @ResponseBody public ResponseEntity<String> ktMinhChung() throws IOException {
        return qlclPage("minh-chung");
    }
    @GetMapping("/khao-thi/phan-cong") @ResponseBody public ResponseEntity<String> ktPhanCong() throws IOException {
        return qlclPage("phan-cong");
    }
    @GetMapping("/khao-thi/tong-hop") @ResponseBody public ResponseEntity<String> ktTh() throws IOException {
        return qlclPage("tong-hop");
    }
    @GetMapping("/khao-thi/thong-ke") @ResponseBody public ResponseEntity<String> ktThongKe() throws IOException {
        return qlclPage("thong-ke");
    }
    private ResponseEntity<String> qlclPage(String name) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/khao-thi/" + name + ".html");
        String html;
        try (var in = resource.getInputStream()) {
            html = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(html);
    }
    @GetMapping("/nhan-su-bao-cao") public String nsbc() {
        return "nhan-su-bao-cao/dashboard";
    }
    @GetMapping("/nhan-su-bao-cao/noi-dung") public String nsbcNoiDung() {
        return "nhan-su-bao-cao/noi-dung";
    }
}
