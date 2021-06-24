package pl.nethos.rekrutacja.konto;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.persister.collection.BasicCollectionPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.nethos.rekrutacja.konto.dto.WhiteListDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void updateKontoBankowe(KontoBankowe kontoBankowe, WhiteListDto whiteListDto){
        System.out.println("--------------UPDATE--------------");

        getCurrentSession().beginTransaction();

        kontoBankowe.setStanWeryfikacji(getStatusToUpdate(whiteListDto));
        kontoBankowe.setDataWeryfikacji(getDateToUpdate(whiteListDto));

        getCurrentSession().update(kontoBankowe);

        getCurrentSession().getTransaction().commit();
    }

    public String getStatusToUpdate(WhiteListDto whiteListDto){

        String status = whiteListDto.getResult().getAccountAssigned();
        String finalStatus;

        if (status.equalsIgnoreCase("TAK")){
            finalStatus = "1";
        } else if(status.equalsIgnoreCase("NIE")){
            finalStatus = "0";
        } else {
            finalStatus = "2";
        }
        return finalStatus;
    }

    public LocalDateTime getDateToUpdate(WhiteListDto whiteListDto){
        String date = whiteListDto.getResult().getRequestDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return LocalDateTime.parse(date, formatter);
    }
}
