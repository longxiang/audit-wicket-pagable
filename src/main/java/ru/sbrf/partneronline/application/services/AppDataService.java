package ru.sbrf.partneronline.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.sbrf.partneronline.application.model.User;
import ru.sbrf.partneronline.application.repository.AuditRepository;
import ru.sbrf.partneronline.application.repository.UserRepository;
import ru.sbrf.partneronline.application.util.DataGenerator;

/**
 * Created by vbauer on 11/05/14.
 */
@Service
public class AppDataService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditRepository auditRepository;

//    @Autowired
//    public AppDataService(UserRepository userRepository){
//        this.userRepository = userRepository;
//        populateDB();
//    }


    private boolean populated = false;


    public void populateDB() {
        if(!populated) {
            System.err.println("POPULATING DB WITH MOCK DATA");
            System.err.println("----------------------------");
            Iterable<User> users = userRepository.save(DataGenerator.generateUsers());
            auditRepository.save(DataGenerator.generateAudits(users, 256));
            populated = true;
        }
    }



}
