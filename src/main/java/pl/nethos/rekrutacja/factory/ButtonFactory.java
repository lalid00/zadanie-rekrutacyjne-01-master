package pl.nethos.rekrutacja.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import pl.nethos.rekrutacja.konto.KontoBankowe;
import pl.nethos.rekrutacja.konto.KontoBankoweRepository;
import pl.nethos.rekrutacja.konto.KontoBankoweService;
import pl.nethos.rekrutacja.kontrahent.Kontrahent;

public class ButtonFactory {

    public static Button createVerifyButton(KontoBankowe kontoBankowe, Kontrahent kontrahent, KontoBankoweRepository kontoBankoweRepository, KontoBankoweService service) {
        String stan = kontoBankowe.getStanWeryfikacji();
        String stanWeryfikacji;

        if(stan == null){
            stanWeryfikacji = "nieokreslony";
        } else if (stan.equals("0")){
            stanWeryfikacji = "bledne konto";
        } else {
            stanWeryfikacji = "zweryfikowany";
        }
        String date;
        if(kontoBankowe.getDataWeryfikacji() == null){
            date = "Brak weryfikacji";
        } else {
            date = kontoBankowe.getDataWeryfikacji().toString();
        }

        Button button = new Button(stanWeryfikacji, buttonClickEvent -> {
            try {
                service.updateAccount(kontoBankowe, kontrahent,kontoBankoweRepository,service);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            UI.getCurrent().getPage().reload();
        });
        button.getElement().setProperty("title",date);
        return button;
    }
}
