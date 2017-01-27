-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 27-01-2017 a las 15:00:59
-- Versión del servidor: 5.5.47-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `SMART_HOME`
--

DELIMITER $$
--
-- Procedimientos
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `set_Enclouser`( IN site_id VARCHAR( 160 ), IN name VARCHAR( 160 ),  IN img VARCHAR( 160 ) )
BEGIN
INSERT INTO enclouser(st_id, enc_name, enc_img) VALUES (site_id, name, img);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `set_Site`( IN usr_id VARCHAR( 160 ), IN name VARCHAR( 160 ), IN latitud VARCHAR( 160 ),  IN longitud VARCHAR( 160 ), IN img VARCHAR( 10 ), IN ip_address VARCHAR( 160 ), IN ip_port VARCHAR( 160 ) )
BEGIN
DECLARE ID INT;
INSERT INTO site(usr_id, st_name, st_latitud, st_longitud, st_img) VALUES (usr_id, name, latitud, longitud, img);
SET ID = LAST_INSERT_ID();
INSERT INTO ip_address(st_id, usr_id, ipa_ip, ipa_port) VALUES (ID, usr_id, ip_address, ip_port);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `set_User`( IN name VARCHAR( 160 ), IN email VARCHAR( 160 ),  IN pass VARCHAR( 160 ), IN service VARCHAR( 10 ) )
BEGIN
INSERT INTO user_app(usr_name, usr_email, usr_pass, usr_service_type) VALUES (name, email, pass, service);
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `components`
--

CREATE TABLE IF NOT EXISTS `components` (
  `comp_id` int(11) NOT NULL AUTO_INCREMENT,
  `comp_name` varchar(160) NOT NULL,
  `comp_description` varchar(300) NOT NULL,
  `comp_price` varchar(160) NOT NULL,
  PRIMARY KEY (`comp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `control`
--

CREATE TABLE IF NOT EXISTS `control` (
  `ctrl_id` int(11) NOT NULL AUTO_INCREMENT,
  `enc_id` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `ctrl_name` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `ctrl_voice_on` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `ctrl_voice_off` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `ctrl_channel` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `ctrl_real_state` tinyint(1) NOT NULL DEFAULT '0',
  `ctrl_img` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`ctrl_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `control`
--

INSERT INTO `control` (`ctrl_id`, `enc_id`, `ctrl_name`, `ctrl_voice_on`, `ctrl_voice_off`, `ctrl_channel`, `ctrl_real_state`, `ctrl_img`) VALUES
(1, '1', 'uno', 'uno on', 'uno off', '1', 0, 'img'),
(2, '1', 'dos', 'dos on', 'dos off', '2', 1, 'img'),
(3, '2', 'tres', 'tres on', 'tres off', '3', 0, 'img'),
(4, '2', 'cuatro', 'cuatro on', 'cuatro off', '4', 1, 'img'),
(5, '2', 'cinco', 'cinco on', 'cinco off', '5', 1, 'img');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `element_pack`
--

CREATE TABLE IF NOT EXISTS `element_pack` (
  `element_id` int(11) NOT NULL AUTO_INCREMENT,
  `pac_id` int(11) NOT NULL,
  `comp_id` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  PRIMARY KEY (`element_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `enclouser`
--

CREATE TABLE IF NOT EXISTS `enclouser` (
  `enc_id` int(11) NOT NULL AUTO_INCREMENT,
  `st_id` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `enc_name` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `enc_img` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`enc_id`),
  KEY `st_id` (`st_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=6 ;

--
-- Volcado de datos para la tabla `enclouser`
--

INSERT INTO `enclouser` (`enc_id`, `st_id`, `enc_name`, `enc_img`) VALUES
(1, '6', 'Desarrollo', '0'),
(2, '6', 'oficina', 'homecontrol_ejm'),
(3, '4', 'test casa', 'homecontrol_ejm'),
(4, '7', 'dhggg', 'homecontrol_ejm'),
(5, '1', 'test sala', 'homecontrol_ejm');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_address`
--

CREATE TABLE IF NOT EXISTS `ip_address` (
  `ipa_id` int(11) NOT NULL AUTO_INCREMENT,
  `st_id` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `usr_id` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `ipa_ip` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `ipa_port` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  PRIMARY KEY (`ipa_id`),
  UNIQUE KEY `st_id` (`st_id`),
  KEY `st_id_2` (`st_id`),
  KEY `usr_id` (`usr_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `ip_address`
--

INSERT INTO `ip_address` (`ipa_id`, `st_id`, `usr_id`, `ipa_ip`, `ipa_port`) VALUES
(1, '1', '1', '111.111.111.111', '1'),
(2, '4', '2', '111.111.111.112', '2'),
(3, '6', '2', '186.4.46.122', '81'),
(4, '7', '2', '186.23.24.46', '8080'),
(5, '8', '1', '111.122.112.111', '80'),
(6, '9', '2', '192.168.0.52', '80');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `package`
--

CREATE TABLE IF NOT EXISTS `package` (
  `pac_id` int(11) NOT NULL AUTO_INCREMENT,
  `pac_name` varchar(160) NOT NULL,
  `pac_description` varchar(300) NOT NULL,
  `pac_cant_elementos` int(11) NOT NULL,
  PRIMARY KEY (`pac_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `site`
--

CREATE TABLE IF NOT EXISTS `site` (
  `st_id` int(11) NOT NULL AUTO_INCREMENT,
  `usr_id` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `st_name` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `st_latitud` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  `st_longitud` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  `st_img` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`st_id`),
  KEY `usr_id` (`usr_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=7 ;

--
-- Volcado de datos para la tabla `site`
--

INSERT INTO `site` (`st_id`, `usr_id`, `st_name`, `st_latitud`, `st_longitud`, `st_img`) VALUES
(1, '1', 'casa test', '0.000.000', '0.000.000', 'homecontro'),
(4, '2', 'casa yoogooo', '0.000.000', '0.000.000', 'homecontro'),
(6, '2', 'Yoogooo oficina', '0.000.000', '0.000.000', 'homecontro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tecnico_app`
--

CREATE TABLE IF NOT EXISTS `tecnico_app` (
  `tec_id` int(11) NOT NULL AUTO_INCREMENT,
  `tec_name` varchar(160) NOT NULL,
  `tec_email` varchar(160) NOT NULL,
  `tec_password` varchar(160) NOT NULL,
  `tec_pais` varchar(50) NOT NULL,
  `tec_provincia` varchar(50) NOT NULL,
  `tec_calificacion` int(11) NOT NULL,
  PRIMARY KEY (`tec_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_app`
--

CREATE TABLE IF NOT EXISTS `user_app` (
  `usr_id` int(11) NOT NULL AUTO_INCREMENT,
  `usr_name` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `usr_email` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `usr_pass` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `usr_service_type` varchar(10) COLLATE utf8_spanish_ci NOT NULL,
  `default_site` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  PRIMARY KEY (`usr_id`),
  UNIQUE KEY `usr_email` (`usr_email`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci AUTO_INCREMENT=3 ;

--
-- Volcado de datos para la tabla `user_app`
--

INSERT INTO `user_app` (`usr_id`, `usr_name`, `usr_email`, `usr_pass`, `usr_service_type`, `default_site`) VALUES
(1, 'Luis G Serrano', 'lserrano467@gmail.com', '9a0a82f0c0cf31470d7affede3406cc9aa8410671520b727044eda15b4c25532a9b5cd8aaf9cec4919d76255b6bfb00f', '1', '1'),
(2, 'Gustavo Serrano S.', 'lserrano@yoogooo.com', '9a0a82f0c0cf31470d7affede3406cc9aa8410671520b727044eda15b4c25532a9b5cd8aaf9cec4919d76255b6bfb00f', '2', '6');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
