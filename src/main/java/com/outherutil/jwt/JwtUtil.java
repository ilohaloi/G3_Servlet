package com.outherutil.jwt;

import java.security.PrivateKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import kotlin.Pair;

public interface JwtUtil {
	public String createJwt(Object emp,Pair<String, String> claim,long expirMillis);
	public default Jws<Claims> decode(String data, PrivateKey key){
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(data);
	}
}
