package com.airbnb99.service;

import com.airbnb99.dto.LoginDto;
import com.airbnb99.dto.PropertyUserDto;
import com.airbnb99.entity.PropertyUser;
import com.airbnb99.repository.PropertyUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private PropertyUserRepository userRepository;


    private JWTService jwtService;
    public UserService(PropertyUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }


    public PropertyUser addUser(PropertyUserDto propertyUserDto){
        PropertyUser user = new PropertyUser();
        user.setFirstName(propertyUserDto.getFirstName());
        user.setLastName(propertyUserDto.getLastName());
        user.setUsername(propertyUserDto.getUsername());
        user.setEmail(propertyUserDto.getEmail());
        user.setPassword(BCrypt.hashpw(propertyUserDto.getPassword(),BCrypt.gensalt(10
        )));
       // user.setUserRole(propertyUserDto.getUserRole());
         user.setUserRole("ROLE_USER");
        PropertyUser savedUser = userRepository.save(user);
        return savedUser;
    }

//    public boolean verifyLogin(LoginDto loginDto) {
//
//       Optional <PropertyUser> opUser =
        //userRepository.findByUsername(loginDto.getUsername());
//    if(opUser.isPresent()){
//        PropertyUser propertyUser = opUser.get();
//       return BCrypt.checkpw(loginDto.getPassword(),propertyUser.getPassword());
//    }
//    return false;
//    }


    public String verifyLogin(LoginDto loginDto) {
        Optional<PropertyUser> opUser =
                userRepository.findByUsername(loginDto.getUsername());
        if (opUser.isPresent()) {
            PropertyUser propertyUser = opUser.get();
           if ((BCrypt.checkpw(loginDto.getPassword(), propertyUser.getPassword()))) {
               return jwtService.generateToken(propertyUser);
           }

        }
        return null;
    }

}