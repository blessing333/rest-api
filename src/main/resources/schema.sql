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
    address VARCHAR(40) NOT NULL,
    create_at datetime(6)  NOT NULL
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

CREATE TABLE orders
(
    id     binary(16) PRIMARY KEY,
    buyer_id binary(16) NOT NULL,
    order_status VARCHAR(50)  NOT NULL,
    order_date   datetime(6)  NOT NULL,
    CONSTRAINT fk_orders_to_customers FOREIGN KEY (buyer_id) REFERENCES customers (id)
);

CREATE TABLE order_items
(
    id           binary(16)  PRIMARY KEY,
    order_id     binary(16)  NOT NULL,
    item_id      binary(16)  NOT NULL,
    order_price  int         NOT NULL,
    item_count   int         NOT NULL,
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES orders (id) ON DELETE CASCADE,
    CONSTRAINT fk_order_items_to_product FOREIGN KEY (item_id) REFERENCES items (id)
);
