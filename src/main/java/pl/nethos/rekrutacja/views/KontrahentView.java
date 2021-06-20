package pl.nethos.rekrutacja.views;

import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.konto.KontoBankowe;
import pl.nethos.rekrutacja.konto.KontoBankoweRepository;
import com.vaadin.flow.component.button.Button;

import java.util.ArrayList;
import java.util.List;



@Route("ListaKontKontrahenta")
public class KontrahentView extends VerticalLayout {

    private long kontrahentId = 3;

    public void setKontrahentId(long kontrahentId) {
        this.kontrahentId = kontrahentId;
    }

    @Autowired
    public KontrahentView( KontoBankoweRepository kontoBankoweRepository){
        setSizeFull();

        wyswietl(kontoBankoweRepository,kontrahentId);
    }

    private void wyswietl(KontoBankoweRepository kontoBankoweRepository, long kontrahentId) {

        List<KontoBankowe> kontoBankoweList = new ArrayList<>(kontoBankoweRepository.all());
        List<KontoBankowe> kontoBankoweKontrahentaList = new ArrayList<>();

        Grid<KontoBankowe> kontoBankoweGrid = new Grid<>();

        for(int i = 0; i < kontoBankoweList.size();i++){
            if(kontoBankoweList.get(i).getIdKontrahent() == kontrahentId){
                kontoBankoweKontrahentaList.add(kontoBankoweList.get(i));
            }
        }
        kontoBankoweGrid.setItems(kontoBankoweKontrahentaList);
        kontoBankoweGrid.addColumn(KontoBankowe::getFormattedNumer).setHeader("Numer").setFlexGrow(2).setTextAlign(ColumnTextAlign.CENTER);
        kontoBankoweGrid.addColumn(KontoBankowe::isAktywne).setHeader("Aktywne").setFlexGrow(1).setTextAlign(ColumnTextAlign.CENTER);
        kontoBankoweGrid.addColumn(KontoBankowe::isDomyslne).setHeader("Domyslne").setFlexGrow(1).setTextAlign(ColumnTextAlign.CENTER);
        kontoBankoweGrid.addColumn(KontoBankowe::isWirtualne).setHeader("Wirtualne").setFlexGrow(1).setTextAlign(ColumnTextAlign.CENTER);
        kontoBankoweGrid.addComponentColumn(item -> createVerifyButton(item)).setHeader("Button").setFlexGrow(1).setTextAlign(ColumnTextAlign.CENTER);
        add(kontoBankoweGrid);
    }
    private Button createVerifyButton(KontoBankowe item) {
        String stan = item.getStanWeryfikacji();
        String stanWeryfikacji;

        if(stan == null){
            stanWeryfikacji = "nieokreslony";
        } else if (stan.equals("0")){
            stanWeryfikacji = "bledne konto";
        } else {
            stanWeryfikacji = "zweryfikowany";
        }

        @SuppressWarnings("unchecked")
        Button button = new Button(stanWeryfikacji, buttonClickEvent -> verifyAccount(item));
        return button;
    }

    private void verifyAccount(KontoBankowe kontoBankowe){
        Notification.show("Button clicked: " + kontoBankowe.getId());

    }
}
