package com.wbsrisktaskerx.wbsrisktaskerx.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.CommonConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointConstants;
import com.wbsrisktaskerx.wbsrisktaskerx.common.constants.EndpointUtil;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.AppException;
import com.wbsrisktaskerx.wbsrisktaskerx.exception.ErrorCode;
import com.wbsrisktaskerx.wbsrisktaskerx.service.admin.AdminDetailsServiceImpl;
import com.wbsrisktaskerx.wbsrisktaskerx.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;
    private final AdminDetailsServiceImpl adminDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (StringUtils.isBlank(jwt)) {
                setErrorMessage(response, new AppException(ErrorCode.UNAUTHORIZED));
                return;
            }
            if (jwtUtils.validateJwtToken(jwt)) {
                String username = jwtUtils.getUserNameFromJwtToken(jwt);

                UserDetails userDetails = adminDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(),
                                request.getHeader("Authorization"),
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (AppException e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
            setErrorMessage(response, e);
        } catch (Exception ex) {
            log.error("Unexpected error in authentication filter: {}", ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void setErrorMessage(HttpServletResponse response, AppException e) throws IOException {
        response.setStatus(e.getErrorCode().getStatus().value());
        response.setContentType(CommonConstants.APPLICATION_JSON_UTF8);
        response.getWriter().write(objectMapper.writeValueAsString(
                Map.of(CommonConstants.MESSAGE, e.getErrorCode().getMessage(),
                        CommonConstants.STATUS, e.getErrorCode().getStatus().value())));
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(CommonConstants.AUTHORIZATION);

        if (StringUtils.isNotBlank(headerAuth) && headerAuth.startsWith(CommonConstants.BEARER)) {
            return headerAuth.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        return path.equals(EndpointConstants.AUTH + EndpointConstants.SIGN_IN) ||
                path.equals(EndpointConstants.AUTH + EndpointConstants.SIGN_UP) ||
                path.startsWith(EndpointConstants.ADMIN + EndpointUtil.OTP + EndpointUtil.EMAIL) ||
                path.startsWith(EndpointConstants.ADMIN +EndpointUtil.OTP + EndpointUtil.VERIFY) ||
                path.startsWith(EndpointConstants.ADMIN +EndpointUtil.OTP + EndpointUtil.FORGOT_PASSWORD) ||
                path.equals(EndpointUtil.OTP) ||
                path.startsWith(EndpointConstants.ACTUATOR) ||
                path.startsWith(EndpointConstants.SWAGGER_ICO) ||
                (path.startsWith(EndpointConstants.SWAGGER_UI) && HttpMethod.GET.matches(method)) ||
                (path.startsWith(EndpointConstants.SWAGGER_API_DOCS) && HttpMethod.GET.matches(method)) ||
                (path.startsWith(EndpointConstants.SWAGGER_API_DOCS + EndpointConstants.SWAGGER_CONFIG) && HttpMethod.GET.matches(method));
    }

}
