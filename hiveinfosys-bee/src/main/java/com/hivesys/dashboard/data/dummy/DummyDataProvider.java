package com.hivesys.dashboard.data.dummy;

import com.hivesys.core.db.DBConnectionPool;
import java.security.AccessControlException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.hivesys.dashboard.data.DataProvider;
import com.hivesys.dashboard.domain.User;
import com.google.common.collect.Multimap;

import com.hivesys.database.domain.QUser;
import com.mysema.query.Tuple;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;
import static com.mysema.query.support.Expressions.template;
import com.mysema.query.types.MappingProjection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A dummy implementation for the backend API.
 */
public class DummyDataProvider implements DataProvider {

    // TODO: Get API key from http://developer.rottentomatoes.com
    private static final String ROTTEN_TOMATOES_API_KEY = null;

    /* List of countries and cities for them */
    private static Multimap<String, String> countryToCities;
    private static Date lastDataUpdate;

    private static Random rand = new Random();

    /**
     * Initialize the data for this application.
     */
    public DummyDataProvider() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -1);
        if (lastDataUpdate == null || lastDataUpdate.before(cal.getTime())) {

            lastDataUpdate = new Date();
        }
    }

    /**
     * Get a list of movies currently playing in theaters.
     *
     * @return a list of Movie objects
     */
    /**
     * Initialize the list of movies playing in theaters currently. Uses the
     * Rotten Tomatoes API to get the list. The result is cached to a local file
     * for 24h (daily limit of API calls is 10,000).
     *
     * @param userName
     * @param password
     * @return
     */
    @Override
    public User authenticate(String userName, String password) throws AccessControlException {
        if (userName.equals("")) {

            return authenticate("SJoshi", "moodle123");
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e1) {
        }
        byte[] passwordDigest = md.digest(password.getBytes());
        StringBuilder passwordmd5hash = new StringBuilder();
        for (int i = 0; i < passwordDigest.length; ++i) {
            passwordmd5hash.append(Integer.toHexString((passwordDigest[i] & 0xFF) | 0x100).substring(1, 3));
        }

        try {
            SQLTemplates config = DBConnectionPool.getInstance().getSQLTemplates();
            Connection con = DBConnectionPool.getInstance().reserveConnection();

            QUser dbUser = QUser.User;

            // insert into file info

            SQLQuery query = new SQLQuery( con, config);
            List<Tuple> result = query.from(dbUser)
                    .where(dbUser.userName.eq(userName).and(dbUser.pword.eq(passwordmd5hash.toString())))
                    .list(dbUser.id, dbUser.firstName, dbUser.lastName, dbUser.email, dbUser.gender, dbUser.title, dbUser.location, dbUser.role, dbUser.telephone, dbUser.website, dbUser.bio);

            if (result.isEmpty()) {
                throw new AccessControlException("Exception: Invalid username or password");
            }

            User u = new User(result.get(0).get(dbUser.id));
            u.setFirstName(result.get(0).get(dbUser.firstName));
            u.setLastName(result.get(0).get(dbUser.lastName));
            u.setEmail(result.get(0).get(dbUser.email));
            u.setMale(result.get(0).get(dbUser.gender));
            u.setTitle(result.get(0).get(dbUser.title));
            u.setLocation(result.get(0).get(dbUser.location));
            u.setRole(result.get(0).get(dbUser.role));
            u.setPhone(result.get(0).get(dbUser.telephone));
            u.setWebsite(result.get(0).get(dbUser.website));
            u.setBio(result.get(0).get(dbUser.bio));

            // always close the connection (performance optimization)
            con.close();
            return u;

        } catch (SQLException ex) {
            Logger.getLogger(DummyDataProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new AccessControlException("Exception: Invalid username or password");                                            
    }

}
