package ru.sbrf.partneronline.web.page.audit;

import org.apache.wicket.model.LoadableDetachableModel;
import ru.sbrf.partneronline.application.dto.AuditView;

/**
 * Created by sbt-bauer-vv on 06.05.2014.
 */
public class DetachableAuditModel extends LoadableDetachableModel<AuditView> {

    private final long id;


    public DetachableAuditModel(AuditView audit){
        this(audit.getId());
    }

    public DetachableAuditModel(long id){
        this.id = id;
    }


    /**
     * Loads and returns the (temporary) model object.
     *
     * @return the (temporary) model object
     */
    @Override
    protected AuditView load() {
        // loads audit from the DB
        return AuditRepository.getInstance().findOne(id);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DetachableAuditModel that = (DetachableAuditModel) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }
}
