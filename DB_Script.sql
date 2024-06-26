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
    ID         INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    eventID    INT,
    benutzerID INT,
    FOREIGN KEY (eventID) REFERENCES Events (eventID),
    FOREIGN KEY (benutzerID) REFERENCES Benutzer (benutzerID)
);