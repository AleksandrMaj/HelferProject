package facade;

import core.entities.Benutzer;
import core.entities.Event;
import jakarta.json.bind.annotation.JsonbDateFormat;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public record EventTO(
        int id,
        String name,

        @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime date,
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
        event.setHelferListe(this.helferListe != null ? this.helferListe : new LinkedList<>());
        return event;
    }
}