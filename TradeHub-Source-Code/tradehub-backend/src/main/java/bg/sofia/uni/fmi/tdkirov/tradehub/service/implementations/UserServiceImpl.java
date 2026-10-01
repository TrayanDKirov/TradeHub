package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;
import bg.sofia.uni.fmi.tdkirov.tradehub.mapper.UserMapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.UserRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private UserMapper mapper;

    @Override
    public List<User> getUsers() {
        return List.copyOf(repository.findAll());
    }

    @Override
    public UserPreviewDto getUserPreview(String currUsername) {
        User user = repository.findByUsername(currUsername).get();

        return mapper.toPreviewDto(user);
    }
}
