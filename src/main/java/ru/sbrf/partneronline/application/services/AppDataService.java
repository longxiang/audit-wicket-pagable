package ru.sbrf.partneronline.application.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.sbrf.partneronline.application.dto.AuditView;
import ru.sbrf.partneronline.application.model.Audit;
import ru.sbrf.partneronline.application.model.User;
import ru.sbrf.partneronline.application.repository.AuditRepository;
import ru.sbrf.partneronline.application.repository.UserRepository;
import ru.sbrf.partneronline.application.util.DataGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbauer on 11/05/14.
 */
@Service
public class AppDataService {

    private static Logger logger = LoggerFactory.getLogger(AppDataService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditRepository auditRepository;

//    @Autowired
//    public AppDataService(UserRepository userRepository){
//        this.userRepository = userRepository;
//        populateDB();
//    }


    private volatile boolean populated = false;


    public void populateDB() {
        if(!populated) {
            logger.info("POPULATING DB WITH MOCK DATA");
            logger.info("----------------------------");
            Iterable<User> users = userRepository.save(DataGenerator.generateUsers());
            auditRepository.save(DataGenerator.generateAudits(users, 256));
            populated = true;
        }
    }



    public List<AuditView> fetchPagebaleAudits(int first, int count, String sortProperty, boolean sortAsc) {

//        Marker pageMarker = MarkerFactory.getMarker("pageable");
        logger.debug(String.format("first=%d,count=%d,sortProperty=%s", first, count, sortProperty));


        Sort.Direction sortDir = sortAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = new Sort(sortDir, sortProperty == null ? "id" : sortProperty);


        Pageable pageable = new PageRequest(first/count, count, sort);

        Page<Audit> audits = auditRepository.findAll(pageable);

        List<AuditView> result = new ArrayList<AuditView>();
        for (Audit audit : audits) {
            result.add(auditToAuditView(audit));
        }

        return result;
    }


    public long countAllAudits(){
        return auditRepository.count();
    }


    public List<AuditView> fetchAllAudits() {
        System.err.println("fetchAllAudits");
        List<AuditView> result = new ArrayList<AuditView>();
        for (Audit audit : auditRepository.findAll()) {
           result.add(auditToAuditView(audit));
        }
        return result;
    }

    private AuditView auditToAuditView(Audit audit) {
        AuditView auditView = new AuditView();
        auditView.setId(audit.getId());
        User user = audit.getWhoIsUser();
        auditView.setUserName(user != null ? user.getUserName() : "");
        auditView.setAction(audit.getAction());
        auditView.setIpAddress(audit.getIpAddress());
        auditView.setCreateDate(audit.getCreateDate());
        return auditView;
    }


}
