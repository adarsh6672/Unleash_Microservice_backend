package com.unleash.userservice.Service;




import com.unleash.userservice.DTO.UserDto;
import com.unleash.userservice.Model.AuthenticationResponse;
import com.unleash.userservice.Model.User;
import com.unleash.userservice.Reposetory.UserRepository;
import com.unleash.userservice.Service.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImp implements AuthenticationService {

    private  final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImp jwtService;

    private final EmailServiceImp emailServiceImp;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImp(UserRepository repository, PasswordEncoder passwordEncoder, JwtServiceImp jwtService, EmailServiceImp emailServiceImp, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.emailServiceImp = emailServiceImp;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(UserDto request){
        User user= new User();
        try{
            user.setPhone(request.getPhone());
            user.setFullname((request.getFullname()));
            user.setEmail(request.getEmail());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());
            user= repository.save(user);
        }catch (Exception e){
            System.out.println(e.getStackTrace());
        }


        String token = jwtService.generateToken(user);


        return  new AuthenticationResponse(token , String.valueOf(request.getRole()));
    }

    @Override
    public AuthenticationResponse authenticate(User request){
        System.out.println(request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        System.out.println(user.getFullname()+"--------------------------------------");
        String role = String.valueOf(user.getRole());

        String token = jwtService.generateToken(user);


        return new AuthenticationResponse(token , role);
    }

    @Override
    public boolean isEmailExisting(String email){
        return repository.existsByEmail(email);
    }
}