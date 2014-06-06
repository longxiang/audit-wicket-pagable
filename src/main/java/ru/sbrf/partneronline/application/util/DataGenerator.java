package ru.sbrf.partneronline.application.util;

import ru.sbrf.partneronline.application.model.Audit;
import ru.sbrf.partneronline.application.model.User;

import java.util.*;

/**
 * Created by vbauer on 11/05/14.
 */
public class DataGenerator {

    private static String[] userNames =
    {
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
            "dealermgr3",
            "flux"
    };

    private static String[] actions = {
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
            "delete news",
            "fall asleep"
    };


    public static List<User> generateUsers() {
        List<User> users = new ArrayList<User>();
        for (String userName : userNames) {
            users.add(new User(userName));
        }
        return users;
    }

    public static List<Audit> generateAudits(Iterable<User> availableUsers, int count){

        List<User> users = makeList(availableUsers);

        List<Audit> result = new ArrayList<Audit>();

        for (int i=0; i<count; i++){
            result.add(generate(selectRandomUser(users)));
        }

        return result;

    }

    private static Audit generate(User user) {
        Audit audit = new Audit();
        audit.setAction(randomString(actions));
        audit.setWhoIsUser(user);
        audit.setIpAddress(generateIpAddress());
        Calendar calendar = Calendar.getInstance();
        int month = rint(0, 11);
        int date = rint(1, 30);
        int febmax = 28;
        if(Calendar.FEBRUARY == month && date > febmax) date = febmax;
        calendar.set(rint(2000,2014), month, date);
        audit.setCreateDate(calendar.getTime());
        return audit;
    }

    private static User selectRandomUser(List<User> users) {
        return users.get(rint(0, users.size()-1));
    }

    private static String randomString(String[] choices) {
        return choices[rint(0,choices.length-1)];
    }

    private static String generateIpAddress(){
        return new StringBuilder("134.32.228.").append(rint(1,254)).toString();
    }

    private static int rint(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    private static <E> List<E> makeList(Iterable<E> iter) {
        List<E> list = new ArrayList<E>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }
}
