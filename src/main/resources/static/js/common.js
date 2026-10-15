const API="/api";
const $=s=>document.querySelector(s);
const $$=s=>[...document.querySelectorAll(s)];
const esc=v=>String(v??"").replace(/[&<>"']/g,m=>({"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;","'":"&#039;"}[m]));

async function api(path,opt={}){
  const maxReadyRetries = opt.readyRetries ?? 30;
  for(let attempt=0;;attempt++){
    const requestOpt={...opt};
    delete requestOpt.readyRetries;
    const r=await fetch(API+path,{cache:"no-store",headers:{"Content-Type":"application/json",...(requestOpt.headers||{})},...requestOpt});
    if(r.status===401){ location.href='/login'; throw new Error('Phiên đăng nhập đã hết hạn'); }
    if(r.status===503 && attempt<maxReadyRetries){
      await new Promise(resolve=>setTimeout(resolve,500));
      continue;
    }
    if(!r.ok){
      let msg="Có lỗi xảy ra";
      try{const j=await r.json();msg=j.message||j.detail||msg}catch(_){const t=await r.text();if(t)msg=t}
      throw new Error(msg);
    }
    if(r.status===204)return null;
    const text=await r.text(); return text?JSON.parse(text):null;
  }
}
function openModal(id){document.getElementById(id).classList.add("show")}
function closeModal(id){document.getElementById(id).classList.remove("show")}
function fmtDate(v){return v?String(v).substring(0,10):""}

function standardCode(v){
  const n=String(v||'').match(/(\d+)/)?.[1];
  return n?`TC${n}`:String(v||'');
}
function criterionCode(v){
  const raw=String(v||'');
  let m=raw.match(/^TC(\d+)[.\-_]?(\d+)?$/i);
  if(m) return m[2]?`TC${+m[1]}.${+m[2]}`:`TC${+m[1]}`;
  m=raw.match(/^H(\d+)\.(?:\d+\.)?(\d+)$/i);
  if(m) return `TC${+m[1]}.${+m[2]}`;
  m=raw.match(/^H(\d+)$/i);
  if(m) return `TC${+m[1]}`;
  return raw;
}
function evidenceApprovalPercent(state){
  const s=String(state||'');
  if(/đã duyệt/i.test(s)) return 100;
  if(/chờ thư ký/i.test(s)) return 60;
  if(/chờ trưởng nhóm|từ chối/i.test(s)) return 30;
  return 30;
}
function reportApprovalPercent(report){
  if(report && Number.isFinite(+report.mucDoDuyet)) return +report.mucDoDuyet;
  const s=String(report?.trangThai||'');
  if(/đã duyệt/i.test(s)) return 100;
  if(/tt qlcl/i.test(s)) return 75;
  if(/hội đồng/i.test(s)) return 50;
  if(/trưởng nhóm/i.test(s) || /chờ duyệt/i.test(s)) return 25;
  return 25;
}

function statusBadge(v){
  const s=String(v||"");
  if(/hoàn thành|đã duyệt|hoạt động|đang áp dụng/i.test(s)) return "b-green";
  if(/chờ|đang|phân công|soạn/i.test(s)) return "b-blue";
  if(/từ chối|yêu cầu chỉnh sửa|khóa|ngừng|tạm dừng/i.test(s)) return "b-red";
  return "b-gray";
}
function activeNav(){
  $$(".nav a").forEach(a=>{ if(a.getAttribute("href")===location.pathname)a.classList.add("active"); });
}
function normalizeRole(s){return String(s||'').normalize('NFD').replace(/[\u0300-\u036f]/g,'').replace(/đ/g,'d').replace(/Đ/g,'D').toLowerCase()}
function hasRole(me,key){return (me?.vaiTroNames||[]).some(x=>normalizeRole(x).includes(normalizeRole(key)))}

async function loadCurrentUser(){
  try{
    const me=await api('/auth/me');
    document.body.dataset.role=me.vaiTro||'';
    $$('.user').forEach(box=>{
      const initials=(me.hoTen||'ND').trim().split(/\s+/).slice(-2).map(x=>x[0]).join('').toUpperCase();
      box.innerHTML=`<div class="avatar">${esc(initials)}</div><div><b>${esc(me.hoTen||me.email)}</b><small>${esc(me.vaiTro||'Người dùng')}</small><span class="role-pill">${esc(me.email||'')}</span></div>`;
    });

    // Ẩn chức năng quản trị hệ thống với tài khoản không phải Quản trị.
    if(!hasRole(me,'Quản trị')){
      $$('.nav a[href="/admin/he-thong"]').forEach(x=>x.style.display='none');
    }

    // Thành viên chỉ cần các chức năng thực hiện công việc; Trưởng nhóm có thêm phân công.
    if(hasRole(me,'Thành viên') && !hasRole(me,'Trưởng nhóm') && !hasRole(me,'Quản trị')){
      $$('.nav a[href="/admin/ctdt"],.nav a[href="/admin/tieu-chuan"],.nav a[href="/admin/phan-cong"]').forEach(x=>x.style.display='none');
    }
    // 'Thư ký nhóm' là chức vụ nội bộ của VT004, không phải vai trò hệ thống riêng.
    // Chỉ người thực sự được gán làm Thư ký nhóm mới thấy chức năng tổng hợp báo cáo nhóm.
    if(hasRole(me,'Thành viên') && !hasRole(me,'Quản trị')){
      try{
        const info=await api('/thanh-vien/thu-ky-nhom/thong-tin');
        if(!info?.laThuKyNhom) $$('.nav a[href="/thanh-vien/tong-hop-nhom"]').forEach(x=>x.style.display='none');
      }catch(_){
        $$('.nav a[href="/thanh-vien/tong-hop-nhom"]').forEach(x=>x.style.display='none');
      }
    }
    if(hasRole(me,'Trưởng nhóm') && !hasRole(me,'Quản trị')){
      $$('.nav a[href="/admin/ctdt"],.nav a[href="/admin/tieu-chuan"]').forEach(x=>x.style.display='none');
    }

    const side=$('.sidebar');
    if(side && !$('#logoutBtn')){
      const b=document.createElement('button');b.id='logoutBtn';b.className='logout-btn';b.textContent='↪ Đăng xuất';
      b.onclick=async()=>{try{await api('/auth/logout',{method:'POST'});}finally{location.href='/login'}};
      const version=side.querySelector('.version');side.insertBefore(b,version||null);
    }
    return me;
  }catch(e){return null}
}

function showGlobalError(error){
  const msg=error instanceof Error?error.message:String(error||'Có lỗi xảy ra');
  let box=document.getElementById('globalErrorBox');
  if(!box){
    box=document.createElement('div');box.id='globalErrorBox';
    box.style.cssText='margin:0 0 16px;padding:12px 14px;border:1px solid #fecaca;background:#fff1f2;color:#991b1b;border-radius:10px;font-size:14px;white-space:pre-wrap';
    const main=document.querySelector('.main');
    if(main) main.insertBefore(box,main.firstChild); else document.body.prepend(box);
  }
  box.textContent='Không tải được dữ liệu: '+msg;
}
window.addEventListener('unhandledrejection',e=>{console.error(e.reason);showGlobalError(e.reason);});
window.addEventListener('error',e=>{if(e.error){console.error(e.error);showGlobalError(e.error);}});

activeNav();
if(location.pathname!='/login') loadCurrentUser();
