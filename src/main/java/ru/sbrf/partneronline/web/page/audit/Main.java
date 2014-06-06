package ru.sbrf.partneronline.web.page.audit;

import java.util.List;

/**
 * Created by sbt-bauer-vv on 05.05.2014.
 */
public class Main {
    public static void main(String[] args) {

//        AuditRepository auditRepository = AuditRepository.getInstance();
//
//        List<Audit> audits = auditRepository.findAll();
//
//        Audit audit = auditRepository.findOne(14L);
//
//        System.out.println(audit);

        AuditService auditService = AuditService.getInstance();

        System.out.println(auditService.getCount());

    }
}
