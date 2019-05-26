package io.github.firefang.power.server.auth;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.firefang.power.common.util.StringUtil;
import io.github.firefang.power.exception.UnAuthorizedException;
import io.github.firefang.power.login.IAuthService;
import io.github.firefang.power.server.IPowerConstants;
import io.github.firefang.power.server.cache.TokenCache;
import io.github.firefang.power.server.entity.domain.UserAuthDO;
import io.github.firefang.power.server.mapper.IUserAuthMapper;

/**
 * @author xinufo
 *
 */
@Service
public class AuthService implements IAuthService {
    private IUserAuthMapper authMapper;
    private PasswordEncoder encoder;

    public AuthService(IUserAuthMapper authMapper, PasswordEncoder encoder) {
        this.authMapper = authMapper;
        this.encoder = encoder;
    }

    @Override
    public Object login(String username, String password, HttpServletRequest request) {
        UserAuthDO user = authMapper.findByUsername(username);
        if (user == null || !encoder.matches(password, user.getPassword())) {
            throw new UnAuthorizedException();
        }
        // 重用Key以实现账号多人登录
        String tokenKey = user.getTokenKey();
        if (tokenKey == null) {
            tokenKey = TokenHelper.generateKey();
            // update db
            user.setTokenKey(tokenKey);
            authMapper.updateById(user);
        }
        return TokenHelper.create(tokenKey, user.getId());
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = request.getHeader(IPowerConstants.TOKEN_KEY);
        Integer userId = TokenHelper.decodeUserId(token);
        UserAuthDO user = authMapper.findOneById(userId);
        if (user != null) {
            user.setTokenKey(null);
            authMapper.updateById(user);
        }
    }

    @Override
    public void auth(Object info) throws UnAuthorizedException {
        String token = (String) info;
        if (!StringUtil.hasLength(token)) {
            throw new UnAuthorizedException();
        }
        if (TokenCache.getUserId(token) != null) {
            // 已验证过该Token
            return;
        }
        Integer userId = TokenHelper.decodeUserId(token);
        UserAuthDO user = authMapper.findOneById(userId);
        if (user == null || user.getTokenKey() == null || !TokenHelper.verify(user.getTokenKey(), token)) {
            throw new UnAuthorizedException();
        } else {
            TokenCache.setUserId(token, user.getId());
        }
    }

}
