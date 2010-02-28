package patterns.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import patterns.IPatternStore;
import patterns.Pattern;

public class HibernatePatternStore implements IPatternStore{
    Session session;
    Long maxIndex;

    public HibernatePatternStore() {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        session = factory.openSession();
        factory.close();
    }//constructor

    public void saveOrUpdatePattern(Pattern pattern) {
        session.beginTransaction();
        session.saveOrUpdate(pattern);
        session.getTransaction().commit();        
    }//savePattern

    public Pattern getPatternById(Long id) {
         return (Pattern) session.get(patterns.Pattern.class, id);
    }//getPatternById

}
