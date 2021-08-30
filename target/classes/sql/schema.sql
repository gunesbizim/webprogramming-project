create table products (id identity primary key, 
					title varchar(50), 
					productImage varchar(100),
					productDescription varchar(1000),
					price double(7, 2),
					categoryId varchar(50));
					
CREATE TABLE categories (id identity primary key, title VARCHAR(50)); 
				 
CREATE TABLE orders (id identity primary key, ownerEmail VARCHAR(100), ownerName VARCHAR(100), address VARCHAR(250), products VARCHAR(1000),orderDate DATETIME); 