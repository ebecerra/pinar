ALTER TABLE `APPBS_USUARIOS_LOG` ADD COLUMN `SESION_ID` VARCHAR(50) NULL COMMENT 'Id. de la sesion'  AFTER `NIVEL` ;

CREATE INDEX fk_APPBS_USUARIOS_LOG_4 ON `APPBS_USUARIOS_LOG` (FECHA);
CREATE INDEX fk_APPBS_USUARIOS_LOG_5 ON `APPBS_USUARIOS_LOG` (SESION_ID);

ALTER TABLE `APPBS_USUARIOS` ADD COLUMN `LOG_LEVEL` INT(10) UNSIGNED NULL COMMENT 'Nivel de log'  AFTER `ACTIVO`;

INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('1', 'Error genérico', 'ERROR: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('200', 'Usuario correcto', 'Ha entrado el usuario $1 - $2 ($3)');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('250', 'Email plantilla incorrecta', 'No se encuentra la plantilla de email: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('251', 'Email enviado', 'Email para: $1, asunto $2');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('252', 'Email error de envío', 'Error al enviar email a $1, asunto $2');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('10', 'Error de actualización', 'Error actualizando la tabla: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('11', 'Error de borrado', 'Error borrando la tabla: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('12', 'Error de consulta', 'Error consultado la tabla: $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('100', 'Actualización de datos', 'Actualizando registro en $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('101', 'Borrado de datos', 'Borrando registro en $1');
INSERT INTO `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`, `NOMBRE`, `TEXTO`) VALUES ('102', 'Consulta de datos', 'Consultando la tabla $1');

SET @APPNAME='APPNAME';

INSERT INTO `APPBS_MENU` (`ID_APLICACION`, `ID_MENU`, `NOMBRE`, `ORDEN`, `ID_PADRE`, `TIPO_ENLACE`) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LEG', 'Leyenda del log ', '110', 'APPBS_ADMIN', 'L');
INSERT INTO `APPBS_MENU` (`ID_APLICACION`, `ID_MENU`, `NOMBRE`, `ORDEN`, `ID_PADRE`, `TIPO_ENLACE`) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LOG', 'Log de usuarios', '100', 'APPBS_ADMIN', 'L');

INSERT INTO `APPBS_MENU_PERFILES` (`ID_APLICACION`, `ID_MENU`, `ID_PERFIL`) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LEG', '1');
INSERT INTO `APPBS_MENU_PERFILES` (`ID_APLICACION`, `ID_MENU`, `ID_PERFIL`) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LOG', '2');
INSERT INTO `APPBS_MENU_PERFILES` (`ID_APLICACION`, `ID_MENU`, `ID_PERFIL`) VALUES (@APPNAME, 'APPBS_ADMIN_USR_LOG', '1');