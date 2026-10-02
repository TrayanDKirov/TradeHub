package bg.sofia.uni.fmi.tdkirov.tradehub.service.implementations;

import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.buyer.BuyerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.seller.SellerResponseDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.dto.user.UserPreviewDto;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.BuyerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.SellerProfile;
import bg.sofia.uni.fmi.tdkirov.tradehub.entity.user.User;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.buyer.BuyerNotFoundException;
import bg.sofia.uni.fmi.tdkirov.tradehub.exception.user.seller.SellerNotFound;
import bg.sofia.uni.fmi.tdkirov.tradehub.mapper.UserMapper;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.BuyerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.SellerProfileRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.repository.UserRepository;
import bg.sofia.uni.fmi.tdkirov.tradehub.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private BuyerProfileRepository buyerRepository;
    private SellerProfileRepository sellerRepository;
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

    @Override
    public BuyerResponseDto getBuyer(String currUsername) {
        if (!buyerRepository.existsByUserUsername(currUsername)) {
            throw new BuyerNotFoundException(String.format(
                "Buyer account for %s not found. Might be another role.",
                currUsername));
        }

        BuyerProfile buyer = buyerRepository.findByUserUsername(currUsername).get();

        return mapper.toBuyerResponseDto(buyer);
    }

    @Override
    public SellerResponseDto getSeller(String currUsername) {
        if (!sellerRepository.existsByUserUsername(currUsername)) {
            throw new SellerNotFound(String.format(
                "Seller account for %s not found. Might be another role.",
                currUsername));
        }

        SellerProfile seller = sellerRepository.findByUserUsername(currUsername).get();

        return mapper.toSellerResponseDto(seller);
    }
}
