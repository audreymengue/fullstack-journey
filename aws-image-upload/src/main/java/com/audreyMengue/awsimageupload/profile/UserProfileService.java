package com.audreyMengue.awsimageupload.profile;


import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.springframework.http.MediaType.*;

@Repository
public class UserProfileService {

    private  final  UserProfileDataAccessService userProfileDataAccessService;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService) {
        this.userProfileDataAccessService = userProfileDataAccessService;
    }
    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file [ " + file.getSize() + "]");
        }
        if (!Arrays.asList(IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an  image.");
        }

        UserProfile user = userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId ))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
    }
//        Map<String, String> metadata = new HashMap<>();
//        metadata.put("Content-Type", file.getContentType());
//        metadata.put("Content-Length", String.valueOf(file.getSize()));
}
