package com.wbsrisktaskerx.wbsrisktaskerx.service.admin;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.PasswordConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.entity.Role;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.AdminMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.RoleRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.service.otp.AdminEmailServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.service.role.RoleService;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.MaskUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
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

        String password = AdminEmailServiceImpl.getTemporaryPassword();
        adminRepository.save(AdminMapper.adminMapperByAdminRequest(request, password));
        return AdminMapper.adminMapper(AdminMapper.adminMapperByAdminRequest(request, password));
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
