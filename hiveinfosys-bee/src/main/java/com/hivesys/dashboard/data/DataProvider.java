package com.hivesys.dashboard.data;

import java.security.AccessControlException;
import com.hivesys.dashboard.domain.User;

/**
 * DataHive Dashboard backend API.
 */
public interface DataProvider {

    User authenticate(String userName, String password) throws AccessControlException;
}
