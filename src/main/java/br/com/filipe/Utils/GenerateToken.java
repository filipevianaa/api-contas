package br.com.filipe.Utils;

import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Singleton;

@Singleton
public class GenerateToken {

    public String generateJwt (RoleEnum role){
        return Jwt.issuer("api-contas")
                .subject("api-contas")
                .groups(role.name()).expiresAt(System.currentTimeMillis() + 3600).sign();
    }
}
