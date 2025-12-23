package br.com.filipe.service;

import br.com.filipe.DTO.LoginRequestDTO;
import br.com.filipe.DTO.LoginResponseDTO;
import br.com.filipe.Utils.GenerateToken;
import br.com.filipe.exception.UserNotFoundException;
import br.com.filipe.model.UserModel;
import br.com.filipe.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;
import org.springframework.security.crypto.bcrypt.BCrypt;

@ApplicationScoped
public class LoginService {

    private final UserRepository userRepository;
    private GenerateToken generateToken = new GenerateToken();

    public LoginService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public LoginResponseDTO getLogin(LoginRequestDTO loginRequestDTO){
        try{
            UserModel user = userRepository.find("username", loginRequestDTO.getUsername()).firstResult();

            LoginResponseDTO response = new LoginResponseDTO();
            if(user.getUsername().equals(loginRequestDTO.getUsername())){

                if (!BCrypt.checkpw(loginRequestDTO.getPassword(), user.getPassword())) {
                    throw new NotAuthorizedException("Erro ao realizar o login, e-mail ou senha inválido!");
                }

                String token = generateToken.generateJwt(user.getRole());
                response.setUsername(loginRequestDTO.getUsername());
                response.setId(user.getId());
                response.setToken(token);
            } else {
                response.setUsername("");
                response.setToken("");
            }

            return response;
        } catch (Exception e){
            throw new UserNotFoundException();
        }
    }
}
