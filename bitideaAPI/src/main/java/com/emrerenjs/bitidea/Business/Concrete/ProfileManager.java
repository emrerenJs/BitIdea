package com.emrerenjs.bitidea.Business.Concrete;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.emrerenjs.bitidea.Business.Abstract.ProfileService;
import com.emrerenjs.bitidea.DataAccess.Abstract.PostDAL;
import com.emrerenjs.bitidea.DataAccess.Abstract.ProfileDAL;
import com.emrerenjs.bitidea.Entity.MongoDB.Post;
import com.emrerenjs.bitidea.Entity.MySQL.Profile;
import com.emrerenjs.bitidea.Model.Profile.ProfileResponseModel;

@Service
public class ProfileManager implements ProfileService {

    @Autowired
    ProfileDAL profileDAL;

    @Autowired
    PostDAL postDAL;

    @Override
    public ProfileResponseModel getProfileWithPosts(String username) {
        Profile profile = profileDAL.getProfile(username);
        ProfileResponseModel profileResponseModel = new ProfileResponseModel();
        profileResponseModel.setProfile(profile);
        List<Post> userPosts = postDAL.findByUsername(username);
        profileResponseModel.setProfilePosts(userPosts);
        return profileResponseModel;
    }

    @Override
    public void updateProfile(Profile profile,MultipartFile multipartFile) throws Exception {
        if(multipartFile != null){
            String originalFile = multipartFile.getOriginalFilename();
            String photoName = "profile_" + profile.getUsername() + "_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date()) + originalFile.substring(originalFile.length() - 5);
            File file = new File("/home/emre/uploads/images/profilephotos/" + photoName);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
            fos.close();
            profile.setPhoto("/static/profilephotos/" + photoName);
        }
        profileDAL.updateProfile(profile);
    }

	@Override
	public Profile getProfile(String username) {
		Profile profile = profileDAL.getProfile(username);
		return profile;
	}

}
