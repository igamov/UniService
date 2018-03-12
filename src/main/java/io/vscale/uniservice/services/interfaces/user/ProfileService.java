package io.vscale.uniservice.services.interfaces.user;

import io.vscale.uniservice.domain.Profile;

import java.util.List;

/**
 * 11.03.2018
 *
 * @author Andrey Romanov
 * @version 1.0
 */
public interface ProfileService {

    List<Profile> getAllProfiles();

}
