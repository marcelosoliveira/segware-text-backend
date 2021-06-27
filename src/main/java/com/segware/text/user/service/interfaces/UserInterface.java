package com.segware.text.user.service.interfaces;

import com.segware.text.exception.UserNameExistsException;

public interface UserInterface {

    String encodePassword(String password);

    void verifyUsername(String username) throws UserNameExistsException;

}
