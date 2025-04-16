package com.wbsrisktaskerx.wbsrisktaskerx.service.admin;

import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.MaskUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final AdminJpaQueryRepository adminJpaQueryRepository;

    public AdminService(AdminRepository adminRepository, AdminJpaQueryRepository adminJpaQueryRepository) {
        this.adminRepository = adminRepository;
        this.adminJpaQueryRepository = adminJpaQueryRepository;
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
}
