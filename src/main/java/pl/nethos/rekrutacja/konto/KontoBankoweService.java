package pl.nethos.rekrutacja.konto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.nethos.rekrutacja.konto.dto.WhiteListDto;
import pl.nethos.rekrutacja.kontrahent.Kontrahent;

@Service
public class KontoBankoweService {
    Logger log = LoggerFactory.getLogger(KontoBankoweService.class);
    ObjectMapper objectMapper = new ObjectMapper();

    private final RestTemplate restTemplate = new RestTemplate();

    private WhiteListDto validateKontoBankowe(KontoBankowe kontoBankowe, Kontrahent kontrahent) throws JsonProcessingException {
        String url = "https://wl-api.mf.gov.pl/api/check/";
        String response = restTemplate.getForObject(url + "nip/{nip}/bank-account/{bank-account}", String.class,kontrahent.getNip(),kontoBankowe.getNumer());
        WhiteListDto whiteListDto = objectMapper.readValue(response, WhiteListDto.class);
        log.info(whiteListDto.getResult().toString());
        return whiteListDto;
    }

    public void updateAccount(KontoBankowe kontoBankowe, Kontrahent kontrahent, KontoBankoweRepository kontoBankoweRepository, KontoBankoweService service) throws JsonProcessingException {
        Notification.show("Weryfikacja - ID kontrahenta: " + kontrahent.getId() + ", ID konta bankowego: " + kontoBankowe.getId());
        log.info("Button for account with id " + kontoBankowe.getId() + " clicked");
        WhiteListDto whiteListDto = service.validateKontoBankowe(kontoBankowe,kontrahent);
        kontoBankoweRepository.updateKontoBankowe(kontoBankowe,whiteListDto);

    }

}
