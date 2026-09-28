package bg.sofia.uni.fmi.tdkirov.tradehub.service;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserCreateDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.model.User;

import java.util.List;

;

public interface UserService {
    Long createdUser(UserCreateDto userDto);

    List<User> getUsers();
}
