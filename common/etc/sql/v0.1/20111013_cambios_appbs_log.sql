DELETE FROM `APPBS_LOG`;
ALTER TABLE `APPBS_LOG` DROP COLUMN `DATOS5` , DROP COLUMN `DATOS4` , DROP COLUMN `DATOS3` , DROP COLUMN `DATOS2` , DROP COLUMN `DATOS1` ,
ADD COLUMN `LOG` VARCHAR(2000) NULL COMMENT 'Informacion de Log'  AFTER `FECHA` ;