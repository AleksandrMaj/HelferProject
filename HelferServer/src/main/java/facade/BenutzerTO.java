package facade;

import core.entities.Adresse;
import core.entities.Benutzer;
import core.enums.Benutzergruppe;

public record BenutzerTO(
        int id,
        String name,
        String vorname,
        Adresse adresse,
        Benutzergruppe benutzergruppe,
        String email,
        String passwort
)
{
    public Benutzer toBenutzer()
    {
        Benutzer benutzer = new Benutzer();
        benutzer.setId(this.id);
        benutzer.setName(this.name);
        benutzer.setVorname(this.vorname);
        benutzer.setAdresse(this.adresse);
        benutzer.setBenutzergruppe(this.benutzergruppe);
        benutzer.setEmail(this.email);
        benutzer.setPasswort(this.passwort);
        return benutzer;
    }
}