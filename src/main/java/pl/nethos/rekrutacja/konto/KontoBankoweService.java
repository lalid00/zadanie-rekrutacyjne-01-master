package pl.nethos.rekrutacja.konto;

import com.vaadin.flow.component.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.nethos.rekrutacja.kontrahent.Kontrahent;
import pl.nethos.rekrutacja.kontrahent.KontrahentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class KontoBankoweService {
    Logger log = LoggerFactory.getLogger(KontoBankoweService.class);

    private String url = "https://wl-api.mf.gov.pl/api/check/";
    private RestTemplate restTemplate = new RestTemplate();

    public void validateKontoBankowe(KontoBankowe kontoBankowe, Kontrahent kontrahent){

        String response = restTemplate.getForObject(url + "nip/{nip}/bank-account/{bank-account}",String.class,kontrahent.getNip(),kontoBankowe.getNumer());
        log.info(response);
    }

    public void verifyAccount(KontoBankowe kontoBankowe, Kontrahent kontrahent, KontoBankoweRepository kontoBankoweRepository, KontrahentRepository kontrahentRepository, KontoBankoweService service){
        Notification.show("Weryfikacja - ID kontrahenta: " + kontrahent.getId() + ", ID konta bankowego: " + kontoBankowe.getId());

        List<Kontrahent> kontrahentList = new ArrayList<>(kontrahentRepository.all());
        List<KontoBankowe> kontoBankoweList = new ArrayList<>(kontoBankoweRepository.all());

        kontoBankowe = kontoBankoweList.get(3);

        int index = (int) (kontoBankowe.getIdKontrahent()-1);
        kontrahent = kontrahentList.get(index);

        // service.validateKontoBankowe(kontoBankowe,kontrahent);

    }

}
