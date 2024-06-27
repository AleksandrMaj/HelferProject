-- Drop table Benutzer if exists (Apache Derby doesn't support IF EXISTS directly)
-- Make sure to drop the table manually or use your DB management tool to drop it before running this script

-- Create Benutzer table
CREATE TABLE Benutzer
(
    id             INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name           VARCHAR(255),
    vorname        VARCHAR(255),
    strasse        VARCHAR(255),
    hausnummer     VARCHAR(10),
    stadt          VARCHAR(255),
    plz            VARCHAR(10),
    benutzergruppe VARCHAR(50),
    email          VARCHAR(255),
    passwort       VARCHAR(255)
);

-- Insert example data
INSERT INTO Benutzer (name, vorname, strasse, hausnummer, stadt, plz, benutzergruppe, email, passwort)
VALUES ('Mustermann', 'Max', 'Musterstraße', '1', 'Musterstadt', '12345', 'ADMIN', 'max.mustermann@example.com',
        'password123'),
       ('Doe', 'John', 'Main St', '123', 'Sample City', '67890', 'USER', 'john.doe@example.com', 'password456');

-- Create Event table
CREATE TABLE Event
(
    eventID       INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name          VARCHAR(255) NOT NULL,
    date          DATE         NOT NULL,
    organisatorID INT,
    CONSTRAINT FK_Organisator FOREIGN KEY (organisatorID) REFERENCES Benutzer (id)
);

-- Create Helfer table for the Many-to-Many relationship
CREATE TABLE Helfer
(
    id         INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    benutzerID INT NOT NULL,
    eventID    INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_benutzer FOREIGN KEY (benutzerID) REFERENCES Benutzer (id),
    CONSTRAINT FK_Event FOREIGN KEY (eventID) REFERENCES Event (eventID)
);

-- Optionally, you can add indexes for the foreign keys if necessary
CREATE INDEX idx_benutzerID ON Helfer (benutzerID);
CREATE INDEX idx_eventID ON Helfer (eventId);

-- Insert a sample Event
INSERT INTO Event (name, date, organisatorID)
VALUES ('Sample Event', '2024-06-26', 3);




