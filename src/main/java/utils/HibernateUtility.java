package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import parts.CPU;
import parts.GPU;

import java.util.ArrayList;
import java.util.List;

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
    
    /* Searches for the CPU in the list, returns a list of string names. */
    public static List<String> searchCPU(String query){
		List<String> cpuNames = new ArrayList<>();
		// Get CPUs
		List<CPU> cpuList = (mySession.openSession()).createQuery("from CPU where name like :nameParameter", CPU.class)
				.setParameter("nameParameter", "%" + query + "%").getResultList();
		for(CPU cpu : cpuList) {
			cpuNames.add(cpu.getName());
		}
		return cpuNames;
	}
    
    /* Returns the chosen CPU. */
    public static CPU findCPUInfo(String exactName) {
    	CPU target;
    	target = (mySession.openSession()).createQuery("from CPU where name = :nameParameter", CPU.class)
    			.setParameter("nameParameter", exactName).uniqueResult();
    	return target;
    }
    
    /* Searches for the GPU in the list, returns a list of string names. */
    public static List<String> searchGPU(String query){
		List<String> gpuNames = new ArrayList<>();
		// Get CPUs
		List<GPU> gpuList = (mySession.openSession()).createQuery("from GPU where product_name like :nameParameter", GPU.class)
				.setParameter("nameParameter", "%" + query + "%").getResultList();
		for(GPU gpu : gpuList) {
			gpuNames.add(gpu.getProductName());
		}
		return gpuNames;
	}
    
    /* Returns the chosen GPU. */
    public static GPU findGPUInfo(String exactName) {
    	GPU target;
    	target = (mySession.openSession()).createQuery("from GPU where product_name = :nameParameter", GPU.class)
    			.setParameter("nameParameter", exactName).uniqueResult();
    	return target;
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
