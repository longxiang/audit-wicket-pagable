package ru.sbrf.partneronline.web.page.audit;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.sbrf.partneronline.application.dto.AuditView;
import ru.sbrf.partneronline.application.services.AppDataService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AuditAdminPage extends WebPage {

    private static final long serialVersionUID = 1L;

    private static final String DATE_PATTERN = "dd.MM.yyyy HH:mm:ss";

    @SpringBean
    private AppDataService dataService;



    @Override
    protected void onInitialize() {
        super.onInitialize();
        dataService.populateDB();
    }

    public AuditAdminPage(){
        System.err.println("Constructor: AuditAdminPage");

        final AuditFilter auditFilter = makeDefaultFilter();
        final AuditDataSource auditDataSource = new AuditDataSource(dataService, auditFilter);

        Form<AuditFilter> filterForm = new Form<AuditFilter>("filterForm",
                new CompoundPropertyModel<AuditFilter>(auditFilter)){
            @Override
            protected void onSubmit(){

                if ( auditFilter.getDateFrom()!= null) {
                    if (auditFilter.getDateTo() == null) {
                        auditFilter.setDateTo(new Date());
                    }
                }
            }

        };

        filterForm.add(new FeedbackPanel("feedback"));

        filterForm.add(new TextField<Long>("auditId"));
        filterForm.add(DateTextField.forDatePattern("dateFrom", DATE_PATTERN));
        filterForm.add(DateTextField.forDatePattern("dateTo", DATE_PATTERN));
        filterForm.add(new TextField<String>("action"));
        filterForm.add(new TextField<String>("userName"));
        filterForm.add(new TextField<String>("ipAddress"));

        add(filterForm);

        DataGrid<IDataSource<AuditView>, AuditView, String> grid =
                new DefaultDataGrid<IDataSource<AuditView>, AuditView, String>("grid", auditDataSource, initColums());

        add(grid);
    }




    private AuditFilter makeDefaultFilter(){

        AuditFilter filter = new AuditFilter();

        Calendar now = Calendar.getInstance();
        filter.setDateTo(now.getTime());
        now.add(Calendar.DAY_OF_MONTH, -1);
        filter.setDateFrom(now.getTime());

        return filter;
    }


    private List<IGridColumn<IDataSource<AuditView>, AuditView, String>> initColums(){

        List<IGridColumn<IDataSource<AuditView>, AuditView, String>> columns = new ArrayList<IGridColumn<IDataSource<AuditView>, AuditView, String>>();

        columns.add(
                new PropertyColumn<IDataSource<AuditView>, AuditView, Long, String>(
                        new ResourceModel("id"),"id").setInitialSize(40)
        );
        columns.add(
                new PropertyColumn<IDataSource<AuditView>, AuditView, Date, String>(new ResourceModel("createDate"), "createDate")
        );
        columns.add(
                new PropertyColumn<IDataSource<AuditView>, AuditView, String, String>(new ResourceModel("action"), "action")
        );
        columns.add(
                new PropertyColumn<IDataSource<AuditView>, AuditView, String, String>(new ResourceModel("userName"), "userName", "whoIsUser_userName")
        );
        columns.add(
                new PropertyColumn<IDataSource<AuditView>, AuditView, String, String>(new ResourceModel("ipAddress"), "ipAddress")
        );

        return columns;
    }

//    private DataGrid<IDataSource<AuditView>, AuditView, String> buildGrid(List<IGridColumn<IDataSource<AuditView>, AuditView, String>> columns){
//       return new DefaultDataGrid<IDataSource<AuditView>, AuditView, String>("grid", auditDataSource, columns);
//    }


}
