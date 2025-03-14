package com.wbsrisktaskerx.wbsrisktaskerx.service.admin;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class AdminDetailsImpl implements UserDetails {

    private final Admin admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Nếu cần mở rộng, bạn có thể trả về danh sách các quyền (roles) của admin tại đây
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getEmail(); // Sử dụng email làm username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Giả định tài khoản luôn không hết hạn
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Giả định tài khoản luôn không bị khóa
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Giả định mật khẩu không bao giờ hết hạn
    }

    @Override
    public boolean isEnabled() {
        return admin.getIsActive(); // Kiểm tra trạng thái hoạt động của admin
    }
}
