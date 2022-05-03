CREATE TABLE categories
(
    id   BINARY(16) PRIMARY KEY,
    name VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE customers
(
    id   BINARY(16) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    email VARCHAR(20) NOT NULL,
    state VARCHAR(20) NOT NULL,
    province VARCHAR(20) NOT NULL,
    zipcode VARCHAR(20) NOT NULL
);

CREATE TABLE items
(
    id   BINARY(16) PRIMARY KEY,
    category_id BINARY(16) NOT NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(20) NOT NULL,
    price long NOT NULL,
    quantity int NOT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL,
    CONSTRAINT fk_order_items_to_category FOREIGN KEY (category_id) REFERENCES categories (id)
);
