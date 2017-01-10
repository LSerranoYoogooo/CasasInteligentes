-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 10-01-2017 a las 09:47:23
-- Versión del servidor: 5.5.47-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `SMART_HOUSE`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_address`
--

CREATE TABLE IF NOT EXISTS `ip_address` (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT 'llave primaria de la tabla',
  `ip_address` int(16) NOT NULL COMMENT 'ip de router del usuario',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_user`
--

CREATE TABLE IF NOT EXISTS `ip_user` (
  `id_usr` int(16) NOT NULL COMMENT 'llave de la tabla',
  `ip_ip` int(16) NOT NULL COMMENT 'llave de la tabla de ip_adress',
  KEY `id_usr` (`id_usr`,`ip_ip`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `phone`
--

CREATE TABLE IF NOT EXISTS `phone` (
  `id` int(16) NOT NULL COMMENT 'foreing key id_user',
  `phone` int(16) NOT NULL COMMENT 'numero de telefono del usuario',
  `region` int(16) NOT NULL COMMENT 'region del # ejm 506 0 costa rica',
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT 'llave primaria de la tabla',
  `user_name` varchar(64) NOT NULL COMMENT 'nombre de usuario con el que se iniciara sesion',
  `password` varchar(64) NOT NULL COMMENT 'contraseña de usuario',
  `email` varchar(64) NOT NULL COMMENT 'email de usuario',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'nombre real del usuario',
  `last_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL COMMENT 'apellidos reales del usuario',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`,`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
