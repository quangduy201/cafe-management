-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 07, 2023 at 01:02 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qlcafe`
--

-- --------------------------------------------------------

--
-- Table structure for table `account`
--

CREATE TABLE `account` (
  `USERNAME` varchar(20) NOT NULL,
  `PASSWD` varchar(20) DEFAULT NULL,
  `DECENTRALIZATION_ID` varchar(10) DEFAULT NULL,
  `STAFF_ID` varchar(10) DEFAULT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`USERNAME`, `PASSWD`, `DECENTRALIZATION_ID`, `STAFF_ID`, `DELETED`) VALUES
('NGUYENVANA', '123456', '001', 'NV001', 0x30),
('NGUYENVANB', '123456', '002', 'NV002', 0x30),
('NGUYENVANC', '123456', '003', 'NV003', 0x30),
('NGUYENVAND', '123456', '004', 'NV004', 0x30),
('NGUYENVANE', '123456', '005', 'NV005', 0x30),
('NGUYENVANF', '123456', '006', 'NV006', 0x30),
('NGUYENVANG', '123456', '007', 'NV007', 0x30),
('NGUYENVANH', '123456', '008', 'NV008', 0x30),
('NGUYENVANI', '123456', '009', 'NV009', 0x30),
('NGUYENVANK', '123456', '010', 'NV010', 0x30);

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `BILL_ID` varchar(10) NOT NULL,
  `CUSTOMER_ID` varchar(10) DEFAULT NULL,
  `STAFF_ID` varchar(10) DEFAULT NULL,
  `DOPURCHASE` date DEFAULT NULL,
  `TOTAL` double DEFAULT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`BILL_ID`, `CUSTOMER_ID`, `STAFF_ID`, `DOPURCHASE`, `TOTAL`, `DELETED`) VALUES
('BILL001', 'KH001', 'NV001', '2022-03-17', 90000, 0x30),
('BILL002', 'KH002', 'NV002', '2022-03-17', 30000, 0x30),
('BILL003', 'KH003', 'NV003', '2022-03-17', 45000, 0x30),
('BILL004', 'KH004', 'NV004', '2022-03-17', 15000, 0x30),
('BILL005', 'KH005', 'NV005', '2022-03-17', 30000, 0x30),
('BILL006', 'KH006', 'NV006', '2022-03-17', 45000, 0x30),
('BILL007', 'KH007', 'NV007', '2022-03-17', 15000, 0x30),
('BILL008', 'KH008', 'NV008', '2022-03-17', 30000, 0x30),
('BILL009', 'KH009', 'NV009', '2022-03-17', 45000, 0x30),
('BILL010', 'KH010', 'NV010', '2022-03-17', 15000, 0x30);

-- --------------------------------------------------------

--
-- Table structure for table `bill_details`
--

CREATE TABLE `bill_details` (
  `BILL_ID` varchar(10) NOT NULL,
  `PRODUCT_ID` varchar(10) NOT NULL,
  `QUANTITY` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bill_details`
--

INSERT INTO `bill_details` (`BILL_ID`, `PRODUCT_ID`, `QUANTITY`) VALUES
('BILL001', 'SP001', 3),
('BILL001', 'SP004', 2),
('BILL002', 'SP002', 2),
('BILL003', 'SP003', 3),
('BILL004', 'SP004', 1),
('BILL005', 'SP005', 2),
('BILL006', 'SP006', 3),
('BILL007', 'SP007', 1),
('BILL008', 'SP008', 2),
('BILL009', 'SP009', 3),
('BILL010', 'SP010', 1);

--
-- Triggers `bill_details`
--
DELIMITER $$
CREATE TRIGGER `InsertTotalBill` AFTER INSERT ON `bill_details` FOR EACH ROW UPDATE bill
SET bill.TOTAL = bill.TOTAL + (SELECT product.COST FROM product WHERE product.PRODUCT_ID = NEW.PRODUCT_ID) * NEW.QUANTITY
WHERE bill.BILL_ID = NEW.BILL_ID
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `UpdateTotalBill` AFTER UPDATE ON `bill_details` FOR EACH ROW UPDATE bill
SET bill.TOTAL = bill.TOTAL + (SELECT product.COST FROM product WHERE product.PRODUCT_ID = NEW.PRODUCT_ID) * NEW.QUANTITY
WHERE bill.BILL_ID = NEW.BILL_ID
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `CATEGORY_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`CATEGORY_ID`, `NAME`, `QUANTITY`, `DELETED`) VALUES
('001', 'LOẠI A', 1, 0x30),
('002', 'LOẠI B', 1, 0x30),
('003', 'LOẠI C', 1, 0x30),
('004', 'LOẠI D', 1, 0x30),
('005', 'LOẠI E', 1, 0x30),
('006', 'LOẠI F', 1, 0x30),
('007', 'LOẠI G', 1, 0x30),
('008', 'LOẠI H', 1, 0x30),
('009', 'LOẠI I', 1, 0x30),
('010', 'LOẠI K', 1, 0x30);

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `CUSTOMER_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `GENDER` binary(1) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `PHONE` varchar(12) DEFAULT NULL,
  `MEMBERSHIP` binary(1) DEFAULT '0',
  `DOSUP` date DEFAULT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CUSTOMER_ID`, `NAME`, `GENDER`, `DOB`, `PHONE`, `MEMBERSHIP`, `DOSUP`, `DELETED`) VALUES
('KH001', 'NGUUYỄN VĂN A', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH002', 'NGUUYỄN VĂN B', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH003', 'NGUUYỄN VĂN C', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH004', 'NGUUYỄN VĂN D', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH005', 'NGUUYỄN VĂN E', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH006', 'NGUUYỄN VĂN F', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH007', 'NGUUYỄN VĂN G', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH008', 'NGUUYỄN VĂN H', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH009', 'NGUUYỄN VĂN I', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30),
('KH010', 'NGUUYỄN VĂN K', 0x4d, '2022-03-17', '0123456789', 0x4e, '2022-03-17', 0x30);

-- --------------------------------------------------------

--
-- Table structure for table `decentralization`
--

CREATE TABLE `decentralization` (
  `DECENTRALIZATION_ID` varchar(10) NOT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `decentralization`
--

INSERT INTO `decentralization` (`DECENTRALIZATION_ID`, `DELETED`) VALUES
('001', 0x58),
('002', 0x58),
('003', 0x58),
('004', 0x58),
('005', 0x58),
('006', 0x58),
('007', 0x58),
('008', 0x58),
('009', 0x58),
('010', 0x58);

-- --------------------------------------------------------

--
-- Table structure for table `discount`
--

CREATE TABLE `discount` (
  `DISCOUNT_ID` varchar(10) NOT NULL,
  `DISCOUNT_PERCENT` int(11) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `STATUS` binary(1) DEFAULT '0',
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `discount`
--

INSERT INTO `discount` (`DISCOUNT_ID`, `DISCOUNT_PERCENT`, `START_DATE`, `END_DATE`, `STATUS`, `DELETED`) VALUES
('DIS001', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS002', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS003', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS004', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS005', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS006', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS007', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS008', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS009', 0, '2023-03-06', '2023-03-07', 0x41, 0x30),
('DIS010', 0, '2023-03-06', '2023-03-07', 0x41, 0x30);

-- --------------------------------------------------------

--
-- Table structure for table `discount_details`
--

CREATE TABLE `discount_details` (
  `DISCOUNT_ID` varchar(10) NOT NULL,
  `PRODUCT_ID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `discount_details`
--

INSERT INTO `discount_details` (`DISCOUNT_ID`, `PRODUCT_ID`) VALUES
('DIS001', 'SP001'),
('DIS002', 'SP002'),
('DIS003', 'SP003'),
('DIS004', 'SP004'),
('DIS005', 'SP005'),
('DIS006', 'SP006'),
('DIS007', 'SP007'),
('DIS008', 'SP008'),
('DIS009', 'SP009'),
('DIS010', 'SP010');

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE `ingredient` (
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `UNIT` varchar(10) DEFAULT NULL,
  `SUPPLIER_ID` varchar(10) DEFAULT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`INGREDIENT_ID`, `NAME`, `QUANTITY`, `UNIT`, `SUPPLIER_ID`, `DELETED`) VALUES
('NL001', 'TÊN NL1', 0, 'ĐƠN VỊ 1', 'NCC001', 0x30),
('NL002', 'TÊN NL2', 0, 'ĐƠN VỊ 2', 'NCC002', 0x30),
('NL003', 'TÊN NL3', 0, 'ĐƠN VỊ 3', 'NCC003', 0x30),
('NL004', 'TÊN NL4', 0, 'ĐƠN VỊ 4', 'NCC004', 0x30),
('NL005', 'TÊN NL5', 0, 'ĐƠN VỊ 5', 'NCC005', 0x30),
('NL006', 'TÊN NL6', 0, 'ĐƠN VỊ 6', 'NCC006', 0x30),
('NL007', 'TÊN NL7', 0, 'ĐƠN VỊ 7', 'NCC007', 0x30),
('NL008', 'TÊN NL8', 0, 'ĐƠN VỊ 8', 'NCC008', 0x30),
('NL009', 'TÊN NL9', 0, 'ĐƠN VỊ 9', 'NCC009', 0x30),
('NL010', 'TÊN NL10', 0, 'ĐƠN VỊ 10', 'NCC010', 0x30);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `PRODUCT_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `CATEGORY_ID` varchar(10) NOT NULL,
  `COST` double DEFAULT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`PRODUCT_ID`, `NAME`, `CATEGORY_ID`, `COST`, `DELETED`) VALUES
('SP001', 'SẢN PHẨM 1', '001', 15000, 0x30),
('SP002', 'SẢN PHẨM 2', '002', 15000, 0x30),
('SP003', 'SẢN PHẨM 3', '003', 15000, 0x30),
('SP004', 'SẢN PHẨM 4', '004', 15000, 0x30),
('SP005', 'SẢN PHẨM 5', '005', 15000, 0x30),
('SP006', 'SẢN PHẨM 6', '006', 15000, 0x30),
('SP007', 'SẢN PHẨM 7', '007', 15000, 0x30),
('SP008', 'SẢN PHẨM 8', '008', 15000, 0x30),
('SP009', 'SẢN PHẨM 9', '009', 15000, 0x30),
('SP010', 'SẢN PHẨM 10', '010', 15000, 0x30);

--
-- Triggers `product`
--
DELIMITER $$
CREATE TRIGGER `InsertQuantityCategory` AFTER INSERT ON `product` FOR EACH ROW UPDATE category SET QUANTITY = ( SELECT COUNT(PRO.PRODUCT_ID) FROM product PRO WHERE PRO.CATEGORY_ID = category.CATEGORY_ID )
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `UpdateQuantityCategory` AFTER UPDATE ON `product` FOR EACH ROW UPDATE category SET QUANTITY = ( SELECT COUNT(PRO.PRODUCT_ID) FROM product PRO WHERE PRO.CATEGORY_ID = category.CATEGORY_ID )
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `recipe`
--

CREATE TABLE `recipe` (
  `PRODUCT_ID` varchar(10) NOT NULL,
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `QUANTITY` int(11) DEFAULT NULL,
  `UNIT` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `recipe`
--

INSERT INTO `recipe` (`PRODUCT_ID`, `INGREDIENT_ID`, `QUANTITY`, `UNIT`) VALUES
('SP001', 'NL001', 0, 'ĐƠN VỊ 1'),
('SP002', 'NL002', 0, 'ĐƠN VỊ 2'),
('SP003', 'NL003', 0, 'ĐƠN VỊ 3'),
('SP004', 'NL004', 0, 'ĐƠN VỊ 4'),
('SP005', 'NL005', 0, 'ĐƠN VỊ 5'),
('SP006', 'NL006', 0, 'ĐƠN VỊ 6'),
('SP007', 'NL007', 0, 'ĐƠN VỊ 7'),
('SP008', 'NL008', 0, 'ĐƠN VỊ 8'),
('SP009', 'NL009', 0, 'ĐƠN VỊ 9'),
('SP010', 'NL010', 0, 'ĐƠN VỊ 10');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `STAFF_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `GENDER` binary(1) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `PHONE` varchar(12) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `SALARY` double DEFAULT NULL,
  `DOENTRY` date DEFAULT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`STAFF_ID`, `NAME`, `GENDER`, `DOB`, `ADDRESS`, `PHONE`, `EMAIL`, `SALARY`, `DOENTRY`, `DELETED`) VALUES
('NV001', 'NGUUYỄN VĂN A', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV002', 'NGUUYỄN VĂN B', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV003', 'NGUUYỄN VĂN C', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV004', 'NGUUYỄN VĂN D', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV005', 'NGUUYỄN VĂN E', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV006', 'NGUUYỄN VĂN F', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV007', 'NGUUYỄN VĂN G', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV008', 'NGUUYỄN VĂN G', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV009', 'NGUUYỄN VĂN I', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30),
('NV010', 'NGUUYỄN VĂN K', 0x4d, '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', 0x30);

-- --------------------------------------------------------

--
-- Table structure for table `supplier`
--

CREATE TABLE `supplier` (
  `SUPPLIER_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `PHONE` varchar(12) DEFAULT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `PRICE` double DEFAULT NULL,
  `DELETED` binary(1) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`SUPPLIER_ID`, `NAME`, `PHONE`, `ADDRESS`, `EMAIL`, `PRICE`, `DELETED`) VALUES
('NCC001', 'NGƯỜI CUNG CẤP 1', '0123456789', 'ĐỊA CHỈ 1', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC002', 'NGƯỜI CUNG CẤP 2', '0123456789', 'ĐỊA CHỈ 2', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC003', 'NGƯỜI CUNG CẤP 3', '0123456789', 'ĐỊA CHỈ 3', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC004', 'NGƯỜI CUNG CẤP 4', '0123456789', 'ĐỊA CHỈ 4', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC005', 'NGƯỜI CUNG CẤP 5', '0123456789', 'ĐỊA CHỈ 5', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC006', 'NGƯỜI CUNG CẤP 6', '0123456789', 'ĐỊA CHỈ 6', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC007', 'NGƯỜI CUNG CẤP 7', '0123456789', 'ĐỊA CHỈ 7', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC008', 'NGƯỜI CUNG CẤP 8', '0123456789', 'ĐỊA CHỈ 8', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC009', 'NGƯỜI CUNG CẤP 9', '0123456789', 'ĐỊA CHỈ 9', 'XXXXXXX@GMAIL.COM', 150000, 0x30),
('NCC010', 'NGƯỜI CUNG CẤP 10', '0123456789', 'ĐỊA CHỈ 10', 'XXXXXXX@GMAIL.COM', 150000, 0x30);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`USERNAME`),
  ADD KEY `FK_STAFF` (`STAFF_ID`) USING BTREE,
  ADD KEY `FK_DECENTRALIZATION` (`DECENTRALIZATION_ID`);

--
-- Indexes for table `bill`
--
ALTER TABLE `bill`
  ADD PRIMARY KEY (`BILL_ID`),
  ADD KEY `FK_CUSTOMER` (`CUSTOMER_ID`),
  ADD KEY `FK_STAFF` (`STAFF_ID`);

--
-- Indexes for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD PRIMARY KEY (`BILL_ID`,`PRODUCT_ID`),
  ADD KEY `FK_PRODU` (`PRODUCT_ID`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`CATEGORY_ID`);

--
-- Indexes for table `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`CUSTOMER_ID`);

--
-- Indexes for table `decentralization`
--
ALTER TABLE `decentralization`
  ADD PRIMARY KEY (`DECENTRALIZATION_ID`);

--
-- Indexes for table `discount`
--
ALTER TABLE `discount`
  ADD PRIMARY KEY (`DISCOUNT_ID`);

--
-- Indexes for table `discount_details`
--
ALTER TABLE `discount_details`
  ADD PRIMARY KEY (`DISCOUNT_ID`,`PRODUCT_ID`),
  ADD KEY `FK_PRODUCT` (`PRODUCT_ID`);

--
-- Indexes for table `ingredient`
--
ALTER TABLE `ingredient`
  ADD PRIMARY KEY (`INGREDIENT_ID`),
  ADD KEY `FK_SUPPLIER` (`SUPPLIER_ID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`PRODUCT_ID`),
  ADD KEY `FK_CATEGORY` (`CATEGORY_ID`);

--
-- Indexes for table `recipe`
--
ALTER TABLE `recipe`
  ADD PRIMARY KEY (`PRODUCT_ID`,`INGREDIENT_ID`),
  ADD KEY `FK_INGREDIENT` (`INGREDIENT_ID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`STAFF_ID`);

--
-- Indexes for table `supplier`
--
ALTER TABLE `supplier`
  ADD PRIMARY KEY (`SUPPLIER_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `FK_DECENTRALIZATION` FOREIGN KEY (`DECENTRALIZATION_ID`) REFERENCES `decentralization` (`DECENTRALIZATION_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `PK_STAFF` FOREIGN KEY (`STAFF_ID`) REFERENCES `staff` (`STAFF_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `bill`
--
ALTER TABLE `bill`
  ADD CONSTRAINT `FK_CUSTOMER` FOREIGN KEY (`CUSTOMER_ID`) REFERENCES `customer` (`CUSTOMER_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_STAFF` FOREIGN KEY (`STAFF_ID`) REFERENCES `staff` (`STAFF_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD CONSTRAINT `FK_BILL` FOREIGN KEY (`BILL_ID`) REFERENCES `bill` (`BILL_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_PRODU` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `discount_details`
--
ALTER TABLE `discount_details`
  ADD CONSTRAINT `FK_DISCOUNT` FOREIGN KEY (`DISCOUNT_ID`) REFERENCES `discount` (`DISCOUNT_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_PRODUCT` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `ingredient`
--
ALTER TABLE `ingredient`
  ADD CONSTRAINT `FK_SUPPLIER` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`SUPPLIER_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK_CATEGORY` FOREIGN KEY (`CATEGORY_ID`) REFERENCES `category` (`CATEGORY_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `recipe`
--
ALTER TABLE `recipe`
  ADD CONSTRAINT `FK_INGREDIENT` FOREIGN KEY (`INGREDIENT_ID`) REFERENCES `ingredient` (`INGREDIENT_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_PRO` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product` (`PRODUCT_ID`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
