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
VALUES ('Mustermann', 'Max', 'Musterstraße', '1', 'Musterstadt', '12345', 'ADMIN',
        'max.mustermann@example.com', 'password123'),
       ('Doe', 'John', 'Main St', '123', 'Sample City', '67890', 'USER', 'john.doe@example.com',
        'password456');

-- Drop table Helfer if exists (Apache Derby doesn't support IF EXISTS directly)
-- Make sure to drop the table manually or use your DB management tool to drop it before running this script

-- Create the table
CREATE TABLE Helfer
(
    id         INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    mitgliedId INT NOT NULL,
    eventId    INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_mitglied FOREIGN KEY (mitgliedId) REFERENCES Benutzer(id),
    CONSTRAINT FK_Event FOREIGN KEY (eventID) REFERENCES Event (eventID)
);

-- Optionally, you can add indexes for the foreign keys if necessary
CREATE INDEX idx_mitgliedId ON Helfer (mitgliedId);
CREATE INDEX idx_eventId ON Helfer (eventId);

-- Insert sample data
-- INSERT INTO Helfer (mitgliedId, eventId) VALUES (1, 100);
-- INSERT INTO Helfer (mitgliedId, eventId) VALUES (2, 101);



------------------------------------------------------------------------------------------------------------------------
-- Meine Variante??

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
VALUES ('Mustermann', 'Max', 'Musterstraße', '1', 'Musterstadt', '12345', 'ADMIN', 'max.mustermann@example.com', 'password123'),
       ('Doe', 'John', 'Main St', '123', 'Sample City', '67890', 'USER', 'john.doe@example.com', 'password456'),
       ('Doe', 'Jane', 'Second St', '456', 'Example City', '23456', 'USER', 'jane.doe@example.com', 'password789'),
       ('Smith', 'Alice', 'Third St', '789', 'Test City', '34567', 'USER', 'alice.smith@example.com', 'password123'),
       ('Johnson', 'Bob', 'Fourth St', '101', 'Demo City', '45678', 'USER', 'bob.johnson@example.com', 'password456');

-- Create Event table
CREATE TABLE Event (
                       eventID INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
                       name VARCHAR(255) NOT NULL,
                       date DATE NOT NULL,
                       organisatorID INT,
                       CONSTRAINT FK_Organisator FOREIGN KEY (organisatorID) REFERENCES Benutzer (id)
);

-- Create Helfer table for the Many-to-Many relationship
CREATE TABLE Helfer
(
    id         INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    mitgliedId INT NOT NULL,
    eventId    INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_mitglied FOREIGN KEY (mitgliedId) REFERENCES Benutzer(id),
    CONSTRAINT FK_Event FOREIGN KEY (eventId) REFERENCES Event (eventID)
);

-- Optionally, you can add indexes for the foreign keys if necessary
CREATE INDEX idx_mitgliedId ON Helfer (mitgliedId);
CREATE INDEX idx_eventId ON Helfer (eventId);

-- Insert a sample Event
INSERT INTO Event (name, date, organisatorID) VALUES ('Sample Event', '2024-06-26', 3);

-- Insert sample Helfer for the Event
INSERT INTO Helfer (mitgliedId, eventId) VALUES (4, 1);
INSERT INTO Helfer (mitgliedId, eventId) VALUES (5, 1);

-- Create a view to show events with their helpers
CREATE VIEW EventWithHelpers AS
SELECT
    e.eventID,
    e.name AS eventName,
    e.date,
    e.organisatorID,
    b.id AS helperID,
    b.vorname AS helperVorname,
    b.name AS helperName
FROM Event e
         LEFT JOIN Helfer h ON e.eventID = h.eventId
         LEFT JOIN Benutzer b ON h.mitgliedId = b.id;

-- Query to retrieve events with their helpers
SELECT * FROM EventWithHelpers;
