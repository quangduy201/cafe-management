-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 08, 2023 at 04:40 AM
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
-- Database: `cafe-management`
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
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `bill`
--

CREATE TABLE `bill` (
  `BILL_ID` varchar(10) NOT NULL,
  `CUSTOMER_ID` varchar(10) DEFAULT NULL,
  `STAFF_ID` varchar(10) DEFAULT NULL,
  `DOPURCHASE` date DEFAULT NULL,
  `TOTAL` double DEFAULT 0,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `bill_details`
--

CREATE TABLE `bill_details` (
  `BILL_ID` varchar(10) NOT NULL,
  `PRODUCT_ID` varchar(10) NOT NULL,
  `QUANTITY` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Triggers `bill_details`
--
DELIMITER $$
CREATE TRIGGER `InsertBill_Details` AFTER INSERT ON `bill_details` FOR EACH ROW BEGIN
IF NEW.PRODUCT_ID IN (SELECT discount_details.PRODUCT_ID
          FROM discount_details JOIN  discount ON discount.DISCOUNT_ID = discount_details.DISCOUNT_ID
		WHERE discount.STATUS = 0)	THEN
    UPDATE bill
	SET bill.TOTAL = bill.TOTAL + (SELECT product.COST FROM product WHERE product.PRODUCT_ID = NEW.PRODUCT_ID) * (100 - (SELECT discount.DISCOUNT_PERCENT FROM discount WHERE discount.STATUS = 0))/100 * NEW.QUANTITY
	WHERE bill.BILL_ID = NEW.BILL_ID;
ELSE
    UPDATE bill
	SET bill.TOTAL = bill.TOTAL + (SELECT product.COST FROM product WHERE product.PRODUCT_ID = NEW.PRODUCT_ID) * NEW.QUANTITY
	WHERE bill.BILL_ID = NEW.BILL_ID;
END IF;
UPDATE ingredient
SET ingredient.QUANTITY = ingredient.QUANTITY - (SELECT RE.MASS
                                                FROM recipe RE
                                                WHERE RE.PRODUCT_ID = NEW.PRODUCT_ID AND RE.INGREDIENT_ID = ingredient.INGREDIENT_ID)*NEW.QUANTITY
WHERE ingredient.INGREDIENT_ID IN (SELECT RE.INGREDIENT_ID
                                  FROM recipe RE
                                  WHERE RE.PRODUCT_ID = NEW.PRODUCT_ID);

END
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
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`CATEGORY_ID`, `NAME`, `QUANTITY`, `DELETED`) VALUES
('001', 'LO???I A', 1, b'0'),
('002', 'LO???I B', 1, b'0'),
('003', 'LO???I C', 1, b'0'),
('004', 'LO???I D', 1, b'0'),
('005', 'LO???I E', 1, b'0'),
('006', 'LO???I F', 1, b'0'),
('007', 'LO???I G', 1, b'0'),
('008', 'LO???I H', 1, b'0'),
('009', 'LO???I I', 1, b'0'),
('010', 'LO???I K', 1, b'0');

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `CUSTOMER_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `GENDER` bit(1) DEFAULT b'0',
  `DOB` date DEFAULT NULL,
  `PHONE` varchar(12) DEFAULT NULL,
  `MEMBERSHIP` bit(1) DEFAULT b'0',
  `DOSUP` date DEFAULT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CUSTOMER_ID`, `NAME`, `GENDER`, `DOB`, `PHONE`, `MEMBERSHIP`, `DOSUP`, `DELETED`) VALUES
('KH001', 'NGUUY???N V??N A', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH002', 'NGUUY???N V??N B', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH003', 'NGUUY???N V??N C', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH004', 'NGUUY???N V??N D', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH005', 'NGUUY???N V??N E', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH006', 'NGUUY???N V??N F', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH007', 'NGUUY???N V??N G', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH008', 'NGUUY???N V??N H', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH009', 'NGUUY???N V??N I', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0'),
('KH010', 'NGUUY???N V??N K', b'0', '2022-03-17', '0123456789', b'0', '2022-03-17', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `decentralization`
--

CREATE TABLE `decentralization` (
  `DECENTRALIZATION_ID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `decentralization`
--

INSERT INTO `decentralization` (`DECENTRALIZATION_ID`) VALUES
('001'),
('002'),
('003'),
('004'),
('005'),
('006'),
('007'),
('008'),
('009'),
('010');

-- --------------------------------------------------------

--
-- Table structure for table `discount`
--

CREATE TABLE `discount` (
  `DISCOUNT_ID` varchar(10) NOT NULL,
  `DISCOUNT_PERCENT` int(11) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `STATUS` bit(1) DEFAULT b'0',
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `discount_details`
--

CREATE TABLE `discount_details` (
  `DISCOUNT_ID` varchar(10) NOT NULL,
  `PRODUCT_ID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Triggers `discount_details`
--
DELIMITER $$
CREATE TRIGGER `UpdateStatus` AFTER INSERT ON `discount_details` FOR EACH ROW UPDATE discount
SET discount.STATUS = 1
WHERE discount.DISCOUNT_ID != NEW.DISCOUNT_ID
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE `ingredient` (
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `QUANTITY` double DEFAULT 0,
  `UNIT` varchar(10) DEFAULT NULL,
  `SUPPLIER_ID` varchar(10) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`INGREDIENT_ID`, `NAME`, `QUANTITY`, `UNIT`, `SUPPLIER_ID`, `DELETED`) VALUES
('NL001', 'T??N NL1', 6.599999999999999, '????N V??? 1', 'NCC001', b'0'),
('NL002', 'T??N NL2', 9.1, '????N V??? 2', 'NCC002', b'0'),
('NL003', 'T??N NL3', 7.6, '????N V??? 3', 'NCC003', b'0'),
('NL004', 'T??N NL4', 0, '????N V??? 4', 'NCC004', b'0'),
('NL005', 'T??N NL5', 0, '????N V??? 5', 'NCC005', b'0'),
('NL006', 'T??N NL6', 0, '????N V??? 6', 'NCC006', b'0'),
('NL007', 'T??N NL7', 0, '????N V??? 7', 'NCC007', b'0'),
('NL008', 'T??N NL8', 0, '????N V??? 8', 'NCC008', b'0'),
('NL009', 'T??N NL9', 0, '????N V??? 9', 'NCC009', b'0'),
('NL010', 'T??N NL10', 0, '????N V??? 10', 'NCC010', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `PRODUCT_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `CATEGORY_ID` varchar(10) NOT NULL,
  `COST` double DEFAULT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`PRODUCT_ID`, `NAME`, `CATEGORY_ID`, `COST`, `DELETED`) VALUES
('SP001', 'S???N PH???M 1', '001', 15000, b'0'),
('SP002', 'S???N PH???M 2', '002', 15000, b'0'),
('SP003', 'S???N PH???M 3', '003', 15000, b'0'),
('SP004', 'S???N PH???M 4', '004', 15000, b'0'),
('SP005', 'S???N PH???M 5', '005', 15000, b'0'),
('SP006', 'S???N PH???M 6', '006', 15000, b'0'),
('SP007', 'S???N PH???M 7', '007', 15000, b'0'),
('SP008', 'S???N PH???M 8', '008', 15000, b'0'),
('SP009', 'S???N PH???M 9', '009', 15000, b'0'),
('SP010', 'S???N PH???M 10', '010', 15000, b'0');

--
-- Triggers `product`
--
DELIMITER $$
CREATE TRIGGER `InsertProduct` AFTER INSERT ON `product` FOR EACH ROW UPDATE category SET QUANTITY = ( SELECT COUNT(PRO.PRODUCT_ID) FROM product PRO WHERE PRO.CATEGORY_ID = category.CATEGORY_ID AND product.DELETED = 0 )
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `UpdateProduct` AFTER UPDATE ON `product` FOR EACH ROW UPDATE category SET QUANTITY = ( SELECT COUNT(PRO.PRODUCT_ID) FROM product PRO WHERE PRO.CATEGORY_ID = category.CATEGORY_ID AND PRO.DELETED = 0 )
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `receipt_details`
--

CREATE TABLE `receipt_details` (
  `RECEIPT_ID` varchar(10) NOT NULL,
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `QUANTITY` double DEFAULT 0,
  `SUPPLIER_ID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Triggers `receipt_details`
--
DELIMITER $$
CREATE TRIGGER `InserReceipt_Details` AFTER INSERT ON `receipt_details` FOR EACH ROW BEGIN
UPDATE ingredient
SET ingredient.QUANTITY = ingredient.QUANTITY + NEW.QUANTITY
WHERE ingredient.INGREDIENT_ID = NEW.INGREDIENT_ID;
UPDATE received_note
SET received_note.GRAND_TOTAL = received_note.GRAND_TOTAL + (SELECT supplier.PRICE FROM supplier WHERE supplier.SUPPLIER_ID = NEW.SUPPLIER_ID) * NEW.QUANTITY
WHERE received_note.RECEIPT_ID = NEW.RECEIPT_ID;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `received_note`
--

CREATE TABLE `received_note` (
  `RECEIPT_ID` varchar(10) NOT NULL,
  `STAFF_ID` varchar(10) NOT NULL,
  `DOR` date DEFAULT NULL,
  `GRAND_TOTAL` double DEFAULT 0,
  `DELETED` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `recipe`
--

CREATE TABLE `recipe` (
  `PRODUCT_ID` varchar(10) NOT NULL,
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `MASS` double DEFAULT 0,
  `UNIT` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `recipe`
--

INSERT INTO `recipe` (`PRODUCT_ID`, `INGREDIENT_ID`, `MASS`, `UNIT`) VALUES
('SP001', 'NL001', 1.2, '????N V??? 1'),
('SP002', 'NL002', 1.2, '????N V??? 2'),
('SP003', 'NL003', 1.2, '????N V??? 3'),
('SP004', 'NL004', 0, '????N V??? 4'),
('SP005', 'NL005', 0, '????N V??? 5'),
('SP006', 'NL006', 0, '????N V??? 6'),
('SP007', 'NL007', 0, '????N V??? 7'),
('SP008', 'NL008', 0, '????N V??? 8'),
('SP009', 'NL009', 0, '????N V??? 9'),
('SP010', 'NL010', 0, '????N V??? 10');

-- --------------------------------------------------------

--
-- Table structure for table `staff`
--

CREATE TABLE `staff` (
  `STAFF_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `GENDER` bit(1) DEFAULT b'0',
  `DOB` date DEFAULT NULL,
  `ADDRESS` varchar(100) DEFAULT NULL,
  `PHONE` varchar(12) DEFAULT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `SALARY` double DEFAULT NULL,
  `DOENTRY` date DEFAULT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`STAFF_ID`, `NAME`, `GENDER`, `DOB`, `ADDRESS`, `PHONE`, `EMAIL`, `SALARY`, `DOENTRY`, `DELETED`) VALUES
('NV001', 'NGUUY???N V??N A', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV002', 'NGUUY???N V??N B', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV003', 'NGUUY???N V??N C', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV004', 'NGUUY???N V??N D', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV005', 'NGUUY???N V??N E', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV006', 'NGUUY???N V??N F', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV007', 'NGUUY???N V??N G', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV008', 'NGUUY???N V??N G', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV009', 'NGUUY???N V??N I', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0'),
('NV010', 'NGUUY???N V??N K', b'0', '2022-03-17', 'NULL', '0123456789', 'XXXXXX@GMAIL.COM', 0, '0000-00-00', b'0');

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
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`SUPPLIER_ID`, `NAME`, `PHONE`, `ADDRESS`, `EMAIL`, `PRICE`, `DELETED`) VALUES
('NCC001', 'NG?????I CUNG C???P 1', '0123456789', '?????A CH??? 1', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC002', 'NG?????I CUNG C???P 2', '0123456789', '?????A CH??? 2', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC003', 'NG?????I CUNG C???P 3', '0123456789', '?????A CH??? 3', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC004', 'NG?????I CUNG C???P 4', '0123456789', '?????A CH??? 4', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC005', 'NG?????I CUNG C???P 5', '0123456789', '?????A CH??? 5', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC006', 'NG?????I CUNG C???P 6', '0123456789', '?????A CH??? 6', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC007', 'NG?????I CUNG C???P 7', '0123456789', '?????A CH??? 7', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC008', 'NG?????I CUNG C???P 8', '0123456789', '?????A CH??? 8', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC009', 'NG?????I CUNG C???P 9', '0123456789', '?????A CH??? 9', 'XXXXXXX@GMAIL.COM', 150000, b'0'),
('NCC010', 'NG?????I CUNG C???P 10', '0123456789', '?????A CH??? 10', 'XXXXXXX@GMAIL.COM', 150000, b'0');

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
-- Indexes for table `receipt_details`
--
ALTER TABLE `receipt_details`
  ADD PRIMARY KEY (`RECEIPT_ID`,`INGREDIENT_ID`),
  ADD KEY `FK_INGRED` (`INGREDIENT_ID`),
  ADD KEY `FK_SUP` (`SUPPLIER_ID`);

--
-- Indexes for table `received_note`
--
ALTER TABLE `received_note`
  ADD PRIMARY KEY (`RECEIPT_ID`),
  ADD KEY `FK_STAF` (`STAFF_ID`);

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
-- Constraints for table `receipt_details`
--
ALTER TABLE `receipt_details`
  ADD CONSTRAINT `FK_INGRED` FOREIGN KEY (`INGREDIENT_ID`) REFERENCES `ingredient` (`INGREDIENT_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_RECEIPT` FOREIGN KEY (`RECEIPT_ID`) REFERENCES `received_note` (`RECEIPT_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_SUP` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`SUPPLIER_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `received_note`
--
ALTER TABLE `received_note`
  ADD CONSTRAINT `FK_STAF` FOREIGN KEY (`STAFF_ID`) REFERENCES `staff` (`STAFF_ID`) ON UPDATE CASCADE;

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
