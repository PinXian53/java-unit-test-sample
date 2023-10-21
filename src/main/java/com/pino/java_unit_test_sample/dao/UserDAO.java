package com.pino.java_unit_test_sample.dao;

import com.pino.java_unit_test_sample.model.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UserDAO {

    private static final Map<String, UserEntity> itemMap = new ConcurrentHashMap<>();

    static {
        itemMap.put("A260013339", new UserEntity("A260013339", "Andy Wang"));
        itemMap.put("D116312695", new UserEntity("D116312695", "Wendy Chang"));
        itemMap.put("L217015737", new UserEntity("L217015737", "Gino Chen"));
    }

    public List<UserEntity> findAll() {
        return new ArrayList<>(itemMap.values());
    }

    public UserEntity findByIdentityNumber(String identityNumber) {
        return itemMap.get(identityNumber);
    }

    public void save(UserEntity userEntity) {
        itemMap.put(userEntity.getIdentityNumber(), userEntity);
    }

    public void deleteByIdentityNumber(String identityNumber) {
        itemMap.remove(identityNumber);
    }
}
