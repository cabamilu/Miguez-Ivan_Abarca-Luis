insert into odontologos (nombre, apellido, numero_matricula) values ('Juan', 'Perez', 12345678), ('Andres', 'Salgado', 12345679)
insert into domicilios (calle, numero, localidad, provincia) values ('Los Alerces', 2884, 'Puerto Montt', 'Los Lagos'), ('Las Gaviotas', 4321, 'La Cisterna', 'Santiago')
insert into pacientes (nombre, apellido, dni, fecha_ingreso, domicilio_id) values ('Mario', 'Fernandez', 1122334455, '2023-11-01', 1), ('Pedro', 'Quiroz', 77442211, '2023-10-05', 2)
insert into turnos (fechayhora, odontologo_id, paciente_id) values ('2023-11-30T15:10', 1, 1)
