CREATE  TABLE Product (
    id INT primary key ,
    name VARCHAR(50) NOT NULL ,
    price NUMERIC(10,2),
    creation_datetime TIMESTAMP
);

CREATE TABLE Product_category (
    id INT,
    name VARCHAR(50),
    product_id INT REFERENCES Product(id)
);