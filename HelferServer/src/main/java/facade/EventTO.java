package facade;

import core.entities.Benutzer;
import core.entities.Event;

import java.util.Date;
import java.util.List;

public record EventTO(
        int id,
        String name,
        Date date,
        List<Benutzer> helferListe,
        Benutzer organisator
)
{
    public Event toEvent()
    {
        Event event = new Event();
        event.setId(this.id);
        event.setName(this.name);
        event.setDate(this.date);
        event.setOrganisator(this.organisator);
        event.setHelferListe(this.helferListe);
        return event;
    }
}