package com.huit.kiemdinh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/") public String root(){ return "redirect:/login"; }

    @GetMapping("/admin") public String dashboard(){ return "admin/dashboard"; }
    @GetMapping("/admin/ctdt") public String ctdt(){ return "admin/ctdt"; }
    @GetMapping("/admin/tieu-chuan") public String tc(){ return "admin/tieu-chuan"; }
    @GetMapping("/admin/nhom") public String nhom(){ return "admin/nhom"; }
    @GetMapping("/admin/phan-cong") public String pc(){ return "admin/phan-cong"; }
    @GetMapping("/admin/minh-chung") public String mc(){ return "admin/minh-chung"; }
    @GetMapping("/admin/bao-cao") public String bc(){ return "admin/bao-cao"; }
    @GetMapping("/admin/he-thong") public String ht(){ return "admin/he-thong"; }

    @GetMapping("/hoi-dong") public String hd(){ return "hoi-dong/dashboard"; }
    @GetMapping("/hoi-dong/minh-chung") public String hdMc(){ return "hoi-dong/minh-chung"; }
    @GetMapping("/hoi-dong/bao-cao") public String hdBc(){ return "hoi-dong/bao-cao"; }
    @GetMapping("/hoi-dong/danh-gia") public String hdDg(){ return "hoi-dong/danh-gia"; }
    @GetMapping("/hoi-dong/tong-hop") public String hdTh(){ return "hoi-dong/tong-hop"; }

    @GetMapping("/truong-nhom") public String tn(){ return "truong-nhom/dashboard"; }
    @GetMapping("/truong-nhom/phan-cong") public String tnPc(){ return "truong-nhom/phan-cong"; }
    @GetMapping("/truong-nhom/minh-chung") public String tnMc(){ return "truong-nhom/minh-chung"; }
    @GetMapping("/truong-nhom/tien-do") public String tnTd(){ return "truong-nhom/tien-do"; }
    @GetMapping("/truong-nhom/bao-cao-nhom") public String tnBcn(){ return "truong-nhom/bao-cao-nhom"; }

    @GetMapping("/thanh-vien") public String tv(){ return "thanh-vien/dashboard"; }
    @GetMapping("/thanh-vien/cong-viec") public String tvCv(){ return "thanh-vien/cong-viec"; }
    @GetMapping("/thanh-vien/minh-chung") public String tvMc(){ return "thanh-vien/minh-chung"; }
    @GetMapping("/thanh-vien/bao-cao") public String tvBc(){ return "thanh-vien/bao-cao"; }
    @GetMapping("/thanh-vien/tong-hop-nhom") public String tvBcn(){ return "thanh-vien/tong-hop-nhom"; }

    @GetMapping("/thu-ky") public String tk(){ return "thu-ky/dashboard"; }
    @GetMapping("/thu-ky/ke-hoach") public String tkKh(){ return "thu-ky/ke-hoach"; }
    @GetMapping("/thu-ky/minh-chung") public String tkMc(){ return "thu-ky/minh-chung"; }
    @GetMapping("/thu-ky/tong-hop") public String tkTh(){ return "thu-ky/tong-hop"; }

    @GetMapping("/khao-thi") public String kt(){ return "khao-thi/dashboard"; }
    @GetMapping("/khao-thi/ke-hoach") public String ktKh(){ return "khao-thi/ke-hoach"; }
    @GetMapping("/khao-thi/tong-hop") public String ktTh(){ return "khao-thi/tong-hop"; }
}
