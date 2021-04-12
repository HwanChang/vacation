package com.hwanchang.vacation.security;

import com.hwanchang.vacation.entity.user.Role;
import com.hwanchang.vacation.entity.user.User;
import com.hwanchang.vacation.error.NotFoundException;
import com.hwanchang.vacation.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.ClassUtils.isAssignable;
import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

@RequiredArgsConstructor
@Component
public class JwtProvider implements AuthenticationProvider {

    private final Jwt jwt;

    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) authentication;
        return processUserAuthentication(jwtToken.authenticationRequest());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return isAssignable(JwtToken.class, authentication);
    }

    private Authentication processUserAuthentication(AuthenticationRequest request) {
        try {
            User user = userService.login(request.getPrincipal(), request.getCredentials());
            JwtToken authenticated = new JwtToken(
                    new JwtAuthentication(user.getUserId(), user.getEmail()),
                    null,
                    commaSeparatedStringToAuthorityList(
                            user.getRoles().stream().map(Role::value).collect(joining(","))
                    )
            );
            authenticated.setDetails(
                    new AuthenticationResult(
                            user.createToken(jwt, user.getRoles().stream().map(Role::value).toArray(String[]::new)),
                            user
                    )
            );
            return authenticated;
        } catch (NotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new BadCredentialsException(e.getMessage());
        } catch (DataAccessException e) {
            throw new AuthenticationServiceException(e.getMessage(), e);
        }
    }

}
