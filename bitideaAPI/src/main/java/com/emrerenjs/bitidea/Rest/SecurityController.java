package com.emrerenjs.bitidea.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emrerenjs.bitidea.Business.Abstract.ProfileService;
import com.emrerenjs.bitidea.Business.Abstract.UserSecurityService;
import com.emrerenjs.bitidea.Business.Concrete.JwtUtilManager;
import com.emrerenjs.bitidea.Business.Concrete.UserDetailsManager;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Entity.MySQL.User;
import com.emrerenjs.bitidea.Model.General.GeneralResponse;
import com.emrerenjs.bitidea.Model.Security.AuthenticationRequest;
import com.emrerenjs.bitidea.Model.Security.ProfileCredentialsResponseModel;
import com.emrerenjs.bitidea.Model.Security.RegisterRequest;

@RestController
@RequestMapping("/security")
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsManager userDetailsManager;
    
    @Autowired
    private ProfileService profileManager;

    @Autowired
    private JwtUtilManager jwtUtil;

    @Autowired
    private UserSecurityService userSecurityService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        if(authenticationRequest.isFieldsNull()){
            throw new IllegalArgumentException("Kullanıcı adı & Parola boş kalamaz!");
        }
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword())
            );
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Kullanıcı adı veya parola hatalı!", e);
        }

        final UserDetails userDetails = userDetailsManager.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        Profile profile = profileManager.getProfile(userDetails.getUsername());
        GeneralResponse generalResponse = new ProfileCredentialsResponseModel(200,"Giriş Başarılı",jwt,profile);
        return ResponseEntity.ok(generalResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) throws Exception{
        if(registerRequest.isFieldsNull()){
            throw new IllegalArgumentException("Kayıt bilgileri boş gönderilemez!");
        }
        User user = new User(registerRequest.getUsername(),registerRequest.getPassword(),registerRequest.getEmail());
        userSecurityService.createAccount(user);
        return ResponseEntity.ok(new GeneralResponse(201,"Başarılı","Kayıt başarıyla tamamlandı"));
    }
    
    @PostMapping("/isAuthenticated")
    public ResponseEntity<?> isAuthenticated(){
    	return ResponseEntity.ok(new GeneralResponse(200,"Evet","Evet sistemde hala aktifsiniz.."));
    }

}
