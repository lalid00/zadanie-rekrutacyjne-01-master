package pl.nethos.rekrutacja.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.konto.KontoBankowe;
import pl.nethos.rekrutacja.konto.KontoBankoweRepository;
import pl.nethos.rekrutacja.kontrahent.KontrahentRepository;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Route("ListaKontKontrahenta")
public class KontrahentView extends VerticalLayout {

    private int kontrahentId;

    public void setKontrahentId(int kontrahentId) {
        this.kontrahentId = kontrahentId;
    }

    public KontrahentView(@Autowired KontoBankoweRepository kontoBankoweRepository){
        setSizeFull();

        wyswietl(kontoBankoweRepository,3);
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
        kontoBankoweGrid.addColumn(KontoBankowe::getNumer).setHeader("Numer");
        kontoBankoweGrid.addColumn(KontoBankowe::isAktywne).setHeader("Aktywne");
        kontoBankoweGrid.addColumn(KontoBankowe::isDomyslne).setHeader("Domyslne");
        kontoBankoweGrid.addColumn(KontoBankowe::isWirtualne).setHeader("Wirtualne");
        add(kontoBankoweGrid);
    }
}
