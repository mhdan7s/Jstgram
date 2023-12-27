USE mmohammedanas2;
DROP TABLE IF EXISTS Visibility;
DROP TABLE IF EXISTS Posts;
DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
  userID INT UNIQUE NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL, 
  PRIMARY KEY (userID)
);

INSERT INTO Users(username, password) VALUES
('Alice', 'Alice123'),
('Bob', 'Bob123'),
('Crystal', 'Crystal123'),
('David', 'David123');

CREATE TABLE Posts (
  postID INT NOT NULL AUTO_INCREMENT,
  postText VARCHAR(255) NOT NULL, 
  postTime VARCHAR(255) NOT NULL,
  visibleID INT NOT NULL,
  PRIMARY KEY(postID),
  FOREIGN KEY (visibleID) REFERENCES Users(userID)
);

INSERT INTO Posts(postText, postTime, visibleID) VALUES 
("Project deadline extended?", "07:00:00PM, Oct 12, 2023", 1),
("Yep", "07:01:00PM, Oct 12, 2023", 2),
("Fall break", "09:00:00PM, Oct 16, 2023", 4),
("Lab due tonight?", "11:30:00PM, Oct 27, 2023", 1),
("No, it's due next week", "11:35:00PM, Oct 27, 2023", 3);

CREATE TABLE Visibility (
  ID INT UNIQUE NOT NULL auto_increment,
  userID INT NOT NULL,
  visibleID INT NOT NULL,
  Primary key(ID),
  FOREIGN KEY(userID) REFERENCES Users(userID),
  FOREIGN KEY(visibleID) REFERENCES Users(userID)
);

INSERT INTO Visibility (userID, visibleID) VALUES 
(1, 2),
(1, 3),
(2, 1),
(2, 3),
(3, 1),
(1,1),
(2,2),
(3,3),
(4,4);
