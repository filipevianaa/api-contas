package br.com.filipe.service;

import br.com.filipe.exception.UserNotFoundException;
import br.com.filipe.model.UserModel;
import br.com.filipe.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> listUsers(Integer page, Integer pageSize){
        return userRepository.findAll().page(page, pageSize).list();
    }

    public UserModel createUser (UserModel userModel){
        userModel.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt()));
        userRepository.persist(userModel);
        return userModel;
    }

    public UserModel findById(UUID id) {
        return (UserModel) userRepository.findByIdOptional(id).orElseThrow(UserNotFoundException::new);
    }

    public UserModel updateUser(UUID id, UserModel userModel) {
        var user = findById(id);

        user.setName(userModel.getName());
        user.setEmail(userModel.getEmail());
        user.setUsername(userModel.getUsername());
        user.setPassword(BCrypt.hashpw(userModel.getPassword(), BCrypt.gensalt()));
        user.setBirthdate(userModel.getBirthdate());

        userRepository.persist(user);
        return user;
    }

    public void deleteUser(UUID id) {
        var user = findById(id);

        userRepository.deleteById(user.getId());
    }
}
