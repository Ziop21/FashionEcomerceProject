package project.fashionecommerce.backend.fashionecommerceproject.util;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import project.fashionecommerce.backend.fashionecommerceproject.config.security.userDetails.Implement.UserDetailsImpl;
import project.fashionecommerce.backend.fashionecommerceproject.dto.user.User;

@Component
public class JwtUtils implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${fashion_ecommerce.app.jwtSecret}")
    private String jwtSecret;

    @Value("${fashion_ecommerce.app.jwtExpirationMs}")
    private Long jwtExpirationMs;
    @Value("${fashion_ecommerce.app.jwtRefreshExpirationMs}")
    private Long refreshJWTExpirationS;
    @Value("${fashion_ecommerce.app.jwtCartExpiration}")
    private Long cartTokenExpirationMs;

    @Value("${fashion_ecommerce.app.jwtRefreshCookieName}")
    private String jwtRefreshCookie;
    @Value("${fashion_ecommerce.app.jwt.issuer}")
    private String issuer;
    @Value("${fashion_ecommerce.app.jwt.audience}")
    private String audience;


    private byte[] generateShareSecret() {
        return jwtSecret.getBytes();
    }

    private Date generateExpirationDate(Long expireTime) {
        return new Date(System.currentTimeMillis() + expireTime);
    }

    public String generateRefreshJWT(String refreshToken) {
        String token = null;
        try {
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim("tokenId", refreshToken);
            builder.expirationTime(generateExpirationDate(refreshJWTExpirationS));
            builder.issueTime(new Date());
            builder.issuer(issuer);
            builder.audience(audience);
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public String getJwtRefreshFromCookies(HttpServletRequest request) {
        return getCookieValueByName(request, jwtRefreshCookie);
    }

    public JWTClaimsSet getClaimsFromJwtToken(String token) {
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            JWTClaimsSet claims = getClaimsFromJwtToken(authToken);
            Date expiration = claims.getExpirationTime();
            return !expiration.before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String generateTokenFromUser(User user) {
        List<String> roles = user.roles().stream().map(role -> role.name().toString()).collect(Collectors.toList());
        String token = null;
        try {
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim("userId", user.id());
            builder.claim("email", user.email());
            builder.claim("roles", roles);
            builder.issuer(issuer);
            builder.audience(audience);
            builder.issueTime(new Date());
            builder.expirationTime(generateExpirationDate(jwtExpirationMs));
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public String generateTokenFromUserDetail(UserDetailsImpl userDetails) {
        List<String> roles = userDetails.getRoles();
        String token = null;
        try {
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim("userId", userDetails.getId());
            builder.claim("email", userDetails.getEmail());
            builder.claim("roles", roles);
            builder.issuer(issuer);
            builder.audience(audience);
            builder.issueTime(new Date());
            builder.expirationTime(generateExpirationDate(jwtExpirationMs));
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
    public String generateTokenFromCartId(String cartId) {
        String token = null;
        try {
            JWSSigner signer = new MACSigner(generateShareSecret());
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim("cartId", cartId);
            builder.issueTime(new Date());
            builder.expirationTime(generateExpirationDate(cartTokenExpirationMs));
            builder.issuer(issuer);
            builder.audience(audience);
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public String getCookieValueByName(HttpServletRequest request, String name) {
        Cookie cookie = WebUtils.getCookie(request, name);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }
}
