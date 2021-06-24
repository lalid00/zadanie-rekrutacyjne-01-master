package pl.nethos.rekrutacja.konto;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime dataWeryfikacji;

    public void setNumer(String numer) {
        this.numer = numer;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public void setStanWeryfikacji(String stanWeryfikacji) {
        this.stanWeryfikacji = stanWeryfikacji;
    }

    public void setDataWeryfikacji(LocalDateTime dataWeryfikacji) {
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

    public LocalDateTime getDataWeryfikacji() {
        return dataWeryfikacji;
    }

    public String getFormattedNumer(){
        String numerToFormat = numer;
        StringBuilder formattedNumer = new StringBuilder();

        String firstTwo = numerToFormat.substring(0,2);
        formattedNumer.append(firstTwo).append(" ");
        numerToFormat = numerToFormat.substring(2);

        while(numerToFormat.length()> 0){
            String nextFour = numerToFormat.substring(0,4);
            formattedNumer.append(nextFour).append(" ");
            numerToFormat = numerToFormat.substring(4);
        }

        return formattedNumer.toString();
    }
}
