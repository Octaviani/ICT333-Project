package HiveSys;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.navigator.ViewDisplay;

import HiveSys.layout.MainLayout;

public class MainView extends MainLayout implements View{
	public static final String NAME = "";
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		getUI().getNavigator().navigateTo(FileUpload.NAME);
		
		ViewDisplay sd;
		sd.showView(view);
	}

}
