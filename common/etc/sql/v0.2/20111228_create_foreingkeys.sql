CREATE TABLE `APPBS_FOREINGKEYS` (
  `ID_KEY` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id. de la llave',
  `NOMBRE` varchar(100) NOT NULL COMMENT 'Nombre',
  `EXPORTABLE` bit(1) DEFAULT NULL COMMENT 'Indica si se utiliza en los procesos de exportacion',
  `ID_TABLA` int(11) DEFAULT NULL COMMENT 'Id. de la tabla',
  PRIMARY KEY (`ID_KEY`),
  KEY `fk_APPBS_FOREINGKEYS_1` (`ID_TABLA`),
  CONSTRAINT `fk_APPBS_FOREINGKEYS_1` FOREIGN KEY (`ID_TABLA`) REFERENCES `APPBS_TABLAS` (`ID_TABLA`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Informacion de FOREING KEYS';

CREATE TABLE `APPBS_FOREINGKEYS_VALUES` (
  `ID_KEY` int(11) NOT NULL COMMENT 'Id. de la llave',
  `ID_TABLA` int(11) NOT NULL COMMENT 'Id. de la tabla',
  `ID_CAMPO` int(11) NOT NULL COMMENT 'Id. del campo',
  `ID_REFTABLA` int(11) NOT NULL COMMENT 'Id. de la tabla que referencia',
  `ID_REFCAMPO` int(11) NOT NULL COMMENT 'Id. del campo que referencia',
  PRIMARY KEY (`ID_KEY`,`ID_TABLA`,`ID_CAMPO`),
  KEY `fk_APPBS_FOREINGKEYS_VALUES_2` (`ID_TABLA`,`ID_CAMPO`),
  KEY `fk_APPBS_FOREINGKEYS_VALUES_3` (`ID_REFTABLA`,`ID_REFCAMPO`),
  CONSTRAINT `fk_APPBS_FOREINGKEYS_VALUES_3` FOREIGN KEY (`ID_REFTABLA`, `ID_REFCAMPO`) REFERENCES `APPBS_CAMPOS` (`ID_TABLA`, `ID_CAMPO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_APPBS_FOREINGKEYS_VALUES_1` FOREIGN KEY (`ID_KEY`) REFERENCES `APPBS_FOREINGKEYS` (`ID_KEY`),
  CONSTRAINT `fk_APPBS_FOREINGKEYS_VALUES_2` FOREIGN KEY (`ID_TABLA`, `ID_CAMPO`) REFERENCES `APPBS_CAMPOS` (`ID_TABLA`, `ID_CAMPO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Valores de los FOREING KEYS';



