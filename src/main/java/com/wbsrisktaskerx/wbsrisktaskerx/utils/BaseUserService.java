//package com.wbsrisktaskerx.wbsrisktaskerx.utils;
//
//import com.wbsrisktaskerx.wbsrisktaskerx.entity.Admin;
//import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
//import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
//import com.wbsrisktaskerx.wbsrisktaskerx.repository.AdminRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//@AllArgsConstructor
//public class BaseUserService {
//    private final AdminRepository adminRepository;
//
//    public Admin getCurrentUser() {
//        String email = JwtUtils.getCurrentAdmin();
//        Optional<Admin> user = adminRepository.findByEmail(email);
//        if (user.isEmpty()) {
//            throw new AppException(ErrorCode.USER_NOT_FOUND);
//        }
//        return user.get();
//    }
//
//    public List<ProfileResponse> getAllProfile(List<Long> userIds) {
//        List<Admin> users = userRepository.findAllById(userIds);
//        return users.stream()
//                .map(user -> ProfileResponse.builder()
//                        .id(user.getId())
//                        .firstName(user.getFirstName())
//                        .lastName(user.getLastName())
//                        .email(user.getEmail())
//                        .phoneNumber(user.getPhoneNumber())
//                        .profileImg(user.getProfileImg())
//                        .build())
//                .collect(Collectors.toList());
//    }
//
//    public static PageRequest buildPageRequest(PagingRequest pagingRequest) {
//        int page = pagingRequest.getPage() > 0 ? pagingRequest.getPage() - 1 : 0;
//        Sort.Direction sortDirection = pagingRequest.getSortBy() != null ? pagingRequest.getSortBy() : Sort.Direction.ASC;
//        return StringUtils.isBlank(pagingRequest.getSortKey())
//                ? PageRequest.of(page, pagingRequest.getSize())
//                : PageRequest.of(page, pagingRequest.getSize(), Sort.by(sortDirection, pagingRequest.getSortKey()));
//    }
//}
