ALTER TABLE `APPBS_USUARIOS`
ADD COLUMN `FECHA_CREACION` DATETIME NULL COMMENT 'Fecha de creacion'  AFTER `TIPO` ,
ADD COLUMN `FECHA_CONEXION` DATETIME NULL COMMENT 'Fecha de la ultima conexion'  AFTER `FECHA_CREACION` ,
ADD COLUMN `ACTIVO` CHAR(1) NULL COMMENT 'Indica si el usario esta activo'  AFTER `FECHA_CONEXION` ;

UPDATE `APPBS_USUARIOS` SET ACTIVO =  'S';

ALTER TABLE `APPBS_USUARIOS_PERFILES` DROP FOREIGN KEY `fk_APPBS_USUARIOS_PERFILES_1` ;
ALTER TABLE `APPBS_USUARIOS_PERFILES` ADD CONSTRAINT `fk_APPBS_USUARIOS_PERFILES_1`
  FOREIGN KEY (`ID_USUARIO` ) REFERENCES `APPBS_USUARIOS` (`ID_USUARIO` ) ON DELETE CASCADE ON UPDATE CASCADE;
