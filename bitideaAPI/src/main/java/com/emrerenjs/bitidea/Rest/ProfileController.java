package com.emrerenjs.bitidea.Rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.emrerenjs.bitidea.Business.Abstract.ProfileService;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Model.Profile.ProfileResponseModel;
import com.emrerenjs.bitidea.Model.Profile.UsernameModel;
import com.emrerenjs.bitidea.Model.Security.UserDetailsModel;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    ProfileService profileService;

    @PostMapping("/getProfile")
    public ProfileResponseModel getProfile(@RequestBody UsernameModel usernameModel){
    	if(usernameModel.isFieldsNull()) {
    		throw new IllegalArgumentException("Kullanıcı adı boş geçilemez..");
    	}
        return profileService.getProfileWithPosts(usernameModel.getUsername());
    }

    //USELESS
    @PostMapping("/editProfileAccess")
    public Profile editProfileAccess(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
        String username = userDetailsModel.getUsername();
        return profileService.getProfile(username);
    }

    @PostMapping("/updateProfile")
    public Profile register(@ModelAttribute Profile profile,@RequestParam(value = "file", required = false) MultipartFile multipartFile) throws Exception{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
        String username = userDetailsModel.getUsername();
        profile.setUsername(username);
        profileService.updateProfile(profile,multipartFile);
        return profile;
    }

}
