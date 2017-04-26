-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2017 at 09:00 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hotel`
--

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `reservation_id` int(7) NOT NULL,
  `room_no` int(3) NOT NULL,
  `user_id` int(6) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `paid_status` varchar(5) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`reservation_id`, `room_no`, `user_id`, `start_date`, `end_date`, `paid_status`) VALUES
(1, 101, 100001, '2017-04-27', '2017-05-03', '1'),
(2, 102, 100002, '2017-04-26', '2017-05-06', '1'),
(3, 103, 100003, '2017-04-24', '2017-05-09', '0'),
(4, 104, 100004, '2017-04-29', '2017-04-30', '1'),
(5, 105, 100005, '2017-05-03', '2017-05-07', '1'),
(6, 106, 100006, '2017-05-08', '2017-05-13', '0'),
(7, 107, 100007, '2017-04-24', '2017-04-27', '1'),
(8, 108, 100008, '2017-04-10', '2017-04-15', '1'),
(9, 109, 100009, '2017-04-01', '2017-04-21', '1'),
(10, 201, 100010, '2017-06-07', '2017-06-16', '0'),
(11, 202, 100010, '2017-07-11', '2017-08-03', '0'),
(12, 203, 100011, '2017-05-19', '2017-06-13', '0'),
(15, 408, 100001, '2017-04-30', '2017-05-19', '1'),
(14, 409, 100001, '2017-04-30', '2017-05-19', '1'),
(13, 204, 1, '2017-04-30', '2017-05-19', '1'),
(16, 308, 100001, '2017-04-30', '2017-05-19', '1'),
(17, 307, 100001, '2017-04-30', '2017-05-19', '1'),
(18, 100, 100001, '2017-04-30', '2017-05-19', '1'),
(19, 205, 100001, '2017-04-30', '2017-05-19', '1'),
(20, 206, 100001, '2017-04-30', '2017-05-19', '1'),
(21, 305, 100001, '2017-04-30', '2017-05-19', '1'),
(22, 303, 100001, '2017-04-30', '2017-05-19', '1'),
(24, 304, 100001, '2017-04-30', '2017-05-19', '1'),
(23, 302, 100001, '2017-04-30', '2017-05-19', '1'),
(25, 401, 100001, '2017-04-30', '2017-05-19', '1'),
(26, 301, 100001, '2017-04-30', '2017-05-19', '1');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `room_no` int(3) NOT NULL,
  `type` varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `status` varchar(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `booked` tinyint(1) NOT NULL,
  `reservation_id` int(7) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`room_no`, `type`, `status`, `booked`, `reservation_id`) VALUES
(101, 'simgle', 'free', 1, 0),
(102, 'double', 'free', 1, 0),
(103, 'single', 'in use', 1, 3),
(104, 'single', 'free', 1, 0),
(105, 'single', 'free', 1, 0),
(106, 'studio', 'free', 0, 0),
(107, 'studio', 'free', 0, 0),
(108, 'double', 'free', 1, 0),
(109, 'double', 'free', 1, 0),
(201, 'single', 'free', 1, 0),
(202, 'single', 'free', 1, 0),
(203, 'studio', 'free', 0, 0),
(204, 'studio', 'free', 1, 0),
(205, 'double', 'free', 1, 19),
(206, 'double', 'free', 1, 20),
(207, 'double', 'free', 0, 0),
(208, 'double', 'free', 0, 0),
(209, 'double', 'free', 0, 0),
(301, 'studio', 'free', 1, 26),
(302, 'studio', 'free', 1, 23),
(303, 'studio', 'free', 1, 22),
(304, 'studio', 'free', 1, 23),
(305, 'single', 'free', 1, 21),
(306, 'single', 'free', 1, 100001),
(307, 'single', 'free', 1, 0),
(308, 'single', 'free', 1, 0),
(309, 'double', 'free', 1, 0),
(401, 'double', 'free', 1, 25),
(402, 'double', 'free', 0, 0),
(403, 'double', 'free', 0, 0),
(404, 'double', 'free', 0, 0),
(405, 'studio', 'free', 0, 0),
(406, 'studio', 'free', 0, 0),
(407, 'studio', 'free', 0, 0),
(408, 'single', 'free', 1, 0),
(409, 'single', 'free', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `Login` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `user_id` int(6) NOT NULL,
  `admin` tinyint(1) NOT NULL,
  `first_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `postcode` varchar(7) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(40) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`Login`, `password`, `user_id`, `admin`, `first_name`, `last_name`, `city`, `postcode`, `email`, `phone`) VALUES
('admin', 'admin', 0, 1, '', '', '', '', '', ''),
('empl1', '1234', 1, 1, '', '', '', '', '', ''),
('empl2', '1234', 2, 0, '', '', '', '', '', ''),
('empl3', '1234', 3, 0, '', '', '', '', '', ''),
('empl4', '1234', 4, 0, '', '', '', '', '', ''),
('empl5', '1234', 5, 0, '', '', '', '', '', ''),
('empl6', '1234qW', 6, 0, '', '', '', '', '', ''),
('empl7', 'qwE@3', 7, 0, '', '', '', '', '', ''),
('client1', 'qwerty123', 100001, 0, '', '', '', '', '', ''),
('client2', 'qwerty123', 100002, 0, '', '', '', '', '', ''),
('client3', 'qwerty123', 100003, 0, '', '', '', '', '', ''),
('client4', 'qwerty123', 100004, 0, '', '', '', '', '', ''),
('client5', 'qwerty123', 100005, 0, '', '', '', '', '', ''),
('client6', 'qwerty123', 100006, 0, '', '', '', '', '', ''),
('client7', 'qwerty123', 100007, 0, '', '', '', '', '', ''),
('client8', 'qwerty123', 100008, 0, '', '', '', '', '', ''),
('client9', 'qwerty123', 100009, 0, '', '', '', '', '', ''),
('client10', 'qwerty123', 100010, 0, '', '', '', '', '', ''),
('empl8', '12345', 8, 0, '', '', '', '', '', ''),
('empl9', '1234', 9, 0, '', '', '', '', '', ''),
('client11', 'qwerty123', 100011, 0, '', '', '', '', '', ''),
('client12', 'qwerty123', 100012, 0, '', '', '', '', '', ''),
('client13', 'qwerty123', 100013, 0, '', '', '', '', '', ''),
('b', 'b', 100014, 0, 'b', 'b', 'b', 'b', 'b', 'b'),
('bb', 'bb', 100015, 0, 'b', 'b', 'b', 'b', 'b', 'b'),
('q', 'q', 100016, 0, 'q', 'q', 'q', 'q', 'q', 'q');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`reservation_id`),
  ADD UNIQUE KEY `room_no` (`room_no`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `room_no_2` (`room_no`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`room_no`),
  ADD UNIQUE KEY `room_no` (`room_no`),
  ADD KEY `rooms_reservations_reservation_id_fk` (`reservation_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`Login`),
  ADD UNIQUE KEY `Login` (`Login`),
  ADD UNIQUE KEY `user_id` (`user_id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
