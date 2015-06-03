package com.hivesys.dashboard.data;

import java.security.AccessControlException;
import java.util.Collection;
import java.util.Date;

import com.hivesys.dashboard.domain.DashboardNotification;
import com.hivesys.dashboard.domain.User;

/**
 * DataHive Dashboard backend API.
 */
public interface DataProvider {
    User authenticate(String userName, String password) throws AccessControlException;
}
