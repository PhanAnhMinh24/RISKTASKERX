package com.wbsrisktaskerx.wbsrisktaskerx.service.admin;

import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EmailConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.mapper.AdminMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.PagingRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.AdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.request.SearchFilterAdminRequest;
import com.wbsrisktaskerx.wbsrisktaskerx.pojo.response.AdminResponse;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminJpaQueryRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.MaskUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final AdminJpaQueryRepository adminJpaQueryRepository;

    public AdminService(AdminRepository adminRepository, AdminJpaQueryRepository adminJpaQueryRepository) {
        this.adminRepository = adminRepository;
        this.adminJpaQueryRepository = adminJpaQueryRepository;
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
                throw new AppException(ErrorCode.FIELT_IS_REQUIRED);
        } else if (!request.getEmail().matches(EmailConstants.EMAIL_REGEX)) {
            throw new AppException(ErrorCode.INVALID_EMAIL);
        }

        adminRepository.save(AdminMapper.adminMapperByAdminRequest(request));
        return AdminMapper.adminMapper(AdminMapper.adminMapperByAdminRequest(request));
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


}
