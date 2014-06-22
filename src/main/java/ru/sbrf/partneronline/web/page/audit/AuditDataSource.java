package ru.sbrf.partneronline.web.page.audit;


import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridSortState;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
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

     private AuditFilter filter;

     public AuditDataSource(AppDataService dataService, AuditFilter filter){
         if(AuditDataSource.dataService == null){
             AuditDataSource.dataService = dataService;
         }
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

        List<AuditView> resultList = dataService.fetchPagebaleAudits((int)query.getFrom(), (int)query.getCount(), sortProperty, sortAsc);


        if (resultList!=null) {
            // determine the total count
            result.setTotalCount(dataService.countAllAudits());
            result.setItems(resultList.iterator());
        }

    }



    /**
     * Allows wrapping the object in a model which will be set as model of the appropriate row. In
     * most cases the model should be detachable.
     *
     * @param audit
     * @return model that can be used to access the object
     */
    @Override
    public IModel<AuditView> model(final AuditView audit) {
//        return new DetachableAuditModel(object);
        return new LoadableDetachableModel<AuditView>(){

            /**
             * Loads and returns the (temporary) model object.
             *
             * @return the (temporary) model object
             */
            @Override
            protected AuditView load() {
//                System.err.printf("load => %s%n", audit);
                return audit;
            }
        };
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
