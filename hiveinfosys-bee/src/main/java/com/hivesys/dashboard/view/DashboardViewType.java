package com.hivesys.dashboard.view;

import com.hivesys.dashboard.view.dashboard.DashboardView;
import com.hivesys.dashboard.view.history.HistoryView;
import com.hivesys.dashboard.view.preferences.PreferencesView;
import com.hivesys.dashboard.view.profile.ProfileView;
import com.hivesys.dashboard.view.repository.RepositoryUpload;
import com.hivesys.dashboard.view.repository.RepositoryView;
import com.hivesys.dashboard.view.repository.UploadView;
import com.hivesys.dashboard.view.search.SearchView;
//import com.vaadin.demo.dashboard.view.sales.SalesView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    //PROFILE("profile", ProfileView.class, FontAwesome.USER, true),
	PROFILE("profile", ProfileView.class, FontAwesome.USER, true),
	//BOARDDASH("dashboard", DashboardView.class, FontAwesome.HOME, true),
    PREFERENCES("preferences", PreferencesView.class, FontAwesome.GEAR, false),
    DASHBOARD("search", SearchView.class, FontAwesome.SEARCH, true),
    //SEARCH("search", SearchView.class, FontAwesome.SEARCH, false),
    UPLOAD("upload", UploadView.class, FontAwesome.UPLOAD, false),
    REPOSITORY("repository", RepositoryView.class, FontAwesome.FOLDER, false),
    HISTORY("history", HistoryView.class, FontAwesome.CLOCK_O, false),
    //
;
;
    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
