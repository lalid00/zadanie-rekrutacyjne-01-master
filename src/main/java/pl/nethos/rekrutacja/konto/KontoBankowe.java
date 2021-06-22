package pl.nethos.rekrutacja.konto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Entity
public class KontoBankowe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kontrahent_gen")
    @SequenceGenerator(name="kontrahent_gen", sequenceName = "kontrahent_seq", allocationSize = 1)
    private long id;

    private long idKontrahent;

    private String numer;

    private boolean aktywne;

    private boolean domyslne;

    private boolean wirtualne;

    private String stanWeryfikacji;

    private Date dataWeryfikacji;

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public void setDomyslne(boolean domyslne) {
        this.domyslne = domyslne;
    }

    public void setWirtualne(boolean wirtualne) {
        this.wirtualne = wirtualne;
    }

    public void setStanWeryfikacji(String stanWeryfikacji) {
        this.stanWeryfikacji = stanWeryfikacji;
    }

    public void setDataWeryfikacji(Date dataWeryfikacji) {
        this.dataWeryfikacji = dataWeryfikacji;
    }

    public long getId() {
        return id;
    }

    public long getIdKontrahent() {
        return idKontrahent;
    }

    public String getNumer() {
        return numer;
    }

    public boolean isAktywne() {
        return aktywne;
    }

    public boolean isDomyslne() {
        return domyslne;
    }

    public boolean isWirtualne() {
        return wirtualne;
    }

    public String getStanWeryfikacji() {
        return stanWeryfikacji;
    }

    public Date getDataWeryfikacji() {
        return dataWeryfikacji;
    }

    public String getFormattedNumer(){
        String numerToFormat = numer;
        StringBuilder formattedNumer = new StringBuilder();

        String firstTwo = numerToFormat.substring(0,2);
        formattedNumer.append(firstTwo).append(" ");
        numerToFormat = numerToFormat.substring(2,numerToFormat.length());

        while(numerToFormat.length()> 0){
            String nextFour = numerToFormat.substring(0,4);
            formattedNumer.append(nextFour).append(" ");
            numerToFormat = numerToFormat.substring(4,numerToFormat.length());
        }

        return formattedNumer.toString();
    }
}
