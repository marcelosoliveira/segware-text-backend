package com.segware.text.user.service.impl;

import com.segware.text.dto.request.UserDTO;
import com.segware.text.dto.response.MessageResponseDTO;
import com.segware.text.exception.UserNameExistsException;
import com.segware.text.mapper.PostMapper;
import com.segware.text.user.model.User;
import com.segware.text.user.repository.UserRepository;
import com.segware.text.user.service.interfaces.UserInterface;
import lombok.AllArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService, UserInterface {

    private final PostMapper postMapper = PostMapper.INSTANCE;

    private UserRepository userRepository;

    public MessageResponseDTO createUserService(UserDTO userDTO) {
        verifyUsername(userDTO.getUsername());
        String passwordEncode = encodePassword(userDTO.getPassword());
        userDTO.setPassword(passwordEncode);

        User user = postMapper.toUserModel(userDTO);
        this.userRepository.save(user);

        return createMessageResponse("User created successfully");
    }

    private MessageResponseDTO createMessageResponse(String message) {
        return MessageResponseDTO
                .builder()
                .message(message)
                .build();
    }

    @Override
    public String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = Optional.ofNullable(this.userRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("No user or invalid password"));
        List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList(
                "ROLE_USER");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorityListUser);
    }

    @Override
    public void verifyUsername(String username) throws UserNameExistsException {
        Optional<User> user = Optional.ofNullable(this.userRepository.findByUsername(username));
        if (user.isPresent()) throw new UserNameExistsException("User already exists!");
    }

}
