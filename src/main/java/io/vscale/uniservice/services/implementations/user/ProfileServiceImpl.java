package io.vscale.uniservice.services.implementations.user;

import io.vscale.uniservice.domain.Profile;
import io.vscale.uniservice.repositories.data.ProfileRepository;
import io.vscale.uniservice.services.interfaces.user.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
@Service
public class ProfileServiceImpl implements ProfileService{

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public List<Profile> getAllProfiles() {
        return this.profileRepository.findAll();
    }
}
