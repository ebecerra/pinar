/*!40101 SET character_set_client = utf8 */;

--
-- Table structure for table `APPEX_IDIOMAS`
--
CREATE TABLE `APPEX_IDIOMAS` (
  `ID_IDIOMA` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Codigo del idioma',
  `NOMBRE` varchar(30) NOT NULL DEFAULT '' COMMENT 'Nombre',
  `ABREVIATURA` char(3) NOT NULL DEFAULT '' COMMENT 'Abreviatura',
  `ISO_2` char(2) NOT NULL DEFAULT '' COMMENT 'Codigo ISO 2',
  `ISO_3` char(3) NOT NULL DEFAULT '' COMMENT 'Codigo ISO 3',
  `ACTIVO` bit(1) DEFAULT NULL COMMENT 'Indica si esta activo',
  `LOGO` varchar(45) DEFAULT NULL COMMENT 'Logo de idioma',
  PRIMARY KEY (`ID_IDIOMA`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Idiomas';

--
-- Table structure for table `APPEX_TRADUCCIONES`
--
CREATE TABLE `APPEX_TRADUCCIONES` (
  `ID_IDIOMA` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Codigo del idioma',
  `ID_TABLA` int(11) NOT NULL DEFAULT '0' COMMENT 'Codigo de la tabla',
  `ROWKEY` varchar(100) NOT NULL DEFAULT '' COMMENT 'Llave de la fila',
  `TEXTO` text NOT NULL COMMENT 'Traduccion',
  PRIMARY KEY (`ID_IDIOMA`,`ID_TABLA`,`ROWKEY`),
  KEY `fk_APPEX_TRADUCCIONES_2` (`ID_TABLA`),
  CONSTRAINT `fk_APPEX_TRADUCCIONES_2` FOREIGN KEY (`ID_TABLA`) REFERENCES `APPBS_TABLAS` (`ID_TABLA`),
  CONSTRAINT `fk_APPEX_TRADUCCIONES_1` FOREIGN KEY (`ID_IDIOMA`) REFERENCES `APPEX_IDIOMAS` (`ID_IDIOMA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Traducciones de la aplicacion';

--
-- Table structure for table `APPEX_PAISES`
--
CREATE TABLE `APPEX_PAISES` (
  `ID_PAIS` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id. del pais',
  `NOMBRE` varchar(45) CHARACTER SET latin1 NOT NULL COMMENT 'Nombre',
  `ISO_CODE_2` varchar(2) DEFAULT NULL COMMENT 'Codigo ISO 2',
  `ISO_CODE_3` varchar(3) DEFAULT NULL COMMENT 'Codigo ISO 3',
  `ACTIVO` varchar(45) DEFAULT NULL COMMENT 'Indica si esta activo',
  `G_CODE` varchar(100) DEFAULT NULL COMMENT 'Codigo asignado por Google Maps',
  PRIMARY KEY (`ID_PAIS`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Paises';

--
-- Table structure for table `APPEX_COMUNIDADES`
--
CREATE TABLE `APPEX_COMUNIDADES` (
  `ID_COMUNIDAD` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id. de la comunidad',
  `ID_PAIS` int(10) unsigned NOT NULL COMMENT 'Id. del pais',
  `NOMBRE` varchar(100) NOT NULL COMMENT 'Nombre',
  `G_CODE` varchar(45) DEFAULT NULL COMMENT 'Codigo asignado por Google Maps',
  PRIMARY KEY (`ID_COMUNIDAD`),
  KEY `fk_APPEX_COMUNIDADES_1` (`ID_PAIS`),
  CONSTRAINT `fk_APPEX_COMUNIDADES_1` FOREIGN KEY (`ID_PAIS`) REFERENCES `APPEX_PAISES` (`ID_PAIS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Comunidades / Regiones';

--
-- Table structure for table `APPEX_PROVINCIAS`
--
CREATE TABLE `APPEX_PROVINCIAS` (
  `ID_PAIS` int(10) unsigned NOT NULL COMMENT 'Id. del pais',
  `ID_PROVINCIA` int(10) unsigned NOT NULL COMMENT 'Id. de la provincia',
  `ID_COMUNIDAD` int(11) unsigned DEFAULT NULL,
  `NOMBRE` varchar(45) CHARACTER SET latin1 NOT NULL COMMENT 'Nombre',
  `CP_PREFIX` varchar(5) DEFAULT NULL COMMENT 'Prefijo de los codigos postales',
  `G_CODE` varchar(100) DEFAULT NULL COMMENT 'Codigo asignado por Google Maps',
  PRIMARY KEY (`ID_PAIS`,`ID_PROVINCIA`),
  KEY `fk_APPEX_PROVINCIAS_1` (`ID_PAIS`),
  KEY `fk_APPEX_PROVINCIAS_2` (`ID_PAIS`,`ID_COMUNIDAD`),
  CONSTRAINT `fk_APPEX_PROVINCIAS_2` FOREIGN KEY (`ID_PAIS`, `ID_COMUNIDAD`) REFERENCES `APPEX_COMUNIDADES` (`ID_PAIS`, `ID_COMUNIDAD`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_APPEX_PROVINCIAS_1` FOREIGN KEY (`ID_PAIS`) REFERENCES `APPEX_PAISES` (`ID_PAIS`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Provincias';


--
-- Table structure for table `APPEX_IVA`
--
CREATE TABLE `APPEX_IVA` (
  `ID_IVA` int(10) unsigned NOT NULL auto_increment COMMENT 'Id. del IVA',
  `NOMBRE` varchar(45) character set latin1 NOT NULL COMMENT 'Nombre',
  `VALOR` double NOT NULL COMMENT 'Valor (%)',
  `RECARGO` double default NULL COMMENT 'Recargo de equivalencia (%)',
  PRIMARY KEY  (`ID_IVA`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Iva';

--
-- Table structure for table `APPEX_DIRECCIONES`
--
CREATE TABLE `APPEX_DIRECCIONES` (
  `ID_DIRECCION` int(10) unsigned NOT NULL auto_increment COMMENT 'Id. de la direccion',
  `ID_PAIS` int(10) unsigned NOT NULL COMMENT 'Id. del pais',
  `ID_PROVINCIA` int(10) unsigned NOT NULL COMMENT 'Id. de la provincia',
  `DIRECCION` varchar(200) character set latin1 NOT NULL COMMENT 'Direccion',
  `POBLACION` varchar(45) character set latin1 NOT NULL COMMENT 'Poblacion',
  `CP` varchar(20) character set latin1 NOT NULL COMMENT 'Codigo postal',
  `OBSERVACIONES` varchar(200) character set latin1 default NULL COMMENT 'Observaciones',
  `ACTIVA` char(1) character set latin1 NOT NULL COMMENT 'Indica si la direccion esta activa',
  `FACTURACION` char(1) character set latin1 NOT NULL COMMENT 'Indica si es direccion de facturacion',
  `FISCAL` char(1) character set latin1 NOT NULL COMMENT 'Indica si es direccion fiscal',
  `ENVIO` char(1) character set latin1 NOT NULL COMMENT 'Indica si es direccion de envio',
  `PRINCIPAL` char(1) character set latin1 NOT NULL COMMENT 'Indica si es la direccion principal',
  PRIMARY KEY  (`ID_DIRECCION`),
  KEY `FK_DIRECCIONES_1` (`ID_PAIS`,`ID_PROVINCIA`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COMMENT='Direcciones'

--
-- Table structure for table `APPEX_CONTACTOS`
--
CREATE TABLE `APPEX_CONTACTOS` (
  `ID_CONTACTO` int(10) unsigned NOT NULL auto_increment COMMENT 'Id. del contacto',
  `NOMBRE` varchar(45) character set latin1 NOT NULL COMMENT 'Nombre',
  `TELEFONO` varchar(45) character set latin1 default NULL COMMENT 'Telefono',
  `MOVIL` varchar(45) character set latin1 default NULL COMMENT 'Movil',
  `FAX` varchar(45) character set latin1 default NULL COMMENT 'Fax',
  `EMAIL` varchar(45) character set latin1 default NULL COMMENT 'Correo electronico',
  `OBSERVACIONES` varchar(2000) character set latin1 default NULL COMMENT 'Observaciones',
  `ACTIVO` char(1) character set latin1 NOT NULL COMMENT 'Indica si esta activo',
  PRIMARY KEY  (`ID_CONTACTO`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='Contactos';

--
-- Table structure for table `APPEX_BANCOS`
--
CREATE TABLE `APPEX_BANCOS` (
  `ID_BANCO` varchar(4) character set latin1 NOT NULL COMMENT 'Id. del banco',
  `NOMBRE` varchar(100) character set latin1 NOT NULL COMMENT 'Nombre',
  `ACTIVO` char(1) character set latin1 NOT NULL COMMENT 'Activo (S/N)',
  PRIMARY KEY  (`ID_BANCO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Bancos y cajas';

--
-- Table structure for table `APPEX_CUENTAS_BANCARIAS`
--
CREATE TABLE `APPEX_CUENTAS_BANCARIAS` (
  `ID_CUENTA` int(10) unsigned NOT NULL auto_increment COMMENT 'Id. de la cuenta',
  `ENTIDAD` varchar(4) character set latin1 NOT NULL default '0' COMMENT 'Entidad',
  `SUCURSAL` varchar(4) character set latin1 NOT NULL default '0' COMMENT 'Sucursal',
  `DC` varchar(2) character set latin1 NOT NULL default '0' COMMENT 'Digito de control',
  `CUENTA` varchar(45) character set latin1 NOT NULL default '0' COMMENT 'Cuenta',
  `IBAN` varchar(60) character set latin1 default NULL COMMENT 'Numero IBAN',
  `NOMBRE` varchar(45) character set latin1 default NULL COMMENT 'Descripcion de la cuenta',
  `ACTIVA` bit(1) NOT NULL COMMENT 'Indica si esta activa',
  PRIMARY KEY  (`ID_CUENTA`),
  KEY `FK_CUENTAS_BANCARIAS_2` (`ENTIDAD`),
  KEY `fk_APPEX_CUENTAS_BANCARIAS_1` (`ENTIDAD`),
  CONSTRAINT `fk_APPEX_CUENTAS_BANCARIAS_1` FOREIGN KEY (`ENTIDAD`) REFERENCES `APPEX_BANCOS` (`ID_BANCO`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='Cuentas bancarias';






