package com.wbsrisktaskerx.wbsrisktaskerx.service.admin;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.AdminMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.ActiveRoleResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.RoleRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.service.otp.AdminEmailServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.service.role.RoleService;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.MaskUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final AdminJpaQueryRepository adminJpaQueryRepository;
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public AdminService(AdminRepository adminRepository, AdminJpaQueryRepository adminJpaQueryRepository,
                        RoleService roleService, RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.adminJpaQueryRepository = adminJpaQueryRepository;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @Override
    public AdminResponse addAdmin(AdminRequest request) {
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        } else if (adminRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_EXISTED);
        } else if (String.valueOf(request.getRole()).isEmpty() ||
                String.valueOf(request.getDepartmentName()).isEmpty() ||
                request.getName().isEmpty() ||
                request.getPhoneNumber().isEmpty() ||
                request.getEmail().isEmpty() ||
                request.getDateOfBirth() == null) {
            throw new AppException(ErrorCode.FIELD_IS_REQUIRED);
        } else if (!request.getEmail().matches(EmailConstants.EMAIL_REGEX)) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }

        List<ActiveRoleResponse> activeRoles = roleService.getAllActiveRole();

        ActiveRoleResponse activeRole = activeRoles.stream()
                .filter(r -> r.getId().equals(request.getRole().getId()))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE_NAME));

        Role roleEntity = roleRepository.findById(activeRole.getId())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE_NAME));

        String password = AdminEmailServiceImpl.getTemporaryPassword();

        Admin admin = AdminMapper.adminMapperByAdminRequest(request, password, roleEntity);
        admin.setRole(roleEntity);
        return AdminMapper.adminMapper(adminRepository.save(admin));
    }


    @Override
    public Page<AdminResponse> searchAndFilterAdmin(PagingRequest<SearchFilterAdminRequest> request) {
        return adminJpaQueryRepository.searchedAndFilteredAdmin(request);
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
        return AdminMapper.adminMapper(admin);
    }

    @Override
    public boolean updateAdmin(AdminRequest adminRequest) {
        Admin admin = findAdminById(adminRequest.getId());

        List<ActiveRoleResponse> activeRoles = roleService.getAllActiveRole();

        ActiveRoleResponse activeRole = activeRoles.stream()
                        .filter(r -> r.getId().equals(adminRequest.getRole().getId()))
                                .findFirst()
                                .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE_NAME));

        Role roleEntity = roleRepository.findById(activeRole.getId())
                        .orElseThrow(() -> new AppException(ErrorCode.INVALID_ROLE_NAME));

        admin.setRole(roleEntity);
        admin.setDepartmentName(adminRequest.getDepartmentName());
        admin.setFullName(adminRequest.getName());
        admin.setPhoneNumber(adminRequest.getPhoneNumber());
        admin.setDateOfBirth(adminRequest.getDateOfBirth());
        admin.setIsActive(adminRequest.getIsActive());
        adminRepository.save(admin);
        return true;
    }
}
