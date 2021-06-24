package pl.nethos.rekrutacja.konto;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.collection.BasicCollectionPersister;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.nethos.rekrutacja.konto.dto.WhiteListDto;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class KontoBankoweRepository {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    SessionFactory sessionFactory;

    protected Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }

    public List<KontoBankowe> all() {
        return em.createQuery("SELECT k FROM KontoBankowe k",KontoBankowe.class).getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStatus(KontoBankowe kontoBankowe){
        System.out.println("--------------UPDATE--------------");
        BasicCollectionPersister basicCollectionPersister;
        getCurrentSession().beginTransaction();
        kontoBankowe.setStanWeryfikacji("1");

        getCurrentSession().update(kontoBankowe);
        getCurrentSession().getTransaction().commit();
    }
}
