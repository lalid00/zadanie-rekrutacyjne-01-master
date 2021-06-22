package pl.nethos.rekrutacja.views;

import com.vaadin.flow.component.button.*;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import pl.nethos.rekrutacja.konto.KontoBankowe;
import pl.nethos.rekrutacja.konto.KontoBankoweRepository;
import com.vaadin.flow.component.button.Button;
import pl.nethos.rekrutacja.konto.KontoBankoweService;
import pl.nethos.rekrutacja.kontrahent.Kontrahent;
import pl.nethos.rekrutacja.kontrahent.KontrahentRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Route("ListaKontKontrahenta")
public class KontrahentView extends VerticalLayout implements HasUrlParameter<Long>{




    @Autowired
    public KontrahentView(KontoBankoweRepository kontoBankoweRepository, KontrahentRepository kontrahentRepository){
        setSizeFull();

        wyswietl(kontoBankoweRepository,kontrahentRepository);
    }

    private void wyswietl(KontoBankoweRepository kontoBankoweRepository,KontrahentRepository kontrahentRepository) throws NumberFormatException{

        List<Kontrahent> kontrahentList = new ArrayList<>(kontrahentRepository.all());
        List<KontoBankowe> kontoBankoweList = new ArrayList<>(kontoBankoweRepository.all());
        List<KontoBankowe> kontoBankoweKontrahentaList = new ArrayList<>();

        KontoBankoweService service = new KontoBankoweService();

        Grid<KontoBankowe> kontoBankoweGrid = new Grid<>();

        long kontrahentId = Long.parseLong(getIdFromUrl());

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
        kontoBankoweGrid.addComponentColumn(item -> createVerifyButton(item,kontrahentList.get((int) (item.getIdKontrahent()-1)),kontoBankoweRepository,kontrahentRepository, service)).setHeader("Button").setFlexGrow(1).setTextAlign(ColumnTextAlign.CENTER);
        add(kontoBankoweGrid);
    }
    private Button createVerifyButton(KontoBankowe kontoBankowe, Kontrahent kontrahent, KontoBankoweRepository kontoBankoweRepository, KontrahentRepository kontrahentRepository, KontoBankoweService service) {
        String stan = kontoBankowe.getStanWeryfikacji();
        String stanWeryfikacji;

        if(stan == null){
            stanWeryfikacji = "nieokreslony";
        } else if (stan.equals("0")){
            stanWeryfikacji = "bledne konto";
        } else {
            stanWeryfikacji = "zweryfikowany";
        }
        String date = "";
        if(kontoBankowe.getDataWeryfikacji() == null){
            date = "Brak weryfikacji";
        } else {
            date = kontoBankowe.getDataWeryfikacji().toString();
        }
        @SuppressWarnings("unchecked")
        Button button = new Button(stanWeryfikacji, buttonClickEvent -> service.verifyAccount(kontoBankowe, kontrahent, kontoBankoweRepository,kontrahentRepository,service));
        button.getElement().setProperty("title",date);
        return button;
    }

    private void updateDatabase(KontoBankowe kontoBankowe, String stanWeryfikacji){

    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) throws NumberFormatException{
    }

    public String getIdFromUrl(){
        HttpServletRequest httpServletRequest = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest();
        String id = httpServletRequest.getRequestURL().toString();
        return id.substring(id.length()-1);
    }

}
