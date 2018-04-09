package io.vscale.uniservice.services.implementations.admin;

import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.domain.User;
import io.vscale.uniservice.forms.rest.ProfileForm;
import io.vscale.uniservice.repositories.data.ProfileRepository;
import io.vscale.uniservice.repositories.data.UserRepository;
import io.vscale.uniservice.services.interfaces.admin.ProfileAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 17.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
public class ProfileAdminServiceImpl implements ProfileAdminService{

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileAdminServiceImpl(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    @Override
    public void addProfileToUser(ProfileForm profileForm) {

        User user = this.userRepository.findOne(profileForm.getUserId());

        Profile profile = Profile.builder()
                                 .surname(profileForm.getSurname())
                                 .name(profileForm.getName())
                                 .patronymic(profileForm.getPatronymic())
                                 .email(profileForm.getEmail())
                                 .description(profileForm.getDescription())
                                 .user(user)
                                 .build();

        this.profileRepository.save(profile);

    }
}
