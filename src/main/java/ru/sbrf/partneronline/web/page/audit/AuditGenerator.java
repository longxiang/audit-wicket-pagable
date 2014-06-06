package ru.sbrf.partneronline.web.page.audit;

import ru.sbrf.partneronline.application.dto.AuditView;

import java.util.Calendar;
import java.util.Collection;

/**
 * Created by sbt-bauer-vv on 05.05.2014.
 */
public class AuditGenerator {


    private static class LazySingletonHolder{
        static AuditGenerator instance = new AuditGenerator();
    }

    private static long nextId = 1;


    private String[] actions = {
            "save brand",
            "save user",
            "delete creditTerm",
            "save news",
            "delete faq",
            "delete RelationshipsSubdivisionsService",
            "delete infoblock",
            "update user",
            "save RelationshipsSubdivisionsService",
            "save createRate",
            "save faq",
            "delete createRate",
            "save automakerDivision",
            "save webConference",
            "save infoblock",
            "delete webConference",
            "save creditTerm",
            "delete news"
    };

    private String[] userNames = {
            "bankGOSB7",
            "bankDO8",
            "dealer1",
            "dealer4",
            "dealermgr6",
            "kmbii",
            "yava",
            "bank2",
            "bank3",
            "bankDO1",
            "bankDO5",
            "bankDO6",
            "dealer6",
            "dealermgr8",
            "dealermgr9",
            "kmbiz",
            "sosb",
            "bank9",
            "bankGOSB1",
            "dealer12",
            "dealermgr3"
    };

    public static AuditGenerator getInstance(){
        return LazySingletonHolder.instance;
    }

    private AuditGenerator(){
    }

    public synchronized long generateId(){
        return nextId++;
    }

    public void generate(Collection<AuditView> collection, int count){
        for (int i=0; i<count; i++) collection.add(generate());
    }

    public AuditView generate(){
        AuditView audit = new AuditView();
        audit.setId(generateId());
        audit.setAction(randomString(actions));
        audit.setUserName(randomString(userNames));
        audit.setIpAddress(generateIpAddress());
        Calendar calendar = Calendar.getInstance();
        int month = rint(0, 11);
        int date = rint(1, 31);
        int febmax = 28;
        if(Calendar.FEBRUARY == month && date > febmax) date = febmax;
        calendar.set(rint(2000,2014), month, date);
        audit.setCreateDate(calendar.getTime());
        return audit;
    }

    private String randomString(String[] choices) {
        return choices[rint(0,choices.length)];
    }

    private int rint(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private String generateIpAddress(){
        return new StringBuilder("134.32.228.").append(rint(1,254)).toString();
    }
}
