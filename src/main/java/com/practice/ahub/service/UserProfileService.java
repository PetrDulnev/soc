package com.practice.ahub.service;

import com.practice.ahub.exception.CustomException;
import com.practice.ahub.model.FileModel;
import com.practice.ahub.model.UserProfile;
import com.practice.ahub.repository.FileModelRepository;
import com.practice.ahub.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final MinioService minioService;
    private final FileModelService fileModelService;

    public UserProfile setNewDecorationImage(Principal principal, MultipartFile file, String decoration) {
        UserProfile profile = getUserProfileWithPrincipal(principal);

        String fileName = minioService.save(file);

        log.info("add image to user: {}", profile.getUser().getName());

        FileModel fileModel = FileModel.builder()
                .fileName(fileName)
                .contentType(file.getContentType())
                .createdDate(LocalDateTime.now())
                .build();

        switch (decoration) {
            case "avatar":
                profile.setProfileImage(fileModelService.saveFileModel(fileModel));
                profile.getAllImages().add(fileModel);
                break;
            case "background":
                profile.setBannerImage(fileModelService.saveFileModel(fileModel));
                break;
            default:
                throw new CustomException(
                        String.format("Can not find param with name: %s", decoration)
                );
        }

        userProfileRepository.save(profile);

        return profile;
    }

    public UserProfile getUserProfileByLink(String link) {
        return userProfileRepository.findByLink(link).orElseThrow(
                () -> new CustomException(
                        String.format("Can not find user profile with link: %s", link)
                )
        );
    }

    public UserProfile updateUserProfile(Principal principal, UserProfile userProfile) {

        UserProfile profile = getUserProfileWithPrincipal(principal);

        if (userProfile.getGender() != null) {
            profile.setGender(userProfile.getGender());
        }

        if (userProfile.getLink() != null) {
            profile.setLink(userProfile.getLink());
        }

        return userProfileRepository.save(profile);
    }

    public void deleteProfileImage(Principal principal, String decoration) {
        UserProfile profile = getUserProfileWithPrincipal(principal);

        switch (decoration){
            case "avatar":
                profile.setProfileImage(null);
                fileModelService.safeDeleteImageWithName(profile.getProfileImage().getFileName());
                break;
            case "background":
                profile.setBannerImage(null);
                fileModelService.safeDeleteImageWithName(profile.getProfileImage().getFileName());
                break;
        }

        userProfileRepository.save(profile);
    }

    private UserProfile getUserProfileWithPrincipal(@NotNull Principal principal) {
        return userProfileRepository.findByUserEmail(principal.getName()).orElseThrow(
                () -> new CustomException(
                        String.format("Can not find user profile with login: %s", principal.getName())
                )
        );
    }
}