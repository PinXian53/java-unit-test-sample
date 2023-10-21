package com.pino.java_unit_test_sample.service;

import com.pino.java_unit_test_sample.dao.UserDAO;
import com.pino.java_unit_test_sample.exception.BadRequestException;
import com.pino.java_unit_test_sample.model.entity.UserEntity;
import com.pino.java_unit_test_sample.model.model.CreateUserDTO;
import com.pino.java_unit_test_sample.model.model.UserDTO;
import com.pino.java_unit_test_sample.util.IdentityNumberUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final ModelMapper modelMapper = new ModelMapper();
    private final UserDAO userDAO;

    public List<UserDTO> findAll() {
        return userDAO.findAll().stream()
            .map(entity -> modelMapper.map(entity, UserDTO.class))
            .toList();
    }

    public void createUser(CreateUserDTO createUserDTO) {
        var identityNumber = createUserDTO.getIdentityNumber();
        if (isIdentityNumberExist(identityNumber)) {
            throw new BadRequestException("User already exists");
        }
        if (!IdentityNumberUtils.isValid(identityNumber)) {
            throw new BadRequestException("Identity number is invalid");
        }
        var userEntity = modelMapper.map(createUserDTO, UserEntity.class);
        userDAO.save(userEntity);
    }

    public void deleteUser(String identityNumber) {
        if (!isIdentityNumberExist(identityNumber)) {
            throw new BadRequestException("User does not exist");
        }
        userDAO.deleteByIdentityNumber(identityNumber);
    }

    private boolean isIdentityNumberExist(String identityNumber) {
        return isNotNull(userDAO.findByIdentityNumber(identityNumber));
    }

    private boolean isNotNull(Object object) {
        return object != null;
    }
}
