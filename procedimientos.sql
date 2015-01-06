use examen;

delimiter //

create procedure registrar(
	in u_email varchar(200),
	in u_pass varchar(80)
)

begin
	declare n_id int;
	
	if (select count(*) from usuarios where email = u_email) = 0 then
		if (select count(*) from usuarios) = 0 then
			set n_id = 1;
		else
			set n_id = (select max(id_usuario) + 1 from usuarios);
		end if;
		insert into usuarios(id_usuario, email, pass)
		values(n_id, u_email, u_pass);
		select 'Registro exitoso' as mensaje;
	else
		select 'Error. El email ya se encuentra registrado en la base de datos.' as mensaje;
	end if;
	
end

//

create procedure nuevo_cliente(
	in nombs varchar(200),
	in apat varchar(100),
	in amat varchar(100),
	in u_email varchar(200),
	in u_pass varchar(80)
)

begin
	declare n_id int;
	
	if (select count(*) from clientes where email = u_email) = 0 then
		if (select count(*) from clientes) = 0 then
			set n_id = 1;
		else
			set n_id = (select max(id_cliente) + 1 from clientes);
		end if;
		insert into clientes(id_cliente, nombres, apaterno, amaterno, email, pass)
		values(n_id, nombs, apat, amat, u_email, u_pass);
		select 'Registro exitoso' as mensaje;
	else
		select 'Error. El email ya se encuentra registrado en la base de datos.' as mensaje;
	end if;
	
end

//

create procedure nueva_categoria(
	in id_padre int,
	in nombre_n varchar(200)
)

begin
	declare n_id int;

	
	if (select count(*) from categorias) = 0 then
		set n_id = 1;
	else
		set n_id = (select max(id_categoria) + 1 from categorias);
	end if;
	
	if (select count(*) from categorias where nombre = nombre_n) = 0 then
		SET foreign_key_checks = 0;	
		insert into categorias(id_categoria, id_categoria_padre, nombre)
		values(n_id, id_padre, nombre_n);
		select 'Categoría registrada con exito' as mensaje;
	else
		select 'No puede haber dos categorías con el mismo nombre.' as mensaje;
	end if;
	
	
end

//

create procedure verificar_existe(
	in u_email varchar(200),
	in u_pass varchar(70)
)

begin


	if (select count(*) from usuarios where email = u_email) = 0 then
		select 'Error. El email no se encuentra registrado en la base de datos.' as mensaje;
	elseif (select count(*) from usuarios where email = u_email and pass = u_pass) = 0 then
		select 'Error. Password incorrecto.' as mensaje;
	else
		select 'Bienvenid@' as mensaje;
	end if;
	
end

//

create procedure verificar_existe_cliente(
	in u_email varchar(200),
	in u_pass varchar(70)
)

begin


	if (select count(*) from clientes where email = u_email) = 0 then
		select 'Error. El email no se encuentra registrado en la base de datos.' as mensaje;
	elseif (select count(*) from clientes where email = u_email and pass = u_pass) = 0 then
		select 'Error. Password incorrecto.' as mensaje;
	else
		select 'Bienvenid@' as mensaje;
	end if;
	
end

//

create procedure obtener_clientes()

begin

	select * from clientes;

end

//
create procedure obtener_categorias(in id_p int)

begin
		
		select * from categorias where id_categoria_padre = id_p;
end

//

create procedure existe_cliente(in c_id int)

begin

	if (select count(*) from clientes where id_cliente = c_id) = 0 then
		select 0 as mensaje;
	else
		select 1 as mensaje;
	end if;

end

//

create procedure existen_compras(in c_id int)

begin

	if(select count(*) from compras where id_cliente = c_id) = 0 then
		select 0 as mensaje;
	else
		select 1 as mensaje;
	end if;

end

//

create procedure obtener_compras(in c_id int)

begin

	select compras.id_compra as id, productos.id_producto as id_producto, productos.descripcion as nombre, productos.costo_venta as costo_venta, compras.cantidad as cantidad, compras.fecha as fecha from productos inner join compras on productos.id_producto = compras.id_producto where id_cliente = c_id;
	
end

//

create procedure existe_categoria(in c_id int)

begin

	if(select count(*) from categorias where id_categoria = c_id) = 0 then
		select 0 as mensaje;
	else
		select 1 as mensaje;
	end if;

end

//


create procedure existen_productos(in c_id int)

begin

	if(select count(*) from productos where id_categoria = c_id) = 0 then
		select 0 as mensaje;
	else
		select 1 as mensaje;
	end if;

end

//

create procedure existe_producto_2(in p_id int)

begin

	if(select count(*) from productos where id_producto = p_id) = 0 then
		select 0 as mensaje;
	else
		select 1 as mensaje;
	end if;

end

//

create procedure existe_producto(in p_id int, in c_id int)

begin

	if(select count(*) from productos where id_producto = p_id and id_categoria = c_id) = 0 then
		select 0 as mensaje;
	else
		select 1 as mensaje;
	end if;

end

//

create procedure obtener_productos(in c_id int)

begin

	select * from productos where id_categoria = c_id;
	
end

//

create procedure nuevo_producto(
	in descr varchar(200),
	in c_compra int,
	in c_venta int,
	in inven int,
	in imag varchar(200),
	in c_id int
)

begin

	declare n_id int;
	
	if (select count(*) from productos) = 0 then
		set n_id = 1;
	else
		set n_id = (select max(id_producto) + 1 from productos);
	end if;
	insert into productos(id_producto, descripcion, costo_compra, costo_venta, imagen, inventario, id_categoria)
	values(n_id, descr, c_compra, c_venta, imag, inven, c_id);
	select 'Registro exitoso' as mensaje;

end

//

create procedure obtener_datos_producto(in p_id int)

begin

	select descripcion, costo_compra, costo_venta, imagen, inventario from productos where id_producto = p_id;

end

//

create procedure modificar_producto(
	in p_id int,
	in descr varchar(200),
	in c_compra int,
	in c_venta int,
	in inven int,
	in imag varchar(200)
)

begin

	declare n_id int;
	
	if (select count(*) from productos) = 0 then
		set n_id = 1;
	else
		set n_id = (select max(id_producto) + 1 from productos);
	end if;
	update productos set descripcion = descr, costo_compra = c_compra, costo_venta = c_venta, imagen = imag, inventario = inven where id_producto = p_id;
	select 'Registro exitoso' as mensaje;

end

//

create procedure obtener_id(
	in u_email varchar(200)
)

begin
	

	select id_cliente from clientes where email = u_email;

end

//

create procedure obtener_id_categoria(
	in u_nom varchar(200)
)

begin
	

	select id_categoria from categorias where nombre = u_nom;

end

//

create procedure comprar(in id_c int, in id_p int, in cant int)

begin
	declare n_id int;

	
	
	if (select inventario from productos where id_producto = id_p) = 0 or  (select inventario from productos where id_producto = id_p) < cant then
		select 0 as mensaje;
	else
		if (select count(*) from compras) = 0 then
			set n_id = 1;
		else
			set n_id = (select max(id_compra) + 1 from compras);
		end if;
		insert into compras(id_compra, id_cliente, id_producto, cantidad, fecha)
		values (n_id, id_c, id_p, cant, current_timestamp()); 
		update productos set inventario = inventario - cant where id_producto = id_p;
		select 1 as mensaje;
	end if;

end

//

delimiter ;