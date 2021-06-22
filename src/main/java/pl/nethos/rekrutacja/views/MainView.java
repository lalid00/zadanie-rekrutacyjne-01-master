package pl.nethos.rekrutacja.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.apache.deltaspike.core.api.provider.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import pl.nethos.rekrutacja.kontrahent.Kontrahent;
import pl.nethos.rekrutacja.kontrahent.KontrahentRepository;

import java.util.ArrayList;
import java.util.List;

@Route
@PWA(name = "Nethos - Zadanie rekrutacyjne na stanowisko programisty", shortName = "Nethos - Rekrutacja")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout {
    Logger log = LoggerFactory.getLogger(MainView.class);

    @Autowired
    public MainView(KontrahentRepository kontrahentRepository) {
        setSizeFull();

        wyswietl(kontrahentRepository);
    }

    private void wyswietl(KontrahentRepository kontrahentRepository) throws NumberFormatException{
        List<Kontrahent> kontrahentList = new ArrayList<>(kontrahentRepository.all());
        Grid<Kontrahent> kontrahentGrid = new Grid<>();

        Location location = new Location("ListaKontKontrahenta");
        kontrahentGrid.setItems(kontrahentList);
        kontrahentGrid.addColumn(Kontrahent::getNazwa).setHeader("Nazwa");
        kontrahentGrid.addColumn(Kontrahent::getNip).setHeader("NIP");
        kontrahentGrid.addItemDoubleClickListener(event -> {

            //String kontrahentId = String.valueOf(event.getItem().getId());
            log.info("Navigating to kontrahent with accurate ID");
            UI.getCurrent().navigate("ListaKontKontrahenta/" + event.getItem().getId());
            UI.getCurrent().getPage().reload();

        });

        add(kontrahentGrid);
    }

}
