package br.com.filipe.controller;

import br.com.filipe.DTO.LoginRequestDTO;
import br.com.filipe.service.LoginService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    public Response login(LoginRequestDTO loginRequestDTO){
        return Response.ok(loginService.getLogin(loginRequestDTO)).build();
    }
}
