package ru.sbrf.partneronline.web.page.audit;

import ru.sbrf.partneronline.application.dto.AuditView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by sbt-bauer-vv on 07.05.2014.
 */
public class AuditService {

    private static AuditService instance;

    private AuditRepository auditRepository = AuditRepository.getInstance();


    private AuditService(){}

    public static AuditService getInstance(){
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }



    /**
     *
     * @return number of all audit records in the DB
     */
    public int getCount(){
//        System.err.println("AuditRepository.getCount()");
        return auditRepository.findAll().size();
    }


    /**
     *
     * select audits and apply sort, based on sortProperty
     *
     * @param first
     * @param count
     * @param sortProperty
     * @param sortAsc
     * @return
     */
    public List<AuditView> findSubset(AuditFilter filter, long first, long count, String sortProperty, boolean sortAsc){

//        System.err.printf("AuditRepository.findSome(first=%s, count=%s, sortProperty=%s, sortAsc=%s)%n",
//                first, count, sortProperty, sortAsc);

        List<AuditView> index = getAllFilteredSortedRecords(filter, sortProperty, sortAsc);
        long last = first + count;

        if (last > index.size()) last = index.size();

        return index.subList((int)first, (int)last);
    }


    private List<AuditView> getAllFilteredSortedRecords(final AuditFilter filter, String sortProp, boolean asc) {

        List<AuditView> audits = auditRepository.findAll();


        if (filter.getAction() != null) {
            filterAudits(audits, new Predicate<AuditView>() {
                @Override
                public boolean test(AuditView audit) {
                    return audit.getAction().toLowerCase().contains(filter.getAction().toLowerCase());
                }
            });
        }


        if (sortProp == null) {
            return audits;
        }
        if (sortProp.equals("userName")) {
            return asc ? auditRepository.findAllSortByUserNameAsc() : auditRepository.findAllSortByUserNameDesc();
        }
        throw new RuntimeException(String.format("unknown sort option [%s], valid options: [userName]", sortProp));
    }




    private void filterAudits(List<AuditView> audits, Predicate<AuditView> p){

        Iterator<AuditView> iterator = audits.iterator();

        while (iterator.hasNext()){
            AuditView audit = iterator.next();
            if (!p.test(audit)) {
                iterator.remove();
            }

        }

    }



//    private static class AuditFilter{
//
//        private AuditFilter(){}
//
//        public AuditFilter apply(SimpleFilter){
//
//        }
//    }

}
