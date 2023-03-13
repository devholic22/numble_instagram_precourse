package numble.instagramprecourse.controller;

import jakarta.validation.Valid;
import numble.instagramprecourse.entity.User;
import numble.instagramprecourse.jwt.JwtFilter;
import numble.instagramprecourse.jwt.TokenProvider;
import numble.instagramprecourse.repository.UserRepository;
import numble.instagramprecourse.repository.dto.LoginDto;
import numble.instagramprecourse.repository.dto.TokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
                          UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userRepository = userRepository;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        User user = userRepository.findByUsername(loginDto.getUsername());
        if (!user.isActivated()) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }

    @DeleteMapping("/quit")
    public ResponseEntity<String> quit(Principal principal) {
        User user = userRepository.findByUsername(principal.getName());
        try {
            if (!user.isActivated()) {
                throw new UserNotActivatedException("탈퇴한 유저입니다.");
            }
            user.setActivated(false);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotActivatedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}