package ee.metxdev.tudengibaar.security;

import ee.metxdev.tudengibaar.DTO.UserDTO;
import ee.metxdev.tudengibaar.entity.User;
import ee.metxdev.tudengibaar.repository.UserRepository;
import ee.metxdev.tudengibaar.security.dto.AuthRequest;
import ee.metxdev.tudengibaar.security.dto.AuthResponse;
import ee.metxdev.tudengibaar.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            String token = jwtUtil.generateToken(userDetails);

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + request.getEmail()));

            System.out.println("Logged in user: id=" + user.getUserId()
                    + ", email=" + user.getEmail()
                    + ", name=" + user.getFirstName() + " " + user.getLastName()
                    + ", role=" + user.getRole().getName());

            UserDTO userDTO = new UserDTO(
                    user.getUserId(),
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRole().getName()
            );

            return ResponseEntity.ok(new AuthResponse(token, userDTO));


        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Invalid credentials", null));
        }
    }
}