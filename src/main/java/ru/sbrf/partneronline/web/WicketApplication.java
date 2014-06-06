package ru.sbrf.partneronline.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import ru.sbrf.partneronline.application.services.AppDataService;
import ru.sbrf.partneronline.web.page.audit.AuditAdminPage;

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 */
public class WicketApplication extends WebApplication
{

	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	@Override
	public Class<? extends WebPage> getHomePage()
	{
		return AuditAdminPage.class;
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	@Override
	public void init()
	{
		super.init();
		// add your configuration here
//        addComponentInstantiationListener(new SpringComponentInjector(this));
        getComponentInstantiationListeners().add(new SpringComponentInjector(this));

    }


}
