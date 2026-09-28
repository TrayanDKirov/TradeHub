package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserLoginDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.InvalidPasswordException;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.UserNotFoundException;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.UsernameTakenException;
import bg.sofia.uni.fmi.tdkirov.tradehub.mapper.UserMapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.User;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.UserRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.security.JwtUtil;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationServiceImpl(UserRepository userRepo, PasswordEncoder passwordEncoder,
                                     JwtUtil jwtUtil, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Long register(UserRegisterDto registerDto) {
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new UsernameTakenException(registerDto.getUsername(), 0);
        }

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User user = userMapper.toEntity(registerDto);
        return userRepository.save(user).getId();
    }

    @Override
    public String login(UserLoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
            .orElseThrow(() -> new UserNotFoundException(loginDto.getUsername(), 0));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}