CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    street VARCHAR(100) NOT NULL,
    postal_code VARCHAR(30) NOT NULL,
    city VARCHAR(50) NOT NULL,
    country VARCHAR(50) NOT NULL,
    creation_date DATETIME,
    updated_date DATETIME,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);
