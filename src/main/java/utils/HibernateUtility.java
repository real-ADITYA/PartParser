package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Session;

/* Class responsible for handling the hibernate utility. Only one instance of this can be created (static fields). */
public class HibernateUtility {

    /* This field will be initialized when a HibernateUtility object is created. */
    private static final SessionFactory mySession = initSession();

    /* This will initialize the session factory. */
    private static SessionFactory initSession(){
        return new Configuration().configure().buildSessionFactory();
    }

    /* This will start a session and begin the transactions. */
    public static Session start(){
        Session session = mySession.openSession();
        session.beginTransaction();
        return session;
    }

    /* Return the session factory object being used. */
    public static SessionFactory getSession(){
        return mySession;
    }

    /* Shut down or terminate the session factory. */
    public static void shutdown(){
        mySession.close();
    }

    /* Shut down the session (not to be confused with session factory shutdown). */
    public static void closeSession(Session session){
        if (session.getTransaction().isActive()) {
            session.getTransaction().commit();
        }
        session.close();
    }
}
