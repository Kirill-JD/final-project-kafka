CREATE TABLE product (
                         product_id VARCHAR(50) PRIMARY KEY,
                         name TEXT NOT NULL,
                         description TEXT,
                         category VARCHAR(255),
                         brand VARCHAR(255),
                         sku VARCHAR(100),
                         store_id VARCHAR(100),
                         index_name VARCHAR(100),

                         price_amount NUMERIC(10,2),
                         price_currency VARCHAR(10),

                         stock_available INT,
                         stock_reserved INT,

                         created_at TIMESTAMP,
                         updated_at TIMESTAMP
);

CREATE TABLE product_tags (
                              product_id VARCHAR(50) REFERENCES product(product_id) ON DELETE CASCADE,
                              tag TEXT
);

CREATE INDEX idx_product_tags_product_id ON product_tags(product_id);

CREATE TABLE product_image (
                               id BIGSERIAL PRIMARY KEY,
                               product_id VARCHAR(50) REFERENCES product(product_id) ON DELETE CASCADE,
                               url TEXT,
                               alt TEXT
);

CREATE INDEX idx_product_image_product_id ON product_image(product_id);

CREATE TABLE product_specification (
                                       product_id VARCHAR(50) PRIMARY KEY REFERENCES product(product_id) ON DELETE CASCADE,
                                       weight VARCHAR(50),
                                       dimensions VARCHAR(100),
                                       battery_life VARCHAR(100),
                                       water_resistance VARCHAR(50)
);

CREATE TABLE recommendation (
                                id BIGSERIAL PRIMARY KEY,
                                user_id VARCHAR(100) NOT NULL
);

CREATE TABLE recommendation_products (
                                         recommendation_id BIGINT REFERENCES recommendation(id) ON DELETE CASCADE,
                                         product_id VARCHAR(50) REFERENCES product(product_id) ON DELETE CASCADE
);