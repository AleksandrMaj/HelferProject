-- Drop table Benutzer if exists (Apache Derby doesn't support IF EXISTS directly)
-- Make sure to drop the table manually or use your DB management tool to drop it before running this script

DROP TABLE Helfer;
DROP TABLE Event;
DROP TABLE Benutzer;

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

-- Create Event table
CREATE TABLE Event
(
    ID            INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    name          VARCHAR(255) NOT NULL,
    date          TIMESTAMP    NOT NULL,
    organisatorID INT,
    FOREIGN KEY (organisatorID) REFERENCES Benutzer (id)
);

-- Create Helfer table for the Many-to-Many relationship
CREATE TABLE Helfer
(
    ID         INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    eventID    INT NOT NULL,
    benutzerID INT NOT NULL,
    FOREIGN KEY (benutzerID) REFERENCES Benutzer (id),
    FOREIGN KEY (eventID) REFERENCES Event (id)
);

-- Optionally, you can add indexes for the foreign keys if necessary
CREATE INDEX idx_benutzerID ON Helfer (benutzerID);
CREATE INDEX idx_eventID ON Helfer (eventID);

-- Insert example data
INSERT INTO Benutzer (name, vorname, strasse, hausnummer, stadt, plz, benutzergruppe, email, passwort)
VALUES ('Mustermann', 'Max', 'Musterstraße', '1', 'Musterstadt', '12345', 'ORGANISATOR', 'max.mustermann@example.com',
        'password123'),
       ('Doe', 'John', 'Main St', '123', 'Sample City', '67890', 'MITGLIED', 'john.doe@example.com', 'password456'),
       ('Smith', 'Jane', 'Second St', '456', 'Example Town', '10101', 'MITGLIED', 'jane.smith@example.com',
        'password789'),
       ('Brown', 'Charlie', 'Third Ave', '789', 'Testville', '20202', 'ORGANISATOR', 'charlie.brown@example.com',
        'password321');

-- Insert a sample Event
INSERT INTO Event (name, date, organisatorID)
VALUES ('Sample Event 1', '2024-06-26 10:00:00', 1),
       ('Sample Event 2', '2024-07-15 14:00:00', 4),
       ('Sample Event 3', '2024-08-05 09:30:00', 4);

-- Insert a sample Event
/*INSERT INTO Helfer (eventID, benutzerID)
VALUES (1, 2),
       (1, 3),
       (2, 1),
       (2, 4),
       (3, 2),
       (3, 3);
*/
