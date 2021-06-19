package pl.nethos.rekrutacja.konto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class KontoBankoweRepository {

    @PersistenceContext
    private EntityManager em;

    public List<KontoBankowe> all() {
        return em.createQuery("SELECT k FROM KontoBankowe k",KontoBankowe.class).getResultList();
    }
}
