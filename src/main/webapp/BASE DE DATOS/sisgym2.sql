-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-11-2023 a las 02:00:41
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sisgym2`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `idAsistencia` int(11) NOT NULL,
  `FechaYHora` datetime DEFAULT NULL,
  `Cliente_idCliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `asistencia`
--

INSERT INTO `asistencia` (`idAsistencia`, `FechaYHora`, `Cliente_idCliente`) VALUES
(1, '2023-11-03 23:03:20', 4),
(2, '2023-11-05 01:06:23', 2),
(3, '2023-11-05 11:10:22', 3),
(4, '2023-11-05 13:46:25', 3),
(5, '2023-11-14 19:50:34', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clases`
--

CREATE TABLE `clases` (
  `idClases` int(11) NOT NULL,
  `instructor` varchar(45) NOT NULL,
  `fecha` date NOT NULL,
  `horario` varchar(45) NOT NULL,
  `descripcion` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clases`
--

INSERT INTO `clases` (`idClases`, `instructor`, `fecha`, `horario`, `descripcion`) VALUES
(1, 'Maximo Dario', '2023-11-02', '6PM - 8PM', 'Full-Body'),
(2, 'Enrique Quispe', '2023-12-09', '5:30pm-8:00pm', 'zumba'),
(3, 'Nicolas R.', '2023-11-09', '09:00 AM - 10:00 AM', 'dormir'),
(4, 'Jano', '2022-12-31', '09:00 AM - 10:00 AM', 'jatear'),
(5, 'jano', '2023-04-30', '09:00 AM - 10:00 AM', 'jajano'),
(6, 'jano', '2023-04-30', '09:00 AM - 10:00 AM', 'jajano'),
(7, 'jano', '2023-04-30', '09:00 AM - 10:00 AM', 'jajano'),
(8, 'jano', '2023-04-30', '09:00 AM - 10:00 AM', 'jajano'),
(9, 'jano', '2023-04-30', '09:00 AM - 10:00 AM', 'jajano'),
(10, 'jano', '2023-04-30', '09:00 AM - 10:00 AM', 'jajano'),
(11, 'anshi', '2023-11-05', '4:30pm-5:30pm', 'yambal'),
(12, 'anshi', '2023-11-05', '4:30pm-5:30pm', 'yambal'),
(13, 'muslitoncio', '2023-04-30', '09:00 AM - 10:00 AM', 'pruebaLento'),
(14, 'muslitoncio2', '2023-04-30', '09:00 AM - 10:00 AM', 'pruebaRapido'),
(15, 'muslitoncio2', '2023-04-30', '09:00 AM - 10:00 AM', 'pruebaRapido'),
(16, 'muslitoncio2', '2023-04-30', '09:00 AM - 10:00 AM', 'pruebaRapido'),
(17, 'panza', '2023-04-30', '09:00 AM - 10:00 AM', 'UltimaPruebaQuieroComer'),
(18, 'Carlos Jesús', '2023-11-06', '09:00 AM - 10:00 AM', 'Step'),
(19, 'Carlos Jesús', '2023-11-16', '21:00 PM - 22:00 PM', 'Step'),
(20, 'Carlos Jesús', '2023-11-16', '19:00 PM - 20:00 PM', 'Step');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `idCliente` int(11) NOT NULL,
  `NombreCliente` varchar(45) NOT NULL,
  `Apellidos` varchar(45) NOT NULL,
  `emailClie` varchar(100) NOT NULL,
  `DNI` char(10) NOT NULL,
  `TelefonoCliente` char(10) DEFAULT NULL,
  `estaClie` tinyint(4) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`idCliente`, `NombreCliente`, `Apellidos`, `emailClie`, `DNI`, `TelefonoCliente`, `estaClie`) VALUES
(1, 'Daniella J', 'Marin', '0333211023@unjfsc.edu.pe', '45671235', '123456123', 1),
(2, 'Jeancarlos ', 'Rios ', 'wtke90@gmail.com', '75117251', '979835782', 1),
(3, 'Nicolas R', 'Requena Castro', '0333211023@unjfsc.edu.pe', '72479565', '967896345', 1),
(4, 'Angie Alexandra', 'Echegaray Ayala', '0333202010@unjfsc.edu.pe', '71960755', '987543123', 1),
(6, 'Alejandro Jesus', 'Rivero', 'jano13.libra@gmail.com', '70856878', '56476338', 1),
(7, 'ALEJANDRO JESUS', 'RIVERO GAMARRA', 'janolibra@gmail.com', '70856878', '56476338', 0),
(8, 'KIKE', 'CHINGA RAMOS', 'chinga@gmail.com', '70856878', '56476338', 0),
(9, 'JOEL ', 'GONZALES MANTILLA', 'bebitofiufiu@gmail.com', '71646979', '16497321', 0),
(10, 'JOEL ', 'GONZALES MANTILLA', 'bebitofiufiu@gmail.com', '71646979', '16497321', 0),
(11, 'CARLOS ', 'SIPAN LOZANO', 'caldito@gmaYl.com', '76946947', '914346784', 0),
(12, 'GIANCARLO ANTONIO', 'QUINTANA ACUÑA', 'yisus@gmail.com', '70861979', '914611678', 0),
(13, 'NICOLAS RODRIGO', 'REQUENA CASTRO', 'nicolasrc1403@gmail.com', '72479565', '957985445', 0),
(14, 'RAFAEL ADONIS', 'FLORES RIVERA', 'rafael@gmail.com', '70134451', '987654321', 0),
(15, 'Delci Denis ', 'Vilcahuaman Perez', 'Delcid@gmail.com', '70314521', '987654321', 0),
(16, 'Angie Alexandra', 'Echegaray Ayala', '0333202010@unjfsc.edu.pe', '71960755', '987543123', 0),
(17, '', 'undefined undefined', 'correo', '45478956', '234543234', 0),
(18, 'Nicolas R', 'Requena Castro', '0333211023@unjfsc.edu.pe', '72479565', '967896345', 0),
(19, 'NICOLAS RODRIGO', 'REQUENA CASTRO', 'nico@gmail.com', '70856878', '957985445', 0),
(20, 'ALEJANDRO JESUS', 'RIVERO GAMARRA', 'jajano@gmail.com', '70856878', '56476338', 0),
(21, 'Nicolas Rodrigo', 'Requena Castro', '0333211023@unjfsc.edu.pex', '72479565', '967896345', 0),
(22, 'Pepe Rodrigo', 'Ramirez', '0333211023@unjfsc.edu.pex', '72479565', '967896345', 0),
(23, 'Jose Pepe', 'Rondon', '0333211023@unjfsc.edu.pex', '72479565', '967896345', 0),
(24, 'Paolo Jose', 'Suarez', '0333211023@unjfsc.edu.pex', '72479565', '967896345', 0),
(25, 'Pepe', 'Rodriguez', '0333211023@unjfsc.edu.pex', '72479565', '967896345', 0),
(26, 'a', 'a', '0333211023@unjfsc.edu.pex', '72479565', '967896345', 0),
(27, 'b', 'b', '0333211023@unjfsc.edu.pex', '72479565', '967896345', 0),
(28, 'a', 'a', '0333211023@unjfsc.edu.pe', '72479565', '967896345', 0),
(29, 'a', 'b', '0333211023@unjfsc.edu.pe', '72479565', '967896345', 0),
(30, 'pruebaFacturaRapida', 'Requena', '0333211023@unjfsc.edu.pe', '72479565', '967896345', 0),
(31, 'pruebaFacturaRapida', 'Requena', '0333211023@unjfsc.edu.pe', '72479565', '967896345', 0),
(32, 'NICOLAS RODRIGO', 'REQUENA CASTRO', 'ncolasrc1403@gmail.com', '72479565', '967896345', 0),
(33, 'NICOLAS', 'REQUENA CASTRO', 'ncolasrc1403@gmail.com', '72479565', '967896345', 0),
(34, 'RODRIGO N.', 'REQUENA CASTRO', '0333211023@unjfsc.edu.pe', '72479565', '967896345', 0),
(35, 'NICOLAS RODRIGO', 'REQUENA CASTRO', '0333211023@unjfsc.edu.pe', '72479565', '967896345', 0),
(36, 'RODRIGO NICOLAS', 'REQUENA CASTRO', 'nicolasrc1403@gmail.com', '72479565', '967896345', 0),
(37, 'JANO LOCO', 'IJIJJA', 'nicolasrc1403@gmail.com', '72479565', '967896345', 0),
(38, 'AngieLoca', 'jijiij', 'nicolasrc1403@gmail.com', '72479565', '967896345', 0),
(39, 'NICOLAS RODRIGO', 'REQUENA CASTRO', 'nicolasrc1403@gmail.com', '72479565', '967896345', 0),
(40, 'NICOLAS RODRIGO', 'REQUENA CASTRO', 'nicolasrc1403@gmail.com', '72479565', '967896345', 0),
(41, 'ALEJANDRO JESUS', 'RIVERO GAMARRA', 'jano13.libra@gmail.com', '70856878', '987654321', 0),
(42, 'NICOLAS RODRIGO', 'REQUENA CASTRO', 'nicolasrc1403@gmail.com', '72479565', '957985445', 0),
(43, 'ANGIE ALEXANDRA', 'ECHEGARAY AYALA', 'anshiclases@gmail.com', '71960755', '936522015', 1),
(44, 'KATHERINE PAMELA', 'ECHEGARAY AYALA', 'pamelaechegarayayala@gmail.com', '71960756', '970674123', 0),
(45, 'LUIS AURELIO', 'SALAS COCHA', 'nicolasrc1403@gmail.com', '72479567', '987654321', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `idEmpleado` int(11) NOT NULL,
  `NombreEmpleado` varchar(45) NOT NULL,
  `ApellidoEmpleado` varchar(45) NOT NULL,
  `dniEmpleado` char(10) NOT NULL,
  `categoriaEmpleado` char(20) DEFAULT NULL,
  `telefonoEmpleado` char(10) DEFAULT NULL,
  `estadoEmpleado` char(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`idEmpleado`, `NombreEmpleado`, `ApellidoEmpleado`, `dniEmpleado`, `categoriaEmpleado`, `telefonoEmpleado`, `estadoEmpleado`) VALUES
(1, 'Oscar Enrique', 'Quispe Huamán', '12345678', 'Atencion al Cliente', '987654321', 'Contratado'),
(2, 'Carlos Jesus', 'Sipan Lozano', '12345678', 'Atencion al Cliente', '987654321', 'Contratado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `idFactura` int(11) NOT NULL,
  `FechaInicio` date NOT NULL,
  `FechaFin` date NOT NULL,
  `subtotal` double NOT NULL,
  `total` double NOT NULL,
  `Cliente_idCliente` int(11) NOT NULL,
  `Empleado_idEmpleado` int(11) NOT NULL,
  `Pago_idPago` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`idFactura`, `FechaInicio`, `FechaFin`, `subtotal`, `total`, `Cliente_idCliente`, `Empleado_idEmpleado`, `Pago_idPago`) VALUES
(1, '2023-11-03', '2023-12-03', 123, 150, 6, 1, 1),
(2, '2023-11-03', '2023-12-03', 123, 150, 8, 1, 2),
(3, '2023-11-04', '2023-12-04', 123, 150, 10, 1, 3),
(4, '2023-11-23', '2023-12-23', 123, 150, 11, 1, 4),
(5, '2023-11-21', '2023-12-21', 123, 150, 12, 1, 5),
(6, '2023-11-02', '2023-11-18', 123, 150, 13, 1, 6),
(7, '2023-11-02', '2023-11-16', 123, 150, 16, 1, 7),
(8, '2023-11-02', '2023-11-10', 123, 150, 17, 1, 8),
(9, '2023-11-03', '2023-11-17', 123, 150, 18, 1, 9),
(10, '2023-11-10', '2023-11-17', 123, 150, 19, 1, 10),
(11, '2023-11-03', '2023-11-11', 123, 150, 20, 1, 11),
(12, '2024-01-01', '2024-01-31', 48.38, 59, 27, 1, 12),
(13, '2023-11-01', '2024-01-30', 123, 150, 28, 1, 13),
(14, '2023-11-01', '2024-01-30', 123, 150, 31, 1, 15),
(15, '2023-01-04', '2023-02-03', 48.38, 59, 32, 1, 16),
(16, '2023-11-01', '2024-01-30', 123, 150, 34, 1, 17),
(17, '2023-11-01', '2024-01-30', 123, 150, 34, 1, 18),
(18, '2023-11-01', '2024-01-30', 123, 150, 35, 1, 19),
(19, '2023-11-01', '2024-01-30', 123, 150, 35, 1, 20),
(20, '2023-11-01', '2024-01-06', 123, 150, 36, 1, 21),
(21, '2023-11-02', '2024-01-31', 123, 150, 39, 1, 22),
(22, '2023-11-02', '2024-03-31', 205, 250, 40, 1, 23),
(23, '2023-11-30', '2024-04-28', 205, 250, 41, 1, 24),
(24, '2023-11-19', '2023-12-19', 48.38, 59, 42, 1, 25),
(25, '2023-11-09', '2024-02-07', 123, 150, 43, 1, 26),
(26, '2023-11-19', '2024-04-17', 205, 250, 44, 1, 27),
(27, '2023-11-26', '2023-12-26', 48.38, 59, 45, 1, 28);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `matricula`
--

CREATE TABLE `matricula` (
  `idMatricula` int(11) NOT NULL,
  `idCliente` int(11) NOT NULL,
  `idMembresia` int(11) NOT NULL,
  `FechaInicio` date NOT NULL,
  `FechaFin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `matricula`
--

INSERT INTO `matricula` (`idMatricula`, `idCliente`, `idMembresia`, `FechaInicio`, `FechaFin`) VALUES
(1, 6, 1, '2023-11-03', '2023-12-03'),
(2, 8, 1, '2023-11-03', '2023-12-03'),
(3, 10, 1, '2023-11-04', '2023-12-04'),
(4, 11, 1, '2023-11-23', '2023-12-23'),
(5, 12, 1, '2023-11-21', '2023-12-21'),
(6, 13, 1, '2023-11-02', '2023-11-18'),
(7, 16, 1, '2023-11-02', '2023-11-16'),
(8, 17, 1, '2023-11-02', '2023-11-10'),
(9, 18, 1, '2023-11-03', '2023-11-17'),
(10, 19, 1, '2023-11-10', '2023-11-17'),
(11, 20, 1, '2023-11-03', '2023-11-11'),
(12, 27, 3, '2024-01-01', '2024-01-31'),
(13, 28, 1, '2023-11-01', '2024-01-30'),
(14, 30, 1, '2023-11-01', '2024-01-05'),
(15, 31, 1, '2023-11-01', '2024-01-30'),
(16, 32, 3, '2023-01-04', '2023-02-03'),
(17, 34, 1, '2023-11-01', '2024-01-30'),
(18, 35, 1, '2023-11-01', '2024-01-30'),
(19, 36, 1, '2023-11-01', '2024-01-06'),
(20, 39, 1, '2023-11-02', '2024-01-31'),
(21, 40, 2, '2023-11-02', '2024-03-31'),
(22, 41, 2, '2023-11-30', '2024-04-28'),
(23, 42, 3, '2023-11-19', '2023-12-19'),
(24, 43, 1, '2023-11-09', '2024-02-07'),
(25, 44, 2, '2023-11-19', '2024-04-17'),
(26, 45, 3, '2023-11-26', '2023-12-26');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `membresia`
--

CREATE TABLE `membresia` (
  `idMembresia` int(11) NOT NULL,
  `TipoMembresia` varchar(45) DEFAULT NULL,
  `DuracionMembresia` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `membresia`
--

INSERT INTO `membresia` (`idMembresia`, `TipoMembresia`, `DuracionMembresia`) VALUES
(1, 'Gold', '3 meses'),
(2, 'Platino', '5'),
(3, 'Basico', '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pago`
--

CREATE TABLE `pago` (
  `idPago` int(11) NOT NULL,
  `FechaPago` date DEFAULT NULL,
  `Monto` double DEFAULT NULL,
  `MetodoPago` varchar(45) DEFAULT NULL,
  `Matricula_idMatricula` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `pago`
--

INSERT INTO `pago` (`idPago`, `FechaPago`, `Monto`, `MetodoPago`, `Matricula_idMatricula`) VALUES
(1, '2023-11-02', 150, 'Tarjeta', 1),
(2, '2023-11-02', 150, 'Tarjeta', 2),
(3, '2023-11-02', 150, 'Tarjeta', 3),
(4, '2023-11-02', 150, 'Tarjeta', 4),
(5, '2023-11-02', 150, 'Tarjeta', 5),
(6, '2023-11-03', 150, 'Tarjeta', 6),
(7, '2023-11-03', 150, 'Tarjeta', 7),
(8, '2023-11-04', 150, 'Tarjeta', 8),
(9, '2023-11-04', 150, 'Tarjeta', 9),
(10, '2023-11-04', 150, 'Tarjeta', 10),
(11, '2023-11-04', 150, 'Tarjeta', 11),
(12, '2023-11-04', 59, 'Tarjeta', 12),
(13, '2023-11-04', 150, 'Tarjeta', 13),
(14, '2023-11-04', 150, 'Tarjeta', 14),
(15, '2023-11-04', 150, 'Tarjeta', 15),
(16, '2023-11-04', 59, 'Tarjeta', 16),
(17, '2023-11-04', 150, 'Tarjeta', 17),
(18, '2023-11-04', 150, 'Tarjeta', 17),
(19, '2023-11-04', 150, 'Tarjeta', 18),
(20, '2023-11-04', 150, 'Tarjeta', 18),
(21, '2023-11-04', 150, 'Tarjeta', 19),
(22, '2023-11-13', 150, 'Tarjeta', 20),
(23, '2023-11-13', 250, 'Tarjeta', 21),
(24, '2023-11-13', 250, 'Efectivo', 22),
(25, '2023-11-18', 59, 'Tarjeta', 23),
(26, '2023-11-18', 150, 'Tarjeta', 24),
(27, '2023-11-18', 250, 'Tarjeta', 25),
(28, '2023-11-18', 59, 'Tarjeta', 26);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `codiUsua` int(11) NOT NULL,
  `logiUsua` varchar(45) NOT NULL,
  `passUsua` char(64) NOT NULL,
  `EstadoUsuario` char(20) DEFAULT NULL,
  `TipoUsuario` char(20) DEFAULT NULL,
  `FechaRegistro` date DEFAULT NULL,
  `Empleado_idEmpleado` int(11) NOT NULL,
  `toknUsua` varchar(100) DEFAULT 'vacio'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`codiUsua`, `logiUsua`, `passUsua`, `EstadoUsuario`, `TipoUsuario`, `FechaRegistro`, `Empleado_idEmpleado`, `toknUsua`) VALUES
(1, 'dachi', 'ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad', 'Activo', 'Editor', '2023-10-28', 1, 'WPOHPKSC3GKBTXCYWTPHETMKOBLE4KO6'),
(2, 'tUki', 'ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad', 'Activo', 'Editor', '2023-10-28', 2, '5LV3JYAOOUPOBUO7O5WZAFA3X6TYIJ44');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`idAsistencia`),
  ADD KEY `fk_Asistencia_Cliente1_idx` (`Cliente_idCliente`);

--
-- Indices de la tabla `clases`
--
ALTER TABLE `clases`
  ADD PRIMARY KEY (`idClases`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`idCliente`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`idEmpleado`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`idFactura`),
  ADD KEY `fk_Factura_Cliente1_idx` (`Cliente_idCliente`),
  ADD KEY `fk_Factura_Empleado1_idx` (`Empleado_idEmpleado`),
  ADD KEY `fk_Factura_Pago1_idx` (`Pago_idPago`);

--
-- Indices de la tabla `matricula`
--
ALTER TABLE `matricula`
  ADD PRIMARY KEY (`idMatricula`),
  ADD KEY `idCliente` (`idCliente`),
  ADD KEY `idMembresia` (`idMembresia`);

--
-- Indices de la tabla `membresia`
--
ALTER TABLE `membresia`
  ADD PRIMARY KEY (`idMembresia`);

--
-- Indices de la tabla `pago`
--
ALTER TABLE `pago`
  ADD PRIMARY KEY (`idPago`),
  ADD KEY `fk_Pago_Matricula` (`Matricula_idMatricula`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`codiUsua`),
  ADD KEY `fk_Usuario_Empleado1_idx` (`Empleado_idEmpleado`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `idAsistencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `clases`
--
ALTER TABLE `clases`
  MODIFY `idClases` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `idEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `idFactura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- AUTO_INCREMENT de la tabla `matricula`
--
ALTER TABLE `matricula`
  MODIFY `idMatricula` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `membresia`
--
ALTER TABLE `membresia`
  MODIFY `idMembresia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `codiUsua` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD CONSTRAINT `fk_Asistencia_Cliente1` FOREIGN KEY (`Cliente_idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `fk_Factura_Cliente1` FOREIGN KEY (`Cliente_idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_Factura_Empleado1` FOREIGN KEY (`Empleado_idEmpleado`) REFERENCES `empleado` (`idEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `matricula`
--
ALTER TABLE `matricula`
  ADD CONSTRAINT `matricula_ibfk_1` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  ADD CONSTRAINT `matricula_ibfk_2` FOREIGN KEY (`idMembresia`) REFERENCES `membresia` (`idMembresia`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `fk_Usuario_Empleado1` FOREIGN KEY (`Empleado_idEmpleado`) REFERENCES `empleado` (`idEmpleado`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
