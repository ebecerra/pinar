/*!40101 SET character_set_client = utf8 */;

--
-- Table structure for table `APPBS_APLICACIONES`
--
CREATE TABLE `APPBS_APLICACIONES` (
  `ID_APLICACION` varchar(10) NOT NULL DEFAULT '' COMMENT 'Codigo',
  `NOMBRE` varchar(45) NOT NULL DEFAULT '' COMMENT 'Nombre',
  PRIMARY KEY (`ID_APLICACION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Aplicaciones';


--
-- Table structure for table `APPBS_PERFILES`
--
CREATE TABLE `APPBS_PERFILES` (
  `ID_PERFIL` int(11) unsigned NOT NULL DEFAULT '0',
  `NOMBRE` varchar(100) DEFAULT NULL,
  `TIPO` varchar(45) DEFAULT NULL COMMENT 'Tipo del perfil',
  PRIMARY KEY (`ID_PERFIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Perfiles de usuarios';


--
-- Table structure for table `APPBS_USUARIOS_TIPOS`
--
CREATE TABLE `APPBS_USUARIOS_TIPOS` (
  `ID_TIPO` int(11) unsigned NOT NULL COMMENT 'Id. del tipo de usuario',
  `NOMBRE` varchar(45) NOT NULL COMMENT 'Nombre del tipo',
  `DESCRIPCION` varchar(150) DEFAULT NULL COMMENT 'Descripcion',
  PRIMARY KEY (`ID_TIPO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tipos de usuarios';

--
-- Table structure for table `APPBS_USUARIOS`
--
CREATE TABLE `APPBS_USUARIOS` (
  `ID_USUARIO` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id. del usuario',
  `NOMBRE` varchar(45) NOT NULL COMMENT 'Nombre',
  `LOGIN` varchar(20) NOT NULL DEFAULT ' ' COMMENT 'Login',
  `CLAVE` varchar(45) NOT NULL DEFAULT ' ' COMMENT 'Clave',
  `FAX` varchar(45) DEFAULT NULL COMMENT 'Numero de FAX',
  `TELEFONO` varchar(45) DEFAULT NULL COMMENT 'Telefono',
  `MOVIL` varchar(45) DEFAULT NULL COMMENT 'Movil',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT 'Email',
  `TIPO` int(11) unsigned DEFAULT NULL COMMENT 'Tipo de usuario',
  `FECHA_CREACION` datetime DEFAULT NULL COMMENT 'Fecha de creacion',
  `FECHA_CONEXION` datetime DEFAULT NULL COMMENT 'Fecha de la ultima conexion',
  `ACTIVO` char(1) DEFAULT NULL COMMENT 'Indica si el usario esta activo',
  `LOG_LEVEL` int(10) unsigned DEFAULT NULL COMMENT 'Nivel de log',
  `COOKIE` varchar(45) DEFAULT NULL COMMENT 'Cookie del usuario',
  `ID_IDIOMA` int(11) unsigned DEFAULT NULL COMMENT 'Id. del ultimo idioma utilizado',
  `PARAM_1` varchar(45) DEFAULT NULL COMMENT 'Parametros adicionales 1',
  `PARAM_2` varchar(45) DEFAULT NULL COMMENT 'Parametros adicionales 2',
  `PARAM_3` varchar(45) DEFAULT NULL COMMENT 'Parametros adicionales 3',
  `PARAM_4` varchar(45) DEFAULT NULL COMMENT 'Parametros adicionales 4',
  `INT_PARAM_1` int(10) unsigned DEFAULT NULL COMMENT 'Parametros adicionales enteros 1',
  `INT_PARAM_2` int(10) unsigned DEFAULT NULL COMMENT 'Parametros adicionales enteros 2',
  `INT_PARAM_3` int(10) unsigned DEFAULT NULL COMMENT 'Parametros adicionales enteros 3',
  `INT_PARAM_4` int(10) unsigned DEFAULT NULL COMMENT 'Parametros adicionales enteros 4',
  PRIMARY KEY (`ID_USUARIO`),
  KEY `fk_APPBS_USUARIOS_1` (`TIPO`),
  CONSTRAINT `fk_APPBS_USUARIOS_1` FOREIGN KEY (`TIPO`) REFERENCES `APPBS_USUARIOS_TIPOS` (`ID_TIPO`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8 COMMENT='Usuarios';

--
-- Table structure for table `APPBS_USUARIOS_PERFILES`
--
CREATE TABLE `APPBS_USUARIOS_PERFILES` (
  `ID_USUARIO` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Codigo de la aplicacion',
  `ID_APLICACION` varchar(10) NOT NULL DEFAULT '' COMMENT 'Codigo de la aplicacion',
  `ID_PERFIL` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Codigo del perfil',
  PRIMARY KEY (`ID_USUARIO`,`ID_APLICACION`,`ID_PERFIL`),
  KEY `FK_usuarios_perfiles_1` (`ID_APLICACION`) USING BTREE,
  KEY `FK_usuarios_perfiles_2` (`ID_PERFIL`) USING BTREE,
  CONSTRAINT `fk_APPBS_USUARIOS_PERFILES_1` FOREIGN KEY (`ID_USUARIO`) REFERENCES `APPBS_USUARIOS` (`ID_USUARIO`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_APPBS_USUARIOS_PERFILES_2` FOREIGN KEY (`ID_APLICACION`) REFERENCES `APPBS_APLICACIONES` (`ID_APLICACION`),
  CONSTRAINT `fk_APPBS_USUARIOS_PERFILES_3` FOREIGN KEY (`ID_PERFIL`) REFERENCES `APPBS_PERFILES` (`ID_PERFIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Applicacion - Usuario - Pefil';


--
-- Table structure for table `APPBS_MENU`
--
CREATE TABLE `APPBS_MENU` (
  `ID_APLICACION` varchar(10) NOT NULL DEFAULT '' COMMENT 'Codigo de la aplicacion',
  `ID_MENU` varchar(20) NOT NULL DEFAULT '' COMMENT 'Codigo',
  `NOMBRE` varchar(45) NOT NULL DEFAULT '' COMMENT 'Nombre',
  `ORDEN` int(10) unsigned NOT NULL DEFAULT '0' COMMENT 'Orden del menu',
  `ID_PADRE` varchar(20) DEFAULT NULL COMMENT 'Codigo del menu padre',
  `ENLACE` varchar(100) DEFAULT NULL COMMENT 'Enlace',
  `TIPO_ENLACE` char(1) NOT NULL DEFAULT 'L' COMMENT 'Tipo del enlace (N - Sin enlace, L - Local, S- Server, A - Absoluto)',
  `ATRIBUTO1` varchar(100) DEFAULT NULL COMMENT 'Atributo 1',
  `ATRIBUTO2` varchar(100) DEFAULT NULL COMMENT 'Atributo 2',
  `ATRIBUTO3` varchar(100) DEFAULT NULL COMMENT 'Atributo 3',
  `ATRIBUTO4` varchar(100) DEFAULT NULL COMMENT 'Atributo 4',
  `ATRIBUTO5` varchar(100) DEFAULT NULL COMMENT 'Atributo 5',
  `ATRIBUTO6` varchar(100) DEFAULT NULL COMMENT 'Atributo 6',
  `ATRIBUTO7` varchar(100) DEFAULT NULL COMMENT 'Atributo 7',
  `ATRIBUTO8` varchar(100) DEFAULT NULL COMMENT 'Atributo 8',
  `ATRIBUTO9` varchar(100) DEFAULT NULL COMMENT 'Atributo 9',
  `ATRIBUTO10` varchar(100) DEFAULT NULL COMMENT 'Atributo 10',
  PRIMARY KEY (`ID_APLICACION`,`ID_MENU`),
  CONSTRAINT `fk_MENU_1` FOREIGN KEY (`ID_APLICACION`) REFERENCES `APPBS_APLICACIONES` (`ID_APLICACION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Elementos del menu';

--
-- Table structure for table `APPBS_MENU_PERFILES`
--
CREATE TABLE `APPBS_MENU_PERFILES` (
  `ID_APLICACION` varchar(10) NOT NULL DEFAULT '' COMMENT 'Codigo de la aplicacion',
  `ID_MENU` varchar(20) NOT NULL DEFAULT '' COMMENT 'Codigo del menu',
  `ID_PERFIL` int(11) unsigned NOT NULL DEFAULT '0' COMMENT 'Codigo del perfil',
  PRIMARY KEY (`ID_APLICACION`,`ID_MENU`,`ID_PERFIL`),
  KEY `FK_MENU_PERFILES_2` (`ID_PERFIL`) USING BTREE,
  CONSTRAINT `fk_APPBS_MENU_PERFILES_1` FOREIGN KEY (`ID_APLICACION`, `ID_MENU`) REFERENCES `APPBS_MENU` (`ID_APLICACION`, `ID_MENU`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_APPBS_MENU_PERFILES_2` FOREIGN KEY (`ID_PERFIL`) REFERENCES `APPBS_PERFILES` (`ID_PERFIL`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Opciones de menu por perfil';

--
-- Table structure for table `APPBS_USUARIOS_HIST_CLAVES`
--
CREATE TABLE `APPBS_USUARIOS_HIST_CLAVES` (
  `ID_USUARIO` int(11) unsigned NOT NULL COMMENT 'Codigo del usuario',
  `CLAVE` varchar(45) NOT NULL COMMENT 'Clave',
  `FECHA` datetime DEFAULT NULL COMMENT 'Fecha de creacion',
  PRIMARY KEY (`ID_USUARIO`,`CLAVE`),
  KEY `FK_USUARIOS_HIST_CLAVES_1` (`ID_USUARIO`),
  CONSTRAINT `fk_APPBS_USUARIOS_HIST_CLAVES_1` FOREIGN KEY (`ID_USUARIO`) REFERENCES `APPBS_USUARIOS` (`ID_USUARIO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Historico de utilizacion de las claves';

--
-- Table structure for table `APPBS_USUARIOS_LOG_LEGEND`
--
CREATE TABLE `APPBS_USUARIOS_LOG_LEGEND` (
  `ID_TIPO` int(11) NOT NULL COMMENT 'Id. del tipo',
  `NOMBRE` varchar(45) NOT NULL COMMENT 'Nombre del tipo',
  `TEXTO` varchar(200) NOT NULL COMMENT 'Texto descriptivo',
  PRIMARY KEY (`ID_TIPO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Lista con la leyenda del Log';


--
-- Table structure for table `APPBS_USUARIOS_LOG`
--
CREATE TABLE `APPBS_USUARIOS_LOG` (
  `ID_USUARIO_LOG` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Id. de la tabla',
  `ID_USUARIO` int(11) unsigned NOT NULL COMMENT 'Id. del usuario',
  `ID_APLICACION` varchar(10) NOT NULL COMMENT 'Id. de la aplicacion',
  `ID_TIPO` int(11) NOT NULL COMMENT 'Tipo',
  `FECHA` datetime NOT NULL COMMENT 'Fecha del evento',
  `NIVEL` int(11) DEFAULT NULL COMMENT 'Nivel de Log',
  `SESION_ID` varchar(50) DEFAULT NULL COMMENT 'Id. de la sesion',
  `TEXTO1` varchar(200) DEFAULT NULL COMMENT 'Valor de texto 1',
  `TEXTO2` varchar(200) DEFAULT NULL COMMENT 'Valor de texto 2',
  `TEXTO3` varchar(200) DEFAULT NULL COMMENT 'Valor de texto 3',
  `TEXTO4` varchar(200) DEFAULT NULL COMMENT 'Valor de texto 4',
  `TEXTO5` varchar(200) DEFAULT NULL COMMENT 'Valor de texto 5',
  PRIMARY KEY (`ID_USUARIO_LOG`),
  KEY `fk_APPBS_USUARIOS_LOG_1` (`ID_USUARIO`,`ID_APLICACION`),
  KEY `fk_APPBS_USUARIOS_LOG_2` (`ID_TIPO`),
  KEY `fk_APPBS_USUARIOS_LOG_4` (`FECHA`),
  KEY `fk_APPBS_USUARIOS_LOG_5` (`SESION_ID`),
  CONSTRAINT `fk_APPBS_USUARIOS_LOG_1` FOREIGN KEY (`ID_USUARIO`, `ID_APLICACION`) REFERENCES `APPBS_USUARIOS_PERFILES` (`ID_USUARIO`, `ID_APLICACION`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_APPBS_USUARIOS_LOG_2` FOREIGN KEY (`ID_TIPO`) REFERENCES `APPBS_USUARIOS_LOG_LEGEND` (`ID_TIPO`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=188404 DEFAULT CHARSET=utf8 COMMENT='Log por usuarios';

--
-- Table structure for table `APPBS_PARAMETROS`
--
CREATE TABLE `APPBS_PARAMETROS` (
  `ID_APLICACION` varchar(10) NOT NULL COMMENT 'Codigo de la aplicacion',
  `ID_PARAMETRO` varchar(40) NOT NULL COMMENT 'Codigo',
  `NOMBRE` varchar(100) NOT NULL COMMENT 'Nombre',
  `VALOR` varchar(2000) NOT NULL COMMENT 'Valor',
  `TIPO` char(1) DEFAULT 'S' COMMENT 'Tipo (E - Enumerativo, I - Entero, F - Real, S - Cadena, M - Mail, D - Fecha)',
  `MIN_INT` int(11) DEFAULT NULL COMMENT 'Minimo entro',
  `MAX_INT` int(11) DEFAULT NULL COMMENT 'Maximo entero',
  `MIN_FLOAT` float DEFAULT NULL COMMENT 'Minimo real',
  `MAX_FLOAT` float DEFAULT NULL COMMENT 'Maximo real',
  `MIN_DATE` datetime DEFAULT NULL COMMENT 'Minimo fecha',
  `MAX_DATE` datetime DEFAULT NULL COMMENT 'Maximo fecha',
  `CHECKRANGE` char(1) DEFAULT NULL COMMENT 'Indice si se chequean los rangos (S/N)',
  `EDIT` char(1) DEFAULT 'S' COMMENT 'Indica si es editable',
  `VISIBLE` char(1) DEFAULT 'S' COMMENT 'Indica si es visible',
  PRIMARY KEY (`ID_APLICACION`,`ID_PARAMETRO`),
  KEY `FK_PARAMETROS_1` (`ID_APLICACION`),
  CONSTRAINT `fk_PARAMETROS_1` FOREIGN KEY (`ID_APLICACION`) REFERENCES `APPBS_APLICACIONES` (`ID_APLICACION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Parametros generales';

--
-- Table structure for table `APPBS_PARAMETROS_VALORES`
--
CREATE TABLE `APPBS_PARAMETROS_VALORES` (
  `ID_APLICACION` varchar(10) NOT NULL COMMENT 'Codigo de la aplicacion',
  `ID_PARAMETRO` varchar(40) NOT NULL COMMENT 'Codigo del parametro',
  `ID_VALOR` int(11) NOT NULL COMMENT 'Id. del valor',
  `NOMBRE` varchar(45) DEFAULT NULL COMMENT 'Nombre del valor',
  PRIMARY KEY (`ID_APLICACION`,`ID_PARAMETRO`,`ID_VALOR`),
  KEY `FK_PARAMETROS_VALORES_1` (`ID_APLICACION`,`ID_PARAMETRO`),
  CONSTRAINT `fk_APPBS_PARAMETROS_VALORES_1` FOREIGN KEY (`ID_APLICACION`) REFERENCES `APPBS_PARAMETROS` (`ID_APLICACION`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Valores de los parametros';

--
-- Table structure for table `APPBS_TABLAS`
--
CREATE TABLE `APPBS_TABLAS` (
  `ID_TABLA` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Codigo',
  `NOMBRE` varchar(50) NOT NULL DEFAULT '' COMMENT 'Nombre de la tabla',
  `ORIGEN` varchar(50) DEFAULT NULL COMMENT 'Tabla original',
  `REPLICA` varchar(50) DEFAULT NULL COMMENT 'Tabla donde se hace la replica',
  `SINCRO` bit(1) DEFAULT NULL COMMENT 'Bandera que indica si se sincroniza con app. clientes',
  `CREACION` bit(1) DEFAULT NULL COMMENT 'Bandera que indica si se crea en el cliente',
  `EXPORTABLE` bit(1) DEFAULT NULL COMMENT 'Indica si la tabla es exportable ',
  `IMPORTABLE` bit(1) DEFAULT NULL COMMENT 'Indica si la tabla es importable',
  `ORDEN` int(11) DEFAULT NULL COMMENT 'Orden de procesamiento de la tabla',
  `VERSION` int(11) DEFAULT NULL COMMENT 'Version de la tabla',
  `XSELECT` varchar(100) DEFAULT NULL COMMENT 'Modificadores del SELECT',
  `XFROM` varchar(200) DEFAULT NULL COMMENT 'From de la select',
  `XWHERE` varchar(1000) DEFAULT NULL COMMENT 'Where de la select',
  `XGROUP` varchar(1000) DEFAULT NULL COMMENT 'Group by de la select',
  `EXTRA_WHERE` varchar(1000) DEFAULT NULL COMMENT 'Sincronizacion (WHERE)',
  `EXTRA_SELECT` varchar(1000) DEFAULT NULL COMMENT 'Sincronizacion (SELECT)',
  `DELETE_WHERE` varchar(1000) DEFAULT NULL COMMENT 'Condicion para el DELETE',
  `EXTRA_UPDATE` varchar(1000) DEFAULT NULL COMMENT 'Sincronizacion (WHERE en los updateDB)',
  `PARAM_1` varchar(100) DEFAULT NULL COMMENT 'Parametro 1',
  `PARAM_2` varchar(100) DEFAULT NULL COMMENT 'Parametro 2',
  `PARAM_3` varchar(100) DEFAULT NULL COMMENT 'Parametro 3',
  `PARAM_4` varchar(100) DEFAULT NULL COMMENT 'Parametro 4',
  `TRIGGERS` varchar(45) DEFAULT NULL COMMENT 'Nombre de la clase Triggers',
  PRIMARY KEY (`ID_TABLA`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Tablas definidas';

--
-- Table structure for table `APPBS_CAMPOS`
--
CREATE TABLE `APPBS_CAMPOS` (
  `ID_CAMPO` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Codigo',
  `ID_TABLA` int(11) NOT NULL DEFAULT '0' COMMENT 'Codigo de la tabla',
  `NOMBRE` varchar(100) NOT NULL DEFAULT '' COMMENT 'Nombre del campo',
  `TIPO` char(1) NOT NULL DEFAULT 'I' COMMENT 'Tipo del campo (I - Entero, F - Real, S - Cadena, D - Fecha)',
  `ORDEN` int(11) NOT NULL DEFAULT '0' COMMENT 'Orden',
  `LLAVE_PRIMARIA` char(1) NOT NULL DEFAULT 'N' COMMENT 'Indica si es parte de la PrimaryKey',
  `ORG_NOMBRE` varchar(100) DEFAULT NULL COMMENT 'Nombre del campo en el origen de datos',
  `ORG_CAMPO` varchar(100) DEFAULT NULL COMMENT 'Nombre del campo de la select en el origen de datos',
  `ORG_TIPO` char(1) DEFAULT NULL COMMENT 'Tipo del campo (I - Entero, F - Real, S - Cadena, D - Fecha, B - Boolean)',
  `ORG_FORMATO` varchar(50) DEFAULT NULL COMMENT 'Formato del campo',
  `FUENTE` char(1) DEFAULT NULL COMMENT 'Fuente de datos (B - Base de datos, C - Constante, P - Parametro, M - Maximo columna, F - Funcion, S - Select)',
  `FUENTE_VALOR1` varchar(500) DEFAULT NULL COMMENT 'Valor 1 segun la fuente',
  `FUENTE_VALOR2` varchar(500) DEFAULT NULL COMMENT 'Valor 2 segun la fuente',
  `EXPORTABLE` bit(1) DEFAULT NULL COMMENT 'Indica si el campo se utiliza en los exports',
  `IMPORTABLE` bit(1) DEFAULT NULL COMMENT 'Indica si el campo es impotable',
  `PARAM_1` varchar(100) DEFAULT NULL COMMENT 'Parametro 1',
  `PARAM_2` varchar(100) DEFAULT NULL COMMENT 'Parametro 2',
  `PARAM_3` varchar(100) DEFAULT NULL COMMENT 'Parametro 3',
  `PARAM_4` varchar(100) DEFAULT NULL COMMENT 'Parametro 4',
  PRIMARY KEY (`ID_CAMPO`,`ID_TABLA`),
  KEY `FLD_TAB_COD` (`ID_TABLA`) USING BTREE,
  CONSTRAINT `fk_CAMPOS_1` FOREIGN KEY (`ID_TABLA`) REFERENCES `APPBS_TABLAS` (`ID_TABLA`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='Informacion de los campos de las tablas a sincronizar';

--
-- Table structure for table `APPBS_LOG`
--
CREATE TABLE `APPBS_LOG` (
  `ID_LOG` int(11) NOT NULL AUTO_INCREMENT,
  `ID_APLICACION` varchar(10) NOT NULL DEFAULT '',
  `NIVEL` int(10) unsigned NOT NULL DEFAULT '4' COMMENT 'Nivel de Log',
  `TIPO` varchar(20) NOT NULL DEFAULT '' COMMENT 'Tipo',
  `FECHA` datetime NOT NULL DEFAULT '2000-01-01 00:00:00' COMMENT 'Fecha',
  `LOG` varchar(2000) DEFAULT NULL COMMENT 'Informacion de Log',
  PRIMARY KEY (`ID_LOG`),
  KEY `Index_1` (`NIVEL`) USING BTREE,
  KEY `Index_2` (`TIPO`) USING BTREE,
  KEY `Index_3` (`FECHA`) USING BTREE,
  KEY `Index_4` (`ID_APLICACION`) USING BTREE,
  CONSTRAINT `fk_APPBS_LOG_1` FOREIGN KEY (`ID_APLICACION`) REFERENCES `APPBS_APLICACIONES` (`ID_APLICACION`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=150223 DEFAULT CHARSET=utf8 COMMENT='Log de eventos';

--
-- Table structure for table `APPBS_LOOKUPS`
--
CREATE TABLE `APPBS_LOOKUPS` (
  `ID_LOOKUP` varchar(30) NOT NULL COMMENT 'Id. del lookup',
  `NOMBRE` varchar(50) NOT NULL COMMENT 'Nombre',
  `EDITABLE` char(1) NOT NULL COMMENT 'Indica si es editable (S/N)',
  `ACTIVO` char(1) NOT NULL COMMENT 'Indica si esta activo (S/N)',
  PRIMARY KEY (`ID_LOOKUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla para lista de lookups';

--
-- Table structure for table `APPBS_LOOKUPS_VALUES`
--
CREATE TABLE `APPBS_LOOKUPS_VALUES` (
  `ID_LOOKUP` varchar(30) NOT NULL COMMENT 'Id. del lookup',
  `ID_VALOR` varchar(100) NOT NULL COMMENT 'Id. del valor ',
  `NOMBRE` varchar(200) NOT NULL COMMENT 'Nombre visible',
  `ACTIVO` char(1) NOT NULL COMMENT 'Indica si esta activo (S/N)',
  PRIMARY KEY (`ID_LOOKUP`,`ID_VALOR`),
  CONSTRAINT `fk_APPBS_LOOKUPS_VALUES_1` FOREIGN KEY (`ID_LOOKUP`) REFERENCES `APPBS_LOOKUPS` (`ID_LOOKUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Valores de lookups';



