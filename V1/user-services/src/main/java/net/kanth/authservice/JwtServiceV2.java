package net.kanth.authservice;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.util.List;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceV2{

	private static final String SECRET_KEY = "my-super-secret-key-for-jwt-signing-123456";
	
	
	
	public String generateToken(String usernane) {
		Map<String,Object>  objMap = new HashMap<>();
		
		return Jwts.builder()
				.claims()
				.add(objMap)
				.subject(usernane)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 100 * 60 * 10))
				.and()
				.signWith(getKey())
				.compact();
			
	}
	
	public String generateTokenV2(String username, List<String> roles, UUID userId) {
	    return Jwts.builder()
	            .claim("roles", roles)
	            .claim("userId", userId.toString())   // add this for rate Limit
	            .subject(username)
	            .issuedAt(new Date())
	            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))	            
	            .signWith(getKey())
	            .compact();
	}
	public SecretKey getKey() {
		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)); 
	}
	
	
	
	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public String extractUserId(String token) {
	    Claims claims = extractAllClaims(token);
	    return claims.get("userId", String.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> extractRoles(String token) {
	    Claims claims = extractAllClaims(token);
	    return claims.get("roles", List.class);
	}
}
