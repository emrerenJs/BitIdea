package com.emrerenjs.bitidea.Rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.emrerenjs.bitidea.Model.General.SearchKeyModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.emrerenjs.bitidea.DataAccess.Abstract.PostDAL;
import com.emrerenjs.bitidea.Entity.MongoDB.Post;
import com.emrerenjs.bitidea.Entity.MongoDB.PostComments;
import com.emrerenjs.bitidea.Model.General.GeneralResponse;
import com.emrerenjs.bitidea.Model.Post.CreateCommentModel;
import com.emrerenjs.bitidea.Model.Post.CreatePostModel;
import com.emrerenjs.bitidea.Model.Post.GetPostResponseModel;
import com.emrerenjs.bitidea.Model.Post.PostIdModel;
import com.emrerenjs.bitidea.Model.Security.UserDetailsModel;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostDAL postDAL;
    
    @Autowired
    private ProfileService profileManager;

    @PostMapping("/getByKey")
    public List<Post> getByKey(@RequestBody SearchKeyModel searchKeyModel){
        List<Post> posts = postDAL.searchByKey(searchKeyModel.getKey().toLowerCase());
        return posts;
    }

    @PostMapping("/uploadToTemp")
    public ResponseEntity<?> uploadToTemp(@RequestParam("body") MultipartFile multipartFile) throws IOException {
        String originalFile = multipartFile.getOriginalFilename();
        String photoName = "temp_post_" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date()) + originalFile.substring(originalFile.length() - 5);
        File file = new File("/home/emre/uploads/images/temp/" + photoName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return ResponseEntity.ok(new GeneralResponse(200,"Başarılı","/static/temp/" + photoName));
    }

    @PostMapping("/createPost")
    public ResponseEntity<?> createPost(@ModelAttribute CreatePostModel createPostModel, @RequestParam("contentPhoto") MultipartFile multipartFile) throws IOException {
        String originalFile = multipartFile.getOriginalFilename();
        String photoName = "temp_post_bigpic" + new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date()) + originalFile.substring(originalFile.length() - 5);
        File file = new File("/home/emre/uploads/images/temp/" + photoName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
        String username = userDetailsModel.getUsername();

        Post post = new Post();
        post.setContent(createPostModel.getContentBody());
        post.setHeader(createPostModel.getContentHeader());
        post.setUsername(username);
        post.setBigPicture("static/temp/" + photoName);
        post.setPostComments(new ArrayList<PostComments>());
        postDAL.save(post);
        return ResponseEntity.ok(new GeneralResponse(201,"Başarılı","İçerik başarıyla oluşturuldu.."));
    }

    @PostMapping("/getPost")
    public GetPostResponseModel getPost(@RequestBody PostIdModel postIdModel){
        Optional<Post> post = postDAL.findById(postIdModel.getPostId());
        GetPostResponseModel getPostResponseModel = new GetPostResponseModel();
        getPostResponseModel.setPost(post);
        
        if(!post.isEmpty()) {
        	String username = post.get().getUsername();
        	getPostResponseModel.setOwner(profileManager.getProfile(username));
        }       
        
        return getPostResponseModel;
    }
    
    @PostMapping("/newComment")
    public ResponseEntity<?> newComment(@RequestBody CreateCommentModel createCommentModel){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsModel userDetailsModel = (UserDetailsModel) authentication.getPrincipal();
        String username = userDetailsModel.getUsername();
        String photo = profileManager.getProfile(username).getPhoto();
    	Optional<Post> post = postDAL.findById(createCommentModel.getPostId());
    	List<PostComments> postComments = post.get().getPostComments();
    	
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = simpleDateFormat.format(date);
    	
    	postComments.add(new PostComments(
    			Integer.toString(postComments.size()),
    			username,
    			photo, 
    			createCommentModel.getComment(),
    			currentDate));
    	
    	postDAL.save(post.get());
    	return ResponseEntity.ok(new GeneralResponse(201,"Başarılı","Yorum başarıyla eklendi"));
    }
}
