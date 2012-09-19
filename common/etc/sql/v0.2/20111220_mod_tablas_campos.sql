ALTER TABLE `APPBS_TABLAS` ADD COLUMN `SINCRO` BIT(1) NULL COMMENT 'Bandera que indica si se sincroniza con app. clientes'  AFTER `REPLICA` ;
ALTER TABLE `APPBS_TABLAS` ADD COLUMN `CREACION` BIT(1) NULL COMMENT 'Bandera que indica si se crea en el cliente'  AFTER `SINCRO` ;
ALTER TABLE `APPBS_TABLAS` ADD COLUMN `ORDEN` INT(11) NULL COMMENT 'Orden de procesamiento de la tabla'  AFTER `CREACION` ;

ALTER TABLE `APPBS_CAMPOS` ADD COLUMN `FUENTE` CHAR(1) NULL COMMENT 'Fuente de datos (B - Base de datos, C - Constante, P - Parametro, M - Maximo columna, F - Funcion, S - Select)'  AFTER `ORG_FORMATO` ;
ALTER TABLE `APPBS_CAMPOS` ADD COLUMN `FUENTE_VALOR1` VARCHAR(500) NULL COMMENT 'Valor 1 segun la fuente'  AFTER `FUENTE` ;
ALTER TABLE `APPBS_CAMPOS` ADD COLUMN `FUENTE_VALOR2` VARCHAR(500) NULL COMMENT 'Valor 2 segun la fuente'  AFTER `FUENTE_VALOR1` ;
ALTER TABLE `APPBS_CAMPOS` ADD COLUMN `EXPORTABLE` BIT(1) NULL COMMENT 'Indica si el campo se utiliza en los exports' AFTER `FUENTE_VALOR2` ;

--
-- 02/01/2012
--
ALTER TABLE `APPBS_TABLAS` ADD COLUMN `VERSION` VARCHAR(45) NULL COMMENT 'Version de la tabla'  AFTER `ORDEN` ;

--
-- 09/01/2012
--
ALTER TABLE `APPBS_TABLAS` CHANGE COLUMN `VERSION` `VERSION` INT(11) NULL DEFAULT NULL COMMENT 'Version de la tabla'  ;
--
-- 29/01/2012
--
ALTER TABLE `APPBS_TABLAS` ADD COLUMN `EXPORTABLE` BIT(1) NULL COMMENT 'Indica si la tabla es exportable '  AFTER `CREACION` ,
ADD COLUMN `IMPORTABLE` BIT(1) NULL COMMENT 'Indica si la tabla es importable'  AFTER `EXPORTABLE` ;
ALTER TABLE `APPBS_CAMPOS` ADD COLUMN `IMPORTABLE` BIT(1) NULL COMMENT 'Indica si el campo es impotable'  AFTER `EXPORTABLE` ;