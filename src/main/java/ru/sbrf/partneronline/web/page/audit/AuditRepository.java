package ru.sbrf.partneronline.web.page.audit;

import ru.sbrf.partneronline.application.dto.AuditView;

import java.util.*;

/**
 * Created by sbt-bauer-vv on 07.05.2014.
 */
public class AuditRepository {

    private static AuditRepository instance;

    private static final int SIZE = 256;

    private final List<AuditView> audits;
    private final List<AuditView> unameIndx;
    private final List<AuditView> unameDescIndx;


    private AuditRepository(int count){
        //Populates audits, sorted by id
        audits = new ArrayList<AuditView>(SIZE);
        AuditGenerator.getInstance().generate(audits, count);

        unameIndx = new ArrayList<AuditView>(audits);
        unameDescIndx = new ArrayList<AuditView>(audits);

        updateIndices();
    }

    /**
     * Gets dummy AuditRepository with 256 random records of Audit
     * @return
     */
    public static AuditRepository getInstance(){
        if (instance == null) {
            instance = new AuditRepository(SIZE);
        }
        return instance;
    }

    /**
     *
     * @return List of Audit sorted by Audit.id acs
     */
    public List<AuditView> findAll(){
        return new LinkedList<AuditView>(audits);
    }

    public List<AuditView> findAllSortByUserNameAsc(){
        return unameIndx;
    }

    public List<AuditView> findAllSortByUserNameDesc(){
        return unameDescIndx;
    }

    /**
     *
     * @param id
     * @return Audit by id
     */
    public AuditView findOne(long id){

        int l = 0, r = audits.size()-1, m;

        while(l < r){
            m = (l+r) / 2;
            if (id > audits.get(m).getId())
                l = m+1;
            else
                r = m;
        }

        AuditView audit = audits.get(r);
        if(id == audit.getId()) return audit;
        return null;
    }


    private void updateIndices() {
        Collections.sort(unameIndx, new Comparator<AuditView>() {
            @Override
            public int compare(AuditView o1, AuditView o2) {
                return o1.getUserName().compareTo(o2.getUserName());
            }
        });
        Collections.sort(unameDescIndx, new Comparator<AuditView>() {
            @Override
            public int compare(AuditView o1, AuditView o2) {
                return o2.getUserName().compareTo(o1.getUserName());
            }
        });
    }
}
