CREATE TABLE book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(300) NOT NULL,
    author VARCHAR(30) NOT NULL,
    pages INT,
    quantity INT,
    image VARCHAR(255),
    date_publication DATETIME,
    creation_date DATETIME,
    last_modification_date DATETIME
);
