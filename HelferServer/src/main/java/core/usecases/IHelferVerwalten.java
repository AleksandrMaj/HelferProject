package core.usecases;

public interface IHelferVerwalten {
    void addHelfer(int eventID, int benutzerID);
    void deleteHelfer(int eventID, int benutzerID);
}
