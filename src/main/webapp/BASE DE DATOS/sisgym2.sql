-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-01-2024 a las 00:23:54
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
(11, '2024-01-05 18:25:16', 134),
(12, '2024-01-05 18:25:31', 134),
(18, '2024-01-05 18:44:24', 132),
(35, '2024-01-06 22:25:17', 134),
(37, '2024-01-06 22:42:01', 134),
(38, '2024-01-06 22:42:25', 134),
(39, '2024-01-07 18:10:23', 137),
(40, '2024-01-07 18:11:13', 137),
(41, '2024-01-08 17:34:53', 137),
(42, '2024-01-10 19:20:06', 141),
(43, '2024-01-11 21:18:20', 142),
(44, '2024-01-14 21:58:01', 147),
(45, '2024-01-14 21:58:07', 146),
(46, '2024-01-14 21:58:14', 143),
(47, '2024-01-14 21:58:20', 141),
(48, '2024-01-14 21:58:27', 12),
(49, '2024-01-14 21:58:38', 14),
(50, '2024-01-14 21:59:04', 71),
(51, '2024-01-14 21:59:05', 71),
(52, '2024-01-14 21:59:05', 71),
(53, '2024-01-14 21:59:06', 71),
(54, '2024-01-14 21:59:06', 71),
(55, '2024-01-14 21:59:06', 71),
(56, '2024-01-14 21:59:06', 71),
(57, '2024-01-14 21:59:06', 71),
(58, '2024-01-14 21:59:06', 71),
(59, '2024-01-14 21:59:07', 71),
(60, '2024-01-14 21:59:07', 71),
(61, '2024-01-14 21:59:07', 71),
(62, '2024-01-14 21:59:23', 145),
(63, '2024-01-14 22:00:03', 147);

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
(3, 'Quispe', '2023-05-10', '09:00 AM - 10:00 AM', 'LOL'),
(4, 'Quispe', '2023-05-10', '09:00 AM - 10:00 AM', 'LOL'),
(6, 'Quispe', '2005-11-19', '09:00 AM - 10:00 AM', 'ewe'),
(7, 'Quispe', '2005-11-19', '09:00 AM - 10:00 AM', 'ewe'),
(8, 'Quispe', '2023-05-10', '09:00 AM - 10:00 AM', 'ewe'),
(9, 'Quispe', '2023-11-04', '09:00 AM - 10:00 AM', 'ewe'),
(10, 'Bad Bunny', '2023-11-05', '09:00 AM - 10:00 AM', 'BOX'),
(11, 'Jano Flores Rivera', '2023-11-05', '19:00 PM - 20:00 PM', 'XBOX'),
(12, 'Carlos Sipan', '2023-11-05', '09:00 AM - 10:00 AM', 'caldito'),
(16, 'Carlos Sipan', '2023-11-06', '09:00 AM - 10:00 AM', 'caldito'),
(17, 'Quispe', '2023-11-06', '09:00 AM - 10:00 AM', 'XBOX');

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
(2, 'Jeancarlos Daniel', 'Rios Gonzales', 'wtke90@gmail.com', '75117250', '979835789', 0),
(4, 'Angie', 'Echegaray Ayala', '0333202010@unjfsc.edu.pe', '71960755', '987543123', 0),
(5, 'Alejandro', 'Rivero Gamarra', '0333211024@unjfsc.edu.pe', '76534567', '985345128', 0),
(6, 'ALEJANDRO ', 'RIVERO GAMARRA', 'janolibra@gmail.com', '70856878', '56476338', 0),
(8, 'KIKE', 'CHINGA RAMOS', 'chinga@gmail.com', '70856878', '56476338', 3),
(10, 'JOEL ', 'GONZALES MANTILLA', 'bebitofiufiu@gmail.com', '71646979', '16497321', 0),
(11, 'CARLOS JESUS', 'SIPAN LOZANO', 'caldito@gmaYl.com', '76946947', '914346784', 0),
(12, 'GIANCARLO ANTONIO', 'QUINTANA ACUÑA', 'yisus@gmail.com', '70861979', '914611678', 1),
(13, 'NICOLAS RODRIGO', 'REQUENA CASTRO', 'nicolasrc1403@gmail.com', '72479565', '957985445', 0),
(14, 'RAFAEL ADONIS', 'FLORES RIVERA', 'rafael@gmail.com', '70134451', '987654321', 1),
(15, 'Delci Denis ', 'Vilcahuaman Perez', 'Delcid@gmail.com', '70314521', '987654321', 0),
(16, 'Pezzini', 'Agostin', '0333202019@unjfsc.edu.pe', '72324985', '961368050', 0),
(17, 'ROGER2', 'CORTEZ VASQUEZ', '12@gmail.com', '232445', '976123456', 0),
(18, 'sdadsa', 'dsaasd', 'dsaads', '3232', '233223', 0),
(24, 'dsadsa', 'das', 'dsa', 'dsadasads', 'sadas', 0),
(29, 'ROGER', 'CORTEZ VASQUEZ', '', '75117250', '976123456', 3),
(31, 'ISABEL', 'GONZALES', 'isa@gmail.com', '15617334', '78484848', 0),
(32, 'ISABEL', 'GONZALES', 'isa@gmail.com', '15617334', '78484848', 3),
(33, 'Nicolas ', 'RC', 'nicolasrc1403@gmail.com', '15617334', '78484848', 3),
(34, '', 'undefined undefined', 'nicolasrc1403@gmail.com', '15617334', '78484848', 3),
(35, 'Martine', 'Lopez', 'martin@gmail.com', '82828282', '967227282', 0),
(36, 'carlos', 'chinga', 'cchinga@unjfsc.edu.pe', '75117250', '976123456', 3),
(37, 'JHEREL ADONNYS', 'PEÑA AMABLE', 'wtke90@gmail.coms', '75481231', '946228564', 0),
(38, 'REYNA ROSA', 'SANCHEZ ESCOLASTICA', 'Juan@gmail.com', '15847172', '948123147', 0),
(39, 'Nicovaco', 'Nicovaco', 'ds@gmail.com', '15617334', '079849494', 3),
(40, 'REYNA ROSA', 'SANCHEZ ESCOLASTICA', 'Juan@gmail.com', '15847172', '948123147', 3),
(41, 'Pezzini', 'Agostin', 'pelona@gmail.com', '71718282', '8788484', 0),
(42, 'BRITNEY DEBIE', 'RODRIGUEZ CUSI', 'Juan@gmail.com', '75117171', '948123147', 0),
(43, 'DIANA VALERIA', 'COILA PACHECO', 'Juan@gmail.com', '71711212', '948123147', 0),
(45, 'JAIRO', 'QUISPE ALTAMIRANO', 'Juan@gmail.com', '71724846', '948123147', 0),
(46, 'LAURA GABRIELA', 'CELIS ODAR', 'Juan@gmail.com', '71234748', '948123147', 0),
(47, 'PRINCIPE CIRO', 'SOTO ROMERO', 'Juan@gmail.com', '15627332', '948123147', 0),
(48, 'CLARA LUZ', 'VASQUEZ MARTINEZ', 'Juan@gmail.com', '71127895', '948123147', 0),
(49, 'ANGELO EMILIANO', 'CHAVEZ CASTAÑEDA', 'Juan@gmail.com', '759911813', '948123147', 0),
(50, 'DEISY ROCIO', 'DIESTRA JARAMILLO', 'Juan@gmail.com', '71129569', '948123147', 0),
(52, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'wtke90@gmail.com', '75117250', '946228564', 3),
(55, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'wtke90@gmail.com', '75117250', '946228564', 3),
(57, 'JOSE PEDRO', 'CASTILLO TERRONES', 'Juan@gmail.com', '27427864', '948123147', 0),
(58, 'KENYI MARJORIE', 'CHURA ADUVIRI', 'Juan@gmail.com', '71727345', '948123147', 0),
(59, 'WILLIAM MANUEL', 'MORALES FERNANDEZ', 'Juan@gmail.com', '45781235', '948123147', 0),
(60, 'ROGER', 'CORTEZ VASQUEZ', 'nicolasrc1403@gmail.com', '2324451248', '976123456', 3),
(61, 'RICARDO', 'MARTINEZ REMON', 'wtke90@gmail.com', '28447131', '946228564', 0),
(63, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(64, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(65, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(66, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(67, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(68, 'ROGER', 'CORTEZ VASQUEZ', 'nicolasrc1403@gmail.com', '2324451248', '976123456', 0),
(69, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '976123456', 3),
(70, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '2', 3),
(71, 'KIARA MELIZA', 'FELIPA YATACO', 'Juan@gmail.com', '46944616', '948123147', 1),
(74, 'BRICETH DENIXSA', 'IPANAQUE SANCHEZ', 'Juan@gmail.com', '75848842', '948123147', 1),
(76, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'XxxrogersitoProXXx@gmail.com', '74123344', '949744191', 1),
(78, 'MARIANA', 'TARAZONA LOPEZ', 'Juan@gmail.com', '76161612', '948123147', 0),
(79, 'carlos', 'qwq', 'Juan@gmail.com', '22323223', '948123147', 0),
(80, 'KAROL MARGOTH', 'PARIZACA ARCE', 'Juan@gmail.com', '75117290', '948123147', 1),
(81, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 0),
(82, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(83, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(84, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(85, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(86, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(87, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(88, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(90, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(91, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(92, 'KEIKO SOFIA', 'FUJIMORI HIGUCHI', 'Juan@gmail.com', '10001088', '948123147', 3),
(93, 'KAROL MARGOTH', 'PARIZACA ARCE', 'Juan@gmail.com', '75117290', '948123147', 3),
(94, 'KAROL MARGOTH', 'PARIZACA ARCE', 'Juan@gmail.com', '75117290', '948123147', 3),
(95, 'KAROL MARGOTH', 'PARIZACA ARCE', 'Juan@gmail.com', '75117290', '948123147', 3),
(97, 'ANA MARIA', 'CARDENAS CORDOVA', 'Juan@gmail.com', '23232323', '948123147', 0),
(98, 'ANGIE MADHELEY', 'CARBONEL IÑOÑAN', 'Juan@gmail.com', '75181133', '948123147', 0),
(99, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(100, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(101, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(102, 'JHONATHAN', 'MAMANI TAFUR', 'Juan@gmail.com', '75172051', '948123147', 1),
(103, 'MARIEL NICOLE', 'BARDALES ALVITES', 'Juan@gmail.com', '75118280', '948123147', 0),
(104, 'LENY SARAI', 'CALDERON ZULUETA', 'Juan@gmail.com', '75811234', '948123147', 1),
(105, 'LENY SARAI', 'CALDERON ZULUETA', 'Juan@gmail.com', '75811234', '948123147', 3),
(106, 'ERIKA FABIOLA', 'CAPIA BERNAL', 'Juan@gmail.com', '71991222', '948123147', 1),
(107, 'Jair', 'PEZO CORDOVA', 'Juan@gmail.com', '48449774', '948123147', 0),
(108, 'CELESTINO', 'PEREZ LOAYZA', 'celestino@gmail.com', '72459788', '948123147', 1),
(109, 'BRAYAN DAVID', 'CERECEDA PEÑA', 'brayan@gmail.com', '77595729', '948123147', 1),
(110, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(111, 'ROYNNER GUILLERMO', 'RAMIREZ QUINTEROS', 'Juan@gmail.com', '75172505', '948123147', 0),
(112, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(113, 'JIMMY IVAN', 'ZURITA LA ROSA', 'Juan@gmail.com', '15617494', '948123147', 0),
(114, 'KEVIN ALBERTO MANUEL', 'FRANCO CASANOVA', 'Juan@gmail.com', '75117256', '948123147', 0),
(115, 'MARCO JUSTO', 'SHUÑA NUÑEZ', 'Juan@gmail.com', '74199944', '948123147', 0),
(116, 'carlos', 'jair', 'cchinga@unjfsc.edu.po', '78494991', '948123147', 0),
(117, 'TEOBALDO VISITACION', 'AVELINO BEJARANO', 'Juan@gmail.com', '17979799', '948123147', 3),
(118, 'JEDER LLANELI', 'ANGULO CHUIMES', 'Juan@gmail.com', '47849494', '948123147', 0),
(119, 'PATRICK ADRIAN', 'SACRAMENTO AREVALO', 'Juan@gmail.com', '75194991', '948123147', 0),
(120, 'MELANY YANINA', 'TALLA HERNANDEZ', 'Juan@gmail.com', '75187849', '948123147', 0),
(121, 'Nombre no encontrado', 'undefined undefined', 'Juan@gmail.com', '79494949', '948123147', 3),
(122, 'VALERIA XIMENA', 'RODRIGUEZ GARCIA', 'Juan@gmail.com', '75118481', '948123147', 0),
(123, 'Nombre no encontrado', 'undefined undefined', 'Juan@gmail.com', '19494949', '948123147', 3),
(124, 'TEOBALDO VISITACION', 'AVELINO BEJARANO', 'Juan@gmail.com', '17979799', '948123147', 0),
(125, 'MARIA ALBINA', 'LOPEZ AZAÑERO', 'maria@gmail.com', '15617771', '948123147', 1),
(126, 'MARIA FERNANDA', 'CALVA TAVARA', 'XxxrogersitoProXXx@gmail.com', '74123344', '8788484', 3),
(127, 'FLORENTINO ANDRES', 'ESPINOZA CHIPARRA', 'florentino@gmail.com', '15674941', '948123147', 1),
(128, 'JANET MERY', 'MERCADO TORRES', 'janet@gmail.com', '74164812', '948123147', 1),
(129, 'MARIA FERNANDA', 'CALVA TAVARA', 'XxxrogersitoProXXx@gmail.com', '74123344', '8788484', 3),
(130, 'MARIA FERNANDA', 'CALVA TAVARA', 'XxxrogersitoProXXx@gmail.com', '74123344', '8788484', 3),
(131, 'MAYRA LIZBETH', 'MENDEZ TIRADO', 'mayra@gmail.com', '71811619', '948123147', 1),
(132, 'LEYDI JESUS', 'CARHUAPOMA UMBO', 'leidy@gmail.com', '71124548', '948123147', 1),
(133, 'JEANCARLOS DANIEL', 'RIOS GONZALES', 'Juan@gmail.com', '75117250', '948123147', 3),
(134, 'JUAN', 'ALEJANDRO ROMERO', 'juanPE@gmail.com', '15661741', '948123147', 1),
(136, 'Angie', 'HUERTAS TIPARRA', 'x@gmail.com', '75117111', '948123147', 1),
(137, 'Martin', 'Velasquez Bernal', 'mar@gmail.com', '75117299', '948112344', 1),
(138, 'Ana Kristhina', 'Ramirez Eustaquio', 'x@gmail.com', '76325926', '994785524', 1),
(139, 'Gianella Elizabeth ', 'Requena Marin ', 'x@gmail.com', '75147319', '946700770', 1),
(140, 'Mis Cristina', 'Requena Tello', '2@gmail.com', '72669804', '914258426', 1),
(141, 'LEONARDO AARON', 'ASENCIOS NIERY', '12@gmail.com', '73706790', '929416869', 1),
(142, 'Iker Gianluigi', 'Reyes Mena', '213@gmail.com', '74241926', '923614527', 1),
(143, 'Fabiola', 'Bances Ramos', 'fabiola.bances.ramos@gmail.com', '79218637', '940828986', 1),
(144, 'Edy Guillermo', 'Zenozain Garay', 'edychemo@hotmail.com', '72772672', '936374763', 1),
(145, 'Milo', 'J', 'miloj@gmail.com', '71234596', '948778484', 1),
(146, 'Jamira ', 'Vega Piñan', 'v@gmail.com', '76725068', '948123147', 1),
(147, 'Jennifer Kaory', 'Villarreal Alvaro ', 'j@gmail.com', '73870460', '923817231', 1);

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
(2, 'Carlos Jesus', 'Sipan Lozano', '12345678', 'Atencion al Cliente', '987654321', 'Contratado'),
(3, 'Ramon', 'Dino', '75117250', 'Admin', '946228564', 'Contratado');

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
(7, '2023-11-01', '2023-11-06', 123, 150, 16, 1, 7),
(8, '2023-11-01', '2023-11-06', 123, 150, 16, 1, 8),
(9, '2023-11-05', '2024-02-03', 123, 150, 31, 1, 9),
(10, '2023-11-05', '2024-02-03', 123, 150, 31, 1, 10),
(11, '2023-11-05', '2024-02-03', 123, 150, 32, 1, 11),
(12, '2023-11-09', '2024-04-07', 205, 250, 33, 1, 12),
(13, '2023-11-09', '2024-04-07', 205, 250, 33, 1, 13),
(14, '2023-11-06', '2024-02-04', 123, 150, 34, 1, 14),
(15, '2023-11-06', '2024-02-04', 123, 150, 35, 1, 15),
(16, '2023-11-06', '2024-02-04', 123, 150, 36, 1, 16),
(17, '2023-11-20', '2024-02-18', 123, 150, 37, 1, 17),
(18, '2023-12-04', '2024-03-03', 123, 150, 38, 1, 18),
(19, '2023-12-04', '2024-03-03', 123, 150, 39, 1, 19),
(20, '2023-12-04', '2024-03-03', 123, 150, 40, 1, 20),
(21, '2024-01-04', '2024-04-03', 123, 150, 68, 1, 21),
(22, '2024-01-04', '2024-04-03', 123, 150, 76, 1, 22),
(23, '2024-01-04', '2024-04-03', 123, 150, 126, 1, 24),
(24, '2024-01-03', '2024-04-02', 123, 150, 127, 1, 25),
(25, '2024-01-03', '2024-04-02', 123, 150, 128, 1, 26),
(26, '2024-01-04', '2024-04-03', 123, 150, 129, 1, 27),
(27, '2024-01-04', '2024-04-03', 123, 150, 130, 1, 28),
(28, '2024-01-03', '2024-04-02', 123, 150, 131, 1, 29),
(29, '2024-01-03', '2024-04-02', 123, 150, 132, 1, 30),
(30, '2024-01-03', '2024-04-02', 123, 150, 133, 1, 31),
(31, '2024-01-11', '2024-02-10', 56.58, 69, 134, 1, 32),
(32, '2023-12-31', '2024-03-30', 123, 150, 137, 1, 33),
(33, '2024-01-06', '2024-04-05', 123, 150, 134, 1, 34),
(34, '2024-01-06', '2024-02-05', 56.58, 69, 125, 1, 35),
(35, '2024-03-06', '2024-08-03', 205, 250, 125, 1, 36),
(36, '2024-01-06', '2024-04-05', 123, 150, 137, 1, 37),
(37, '2024-01-08', '2024-04-07', 123, 150, 138, 1, 38),
(38, '2024-01-31', '2024-06-29', 205, 250, 139, 1, 39),
(39, '2024-02-01', '2024-05-01', 123, 150, 140, 1, 40),
(40, '2023-07-08', '2023-10-06', 123, 150, 141, 1, 41),
(41, '2024-01-10', '2024-04-09', 123, 150, 142, 1, 42),
(42, '2024-02-10', '2024-05-10', 123, 150, 140, 1, 43),
(43, '2024-01-10', '2024-06-08', 205, 250, 144, 1, 44),
(44, '2023-05-31', '2023-08-29', 123, 150, 145, 1, 45),
(45, '2024-01-13', '2024-04-12', 123, 150, 146, 1, 46),
(46, '2024-01-13', '2024-04-12', 123, 150, 147, 1, 47);

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
(3, 10, 1, '2023-11-04', '2023-12-04'),
(4, 11, 1, '2023-11-23', '2023-12-23'),
(5, 12, 1, '2023-11-21', '2023-12-21'),
(6, 13, 1, '2023-11-02', '2023-11-18'),
(7, 16, 1, '2023-11-01', '2023-11-06'),
(8, 31, 1, '2023-11-05', '2024-02-03'),
(9, 32, 1, '2023-11-05', '2024-02-03'),
(10, 34, 1, '2023-11-06', '2024-02-04'),
(11, 35, 1, '2023-11-06', '2024-02-04'),
(12, 36, 1, '2023-11-06', '2024-02-04'),
(13, 37, 1, '2023-11-20', '2024-02-18'),
(14, 38, 1, '2023-12-04', '2024-03-03'),
(15, 39, 1, '2023-12-04', '2024-03-03'),
(16, 40, 1, '2023-12-04', '2024-03-03'),
(17, 60, 2, '2024-01-04', '2024-06-02'),
(18, 68, 1, '2024-01-04', '2024-04-03'),
(20, 80, 1, '2024-01-03', '2024-04-02'),
(21, 81, 3, '2024-01-03', '2024-02-02'),
(22, 82, 1, '2024-01-03', '2024-04-02'),
(23, 83, 1, '2024-01-03', '2024-04-02'),
(24, 84, 1, '2024-01-03', '2024-04-02'),
(25, 85, 3, '2024-01-03', '2024-02-02'),
(26, 86, 3, '2024-01-03', '2024-02-02'),
(27, 87, 2, '2024-01-03', '2024-06-01'),
(28, 88, 1, '2024-01-03', '2024-04-02'),
(30, 90, 3, '2024-01-03', '2024-02-02'),
(31, 91, 1, '2024-01-03', '2024-04-02'),
(32, 92, 2, '2024-01-03', '2024-06-01'),
(33, 93, 3, '2024-01-03', '2024-02-02'),
(34, 94, 1, '2024-01-03', '2024-04-02'),
(35, 95, 3, '2024-01-03', '2024-02-02'),
(37, 97, 1, '2024-01-03', '2024-04-02'),
(38, 98, 1, '2024-01-03', '2024-04-02'),
(39, 99, 1, '2024-01-03', '2024-04-02'),
(40, 100, 1, '2024-01-03', '2024-04-02'),
(41, 101, 2, '2024-01-03', '2024-06-01'),
(42, 102, 1, '2024-01-03', '2024-04-02'),
(43, 103, 2, '2024-01-03', '2024-06-01'),
(44, 104, 2, '2024-01-09', '2024-06-07'),
(45, 105, 2, '2024-01-03', '2024-06-01'),
(46, 106, 1, '2024-01-03', '2024-04-02'),
(47, 107, 1, '2024-01-03', '2024-04-02'),
(48, 108, 3, '2024-01-03', '2024-02-02'),
(49, 109, 1, '2024-01-03', '2024-04-02'),
(50, 111, 1, '2024-01-03', '2024-04-02'),
(51, 112, 2, '2024-01-03', '2024-06-01'),
(53, 114, 2, '2024-01-03', '2024-06-01'),
(54, 115, 2, '2024-01-03', '2024-06-01'),
(56, 117, 3, '2024-01-03', '2024-02-02'),
(57, 118, 2, '2024-01-03', '2024-06-01'),
(58, 120, 1, '2024-01-03', '2024-04-02'),
(59, 121, 2, '2024-01-03', '2024-06-01'),
(60, 122, 2, '2024-01-03', '2024-06-01'),
(61, 123, 3, '2024-01-03', '2024-02-02'),
(62, 124, 3, '2024-01-03', '2024-02-02'),
(68, 129, 1, '2024-01-04', '2024-04-03'),
(71, 132, 1, '2024-01-03', '2024-04-02'),
(72, 133, 1, '2024-01-03', '2024-04-02'),
(73, 134, 3, '2024-01-11', '2024-02-10'),
(74, 137, 1, '2023-12-31', '2024-03-30'),
(75, 134, 1, '2024-01-06', '2024-04-05'),
(76, 125, 3, '2024-01-06', '2024-02-05'),
(77, 125, 2, '2024-03-06', '2024-08-03'),
(78, 137, 1, '2024-01-06', '2024-04-05'),
(79, 138, 1, '2024-01-08', '2024-04-07'),
(80, 139, 2, '2024-01-31', '2024-06-29'),
(81, 140, 1, '2024-02-01', '2024-05-01'),
(82, 141, 1, '2023-07-08', '2023-10-06'),
(83, 142, 1, '2024-01-10', '2024-04-09'),
(84, 140, 1, '2024-02-10', '2024-05-10'),
(85, 144, 2, '2024-01-10', '2024-06-08'),
(86, 145, 1, '2023-05-31', '2023-08-29'),
(87, 146, 1, '2024-01-13', '2024-04-12'),
(88, 147, 1, '2024-01-13', '2024-04-12');

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
(3, 'Básico', '1');

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
(3, '2023-11-02', 150, 'Tarjeta', 3),
(4, '2023-11-02', 150, 'Tarjeta', 4),
(5, '2023-11-02', 150, 'Tarjeta', 5),
(6, '2023-11-03', 150, 'Tarjeta', 6),
(7, '2023-11-04', 150, 'Tarjeta', 7),
(8, '2023-11-04', 150, 'Tarjeta', 7),
(9, '2023-11-04', 150, 'Tarjeta', 8),
(10, '2023-11-04', 150, 'Tarjeta', 8),
(11, '2023-11-04', 150, 'Tarjeta', 9),
(15, '2023-11-05', 150, 'Tarjeta', 11),
(16, '2023-11-05', 150, 'Tarjeta', 12),
(17, '2023-11-19', 150, 'Tarjeta', 13),
(18, '2023-12-03', 150, 'Tarjeta', 14),
(21, '2024-01-03', 150, 'Tarjeta', 18),
(22, '2024-01-03', 150, 'Tarjeta', 63),
(23, '2024-01-03', 250, 'Tarjeta', 64),
(25, '2024-01-03', 150, 'Tarjeta', 66),
(28, '2024-01-03', 150, 'Efectivo', 69),
(29, '2024-01-03', 150, 'Tarjeta', 70),
(30, '2024-01-03', 150, 'Tarjeta', 71),
(32, '2024-01-03', 69, 'Tarjeta', 73),
(33, '2024-01-06', 150, 'Tarjeta', 74),
(34, '2024-01-06', 150, 'Efectivo', 75),
(35, '2024-01-06', 69, 'Tarjeta', 76),
(36, '2024-01-06', 250, 'Tarjeta', 77),
(37, '2024-01-06', 150, 'Tarjeta', 78),
(38, '2024-01-08', 150, 'Tarjeta', 79),
(39, '2024-01-08', 250, 'Tarjeta', 80),
(40, '2024-01-08', 150, 'Tarjeta', 81),
(41, '2024-01-08', 150, 'Tarjeta', 82),
(42, '2024-01-10', 150, 'Tarjeta', 83),
(43, '2024-01-10', 150, 'Efectivo', 84),
(44, '2024-01-10', 250, 'Tarjeta', 85),
(45, '2024-01-13', 150, 'Tarjeta', 86),
(46, '2024-01-13', 150, 'Tarjeta', 87),
(47, '2024-01-13', 150, 'Tarjeta', 88);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prueba`
--

CREATE TABLE `prueba` (
  `codigo` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `registro` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `prueba`
--

INSERT INTO `prueba` (`codigo`, `nombre`, `registro`) VALUES
(1, 'Daniel', '2023-12-12'),
(2, 'Janoel', '2023-05-11');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `codRol` int(11) NOT NULL,
  `nombRol` varchar(50) NOT NULL,
  `fechRol` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`codRol`, `nombRol`, `fechRol`) VALUES
(1, 'Admin', '2023-12-04'),
(2, 'Asistente', '2023-12-04'),
(3, 'Contador', '2023-12-05'),
(5, 'Analista', '2023-12-04');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolusuario`
--

CREATE TABLE `rolusuario` (
  `codRol` int(11) NOT NULL,
  `codiUsua` int(11) NOT NULL,
  `actvRol` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `rolusuario`
--

INSERT INTO `rolusuario` (`codRol`, `codiUsua`, `actvRol`) VALUES
(1, 3, 0),
(1, 3, 1),
(2, 1, 1),
(4, 2, 0),
(4, 2, 1);

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
(1, 'dachi', 'fe2592b42a727e977f055947385b709cc82b16b9a87f88c6abf3900d65d0cdc3', 'Activo', 'asistente', '2023-10-28', 1, 'WPOHPKSC3GKBTXCYWTPHETMKOBLE4KO6'),
(2, 'tUki', 'ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad', 'Activo', 'asistente', '2023-10-28', 2, '5LV3JYAOOUPOBUO7O5WZAFA3X6TYIJ44'),
(4, 'root', 'fe2592b42a727e977f055947385b709cc82b16b9a87f88c6abf3900d65d0cdc3', 'Activo', 'admin', '2023-11-04', 3, 'PYPY7VX7RXQQ7BJMO7MWB6BQ24YUFIWI');

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
-- Indices de la tabla `prueba`
--
ALTER TABLE `prueba`
  ADD PRIMARY KEY (`codigo`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`codRol`);

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
  MODIFY `idAsistencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT de la tabla `clases`
--
ALTER TABLE `clases`
  MODIFY `idClases` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `idCliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=148;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `idEmpleado` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `idFactura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT de la tabla `matricula`
--
ALTER TABLE `matricula`
  MODIFY `idMatricula` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=89;

--
-- AUTO_INCREMENT de la tabla `membresia`
--
ALTER TABLE `membresia`
  MODIFY `idMembresia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pago`
--
ALTER TABLE `pago`
  MODIFY `idPago` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT de la tabla `prueba`
--
ALTER TABLE `prueba`
  MODIFY `codigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `codRol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `codiUsua` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

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
