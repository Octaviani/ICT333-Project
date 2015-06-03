package com.hivesys.dashboard.data;

import java.security.AccessControlException;
import com.hivesys.dashboard.domain.User;

/**
 * DataHive Dashboard backend API.
 */
public interface DataProvider {
    /**
     * @param count
     *            Number of transactions to fetch.
     * @return A Collection of most recent transactions.

    /**
     * @param userName
     * @param password
     * @return Authenticated used.
     */
    User authenticate(String userName, String password) throws AccessControlException;


}
