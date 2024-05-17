package com.practice.ahub.conroller;

import com.practice.ahub.model.UserProfile;
import com.practice.ahub.service.FileModelService;
import com.practice.ahub.service.UserProfileService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {

    private final UserProfileService profileService;
    private final FileModelService fileModelService;

    @PatchMapping("/decoration")
    @Secured("USER")
    public UserProfile setProfileImage(Principal principal,
                                       @RequestPart("file") MultipartFile file,
                                       @RequestParam("decoration") String decoration) {

        fileModelService.isValidFile(file);

        return profileService.setNewDecorationImage(principal, file, decoration);
    }

    @DeleteMapping("/decoration")
    @Secured("User")
    public void deleteProfileImage(Principal principal, @RequestParam("decoration") String decoration) {
        profileService.deleteProfileImage(principal, decoration);
    }

    @GetMapping("/{link}")
    @PermitAll
    public UserProfile getProfile(@PathVariable String link) {
        return profileService.getUserProfileByLink(link);
    }

    @PutMapping
    @Secured("USER")
    public UserProfile updateProfile(Principal principal, @RequestBody UserProfile userProfile) {
        return profileService.updateUserProfile(principal, userProfile);
    }


    @DeleteMapping("/{fileName}")
    public void deleteFileByName(@PathVariable String fileName) {
        fileModelService.safeDeleteImageWithName(fileName);
    }


}
