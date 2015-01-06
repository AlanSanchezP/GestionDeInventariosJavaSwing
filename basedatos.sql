create database examen;

use examen;

create table usuarios(
	id_usuario int primary key,
	email varchar(250) not null,
	pass varchar(70) not null
);

create table clientes(
	id_cliente int primary key,
	nombres varchar(200) not null,
	apaterno varchar(100) not null,
	amaterno varchar(100) not null,
	email varchar(250) not null,
	pass varchar(70) not null
);

create table categorias(
	id_categoria int primary key,
	nombre varchar(200) not null,
	id_categoria_padre int default 0,
	FOREIGN KEY (id_categoria_padre) REFERENCES categorias(id_categoria)
);

create table productos(
	id_producto int primary key,
	descripcion varchar(200) not null,
	costo_compra int,
	costo_venta int,
	imagen varchar(200) not null,
	inventario int,
	id_categoria int,
	FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria)
);

create table compras(
	id_compra int primary key,
	id_cliente int,
	id_producto int,
	cantidad int,
	fecha datetime,
	FOREIGN KEY (id_producto) REFERENCES productos(id_producto),
	FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

