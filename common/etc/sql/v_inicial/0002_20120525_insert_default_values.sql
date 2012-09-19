SET @APPNAME='APPNAME';
SET @APPDESC='Descripcion de la aplicacion';

INSERT INTO `APPBS_APLICACIONES` VALUES (@APPNAME, @APPDESC);

INSERT INTO `APPBS_PARAMETROS` VALUES
(@APPNAME,'DBLOG_LEVEL','Nivel de Log en la DB','44','I',1,5,0,0,NULL,NULL,'S','N','N'),
(@APPNAME,'PASSWORD_CHECK','Nivel de verificación de las claves','4','E',0,0,0,0,NULL,NULL,'N','S','S'),
(@APPNAME,'PASSWORD_MAX','Longitud máxima','20','I',10,45,0,0,NULL,NULL,'S','S','S'),
(@APPNAME,'PASSWORD_MIN','Longitud minima','6','I',4,10,0,0,NULL,NULL,'S','S','S'),
(@APPNAME,'PASSWORD_STORE','Cantidad de claves diferentes a recordar','3','I',0,0,0,0,NULL,NULL,'N','S','S'),
(@APPNAME,'PASSWORD_USER','Indica si el usuario puede cambiar el Login','1','E',0,0,0,0,NULL,NULL,'N','S','S'),
(@APPNAME,'PASSWORD_VALID','Cantidad de días que la clave es valida','0','I',10,0,0,0,NULL,NULL,'N','S','S'),
(@APPNAME,'SMTP_FROM','SMTP: Direccion desde la que se envia los mail','cuenta@server.com','S',0,0,0,0,NULL,NULL,'N','N','S'),
(@APPNAME,'SMTP_PASSWD','SMTP: Clave del servidor','password','P',0,0,0,0,NULL,NULL,'N','N','N'),
(@APPNAME,'SMTP_PORT','SMTP: Puerto del servidor','25','S',0,0,0,0,NULL,NULL,'N','N','S'),
(@APPNAME,'SMTP_SERVER','SMTP: Servidor de correo','mail.server.com','S',0,0,0,0,NULL,NULL,'N','N','S'),
(@APPNAME,'SMTP_USER','SMTP: Usuario del servidor','mail_user','S',0,0,0,0,NULL,NULL,'N','N','S');

INSERT INTO `APPBS_PARAMETROS_VALORES` VALUES 
(@APPNAME,'PASSWORD_CHECK',0,'Ninguno'),
(@APPNAME,'PASSWORD_CHECK',1,'Bajo'),
(@APPNAME,'PASSWORD_CHECK',2,'Medio'),
(@APPNAME,'PASSWORD_CHECK',3,'Alto'),
(@APPNAME,'PASSWORD_CHECK',4,'Muy Alto'),
(@APPNAME,'PASSWORD_USER',1,'Sí'),
(@APPNAME,'PASSWORD_USER',2,'No');

INSERT INTO APPBS_PERFILES (ID_PERFIL, NOMBRE, TIPO) VALUES ('1', 'Administrador del Sistema', 'MANAGER');
INSERT INTO APPBS_USUARIOS_TIPOS (ID_TIPO, NOMBRE, DESCRIPCION) VALUES ('1', 'Administrador', 'Administrador del Sistema');
INSERT INTO APPBS_USUARIOS (ID_USUARIO, NOMBRE, LOGIN, CLAVE, TIPO, ACTIVO, FECHA_CREACION) VALUES (1, 'Administrador del sistema', 'root', '7110eda4d09e062aa5e4a390b0a572ac0d2c0220', '1', 'S', sysdate());
INSERT INTO APPBS_USUARIOS_PERFILES (ID_USUARIO, ID_APLICACION, ID_PERFIL) VALUES (1, @APPNAME, 1);

INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN', 'Administración', '100', 'MAIN_MENU', 'N');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_PERFIL', 'Perfiles', '10', 'APPBS_ADMIN', 'L');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_USER', 'Usuarios', '20', 'APPBS_ADMIN', 'L');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_PARAM', 'Parámetros', '30', 'APPBS_ADMIN', 'L');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_PASSWD', 'Cambiar clave', '40', 'APPBS_ADMIN', 'L');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_LOOKUPS', 'Lookups', '50', 'APPBS_ADMIN', 'L');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LEG', 'Leyenda del log', '60', 'APPBS_ADMIN', 'L');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LOG', 'Log de usuarios', '70', 'APPBS_ADMIN', 'L');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_LOG', 'Logs', '80', 'APPBS_ADMIN', 'L');
INSERT INTO APPBS_MENU (ID_APLICACION, ID_MENU, NOMBRE, ORDEN, ID_PADRE, TIPO_ENLACE) VALUES (@APPNAME, 'APPBS_ADMIN_SESSION', 'Sesiones activas', '90', 'APPBS_ADMIN', 'L');

INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_PERFIL', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_USER', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_PARAM', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_PASSWD', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_LOOKUPS', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LEG', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LOG', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_LOG', '1');
INSERT INTO APPBS_MENU_PERFILES (ID_APLICACION, ID_MENU, ID_PERFIL) VALUES (@APPNAME, 'APPBS_ADMIN_SESSION', '1');

INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('1', 'Error genérico', 'ERROR: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('10', 'Error de actualización', 'Error actualizando la tabla: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('11', 'Error de borrado', 'Error borrando la tabla: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('12', 'Error de consulta', 'Error consultado la tabla: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('100', 'Actualización de datos', 'Actualizando registro en $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('101', 'Borrado de datos', 'Borrando registro en $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('102', 'Consulta de datos', 'Consultando la tabla $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('250', 'Email plantilla incorrecta', 'No se encuentra la plantilla de email: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('251', 'Email enviado', 'Email para: $1, asunto $2');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('252', 'Email error de envío', 'Error al enviar email a $1, asunto $2');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('300', 'Inicio de sesión', 'El usuario se ha conectado');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('301', 'Cierre de sessión', 'El usuario se ha desconectado');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('500', 'Direcciones - Cargar', 'Carga la dirección: $1, Tabla: $2');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('501', 'Direcciones - Modificar', 'Modifica la dirección: $1, Tabla: $2, Operación: $3');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('510', 'Cuenta bancaria - Cargar', 'Carga la cuenta bancaria: $1, Tabla: $2');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('511', 'Cuenta bancaria - Modificar', 'Modifica la cuenta bancaria: $1, Tabla: $2, Operación: $3');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('520', 'IVA - Listar', 'Lista el I.V.A');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('521', 'IVA - Modificar', 'Modifica el I.V.A.: $1, Operación: $2');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('530', 'Contactos - Cargar', 'Carga el contacto: $1, Tabla: $2');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('531', 'Contactos - Modificar', 'Modifica el contacto: $1, Operación: $2, Tabla: $3');
