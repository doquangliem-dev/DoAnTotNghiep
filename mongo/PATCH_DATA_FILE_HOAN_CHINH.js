// Patch dữ liệu/file hoàn chỉnh - KHÔNG thay đổi CTĐT và KHÔNG ghi đè file người dùng upload thật.
db = db.getSiblingDB("HeThongKiemDinh");
function isUserUpload(p) { return typeof p === "string" && /_\d{13}(?:_[^.]*)?\.[A-Za-z0-9]+$/.test(p); }
db.tieuChi.updateOne({ _id: "TC1.1" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC1.1-01", "tenMinhChung": "Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.1-02", "tenMinhChung": "Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.1-03", "tenMinhChung": "Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.1-04", "tenMinhChung": "Biên bản lấy ý kiến các bên liên quan về mục tiêu và chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.1-05", "tenMinhChung": "Ma trận liên kết chuẩn đầu ra CTĐT với chuẩn đầu ra học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.1-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC1.1-1", "soChiBao": "TC1.1.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Mục tiêu của chương trình đào tạo được xác định rõ ràng, phù hợp và gắn kết với sứ mạng, tầm nhìn, chiến lược phát triển của cơ sở đào tạo; phù hợp với mục tiêu của giáo dục đại học.", "thuTu": 1 }, { "maChiBao": "CB-TC1.1-2", "soChiBao": "TC1.1.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC1.1-3", "soChiBao": "TC1.1.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC1.2" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC1.2-01", "tenMinhChung": "Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.2-02", "tenMinhChung": "Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.2-03", "tenMinhChung": "Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.2-04", "tenMinhChung": "Biên bản lấy ý kiến các bên liên quan về mục tiêu và chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.2-05", "tenMinhChung": "Ma trận liên kết chuẩn đầu ra CTĐT với chuẩn đầu ra học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.2-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC1.2-1", "soChiBao": "TC1.2.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Chuẩn đầu ra của chương trình đào tạo được xây dựng rõ ràng; phù hợp với sứ mạng, tầm nhìn và mục tiêu chiến lược của cơ sở đào tạo và được phổ biến đến các bên liên quan.", "thuTu": 1 }, { "maChiBao": "CB-TC1.2-2", "soChiBao": "TC1.2.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC1.2-3", "soChiBao": "TC1.2.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC1.3" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC1.3-01", "tenMinhChung": "Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.3-02", "tenMinhChung": "Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.3-03", "tenMinhChung": "Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.3-04", "tenMinhChung": "Biên bản lấy ý kiến các bên liên quan về mục tiêu và chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.3-05", "tenMinhChung": "Ma trận liên kết chuẩn đầu ra CTĐT với chuẩn đầu ra học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.3-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC1.3-1", "soChiBao": "TC1.3.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Chuẩn đầu ra của chương trình đào tạo phù hợp với Khung trình độ quốc gia Việt Nam và chuẩn chương trình đào tạo nhóm ngành.", "thuTu": 1 }, { "maChiBao": "CB-TC1.3-2", "soChiBao": "TC1.3.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC1.3-3", "soChiBao": "TC1.3.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC1.4" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC1.4-01", "tenMinhChung": "Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.4-02", "tenMinhChung": "Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.4-03", "tenMinhChung": "Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.4-04", "tenMinhChung": "Biên bản lấy ý kiến các bên liên quan về mục tiêu và chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.4-05", "tenMinhChung": "Ma trận liên kết chuẩn đầu ra CTĐT với chuẩn đầu ra học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.4-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC1.4-1", "soChiBao": "TC1.4.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Chuẩn đầu ra của tất cả các học phần được xây dựng phù hợp và tương thích với chuẩn đầu ra của chương trình đào tạo đã công bố.", "thuTu": 1 }, { "maChiBao": "CB-TC1.4-2", "soChiBao": "TC1.4.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC1.4-3", "soChiBao": "TC1.4.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC1.5" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC1.5-01", "tenMinhChung": "Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.5-02", "tenMinhChung": "Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.5-03", "tenMinhChung": "Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.5-04", "tenMinhChung": "Biên bản lấy ý kiến các bên liên quan về mục tiêu và chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.5-05", "tenMinhChung": "Ma trận liên kết chuẩn đầu ra CTĐT với chuẩn đầu ra học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.5-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC1.5-1", "soChiBao": "TC1.5.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Chuẩn đầu ra của chương trình đào tạo phản ánh rõ yêu cầu của các bên liên quan, đặc biệt là các bên liên quan bên ngoài.", "thuTu": 1 }, { "maChiBao": "CB-TC1.5-2", "soChiBao": "TC1.5.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC1.5-3", "soChiBao": "TC1.5.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC1.6" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC1.6-01", "tenMinhChung": "Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.6-02", "tenMinhChung": "Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.6-03", "tenMinhChung": "Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.6-04", "tenMinhChung": "Biên bản lấy ý kiến các bên liên quan về mục tiêu và chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.6-05", "tenMinhChung": "Ma trận liên kết chuẩn đầu ra CTĐT với chuẩn đầu ra học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC1.6-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC1.6-1", "soChiBao": "TC1.6.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Chuẩn đầu ra của chương trình đào tạo được đo lường, đánh giá tại thời điểm người học tốt nghiệp.", "thuTu": 1 }, { "maChiBao": "CB-TC1.6-2", "soChiBao": "TC1.6.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC1.6-3", "soChiBao": "TC1.6.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC2.1" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC2.1-01", "tenMinhChung": "Chương trình đào tạo và đề cương chi tiết các học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.1-02", "tenMinhChung": "Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.1-03", "tenMinhChung": "Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.1-04", "tenMinhChung": "Hồ sơ đối sánh chương trình với các chương trình tham chiếu", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.1-05", "tenMinhChung": "Quy định/quy trình định kỳ rà soát và cập nhật chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.1-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC2.1-1", "soChiBao": "TC2.1.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Bản mô tả chương trình đào tạo và đề cương các học phần có đủ thông tin, được cập nhật, phê duyệt và công bố công khai.", "thuTu": 1 }, { "maChiBao": "CB-TC2.1-2", "soChiBao": "TC2.1.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC2.1-3", "soChiBao": "TC2.1.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC2.2" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC2.2-01", "tenMinhChung": "Chương trình đào tạo và đề cương chi tiết các học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.2-02", "tenMinhChung": "Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.2-03", "tenMinhChung": "Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.2-04", "tenMinhChung": "Hồ sơ đối sánh chương trình với các chương trình tham chiếu", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.2-05", "tenMinhChung": "Quy định/quy trình định kỳ rà soát và cập nhật chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.2-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC2.2-1", "soChiBao": "TC2.2.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển để bảo đảm người học đạt chuẩn đầu ra và có khối lượng học tập phù hợp.", "thuTu": 1 }, { "maChiBao": "CB-TC2.2-2", "soChiBao": "TC2.2.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC2.2-3", "soChiBao": "TC2.2.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC2.3" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC2.3-01", "tenMinhChung": "Chương trình đào tạo và đề cương chi tiết các học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.3-02", "tenMinhChung": "Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.3-03", "tenMinhChung": "Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.3-04", "tenMinhChung": "Hồ sơ đối sánh chương trình với các chương trình tham chiếu", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.3-05", "tenMinhChung": "Quy định/quy trình định kỳ rà soát và cập nhật chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.3-06", "tenMinhChung": "Phiếu khảo sát, tổng hợp ý kiến và biên bản tham vấn các bên liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC2.3-1", "soChiBao": "TC2.3.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển dựa trên phản hồi và nhu cầu của các bên liên quan.", "thuTu": 1 }, { "maChiBao": "CB-TC2.3-2", "soChiBao": "TC2.3.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC2.3-3", "soChiBao": "TC2.3.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC2.4" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC2.4-01", "tenMinhChung": "Chương trình đào tạo và đề cương chi tiết các học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.4-02", "tenMinhChung": "Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.4-03", "tenMinhChung": "Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.4-04", "tenMinhChung": "Hồ sơ đối sánh chương trình với các chương trình tham chiếu", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.4-05", "tenMinhChung": "Quy định/quy trình định kỳ rà soát và cập nhật chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.4-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC2.4-1", "soChiBao": "TC2.4.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Đóng góp của từng học phần trong việc đạt được chuẩn đầu ra của chương trình đào tạo là rõ ràng.", "thuTu": 1 }, { "maChiBao": "CB-TC2.4-2", "soChiBao": "TC2.4.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC2.4-3", "soChiBao": "TC2.4.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC2.5" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC2.5-01", "tenMinhChung": "Chương trình đào tạo và đề cương chi tiết các học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.5-02", "tenMinhChung": "Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.5-03", "tenMinhChung": "Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.5-04", "tenMinhChung": "Hồ sơ đối sánh chương trình với các chương trình tham chiếu", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.5-05", "tenMinhChung": "Quy định/quy trình định kỳ rà soát và cập nhật chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.5-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC2.5-1", "soChiBao": "TC2.5.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Chương trình đào tạo có cấu trúc logic, trình tự hợp lý, có tính linh hoạt và tích hợp.", "thuTu": 1 }, { "maChiBao": "CB-TC2.5-2", "soChiBao": "TC2.5.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC2.5-3", "soChiBao": "TC2.5.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC2.6" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC2.6-01", "tenMinhChung": "Chương trình đào tạo và đề cương chi tiết các học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.6-02", "tenMinhChung": "Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.6-03", "tenMinhChung": "Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.6-04", "tenMinhChung": "Hồ sơ đối sánh chương trình với các chương trình tham chiếu", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.6-05", "tenMinhChung": "Quy định/quy trình định kỳ rà soát và cập nhật chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.6-06", "tenMinhChung": "Danh mục đề tài, công bố, sản phẩm nghiên cứu và biên bản nghiệm thu liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC2.6-1", "soChiBao": "TC2.6.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Cấu trúc và nội dung chương trình đào tạo thể hiện rõ các học phần bắt buộc, tự chọn, lý thuyết, thực hành, trải nghiệm và nghiên cứu khoa học.", "thuTu": 1 }, { "maChiBao": "CB-TC2.6-2", "soChiBao": "TC2.6.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC2.6-3", "soChiBao": "TC2.6.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC2.7" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC2.7-01", "tenMinhChung": "Chương trình đào tạo và đề cương chi tiết các học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.7-02", "tenMinhChung": "Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.7-03", "tenMinhChung": "Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.7-04", "tenMinhChung": "Hồ sơ đối sánh chương trình với các chương trình tham chiếu", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.7-05", "tenMinhChung": "Quy định/quy trình định kỳ rà soát và cập nhật chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC2.7-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC2.7-1", "soChiBao": "TC2.7.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Cấu trúc và nội dung chương trình đào tạo được rà soát, đánh giá và cải tiến theo quy trình, bảo đảm tính cập nhật.", "thuTu": 1 }, { "maChiBao": "CB-TC2.7-2", "soChiBao": "TC2.7.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC2.7-3", "soChiBao": "TC2.7.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC3.1" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC3.1-01", "tenMinhChung": "Kế hoạch giảng dạy và học tập của chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.1-02", "tenMinhChung": "Đề cương học phần thể hiện phương pháp dạy và học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.1-03", "tenMinhChung": "Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.1-04", "tenMinhChung": "Biên bản/hồ sơ lấy phản hồi người học về hoạt động dạy học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.1-05", "tenMinhChung": "Hồ sơ cải tiến phương pháp dạy và học sau phản hồi", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.1-06", "tenMinhChung": "Phiếu khảo sát, tổng hợp ý kiến và biên bản tham vấn các bên liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC3.1-1", "soChiBao": "TC3.1.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Triết lý giáo dục của cơ sở đào tạo được tuyên bố rõ ràng, phổ biến đến các bên liên quan và truyền tải vào hoạt động dạy và học.", "thuTu": 1 }, { "maChiBao": "CB-TC3.1-2", "soChiBao": "TC3.1.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC3.1-3", "soChiBao": "TC3.1.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC3.2" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC3.2-01", "tenMinhChung": "Kế hoạch giảng dạy và học tập của chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.2-02", "tenMinhChung": "Đề cương học phần thể hiện phương pháp dạy và học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.2-03", "tenMinhChung": "Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.2-04", "tenMinhChung": "Biên bản/hồ sơ lấy phản hồi người học về hoạt động dạy học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.2-05", "tenMinhChung": "Hồ sơ cải tiến phương pháp dạy và học sau phản hồi", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.2-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC3.2-1", "soChiBao": "TC3.2.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Hoạt động dạy và học được thiết kế tương thích với chuẩn đầu ra của chương trình đào tạo.", "thuTu": 1 }, { "maChiBao": "CB-TC3.2-2", "soChiBao": "TC3.2.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC3.2-3", "soChiBao": "TC3.2.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC3.3" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC3.3-01", "tenMinhChung": "Kế hoạch giảng dạy và học tập của chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.3-02", "tenMinhChung": "Đề cương học phần thể hiện phương pháp dạy và học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.3-03", "tenMinhChung": "Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.3-04", "tenMinhChung": "Biên bản/hồ sơ lấy phản hồi người học về hoạt động dạy học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.3-05", "tenMinhChung": "Hồ sơ cải tiến phương pháp dạy và học sau phản hồi", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.3-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC3.3-1", "soChiBao": "TC3.3.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Hoạt động dạy và học thể hiện việc học tập chủ động và phát triển khả năng học tập suốt đời của người học.", "thuTu": 1 }, { "maChiBao": "CB-TC3.3-2", "soChiBao": "TC3.3.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC3.3-3", "soChiBao": "TC3.3.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC3.4" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC3.4-01", "tenMinhChung": "Kế hoạch giảng dạy và học tập của chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.4-02", "tenMinhChung": "Đề cương học phần thể hiện phương pháp dạy và học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.4-03", "tenMinhChung": "Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.4-04", "tenMinhChung": "Biên bản/hồ sơ lấy phản hồi người học về hoạt động dạy học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.4-05", "tenMinhChung": "Hồ sơ cải tiến phương pháp dạy và học sau phản hồi", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.4-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC3.4-1", "soChiBao": "TC3.4.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Hoạt động dạy và học thúc đẩy người học đưa ra ý tưởng mới, sáng kiến, đổi mới sáng tạo và tinh thần khởi nghiệp.", "thuTu": 1 }, { "maChiBao": "CB-TC3.4-2", "soChiBao": "TC3.4.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC3.4-3", "soChiBao": "TC3.4.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC3.5" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC3.5-01", "tenMinhChung": "Kế hoạch giảng dạy và học tập của chương trình đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.5-02", "tenMinhChung": "Đề cương học phần thể hiện phương pháp dạy và học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.5-03", "tenMinhChung": "Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.5-04", "tenMinhChung": "Biên bản/hồ sơ lấy phản hồi người học về hoạt động dạy học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.5-05", "tenMinhChung": "Hồ sơ cải tiến phương pháp dạy và học sau phản hồi", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC3.5-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC3.5-1", "soChiBao": "TC3.5.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Quá trình dạy và học được cải tiến thường xuyên để đáp ứng yêu cầu của thị trường lao động và chuẩn đầu ra.", "thuTu": 1 }, { "maChiBao": "CB-TC3.5-2", "soChiBao": "TC3.5.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC3.5-3", "soChiBao": "TC3.5.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC4.1" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC4.1-01", "tenMinhChung": "Quy định và quy trình kiểm tra, đánh giá kết quả học tập", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.1-02", "tenMinhChung": "Đề cương học phần và ma trận đánh giá chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.1-03", "tenMinhChung": "Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.1-04", "tenMinhChung": "Hồ sơ phúc khảo, phản hồi và công bố kết quả đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.1-05", "tenMinhChung": "Biên bản rà soát tính phù hợp của phương pháp đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.1-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC4.1-1", "soChiBao": "TC4.1.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Các phương pháp đánh giá kết quả học tập của người học đa dạng, tương thích với chuẩn đầu ra của chương trình đào tạo.", "thuTu": 1 }, { "maChiBao": "CB-TC4.1-2", "soChiBao": "TC4.1.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC4.1-3", "soChiBao": "TC4.1.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC4.2" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC4.2-01", "tenMinhChung": "Quy định và quy trình kiểm tra, đánh giá kết quả học tập", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.2-02", "tenMinhChung": "Đề cương học phần và ma trận đánh giá chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.2-03", "tenMinhChung": "Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.2-04", "tenMinhChung": "Hồ sơ phúc khảo, phản hồi và công bố kết quả đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.2-05", "tenMinhChung": "Biên bản rà soát tính phù hợp của phương pháp đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.2-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC4.2-1", "soChiBao": "TC4.2.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Có các quy định rõ ràng về đánh giá kết quả học tập, quy trình phúc khảo, được phổ biến và triển khai nhất quán.", "thuTu": 1 }, { "maChiBao": "CB-TC4.2-2", "soChiBao": "TC4.2.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC4.2-3", "soChiBao": "TC4.2.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC4.3" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC4.3-01", "tenMinhChung": "Quy định và quy trình kiểm tra, đánh giá kết quả học tập", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.3-02", "tenMinhChung": "Đề cương học phần và ma trận đánh giá chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.3-03", "tenMinhChung": "Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.3-04", "tenMinhChung": "Hồ sơ phúc khảo, phản hồi và công bố kết quả đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.3-05", "tenMinhChung": "Biên bản rà soát tính phù hợp của phương pháp đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.3-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC4.3-1", "soChiBao": "TC4.3.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Các tiêu chuẩn và quy trình đánh giá kết quả học tập, công nhận kết quả học tập và xét tốt nghiệp được phổ biến, triển khai nhất quán.", "thuTu": 1 }, { "maChiBao": "CB-TC4.3-2", "soChiBao": "TC4.3.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC4.3-3", "soChiBao": "TC4.3.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC4.4" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC4.4-01", "tenMinhChung": "Quy định và quy trình kiểm tra, đánh giá kết quả học tập", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.4-02", "tenMinhChung": "Đề cương học phần và ma trận đánh giá chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.4-03", "tenMinhChung": "Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.4-04", "tenMinhChung": "Hồ sơ phúc khảo, phản hồi và công bố kết quả đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.4-05", "tenMinhChung": "Biên bản rà soát tính phù hợp của phương pháp đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.4-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC4.4-1", "soChiBao": "TC4.4.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Các phương pháp đánh giá được thể hiện qua ma trận, tiêu chí, mức độ đánh giá, đáp án, thang điểm và kế hoạch đánh giá.", "thuTu": 1 }, { "maChiBao": "CB-TC4.4-2", "soChiBao": "TC4.4.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC4.4-3", "soChiBao": "TC4.4.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC4.5" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC4.5-01", "tenMinhChung": "Quy định và quy trình kiểm tra, đánh giá kết quả học tập", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.5-02", "tenMinhChung": "Đề cương học phần và ma trận đánh giá chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.5-03", "tenMinhChung": "Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.5-04", "tenMinhChung": "Hồ sơ phúc khảo, phản hồi và công bố kết quả đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.5-05", "tenMinhChung": "Biên bản rà soát tính phù hợp của phương pháp đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.5-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC4.5-1", "soChiBao": "TC4.5.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Các phương pháp đánh giá bảo đảm đo lường được mức độ đạt chuẩn đầu ra học phần và chuẩn đầu ra chương trình đào tạo.", "thuTu": 1 }, { "maChiBao": "CB-TC4.5-2", "soChiBao": "TC4.5.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC4.5-3", "soChiBao": "TC4.5.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC4.6" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC4.6-01", "tenMinhChung": "Quy định và quy trình kiểm tra, đánh giá kết quả học tập", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.6-02", "tenMinhChung": "Đề cương học phần và ma trận đánh giá chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.6-03", "tenMinhChung": "Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.6-04", "tenMinhChung": "Hồ sơ phúc khảo, phản hồi và công bố kết quả đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.6-05", "tenMinhChung": "Biên bản rà soát tính phù hợp của phương pháp đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.6-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC4.6-1", "soChiBao": "TC4.6.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Kết quả đánh giá được phản hồi kịp thời cho người học để cải thiện việc học tập.", "thuTu": 1 }, { "maChiBao": "CB-TC4.6-2", "soChiBao": "TC4.6.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC4.6-3", "soChiBao": "TC4.6.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC4.7" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC4.7-01", "tenMinhChung": "Quy định và quy trình kiểm tra, đánh giá kết quả học tập", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.7-02", "tenMinhChung": "Đề cương học phần và ma trận đánh giá chuẩn đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.7-03", "tenMinhChung": "Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.7-04", "tenMinhChung": "Hồ sơ phúc khảo, phản hồi và công bố kết quả đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.7-05", "tenMinhChung": "Biên bản rà soát tính phù hợp của phương pháp đánh giá", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC4.7-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC4.7-1", "soChiBao": "TC4.7.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Việc đánh giá kết quả học tập được định kỳ rà soát và cải tiến để bảo đảm đo lường chuẩn đầu ra.", "thuTu": 1 }, { "maChiBao": "CB-TC4.7-2", "soChiBao": "TC4.7.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC4.7-3", "soChiBao": "TC4.7.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC5.1" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC5.1-01", "tenMinhChung": "Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.1-02", "tenMinhChung": "Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.1-03", "tenMinhChung": "Hồ sơ phân công giảng dạy và định mức khối lượng công việc", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.1-04", "tenMinhChung": "Hồ sơ đào tạo, bồi dưỡng và phát triển chuyên môn nghiệp vụ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.1-05", "tenMinhChung": "Hồ sơ nghiên cứu khoa học, công bố và hoạt động chuyên môn của giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.1-06", "tenMinhChung": "Hồ sơ nhân sự, năng lực chuyên môn và minh chứng phát triển đội ngũ liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC5.1-1", "soChiBao": "TC5.1.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Kế hoạch phát triển đội ngũ giảng viên, nghiên cứu viên được thực hiện nhằm bảo đảm số lượng và chất lượng.", "thuTu": 1 }, { "maChiBao": "CB-TC5.1-2", "soChiBao": "TC5.1.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC5.1-3", "soChiBao": "TC5.1.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC5.2" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC5.2-01", "tenMinhChung": "Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.2-02", "tenMinhChung": "Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.2-03", "tenMinhChung": "Hồ sơ phân công giảng dạy và định mức khối lượng công việc", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.2-04", "tenMinhChung": "Hồ sơ đào tạo, bồi dưỡng và phát triển chuyên môn nghiệp vụ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.2-05", "tenMinhChung": "Hồ sơ nghiên cứu khoa học, công bố và hoạt động chuyên môn của giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.2-06", "tenMinhChung": "Hồ sơ nhân sự, năng lực chuyên môn và minh chứng phát triển đội ngũ liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC5.2-1", "soChiBao": "TC5.2.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Số lượng và chất lượng đội ngũ giảng viên, nghiên cứu viên đáp ứng yêu cầu thực hiện chương trình đào tạo; khối lượng công việc được đo lường và giám sát.", "thuTu": 1 }, { "maChiBao": "CB-TC5.2-2", "soChiBao": "TC5.2.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC5.2-3", "soChiBao": "TC5.2.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC5.3" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC5.3-01", "tenMinhChung": "Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.3-02", "tenMinhChung": "Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.3-03", "tenMinhChung": "Hồ sơ phân công giảng dạy và định mức khối lượng công việc", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.3-04", "tenMinhChung": "Hồ sơ đào tạo, bồi dưỡng và phát triển chuyên môn nghiệp vụ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.3-05", "tenMinhChung": "Hồ sơ nghiên cứu khoa học, công bố và hoạt động chuyên môn của giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.3-06", "tenMinhChung": "Phiếu khảo sát, tổng hợp ý kiến và biên bản tham vấn các bên liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC5.3-1", "soChiBao": "TC5.3.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Năng lực của đội ngũ giảng viên, nghiên cứu viên được xác định, đánh giá và thông tin tới các bên liên quan trực tiếp.", "thuTu": 1 }, { "maChiBao": "CB-TC5.3-2", "soChiBao": "TC5.3.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC5.3-3", "soChiBao": "TC5.3.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC5.4" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC5.4-01", "tenMinhChung": "Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.4-02", "tenMinhChung": "Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.4-03", "tenMinhChung": "Hồ sơ phân công giảng dạy và định mức khối lượng công việc", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.4-04", "tenMinhChung": "Hồ sơ đào tạo, bồi dưỡng và phát triển chuyên môn nghiệp vụ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.4-05", "tenMinhChung": "Hồ sơ nghiên cứu khoa học, công bố và hoạt động chuyên môn của giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.4-06", "tenMinhChung": "Hồ sơ nhân sự, năng lực chuyên môn và minh chứng phát triển đội ngũ liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC5.4-1", "soChiBao": "TC5.4.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Giảng viên, nghiên cứu viên được phân công nhiệm vụ phù hợp với trình độ, năng lực và kinh nghiệm.", "thuTu": 1 }, { "maChiBao": "CB-TC5.4-2", "soChiBao": "TC5.4.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC5.4-3", "soChiBao": "TC5.4.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC5.5" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC5.5-01", "tenMinhChung": "Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.5-02", "tenMinhChung": "Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.5-03", "tenMinhChung": "Hồ sơ phân công giảng dạy và định mức khối lượng công việc", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.5-04", "tenMinhChung": "Hồ sơ đào tạo, bồi dưỡng và phát triển chuyên môn nghiệp vụ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.5-05", "tenMinhChung": "Hồ sơ nghiên cứu khoa học, công bố và hoạt động chuyên môn của giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.5-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC5.5-1", "soChiBao": "TC5.5.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Việc bổ nhiệm, thăng tiến dựa trên hệ thống đánh giá năng lực và kết quả thực hiện nhiệm vụ.", "thuTu": 1 }, { "maChiBao": "CB-TC5.5-2", "soChiBao": "TC5.5.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC5.5-3", "soChiBao": "TC5.5.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC5.6" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC5.6-01", "tenMinhChung": "Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.6-02", "tenMinhChung": "Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.6-03", "tenMinhChung": "Hồ sơ phân công giảng dạy và định mức khối lượng công việc", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.6-04", "tenMinhChung": "Hồ sơ đào tạo, bồi dưỡng và phát triển chuyên môn nghiệp vụ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.6-05", "tenMinhChung": "Hồ sơ nghiên cứu khoa học, công bố và hoạt động chuyên môn của giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.6-06", "tenMinhChung": "Hồ sơ nhân sự, năng lực chuyên môn và minh chứng phát triển đội ngũ liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC5.6-1", "soChiBao": "TC5.6.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Trách nhiệm, quyền hạn và nghĩa vụ của giảng viên, nghiên cứu viên được xác định rõ ràng và phổ biến.", "thuTu": 1 }, { "maChiBao": "CB-TC5.6-2", "soChiBao": "TC5.6.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC5.6-3", "soChiBao": "TC5.6.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC5.7" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC5.7-01", "tenMinhChung": "Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.7-02", "tenMinhChung": "Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.7-03", "tenMinhChung": "Hồ sơ phân công giảng dạy và định mức khối lượng công việc", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.7-04", "tenMinhChung": "Hồ sơ đào tạo, bồi dưỡng và phát triển chuyên môn nghiệp vụ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.7-05", "tenMinhChung": "Hồ sơ nghiên cứu khoa học, công bố và hoạt động chuyên môn của giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.7-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC5.7-1", "soChiBao": "TC5.7.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Nhu cầu đào tạo, bồi dưỡng và phát triển chuyên môn được xác định có hệ thống và được triển khai đáp ứng nhu cầu.", "thuTu": 1 }, { "maChiBao": "CB-TC5.7-2", "soChiBao": "TC5.7.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC5.7-3", "soChiBao": "TC5.7.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC5.8" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC5.8-01", "tenMinhChung": "Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.8-02", "tenMinhChung": "Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.8-03", "tenMinhChung": "Hồ sơ phân công giảng dạy và định mức khối lượng công việc", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.8-04", "tenMinhChung": "Hồ sơ đào tạo, bồi dưỡng và phát triển chuyên môn nghiệp vụ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.8-05", "tenMinhChung": "Hồ sơ nghiên cứu khoa học, công bố và hoạt động chuyên môn của giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC5.8-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC5.8-1", "soChiBao": "TC5.8.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Công tác quản lý để đánh giá chất lượng giảng dạy, nghiên cứu khoa học và phục vụ cộng đồng được triển khai theo quy định.", "thuTu": 1 }, { "maChiBao": "CB-TC5.8-2", "soChiBao": "TC5.8.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC5.8-3", "soChiBao": "TC5.8.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC6.1" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC6.1-01", "tenMinhChung": "Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.1-02", "tenMinhChung": "Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.1-03", "tenMinhChung": "Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.1-04", "tenMinhChung": "Hồ sơ hỗ trợ việc làm, thực tập và kết nối doanh nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.1-05", "tenMinhChung": "Khảo sát mức độ hài lòng của người học về dịch vụ hỗ trợ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.1-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC6.1-1", "soChiBao": "TC6.1.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Chính sách, tiêu chí và quy trình tuyển sinh được xác định rõ ràng, công bố công khai và cập nhật.", "thuTu": 1 }, { "maChiBao": "CB-TC6.1-2", "soChiBao": "TC6.1.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC6.1-3", "soChiBao": "TC6.1.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC6.2" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC6.2-01", "tenMinhChung": "Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.2-02", "tenMinhChung": "Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.2-03", "tenMinhChung": "Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.2-04", "tenMinhChung": "Hồ sơ hỗ trợ việc làm, thực tập và kết nối doanh nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.2-05", "tenMinhChung": "Khảo sát mức độ hài lòng của người học về dịch vụ hỗ trợ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.2-06", "tenMinhChung": "Phiếu khảo sát, tổng hợp ý kiến và biên bản tham vấn các bên liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC6.2-1", "soChiBao": "TC6.2.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Năng lực đội ngũ cung cấp dịch vụ hỗ trợ được xác định rõ ràng và được đánh giá để bảo đảm phù hợp nhu cầu các bên liên quan.", "thuTu": 1 }, { "maChiBao": "CB-TC6.2-2", "soChiBao": "TC6.2.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC6.2-3", "soChiBao": "TC6.2.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC6.3" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC6.3-01", "tenMinhChung": "Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.3-02", "tenMinhChung": "Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.3-03", "tenMinhChung": "Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.3-04", "tenMinhChung": "Hồ sơ hỗ trợ việc làm, thực tập và kết nối doanh nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.3-05", "tenMinhChung": "Khảo sát mức độ hài lòng của người học về dịch vụ hỗ trợ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.3-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC6.3-1", "soChiBao": "TC6.3.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Kế hoạch ngắn hạn và dài hạn đối với dịch vụ hỗ trợ người học được xây dựng và triển khai thực hiện.", "thuTu": 1 }, { "maChiBao": "CB-TC6.3-2", "soChiBao": "TC6.3.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC6.3-3", "soChiBao": "TC6.3.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC6.4" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC6.4-01", "tenMinhChung": "Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.4-02", "tenMinhChung": "Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.4-03", "tenMinhChung": "Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.4-04", "tenMinhChung": "Hồ sơ hỗ trợ việc làm, thực tập và kết nối doanh nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.4-05", "tenMinhChung": "Khảo sát mức độ hài lòng của người học về dịch vụ hỗ trợ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.4-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC6.4-1", "soChiBao": "TC6.4.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Có hệ thống quản lý đào tạo phù hợp để giám sát, ghi nhận tiến độ, kết quả và khối lượng học tập của người học.", "thuTu": 1 }, { "maChiBao": "CB-TC6.4-2", "soChiBao": "TC6.4.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC6.4-3", "soChiBao": "TC6.4.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC6.5" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC6.5-01", "tenMinhChung": "Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.5-02", "tenMinhChung": "Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.5-03", "tenMinhChung": "Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.5-04", "tenMinhChung": "Hồ sơ hỗ trợ việc làm, thực tập và kết nối doanh nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.5-05", "tenMinhChung": "Khảo sát mức độ hài lòng của người học về dịch vụ hỗ trợ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.5-06", "tenMinhChung": "Báo cáo khảo sát việc làm sau tốt nghiệp và dữ liệu phản hồi nhà tuyển dụng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC6.5-1", "soChiBao": "TC6.5.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Có các hoạt động tư vấn học tập, ngoại khóa, cuộc thi và dịch vụ hỗ trợ giúp cải thiện việc học và khả năng có việc làm.", "thuTu": 1 }, { "maChiBao": "CB-TC6.5-2", "soChiBao": "TC6.5.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC6.5-3", "soChiBao": "TC6.5.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC6.6" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC6.6-01", "tenMinhChung": "Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.6-02", "tenMinhChung": "Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.6-03", "tenMinhChung": "Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.6-04", "tenMinhChung": "Hồ sơ hỗ trợ việc làm, thực tập và kết nối doanh nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.6-05", "tenMinhChung": "Khảo sát mức độ hài lòng của người học về dịch vụ hỗ trợ", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC6.6-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC6.6-1", "soChiBao": "TC6.6.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Các dịch vụ hỗ trợ người học được định kỳ đánh giá, đối sánh và cải tiến chất lượng.", "thuTu": 1 }, { "maChiBao": "CB-TC6.6-2", "soChiBao": "TC6.6.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC6.6-3", "soChiBao": "TC6.6.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC7.1" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC7.1-01", "tenMinhChung": "Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.1-02", "tenMinhChung": "Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.1-03", "tenMinhChung": "Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.1-04", "tenMinhChung": "Kế hoạch bảo trì, bảo dưỡng và nâng cấp cơ sở vật chất", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.1-05", "tenMinhChung": "Khảo sát mức độ đáp ứng cơ sở vật chất đối với người học và giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.1-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC7.1-1", "soChiBao": "TC7.1.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Có hệ thống phòng làm việc, phòng học và các phòng chức năng với trang thiết bị phù hợp để triển khai chương trình đào tạo.", "thuTu": 1 }, { "maChiBao": "CB-TC7.1-2", "soChiBao": "TC7.1.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC7.1-3", "soChiBao": "TC7.1.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC7.2" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC7.2-01", "tenMinhChung": "Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.2-02", "tenMinhChung": "Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.2-03", "tenMinhChung": "Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.2-04", "tenMinhChung": "Kế hoạch bảo trì, bảo dưỡng và nâng cấp cơ sở vật chất", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.2-05", "tenMinhChung": "Khảo sát mức độ đáp ứng cơ sở vật chất đối với người học và giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.2-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC7.2-1", "soChiBao": "TC7.2.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Các phòng thí nghiệm, phòng thực hành và trang thiết bị đầy đủ, cập nhật và được sử dụng hiệu quả.", "thuTu": 1 }, { "maChiBao": "CB-TC7.2-2", "soChiBao": "TC7.2.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC7.2-3", "soChiBao": "TC7.2.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC7.3" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC7.3-01", "tenMinhChung": "Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.3-02", "tenMinhChung": "Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.3-03", "tenMinhChung": "Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.3-04", "tenMinhChung": "Kế hoạch bảo trì, bảo dưỡng và nâng cấp cơ sở vật chất", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.3-05", "tenMinhChung": "Khảo sát mức độ đáp ứng cơ sở vật chất đối với người học và giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.3-06", "tenMinhChung": "Danh mục học liệu, số liệu khai thác thư viện và tài nguyên điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC7.3-1", "soChiBao": "TC7.3.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Có thư viện, thư viện số và nguồn học liệu được cập nhật đáp ứng nhu cầu đào tạo, nghiên cứu khoa học.", "thuTu": 1 }, { "maChiBao": "CB-TC7.3-2", "soChiBao": "TC7.3.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC7.3-3", "soChiBao": "TC7.3.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC7.4" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC7.4-01", "tenMinhChung": "Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.4-02", "tenMinhChung": "Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.4-03", "tenMinhChung": "Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.4-04", "tenMinhChung": "Kế hoạch bảo trì, bảo dưỡng và nâng cấp cơ sở vật chất", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.4-05", "tenMinhChung": "Khảo sát mức độ đáp ứng cơ sở vật chất đối với người học và giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.4-06", "tenMinhChung": "Danh mục hệ thống CNTT, biên bản vận hành và số liệu sử dụng hệ thống", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC7.4-1", "soChiBao": "TC7.4.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Có hệ thống công nghệ thông tin, hạ tầng mạng và máy tính dễ tiếp cận, đáp ứng nhu cầu giảng dạy, nghiên cứu và quản lý.", "thuTu": 1 }, { "maChiBao": "CB-TC7.4-2", "soChiBao": "TC7.4.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC7.4-3", "soChiBao": "TC7.4.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC7.5" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC7.5-01", "tenMinhChung": "Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.5-02", "tenMinhChung": "Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.5-03", "tenMinhChung": "Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.5-04", "tenMinhChung": "Kế hoạch bảo trì, bảo dưỡng và nâng cấp cơ sở vật chất", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.5-05", "tenMinhChung": "Khảo sát mức độ đáp ứng cơ sở vật chất đối với người học và giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.5-06", "tenMinhChung": "Danh mục đề tài, công bố, sản phẩm nghiên cứu và biên bản nghiệm thu liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC7.5-1", "soChiBao": "TC7.5.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Môi trường tâm lý, xã hội và cảnh quan thiên nhiên tạo thuận lợi cho đào tạo, nghiên cứu và sự thoải mái của người học.", "thuTu": 1 }, { "maChiBao": "CB-TC7.5-2", "soChiBao": "TC7.5.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC7.5-3", "soChiBao": "TC7.5.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC7.6" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC7.6-01", "tenMinhChung": "Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.6-02", "tenMinhChung": "Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.6-03", "tenMinhChung": "Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.6-04", "tenMinhChung": "Kế hoạch bảo trì, bảo dưỡng và nâng cấp cơ sở vật chất", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.6-05", "tenMinhChung": "Khảo sát mức độ đáp ứng cơ sở vật chất đối với người học và giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.6-06", "tenMinhChung": "Biên bản rà soát và hồ sơ chuyên môn liên quan trực tiếp đến yêu cầu của tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC7.6-1", "soChiBao": "TC7.6.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Các tiêu chuẩn về môi trường, sức khỏe, an toàn được xác định và triển khai phù hợp.", "thuTu": 1 }, { "maChiBao": "CB-TC7.6-2", "soChiBao": "TC7.6.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC7.6-3", "soChiBao": "TC7.6.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC7.7" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC7.7-01", "tenMinhChung": "Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.7-02", "tenMinhChung": "Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.7-03", "tenMinhChung": "Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.7-04", "tenMinhChung": "Kế hoạch bảo trì, bảo dưỡng và nâng cấp cơ sở vật chất", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.7-05", "tenMinhChung": "Khảo sát mức độ đáp ứng cơ sở vật chất đối với người học và giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.7-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC7.7-1", "soChiBao": "TC7.7.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Năng lực đội ngũ hỗ trợ về cơ sở vật chất và trang thiết bị được xác định và đánh giá đáp ứng nhu cầu.", "thuTu": 1 }, { "maChiBao": "CB-TC7.7-2", "soChiBao": "TC7.7.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC7.7-3", "soChiBao": "TC7.7.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC7.8" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC7.8-01", "tenMinhChung": "Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.8-02", "tenMinhChung": "Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.8-03", "tenMinhChung": "Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.8-04", "tenMinhChung": "Kế hoạch bảo trì, bảo dưỡng và nâng cấp cơ sở vật chất", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.8-05", "tenMinhChung": "Khảo sát mức độ đáp ứng cơ sở vật chất đối với người học và giảng viên", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC7.8-06", "tenMinhChung": "Ma trận đánh giá, rubric và hồ sơ kết quả đánh giá phục vụ tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC7.8-1", "soChiBao": "TC7.8.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Chất lượng cơ sở vật chất phục vụ chương trình đào tạo được định kỳ đánh giá và cải tiến.", "thuTu": 1 }, { "maChiBao": "CB-TC7.8-2", "soChiBao": "TC7.8.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC7.8-3", "soChiBao": "TC7.8.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC8.1" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC8.1-01", "tenMinhChung": "Thống kê kết quả học tập, tốt nghiệp và thôi học của người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.1-02", "tenMinhChung": "Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.1-03", "tenMinhChung": "Khảo sát việc làm của người học sau tốt nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.1-04", "tenMinhChung": "Khảo sát mức độ hài lòng của người học, cựu người học và nhà tuyển dụng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.1-05", "tenMinhChung": "Hồ sơ theo dõi, phân tích và cải tiến dựa trên kết quả đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.1-06", "tenMinhChung": "Bảng thống kê cohort, tỷ lệ tốt nghiệp, thôi học và thời gian hoàn thành chương trình", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC8.1-1", "soChiBao": "TC8.1.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Tỉ lệ tốt nghiệp, thôi học và thời gian tốt nghiệp trung bình của người học được xác lập, giám sát, đối sánh để cải tiến chất lượng.", "thuTu": 1 }, { "maChiBao": "CB-TC8.1-2", "soChiBao": "TC8.1.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC8.1-3", "soChiBao": "TC8.1.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC8.2" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC8.2-01", "tenMinhChung": "Thống kê kết quả học tập, tốt nghiệp và thôi học của người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.2-02", "tenMinhChung": "Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.2-03", "tenMinhChung": "Khảo sát việc làm của người học sau tốt nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.2-04", "tenMinhChung": "Khảo sát mức độ hài lòng của người học, cựu người học và nhà tuyển dụng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.2-05", "tenMinhChung": "Hồ sơ theo dõi, phân tích và cải tiến dựa trên kết quả đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.2-06", "tenMinhChung": "Báo cáo khảo sát việc làm sau tốt nghiệp và dữ liệu phản hồi nhà tuyển dụng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC8.2-1", "soChiBao": "TC8.2.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Tỉ lệ việc làm, tự tạo việc làm, khởi nghiệp và học tập nâng cao trình độ của người học tốt nghiệp được xác lập, giám sát và đối sánh.", "thuTu": 1 }, { "maChiBao": "CB-TC8.2-2", "soChiBao": "TC8.2.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC8.2-3", "soChiBao": "TC8.2.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC8.3" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC8.3-01", "tenMinhChung": "Thống kê kết quả học tập, tốt nghiệp và thôi học của người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.3-02", "tenMinhChung": "Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.3-03", "tenMinhChung": "Khảo sát việc làm của người học sau tốt nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.3-04", "tenMinhChung": "Khảo sát mức độ hài lòng của người học, cựu người học và nhà tuyển dụng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.3-05", "tenMinhChung": "Hồ sơ theo dõi, phân tích và cải tiến dựa trên kết quả đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.3-06", "tenMinhChung": "Hồ sơ nhân sự, năng lực chuyên môn và minh chứng phát triển đội ngũ liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC8.3-1", "soChiBao": "TC8.3.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Hoạt động nghiên cứu khoa học và sản phẩm sáng tạo của người học, giảng viên, nghiên cứu viên được xác lập, giám sát và đối sánh.", "thuTu": 1 }, { "maChiBao": "CB-TC8.3-2", "soChiBao": "TC8.3.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC8.3-3", "soChiBao": "TC8.3.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC8.4" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC8.4-01", "tenMinhChung": "Thống kê kết quả học tập, tốt nghiệp và thôi học của người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.4-02", "tenMinhChung": "Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.4-03", "tenMinhChung": "Khảo sát việc làm của người học sau tốt nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.4-04", "tenMinhChung": "Khảo sát mức độ hài lòng của người học, cựu người học và nhà tuyển dụng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.4-05", "tenMinhChung": "Hồ sơ theo dõi, phân tích và cải tiến dựa trên kết quả đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.4-06", "tenMinhChung": "Bảng đối sánh và biên bản rà soát chuẩn đầu ra liên quan trực tiếp đến tiêu chí", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC8.4-1", "soChiBao": "TC8.4.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Dữ liệu về mức độ người học đạt chuẩn đầu ra của chương trình đào tạo được xác lập và giám sát để cải tiến chất lượng.", "thuTu": 1 }, { "maChiBao": "CB-TC8.4-2", "soChiBao": "TC8.4.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC8.4-3", "soChiBao": "TC8.4.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
db.tieuChi.updateOne({ _id: "TC8.5" }, { $set: { minhChungGoiY: [{ "maMinhChungGoiY": "GY-TC8.5-01", "tenMinhChung": "Thống kê kết quả học tập, tốt nghiệp và thôi học của người học", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.5-02", "tenMinhChung": "Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.5-03", "tenMinhChung": "Khảo sát việc làm của người học sau tốt nghiệp", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.5-04", "tenMinhChung": "Khảo sát mức độ hài lòng của người học, cựu người học và nhà tuyển dụng", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.5-05", "tenMinhChung": "Hồ sơ theo dõi, phân tích và cải tiến dựa trên kết quả đầu ra", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }, { "maMinhChungGoiY": "GY-TC8.5-06", "tenMinhChung": "Phiếu khảo sát, tổng hợp ý kiến và biên bản tham vấn các bên liên quan", "moTa": "Tài liệu gợi ý phục vụ đối chiếu tiêu chí." }], chiBao: [{ "maChiBao": "CB-TC8.5-1", "soChiBao": "TC8.5.1", "noiDung": "Có hồ sơ/quy định hoặc dữ liệu chứng minh việc thực hiện yêu cầu: Mức độ hài lòng của các bên liên quan được xác lập, giám sát, đối sánh để cải tiến chất lượng.", "thuTu": 1 }, { "maChiBao": "CB-TC8.5-2", "soChiBao": "TC8.5.2", "noiDung": "Có bằng chứng triển khai thực tế và có thể truy xuất theo thời gian/đơn vị phụ trách", "thuTu": 2 }, { "maChiBao": "CB-TC8.5-3", "soChiBao": "TC8.5.3", "noiDung": "Có hoạt động rà soát, phản hồi hoặc cải tiến dựa trên kết quả thực hiện tiêu chí", "thuTu": 3 }] } });
var x = db.minhChung.findOne({ _id: "MC001" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC1.1 - Mục tiêu của chương trình đào tạo được xác định rõ ràng, phù hợp và gắn kết với sứ mạng, tầm nhìn, chiến lược phát triển của cơ sở đào tạo; phù hợp với mục tiêu của giáo dục đại học.", moTa: "Hồ sơ minh chứng TC1.1: Mục tiêu của chương trình đào tạo được xác định rõ ràng, phù hợp và gắn kết với sứ mạng, tầm nhìn, chiến lược phát triển của cơ sở đào tạo; phù hợp với mục tiêu của giáo dục đại học.. Thành phần gợi ý: Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa; Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo; Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC001_TC1.1.pdf";
    db.minhChung.updateOne({ _id: "MC001" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC002" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC1.2 - Chuẩn đầu ra của chương trình đào tạo được xây dựng rõ ràng; phù hợp với sứ mạng, tầm nhìn và mục tiêu chiến lược của cơ sở đào tạo và được phổ biến đến các bên liên quan.", moTa: "Hồ sơ minh chứng TC1.2: Chuẩn đầu ra của chương trình đào tạo được xây dựng rõ ràng; phù hợp với sứ mạng, tầm nhìn và mục tiêu chiến lược của cơ sở đào tạo và được phổ biến đến các bên liên quan.. Thành phần gợi ý: Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa; Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo; Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC002_TC1.2.pdf";
    db.minhChung.updateOne({ _id: "MC002" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC003" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC1.3 - Chuẩn đầu ra của chương trình đào tạo phù hợp với Khung trình độ quốc gia Việt Nam và chuẩn chương trình đào tạo nhóm ngành.", moTa: "Hồ sơ minh chứng TC1.3: Chuẩn đầu ra của chương trình đào tạo phù hợp với Khung trình độ quốc gia Việt Nam và chuẩn chương trình đào tạo nhóm ngành.. Thành phần gợi ý: Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa; Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo; Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC003_TC1.3.pdf";
    db.minhChung.updateOne({ _id: "MC003" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC004" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC1.4 - Chuẩn đầu ra của tất cả các học phần được xây dựng phù hợp và tương thích với chuẩn đầu ra của chương trình đào tạo đã công bố.", moTa: "Hồ sơ minh chứng TC1.4: Chuẩn đầu ra của tất cả các học phần được xây dựng phù hợp và tương thích với chuẩn đầu ra của chương trình đào tạo đã công bố.. Thành phần gợi ý: Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa; Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo; Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC004_TC1.4.pdf";
    db.minhChung.updateOne({ _id: "MC004" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC005" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC1.5 - Chuẩn đầu ra của chương trình đào tạo phản ánh rõ yêu cầu của các bên liên quan, đặc biệt là các bên liên quan bên ngoài.", moTa: "Hồ sơ minh chứng TC1.5: Chuẩn đầu ra của chương trình đào tạo phản ánh rõ yêu cầu của các bên liên quan, đặc biệt là các bên liên quan bên ngoài.. Thành phần gợi ý: Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa; Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo; Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC005_TC1.5.pdf";
    db.minhChung.updateOne({ _id: "MC005" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC006" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC1.6 - Chuẩn đầu ra của chương trình đào tạo được đo lường, đánh giá tại thời điểm người học tốt nghiệp.", moTa: "Hồ sơ minh chứng TC1.6: Chuẩn đầu ra của chương trình đào tạo được đo lường, đánh giá tại thời điểm người học tốt nghiệp.. Thành phần gợi ý: Quyết định ban hành sứ mạng, tầm nhìn và chiến lược phát triển của Trường/Khoa; Hồ sơ xây dựng, rà soát và công bố mục tiêu chương trình đào tạo; Hồ sơ xây dựng và công bố chuẩn đầu ra chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC006_TC1.6.pdf";
    db.minhChung.updateOne({ _id: "MC006" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC007" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC2.1 - Bản mô tả chương trình đào tạo và đề cương các học phần có đủ thông tin, được cập nhật, phê duyệt và công bố công khai.", moTa: "Hồ sơ minh chứng TC2.1: Bản mô tả chương trình đào tạo và đề cương các học phần có đủ thông tin, được cập nhật, phê duyệt và công bố công khai.. Thành phần gợi ý: Chương trình đào tạo và đề cương chi tiết các học phần; Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo; Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC007_TC2.1.pdf";
    db.minhChung.updateOne({ _id: "MC007" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC008" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC2.2 - Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển để bảo đảm người học đạt chuẩn đầu ra và có khối lượng học tập phù hợp.", moTa: "Hồ sơ minh chứng TC2.2: Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển để bảo đảm người học đạt chuẩn đầu ra và có khối lượng học tập phù hợp.. Thành phần gợi ý: Chương trình đào tạo và đề cương chi tiết các học phần; Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo; Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC008_TC2.2.pdf";
    db.minhChung.updateOne({ _id: "MC008" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC009" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC2.3 - Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển dựa trên phản hồi và nhu cầu của các bên liên quan.", moTa: "Hồ sơ minh chứng TC2.3: Cấu trúc và nội dung chương trình đào tạo được thiết kế và phát triển dựa trên phản hồi và nhu cầu của các bên liên quan.. Thành phần gợi ý: Chương trình đào tạo và đề cương chi tiết các học phần; Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo; Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC009_TC2.3.pdf";
    db.minhChung.updateOne({ _id: "MC009" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC010" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC2.4 - Đóng góp của từng học phần trong việc đạt được chuẩn đầu ra của chương trình đào tạo là rõ ràng.", moTa: "Hồ sơ minh chứng TC2.4: Đóng góp của từng học phần trong việc đạt được chuẩn đầu ra của chương trình đào tạo là rõ ràng.. Thành phần gợi ý: Chương trình đào tạo và đề cương chi tiết các học phần; Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo; Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC010_TC2.4.pdf";
    db.minhChung.updateOne({ _id: "MC010" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC011" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC2.5 - Chương trình đào tạo có cấu trúc logic, trình tự hợp lý, có tính linh hoạt và tích hợp.", moTa: "Hồ sơ minh chứng TC2.5: Chương trình đào tạo có cấu trúc logic, trình tự hợp lý, có tính linh hoạt và tích hợp.. Thành phần gợi ý: Chương trình đào tạo và đề cương chi tiết các học phần; Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo; Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC011_TC2.5.pdf";
    db.minhChung.updateOne({ _id: "MC011" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC012" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC2.6 - Cấu trúc và nội dung chương trình đào tạo thể hiện rõ các học phần bắt buộc, tự chọn, lý thuyết, thực hành, trải nghiệm và nghiên cứu khoa học.", moTa: "Hồ sơ minh chứng TC2.6: Cấu trúc và nội dung chương trình đào tạo thể hiện rõ các học phần bắt buộc, tự chọn, lý thuyết, thực hành, trải nghiệm và nghiên cứu khoa học.. Thành phần gợi ý: Chương trình đào tạo và đề cương chi tiết các học phần; Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo; Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC012_TC2.6.pdf";
    db.minhChung.updateOne({ _id: "MC012" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC013" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC2.7 - Cấu trúc và nội dung chương trình đào tạo được rà soát, đánh giá và cải tiến theo quy trình, bảo đảm tính cập nhật.", moTa: "Hồ sơ minh chứng TC2.7: Cấu trúc và nội dung chương trình đào tạo được rà soát, đánh giá và cải tiến theo quy trình, bảo đảm tính cập nhật.. Thành phần gợi ý: Chương trình đào tạo và đề cương chi tiết các học phần; Ma trận phân bổ học phần và chuẩn đầu ra chương trình đào tạo; Biên bản họp rà soát cấu trúc và nội dung chương trình đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC013_TC2.7.pdf";
    db.minhChung.updateOne({ _id: "MC013" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC014" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC3.1 - Triết lý giáo dục của cơ sở đào tạo được tuyên bố rõ ràng, phổ biến đến các bên liên quan và truyền tải vào hoạt động dạy và học.", moTa: "Hồ sơ minh chứng TC3.1: Triết lý giáo dục của cơ sở đào tạo được tuyên bố rõ ràng, phổ biến đến các bên liên quan và truyền tải vào hoạt động dạy và học.. Thành phần gợi ý: Kế hoạch giảng dạy và học tập của chương trình đào tạo; Đề cương học phần thể hiện phương pháp dạy và học; Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC014_TC3.1.pdf";
    db.minhChung.updateOne({ _id: "MC014" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC015" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC3.2 - Hoạt động dạy và học được thiết kế tương thích với chuẩn đầu ra của chương trình đào tạo.", moTa: "Hồ sơ minh chứng TC3.2: Hoạt động dạy và học được thiết kế tương thích với chuẩn đầu ra của chương trình đào tạo.. Thành phần gợi ý: Kế hoạch giảng dạy và học tập của chương trình đào tạo; Đề cương học phần thể hiện phương pháp dạy và học; Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC015_TC3.2.pdf";
    db.minhChung.updateOne({ _id: "MC015" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC016" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC3.3 - Hoạt động dạy và học thể hiện việc học tập chủ động và phát triển khả năng học tập suốt đời của người học.", moTa: "Hồ sơ minh chứng TC3.3: Hoạt động dạy và học thể hiện việc học tập chủ động và phát triển khả năng học tập suốt đời của người học.. Thành phần gợi ý: Kế hoạch giảng dạy và học tập của chương trình đào tạo; Đề cương học phần thể hiện phương pháp dạy và học; Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC016_TC3.3.pdf";
    db.minhChung.updateOne({ _id: "MC016" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC017" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC3.4 - Hoạt động dạy và học thúc đẩy người học đưa ra ý tưởng mới, sáng kiến, đổi mới sáng tạo và tinh thần khởi nghiệp.", moTa: "Hồ sơ minh chứng TC3.4: Hoạt động dạy và học thúc đẩy người học đưa ra ý tưởng mới, sáng kiến, đổi mới sáng tạo và tinh thần khởi nghiệp.. Thành phần gợi ý: Kế hoạch giảng dạy và học tập của chương trình đào tạo; Đề cương học phần thể hiện phương pháp dạy và học; Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC017_TC3.4.pdf";
    db.minhChung.updateOne({ _id: "MC017" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC018" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC3.5 - Quá trình dạy và học được cải tiến thường xuyên để đáp ứng yêu cầu của thị trường lao động và chuẩn đầu ra.", moTa: "Hồ sơ minh chứng TC3.5: Quá trình dạy và học được cải tiến thường xuyên để đáp ứng yêu cầu của thị trường lao động và chuẩn đầu ra.. Thành phần gợi ý: Kế hoạch giảng dạy và học tập của chương trình đào tạo; Đề cương học phần thể hiện phương pháp dạy và học; Hồ sơ hoạt động học tập chủ động, trải nghiệm, thực hành và dự án.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC018_TC3.5.pdf";
    db.minhChung.updateOne({ _id: "MC018" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC019" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC4.1 - Các phương pháp đánh giá kết quả học tập của người học đa dạng, tương thích với chuẩn đầu ra của chương trình đào tạo.", moTa: "Hồ sơ minh chứng TC4.1: Các phương pháp đánh giá kết quả học tập của người học đa dạng, tương thích với chuẩn đầu ra của chương trình đào tạo.. Thành phần gợi ý: Quy định và quy trình kiểm tra, đánh giá kết quả học tập; Đề cương học phần và ma trận đánh giá chuẩn đầu ra; Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC019_TC4.1.pdf";
    db.minhChung.updateOne({ _id: "MC019" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC020" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC4.2 - Có các quy định rõ ràng về đánh giá kết quả học tập, quy trình phúc khảo, được phổ biến và triển khai nhất quán.", moTa: "Hồ sơ minh chứng TC4.2: Có các quy định rõ ràng về đánh giá kết quả học tập, quy trình phúc khảo, được phổ biến và triển khai nhất quán.. Thành phần gợi ý: Quy định và quy trình kiểm tra, đánh giá kết quả học tập; Đề cương học phần và ma trận đánh giá chuẩn đầu ra; Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC020_TC4.2.pdf";
    db.minhChung.updateOne({ _id: "MC020" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC021" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC4.3 - Các tiêu chuẩn và quy trình đánh giá kết quả học tập, công nhận kết quả học tập và xét tốt nghiệp được phổ biến, triển khai nhất quán.", moTa: "Hồ sơ minh chứng TC4.3: Các tiêu chuẩn và quy trình đánh giá kết quả học tập, công nhận kết quả học tập và xét tốt nghiệp được phổ biến, triển khai nhất quán.. Thành phần gợi ý: Quy định và quy trình kiểm tra, đánh giá kết quả học tập; Đề cương học phần và ma trận đánh giá chuẩn đầu ra; Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC021_TC4.3.pdf";
    db.minhChung.updateOne({ _id: "MC021" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC022" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC4.4 - Các phương pháp đánh giá được thể hiện qua ma trận, tiêu chí, mức độ đánh giá, đáp án, thang điểm và kế hoạch đánh giá.", moTa: "Hồ sơ minh chứng TC4.4: Các phương pháp đánh giá được thể hiện qua ma trận, tiêu chí, mức độ đánh giá, đáp án, thang điểm và kế hoạch đánh giá.. Thành phần gợi ý: Quy định và quy trình kiểm tra, đánh giá kết quả học tập; Đề cương học phần và ma trận đánh giá chuẩn đầu ra; Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC022_TC4.4.pdf";
    db.minhChung.updateOne({ _id: "MC022" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC023" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC4.5 - Các phương pháp đánh giá bảo đảm đo lường được mức độ đạt chuẩn đầu ra học phần và chuẩn đầu ra chương trình đào tạo.", moTa: "Hồ sơ minh chứng TC4.5: Các phương pháp đánh giá bảo đảm đo lường được mức độ đạt chuẩn đầu ra học phần và chuẩn đầu ra chương trình đào tạo.. Thành phần gợi ý: Quy định và quy trình kiểm tra, đánh giá kết quả học tập; Đề cương học phần và ma trận đánh giá chuẩn đầu ra; Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC023_TC4.5.pdf";
    db.minhChung.updateOne({ _id: "MC023" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC024" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC4.6 - Kết quả đánh giá được phản hồi kịp thời cho người học để cải thiện việc học tập.", moTa: "Hồ sơ minh chứng TC4.6: Kết quả đánh giá được phản hồi kịp thời cho người học để cải thiện việc học tập.. Thành phần gợi ý: Quy định và quy trình kiểm tra, đánh giá kết quả học tập; Đề cương học phần và ma trận đánh giá chuẩn đầu ra; Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC024_TC4.6.pdf";
    db.minhChung.updateOne({ _id: "MC024" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC025" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC4.7 - Việc đánh giá kết quả học tập được định kỳ rà soát và cải tiến để bảo đảm đo lường chuẩn đầu ra.", moTa: "Hồ sơ minh chứng TC4.7: Việc đánh giá kết quả học tập được định kỳ rà soát và cải tiến để bảo đảm đo lường chuẩn đầu ra.. Thành phần gợi ý: Quy định và quy trình kiểm tra, đánh giá kết quả học tập; Đề cương học phần và ma trận đánh giá chuẩn đầu ra; Đề thi, đáp án, rubric và hồ sơ chấm đánh giá học phần.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC025_TC4.7.pdf";
    db.minhChung.updateOne({ _id: "MC025" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC026" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC5.1 - Kế hoạch phát triển đội ngũ giảng viên, nghiên cứu viên được thực hiện nhằm bảo đảm số lượng và chất lượng.", moTa: "Hồ sơ minh chứng TC5.1: Kế hoạch phát triển đội ngũ giảng viên, nghiên cứu viên được thực hiện nhằm bảo đảm số lượng và chất lượng.. Thành phần gợi ý: Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên; Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ; Hồ sơ phân công giảng dạy và định mức khối lượng công việc.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC026_TC5.1.pdf";
    db.minhChung.updateOne({ _id: "MC026" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC027" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC5.2 - Số lượng và chất lượng đội ngũ giảng viên, nghiên cứu viên đáp ứng yêu cầu thực hiện chương trình đào tạo; khối lượng công việc được đo lường và giám sát.", moTa: "Hồ sơ minh chứng TC5.2: Số lượng và chất lượng đội ngũ giảng viên, nghiên cứu viên đáp ứng yêu cầu thực hiện chương trình đào tạo; khối lượng công việc được đo lường và giám sát.. Thành phần gợi ý: Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên; Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ; Hồ sơ phân công giảng dạy và định mức khối lượng công việc.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC027_TC5.2.pdf";
    db.minhChung.updateOne({ _id: "MC027" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC028" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC5.3 - Năng lực của đội ngũ giảng viên, nghiên cứu viên được xác định, đánh giá và thông tin tới các bên liên quan trực tiếp.", moTa: "Hồ sơ minh chứng TC5.3: Năng lực của đội ngũ giảng viên, nghiên cứu viên được xác định, đánh giá và thông tin tới các bên liên quan trực tiếp.. Thành phần gợi ý: Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên; Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ; Hồ sơ phân công giảng dạy và định mức khối lượng công việc.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC028_TC5.3.pdf";
    db.minhChung.updateOne({ _id: "MC028" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC029" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC5.4 - Giảng viên, nghiên cứu viên được phân công nhiệm vụ phù hợp với trình độ, năng lực và kinh nghiệm.", moTa: "Hồ sơ minh chứng TC5.4: Giảng viên, nghiên cứu viên được phân công nhiệm vụ phù hợp với trình độ, năng lực và kinh nghiệm.. Thành phần gợi ý: Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên; Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ; Hồ sơ phân công giảng dạy và định mức khối lượng công việc.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC029_TC5.4.pdf";
    db.minhChung.updateOne({ _id: "MC029" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC030" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC5.5 - Việc bổ nhiệm, thăng tiến dựa trên hệ thống đánh giá năng lực và kết quả thực hiện nhiệm vụ.", moTa: "Hồ sơ minh chứng TC5.5: Việc bổ nhiệm, thăng tiến dựa trên hệ thống đánh giá năng lực và kết quả thực hiện nhiệm vụ.. Thành phần gợi ý: Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên; Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ; Hồ sơ phân công giảng dạy và định mức khối lượng công việc.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC030_TC5.5.pdf";
    db.minhChung.updateOne({ _id: "MC030" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC031" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC5.6 - Trách nhiệm, quyền hạn và nghĩa vụ của giảng viên, nghiên cứu viên được xác định rõ ràng và phổ biến.", moTa: "Hồ sơ minh chứng TC5.6: Trách nhiệm, quyền hạn và nghĩa vụ của giảng viên, nghiên cứu viên được xác định rõ ràng và phổ biến.. Thành phần gợi ý: Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên; Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ; Hồ sơ phân công giảng dạy và định mức khối lượng công việc.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC031_TC5.6.pdf";
    db.minhChung.updateOne({ _id: "MC031" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC032" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC5.7 - Nhu cầu đào tạo, bồi dưỡng và phát triển chuyên môn được xác định có hệ thống và được triển khai đáp ứng nhu cầu.", moTa: "Hồ sơ minh chứng TC5.7: Nhu cầu đào tạo, bồi dưỡng và phát triển chuyên môn được xác định có hệ thống và được triển khai đáp ứng nhu cầu.. Thành phần gợi ý: Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên; Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ; Hồ sơ phân công giảng dạy và định mức khối lượng công việc.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC032_TC5.7.pdf";
    db.minhChung.updateOne({ _id: "MC032" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC033" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC5.8 - Công tác quản lý để đánh giá chất lượng giảng dạy, nghiên cứu khoa học và phục vụ cộng đồng được triển khai theo quy định.", moTa: "Hồ sơ minh chứng TC5.8: Công tác quản lý để đánh giá chất lượng giảng dạy, nghiên cứu khoa học và phục vụ cộng đồng được triển khai theo quy định.. Thành phần gợi ý: Danh sách và hồ sơ đội ngũ giảng viên/nghiên cứu viên; Kế hoạch tuyển dụng, quy hoạch và phát triển đội ngũ; Hồ sơ phân công giảng dạy và định mức khối lượng công việc.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC033_TC5.8.pdf";
    db.minhChung.updateOne({ _id: "MC033" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC034" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC6.1 - Chính sách, tiêu chí và quy trình tuyển sinh được xác định rõ ràng, công bố công khai và cập nhật.", moTa: "Hồ sơ minh chứng TC6.1: Chính sách, tiêu chí và quy trình tuyển sinh được xác định rõ ràng, công bố công khai và cập nhật.. Thành phần gợi ý: Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học; Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học; Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC034_TC6.1.pdf";
    db.minhChung.updateOne({ _id: "MC034" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC035" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC6.2 - Năng lực đội ngũ cung cấp dịch vụ hỗ trợ được xác định rõ ràng và được đánh giá để bảo đảm phù hợp nhu cầu các bên liên quan.", moTa: "Hồ sơ minh chứng TC6.2: Năng lực đội ngũ cung cấp dịch vụ hỗ trợ được xác định rõ ràng và được đánh giá để bảo đảm phù hợp nhu cầu các bên liên quan.. Thành phần gợi ý: Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học; Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học; Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC035_TC6.2.pdf";
    db.minhChung.updateOne({ _id: "MC035" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC036" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC6.3 - Kế hoạch ngắn hạn và dài hạn đối với dịch vụ hỗ trợ người học được xây dựng và triển khai thực hiện.", moTa: "Hồ sơ minh chứng TC6.3: Kế hoạch ngắn hạn và dài hạn đối với dịch vụ hỗ trợ người học được xây dựng và triển khai thực hiện.. Thành phần gợi ý: Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học; Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học; Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC036_TC6.3.pdf";
    db.minhChung.updateOne({ _id: "MC036" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC037" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC6.4 - Có hệ thống quản lý đào tạo phù hợp để giám sát, ghi nhận tiến độ, kết quả và khối lượng học tập của người học.", moTa: "Hồ sơ minh chứng TC6.4: Có hệ thống quản lý đào tạo phù hợp để giám sát, ghi nhận tiến độ, kết quả và khối lượng học tập của người học.. Thành phần gợi ý: Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học; Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học; Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC037_TC6.4.pdf";
    db.minhChung.updateOne({ _id: "MC037" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC038" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC6.5 - Có các hoạt động tư vấn học tập, ngoại khóa, cuộc thi và dịch vụ hỗ trợ giúp cải thiện việc học và khả năng có việc làm.", moTa: "Hồ sơ minh chứng TC6.5: Có các hoạt động tư vấn học tập, ngoại khóa, cuộc thi và dịch vụ hỗ trợ giúp cải thiện việc học và khả năng có việc làm.. Thành phần gợi ý: Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học; Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học; Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC038_TC6.5.pdf";
    db.minhChung.updateOne({ _id: "MC038" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC039" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC6.6 - Các dịch vụ hỗ trợ người học được định kỳ đánh giá, đối sánh và cải tiến chất lượng.", moTa: "Hồ sơ minh chứng TC6.6: Các dịch vụ hỗ trợ người học được định kỳ đánh giá, đối sánh và cải tiến chất lượng.. Thành phần gợi ý: Quy định và hồ sơ tư vấn, hỗ trợ học tập cho người học; Hồ sơ hoạt động cố vấn học tập và hỗ trợ người học; Hồ sơ hoạt động ngoại khóa, câu lạc bộ và phát triển kỹ năng.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC039_TC6.6.pdf";
    db.minhChung.updateOne({ _id: "MC039" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC040" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC7.1 - Có hệ thống phòng làm việc, phòng học và các phòng chức năng với trang thiết bị phù hợp để triển khai chương trình đào tạo.", moTa: "Hồ sơ minh chứng TC7.1: Có hệ thống phòng làm việc, phòng học và các phòng chức năng với trang thiết bị phù hợp để triển khai chương trình đào tạo.. Thành phần gợi ý: Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT; Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử; Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC040_TC7.1.pdf";
    db.minhChung.updateOne({ _id: "MC040" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC041" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC7.2 - Các phòng thí nghiệm, phòng thực hành và trang thiết bị đầy đủ, cập nhật và được sử dụng hiệu quả.", moTa: "Hồ sơ minh chứng TC7.2: Các phòng thí nghiệm, phòng thực hành và trang thiết bị đầy đủ, cập nhật và được sử dụng hiệu quả.. Thành phần gợi ý: Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT; Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử; Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC041_TC7.2.pdf";
    db.minhChung.updateOne({ _id: "MC041" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC042" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC7.3 - Có thư viện, thư viện số và nguồn học liệu được cập nhật đáp ứng nhu cầu đào tạo, nghiên cứu khoa học.", moTa: "Hồ sơ minh chứng TC7.3: Có thư viện, thư viện số và nguồn học liệu được cập nhật đáp ứng nhu cầu đào tạo, nghiên cứu khoa học.. Thành phần gợi ý: Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT; Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử; Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC042_TC7.3.pdf";
    db.minhChung.updateOne({ _id: "MC042" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC043" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC7.4 - Có hệ thống công nghệ thông tin, hạ tầng mạng và máy tính dễ tiếp cận, đáp ứng nhu cầu giảng dạy, nghiên cứu và quản lý.", moTa: "Hồ sơ minh chứng TC7.4: Có hệ thống công nghệ thông tin, hạ tầng mạng và máy tính dễ tiếp cận, đáp ứng nhu cầu giảng dạy, nghiên cứu và quản lý.. Thành phần gợi ý: Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT; Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử; Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC043_TC7.4.pdf";
    db.minhChung.updateOne({ _id: "MC043" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC044" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC7.5 - Môi trường tâm lý, xã hội và cảnh quan thiên nhiên tạo thuận lợi cho đào tạo, nghiên cứu và sự thoải mái của người học.", moTa: "Hồ sơ minh chứng TC7.5: Môi trường tâm lý, xã hội và cảnh quan thiên nhiên tạo thuận lợi cho đào tạo, nghiên cứu và sự thoải mái của người học.. Thành phần gợi ý: Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT; Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử; Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC044_TC7.5.pdf";
    db.minhChung.updateOne({ _id: "MC044" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC045" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC7.6 - Các tiêu chuẩn về môi trường, sức khỏe, an toàn được xác định và triển khai phù hợp.", moTa: "Hồ sơ minh chứng TC7.6: Các tiêu chuẩn về môi trường, sức khỏe, an toàn được xác định và triển khai phù hợp.. Thành phần gợi ý: Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT; Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử; Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC045_TC7.6.pdf";
    db.minhChung.updateOne({ _id: "MC045" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC046" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC7.7 - Năng lực đội ngũ hỗ trợ về cơ sở vật chất và trang thiết bị được xác định và đánh giá đáp ứng nhu cầu.", moTa: "Hồ sơ minh chứng TC7.7: Năng lực đội ngũ hỗ trợ về cơ sở vật chất và trang thiết bị được xác định và đánh giá đáp ứng nhu cầu.. Thành phần gợi ý: Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT; Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử; Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC046_TC7.7.pdf";
    db.minhChung.updateOne({ _id: "MC046" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC047" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC7.8 - Chất lượng cơ sở vật chất phục vụ chương trình đào tạo được định kỳ đánh giá và cải tiến.", moTa: "Hồ sơ minh chứng TC7.8: Chất lượng cơ sở vật chất phục vụ chương trình đào tạo được định kỳ đánh giá và cải tiến.. Thành phần gợi ý: Danh mục phòng học, phòng thực hành và trang thiết bị phục vụ CTĐT; Hồ sơ thư viện, học liệu và cơ sở dữ liệu điện tử; Hồ sơ hạ tầng công nghệ thông tin và hệ thống hỗ trợ đào tạo.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC047_TC7.8.pdf";
    db.minhChung.updateOne({ _id: "MC047" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC048" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC8.1 - Tỉ lệ tốt nghiệp, thôi học và thời gian tốt nghiệp trung bình của người học được xác lập, giám sát, đối sánh để cải tiến chất lượng.", moTa: "Hồ sơ minh chứng TC8.1: Tỉ lệ tốt nghiệp, thôi học và thời gian tốt nghiệp trung bình của người học được xác lập, giám sát, đối sánh để cải tiến chất lượng.. Thành phần gợi ý: Thống kê kết quả học tập, tốt nghiệp và thôi học của người học; Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình; Khảo sát việc làm của người học sau tốt nghiệp.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC048_TC8.1.pdf";
    db.minhChung.updateOne({ _id: "MC048" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC049" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC8.2 - Tỉ lệ việc làm, tự tạo việc làm, khởi nghiệp và học tập nâng cao trình độ của người học tốt nghiệp được xác lập, giám sát và đối sánh.", moTa: "Hồ sơ minh chứng TC8.2: Tỉ lệ việc làm, tự tạo việc làm, khởi nghiệp và học tập nâng cao trình độ của người học tốt nghiệp được xác lập, giám sát và đối sánh.. Thành phần gợi ý: Thống kê kết quả học tập, tốt nghiệp và thôi học của người học; Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình; Khảo sát việc làm của người học sau tốt nghiệp.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC049_TC8.2.pdf";
    db.minhChung.updateOne({ _id: "MC049" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC050" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC8.3 - Hoạt động nghiên cứu khoa học và sản phẩm sáng tạo của người học, giảng viên, nghiên cứu viên được xác lập, giám sát và đối sánh.", moTa: "Hồ sơ minh chứng TC8.3: Hoạt động nghiên cứu khoa học và sản phẩm sáng tạo của người học, giảng viên, nghiên cứu viên được xác lập, giám sát và đối sánh.. Thành phần gợi ý: Thống kê kết quả học tập, tốt nghiệp và thôi học của người học; Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình; Khảo sát việc làm của người học sau tốt nghiệp.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC050_TC8.3.pdf";
    db.minhChung.updateOne({ _id: "MC050" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC051" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC8.4 - Dữ liệu về mức độ người học đạt chuẩn đầu ra của chương trình đào tạo được xác lập và giám sát để cải tiến chất lượng.", moTa: "Hồ sơ minh chứng TC8.4: Dữ liệu về mức độ người học đạt chuẩn đầu ra của chương trình đào tạo được xác lập và giám sát để cải tiến chất lượng.. Thành phần gợi ý: Thống kê kết quả học tập, tốt nghiệp và thôi học của người học; Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình; Khảo sát việc làm của người học sau tốt nghiệp.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC051_TC8.4.pdf";
    db.minhChung.updateOne({ _id: "MC051" }, { $set: set });
}
var x = db.minhChung.findOne({ _id: "MC052" });
if (x) {
    var set = { tenMinhChung: "Hồ sơ minh chứng TC8.5 - Mức độ hài lòng của các bên liên quan được xác lập, giám sát, đối sánh để cải tiến chất lượng.", moTa: "Hồ sơ minh chứng TC8.5: Mức độ hài lòng của các bên liên quan được xác lập, giám sát, đối sánh để cải tiến chất lượng.. Thành phần gợi ý: Thống kê kết quả học tập, tốt nghiệp và thôi học của người học; Số liệu thời gian tốt nghiệp trung bình và tỷ lệ hoàn thành chương trình; Khảo sát việc làm của người học sau tốt nghiệp.", loaiFile: "pdf" };
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minhchung/MC052_TC8.5.pdf";
    db.minhChung.updateOne({ _id: "MC052" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC001" });
if (x) {
    var set = { tenFile: "MCC001.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-TRUONG-2026-001";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC001.pdf";
    db.minhChungChung.updateOne({ _id: "MCC001" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC002" });
if (x) {
    var set = { tenFile: "MCC002.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-TRUONG-2026-002";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC002.pdf";
    db.minhChungChung.updateOne({ _id: "MCC002" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC003" });
if (x) {
    var set = { tenFile: "MCC003.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-KCNTT-2026-003";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC003.pdf";
    db.minhChungChung.updateOne({ _id: "MCC003" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC004" });
if (x) {
    var set = { tenFile: "MCC004.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-KCNTT-2026-004";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC004.pdf";
    db.minhChungChung.updateOne({ _id: "MCC004" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC005" });
if (x) {
    var set = { tenFile: "MCC005.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-KCNTT-2026-005";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC005.pdf";
    db.minhChungChung.updateOne({ _id: "MCC005" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC006" });
if (x) {
    var set = { tenFile: "MCC006.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-KCNTT-2026-006";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC006.pdf";
    db.minhChungChung.updateOne({ _id: "MCC006" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC007" });
if (x) {
    var set = { tenFile: "MCC007.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-KCNTT-2026-007";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC007.pdf";
    db.minhChungChung.updateOne({ _id: "MCC007" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC008" });
if (x) {
    var set = { tenFile: "MCC008.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-KCNTT-2026-008";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC008.pdf";
    db.minhChungChung.updateOne({ _id: "MCC008" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC009" });
if (x) {
    var set = { tenFile: "MCC009.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-KCNTT-2026-009";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC009.pdf";
    db.minhChungChung.updateOne({ _id: "MCC009" }, { $set: set });
}
var x = db.minhChungChung.findOne({ _id: "MCC010" });
if (x) {
    var set = { tenFile: "MCC010.pdf", loaiFile: "pdf" };
    if (!x.soHieuVanBan)
        set.soHieuVanBan = "MC-KCNTT-2026-010";
    if (!x.ngayBanHanh)
        set.ngayBanHanh = ISODate("2026-01-15T00:00:00Z");
    if (!isUserUpload(x.duongDanFile))
        set.duongDanFile = "/uploads/minh-chung-chung/MCC010.pdf";
    db.minhChungChung.updateOne({ _id: "MCC010" }, { $set: set });
}
var x = db.baoCaoTieuChi.findOne({ _id: "BC001" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC001" }, { $set: { duongDanFile: "/uploads/bao-cao/BC001_TC1.1.pdf", tenFileBaoCao: "BC001_TC1.1.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC002" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC002" }, { $set: { duongDanFile: "/uploads/bao-cao/BC002_TC1.2.pdf", tenFileBaoCao: "BC002_TC1.2.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC003" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC003" }, { $set: { duongDanFile: "/uploads/bao-cao/BC003_TC1.3.pdf", tenFileBaoCao: "BC003_TC1.3.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC004" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC004" }, { $set: { duongDanFile: "/uploads/bao-cao/BC004_TC1.4.pdf", tenFileBaoCao: "BC004_TC1.4.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC005" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC005" }, { $set: { duongDanFile: "/uploads/bao-cao/BC005_TC1.5.pdf", tenFileBaoCao: "BC005_TC1.5.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC006" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC006" }, { $set: { duongDanFile: "/uploads/bao-cao/BC006_TC1.6.pdf", tenFileBaoCao: "BC006_TC1.6.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC007" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC007" }, { $set: { duongDanFile: "/uploads/bao-cao/BC007_TC2.1.pdf", tenFileBaoCao: "BC007_TC2.1.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC008" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC008" }, { $set: { duongDanFile: "/uploads/bao-cao/BC008_TC2.2.pdf", tenFileBaoCao: "BC008_TC2.2.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC009" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC009" }, { $set: { duongDanFile: "/uploads/bao-cao/BC009_TC2.3.pdf", tenFileBaoCao: "BC009_TC2.3.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC010" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC010" }, { $set: { duongDanFile: "/uploads/bao-cao/BC010_TC2.4.pdf", tenFileBaoCao: "BC010_TC2.4.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC011" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC011" }, { $set: { duongDanFile: "/uploads/bao-cao/BC011_TC2.5.pdf", tenFileBaoCao: "BC011_TC2.5.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC012" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC012" }, { $set: { duongDanFile: "/uploads/bao-cao/BC012_TC2.6.pdf", tenFileBaoCao: "BC012_TC2.6.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC013" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC013" }, { $set: { duongDanFile: "/uploads/bao-cao/BC013_TC2.7.pdf", tenFileBaoCao: "BC013_TC2.7.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC014" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC014" }, { $set: { duongDanFile: "/uploads/bao-cao/BC014_TC3.1.pdf", tenFileBaoCao: "BC014_TC3.1.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC015" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC015" }, { $set: { duongDanFile: "/uploads/bao-cao/BC015_TC3.2.pdf", tenFileBaoCao: "BC015_TC3.2.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC016" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC016" }, { $set: { duongDanFile: "/uploads/bao-cao/BC016_TC3.3.pdf", tenFileBaoCao: "BC016_TC3.3.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC017" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC017" }, { $set: { duongDanFile: "/uploads/bao-cao/BC017_TC3.4.pdf", tenFileBaoCao: "BC017_TC3.4.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC018" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC018" }, { $set: { duongDanFile: "/uploads/bao-cao/BC018_TC3.5.pdf", tenFileBaoCao: "BC018_TC3.5.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC019" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC019" }, { $set: { duongDanFile: "/uploads/bao-cao/BC019_TC4.1.pdf", tenFileBaoCao: "BC019_TC4.1.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC020" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC020" }, { $set: { duongDanFile: "/uploads/bao-cao/BC020_TC4.2.pdf", tenFileBaoCao: "BC020_TC4.2.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC021" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC021" }, { $set: { duongDanFile: "/uploads/bao-cao/BC021_TC4.3.pdf", tenFileBaoCao: "BC021_TC4.3.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC022" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC022" }, { $set: { duongDanFile: "/uploads/bao-cao/BC022_TC4.4.pdf", tenFileBaoCao: "BC022_TC4.4.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC023" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC023" }, { $set: { duongDanFile: "/uploads/bao-cao/BC023_TC4.5.pdf", tenFileBaoCao: "BC023_TC4.5.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC024" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC024" }, { $set: { duongDanFile: "/uploads/bao-cao/BC024_TC4.6.pdf", tenFileBaoCao: "BC024_TC4.6.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC025" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC025" }, { $set: { duongDanFile: "/uploads/bao-cao/BC025_TC4.7.pdf", tenFileBaoCao: "BC025_TC4.7.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC026" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC026" }, { $set: { duongDanFile: "/uploads/bao-cao/BC026_TC5.1.pdf", tenFileBaoCao: "BC026_TC5.1.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC027" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC027" }, { $set: { duongDanFile: "/uploads/bao-cao/BC027_TC5.2.pdf", tenFileBaoCao: "BC027_TC5.2.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC028" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC028" }, { $set: { duongDanFile: "/uploads/bao-cao/BC028_TC5.3.pdf", tenFileBaoCao: "BC028_TC5.3.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC029" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC029" }, { $set: { duongDanFile: "/uploads/bao-cao/BC029_TC5.4.pdf", tenFileBaoCao: "BC029_TC5.4.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC030" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC030" }, { $set: { duongDanFile: "/uploads/bao-cao/BC030_TC5.5.pdf", tenFileBaoCao: "BC030_TC5.5.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC031" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC031" }, { $set: { duongDanFile: "/uploads/bao-cao/BC031_TC5.6.pdf", tenFileBaoCao: "BC031_TC5.6.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC032" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC032" }, { $set: { duongDanFile: "/uploads/bao-cao/BC032_TC5.7.pdf", tenFileBaoCao: "BC032_TC5.7.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC033" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC033" }, { $set: { duongDanFile: "/uploads/bao-cao/BC033_TC5.8.pdf", tenFileBaoCao: "BC033_TC5.8.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC034" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC034" }, { $set: { duongDanFile: "/uploads/bao-cao/BC034_TC6.1.pdf", tenFileBaoCao: "BC034_TC6.1.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC035" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC035" }, { $set: { duongDanFile: "/uploads/bao-cao/BC035_TC6.2.pdf", tenFileBaoCao: "BC035_TC6.2.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC036" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC036" }, { $set: { duongDanFile: "/uploads/bao-cao/BC036_TC6.3.pdf", tenFileBaoCao: "BC036_TC6.3.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC037" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC037" }, { $set: { duongDanFile: "/uploads/bao-cao/BC037_TC6.4.pdf", tenFileBaoCao: "BC037_TC6.4.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC038" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC038" }, { $set: { duongDanFile: "/uploads/bao-cao/BC038_TC6.5.pdf", tenFileBaoCao: "BC038_TC6.5.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC039" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC039" }, { $set: { duongDanFile: "/uploads/bao-cao/BC039_TC6.6.pdf", tenFileBaoCao: "BC039_TC6.6.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC040" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC040" }, { $set: { duongDanFile: "/uploads/bao-cao/BC040_TC7.1.pdf", tenFileBaoCao: "BC040_TC7.1.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC041" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC041" }, { $set: { duongDanFile: "/uploads/bao-cao/BC041_TC7.2.pdf", tenFileBaoCao: "BC041_TC7.2.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC042" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC042" }, { $set: { duongDanFile: "/uploads/bao-cao/BC042_TC7.3.pdf", tenFileBaoCao: "BC042_TC7.3.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC043" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC043" }, { $set: { duongDanFile: "/uploads/bao-cao/BC043_TC7.4.pdf", tenFileBaoCao: "BC043_TC7.4.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC044" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC044" }, { $set: { duongDanFile: "/uploads/bao-cao/BC044_TC7.5.pdf", tenFileBaoCao: "BC044_TC7.5.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC045" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC045" }, { $set: { duongDanFile: "/uploads/bao-cao/BC045_TC7.6.pdf", tenFileBaoCao: "BC045_TC7.6.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC046" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC046" }, { $set: { duongDanFile: "/uploads/bao-cao/BC046_TC7.7.pdf", tenFileBaoCao: "BC046_TC7.7.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC047" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC047" }, { $set: { duongDanFile: "/uploads/bao-cao/BC047_TC7.8.pdf", tenFileBaoCao: "BC047_TC7.8.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC048" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC048" }, { $set: { duongDanFile: "/uploads/bao-cao/BC048_TC8.1.pdf", tenFileBaoCao: "BC048_TC8.1.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC049" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC049" }, { $set: { duongDanFile: "/uploads/bao-cao/BC049_TC8.2.pdf", tenFileBaoCao: "BC049_TC8.2.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC050" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC050" }, { $set: { duongDanFile: "/uploads/bao-cao/BC050_TC8.3.pdf", tenFileBaoCao: "BC050_TC8.3.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC051" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC051" }, { $set: { duongDanFile: "/uploads/bao-cao/BC051_TC8.4.pdf", tenFileBaoCao: "BC051_TC8.4.pdf" } });
var x = db.baoCaoTieuChi.findOne({ _id: "BC052" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoTieuChi.updateOne({ _id: "BC052" }, { $set: { duongDanFile: "/uploads/bao-cao/BC052_TC8.5.pdf", tenFileBaoCao: "BC052_TC8.5.pdf" } });
var x = db.baoCaoNhom.findOne({ _id: "BCN001" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoNhom.updateOne({ _id: "BCN001" }, { $set: { duongDanFile: "/uploads/bao-cao/BCN001_TC1.pdf", tenFileBaoCao: "BCN001_TC1.pdf" } });
var x = db.baoCaoNhom.findOne({ _id: "BCN002" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoNhom.updateOne({ _id: "BCN002" }, { $set: { duongDanFile: "/uploads/bao-cao/BCN002_TC2.pdf", tenFileBaoCao: "BCN002_TC2.pdf" } });
var x = db.baoCaoNhom.findOne({ _id: "BCN003" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoNhom.updateOne({ _id: "BCN003" }, { $set: { duongDanFile: "/uploads/bao-cao/BCN003_TC3.pdf", tenFileBaoCao: "BCN003_TC3.pdf" } });
var x = db.baoCaoNhom.findOne({ _id: "BCN004" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoNhom.updateOne({ _id: "BCN004" }, { $set: { duongDanFile: "/uploads/bao-cao/BCN004_TC4.pdf", tenFileBaoCao: "BCN004_TC4.pdf" } });
var x = db.baoCaoNhom.findOne({ _id: "BCN005" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoNhom.updateOne({ _id: "BCN005" }, { $set: { duongDanFile: "/uploads/bao-cao/BCN005_TC5.pdf", tenFileBaoCao: "BCN005_TC5.pdf" } });
var x = db.baoCaoNhom.findOne({ _id: "BCN006" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoNhom.updateOne({ _id: "BCN006" }, { $set: { duongDanFile: "/uploads/bao-cao/BCN006_TC6.pdf", tenFileBaoCao: "BCN006_TC6.pdf" } });
var x = db.baoCaoNhom.findOne({ _id: "BCN007" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoNhom.updateOne({ _id: "BCN007" }, { $set: { duongDanFile: "/uploads/bao-cao/BCN007_TC7.pdf", tenFileBaoCao: "BCN007_TC7.pdf" } });
var x = db.baoCaoNhom.findOne({ _id: "BCN008" });
if (x && !isUserUpload(x.duongDanFile))
    db.baoCaoNhom.updateOne({ _id: "BCN008" }, { $set: { duongDanFile: "/uploads/bao-cao/BCN008_TC8.pdf", tenFileBaoCao: "BCN008_TC8.pdf" } });
var x = db.baoCaoPhan.findOne({ _id: "BCP001" });
if (x) {
    var set = {};
    if (!isUserUpload(x.duongDanFile)) {
        set.duongDanFile = "/uploads/bao-cao/BCP001_Phan1_KhaiQuat.pdf";
        set.tenFileBaoCao = "BCP001_Phan1_KhaiQuat.pdf";
    }
    set.danhSachMinhChungCapTruong = ["MCC001", "MCC002"];
    set.danhSachMinhChungCapKhoa = ["MCC003", "MCC005", "MCC006", "MCC009"];
    set.danhSachMinhChungTieuChi = ["MC001", "MC002", "MC003", "MC004", "MC005", "MC006", "MC007", "MC008", "MC009", "MC010", "MC011", "MC012", "MC013", "MC014", "MC015", "MC016", "MC017", "MC018", "MC019", "MC020", "MC021", "MC022", "MC023", "MC024", "MC025", "MC026", "MC027", "MC028", "MC029", "MC030", "MC031", "MC032", "MC033", "MC034", "MC035", "MC036", "MC037", "MC038", "MC039", "MC040", "MC041", "MC042", "MC043", "MC044", "MC045", "MC046", "MC047", "MC048", "MC049", "MC050", "MC051", "MC052"];
    db.baoCaoPhan.updateOne({ _id: "BCP001" }, { $set: set });
}
var x = db.baoCaoPhan.findOne({ _id: "BCP003" });
if (x) {
    var set = {};
    if (!isUserUpload(x.duongDanFile)) {
        set.duongDanFile = "/uploads/bao-cao/BCP003_Phan3_KetLuan.pdf";
        set.tenFileBaoCao = "BCP003_Phan3_KetLuan.pdf";
    }
    set.danhSachMinhChungCapTruong = ["MCC001", "MCC002"];
    set.danhSachMinhChungCapKhoa = ["MCC003", "MCC005", "MCC006", "MCC009"];
    set.danhSachMinhChungTieuChi = ["MC001", "MC002", "MC003", "MC004", "MC005", "MC006", "MC007", "MC008", "MC009", "MC010", "MC011", "MC012", "MC013", "MC014", "MC015", "MC016", "MC017", "MC018", "MC019", "MC020", "MC021", "MC022", "MC023", "MC024", "MC025", "MC026", "MC027", "MC028", "MC029", "MC030", "MC031", "MC032", "MC033", "MC034", "MC035", "MC036", "MC037", "MC038", "MC039", "MC040", "MC041", "MC042", "MC043", "MC044", "MC045", "MC046", "MC047", "MC048", "MC049", "MC050", "MC051", "MC052"];
    db.baoCaoPhan.updateOne({ _id: "BCP003" }, { $set: set });
}
var x = db.baoCaoPhan.findOne({ _id: "BCP004" });
if (x) {
    var set = {};
    if (!isUserUpload(x.duongDanFile)) {
        set.duongDanFile = "/uploads/bao-cao/BCP004_Phan4_PhuLuc.pdf";
        set.tenFileBaoCao = "BCP004_Phan4_PhuLuc.pdf";
    }
    db.baoCaoPhan.updateOne({ _id: "BCP004" }, { $set: set });
}
print("DONE - dữ liệu/file đã được đồng bộ, CTĐT không bị thay đổi.");
printjson({ minhChung: db.minhChung.countDocuments({ duongDanFile: { $exists: true, $ne: "" } }), minhChungChung: db.minhChungChung.countDocuments({ duongDanFile: { $exists: true, $ne: "" } }), baoCaoTieuChi: db.baoCaoTieuChi.countDocuments({ duongDanFile: { $exists: true, $ne: "" } }), baoCaoNhom: db.baoCaoNhom.countDocuments({ duongDanFile: { $exists: true, $ne: "" } }), baoCaoPhan: db.baoCaoPhan.countDocuments({ duongDanFile: { $exists: true, $ne: "" } }) });
// Đồng bộ báo cáo nhóm theo đúng phân công Excel và mức duyệt
const canonicalGroupReports = [
    ["BCN001", "TC1", "NT001"], ["BCN002", "TC2", "NT001"],
    ["BCN003", "TC3", "NT002"], ["BCN004", "TC4", "NT002"],
    ["BCN005", "TC5", "NT003"], ["BCN006", "TC6", "NT004"],
    ["BCN007", "TC7", "NT004"], ["BCN008", "TC8", "NT005"]
];
canonicalGroupReports.forEach(r => {
    const g = db.nhomTieuChi.findOne({ _id: r[2] });
    const x = db.baoCaoNhom.findOne({ _id: r[0] });
    if (!x)
        return;
    const set = { maTieuChuan: r[1], maNhomTieuChi: r[2], mucDoDuyet: (x.mucDoDuyet == null ? 25 : x.mucDoDuyet), capDuyetHienTai: (x.capDuyetHienTai || "Thư ký nhóm") };
    if (g) {
        set.maThuKyNhom = g.maThuKyNhom;
        set.maTruongNhom = g.maTruongNhom;
    }
    db.baoCaoNhom.updateOne({ _id: r[0] }, { $set: set });
});
