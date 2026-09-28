package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.model.User;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.UserRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;

    @Override
    public Long createdUser(UserCreateDto userDto) {
        User user = new User(userDto.getUsername(), userDto.getPassword());

        return repository.save(user).getId();
    }

    @Override
    public List<User> getUsers() {

        return List.copyOf(repository.findAll());
    }
}
