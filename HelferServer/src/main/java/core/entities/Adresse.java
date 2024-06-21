package core.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Adresse
{
    private String strasse;
    private String hausnummer;
    private String stadt;
    private String plz;

    public String getStrasse()
    {
        return strasse;
    }

    public void setStrasse(String strasse)
    {
        this.strasse = strasse;
    }

    public String getHausnummer()
    {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer)
    {
        this.hausnummer = hausnummer;
    }

    public String getStadt()
    {
        return stadt;
    }

    public void setStadt(String stadt)
    {
        this.stadt = stadt;
    }

    public String getPlz()
    {
        return plz;
    }

    public void setPlz(String plz)
    {
        this.plz = plz;
    }
}