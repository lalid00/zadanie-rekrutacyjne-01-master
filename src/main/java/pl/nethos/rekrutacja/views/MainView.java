package pl.nethos.rekrutacja.views;

import com.vaadin.flow.component.charts.model.Navigator;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ClickableRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.RouteMatcher;
import org.springframework.web.servlet.view.RedirectView;
import pl.nethos.rekrutacja.konto.KontoBankoweRepository;
import pl.nethos.rekrutacja.kontrahent.Kontrahent;
import pl.nethos.rekrutacja.kontrahent.KontrahentRepository;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Route
@PWA(name = "Nethos - Zadanie rekrutacyjne na stanowisko programisty", shortName = "Nethos - Rekrutacja")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class MainView extends VerticalLayout {
    RedirectView listaKontKontrahenta = new RedirectView("ListaKontKontrahenta");

    public MainView(@Autowired KontrahentRepository kontrahentRepository) {
        setSizeFull();

        wyswietl(kontrahentRepository);
    }

    private void wyswietl(KontrahentRepository kontrahentRepository) {
        List<Kontrahent> kontrahentList = new ArrayList<>(kontrahentRepository.all());

        Grid<Kontrahent> kontrahentGrid = new Grid<>();

        kontrahentGrid.setItems(kontrahentList);
        kontrahentGrid.addColumn(Kontrahent::getNazwa).setHeader("Nazwa");
        kontrahentGrid.addColumn(Kontrahent::getNip).setHeader("NIP");


        add(kontrahentGrid);
    }
}
