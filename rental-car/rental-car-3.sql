-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jun 08, 2024 at 09:57 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `rental-car`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `ID` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `Type` varchar(50) NOT NULL DEFAULT 'user',
  `Phone` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `gender` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`ID`, `name`, `Type`, `Phone`, `password`, `email`, `gender`) VALUES
(1, 'mujahed', 'user', '1234', '12', 'mujahed', 'male'),
(12, 'm a', 'user', 'ma', '123', '1', 'Male'),
(32, 'issa', 'user', '32', '123', 'issa', 'male'),
(123, 'Ahmed', 'admin', '123456', '123', 'ahmed', 'male'),
(124, 'rasha mansor', 'user', 'rasha', '123', 'rasha', 'Female'),
(125, 'taha taha1', 'user', '12345', '123', 'taha', 'Male');

-- --------------------------------------------------------

--
-- Table structure for table `car`
--

CREATE TABLE `car` (
  `ID` int(11) NOT NULL,
  `company` varchar(50) NOT NULL,
  `Model_year` varchar(50) NOT NULL,
  `Mileage` int(11) NOT NULL,
  `SeatsNumber` int(11) NOT NULL,
  `MonthlyPrice` int(11) NOT NULL,
  `DailyPrice` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `color` varchar(50) NOT NULL,
  `status` varchar(50) NOT NULL,
  `image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `car`
--

INSERT INTO `car` (`ID`, `company`, `Model_year`, `Mileage`, `SeatsNumber`, `MonthlyPrice`, `DailyPrice`, `price`, `color`, `status`, `image`) VALUES
(1, 'Chevrolet', 'Spark 2021', 1000, 4, 1200, 10000, 13000, 'red', 'Free', 'rental-car/images/chev.jpg'),
(2, 'Seat', 'Leon 2021', 40000, 4, 2000, 300, 160000, 'black', 'Free', 'rental-car/images/seat.jpg'),
(3, 'Swift', 'q1 2022', 20000, 3, 3000, 350, 30000, 'blue', 'Free', 'rental-car/images/swift.jpg'),
(7, 'Skoda', 'Octavia 2021', 20000, 1, 12000, 500, 80000, 'Red', 'Free', 'rental-car/images/skoda.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `rent`
--

CREATE TABLE `rent` (
  `ID` int(11) NOT NULL,
  `carID` int(11) NOT NULL,
  `AccountID` int(11) NOT NULL,
  `TotalPrice` int(11) NOT NULL,
  `status` varchar(50) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `car`
--
ALTER TABLE `car`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `rent`
--
ALTER TABLE `rent`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `carID` (`carID`),
  ADD KEY `AccountID` (`AccountID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `account`
--
ALTER TABLE `account`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=126;

--
-- AUTO_INCREMENT for table `car`
--
ALTER TABLE `car`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `rent`
--
ALTER TABLE `rent`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `rent`
--
ALTER TABLE `rent`
  ADD CONSTRAINT `rent_ibfk_1` FOREIGN KEY (`AccountID`) REFERENCES `account` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
