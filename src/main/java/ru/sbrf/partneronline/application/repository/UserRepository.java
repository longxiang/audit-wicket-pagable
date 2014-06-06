package ru.sbrf.partneronline.application.repository;

import org.springframework.data.repository.CrudRepository;
import ru.sbrf.partneronline.application.model.User;

import java.util.List;

/**
 * Created by vbauer on 11/05/14.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByUserName(String userName);
}
