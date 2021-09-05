CREATE TABLE categories (id identity primary key, title VARCHAR(50)); 

create table products (id identity primary key, 
					title varchar(50),
					category_id int,
					productimage varchar(100),
					productdescription varchar(1000),
					price decimal(7,2),
					foreign key(category_id) references categories(id));
CREATE TABLE orders (id identity primary key, 
name VARCHAR(100),  
email VARCHAR(100),  
adress VARCHAR(250), 
preferreddate DATE,
ordertime TIMESTAMP,
phone VARCHAR(17),
items CLOB,
process_status VARCHAR(10)); 
												 