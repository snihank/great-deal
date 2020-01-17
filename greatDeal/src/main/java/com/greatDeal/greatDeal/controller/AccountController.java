package com.greatDeal.greatDeal.controller;

import com.greatDeal.greatDeal.model.AppUser;
import com.greatDeal.greatDeal.service.AccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class AccountController {

    private Long userImageId;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AccountService accountService;

    @GetMapping("/list")
    public ResponseEntity<?> getUsersList() {
        List<AppUser> appUsers = accountService.userList();
        if (appUsers.isEmpty()) {
            return new ResponseEntity<>("No Users Found.", HttpStatus.OK);
        }
        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserInfo(@PathVariable String username) {
        AppUser appUser = accountService.findByUsername(username);
        if (appUser == null) {
            return new ResponseEntity<>("No Users Found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appUser, HttpStatus.OK);
    }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<?> getUsersListByUsername(@PathVariable String username) {
        List<AppUser> appUsers = accountService.getUsersListByUsername(username);
        if (appUsers.isEmpty()) {
            return new ResponseEntity<>("No Users Found.", HttpStatus.OK);
        }
        return new ResponseEntity<>(appUsers, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody HashMap<String, String> request) {
        String username = request.get("username");
        if (accountService.findByUsername(username) != null) {
            return new ResponseEntity<>("usernameExist", HttpStatus.CONFLICT);
        }
        String email = request.get("email");
        if (accountService.findByEmail(email) != null) {
            return new ResponseEntity<>("emailExist", HttpStatus.CONFLICT);
        }
        String name = request.get("name");
        try {
            AppUser appUser = accountService.saveUser(name, username, email);
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occured", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody HashMap<String, String> request) {
        String id = request.get("id");
        AppUser appUser = accountService.findUserById(Long.parseLong(id));
        if (appUser == null) {
            return new ResponseEntity<>("userNotFound", HttpStatus.NOT_FOUND);
        }
        try {
            accountService.updateUser(appUser, request);
            userImageId = appUser.getId();
            return new ResponseEntity<>(appUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occured", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/photo/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("image") MultipartFile multipartFile) {
        try {
            accountService.saveUserImage(multipartFile, userImageId);
            return new ResponseEntity<>("User Picture Saved!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User Picture Not Saved", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody HashMap<String, String> request) {
        String username = request.get("username");
        AppUser appUser = accountService.findByUsername(username);
        if (appUser == null) {
            return new ResponseEntity<>("User not found!", HttpStatus.BAD_REQUEST);
        }
        String currentPassword = request.get("currentpassword");
        String newPassword = request.get("newpassword");
        String confirmpassword = request.get("confirmpassword");
        if (!newPassword.equals(confirmpassword)) {
            return new ResponseEntity<>("PasswordNotMatched", HttpStatus.BAD_REQUEST);
        }
        String userPassword = appUser.getPassword();
        try {
            if (newPassword != null && !newPassword.isEmpty() && !StringUtils.isEmpty(newPassword)) {
                if (bCryptPasswordEncoder.matches(currentPassword, userPassword)) {
                    accountService.updateUserPassword(appUser, newPassword);
                }
            } else {
                return new ResponseEntity<>("IncorrectCurrentPassword", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>("Password Changed Successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error Occured: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(@PathVariable("email") String email) {
        AppUser appUser = accountService.findByEmail(email);
        if (appUser == null) {
            return new ResponseEntity<String>("emailNotFound", HttpStatus.BAD_REQUEST);
        }
        accountService.resetPassword(appUser);
        return new ResponseEntity<String>("EmailSent!", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody HashMap<String, String> mapper) {
        String username = mapper.get("username");
        AppUser appUser = accountService.findByUsername(username);
        accountService.deleteUser(appUser);
        return new ResponseEntity<String>("User Deleted Successfully!", HttpStatus.OK);
    }


}
