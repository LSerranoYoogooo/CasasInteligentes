-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-01-2017 a las 22:52:48
-- Versión del servidor: 10.1.16-MariaDB
-- Versión de PHP: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `smart_home`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `control`
--

CREATE TABLE `control` (
  `ctrl_id` int(11) NOT NULL,
  `enc_id` int(11) NOT NULL,
  `ctrl_name` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `ctrl_voice_on` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  `ctrl_voice_off` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  `ctrl_channel` smallint(6) NOT NULL,
  `ctrl_real_state` tinyint(1) NOT NULL DEFAULT '0',
  `ctrl_img` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `enclouser`
--

CREATE TABLE `enclouser` (
  `enc_id` int(11) NOT NULL,
  `st_id` int(11) NOT NULL,
  `enc_name` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `enc_img` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ip_address`
--

CREATE TABLE `ip_address` (
  `ipa_id` int(11) NOT NULL,
  `st_id` int(11) NOT NULL,
  `ipa_ip` varchar(20) COLLATE utf8_spanish_ci NOT NULL,
  `ipa_port` varchar(10) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `site`
--

CREATE TABLE `site` (
  `st_id` int(11) NOT NULL,
  `usr_id` int(11) NOT NULL,
  `st_name` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `st_latitud` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  `st_longitud` varchar(160) COLLATE utf8_spanish_ci DEFAULT NULL,
  `st_img` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_app`
--

CREATE TABLE `user_app` (
  `usr_id` int(11) NOT NULL,
  `usr_name` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `usr_email` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `usr_pass` varchar(160) COLLATE utf8_spanish_ci NOT NULL,
  `usr_service_type` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `control`
--
ALTER TABLE `control`
  ADD PRIMARY KEY (`ctrl_id`),
  ADD UNIQUE KEY `enc_id` (`enc_id`);

--
-- Indices de la tabla `enclouser`
--
ALTER TABLE `enclouser`
  ADD PRIMARY KEY (`enc_id`),
  ADD KEY `st_id` (`st_id`);

--
-- Indices de la tabla `ip_address`
--
ALTER TABLE `ip_address`
  ADD PRIMARY KEY (`ipa_id`),
  ADD UNIQUE KEY `st_id` (`st_id`);

--
-- Indices de la tabla `site`
--
ALTER TABLE `site`
  ADD PRIMARY KEY (`st_id`),
  ADD KEY `usr_id` (`usr_id`);

--
-- Indices de la tabla `user_app`
--
ALTER TABLE `user_app`
  ADD PRIMARY KEY (`usr_id`),
  ADD UNIQUE KEY `usr_email` (`usr_email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `control`
--
ALTER TABLE `control`
  MODIFY `ctrl_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `enclouser`
--
ALTER TABLE `enclouser`
  MODIFY `enc_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `ip_address`
--
ALTER TABLE `ip_address`
  MODIFY `ipa_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `site`
--
ALTER TABLE `site`
  MODIFY `st_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `user_app`
--
ALTER TABLE `user_app`
  MODIFY `usr_id` int(11) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
