package com.emrerenjs.bitidea.Business.Abstract;

import org.springframework.web.multipart.MultipartFile;

import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Model.Profile.ProfileResponseModel;

public interface ProfileService {
    ProfileResponseModel getProfileWithPosts(String username);
    Profile getProfile(String username);
    void updateProfile(Profile profile,MultipartFile multipartFile) throws Exception;
}
