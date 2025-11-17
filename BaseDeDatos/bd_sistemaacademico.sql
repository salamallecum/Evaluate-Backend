-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 17-11-2025 a las 18:13:04
-- Versión del servidor: 5.7.40
-- Versión de PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bd_sistemaacademico`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `categorias`
--

INSERT INTO `categorias` (`id`, `descripcion`, `titulo`) VALUES
(2, 'Exámenes realcionados con lenguajes de programación', 'Lenguajes de programación'),
(3, 'Examenes relacionados con el framework SpringBoot.', 'SpringBoot'),
(4, 'Examenes sobre cultura general.', 'Trivias'),
(5, 'Examenes sobre biologpia y ciencias', 'Reino animal');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `examenes`
--

DROP TABLE IF EXISTS `examenes`;
CREATE TABLE IF NOT EXISTS `examenes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `esta_activo` bit(1) NOT NULL,
  `numero_de_preguntas` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `puntos_maximos` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `titulo` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `categoria_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbes7bp4f8xktff2u0rdwnd0ph` (`categoria_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `examenes`
--

INSERT INTO `examenes` (`id`, `descripcion`, `esta_activo`, `numero_de_preguntas`, `puntos_maximos`, `titulo`, `categoria_id`) VALUES
(5, 'Con este exammen, podrás a prueba tus conocimientos sobre Java core y POO', b'1', '10', '20', 'Test Java SE', 2),
(6, 'Con este examen, pondras a prueba tus conocimientos en cultura general.', b'0', '10', '30', 'Examen de cultura general actualizado', 4),
(8, 'Con este examen, pondrás a prueba tus conocimientos en el framework Spring de Java.', b'1', '15', '20', 'Examen de Spring', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `preguntas`
--

DROP TABLE IF EXISTS `preguntas`;
CREATE TABLE IF NOT EXISTS `preguntas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contenido` varchar(5000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `imagen` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `opcion1` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `opcion2` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `opcion3` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `opcion4` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `respuesta` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `examen_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9hlw51x7hfqs1tv3sviwqycqi` (`examen_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `preguntas`
--

INSERT INTO `preguntas` (`id`, `contenido`, `imagen`, `opcion1`, `opcion2`, `opcion3`, `opcion4`, `respuesta`, `examen_id`) VALUES
(1, '¿Qué es Programación Orientada a Objetos (POO)?', 'defecto.png', 'Una marca de carros.', 'Un paradigma de programacion en donde las entidades se modelan como objetos.', 'Una marca de shampoo', 'No tengo ni puta idea', 'Un paradigma de programacion en donde las entidades se modelan como objetos.', 5),
(4, '¿Que es Inyección de dependencias?', 'defecto.png', 'Es lo mismo que Java Enterprise', 'Un API Rest', 'Es lo mismo que Spring', 'Un patron de diseño', 'Un patron de diseño', 8),
(5, '¿Cuál es la ultima version existente para el Java JDK?', 'defecto.png', '8', '11', '6', '25', '25', 5),
(6, '¿Que es Junit?', 'defecto.png', 'Un tipo de arquitectura de software', 'Un framework para la codificación de pruebas unitarias', 'Una marca de zapatos', 'No tengo ni puta idea', 'Un framework para la codificación de pruebas unitarias', 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `rol_id` bigint(20) NOT NULL,
  `nombre` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`rol_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`rol_id`, `nombre`) VALUES
(1, 'ADMIN'),
(2, 'NORMAL');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `esta_activo` bit(1) NOT NULL,
  `nombres` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `perfil` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefono` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKm2dvbwfge291euvmk6vkkocao` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `apellidos`, `email`, `esta_activo`, `nombres`, `password`, `perfil`, `telefono`, `username`) VALUES
(1, 'Rodriguez', 'tatico1994@gmail.com', b'1', 'Tatis', '$2a$10$we9OUDTt9xrsfXQNKY8mAujep.BUAV5iMSsy8JgcOqgoG9BGL1AZK', 'default.png', '1981913', 'Tatis94'),
(2, 'Amaya', 'alejo1997@gmail.com', b'1', 'Alejo', '$2a$10$PyQfvdDYhXK37zqzfyTuCerujaa8DyRHL/iH1V.U0F1PM12AO4ICu', 'default.png', '1981913', 'Alejo97');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_rol`
--

DROP TABLE IF EXISTS `usuario_rol`;
CREATE TABLE IF NOT EXISTS `usuario_rol` (
  `usuario_rol_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `rol_rol_id` bigint(20) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`usuario_rol_id`),
  KEY `FK7j1tyvjj1tv8gbq7n6f7efccc` (`rol_rol_id`),
  KEY `FKktsemf1f6awjww4da0ocv4n32` (`usuario_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuario_rol`
--

INSERT INTO `usuario_rol` (`usuario_rol_id`, `rol_rol_id`, `usuario_id`) VALUES
(1, 2, 1),
(2, 1, 2);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `examenes`
--
ALTER TABLE `examenes`
  ADD CONSTRAINT `FKbes7bp4f8xktff2u0rdwnd0ph` FOREIGN KEY (`categoria_id`) REFERENCES `categorias` (`id`);

--
-- Filtros para la tabla `preguntas`
--
ALTER TABLE `preguntas`
  ADD CONSTRAINT `FK9hlw51x7hfqs1tv3sviwqycqi` FOREIGN KEY (`examen_id`) REFERENCES `examenes` (`id`);

--
-- Filtros para la tabla `usuario_rol`
--
ALTER TABLE `usuario_rol`
  ADD CONSTRAINT `FK7j1tyvjj1tv8gbq7n6f7efccc` FOREIGN KEY (`rol_rol_id`) REFERENCES `roles` (`rol_id`),
  ADD CONSTRAINT `FKktsemf1f6awjww4da0ocv4n32` FOREIGN KEY (`usuario_id`) REFERENCES `usuarios` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
