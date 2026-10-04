const API="/api";
const $=s=>document.querySelector(s);
const $$=s=>[...document.querySelectorAll(s)];
const esc=v=>String(v??"").replace(/[&<>"']/g,m=>({"&":"&amp;","<":"&lt;",">":"&gt;","\"":"&quot;","'":"&#039;"}[m]));

async function api(path,opt={}){
  const r=await fetch(API+path,{cache:"no-store",headers:{"Content-Type":"application/json",...(opt.headers||{})},...opt});
  if(r.status===401){ location.href='/login'; throw new Error('Phiên đăng nhập đã hết hạn'); }
  if(!r.ok){
    let msg="Có lỗi xảy ra";
    try{const j=await r.json();msg=j.message||j.detail||msg}catch(_){const t=await r.text();if(t)msg=t}
    throw new Error(msg);
  }
  if(r.status===204)return null;
  const text=await r.text(); return text?JSON.parse(text):null;
}
function openModal(id){document.getElementById(id).classList.add("show")}
function closeModal(id){document.getElementById(id).classList.remove("show")}
function fmtDate(v){return v?String(v).substring(0,10):""}
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

activeNav();
if(location.pathname!='/login') loadCurrentUser();
