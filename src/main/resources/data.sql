CREATE TABLE "orders"
(
    order_id INT AUTO_INCREMENT NOT NULL,
    date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP(),
    cost     FLOAT NOT NULL,
    CONSTRAINT order_pkey PRIMARY KEY (order_id)
);

CREATE TABLE "products"
(
    id INT AUTO_INCREMENT NOT NULL,
    name       VARCHAR(45) NOT NULL,
    cost       FLOAT NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

CREATE TABLE "order_products"
(
    order_id   INT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES "orders" (order_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES "products" (id) ON DELETE CASCADE
);