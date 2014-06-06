package ru.sbrf.partneronline.web.page.audit;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridColumn;
import com.inmethod.grid.column.PropertyColumn;
import com.inmethod.grid.datagrid.DataGrid;
import com.inmethod.grid.datagrid.DefaultDataGrid;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import ru.sbrf.partneronline.application.dto.AuditView;
import ru.sbrf.partneronline.application.services.AppDataService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuditAdminPage extends WebPage {

    private static final long serialVersionUID = 1L;

    @SpringBean
    private AppDataService dataService;

//    private FilterForm filterForm;
//    private AuditDataSource auditDataSource;
//    private SimpleFilter simpleFilter;

//    private List<IGridColumn<IDataSource<Audit>, Audit, String>> columns;
//    private DataGrid<IDataSource<Audit>, Audit, String> grid;


    @Override
    protected void onInitialize() {
        super.onInitialize();
        dataService.populateDB();
    }

    public AuditAdminPage(){
        System.err.println("Constructor: AuditAdminPage");

        final SimpleFilter simpleFilter = new SimpleFilter();
        final AuditDataSource auditDataSource = new AuditDataSource(dataService);

        Form<SimpleFilter> filterForm = new Form<SimpleFilter>("filterForm",
                new CompoundPropertyModel<SimpleFilter>(simpleFilter));

        filterForm.add(new TextField<String>("action"));
        filterForm.add(new TextField<String>("userName"));

        add(filterForm);


        DataGrid<IDataSource<AuditView>, AuditView, String> grid =
                new DefaultDataGrid<IDataSource<AuditView>, AuditView, String>("grid", auditDataSource, initColums());

        add(grid);
    }

//    @Override
//    protected void onBeforeRender(){
//        System.err.println("onBeforeRender");
//        System.err.printf("[action=%s, userName=%s]%n", simpleFilter.getAction(), simpleFilter.getUserName());
//
//        auditDataSource = new AuditDataSource(simpleFilter);
////        add(updateGrid(initColums()));
//
//        super.onBeforeRender();
//    }



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
                new PropertyColumn<IDataSource<AuditView>, AuditView, String, String>(new ResourceModel("userName"), "userName", "userName")
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
