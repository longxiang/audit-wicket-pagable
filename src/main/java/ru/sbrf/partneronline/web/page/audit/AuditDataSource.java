package ru.sbrf.partneronline.web.page.audit;


import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridSortState;
import org.apache.wicket.model.IModel;
import ru.sbrf.partneronline.application.dto.AuditView;
import ru.sbrf.partneronline.application.services.AppDataService;

import java.util.Iterator;
import java.util.List;


/**
 * Created by sbt-bauer-vv on 05.05.2014.
 */
public class AuditDataSource implements IDataSource<AuditView> {

     private static final long serialVersionUID = 1L;

     private static AppDataService dataService;

     private SimpleFilter filter;

     public AuditDataSource(AppDataService dataService){
         if(AuditDataSource.dataService == null){
             AuditDataSource.dataService = dataService;
         }
         filter = new SimpleFilter();
     }

     public void setFilter(SimpleFilter filter){
         System.err.printf("AuditDataSource: %s%n", filter);
         this.filter = filter;
     }


    /**
     * Implementation of this method should load subset of the data specified by
     * <code>query.getFrom()</code> and <code>query.getCount()</code>. Also if the total item count
     * can be determined, it should be passed to <code>result</code>.
     *
     * @param query  Specified the amount and position of items to be queried
     * @param result
     */
    @Override
    public void query(IQuery query, IQueryResult<AuditView> result) {


        String sortProperty = null;
        boolean sortAsc = true;

        // is there any sorting
        if (query.getSortState().getColumns().size() > 0)
        {
            // get the most relevant column
            IGridSortState.ISortStateColumn<Object> state = (IGridSortState.ISortStateColumn<Object>) query.getSortState().getColumns().get(0);

            // get the column sort properties
            sortProperty = state.getPropertyName().toString();
            sortAsc = state.getDirection() == IGridSortState.Direction.ASC;
        }

        List<AuditView> resultList = getAllFilteredSortedRecords(sortProperty, sortAsc);

        // determine the total count
        result.setTotalCount(resultList.size());

        // get the actual items
//        List<Audit> resultList = auditService.findSubset(filter, query.getFrom(), query.getCount(), sortProperty, sortAsc);

        result.setItems(
                getSubset(resultList, query.getFrom(), query.getCount()).iterator()
        );
    }

    /**
     * Allows wrapping the object in a model which will be set as model of the appropriate row. In
     * most cases the model should be detachable.
     *
     * @param object
     * @return model that can be used to access the object
     */
    @Override
    public IModel<AuditView> model(AuditView object) {
        return new DetachableAuditModel(object);
    }

    /**
     * Detaches model after use. This is generally used to null out transient references that can be
     * re-attached later.
     */
    @Override
    public void detach() {

    }


    private List<AuditView> getSubset(List<AuditView> index, long first, long count){

//        System.err.printf("AuditRepository.findSome(first=%s, count=%s, sortProperty=%s, sortAsc=%s)%n",
//                first, count, sortProperty, sortAsc);

        long last = first + count;

        if (last > index.size()) last = index.size();

        return index.subList((int)first, (int)last);
    }


    private List<AuditView> getAllFilteredSortedRecords(String sortProp, boolean asc) {



        List<AuditView> audits = AuditRepository.getInstance().findAll();


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
//            return asc ? auditRepository.findAllSortByUserNameAsc() : auditRepository.findAllSortByUserNameDesc();
            return  audits;
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
}
