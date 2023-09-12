-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 12, 2023 at 06:13 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

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
  `ACCOUNT_ID` varchar(10) NOT NULL,
  `USERNAME` varchar(20) NOT NULL,
  `PASSWD` varchar(20) DEFAULT NULL,
  `DECENTRALIZATION_ID` varchar(10) DEFAULT NULL,
  `STAFF_ID` varchar(10) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`ACCOUNT_ID`, `USERNAME`, `PASSWD`, `DECENTRALIZATION_ID`, `STAFF_ID`, `DELETED`) VALUES
('AC000', 'admin', 'admin', 'DE00', 'ST00', b'0'),
('AC001', 'dungboi', 'Dung123', 'DE01', 'ST01', b'0'),
('AC002', 'legiang', 'Giang123', 'DE04', 'ST08', b'0'),
('AC003', 'longbott', 'Long123', 'DE01', 'ST03', b'0'),
('AC004', 'quangduy', 'Duy123', 'DE01', 'ST02', b'0'),
('AC005', 'tienmanh', 'Manh123', 'DE03', 'ST06', b'0'),
('AC006', 'vanlam', 'Lam123', 'DE05', 'ST07', b'0'),
('AC007', 'xuanmai', 'Mai123', 'DE06', 'ST05', b'0'),
('AC008', 'xuanphuc', 'Phuc123', 'DE02', 'ST09', b'0'),
('AC010', 'zidan', 'Dan123', 'DE01', 'ST04', b'0');

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
  `RECEIVED` double DEFAULT 0,
  `EXCESS` double DEFAULT 0,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`BILL_ID`, `CUSTOMER_ID`, `STAFF_ID`, `DOPURCHASE`, `TOTAL`, `RECEIVED`, `EXCESS`, `DELETED`) VALUES
('BI0001', 'CUS001', 'ST05', '2020-09-08', 239000, 300000, -61000, b'0'),
('BI0002', 'CUS002', 'ST05', '2021-02-07', 195000, 300000, -105000, b'0'),
('BI0003', 'CUS003', 'ST05', '2021-05-06', 110000, 300000, -190000, b'0'),
('BI0004', 'CUS004', 'ST05', '2021-08-03', 175000, 300000, -125000, b'0'),
('BI0005', 'CUS005', 'ST05', '2022-03-19', 173000, 300000, -127000, b'0'),
('BI0006', 'CUS006', 'ST05', '2022-03-28', 109000, 300000, -191000, b'0'),
('BI0007', 'CUS007', 'ST05', '2022-05-05', 135000, 300000, -165000, b'0'),
('BI0008', 'CUS008', 'ST05', '2022-09-08', 90000, 300000, -210000, b'0'),
('BI0009', 'CUS009', 'ST05', '2023-01-09', 277000, 300000, -23000, b'0'),
('BI0010', 'CUS010', 'ST05', '2023-03-07', 207000, 300000, -93000, b'0');

--
-- Triggers `bill`
--
DELIMITER $$
CREATE TRIGGER `UpdateBill` AFTER UPDATE ON `bill` FOR EACH ROW BEGIN
IF NEW.DELETED <> OLD.DELETED THEN
	CREATE TEMPORARY TABLE my_temp_table ( INGREDIENT_ID VARCHAR(10) NOT NULL, MASS DOUBLE NOT NULL, PRIMARY KEY (INGREDIENT_ID) );
	INSERT INTO my_temp_table (INGREDIENT_ID, MASS)
    SELECT RE.INGREDIENT_ID, SUM(RE.MASS*BD.QUANTITY) FROM bill_details BD JOIN recipe RE ON BD.PRODUCT_ID = RE.PRODUCT_ID
    WHERE BD.BILL_ID = NEW.BILL_ID
    GROUP BY RE.INGREDIENT_ID;
	UPDATE ingredient
    SET ingredient.QUANTITY = ingredient.QUANTITY + (SELECT MASS FROM my_temp_table WHERE my_temp_table.INGREDIENT_ID = ingredient.INGREDIENT_ID)
    WHERE ingredient.INGREDIENT_ID IN (SELECT INGREDIENT_ID FROM my_temp_table);
END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `bill_details`
--

CREATE TABLE `bill_details` (
  `BILL_ID` varchar(10) NOT NULL,
  `PRODUCT_ID` varchar(10) NOT NULL,
  `QUANTITY` int(11) DEFAULT 0,
  `TOTAL` double DEFAULT 0,
  `PERCENT` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bill_details`
--

INSERT INTO `bill_details` (`BILL_ID`, `PRODUCT_ID`, `QUANTITY`, `TOTAL`, `PERCENT`) VALUES
('BI0001', 'PR001', 3, 87000, 0),
('BI0001', 'PR004', 2, 58000, 0),
('BI0001', 'PR009', 1, 45000, 0),
('BI0001', 'PR011', 1, 49000, 0),
('BI0002', 'PR023', 2, 130000, 0),
('BI0002', 'PR035', 1, 65000, 0),
('BI0003', 'PR046', 2, 110000, 0),
('BI0004', 'PR025', 2, 110000, 0),
('BI0004', 'PR050', 1, 65000, 0),
('BI0005', 'PR001', 1, 29000, 0),
('BI0005', 'PR010', 2, 90000, 0),
('BI0005', 'PR060', 1, 54000, 0),
('BI0006', 'PR019', 1, 19000, 0),
('BI0006', 'PR024', 2, 90000, 0),
('BI0007', 'PR027', 3, 135000, 0),
('BI0008', 'PR018', 1, 55000, 0),
('BI0008', 'PR038', 1, 35000, 0),
('BI0009', 'PR027', 2, 90000, 0),
('BI0009', 'PR051', 1, 69000, 0),
('BI0009', 'PR063', 2, 59000, 0),
('BI0010', 'PR045', 3, 207000, 0);

--
-- Triggers `bill_details`
--
DELIMITER $$
CREATE TRIGGER `InsertBill_Details` AFTER INSERT ON `bill_details` FOR EACH ROW BEGIN
IF NEW.PRODUCT_ID IN (SELECT discount_details.PRODUCT_ID
          FROM discount_details JOIN  discount ON discount.DISCOUNT_ID = discount_details.DISCOUNT_ID
		WHERE discount.STATUS = 0 AND discount.DELETED = 0)	THEN
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`CATEGORY_ID`, `NAME`, `QUANTITY`, `DELETED`) VALUES
('CA01', 'CÀ PHÊ PHIN', 9, b'0'),
('CA02', 'PHINDI', 9, b'0'),
('CA03', 'BÁNH MÌ QUE', 2, b'0'),
('CA04', 'TRÀ', 15, b'0'),
('CA05', 'BÁNH', 7, b'0'),
('CA06', 'FREEZE', 15, b'0'),
('CA07', 'TRÀ SỮA', 8, b'0');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CUSTOMER_ID`, `NAME`, `GENDER`, `DOB`, `PHONE`, `MEMBERSHIP`, `DOSUP`, `DELETED`) VALUES
('CUS000', 'VÃNG LAI', b'1', '1000-01-01', '', b'0', '1000-01-01', b'0'),
('CUS001', 'NGUYỄN VĂN NAM', b'1', '2000-12-01', '0862994282', b'0', '2020-09-08', b'0'),
('CUS002', 'HOÀNG XUÂN BẮC', b'1', '2001-09-03', '0967563268', b'1', '2021-02-07', b'0'),
('CUS003', 'NGUYỄN THỊ THU HIỀN', b'0', '2004-05-04', '0981485618', b'0', '2021-05-06', b'1'),
('CUS004', 'NGUYỄN VĂN THẮNG', b'1', '1999-08-10', '0861149539', b'1', '2021-08-03', b'0'),
('CUS005', 'NGUYỄN THỊ YẾN NHI', b'0', '2004-12-08', '0392258127', b'1', '2022-03-19', b'0'),
('CUS006', 'ĐẶNG NGUYỄN GIA HUY', b'1', '2003-06-09', '0371977937', b'0', '2022-03-28', b'0'),
('CUS007', 'NGUYỄN THI DIỆU CHI', b'0', '2000-04-09', '0378367833', b'0', '2022-05-05', b'1'),
('CUS008', 'NGUYỄN THỊ THANH NHÀN', b'0', '2001-08-03', '0323373316', b'1', '2022-09-08', b'0'),
('CUS009', 'NGUYỄN TRUNG TÍN', b'1', '2002-06-20', '0357118533', b'0', '2023-01-09', b'1'),
('CUS010', 'ĐINH XUÂN HOÀI', b'1', '2004-07-06', '0964745278', b'1', '2023-03-07', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `decentralization`
--

CREATE TABLE `decentralization` (
  `DECENTRALIZATION_ID` varchar(10) NOT NULL,
  `DECENTRALIZATION_NAME` varchar(20) NOT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `decentralization`
--

INSERT INTO `decentralization` (`DECENTRALIZATION_ID`, `DECENTRALIZATION_NAME`, `DELETED`) VALUES
('DE00', 'admin', b'0'),
('DE01', 'QUẢN LÝ', b'0'),
('DE02', 'NV BÁN HÀNG', b'0'),
('DE03', 'NV QUẢN LÝ NHÀ KHO', b'0'),
('DE04', 'NV PHỤC VỤ', b'0'),
('DE05', 'NV PHA CHẾ', b'0'),
('DE06', 'NV QUẢN LÝ GIẢM GIÁ', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `decentralization_detail`
--

CREATE TABLE `decentralization_detail` (
  `DECENTRALIZATION_ID` varchar(10) NOT NULL,
  `MODULE_ID` varchar(10) NOT NULL,
  `CAN_ADD` bit(1) DEFAULT b'0',
  `CAN_EDIT` bit(1) DEFAULT b'0',
  `CAN_REMOVE` bit(1) DEFAULT b'0',
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `decentralization_detail`
--

INSERT INTO `decentralization_detail` (`DECENTRALIZATION_ID`, `MODULE_ID`, `CAN_ADD`, `CAN_EDIT`, `CAN_REMOVE`, `DELETED`) VALUES
('DE00', 'MOD01', b'1', b'1', b'1', b'0'),
('DE00', 'MOD02', b'1', b'1', b'1', b'0'),
('DE00', 'MOD03', b'1', b'1', b'1', b'0'),
('DE00', 'MOD04', b'1', b'1', b'1', b'0'),
('DE00', 'MOD05', b'1', b'1', b'1', b'0'),
('DE00', 'MOD06', b'1', b'1', b'1', b'0'),
('DE00', 'MOD07', b'1', b'1', b'1', b'0'),
('DE00', 'MOD08', b'1', b'1', b'1', b'0'),
('DE00', 'MOD09', b'1', b'1', b'1', b'0'),
('DE00', 'MOD10', b'1', b'1', b'1', b'0'),
('DE00', 'MOD11', b'1', b'1', b'1', b'0'),
('DE00', 'MOD12', b'1', b'1', b'1', b'0'),
('DE00', 'MOD13', b'1', b'1', b'1', b'0'),
('DE01', 'MOD01', b'1', b'1', b'1', b'0'),
('DE01', 'MOD02', b'1', b'1', b'1', b'0'),
('DE01', 'MOD03', b'1', b'1', b'1', b'0'),
('DE01', 'MOD04', b'1', b'1', b'1', b'0'),
('DE01', 'MOD05', b'1', b'1', b'1', b'0'),
('DE01', 'MOD06', b'1', b'1', b'1', b'0'),
('DE01', 'MOD07', b'1', b'1', b'1', b'0'),
('DE01', 'MOD08', b'1', b'1', b'1', b'0'),
('DE01', 'MOD09', b'1', b'1', b'1', b'0'),
('DE01', 'MOD10', b'1', b'1', b'1', b'0'),
('DE01', 'MOD11', b'0', b'0', b'0', b'0'),
('DE01', 'MOD12', b'0', b'0', b'0', b'0'),
('DE01', 'MOD13', b'1', b'1', b'1', b'0'),
('DE02', 'MOD01', b'1', b'0', b'0', b'0'),
('DE02', 'MOD02', b'0', b'0', b'0', b'0'),
('DE02', 'MOD03', b'1', b'1', b'0', b'0'),
('DE02', 'MOD04', b'0', b'0', b'0', b'0'),
('DE02', 'MOD05', b'1', b'1', b'0', b'0'),
('DE02', 'MOD06', b'1', b'1', b'0', b'0'),
('DE02', 'MOD07', b'0', b'0', b'0', b'0'),
('DE02', 'MOD08', b'0', b'0', b'0', b'0'),
('DE02', 'MOD09', b'1', b'1', b'0', b'0'),
('DE02', 'MOD10', b'0', b'0', b'0', b'0'),
('DE02', 'MOD11', b'0', b'0', b'0', b'0'),
('DE02', 'MOD12', b'0', b'0', b'0', b'0'),
('DE02', 'MOD13', b'1', b'0', b'0', b'0'),
('DE03', 'MOD01', b'1', b'0', b'0', b'0'),
('DE03', 'MOD02', b'1', b'1', b'0', b'0'),
('DE03', 'MOD03', b'0', b'0', b'0', b'0'),
('DE03', 'MOD04', b'1', b'1', b'0', b'0'),
('DE03', 'MOD05', b'0', b'0', b'0', b'0'),
('DE03', 'MOD06', b'0', b'0', b'0', b'0'),
('DE03', 'MOD07', b'0', b'0', b'0', b'0'),
('DE03', 'MOD08', b'0', b'0', b'0', b'0'),
('DE03', 'MOD09', b'0', b'0', b'0', b'0'),
('DE03', 'MOD10', b'0', b'0', b'0', b'0'),
('DE03', 'MOD11', b'0', b'0', b'0', b'0'),
('DE03', 'MOD12', b'0', b'0', b'0', b'0'),
('DE03', 'MOD13', b'1', b'1', b'0', b'0'),
('DE04', 'MOD01', b'1', b'0', b'0', b'0'),
('DE04', 'MOD02', b'0', b'0', b'0', b'0'),
('DE04', 'MOD03', b'0', b'0', b'0', b'0'),
('DE04', 'MOD04', b'0', b'0', b'0', b'0'),
('DE04', 'MOD05', b'0', b'0', b'0', b'0'),
('DE04', 'MOD06', b'0', b'0', b'0', b'0'),
('DE04', 'MOD07', b'0', b'0', b'0', b'0'),
('DE04', 'MOD08', b'0', b'0', b'0', b'0'),
('DE04', 'MOD09', b'0', b'0', b'0', b'0'),
('DE04', 'MOD10', b'0', b'0', b'0', b'0'),
('DE04', 'MOD11', b'0', b'0', b'0', b'0'),
('DE04', 'MOD12', b'0', b'0', b'0', b'0'),
('DE04', 'MOD13', b'0', b'0', b'0', b'0'),
('DE05', 'MOD01', b'1', b'0', b'0', b'0'),
('DE05', 'MOD02', b'0', b'0', b'0', b'0'),
('DE05', 'MOD03', b'0', b'0', b'0', b'0'),
('DE05', 'MOD04', b'0', b'0', b'0', b'0'),
('DE05', 'MOD05', b'0', b'0', b'0', b'0'),
('DE05', 'MOD06', b'0', b'0', b'0', b'0'),
('DE05', 'MOD07', b'1', b'1', b'0', b'0'),
('DE05', 'MOD08', b'0', b'0', b'0', b'0'),
('DE05', 'MOD09', b'0', b'0', b'0', b'0'),
('DE05', 'MOD10', b'0', b'0', b'0', b'0'),
('DE05', 'MOD11', b'0', b'0', b'0', b'0'),
('DE05', 'MOD12', b'0', b'0', b'0', b'0'),
('DE05', 'MOD13', b'0', b'0', b'0', b'0'),
('DE06', 'MOD01', b'1', b'0', b'0', b'0'),
('DE06', 'MOD02', b'0', b'0', b'0', b'0'),
('DE06', 'MOD03', b'0', b'0', b'0', b'0'),
('DE06', 'MOD04', b'0', b'0', b'0', b'0'),
('DE06', 'MOD05', b'0', b'0', b'0', b'0'),
('DE06', 'MOD06', b'0', b'0', b'0', b'0'),
('DE06', 'MOD07', b'0', b'0', b'0', b'0'),
('DE06', 'MOD08', b'1', b'1', b'0', b'0'),
('DE06', 'MOD09', b'0', b'0', b'0', b'0'),
('DE06', 'MOD10', b'0', b'0', b'0', b'0'),
('DE06', 'MOD11', b'0', b'0', b'0', b'0'),
('DE06', 'MOD12', b'0', b'0', b'0', b'0'),
('DE06', 'MOD13', b'0', b'0', b'0', b'0');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `discount`
--

INSERT INTO `discount` (`DISCOUNT_ID`, `DISCOUNT_PERCENT`, `START_DATE`, `END_DATE`, `STATUS`, `DELETED`) VALUES
('DIS001', 5, '2023-08-03', '2023-08-10', b'1', b'0'),
('DIS002', 10, '2023-04-29', '2023-05-01', b'1', b'0'),
('DIS003', 5, '2023-10-20', '2023-10-21', b'1', b'0'),
('DIS004', 5, '2023-12-25', '2023-12-25', b'1', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `discount_details`
--

CREATE TABLE `discount_details` (
  `DISCOUNT_ID` varchar(10) NOT NULL,
  `PRODUCT_ID` varchar(10) NOT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `discount_details`
--

INSERT INTO `discount_details` (`DISCOUNT_ID`, `PRODUCT_ID`, `DELETED`) VALUES
('DIS001', 'PR003', b'0'),
('DIS001', 'PR006', b'0'),
('DIS001', 'PR009', b'0'),
('DIS001', 'PR012', b'0'),
('DIS001', 'PR015', b'0'),
('DIS001', 'PR018', b'0'),
('DIS002', 'PR021', b'0'),
('DIS002', 'PR024', b'0'),
('DIS002', 'PR027', b'0'),
('DIS002', 'PR030', b'0'),
('DIS002', 'PR033', b'0'),
('DIS002', 'PR036', b'0'),
('DIS002', 'PR039', b'0'),
('DIS002', 'PR042', b'0'),
('DIS003', 'PR045', b'0'),
('DIS003', 'PR048', b'0'),
('DIS003', 'PR051', b'0'),
('DIS003', 'PR054', b'0'),
('DIS003', 'PR057', b'0'),
('DIS004', 'PR061', b'0'),
('DIS004', 'PR063', b'0'),
('DIS004', 'PR065', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `ingredient`
--

CREATE TABLE `ingredient` (
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `QUANTITY` double DEFAULT 0,
  `UNIT` varchar(10) DEFAULT NULL,
  `UNIT_PRICE` double DEFAULT 0,
  `SUPPLIER_ID` varchar(10) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `ingredient`
--

INSERT INTO `ingredient` (`INGREDIENT_ID`, `NAME`, `QUANTITY`, `UNIT`, `UNIT_PRICE`, `SUPPLIER_ID`, `DELETED`) VALUES
('ING001', 'BỘT CAFE NGUYÊN CHẤT', 10, 'kg', 30000, 'SUP001', b'0'),
('ING002', 'SỮA TƯƠI KHÔNG ĐƯỜNG', 10, 'l', 30000, 'SUP001', b'0'),
('ING003', 'SỮA ĐẶC', 10, 'l', 30000, 'SUP001', b'0'),
('ING004', 'ĐÁ', 10, 'bag', 30000, 'SUP001', b'0'),
('ING005', 'NƯỚC', 10, 'l', 30000, 'SUP001', b'0'),
('ING006', 'MUỐI', 10, 'kg', 30000, 'SUP001', b'0'),
('ING007', 'ĐƯỜNG CÁT', 10, 'kg', 30000, 'SUP001', b'0'),
('ING008', 'SYRUP HẠNH NHÂN', 10, 'l', 30000, 'SUP001', b'0'),
('ING009', 'SYRUP ĐƯỜNG', 10, 'l', 30000, 'SUP001', b'0'),
('ING010', 'SỮA TƯƠI MILKLAB', 10, 'l', 30000, 'SUP001', b'0'),
('ING011', 'CÀ PHÊ TRUYỀN THỐNG SHIN', 10, 'kg', 30000, 'SUP001', b'0'),
('ING012', 'THẠCH CAFE', 10, 'kg', 30000, 'SUP001', b'0'),
('ING013', 'SYRUP ĐƯỜNG ĐEN HÀN QUỐC', 10, 'l', 30000, 'SUP001', b'0'),
('ING014', 'KEM SỮA', 10, 'l', 30000, 'SUP001', b'0'),
('ING015', 'SỐT CHOCOLA', 10, 'l', 30000, 'SUP001', b'0'),
('ING016', 'PATE', 10, 'kg', 30000, 'SUP002', b'0'),
('ING017', 'TƯƠNG ỚT', 10, 'l', 30000, 'SUP002', b'0'),
('ING018', 'CHÀ BÔNG', 10, 'kg', 30000, 'SUP002', b'0'),
('ING019', 'CỦ CẢI MUỐI', 10, 'kg', 30000, 'SUP002', b'0'),
('ING020', 'BÁNH MÌ QUE', 10, 'bag', 30000, 'SUP003', b'0'),
('ING021', 'SỐT GÀ PHÔ MAI', 10, 'kg', 30000, 'SUP002', b'0'),
('ING022', 'TRÀ Ô LONG', 10, 'kg', 30000, 'SUP001', b'0'),
('ING023', 'HẠT SEN', 10, 'kg', 30000, 'SUP001', b'0'),
('ING024', 'MILK FOAM', 10, 'l', 30000, 'SUP001', b'0'),
('ING025', 'THẠCH CỦ NĂNG', 10, 'kg', 30000, 'SUP001', b'0'),
('ING026', 'TRÀ ĐÀO', 10, 'kg', 30000, 'SUP001', b'0'),
('ING027', 'SỮA RICH', 10, 'l', 30000, 'SUP001', b'0'),
('ING028', 'THẠCH ĐÀO', 10, 'kg', 30000, 'SUP001', b'0'),
('ING029', 'ĐÀO', 10, 'kg', 30000, 'SUP001', b'0'),
('ING030', 'SẢ', 10, 'kg', 30000, 'SUP001', b'0'),
('ING031', 'SYRUP ĐÀO', 10, 'l', 30000, 'SUP001', b'0'),
('ING032', 'TRÀ ĐEN', 10, 'kg', 30000, 'SUP001', b'0'),
('ING033', 'SYRUP VẢI NGÂM', 10, 'l', 30000, 'SUP001', b'0'),
('ING034', 'THẠCH VẢI', 10, 'kg', 30000, 'SUP001', b'0'),
('ING035', 'VẢI', 10, 'kg', 30000, 'SUP001', b'0'),
('ING036', 'BỘT TRÀ XANH', 10, 'kg', 30000, 'SUP001', b'0'),
('ING037', 'KEM BÉO RICH', 10, 'l', 30000, 'SUP001', b'0'),
('ING038', 'ĐẬU ĐỎ', 10, 'kg', 30000, 'SUP001', b'0'),
('ING039', 'BÁNH MÌ SANDWICH', 4, 'kg', 30000, 'SUP001', b'0'),
('ING040', 'BỘT MÌ', 3, 'kg', 30000, 'SUP001', b'0'),
('ING041', 'BỘT BẮP', 3, 'kg', 30000, 'SUP001', b'0'),
('ING042', 'BƠ', 3, 'kg', 30000, 'SUP001', b'0'),
('ING043', 'CHUỐI SỨ', 24, 'quả', 30000, 'SUP001', b'0'),
('ING044', 'CHANH DÂY', 30, 'quả', 30000, 'SUP001', b'0'),
('ING045', 'BÁNH OREO', 3.5, 'kg', 30000, 'SUP001', b'0'),
('ING046', 'BỘT GELATIN', 3, 'kg', 30000, 'SUP001', b'0'),
('ING047', 'CREAM CHEESE', 6, 'kg', 30000, 'SUP001', b'0'),
('ING048', 'WHIPPING CREAM', 8, 'l', 30000, 'SUP001', b'0'),
('ING049', 'BỘT BÁNH', 12, 'kg', 30000, 'SUP001', b'0'),
('ING050', 'TRỨNG GÀ', 24, 'quả', 30000, 'SUP001', b'0'),
('ING051', 'DẦU ĂN', 4, 'l', 30000, 'SUP001', b'0'),
('ING052', 'BỘT CACAO', 3, 'kg', 30000, 'SUP001', b'0'),
('ING053', 'CHOCOLATE ĐEN', 4, 'kg', 30000, 'SUP001', b'0'),
('ING054', 'MASCAPONE CHEESE', 14, 'kg', 30000, 'SUP001', b'0'),
('ING055', 'RƯỢU AMARETTO', 5, 'l', 30000, 'SUP001', b'0'),
('ING056', 'RƯỢU KAHLUA', 2, 'l', 30000, 'SUP001', b'0'),
('ING057', 'TINH CHẤT VANI', 4, 'l', 30000, 'SUP001', b'0'),
('ING058', 'SỮA CHUA KHÔNG ĐƯỜNG', 10, 'kg', 30000, 'SUP001', b'0'),
('ING059', 'BỘT QUẾ', 3, 'kg', 30000, 'SUP001', b'0'),
('ING060', 'SYRUP BẠC HÀ', 5, 'l', 30000, 'SUP001', b'0'),
('ING061', 'BỘT DÂU', 7, 'kg', 30000, 'SUP001', b'0'),
('ING062', 'SYRUP DÂU', 10, 'l', 30000, 'SUP001', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
  `MODULE_ID` varchar(10) NOT NULL,
  `MODULE_NAME` varchar(20) NOT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`MODULE_ID`, `MODULE_NAME`, `DELETED`) VALUES
('MOD01', 'IS_SALE', b'0'),
('MOD02', 'IS_IMPORT', b'0'),
('MOD03', 'IS_BILL', b'0'),
('MOD04', 'IS_WAREHOUSES', b'0'),
('MOD05', 'IS_PRODUCT', b'0'),
('MOD06', 'IS_CATEGORY', b'0'),
('MOD07', 'IS_RECIPE', b'0'),
('MOD08', 'IS_DISCOUNT', b'0'),
('MOD09', 'IS_CUSTOMER', b'0'),
('MOD10', 'IS_STAFF', b'0'),
('MOD11', 'IS_ACCOUNT', b'0'),
('MOD12', 'IS_DECENTRALIZE', b'0'),
('MOD13', 'IS_SUPPLIER', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `PRODUCT_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `CATEGORY_ID` varchar(10) NOT NULL,
  `SIZED` varchar(4) DEFAULT 'NULL',
  `COST` double DEFAULT NULL,
  `IMAGE` mediumtext NOT NULL,
  `DELETED` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`PRODUCT_ID`, `NAME`, `CATEGORY_ID`, `SIZED`, `COST`, `IMAGE`, `DELETED`) VALUES
('PR001', 'PHIN SỮA ĐÁ', 'CA01', 'S', 29000, 'img/products/PR001.jpg', b'0'),
('PR002', 'PHIN SỮA ĐÁ', 'CA01', 'M', 39000, 'img/products/PR002.jpg', b'0'),
('PR003', 'PHIN SỮA ĐÁ', 'CA01', 'L', 45000, 'img/products/PR003.jpg', b'0'),
('PR004', 'PHIN ĐEN ĐÁ', 'CA01', 'S', 29000, 'img/products/PR004.jpg', b'0'),
('PR005', 'PHIN ĐEN ĐÁ', 'CA01', 'M', 35000, 'img/products/PR005.jpg', b'0'),
('PR006', 'PHIN ĐEN ĐÁ', 'CA01', 'L', 39000, 'img/products/PR006.jpg', b'0'),
('PR007', 'BẠC XỈU', 'CA01', 'S', 29000, 'img/products/PR007.jpg', b'0'),
('PR008', 'BẠC XỈU', 'CA01', 'M', 39000, 'img/products/PR008.jpg', b'0'),
('PR009', 'BẠC XỈU', 'CA01', 'L', 45000, 'img/products/PR009.jpg', b'0'),
('PR010', 'PHINDI HẠNH NHÂN', 'CA02', 'S', 45000, 'img/products/PR010.jpg', b'0'),
('PR011', 'PHINDI HẠNH NHÂN', 'CA02', 'M', 49000, 'img/products/PR011.jpg', b'0'),
('PR012', 'PHINDI HẠNH NHÂN', 'CA02', 'L', 55000, 'img/products/PR012.jpg', b'0'),
('PR013', 'PHINDI KEM SỮA', 'CA02', 'S', 45000, 'img/products/PR013.jpg', b'0'),
('PR014', 'PHINDI KEM SỮA', 'CA02', 'M', 49000, 'img/products/PR014.jpg', b'0'),
('PR015', 'PHINDI KEM SỮA', 'CA02', 'L', 55000, 'img/products/PR015.jpg', b'0'),
('PR016', 'PHINDI CHOCO', 'CA02', 'S', 45000, 'img/products/PR016.jpg', b'0'),
('PR017', 'PHINDI CHOCO', 'CA02', 'M', 49000, 'img/products/PR017.jpg', b'0'),
('PR018', 'PHINDI CHOCO', 'CA02', 'L', 55000, 'img/products/PR018.jpg', b'0'),
('PR019', 'BÁNH MÌ PATE', 'CA03', 'null', 19000, 'img/products/PR019.jpg', b'0'),
('PR020', 'BÁNH MÌ GÀ PHÔ MAI', 'CA03', 'null', 19000, 'img/products/PR020.jpg', b'0'),
('PR021', 'TRÀ SEN VÀNG', 'CA04', 'S', 45000, 'img/products/PR021.jpg', b'0'),
('PR022', 'TRÀ SEN VÀNG', 'CA04', 'M', 55000, 'img/products/PR022.jpg', b'0'),
('PR023', 'TRÀ SEN VÀNG', 'CA04', 'L', 65000, 'img/products/PR023.jpg', b'0'),
('PR024', 'TRÀ THẠCH ĐÀO', 'CA04', 'S', 45000, 'img/products/PR024.jpg', b'0'),
('PR025', 'TRÀ THẠCH ĐÀO', 'CA04', 'M', 55000, 'img/products/PR025.jpg', b'0'),
('PR026', 'TRÀ THẠCH ĐÀO', 'CA04', 'L', 65000, 'img/products/PR026.jpg', b'0'),
('PR027', 'TRÀ THANH ĐÀO', 'CA04', 'S', 45000, 'img/products/PR027.jpg', b'0'),
('PR028', 'TRÀ THANH ĐÀO', 'CA04', 'M', 55000, 'img/products/PR028.jpg', b'0'),
('PR029', 'TRÀ THANH ĐÀO', 'CA04', 'L', 65000, 'img/products/PR029.jpg', b'0'),
('PR030', 'TRÀ THẠCH VẢI', 'CA04', 'S', 45000, 'img/products/PR030.jpg', b'0'),
('PR031', 'TRÀ THẠCH VẢI', 'CA04', 'M', 55000, 'img/products/PR031.jpg', b'0'),
('PR032', 'TRÀ THẠCH VẢI', 'CA04', 'L', 65000, 'img/products/PR032.jpg', b'0'),
('PR033', 'TRÀ XANH ĐẬU ĐỎ', 'CA04', 'S', 45000, 'img/products/PR033.jpg', b'0'),
('PR034', 'TRÀ XANH ĐẬU ĐỎ', 'CA04', 'M', 55000, 'img/products/PR034.jpg', b'0'),
('PR035', 'TRÀ XANH ĐẬU ĐỎ', 'CA04', 'L', 65000, 'img/products/PR035.jpg', b'0'),
('PR036', 'BÁNH CHUỐI', 'CA05', 'null', 29000, 'img/products/PR036.jpg', b'0'),
('PR037', 'PHÔ MAI CHANH DÂY', 'CA05', 'null', 29000, 'img/products/PR037.jpg', b'0'),
('PR038', 'TIRAMISU', 'CA05', 'null', 35000, 'img/products/PR038.jpg', b'0'),
('PR039', 'MOUSSE ĐÀO', 'CA05', 'null', 35000, 'img/products/PR039.jpg', b'0'),
('PR040', 'PHÔ MAI TRÀ XANH', 'CA05', 'null', 35000, 'img/products/PR040.jpg', b'0'),
('PR041', 'PHÔ MAI CARAMEL', 'CA05', 'null', 35000, 'img/products/PR041.jpg', b'0'),
('PR042', 'PHÔ MAI CACAO', 'CA05', 'null', 35000, 'img/products/PR042.jpg', b'0'),
('PR043', 'FREEZE TRÀ XANH', 'CA06', 'S', 55000, 'img/products/PR043.jpg', b'0'),
('PR044', 'FREEZE TRÀ XANH', 'CA06', 'M', 65000, 'img/products/PR044.jpg', b'0'),
('PR045', 'FREEZE TRÀ XANH', 'CA06', 'L', 69000, 'img/products/PR045.jpg', b'0'),
('PR046', 'CARAMEL PHIN FREEZE', 'CA06', 'S', 55000, 'img/products/PR046.jpg', b'0'),
('PR047', 'CARAMEL PHIN FREEZE', 'CA06', 'M', 65000, 'img/products/PR047.jpg', b'0'),
('PR048', 'CARAMEL PHIN FREEZE', 'CA06', 'L', 69000, 'img/products/PR048.jpg', b'0'),
('PR049', 'COOKIES & CREAM', 'CA06', 'S', 55000, 'img/products/PR049.jpg', b'0'),
('PR050', 'COOKIES & CREAM', 'CA06', 'M', 65000, 'img/products/PR050.jpg', b'0'),
('PR051', 'COOKIES & CREAM', 'CA06', 'L', 69000, 'img/products/PR051.jpg', b'0'),
('PR052', 'FREEZE SÔ-CÔ-LA', 'CA06', 'S', 55000, 'img/products/PR052.jpg', b'0'),
('PR053', 'FREEZE SÔ-CÔ-LA', 'CA06', 'M', 65000, 'img/products/PR053.jpg', b'0'),
('PR054', 'FREEZE SÔ-CÔ-LA', 'CA06', 'L', 69000, 'img/products/PR054.jpg', b'0'),
('PR055', 'CLASSIC PHIN FREEZE', 'CA06', 'S', 55000, 'img/products/PR055.jpg', b'0'),
('PR056', 'CLASSIC PHIN FREEZE', 'CA06', 'M', 65000, 'img/products/PR056.jpg', b'0'),
('PR057', 'CLASSIC PHIN FREEZE', 'CA06', 'L', 69000, 'img/products/PR057.jpg', b'0'),
('PR058', 'TRÀ SỮA BẠC HÀ', 'CA07', 'S', 54000, 'img/products/PR058.jpg', b'0'),
('PR059', 'TRÀ SỮA BẠC HÀ', 'CA07', 'M', 59000, 'img/products/PR059.jpg', b'0'),
('PR060', 'TRÀ SỮA TRÀ XANH', 'CA07', 'S', 54000, 'img/products/PR060.jpg', b'0'),
('PR061', 'TRÀ SỮA TRÀ XANH', 'CA07', 'M', 59000, 'img/products/PR061.jpg', b'0'),
('PR062', 'TRÀ SỮA DÂU', 'CA07', 'S', 54000, 'img/products/PR062.jpg', b'0'),
('PR063', 'TRÀ SỮA DÂU', 'CA07', 'M', 59000, 'img/products/PR063.jpg', b'0'),
('PR064', 'TRÀ SỮA SÔ-CÔ-LA', 'CA07', 'S', 54000, 'img/products/PR064.jpg', b'0'),
('PR065', 'TRÀ SỮA SÔ-CÔ-LA', 'CA07', 'M', 59000, 'img/products/PR065.jpg', b'0');

--
-- Triggers `product`
--
DELIMITER $$
CREATE TRIGGER `InsertProduct` AFTER INSERT ON `product` FOR EACH ROW UPDATE category SET category.QUANTITY = category.QUANTITY + 1
WHERE category.CATEGORY_ID = NEW.CATEGORY_ID
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `UpdateProduct` AFTER UPDATE ON `product` FOR EACH ROW BEGIN
UPDATE category
SET QUANTITY = ( SELECT COUNT(PRO.PRODUCT_ID) FROM product PRO WHERE PRO.CATEGORY_ID = category.CATEGORY_ID AND PRO.DELETED = b'0' );
UPDATE recipe
SET recipe.DELETED = b'1'
WHERE recipe.PRODUCT_ID IN (SELECT PRO.PRODUCT_ID FROM product PRO WHERE PRO.DELETED = b'1');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `RECEIPT_ID` varchar(10) NOT NULL,
  `STAFF_ID` varchar(10) NOT NULL,
  `DOR` date DEFAULT NULL,
  `GRAND_TOTAL` double DEFAULT 0,
  `SUPPLIER_ID` varchar(10) NOT NULL,
  `DELETED` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`RECEIPT_ID`, `STAFF_ID`, `DOR`, `GRAND_TOTAL`, `SUPPLIER_ID`, `DELETED`) VALUES
('REC001', 'ST06', '2023-03-01', 1500000, 'SUP001', b'0'),
('REC002', 'ST06', '2023-01-01', 1500000, 'SUP001', b'0'),
('REC003', 'ST06', '2023-02-01', 1500000, 'SUP001', b'0'),
('REC004', 'ST06', '2023-01-01', 750000, 'SUP001', b'0'),
('REC005', 'ST06', '2022-03-01', 1500000, 'SUP001', b'0'),
('REC006', 'ST06', '2022-04-01', 750000, 'SUP001', b'0'),
('REC007', 'ST06', '2022-06-01', 750000, 'SUP001', b'0'),
('REC008', 'ST06', '2022-03-01', 750000, 'SUP001', b'0'),
('REC009', 'ST06', '2022-03-01', 750000, 'SUP001', b'0'),
('REC010', 'ST06', '2022-03-01', 750000, 'SUP001', b'0'),
('REC011', 'ST06', '2022-02-01', 1500000, 'SUP001', b'0'),
('REC012', 'ST06', '2022-02-01', 1500000, 'SUP001', b'0'),
('REC013', 'ST06', '2022-02-01', 750000, 'SUP001', b'0'),
('REC014', 'ST06', '2022-02-01', 750000, 'SUP001', b'0'),
('REC015', 'ST06', '2022-03-01', 750000, 'SUP001', b'0'),
('REC016', 'ST06', '2022-03-01', 300000, 'SUP002', b'0'),
('REC017', 'ST06', '2021-03-01', 750000, 'SUP002', b'0'),
('REC018', 'ST06', '2021-03-01', 300000, 'SUP002', b'0'),
('REC019', 'ST06', '2021-07-01', 750000, 'SUP002', b'0'),
('REC020', 'ST06', '2021-07-01', 750000, 'SUP003', b'0'),
('REC021', 'ST06', '2021-07-01', 300000, 'SUP002', b'0'),
('REC022', 'ST06', '2021-07-01', 1500000, 'SUP001', b'0'),
('REC023', 'ST06', '2021-09-01', 450000, 'SUP001', b'0'),
('REC024', 'ST06', '2021-09-01', 750000, 'SUP001', b'0'),
('REC025', 'ST06', '2021-09-01', 750000, 'SUP001', b'0'),
('REC026', 'ST06', '2021-09-01', 1500000, 'SUP001', b'0'),
('REC027', 'ST06', '2021-05-01', 750000, 'SUP001', b'0'),
('REC028', 'ST06', '2021-05-01', 750000, 'SUP001', b'0'),
('REC029', 'ST06', '2021-05-01', 750000, 'SUP001', b'0'),
('REC030', 'ST06', '2022-05-01', 750000, 'SUP001', b'0'),
('REC031', 'ST06', '2022-02-01', 750000, 'SUP001', b'0'),
('REC032', 'ST06', '2022-02-01', 1500000, 'SUP001', b'0'),
('REC033', 'ST06', '2022-02-01', 750000, 'SUP001', b'0'),
('REC034', 'ST06', '2022-02-01', 750000, 'SUP001', b'0'),
('REC035', 'ST06', '2022-01-01', 750000, 'SUP001', b'0'),
('REC036', 'ST06', '2022-01-01', 300000, 'SUP001', b'0'),
('REC037', 'ST06', '2022-01-01', 750000, 'SUP001', b'0'),
('REC038', 'ST06', '2022-01-01', 750000, 'SUP001', b'0'),
('REC039', 'ST06', '2023-01-01', 1500000, 'SUP003', b'0'),
('REC040', 'ST06', '2023-01-01', 300000, 'SUP001', b'0'),
('REC041', 'ST06', '2023-01-01', 1500000, 'SUP001', b'0'),
('REC042', 'ST06', '2022-01-01', 300000, 'SUP001', b'0'),
('REC043', 'ST06', '2023-01-01', 750000, 'SUP001', b'0'),
('REC044', 'ST06', '2022-01-01', 1500000, 'SUP001', b'0'),
('REC045', 'ST06', '2023-03-01', 750000, 'SUP001', b'0'),
('REC046', 'ST06', '2022-04-01', 300000, 'SUP001', b'0'),
('REC047', 'ST06', '2022-04-01', 750000, 'SUP001', b'0'),
('REC048', 'ST06', '2023-03-01', 1500000, 'SUP001', b'0'),
('REC049', 'ST06', '2022-04-01', 750000, 'SUP001', b'0'),
('REC050', 'ST06', '2022-01-01', 750000, 'SUP001', b'0'),
('REC051', 'ST06', '2023-03-01', 300000, 'SUP001', b'0'),
('REC052', 'ST06', '2022-01-01', 750000, 'SUP001', b'0'),
('REC053', 'ST06', '2022-01-01', 300000, 'SUP001', b'0'),
('REC054', 'ST06', '2022-01-01', 1500000, 'SUP001', b'0'),
('REC055', 'ST06', '2022-01-01', 750000, 'SUP001', b'0'),
('REC056', 'ST06', '2023-03-01', 750000, 'SUP001', b'0'),
('REC057', 'ST06', '2023-03-01', 1500000, 'SUP001', b'0'),
('REC058', 'ST06', '2022-01-01', 750000, 'SUP001', b'0'),
('REC059', 'ST06', '2023-01-01', 750000, 'SUP001', b'0'),
('REC060', 'ST06', '2022-01-01', 300000, 'SUP001', b'0'),
('REC061', 'ST06', '2023-01-01', 300000, 'SUP001', b'0'),
('REC062', 'ST06', '2023-03-01', 750000, 'SUP001', b'0');

--
-- Triggers `receipt`
--
DELIMITER $$
CREATE TRIGGER `UpdateReceipt` AFTER UPDATE ON `receipt` FOR EACH ROW BEGIN
IF NEW.DELETED <> OLD.DELETED THEN
	CREATE TEMPORARY TABLE my_temp_table ( INGREDIENT_ID VARCHAR(10) NOT NULL, QUANTITY DOUBLE NOT NULL, PRIMARY KEY (INGREDIENT_ID) );
	INSERT INTO my_temp_table (INGREDIENT_ID, QUANTITY)
    SELECT RD.INGREDIENT_ID, RD.QUANTITY
    FROM receipt_details RD
    WHERE RD.RECEIPT_ID = NEW.RECEIPT_ID;

	UPDATE ingredient
    SET ingredient.QUANTITY = ingredient.QUANTITY - (SELECT QUANTITY
                                                     FROM my_temp_table WHERE my_temp_table.INGREDIENT_ID = ingredient.INGREDIENT_ID)
    WHERE ingredient.INGREDIENT_ID IN (SELECT INGREDIENT_ID FROM my_temp_table);
END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `receipt_details`
--

CREATE TABLE `receipt_details` (
  `RECEIPT_ID` varchar(10) NOT NULL,
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `QUANTITY` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `receipt_details`
--

INSERT INTO `receipt_details` (`RECEIPT_ID`, `INGREDIENT_ID`, `QUANTITY`) VALUES
('REC001', 'ING001', 10),
('REC002', 'ING002', 10),
('REC003', 'ING003', 10),
('REC004', 'ING004', 5),
('REC005', 'ING005', 1000),
('REC006', 'ING006', 5),
('REC007', 'ING007', 5),
('REC008', 'ING008', 5),
('REC009', 'ING009', 5),
('REC010', 'ING010', 5),
('REC011', 'ING011', 10),
('REC012', 'ING012', 10),
('REC013', 'ING013', 5),
('REC014', 'ING014', 5),
('REC015', 'ING015', 5),
('REC016', 'ING016', 2),
('REC017', 'ING017', 5),
('REC018', 'ING018', 2),
('REC019', 'ING019', 5),
('REC020', 'ING020', 5),
('REC021', 'ING021', 2),
('REC022', 'ING022', 10),
('REC023', 'ING023', 3),
('REC024', 'ING024', 5),
('REC025', 'ING025', 5),
('REC026', 'ING026', 10),
('REC027', 'ING027', 5),
('REC028', 'ING028', 5),
('REC029', 'ING029', 5),
('REC030', 'ING030', 5),
('REC031', 'ING031', 5),
('REC032', 'ING032', 10),
('REC033', 'ING033', 5),
('REC034', 'ING034', 5),
('REC035', 'ING035', 5),
('REC036', 'ING036', 2),
('REC037', 'ING037', 5),
('REC038', 'ING038', 5),
('REC039', 'ING039', 5),
('REC040', 'ING040', 2),
('REC041', 'ING041', 2),
('REC042', 'ING042', 5),
('REC043', 'ING043', 12),
('REC044', 'ING044', 20),
('REC045', 'ING045', 5),
('REC046', 'ING046', 2),
('REC047', 'ING047', 5),
('REC048', 'ING048', 6),
('REC049', 'ING049', 6),
('REC050', 'ING050', 12),
('REC051', 'ING051', 5),
('REC052', 'ING052', 2),
('REC053', 'ING053', 5),
('REC054', 'ING054', 10),
('REC055', 'ING055', 10),
('REC056', 'ING056', 8),
('REC057', 'ING057', 7),
('REC058', 'ING058', 12),
('REC059', 'ING059', 5),
('REC060', 'ING060', 6),
('REC061', 'ING061', 6),
('REC062', 'ING062', 6);

--
-- Triggers `receipt_details`
--
DELIMITER $$
CREATE TRIGGER `InsertReceipt_Details` AFTER INSERT ON `receipt_details` FOR EACH ROW BEGIN
UPDATE ingredient
SET ingredient.QUANTITY = ingredient.QUANTITY + NEW.QUANTITY
WHERE ingredient.INGREDIENT_ID = NEW.INGREDIENT_ID;
UPDATE receipt
SET receipt.GRAND_TOTAL = receipt.GRAND_TOTAL + (SELECT ingredient.UNIT_PRICE FROM ingredient WHERE ingredient.INGREDIENT_ID = NEW.INGREDIENT_ID) * NEW.QUANTITY
WHERE receipt.RECEIPT_ID = NEW.RECEIPT_ID;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `recipe`
--

CREATE TABLE `recipe` (
  `RECIPE_ID` varchar(10) NOT NULL,
  `PRODUCT_ID` varchar(10) NOT NULL,
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `MASS` double DEFAULT 0,
  `UNIT` varchar(10) DEFAULT NULL,
  `DELETED` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `recipe`
--

INSERT INTO `recipe` (`RECIPE_ID`, `PRODUCT_ID`, `INGREDIENT_ID`, `MASS`, `UNIT`, `DELETED`) VALUES
('RE001', 'PR001', 'ING001', 0.03, 'kg', b'0'),
('RE002', 'PR001', 'ING002', 0.015, 'l', b'0'),
('RE003', 'PR001', 'ING003', 0.01, 'l', b'0'),
('RE004', 'PR001', 'ING004', 0.02, 'kg', b'0'),
('RE005', 'PR001', 'ING005', 0.13, 'l', b'0'),
('RE006', 'PR002', 'ING001', 0.04, 'kg', b'0'),
('RE007', 'PR002', 'ING002', 0.025, 'l', b'0'),
('RE008', 'PR002', 'ING003', 0.02, 'l', b'0'),
('RE009', 'PR002', 'ING004', 0.03, 'kg', b'0'),
('RE010', 'PR002', 'ING005', 0.23, 'l', b'0'),
('RE011', 'PR003', 'ING001', 0.045, 'kg', b'0'),
('RE012', 'PR003', 'ING002', 0.03, 'l', b'0'),
('RE013', 'PR003', 'ING003', 0.025, 'l', b'0'),
('RE014', 'PR003', 'ING004', 0.035, 'kg', b'0'),
('RE015', 'PR003', 'ING005', 0.28, 'l', b'0'),
('RE016', 'PR004', 'ING001', 0.02, 'kg', b'0'),
('RE017', 'PR004', 'ING004', 0.02, 'kg', b'0'),
('RE018', 'PR004', 'ING005', 0.13, 'l', b'0'),
('RE019', 'PR004', 'ING006', 0.002, 'kg', b'0'),
('RE020', 'PR004', 'ING007', 0.005, 'kg', b'0'),
('RE021', 'PR005', 'ING001', 0.027, 'kg', b'0'),
('RE022', 'PR005', 'ING004', 0.027, 'kg', b'0'),
('RE023', 'PR005', 'ING005', 0.2, 'l', b'0'),
('RE024', 'PR005', 'ING006', 0.003, 'kg', b'0'),
('RE025', 'PR005', 'ING007', 0.006, 'kg', b'0'),
('RE026', 'PR006', 'ING001', 0.031, 'kg', b'0'),
('RE027', 'PR006', 'ING004', 0.031, 'kg', b'0'),
('RE028', 'PR006', 'ING005', 0.25, 'l', b'0'),
('RE029', 'PR006', 'ING006', 0.0035, 'kg', b'0'),
('RE030', 'PR006', 'ING007', 0.0065, 'kg', b'0'),
('RE031', 'PR007', 'ING002', 0.03, 'kg', b'0'),
('RE032', 'PR007', 'ING003', 0.02, 'kg', b'0'),
('RE033', 'PR007', 'ING004', 0.02, 'kg', b'0'),
('RE034', 'PR007', 'ING005', 0.1, 'l', b'0'),
('RE035', 'PR007', 'ING008', 0.01, 'l', b'0'),
('RE036', 'PR008', 'ING002', 0.04, 'kg', b'0'),
('RE037', 'PR008', 'ING003', 0.03, 'kg', b'0'),
('RE038', 'PR008', 'ING004', 0.03, 'kg', b'0'),
('RE039', 'PR008', 'ING005', 0.2, 'l', b'0'),
('RE040', 'PR008', 'ING008', 0.02, 'l', b'0'),
('RE041', 'PR009', 'ING002', 0.045, 'kg', b'0'),
('RE042', 'PR009', 'ING003', 0.035, 'kg', b'0'),
('RE043', 'PR009', 'ING004', 0.035, 'kg', b'0'),
('RE044', 'PR009', 'ING005', 0.25, 'l', b'0'),
('RE045', 'PR009', 'ING008', 0.025, 'l', b'0'),
('RE046', 'PR010', 'ING004', 0.02, 'kg', b'0'),
('RE047', 'PR010', 'ING008', 0.01, 'l', b'0'),
('RE048', 'PR010', 'ING009', 0.01, 'l', b'0'),
('RE049', 'PR010', 'ING010', 0.075, 'l', b'0'),
('RE050', 'PR010', 'ING011', 0.025, 'l', b'0'),
('RE051', 'PR010', 'ING012', 0.025, 'kg', b'0'),
('RE052', 'PR011', 'ING004', 0.025, 'kg', b'0'),
('RE053', 'PR011', 'ING008', 0.015, 'l', b'0'),
('RE054', 'PR011', 'ING009', 0.015, 'l', b'0'),
('RE055', 'PR011', 'ING010', 0.08, 'l', b'0'),
('RE056', 'PR011', 'ING011', 0.03, 'l', b'0'),
('RE057', 'PR011', 'ING012', 0.03, 'kg', b'0'),
('RE058', 'PR012', 'ING004', 0.03, 'kg', b'0'),
('RE059', 'PR012', 'ING008', 0.02, 'l', b'0'),
('RE060', 'PR012', 'ING009', 0.02, 'l', b'0'),
('RE061', 'PR012', 'ING010', 0.085, 'l', b'0'),
('RE062', 'PR012', 'ING011', 0.035, 'l', b'0'),
('RE063', 'PR012', 'ING012', 0.035, 'kg', b'0'),
('RE064', 'PR013', 'ING004', 0.02, 'kg', b'0'),
('RE065', 'PR013', 'ING010', 0.075, 'l', b'0'),
('RE066', 'PR013', 'ING011', 0.025, 'l', b'0'),
('RE067', 'PR013', 'ING012', 0.025, 'kg', b'0'),
('RE068', 'PR013', 'ING013', 0.01, 'l', b'0'),
('RE069', 'PR013', 'ING014', 0.035, 'l', b'0'),
('RE070', 'PR014', 'ING004', 0.025, 'kg', b'0'),
('RE071', 'PR014', 'ING010', 0.08, 'l', b'0'),
('RE072', 'PR014', 'ING011', 0.03, 'l', b'0'),
('RE073', 'PR014', 'ING012', 0.03, 'kg', b'0'),
('RE074', 'PR014', 'ING013', 0.015, 'l', b'0'),
('RE075', 'PR014', 'ING014', 0.04, 'l', b'0'),
('RE076', 'PR015', 'ING004', 0.03, 'kg', b'0'),
('RE077', 'PR015', 'ING010', 0.085, 'l', b'0'),
('RE078', 'PR015', 'ING011', 0.035, 'l', b'0'),
('RE079', 'PR015', 'ING012', 0.035, 'kg', b'0'),
('RE080', 'PR015', 'ING013', 0.02, 'l', b'0'),
('RE081', 'PR015', 'ING014', 0.045, 'l', b'0'),
('RE082', 'PR016', 'ING004', 0.02, 'kg', b'0'),
('RE083', 'PR016', 'ING010', 0.075, 'l', b'0'),
('RE084', 'PR016', 'ING011', 0.025, 'l', b'0'),
('RE085', 'PR016', 'ING012', 0.25, 'kg', b'0'),
('RE086', 'PR016', 'ING013', 0.015, 'l', b'0'),
('RE087', 'PR016', 'ING015', 0.01, 'l', b'0'),
('RE088', 'PR017', 'ING004', 0.025, 'kg', b'0'),
('RE089', 'PR017', 'ING010', 0.08, 'l', b'0'),
('RE090', 'PR017', 'ING011', 0.03, 'l', b'0'),
('RE091', 'PR017', 'ING012', 0.3, 'kg', b'0'),
('RE092', 'PR017', 'ING013', 0.02, 'l', b'0'),
('RE093', 'PR017', 'ING015', 0.015, 'l', b'0'),
('RE094', 'PR018', 'ING004', 0.03, 'kg', b'0'),
('RE095', 'PR018', 'ING010', 0.085, 'l', b'0'),
('RE096', 'PR018', 'ING011', 0.035, 'l', b'0'),
('RE097', 'PR018', 'ING012', 0.35, 'kg', b'0'),
('RE098', 'PR018', 'ING013', 0.025, 'l', b'0'),
('RE099', 'PR018', 'ING015', 0.02, 'l', b'0'),
('RE100', 'PR019', 'ING016', 0.015, 'kg', b'0'),
('RE101', 'PR019', 'ING017', 0.01, 'l', b'0'),
('RE102', 'PR019', 'ING018', 0.03, 'kg', b'0'),
('RE103', 'PR019', 'ING019', 0.03, 'kg', b'0'),
('RE104', 'PR019', 'ING020', 0.1, 'kg', b'0'),
('RE105', 'PR020', 'ING017', 0.01, 'l', b'0'),
('RE106', 'PR020', 'ING018', 0.03, 'kg', b'0'),
('RE107', 'PR020', 'ING020', 0.1, 'kg', b'0'),
('RE108', 'PR020', 'ING021', 0.01, 'kg', b'0'),
('RE109', 'PR021', 'ING004', 0.02, 'kg', b'0'),
('RE110', 'PR021', 'ING005', 0.13, 'l', b'0'),
('RE111', 'PR021', 'ING022', 0.002, 'kg', b'0'),
('RE112', 'PR021', 'ING023', 0.02, 'kg', b'0'),
('RE113', 'PR021', 'ING024', 0.05, 'l', b'0'),
('RE114', 'PR021', 'ING025', 0.015, 'kg', b'0'),
('RE115', 'PR022', 'ING004', 0.028, 'kg', b'0'),
('RE116', 'PR022', 'ING005', 0.21, 'l', b'0'),
('RE117', 'PR022', 'ING022', 0.0028, 'kg', b'0'),
('RE118', 'PR022', 'ING023', 0.028, 'kg', b'0'),
('RE119', 'PR022', 'ING024', 0.064, 'l', b'0'),
('RE120', 'PR022', 'ING025', 0.023, 'kg', b'0'),
('RE121', 'PR023', 'ING004', 0.034, 'kg', b'0'),
('RE122', 'PR023', 'ING005', 0.27, 'l', b'0'),
('RE123', 'PR023', 'ING022', 0.0035, 'kg', b'0'),
('RE124', 'PR023', 'ING023', 0.031, 'kg', b'0'),
('RE125', 'PR023', 'ING024', 0.069, 'l', b'0'),
('RE126', 'PR023', 'ING025', 0.028, 'kg', b'0'),
('RE127', 'PR024', 'ING004', 0.02, 'kg', b'0'),
('RE128', 'PR024', 'ING005', 0.13, 'l', b'0'),
('RE129', 'PR024', 'ING007', 0.02, 'l', b'0'),
('RE130', 'PR024', 'ING026', 0.04, 'kg', b'0'),
('RE131', 'PR024', 'ING027', 0.03, 'kg', b'0'),
('RE132', 'PR024', 'ING028', 0.02, 'kg', b'0'),
('RE133', 'PR024', 'ING029', 0.05, 'kg', b'0'),
('RE134', 'PR025', 'ING004', 0.028, 'kg', b'0'),
('RE135', 'PR025', 'ING005', 0.2, 'l', b'0'),
('RE136', 'PR025', 'ING007', 0.027, 'l', b'0'),
('RE137', 'PR025', 'ING026', 0.045, 'kg', b'0'),
('RE138', 'PR025', 'ING027', 0.035, 'kg', b'0'),
('RE139', 'PR025', 'ING028', 0.03, 'kg', b'0'),
('RE140', 'PR025', 'ING029', 0.054, 'kg', b'0'),
('RE141', 'PR026', 'ING004', 0.035, 'kg', b'0'),
('RE142', 'PR026', 'ING005', 0.25, 'l', b'0'),
('RE143', 'PR026', 'ING007', 0.032, 'l', b'0'),
('RE144', 'PR026', 'ING026', 0.05, 'kg', b'0'),
('RE145', 'PR026', 'ING027', 0.042, 'kg', b'0'),
('RE146', 'PR026', 'ING028', 0.037, 'kg', b'0'),
('RE147', 'PR026', 'ING029', 0.053, 'kg', b'0'),
('RE148', 'PR027', 'ING004', 0.02, 'kg', b'0'),
('RE149', 'PR027', 'ING005', 0.13, 'l', b'0'),
('RE150', 'PR027', 'ING007', 0.01, 'kg', b'0'),
('RE151', 'PR027', 'ING029', 0.05, 'kg', b'0'),
('RE152', 'PR027', 'ING030', 0.04, 'kg', b'0'),
('RE153', 'PR027', 'ING031', 0.01, 'kg', b'0'),
('RE154', 'PR027', 'ING032', 0.015, 'l', b'0'),
('RE155', 'PR028', 'ING004', 0.025, 'kg', b'0'),
('RE156', 'PR028', 'ING005', 0.19, 'l', b'0'),
('RE157', 'PR028', 'ING029', 0.053, 'kg', b'0'),
('RE158', 'PR028', 'ING030', 0.045, 'kg', b'0'),
('RE159', 'PR028', 'ING031', 0.03, 'kg', b'0'),
('RE160', 'PR028', 'ING032', 0.02, 'l', b'0'),
('RE161', 'PR029', 'ING004', 0.03, 'kg', b'0'),
('RE162', 'PR029', 'ING005', 0.25, 'l', b'0'),
('RE163', 'PR029', 'ING007', 0.02, 'kg', b'0'),
('RE164', 'PR029', 'ING029', 0.06, 'kg', b'0'),
('RE165', 'PR029', 'ING030', 0.052, 'kg', b'0'),
('RE166', 'PR029', 'ING031', 0.021, 'kg', b'0'),
('RE167', 'PR029', 'ING032', 0.028, 'l', b'0'),
('RE168', 'PR030', 'ING004', 0.02, 'kg', b'0'),
('RE169', 'PR030', 'ING005', 0.12, 'l', b'0'),
('RE170', 'PR030', 'ING007', 0.02, 'l', b'0'),
('RE171', 'PR030', 'ING032', 0.002, 'kg', b'0'),
('RE172', 'PR030', 'ING033', 0.015, 'kg', b'0'),
('RE173', 'PR030', 'ING034', 0.05, 'kg', b'0'),
('RE174', 'PR030', 'ING035', 0.05, 'kg', b'0'),
('RE175', 'PR031', 'ING004', 0.025, 'kg', b'0'),
('RE176', 'PR031', 'ING005', 0.128, 'l', b'0'),
('RE177', 'PR031', 'ING007', 0.024, 'l', b'0'),
('RE178', 'PR031', 'ING032', 0.0028, 'kg', b'0'),
('RE179', 'PR031', 'ING033', 0.0154, 'kg', b'0'),
('RE180', 'PR031', 'ING034', 0.053, 'kg', b'0'),
('RE181', 'PR031', 'ING035', 0.054, 'kg', b'0'),
('RE182', 'PR032', 'ING004', 0.03, 'kg', b'0'),
('RE183', 'PR032', 'ING005', 0.135, 'l', b'0'),
('RE184', 'PR032', 'ING007', 0.03, 'l', b'0'),
('RE185', 'PR032', 'ING032', 0.0035, 'kg', b'0'),
('RE186', 'PR032', 'ING033', 0.016, 'kg', b'0'),
('RE187', 'PR032', 'ING034', 0.058, 'kg', b'0'),
('RE188', 'PR032', 'ING035', 0.057, 'kg', b'0'),
('RE189', 'PR033', 'ING002', 0.025, 'l', b'0'),
('RE190', 'PR033', 'ING003', 0.02, 'l', b'0'),
('RE191', 'PR033', 'ING004', 0.02, 'kg', b'0'),
('RE192', 'PR033', 'ING005', 0.12, 'l', b'0'),
('RE193', 'PR033', 'ING036', 0.002, 'kg', b'0'),
('RE194', 'PR033', 'ING037', 0.005, 'l', b'0'),
('RE195', 'PR033', 'ING038', 0.015, 'kg', b'0'),
('RE196', 'PR034', 'ING002', 0.03, 'l', b'0'),
('RE197', 'PR034', 'ING003', 0.025, 'l', b'0'),
('RE198', 'PR034', 'ING004', 0.025, 'kg', b'0'),
('RE199', 'PR034', 'ING005', 0.125, 'l', b'0'),
('RE200', 'PR034', 'ING036', 0.0025, 'kg', b'0'),
('RE201', 'PR034', 'ING037', 0.0055, 'l', b'0'),
('RE202', 'PR034', 'ING038', 0.0155, 'kg', b'0'),
('RE203', 'PR035', 'ING002', 0.033, 'l', b'0'),
('RE204', 'PR035', 'ING003', 0.0258, 'l', b'0'),
('RE205', 'PR035', 'ING004', 0.0258, 'kg', b'0'),
('RE206', 'PR035', 'ING005', 0.1254, 'l', b'0'),
('RE207', 'PR035', 'ING036', 0.00253, 'kg', b'0'),
('RE208', 'PR035', 'ING037', 0.00552, 'l', b'0'),
('RE209', 'PR035', 'ING038', 0.01555, 'kg', b'0'),
('RE210', 'PR036', 'ING043', 3, 'quả', b'0'),
('RE211', 'PR036', 'ING002', 0.09, 'l', b'0'),
('RE212', 'PR036', 'ING039', 0.23, 'kg', b'0'),
('RE213', 'PR036', 'ING007', 0.007, 'kg', b'0'),
('RE214', 'PR036', 'ING040', 0.02, 'kg', b'0'),
('RE215', 'PR036', 'ING041', 0.003, 'kg', b'0'),
('RE216', 'PR036', 'ING042', 0.01, 'kg', b'0'),
('RE217', 'PR036', 'ING006', 0.001, 'kg', b'0'),
('RE218', 'PR037', 'ING044', 4, 'quả', b'0'),
('RE219', 'PR037', 'ING045', 0.1, 'kg', b'0'),
('RE220', 'PR037', 'ING046', 0.03, 'kg', b'0'),
('RE221', 'PR037', 'ING042', 0.025, 'kg', b'0'),
('RE222', 'PR037', 'ING047', 0.1, 'kg', b'0'),
('RE223', 'PR037', 'ING048', 0.2, 'l', b'0'),
('RE224', 'PR037', 'ING007', 0.05, 'kg', b'0'),
('RE225', 'PR038', 'ING049', 0.15, 'kg', b'0'),
('RE226', 'PR038', 'ING050', 5, 'quả', b'0'),
('RE227', 'PR038', 'ING051', 0.024, 'l', b'0'),
('RE228', 'PR038', 'ING052', 0.01, 'kg', b'0'),
('RE229', 'PR038', 'ING053', 0.02, 'kg', b'0'),
('RE230', 'PR038', 'ING054', 0.4, 'kg', b'0'),
('RE231', 'PR038', 'ING055', 0.025, 'l', b'0'),
('RE232', 'PR038', 'ING041', 0.01, 'kg', b'0'),
('RE233', 'PR038', 'ING007', 0.26, 'kg', b'0'),
('RE234', 'PR038', 'ING002', 0.04, 'l', b'0'),
('RE235', 'PR038', 'ING001', 0.2, 'kg', b'0'),
('RE236', 'PR038', 'ING048', 0.5, 'l', b'0'),
('RE237', 'PR038', 'ING046', 0.01, 'l', b'0'),
('RE238', 'PR039', 'ING029', 0.35, 'kg', b'0'),
('RE239', 'PR039', 'ING031', 0.2, 'l', b'0'),
('RE240', 'PR039', 'ING057', 0.01, 'l', b'0'),
('RE241', 'PR039', 'ING046', 0.02, 'kg', b'0'),
('RE242', 'PR039', 'ING002', 0.2, 'l', b'0'),
('RE243', 'PR039', 'ING048', 0.2, 'kg', b'0'),
('RE244', 'PR039', 'ING007', 0.1, 'kg', b'0'),
('RE245', 'PR039', 'ING050', 2, 'quả', b'0'),
('RE246', 'PR040', 'ING046', 0.01, 'kg', b'0'),
('RE247', 'PR040', 'ING005', 0.025, 'l', b'0'),
('RE248', 'PR040', 'ING047', 0.2, 'kg', b'0'),
('RE249', 'PR040', 'ING058', 0.1, 'kg', b'0'),
('RE250', 'PR040', 'ING007', 0.08, 'kg', b'0'),
('RE251', 'PR040', 'ING048', 0.2, 'kg', b'0'),
('RE252', 'PR040', 'ING036', 0.01, 'kg', b'0'),
('RE253', 'PR040', 'ING002', 0.03, 'l', b'0'),
('RE254', 'PR041', 'ING042', 0.1, 'kg', b'0'),
('RE255', 'PR041', 'ING059', 0.05, 'kg', b'0'),
('RE256', 'PR041', 'ING047', 0.453, 'kg', b'0'),
('RE257', 'PR041', 'ING050', 2, 'quả', b'0'),
('RE258', 'PR041', 'ING003', 0.414, 'l', b'0'),
('RE259', 'PR041', 'ING007', 0.05, 'kg', b'0'),
('RE260', 'PR041', 'ING054', 0.03, 'kg', b'0'),
('RE261', 'PR042', 'ING045', 0.125, 'kg', b'0'),
('RE262', 'PR042', 'ING042', 0.03, 'kg', b'0'),
('RE263', 'PR042', 'ING048', 0.03, 'l', b'0'),
('RE264', 'PR042', 'ING001', 0.02, 'kg', b'0'),
('RE265', 'PR042', 'ING047', 0.675, 'kg', b'0'),
('RE266', 'PR042', 'ING058', 0.113, 'kg', b'0'),
('RE267', 'PR042', 'ING007', 0.22, 'kg', b'0'),
('RE268', 'PR042', 'ING050', 2, 'quả', b'0'),
('RE269', 'PR043', 'ING014', 0.6, 'l', b'0'),
('RE270', 'PR043', 'ING003', 0.08, 'l', b'0'),
('RE271', 'PR043', 'ING057', 0.04, 'l', b'0'),
('RE272', 'PR043', 'ING005', 0.2, 'l', b'0'),
('RE273', 'PR043', 'ING007', 0.05, 'kg', b'0'),
('RE274', 'PR043', 'ING036', 0.01, 'kg', b'0'),
('RE275', 'PR043', 'ING058', 0.2, 'l', b'0'),
('RE276', 'PR043', 'ING002', 0.1, 'l', b'0'),
('RE277', 'PR043', 'ING004', 0.03, 'kg', b'0'),
('RE278', 'PR044', 'ING014', 0.65, 'l', b'0'),
('RE279', 'PR044', 'ING003', 0.09, 'l', b'0'),
('RE280', 'PR044', 'ING057', 0.05, 'l', b'0'),
('RE281', 'PR044', 'ING005', 0.25, 'l', b'0'),
('RE282', 'PR044', 'ING007', 0.055, 'kg', b'0'),
('RE283', 'PR044', 'ING036', 0.015, 'kg', b'0'),
('RE284', 'PR044', 'ING058', 0.25, 'l', b'0'),
('RE285', 'PR044', 'ING002', 0.15, 'l', b'0'),
('RE286', 'PR044', 'ING004', 0.05, 'kg', b'0'),
('RE287', 'PR045', 'ING014', 0.68, 'l', b'0'),
('RE288', 'PR045', 'ING003', 0.093, 'l', b'0'),
('RE289', 'PR045', 'ING057', 0.055, 'l', b'0'),
('RE290', 'PR045', 'ING005', 0.254, 'l', b'0'),
('RE291', 'PR045', 'ING007', 0.059, 'kg', b'0'),
('RE292', 'PR045', 'ING036', 0.017, 'kg', b'0'),
('RE293', 'PR045', 'ING058', 0.256, 'l', b'0'),
('RE294', 'PR045', 'ING002', 0.17, 'l', b'0'),
('RE295', 'PR045', 'ING004', 0.053, 'kg', b'0'),
('RE296', 'PR046', 'ING048', 0.24, 'l', b'0'),
('RE297', 'PR046', 'ING007', 0.2, 'kg', b'0'),
('RE298', 'PR046', 'ING057', 0.01, 'l', b'0'),
('RE299', 'PR046', 'ING004', 0.15, 'kg', b'0'),
('RE300', 'PR046', 'ING001', 0.125, 'kg', b'0'),
('RE301', 'PR046', 'ING003', 0.2, 'l', b'0'),
('RE302', 'PR046', 'ING011', 0.1, 'kg', b'0'),
('RE303', 'PR047', 'ING048', 0.28, 'l', b'0'),
('RE304', 'PR047', 'ING007', 0.25, 'kg', b'0'),
('RE305', 'PR047', 'ING057', 0.02, 'l', b'0'),
('RE306', 'PR047', 'ING004', 0.175, 'kg', b'0'),
('RE307', 'PR047', 'ING001', 0.175, 'kg', b'0'),
('RE308', 'PR047', 'ING003', 0.25, 'l', b'0'),
('RE309', 'PR047', 'ING011', 0.15, 'kg', b'0'),
('RE310', 'PR048', 'ING048', 0.3, 'l', b'0'),
('RE311', 'PR048', 'ING007', 0.275, 'kg', b'0'),
('RE312', 'PR048', 'ING057', 0.029, 'l', b'0'),
('RE313', 'PR048', 'ING004', 0.185, 'kg', b'0'),
('RE314', 'PR048', 'ING001', 0.19, 'kg', b'0'),
('RE315', 'PR048', 'ING003', 0.275, 'l', b'0'),
('RE316', 'PR048', 'ING011', 0.195, 'kg', b'0'),
('RE317', 'PR049', 'ING045', 0.2, 'kg', b'0'),
('RE318', 'PR049', 'ING015', 0.02, 'l', b'0'),
('RE319', 'PR049', 'ING007', 0.02, 'l', b'0'),
('RE320', 'PR049', 'ING003', 0.03, 'l', b'0'),
('RE321', 'PR049', 'ING002', 0.045, 'l', b'0'),
('RE322', 'PR049', 'ING057', 0.02, 'l', b'0'),
('RE323', 'PR050', 'ING045', 0.25, 'kg', b'0'),
('RE324', 'PR050', 'ING015', 0.024, 'l', b'0'),
('RE325', 'PR050', 'ING007', 0.03, 'l', b'0'),
('RE326', 'PR050', 'ING003', 0.04, 'l', b'0'),
('RE327', 'PR050', 'ING002', 0.06, 'l', b'0'),
('RE328', 'PR050', 'ING057', 0.025, 'l', b'0'),
('RE329', 'PR051', 'ING045', 0.27, 'kg', b'0'),
('RE330', 'PR051', 'ING015', 0.03, 'l', b'0'),
('RE331', 'PR051', 'ING007', 0.039, 'l', b'0'),
('RE332', 'PR051', 'ING003', 0.05, 'l', b'0'),
('RE333', 'PR051', 'ING002', 0.07, 'l', b'0'),
('RE334', 'PR051', 'ING057', 0.035, 'l', b'0'),
('RE335', 'PR052', 'ING001', 0.1, 'kg', b'0'),
('RE336', 'PR052', 'ING011', 0.2, 'kg', b'0'),
('RE337', 'PR052', 'ING003', 0.02, 'kg', b'0'),
('RE338', 'PR052', 'ING014', 0.04, 'kg', b'0'),
('RE339', 'PR052', 'ING004', 0.25, 'kg', b'0'),
('RE340', 'PR052', 'ING015', 0.09, 'kg', b'0'),
('RE341', 'PR053', 'ING001', 0.13, 'kg', b'0'),
('RE342', 'PR053', 'ING011', 0.25, 'kg', b'0'),
('RE343', 'PR053', 'ING003', 0.035, 'kg', b'0'),
('RE344', 'PR053', 'ING014', 0.05, 'kg', b'0'),
('RE345', 'PR053', 'ING004', 0.275, 'kg', b'0'),
('RE346', 'PR053', 'ING015', 0.12, 'kg', b'0'),
('RE347', 'PR054', 'ING001', 0.14, 'kg', b'0'),
('RE348', 'PR054', 'ING011', 0.275, 'kg', b'0'),
('RE349', 'PR054', 'ING003', 0.05, 'kg', b'0'),
('RE350', 'PR054', 'ING014', 0.045, 'kg', b'0'),
('RE351', 'PR054', 'ING004', 0.29, 'kg', b'0'),
('RE352', 'PR054', 'ING015', 0.14, 'kg', b'0'),
('RE353', 'PR055', 'ING048', 0.24, 'l', b'0'),
('RE354', 'PR055', 'ING007', 0.2, 'kg', b'0'),
('RE355', 'PR055', 'ING057', 0.01, 'l', b'0'),
('RE356', 'PR055', 'ING004', 0.15, 'kg', b'0'),
('RE357', 'PR055', 'ING001', 0.125, 'kg', b'0'),
('RE358', 'PR055', 'ING003', 0.2, 'l', b'0'),
('RE359', 'PR055', 'ING024', 0.05, 'l', b'0'),
('RE360', 'PR055', 'ING037', 0.2, 'l', b'0'),
('RE361', 'PR055', 'ING011', 0.1, 'kg', b'0'),
('RE362', 'PR056', 'ING048', 0.28, 'l', b'0'),
('RE363', 'PR056', 'ING007', 0.25, 'kg', b'0'),
('RE364', 'PR056', 'ING057', 0.02, 'l', b'0'),
('RE365', 'PR056', 'ING004', 0.175, 'kg', b'0'),
('RE366', 'PR056', 'ING001', 0.175, 'kg', b'0'),
('RE367', 'PR056', 'ING003', 0.25, 'l', b'0'),
('RE368', 'PR056', 'ING024', 0.08, 'l', b'0'),
('RE369', 'PR056', 'ING037', 0.23, 'l', b'0'),
('RE370', 'PR056', 'ING011', 0.15, 'kg', b'0'),
('RE371', 'PR057', 'ING048', 0.3, 'l', b'0'),
('RE372', 'PR057', 'ING007', 0.275, 'kg', b'0'),
('RE373', 'PR057', 'ING057', 0.029, 'l', b'0'),
('RE374', 'PR057', 'ING004', 0.185, 'kg', b'0'),
('RE375', 'PR057', 'ING001', 0.19, 'kg', b'0'),
('RE376', 'PR057', 'ING003', 0.275, 'l', b'0'),
('RE377', 'PR057', 'ING024', 0.95, 'l', b'0'),
('RE378', 'PR057', 'ING037', 0.25, 'l', b'0'),
('RE379', 'PR057', 'ING011', 0.195, 'kg', b'0'),
('RE380', 'PR058', 'ING036', 0.01, 'kg', b'0'),
('RE381', 'PR058', 'ING005', 0.1, 'l', b'0'),
('RE382', 'PR058', 'ING003', 0.02, 'l', b'0'),
('RE383', 'PR058', 'ING007', 0.03, 'l', b'0'),
('RE384', 'PR058', 'ING060', 0.02, 'l', b'0'),
('RE385', 'PR058', 'ING004', 0.2, 'l', b'0'),
('RE386', 'PR059', 'ING036', 0.02, 'kg', b'0'),
('RE387', 'PR059', 'ING005', 0.15, 'l', b'0'),
('RE388', 'PR059', 'ING003', 0.03, 'l', b'0'),
('RE389', 'PR059', 'ING007', 0.037, 'l', b'0'),
('RE390', 'PR059', 'ING060', 0.026, 'l', b'0'),
('RE391', 'PR059', 'ING004', 0.25, 'l', b'0'),
('RE392', 'PR060', 'ING041', 0.01, 'kg', b'0'),
('RE393', 'PR060', 'ING003', 0.2, 'kg', b'0'),
('RE394', 'PR060', 'ING007', 0.2, 'kg', b'0'),
('RE395', 'PR060', 'ING014', 0.06, 'kg', b'0'),
('RE396', 'PR060', 'ING036', 0.06, 'kg', b'0'),
('RE397', 'PR060', 'ING005', 0.3, 'l', b'0'),
('RE398', 'PR061', 'ING041', 0.02, 'kg', b'0'),
('RE399', 'PR061', 'ING003', 0.25, 'kg', b'0'),
('RE400', 'PR061', 'ING007', 0.23, 'kg', b'0'),
('RE401', 'PR061', 'ING014', 0.08, 'kg', b'0'),
('RE402', 'PR061', 'ING036', 0.07, 'kg', b'0'),
('RE403', 'PR061', 'ING005', 0.3, 'l', b'0'),
('RE404', 'PR062', 'ING041', 0.01, 'kg', b'0'),
('RE405', 'PR062', 'ING003', 0.2, 'kg', b'0'),
('RE406', 'PR062', 'ING007', 0.2, 'kg', b'0'),
('RE407', 'PR062', 'ING014', 0.06, 'kg', b'0'),
('RE408', 'PR062', 'ING061', 0.06, 'kg', b'0'),
('RE409', 'PR062', 'ING062', 0.08, 'l', b'0'),
('RE410', 'PR062', 'ING005', 0.3, 'l', b'0'),
('RE411', 'PR063', 'ING041', 0.02, 'kg', b'0'),
('RE412', 'PR063', 'ING003', 0.25, 'kg', b'0'),
('RE413', 'PR063', 'ING007', 0.23, 'kg', b'0'),
('RE414', 'PR063', 'ING014', 0.065, 'kg', b'0'),
('RE415', 'PR063', 'ING061', 0.075, 'kg', b'0'),
('RE416', 'PR063', 'ING062', 0.1, 'l', b'0'),
('RE417', 'PR063', 'ING005', 0.33, 'l', b'0'),
('RE418', 'PR064', 'ING041', 0.01, 'kg', b'0'),
('RE419', 'PR064', 'ING003', 0.2, 'kg', b'0'),
('RE420', 'PR064', 'ING007', 0.2, 'kg', b'0'),
('RE421', 'PR064', 'ING014', 0.06, 'kg', b'0'),
('RE422', 'PR064', 'ING001', 0.07, 'kg', b'0'),
('RE423', 'PR064', 'ING015', 0.085, 'l', b'0'),
('RE424', 'PR064', 'ING005', 0.3, 'l', b'0'),
('RE435', 'PR065', 'ING041', 0.02, 'kg', b'0'),
('RE436', 'PR065', 'ING003', 0.25, 'kg', b'0'),
('RE437', 'PR065', 'ING007', 0.23, 'kg', b'0'),
('RE438', 'PR065', 'ING014', 0.065, 'kg', b'0'),
('RE439', 'PR065', 'ING001', 0.075, 'kg', b'0'),
('RE440', 'PR065', 'ING015', 0.1, 'l', b'0'),
('RE441', 'PR065', 'ING005', 0.33, 'l', b'0');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `staff`
--

INSERT INTO `staff` (`STAFF_ID`, `NAME`, `GENDER`, `DOB`, `ADDRESS`, `PHONE`, `EMAIL`, `SALARY`, `DOENTRY`, `DELETED`) VALUES
('ST00', 'ADMIN', b'0', '1000-01-01', '', '', '', 0, '1000-01-01', b'0'),
('ST01', 'NGUYỄN TIẾN DŨNG', b'1', '2003-12-19', '531 Nguyễn Oanh, Phường 17, Gò Vấp, Thành phố Hồ Chí Minh', '0812535278', 'dungboi@gmail.com', 0, '1000-01-01', b'0'),
('ST02', 'ĐINH QUANG DUY', b'1', '2023-01-20', '1A Lê Đức Thọ, Phường 17, Gò Vấp, Thành phố Hồ Chí Minh', '0834527892', 'quangduy@gmail.com', 0, '1000-01-01', b'0'),
('ST03', 'NGUYỄN HOÀNG LONG', b'1', '2003-08-30', '514/26 Lê Đức Thọ, Phường 17, Gò Vấp, Thành phố Hồ Chí Minh', '0359872569', 'longbot@gmail.com', 0, '1000-01-01', b'0'),
('ST04', 'NGUYỄN ZI ĐAN', b'1', '2003-03-06', '153 Lê Hoàng Phái, Phường 17, Gò Vấp, Thành phố Hồ Chí Minh', '0970352875', 'zidan@gmail.com', 0, '1000-01-01', b'0'),
('ST05', 'NGUYỄN THỊ XUÂN MAI', b'0', '2002-06-19', '168 Lê Đức Thọ, Phường 15, Gò Vấp, Thành phố Hồ Chí Minh', '0367834257', 'thungan@gmail.com', 3100000, '2023-09-15', b'0'),
('ST06', 'ĐINH TIẾN MẠNH', b'1', '2002-09-20', '242 Nguyễn Văn Lượng, Phường 10, Gò Vấp, Thành phố Hồ Chí Minh', '0825367498', 'nhakho@gmail.com', 3100000, '2023-05-16', b'0'),
('ST07', 'ĐẶNG VĂN LÂM', b'1', '2001-02-18', '7 Phan Văn Trị, Phường 10, Gò Vấp, Thành phố Hồ Chí Minh', '0935627488', 'phache@gmail.com', 3100000, '2023-06-27', b'0'),
('ST08', 'NGUYỄN THỊ LỆ GIANG', b'0', '2000-05-27', '190 Quang Trung, Phường 10, Gò Vấp, Thành phố Hồ Chí Minh', '0340734629', 'phucvu@gmail.com', 3100000, '2023-09-28', b'0'),
('ST09', 'HOÀNG XUÂN PHÚC', b'1', '2001-04-11', '526 Lê Quang Định, Phường 1, Gò Vấp, Thành phố Hồ Chí Minh', '0963527895', 'sale@gmail.com', 2000000, '2023-08-17', b'0');

-- --------------------------------------------------------

--
-- Table structure for table `statistic`
--

CREATE TABLE `statistic` (
  `STATISTIC_ID` varchar(10) NOT NULL,
  `DATE` date DEFAULT NULL,
  `AMOUNT` double DEFAULT NULL,
  `INGREDIENT_COST` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `statistic`
--

INSERT INTO `statistic` (`STATISTIC_ID`, `DATE`, `AMOUNT`, `INGREDIENT_COST`) VALUES
('STAT0000', '2020-01-01', 0, 0),
('STAT0001', '2020-09-08', 239000, 29010),
('STAT0002', '2021-02-07', 195000, 20073),
('STAT0003', '2021-05-06', 110000, 30750),
('STAT0004', '2021-08-03', 175000, 25440),
('STAT0005', '2022-03-19', 173000, 36000),
('STAT0006', '2022-03-28', 109000, 14850),
('STAT0007', '2022-05-05', 135000, 8250),
('STAT0008', '2022-09-08', 90000, 215820),
('STAT0009', '2023-01-09', 277000, 55170),
('STAT0010', '2023-03-07', 207000, 49110);

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
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `supplier`
--

INSERT INTO `supplier` (`SUPPLIER_ID`, `NAME`, `PHONE`, `ADDRESS`, `EMAIL`, `DELETED`) VALUES
('SUP001', 'GLOFOOD', '02838035555', ' L2-10 Tầng 2 Pearl Plaza, 561A Điện Biên Phủ, Phường 25, Quận Bình Thạnh, TP. HCM', 'Gigroup@gigroup.net', b'0'),
('SUP002', 'BIG C', '0839958368', '163 Phan Đăng Lưu, P. 1, Quận Phú Nhuận, TP.HCM', 'crv.dvkh@vn.centralretail.com', b'0'),
('SUP003', 'TousLesJours', '02838272772', '180 Hai Bà Trưng, Quận 1, TP. HCM', 'touslesjours@gmail.com', b'0');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`ACCOUNT_ID`),
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
-- Indexes for table `decentralization_detail`
--
ALTER TABLE `decentralization_detail`
  ADD PRIMARY KEY (`DECENTRALIZATION_ID`,`MODULE_ID`),
  ADD KEY `FK_DECENT` (`DECENTRALIZATION_ID`),
  ADD KEY `FK_MODULE` (`MODULE_ID`);

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
-- Indexes for table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`MODULE_ID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`PRODUCT_ID`),
  ADD KEY `FK_CATEGORY` (`CATEGORY_ID`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`RECEIPT_ID`),
  ADD KEY `FK_STAF` (`STAFF_ID`),
  ADD KEY `FK_SUP` (`SUPPLIER_ID`);

--
-- Indexes for table `receipt_details`
--
ALTER TABLE `receipt_details`
  ADD PRIMARY KEY (`RECEIPT_ID`,`INGREDIENT_ID`),
  ADD KEY `FK_INGRED` (`INGREDIENT_ID`);

--
-- Indexes for table `recipe`
--
ALTER TABLE `recipe`
  ADD PRIMARY KEY (`RECIPE_ID`),
  ADD KEY `FK_INGREDIENT` (`INGREDIENT_ID`),
  ADD KEY `FK_PRO` (`PRODUCT_ID`);

--
-- Indexes for table `staff`
--
ALTER TABLE `staff`
  ADD PRIMARY KEY (`STAFF_ID`);

--
-- Indexes for table `statistic`
--
ALTER TABLE `statistic`
  ADD PRIMARY KEY (`STATISTIC_ID`);

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
-- Constraints for table `decentralization_detail`
--
ALTER TABLE `decentralization_detail`
  ADD CONSTRAINT `FK_DECENT` FOREIGN KEY (`DECENTRALIZATION_ID`) REFERENCES `decentralization` (`DECENTRALIZATION_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `PK_MODULE` FOREIGN KEY (`MODULE_ID`) REFERENCES `module` (`MODULE_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `discount_details`
--
ALTER TABLE `discount_details`
  ADD CONSTRAINT `FK_DISCOUNT` FOREIGN KEY (`DISCOUNT_ID`) REFERENCES `discount` (`DISCOUNT_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
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
-- Constraints for table `receipt`
--
ALTER TABLE `receipt`
  ADD CONSTRAINT `FK_STAF` FOREIGN KEY (`STAFF_ID`) REFERENCES `staff` (`STAFF_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `receipt_details`
--
ALTER TABLE `receipt_details`
  ADD CONSTRAINT `FK_INGRED` FOREIGN KEY (`INGREDIENT_ID`) REFERENCES `ingredient` (`INGREDIENT_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_RECEIPT` FOREIGN KEY (`RECEIPT_ID`) REFERENCES `receipt` (`RECEIPT_ID`) ON UPDATE CASCADE;

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
