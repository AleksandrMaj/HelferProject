package core.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Helfer implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int mitgliedId;
    private int eventId;

    // Standardkonstruktor
    public Helfer() {
    }

    // Konstruktor mit Parametern
    public Helfer(int mitgliedId, int eventId) {
        this.mitgliedId = mitgliedId;
        this.eventId = eventId;
    }

    // Getter und Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMitgliedId() {
        return mitgliedId;
    }

    public void setMitgliedId(int mitgliedId) {
        this.mitgliedId = mitgliedId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
