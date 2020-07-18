package com.example.Notes.security_manager;

import com.example.Notes.security_manager.models.AuthenticationRequest;
import com.example.Notes.security_manager.models.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("authenticate")
public class AuthenticationController {

    private AuthenticationManager authenticationManager;
    private KnownUserDetailsService knownUserDetailsService;
    private JwtUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, KnownUserDetailsService knownUserDetailsService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.knownUserDetailsService = knownUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
    throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(),
                            authenticationRequest.getPassword()
                    )
            );

        }catch (BadCredentialsException e){
            throw new Exception("Incorrect UserName of password");
        }

        final UserDetails userDetails = knownUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
