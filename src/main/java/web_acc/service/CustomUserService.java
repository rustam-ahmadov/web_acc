package web_acc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web_acc.dto.ImageDTO;
import web_acc.dto.UserDTO;
import web_acc.dto.request.RegistrationRequest;
import web_acc.entity.Authority;
import web_acc.entity.CustomUser;
import web_acc.entity.Image;
import web_acc.repository.AuthorityRepository;
import web_acc.repository.CustomUserRepository;
import web_acc.service.image.ImageService;

import java.io.IOException;
import java.util.UUID;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static web_acc.entity.CustomUser.CustomUserBuilder;

@Service
public class CustomUserService implements UserDetailsService {
    private final CustomUserRepository customUserRepository;
    private final AuthorityRepository authorityRepository;

    private final ImageService imageService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CustomUserService(CustomUserRepository customUserRepository, AuthorityRepository authorityRepository, ImageService imageService) {
        this.customUserRepository = customUserRepository;
        this.authorityRepository = authorityRepository;
        this.imageService = imageService;
    }


    public UserDTO update(UserDTO dto, String uuid) {

        CustomUser user = customUserRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setUsername(dto.getUsername());
        user.setSurname(dto.getSurname());
        user.setEmail(dto.getEmail());

        if (!dto.getPassword().isBlank()) {
            String pass = dto.getPassword();
            String encoded = passwordEncoder.encode(pass);
            user.setPassword(encoded);
        }
        customUserRepository.save(user);

        return dto;
    }

    private void createAuthorities() {
        List<Authority> authorities = authorityRepository.findAll();
        if (authorities.isEmpty()) {
            authorityRepository.save(new Authority().authority("ROLE_ADMIN"));
            authorityRepository.save(new Authority().authority("ROLE_USER"));
        }
    }

    private Set<Authority> getAuthoritySetForUser() {
        Authority authority = null;
        if (customUserRepository.findAll().isEmpty()) {
            authority = authorityRepository.findAuthorityByName("ROLE_ADMIN");
        } else
            authority = authorityRepository.findAuthorityByName("ROLE_USER");
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);
        return authoritySet;
    }

    public CustomUser save(RegistrationRequest request) throws Exception {

        Optional<CustomUser> optionalCustomUser = customUserRepository.findByEmail(request.getEmail());
        if (optionalCustomUser.isPresent())
            throw new Exception("There is already user with such email in db");

        createAuthorities();

        CustomUser user = new CustomUserBuilder()
                .email(request.getEmail())
                .username(request.getUsername())
                .password(request.getPassword())
                .surname(request.getSurname())
                .enabled(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .authorities(getAuthoritySetForUser())
                .passwordEncoder(passwordEncoder)
                .uuid(UUID.randomUUID().toString())
                .build();

        user = customUserRepository.save(user);
        return user;
    }

    public List<CustomUser> getAll() {
        return customUserRepository.findAll();
    }


    public UserDetails loadUserByUUID(String userUUID) throws UsernameNotFoundException {
        return customUserRepository
                .findByUuid(userUUID)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return customUserRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void saveUserImage(ImageDTO imageDTO, String userUUID) throws IOException {
        CustomUser user = customUserRepository
                .findByUuid(userUUID)
                .orElseThrow(() -> new UsernameNotFoundException("UserName not found"));

        Image savedImage = imageService.saveImage(imageDTO, user);
        user.setImage(savedImage);
        customUserRepository.save(user);
    }

    public Image getUserImage(String userUUID) throws IOException {
        CustomUser customUser = customUserRepository
                .findByUuid(userUUID)
                .orElseThrow(() -> new UsernameNotFoundException("UserName not found"));

        return imageService.getImage(customUser);
    }


}
