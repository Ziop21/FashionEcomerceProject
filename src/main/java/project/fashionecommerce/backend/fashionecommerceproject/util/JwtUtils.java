package project.fashionecommerce.backend.fashionecommerceproject.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import io.jsonwebtoken.*;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;

@Component
public class JwtUtils implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${fashion_ecommerce.app.jwtSecret}")
    private String jwtSecret;

    @Value("${fashion_ecommerce.app.jwtExpirationMs}")
    private Long jwtExpirationMs;
    @Value("${fashion_ecommerce.app.cookieExpirationS}")
    private Long cookieExpirationS;

    @Value("${fashion_ecommerce.app.jwtRefreshCookieName}")
    private String jwtRefreshCookie;


    public ResponseCookie generateRefreshJwtCookie(String refreshToken, String path) {
        return generateCookie(jwtRefreshCookie, refreshToken, path);
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    public ResponseCookie getCleanJwtRefreshCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtRefreshCookie, null).path("/api/auth/refresh-token").build();
        return cookie;
    }

    public Claims getClaimsFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parseClaimsJws(token).getBody();
    }

    public boolean validateJwtToken(String authToken, HttpServletRequest request) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes()).build().parse(authToken);
            return true;
        }
        catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String generateTokenFromUser(UserDetailsImpl userDetails) {
        List<String> roles = userDetails.getRoles();

        Map<String, Object> tokenData = new HashMap<>();
        tokenData.put("userId", userDetails.getEmail());
        tokenData.put("email", userDetails.getEmail());
        tokenData.put("roles", roles);
        return Jwts.builder()
                .setClaims(tokenData)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
    }
    public String generateTokenFromCartId(String cartId) {
        return Jwts.builder()
                .setSubject(cartId)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret.getBytes())
                .compact();
    }

    public String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateCookie(String name, String value, String path) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path(path)
                .maxAge(cookieExpirationS)
                .httpOnly(true)
                .build();
        return cookie;
    }
}
