package com.gmontinny.keycloak.component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException {

        if (authentication != null) {
            System.out.println("Usuário fez logout: " + authentication.getName());
            // Aqui você pode adicionar lógica personalizada, como salvar logs, enviar eventos, etc.
        }
        // Limpa o contexto do Spring Security
        SecurityContextHolder.clearContext();

        // Invalida a sessão HTTP
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // URL do endpoint de logout do Keycloak
        String keycloakLogoutUrl = "http://localhost:8088/realms/spring-boot-keycloak-realm/protocol/openid-connect/logout";


        // Redireciona o usuário para a página de logout do Keycloak
        response.sendRedirect(keycloakLogoutUrl + "?redirect_uri=" + request.getContextPath());

    }

}
