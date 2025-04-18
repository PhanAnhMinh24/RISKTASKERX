package com.wbsrisktaskerx.wbsrisktaskerx.service.admin;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.RoleRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.service.role.RoleService;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.MaskUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final RoleRepository roleRepository;
    private final AdminJpaQueryRepository adminJpaQueryRepository;
    private final RoleService roleService;

    public AdminService(AdminRepository adminRepository,RoleRepository roleRepository, AdminJpaQueryRepository adminJpaQueryRepository,
                        RoleService roleService) {
        this.adminRepository = adminRepository;
        this.roleRepository = roleRepository;
        this.adminJpaQueryRepository = adminJpaQueryRepository;
        this.roleService = roleService;
    }

    @Override
    public Page<AdminResponse> searchAndFilterAdmin(PagingRequest<SearchFilterAdminRequest> request) {
        return adminJpaQueryRepository.searchedAndFilteredAdmin(request)
                .map(ad -> AdminResponse.builder()
                        .id(ad.getId())
                        .fullName(MaskUtils.mask(ad.getFullName()))
                        .email(MaskUtils.mask(ad.getEmail()))
                        .role(ad.getRole())
                        .departmentName(ad.getDepartmentName())
                        .lastLogin(ad.getLastLogin())
                        .isActive(ad.getIsActive())
                        .build());
    }

    @Override
    public List<AdminResponse> searchedAndFilteredAdminNoPaging(SearchFilterAdminRequest request) {
        return adminJpaQueryRepository.searchedAndFilteredAdminNoPaging(request).stream()
                .map(ad -> AdminResponse.builder()
                        .id(ad.getId())
                        .fullName(MaskUtils.mask(ad.getFullName()))
                        .email(MaskUtils.mask(ad.getEmail()))
                        .role(ad.getRole())
                        .departmentName(ad.getDepartmentName())
                        .lastLogin(ad.getLastLogin())
                        .isActive(ad.getIsActive())
                        .build())
                .toList();
    }

    public Admin findAdminById(Integer adminId) {
        Optional<Admin> admin = adminRepository.findById(adminId);
        if (admin.isEmpty()) {
            throw new AppException(ErrorCode.ACCOUNT_ADMIN_NOT_FOUND);
        }
        return admin.get();
    }

    @Override
    public AdminResponse getAdminById(int id) {
        Admin admin = findAdminById(id);
        return new AdminResponse(
                admin.getId(),
                admin.getFullName(),
                admin.getEmail(),
                admin.getPhoneNumber(),
                admin.getRole(),
                admin.getDepartmentName(),
                admin.getLastLogin(),
                admin.getDateOfBirth(),
                admin.getIsActive()
        );
    }

    @Override
    public boolean updateAdmin(AdminRequest adminRequest) {
        Admin admin = findAdminById(adminRequest.getId());

        Integer roleId = Optional.ofNullable(adminRequest.getRole())
                .map(Role::getId)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_ID_REQUIRED));

        Role role = roleService.findById(roleId);

        admin.setRole(role);
        admin.setDepartmentName(adminRequest.getDepartmentName());
        admin.setFullName(adminRequest.getName());
        admin.setPhoneNumber(adminRequest.getPhoneNumber());
        admin.setDateOfBirth(adminRequest.getDateOfBirth());
        admin.setIsActive(adminRequest.getIsActive());
        adminRepository.save(admin);
        return true;
    }
}
