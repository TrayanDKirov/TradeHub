package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.BuyerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.BuyerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.SellerRegisterDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.SellerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserLoginDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.BuyerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.Role;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.EmailTakenException;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.InvalidPasswordException;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.UserNotFoundException;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.UsernameTakenException;
import bg.sofia.uni.fmi.tdkirov.tradehub.mapper.UserMapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.BuyerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.SellerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.UserRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.security.JwtUtil;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.AuthenticationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BuyerProfileRepository buyerRepository;
    private final SellerProfileRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationServiceImpl(UserRepository userRepo,
                                     BuyerProfileRepository buyerRepository,
                                     SellerProfileRepository sellerRepository,
                                     PasswordEncoder passwordEncoder,
                                     JwtUtil jwtUtil, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userRepository = userRepo;
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    private void assertUsernameNotTaken(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameTakenException(username, 0);
        }
    }

    private void assertEmailNotTaken(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailTakenException(email, 0);
        }
    }

    @Override
    @Transactional
    public String registerBuyer(BuyerRegisterDto registerDto) {
        assertUsernameNotTaken(registerDto.getUsername());
        assertEmailNotTaken(registerDto.getEmail());

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User user = userMapper.toUser(registerDto, Role.BUYER);
        user = userRepository.save(user);
        BuyerProfile buyer = userMapper.toBuyer(user, registerDto);

        buyerRepository.save(buyer);

        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    @Transactional
    public String registerSeller(SellerRegisterDto registerDto) {
        assertUsernameNotTaken(registerDto.getUsername());
        assertEmailNotTaken(registerDto.getEmail());

        registerDto.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        User user = userMapper.toUser(registerDto, Role.SELLER);
        user = userRepository.save(user);
        SellerProfile seller = userMapper.toSeller(user, registerDto);

        sellerRepository.save(seller);

        return jwtUtil.generateToken(user.getUsername());
    }

    @Override
    public String login(UserLoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername())
            .orElseThrow(() -> new UserNotFoundException(loginDto.getUsername(), 0));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
            throw new InvalidPasswordException();
        }

        return jwtUtil.generateToken(user.getUsername());
    }
}