-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 14, 2023 at 07:31 AM
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
  `ACCOUNT_ID` varchar(10) NOT NULL,
  `USERNAME` varchar(20) NOT NULL,
  `PASSWD` varchar(20) DEFAULT NULL,
  `DECENTRALIZATION_ID` varchar(10) DEFAULT NULL,
  `STAFF_ID` varchar(10) DEFAULT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `account`
--

INSERT INTO `account` (`ACCOUNT_ID`, `USERNAME`, `PASSWD`, `DECENTRALIZATION_ID`, `STAFF_ID`, `DELETED`) VALUES
('AC001', 'dungboi', '123', 'DE01', 'ST01', b'0'),
('AC010', 'zidan', '123', 'DE01', 'ST04', b'0'),
('AC002', 'legiang', '123', 'DE04', 'ST08', b'0'),
('AC003', 'longbott', '123', 'DE01', 'ST03', b'0'),
('AC004', 'quangduy', '123', 'DE01', 'ST02', b'0'),
('AC005', 'tienmanh', '123', 'DE03', 'ST06', b'0'),
('AC006', 'vanlam', '123', 'DE05', 'ST07', b'0'),
('AC007', 'xuanmai', '123', 'DE06', 'ST05', b'0'),
('AC008', 'xuanphuc', '123', 'DE02', 'ST09', b'0');

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

--
-- Dumping data for table `bill`
--

INSERT INTO `bill` (`BILL_ID`, `CUSTOMER_ID`, `STAFF_ID`, `DOPURCHASE`, `TOTAL`, `DELETED`) VALUES
('BI0001', 'CUS001', 'ST05', '2020-09-08', 239000, b'0'),
('BI0002', 'CUS002', 'ST05', '2021-02-07', 195000, b'0'),
('BI0003', 'CUS003', 'ST05', '2021-05-06', 110000, b'0'),
('BI0004', 'CUS004', 'ST05', '2021-08-03', 175000, b'0'),
('BI0005', 'CUS005', 'ST05', '2022-03-19', 173000, b'0'),
('BI0006', 'CUS006', 'ST05', '2022-03-28', 109000, b'0'),
('BI0007', 'CUS007', 'ST05', '2022-05-05', 135000, b'0'),
('BI0008', 'CUS008', 'ST05', '2022-09-08', 90000, b'0'),
('BI0009', 'CUS009', 'ST05', '2023-01-09', 277000, b'0'),
('BI0010', 'CUS010', 'ST05', '2023-03-07', 207000, b'0');

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
-- Dumping data for table `bill_details`
--

INSERT INTO `bill_details` (`BILL_ID`, `PRODUCT_ID`, `QUANTITY`) VALUES
('BI0001', 'PR001', 3),
('BI0001', 'PR004', 2),
('BI0001', 'PR009', 1),
('BI0001', 'PR011', 1),
('BI0002', 'PR023', 2),
('BI0002', 'PR035', 1),
('BI0003', 'PR046', 2),
('BI0004', 'PR025', 2),
('BI0004', 'PR050', 1),
('BI0005', 'PR001', 1),
('BI0005', 'PR010', 2),
('BI0005', 'PR060', 1),
('BI0006', 'PR019', 1),
('BI0006', 'PR024', 2),
('BI0007', 'PR027', 3),
('BI0008', 'PR018', 1),
('BI0008', 'PR038', 1),
('BI0009', 'PR027', 2),
('BI0009', 'PR051', 1),
('BI0009', 'PR063', 2),
('BI0010', 'PR045', 3);

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
('CA01', 'CÀ PHÊ PHIN', 9, b'0'),
('CA02', 'PHINDI', 9, b'0'),
('CA03', 'BÁNH MÌ QUE', 2, b'0'),
('CA04', 'TRÀ', 15, b'0'),
('CA05', 'BÁNH', 7, b'0'),
('CA06', 'FREERE', 15, b'0'),
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`CUSTOMER_ID`, `NAME`, `GENDER`, `DOB`, `PHONE`, `MEMBERSHIP`, `DOSUP`, `DELETED`) VALUES
('CUS001', 'NGUYỄN VĂN NAM ', b'1', '2000-12-01', '0862994282', b'0', '2020-09-08', b'0'),
('CUS002', 'HOÀNG XUÂN BẮC', b'1', '2001-09-03', '096756326', b'1', '2021-02-07', b'0'),
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

  `IS_SALE` int(1) DEFAULT NULL,
  `IS_PRODUCT` int(1) DEFAULT NULL,
  `IS_CATEGORY` int(1) DEFAULT NULL,
  `IS_RECIPE` int(1) DEFAULT NULL,
  `IS_IMPORT` int(1) DEFAULT NULL,
  `IS_BILL` int(1) DEFAULT NULL,
  `IS_WAREHOUSES` int(1) DEFAULT NULL,
  `IS_ACCOUNT` int(1) DEFAULT NULL,
  `IS_STAFF` int(1) DEFAULT NULL,
  `IS_CUSTOMER` int(1) DEFAULT NULL,
  `IS_DISCOUNT` int(1) DEFAULT NULL,
  `IS_DECENTRALIZE` int(1) DEFAULT NULL,

  `DECENTRALIZATION_NAME` varchar(20) NOT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `decentralization`
--

INSERT INTO `decentralization` (`DECENTRALIZATION_ID`, `IS_SALE`, `IS_PRODUCT`, `IS_CATEGORY`, `IS_RECIPE`, `IS_IMPORT`, `IS_BILL`, `IS_WAREHOUSES`, `IS_ACCOUNT`, `IS_STAFF`, `IS_CUSTOMER`, `IS_DISCOUNT`, `IS_DECENTRALIZE`, `DECENTRALIZATION_NAME`) VALUES
('DE01', 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 'manager'),
('DE02', 1, 2, 2, 0, 0, 2, 0, 0, 0, 2, 0, 0, 'staffSale'),
('DE03', 1, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0, 0, 'staffWarehousing'),
('DE04', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 'staffService'),
('DE05', 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 'bartender'),
('DE06', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 'staffdiscount');

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
  `PRODUCT_ID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `discount_details`
--

INSERT INTO `discount_details` (`DISCOUNT_ID`, `PRODUCT_ID`) VALUES
('DIS001', 'PR003'),
('DIS001', 'PR006'),
('DIS001', 'PR009'),
('DIS001', 'PR012'),
('DIS001', 'PR015'),
('DIS001', 'PR018'),
('DIS002', 'PR021'),
('DIS002', 'PR024'),
('DIS002', 'PR027'),
('DIS002', 'PR030'),
('DIS002', 'PR033'),
('DIS002', 'PR036'),
('DIS002', 'PR039'),
('DIS002', 'PR042'),
('DIS003', 'PR045'),
('DIS003', 'PR048'),
('DIS003', 'PR051'),
('DIS003', 'PR054'),
('DIS003', 'PR057'),
('DIS004', 'PR061'),
('DIS004', 'PR063'),
('DIS004', 'PR065');

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
('ING001', 'BỘT CAFE NGUYÊN CHẤT', 10.840000000000002, 'kg', 'SUP001', b'0'),
('ING002', 'SỮA TƯƠI KHÔNG ĐƯỜNG', 10.94, 'l', 'SUP001', b'0'),
('ING003', 'SỮA ĐẶC', 10.96, 'l', 'SUP001', b'0'),
('ING004', 'ĐÁ', 5.700000000000001, 'kg', 'SUP001', b'0'),
('ING005', 'NƯỚC', 999.3100000000001, 'l', 'SUP001', b'0'),
('ING006', 'MUỐI', 5.996, 'kg', 'SUP001', b'0'),
('ING007', 'ĐƯỜNG CÁT', 5.9, 'kg', 'SUP001', b'0'),
('ING008', 'SYRUP HẠNH NHÂN', 5.98, 'l', 'SUP001', b'0'),
('ING009', 'SYRUP ĐƯỜNG', 5.98, 'l', 'SUP001', b'0'),
('ING010', 'SỮA TƯƠI MILKLAB', 5.85, 'l', 'SUP001', b'0'),
('ING011', 'CÀ PHÊ TRUYỀN THỐNG SHIN', 10.95, 'kg', 'SUP001', b'0'),
('ING012', 'THẠCH CAFE', 10.95, 'kg', 'SUP001', b'0'),
('ING013', 'SYRUP ĐƯỜNG ĐEN HÀN QUỐC', 6, 'l', 'SUP001', b'0'),
('ING014', 'KEM SỮA', 6, 'l', 'SUP001', b'0'),
('ING015', 'SỐT CHOCOLA', 6, 'l', 'SUP001', b'0'),
('ING016', 'PATE', 2.985, 'kg', 'SUP002', b'0'),
('ING017', 'TƯƠNG ỚT', 5.99, 'l', 'SUP002', b'0'),
('ING018', 'CHÀ BÔNG', 2.97, 'kg', 'SUP002', b'0'),
('ING019', 'CỦ CẢI MUỐI', 5.97, 'kg', 'SUP002', b'0'),
('ING020', 'BÁNH MÌ QUE', 5.9, 'bag', 'SUP003', b'0'),
('ING021', 'SỐT GÀ PHÔ MAI', 3, 'kg', 'SUP002', b'0'),
('ING022', 'TRÀ Ô LONG', 11, 'kg', 'SUP001', b'0'),
('ING023', 'HẠT SEN', 4, 'kg', 'SUP001', b'0'),
('ING024', 'MILK FOAM', 6, 'l', 'SUP001', b'0'),
('ING025', 'THẠCH CỦ NĂNG', 6, 'kg', 'SUP001', b'0'),
('ING026', 'TRÀ ĐÀO', 10.92, 'kg', 'SUP001', b'0'),
('ING027', 'SỮA RICH', 5.94, 'l', 'SUP001', b'0'),
('ING028', 'THẠCH ĐÀO', 5.96, 'kg', 'SUP001', b'0'),
('ING029', 'ĐÀO', 5.65, 'kg', 'SUP001', b'0'),
('ING030', 'SẢ', 5.8, 'kg', 'SUP001', b'0'),
('ING031', 'SYRUP ĐÀO', 5.95, 'l', 'SUP001', b'0'),
('ING032', 'TRÀ ĐEN', 10.925, 'kg', 'SUP001', b'0'),
('ING033', 'SYRUP VẢI NGÂM', 6, 'l', 'SUP001', b'0'),
('ING034', 'THẠCH VẢI', 6, 'kg', 'SUP001', b'0'),
('ING035', 'VẢI', 6, 'kg', 'SUP001', b'0'),
('ING036', 'BỘT TRÀ XANH', 3, 'kg', 'SUP001', b'0'),
('ING037', 'KEM BÉO RICH', 6, 'l', 'SUP001', b'0'),
('ING038', 'ĐẬU ĐỎ', 6, 'kg', 'SUP001', b'0'),
('ING039', 'BÁNH MÌ SANDWICH', 2, 'kg', 'SUP001', b'0'),
('ING040', 'BỘT MÌ', 0.5, 'kg', 'SUP001', b'0'),
('ING041', 'BỘT BẮP', 0.5, 'kg', 'SUP001', b'0'),
('ING042', 'BƠ', 0.5, 'kg', 'SUP001', b'0'),
('ING043', 'CHUỐI SỨ', 12, 'quả', 'SUP001', b'0'),
('ING044', 'CHANH DÂY', 20, 'quả', 'SUP001', b'0'),
('ING045', 'BÁNH OREO', 2, 'kg', 'SUP001', b'0'),
('ING046', 'BỘT GELATIN', 0.5, 'kg', 'SUP001', b'0'),
('ING047', 'CREAM CHEESE', 2, 'kg', 'SUP001', b'0'),
('ING048', 'WHIPPING CREAM', 3, 'l', 'SUP001', b'0'),
('ING049', 'BỘT BÁNH', 6, 'kg', 'SUP001', b'0'),
('ING050', 'TRỨNG GÀ', 12, 'quả', 'SUP001', b'0'),
('ING051', 'DẦU ĂN', 2, 'l', 'SUP001', b'0'),
('ING052', 'BỘT CACAO', 0.5, 'kg', 'SUP001', b'0'),
('ING053', 'CHOCOLATE ĐEN', 1, 'kg', 'SUP001', b'0'),
('ING054', 'MASCAPONE CHEESE', 5, 'kg', 'SUP001', b'0'),
('ING055', 'RƯỢU AMARETTO', 2, 'l', 'SUP001', b'0'),
('ING056', 'RƯỢU KAHLUA', 2, 'l', 'SUP001', b'0'),
('ING057', 'TINH CHẤT VANI', 1, 'l', 'SUP001', b'0'),
('ING058', 'SỮA CHUA KHÔNG ĐƯỜNG', 3, 'kg	', 'SUP001', b'0'),
('ING059', 'BỘT QUẾ', 1, 'kg	', 'SUP001', b'0'),
('ING060', 'SYRUP BẠC HÀ', 2, 'l', 'SUP001', b'0'),
('ING061', 'BỘT DÂU', 3, 'kg', 'SUP001', b'0'),
('ING062', 'SYRUP DÂU', 6, 'l', 'SUP001', b'0');
-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `PRODUCT_ID` varchar(10) NOT NULL,
  `NAME` varchar(100) DEFAULT NULL,
  `CATEGORY_ID` varchar(10) NOT NULL,
  `SIZED` varchar(4) DEFAULT "NULL",
  `COST` double DEFAULT NULL,
  `IMAGE` mediumtext NOT NULL,
  `DELETED` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`PRODUCT_ID`, `NAME`, `CATEGORY_ID`, `SIZED`, `COST`, `IMAGE`, `DELETED`) VALUES
('PR001', 'PHIN SỮA ĐÁ', 'CA01', "S", 29000, 'img/PR001.jpg', b'0'),
('PR002', 'PHIN SỮA ĐÁ', 'CA01', "M", 39000, 'img/PR002.jpg', b'0'),
('PR003', 'PHIN SỮA ĐÁ', 'CA01', "L", 45000, 'img/PR003.jpg', b'0'),
('PR004', 'PHIN ĐEN ĐÁ', 'CA01', "S", 29000, 'img/PR004.jpg', b'0'),
('PR005', 'PHIN ĐEN ĐÁ', 'CA01', "M", 35000, 'img/PR005.jpg', b'0'),
('PR006', 'PHIN ĐEN ĐÁ', 'CA01', "L", 39000, 'img/PR006.jpg', b'0'),
('PR007', 'BẠC XỈU', 'CA01', "S", 29000, 'img/PR007.jpg', b'0'),
('PR008', 'BẠC XỈU', 'CA01', "M", 39000, 'img/PR008.jpg', b'0'),
('PR009', 'BẠC XỈU', 'CA01', "L", 45000, 'img/PR009.jpg', b'0'),
('PR010', 'PHINDI HẠNH NHÂN', 'CA02', "S", 45000, 'img/PR010.jpg', b'0'),
('PR011', 'PHINDI HẠNH NHÂN', 'CA02', "M", 49000, 'img/PR011.jpg', b'0'),
('PR012', 'PHINDI HẠNH NHÂN', 'CA02', "L", 55000, 'img/PR012.jpg', b'0'),
('PR013', 'PHINDI KEM SỮA', 'CA02', "S", 45000, 'img/PR013.jpg', b'0'),
('PR014', 'PHINDI KEM SỮA', 'CA02', "M", 49000, 'img/PR014.jpg', b'0'),
('PR015', 'PHINDI KEM SỮA', 'CA02', "L", 55000, 'img/PR015.jpg', b'0'),
('PR016', 'PHINDI CHOCO', 'CA02', "S", 45000, 'img/PR016.jpg', b'0'),
('PR017', 'PHINDI CHOCO', 'CA02', "M", 49000, 'img/PR017.jpg', b'0'),
('PR018', 'PHINDI CHOCO', 'CA02', "L", 55000, 'img/PR018.jpg', b'0'),
('PR019', 'BÁNH MÌ PATE', 'CA03', "null", 19000, 'img/PR019.jpg', b'0'),
('PR020', 'BÁNH MÌ GÀ PHÔ MAI', 'CA03', "null", 19000, 'img/PR020.jpg', b'0'),
('PR021', 'TRÀ SEN VÀNG', 'CA04', "S", 45000, 'img/PR021.jpg', b'0'),
('PR022', 'TRÀ SEN VÀNG', 'CA04', "M", 55000, 'img/PR022.jpg', b'0'),
('PR023', 'TRÀ SEN VÀNG', 'CA04', "L", 65000, 'img/PR023.jpg', b'0'),
('PR024', 'TRÀ THẠCH ĐÀO', 'CA04', "S", 45000, 'img/PR024.jpg', b'0'),
('PR025', 'TRÀ THẠCH ĐÀO', 'CA04', "M", 55000, 'img/PR025.jpg', b'0'),
('PR026', 'TRÀ THẠCH ĐÀO', 'CA04', "L", 65000, 'img/PR026.jpg', b'0'),
('PR027', 'TRÀ THANH ĐÀO', 'CA04', "S", 45000, 'img/PR027.jpg', b'0'),
('PR028', 'TRÀ THANH ĐÀO', 'CA04', "M", 55000, 'img/PR028.jpg', b'0'),
('PR029', 'TRÀ THANH ĐÀO', 'CA04', "L", 65000, 'img/PR029.jpg', b'0'),
('PR030', 'TRÀ THẠCH VẢI', 'CA04', "S", 45000, 'img/PR030.jpg', b'0'),
('PR031', 'TRÀ THẠCH VẢI', 'CA04', "M", 55000, 'img/PR031.jpg', b'0'),
('PR032', 'TRÀ THẠCH VẢI', 'CA04', "L", 65000, 'img/PR032.jpg', b'0'),
('PR033', 'TRÀ XANH ĐẬU ĐỎ', 'CA04', "S", 45000, 'img/PR033.jpg', b'0'),
('PR034', 'TRÀ XANH ĐẬU ĐỎ', 'CA04', "M", 55000, 'img/PR034.jpg', b'0'),
('PR035', 'TRÀ XANH ĐẬU ĐỎ', 'CA04', "L", 65000, 'img/PR035.jpg', b'0'),
('PR036', 'BÁNH CHUỐI', 'CA05', "null", 29000, 'img/PR036.jpg', b'0'),
('PR037', 'PHÔ MAI CHANH DÂY', 'CA05', "null", 29000, 'img/PR037.jpg', b'0'),
('PR038', 'TIRAMISU', 'CA05', "null", 35000, 'img/PR038.jpg', b'0'),
('PR039', 'MOUSSE ĐÀO', 'CA05', "null", 35000, 'img/PR039.jpg', b'0'),
('PR040', 'PHÔ MAI TRÀ XANH', 'CA05', "null", 35000, 'img/PR040.jpg', b'0'),
('PR041', 'PHÔ MAI CARAMEL', 'CA05', "null", 35000, 'img/PR041.jpg', b'0'),
('PR042', 'PHÔ MAI CACAO', 'CA05', "null", 35000, 'img/PR042.jpg', b'0'),
('PR043', 'FREEZE TRÀ XANH', 'CA06', "S", 55000, 'img/PR043.jpg', b'0'),
('PR044', 'FREEZE TRÀ XANH', 'CA06', "M", 65000, 'img/PR044.jpg', b'0'),
('PR045', 'FREEZE TRÀ XANH', 'CA06', "L", 69000, 'img/PR045.jpg', b'0'),
('PR046', 'CARAMEL PHIN FREEZE', 'CA06', "S", 55000, 'img/PR046.jpg', b'0'),
('PR047', 'CARAMEL PHIN FREEZE', 'CA06', "M", 65000, 'img/PR047.jpg', b'0'),
('PR048', 'CARAMEL PHIN FREEZE', 'CA06', "L", 69000, 'img/PR048.jpg', b'0'),
('PR049', 'COOKIES & CREAM', 'CA06', "S", 55000, 'img/PR049.jpg', b'0'),
('PR050', 'COOKIES & CREAM', 'CA06', "M", 65000, 'img/PR050.jpg', b'0'),
('PR051', 'COOKIES & CREAM', 'CA06', "L", 69000, 'img/PR051.jpg', b'0'),
('PR052', 'FREEZE SÔ-CÔ-LA', 'CA06', "S", 55000, 'img/PR052.jpg', b'0'),
('PR053', 'FREEZE SÔ-CÔ-LA', 'CA06', "M", 65000, 'img/PR053.jpg', b'0'),
('PR054', 'FREEZE SÔ-CÔ-LA', 'CA06', "L", 69000, 'img/PR054.jpg', b'0'),
('PR055', 'CLASSIC PHIN FREEZE', 'CA06', "S", 55000, 'img/PR055.jpg', b'0'),
('PR056', 'CLASSIC PHIN FREEZE', 'CA06', "M", 65000, 'img/PR056.jpg', b'0'),
('PR057', 'CLASSIC PHIN FREEZE', 'CA06', "L", 69000, 'img/PR057.jpg', b'0'),
('PR058', 'TRÀ SỮA BẠC HÀ', 'CA07', "S", 54000, 'img/PR058.jpg', b'0'),
('PR059', 'TRÀ SỮA BẠC HÀ', 'CA07', "M", 59000, 'img/PR059.jpg', b'0'),
('PR060', 'TRÀ SỮA TRÀ XANH', 'CA07', "S", 54000, 'img/PR060.jpg', b'0'),
('PR061', 'TRÀ SỮA TRÀ XANH', 'CA07', "M", 59000, 'img/PR061.jpg', b'0'),
('PR062', 'TRÀ SỮA DÂU', 'CA07', "S", 54000, 'img/PR062.jpg', b'0'),
('PR063', 'TRÀ SỮA DÂU', 'CA07', "M", 59000, 'img/PR063.jpg', b'0'),
('PR064', 'TRÀ SỮA SÔ-CÔ-LA', 'CA07', "S", 54000, 'img/PR064.jpg', b'0'),
('PR065', 'TRÀ SỮA SÔ-CÔ-LA', 'CA07', "M", 59000, 'img/PR065.jpg', b'0');

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
-- Table structure for table `receipt_details`
--

CREATE TABLE `receipt_details` (
  `RECEIPT_ID` varchar(10) NOT NULL,
  `INGREDIENT_ID` varchar(10) NOT NULL,
  `QUANTITY` double DEFAULT 0,
  `SUPPLIER_ID` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `receipt_details`
--

INSERT INTO `receipt_details` (`RECEIPT_ID`, `INGREDIENT_ID`, `QUANTITY`, `SUPPLIER_ID`) VALUES
('REC001', 'ING001', 10, 'SUP001'),
('REC002', 'ING002', 10, 'SUP001'),
('REC003', 'ING003', 10, 'SUP001'),
('REC004', 'ING004', 5, 'SUP001'),
('REC005', 'ING005', 1000, 'SUP001'),
('REC006', 'ING006', 5, 'SUP001'),
('REC007', 'ING007', 5, 'SUP001'),
('REC008', 'ING008', 5, 'SUP001'),
('REC009', 'ING009', 5, 'SUP001'),
('REC010', 'ING010', 5, 'SUP001'),
('REC011', 'ING011', 10, 'SUP001'),
('REC012', 'ING012', 10, 'SUP001'),
('REC013', 'ING013', 5, 'SUP001'),
('REC014', 'ING014', 5, 'SUP001'),
('REC015', 'ING015', 5, 'SUP001'),
('REC016', 'ING016', 2, 'SUP002'),
('REC017', 'ING017', 5, 'SUP002'),
('REC018', 'ING018', 2, 'SUP002'),
('REC019', 'ING019', 5, 'SUP002'),
('REC020', 'ING020', 5, 'SUP003'),
('REC021', 'ING021', 2, 'SUP002'),
('REC022', 'ING022', 10, 'SUP001'),
('REC023', 'ING023', 3, 'SUP001'),
('REC024', 'ING024', 5, 'SUP001'),
('REC025', 'ING025', 5, 'SUP001'),
('REC026', 'ING026', 10, 'SUP001'),
('REC027', 'ING027', 5, 'SUP001'),
('REC028', 'ING028', 5, 'SUP001'),
('REC029', 'ING029', 5, 'SUP001'),
('REC030', 'ING030', 5, 'SUP001'),
('REC031', 'ING031', 5, 'SUP001'),
('REC032', 'ING032', 10, 'SUP001'),
('REC033', 'ING033', 5, 'SUP001'),
('REC034', 'ING034', 5, 'SUP001'),
('REC035', 'ING035', 5, 'SUP001'),
('REC036', 'ING036', 2, 'SUP001'),
('REC037', 'ING037', 5, 'SUP001'),
('REC038', 'ING038', 5, 'SUP001');

--
-- Triggers `receipt_details`
--
DELIMITER $$
CREATE TRIGGER `InserReceipt_Details` AFTER INSERT ON `receipt_details` FOR EACH ROW BEGIN
UPDATE ingredient
SET ingredient.QUANTITY = ingredient.QUANTITY + NEW.QUANTITY
WHERE ingredient.INGREDIENT_ID = NEW.INGREDIENT_ID;
UPDATE receipt
SET receipt.GRAND_TOTAL = receipt.GRAND_TOTAL + (SELECT supplier.PRICE FROM supplier WHERE supplier.SUPPLIER_ID = NEW.SUPPLIER_ID) * NEW.QUANTITY
WHERE receipt.RECEIPT_ID = NEW.RECEIPT_ID;
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
  `DELETED` bit(1) NOT NULL DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`RECEIPT_ID`, `STAFF_ID`, `DOR`, `GRAND_TOTAL`, `DELETED`) VALUES
('REC001', 'ST06', '2023-03-01', 1500000, b'0'),
('REC002', 'ST06', '2023-01-01', 1500000, b'0'),
('REC003', 'ST06', '2023-02-01', 1500000, b'0'),
('REC004', 'ST06', '2023-01-01', 750000, b'0'),
('REC005', 'ST06', '2022-03-01', 150000000, b'0'),
('REC006', 'ST06', '2022-04-01', 750000, b'0'),
('REC007', 'ST06', '2022-06-01', 750000, b'0'),
('REC008', 'ST06', '2022-03-01', 750000, b'0'),
('REC009', 'ST06', '2022-03-01', 750000, b'0'),
('REC010', 'ST06', '2022-03-01', 750000, b'0'),
('REC011', 'ST06', '2022-02-01', 1500000, b'0'),
('REC012', 'ST06', '2022-02-01', 1500000, b'0'),
('REC013', 'ST06', '2022-02-01', 750000, b'0'),
('REC014', 'ST06', '2022-02-01', 750000, b'0'),
('REC015', 'ST06', '2022-03-01', 750000, b'0'),
('REC016', 'ST06', '2022-03-01', 300000, b'0'),
('REC017', 'ST06', '2021-03-01', 750000, b'0'),
('REC018', 'ST06', '2021-03-01', 300000, b'0'),
('REC019', 'ST06', '2021-07-01', 750000, b'0'),
('REC020', 'ST06', '2021-07-01', 750000, b'0'),
('REC021', 'ST06', '2021-07-01', 300000, b'0'),
('REC022', 'ST06', '2021-07-01', 1500000, b'0'),
('REC023', 'ST06', '2021-09-01', 450000, b'0'),
('REC024', 'ST06', '2021-09-01', 750000, b'0'),
('REC025', 'ST06', '2021-09-01', 750000, b'0'),
('REC026', 'ST06', '2021-09-01', 1500000, b'0'),
('REC027', 'ST06', '2021-05-01', 750000, b'0'),
('REC028', 'ST06', '2021-05-01', 750000, b'0'),
('REC029', 'ST06', '2021-05-01', 750000, b'0'),
('REC030', 'ST06', '2022-05-01', 750000, b'0'),
('REC031', 'ST06', '2022-02-01', 750000, b'0'),
('REC032', 'ST06', '2022-02-01', 1500000, b'0'),
('REC033', 'ST06', '2022-02-01', 750000, b'0'),
('REC034', 'ST06', '2022-02-01', 750000, b'0'),
('REC035', 'ST06', '2022-01-01', 750000, b'0'),
('REC036', 'ST06', '2022-01-01', 300000, b'0'),
('REC037', 'ST06', '2022-01-01', 750000, b'0'),
('REC038', 'ST06', '2022-01-01', 750000, b'0');

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `recipe`
--

INSERT INTO `recipe` (`RECIPE_ID`, `PRODUCT_ID`, `INGREDIENT_ID`, `MASS`, `UNIT`, `DELETED`) VALUES
('RE001', 'PR001', 'ING001', 0.03, 'kg', b'0'),
('RE002', 'PR001', 'ING002', 0.015, 'l', b'0'),
('RE003', 'PR001', 'ING003', 0.01, 'l', b'0'),
('RE004', 'PR001', 'ING004', 0.02, 'bag', b'0'),
('RE005', 'PR001', 'ING005', 0.13, 'l', b'0'),

('RE001', 'PR002', 'ING001', 0.04, 'kg', b'0'),
('RE002', 'PR002', 'ING002', 0.025, 'l', b'0'),
('RE003', 'PR002', 'ING003', 0.02, 'l', b'0'),
('RE004', 'PR002', 'ING004', 0.03, 'bag', b'0'),
('RE005', 'PR002', 'ING005', 0.23, 'l', b'0'),

('RE001', 'PR003', 'ING001', 0.045, 'kg', b'0'),
('RE002', 'PR003', 'ING002', 0.030, 'l', b'0'),
('RE003', 'PR003', 'ING003', 0.025, 'l', b'0'),
('RE004', 'PR003', 'ING004', 0.035, 'bag', b'0'),
('RE005', 'PR003', 'ING005', 0.28, 'l', b'0'),

('RE006', 'PR004', 'ING001', 0.02, 'kg', b'0'),
('RE007', 'PR004', 'ING004', 0.02, 'bag', b'0'),
('RE008', 'PR004', 'ING005', 0.13, 'l', b'0'),
('RE009', 'PR004', 'ING006', 0.002, 'kg', b'0'),
('RE010', 'PR004', 'ING007', 0.005, 'kg', b'0'),

('RE006', 'PR005', 'ING001', 0.027, 'kg', b'0'),
('RE007', 'PR005', 'ING004', 0.027, 'bag', b'0'),
('RE008', 'PR005', 'ING005', 0.20, 'l', b'0'),
('RE009', 'PR005', 'ING006', 0.003, 'kg', b'0'),
('RE010', 'PR005', 'ING007', 0.006, 'kg', b'0'),

('RE006', 'PR006', 'ING001', 0.031, 'kg', b'0'),
('RE007', 'PR006', 'ING004', 0.031, 'bag', b'0'),
('RE008', 'PR006', 'ING005', 0.25, 'l', b'0'),
('RE009', 'PR006', 'ING006', 0.0035, 'kg', b'0'),
('RE010', 'PR006', 'ING007', 0.0065, 'kg', b'0'),

('RE011', 'PR007', 'ING002', 0.03, 'kg', b'0'),
('RE012', 'PR007', 'ING003', 0.02, 'kg', b'0'),
('RE013', 'PR007', 'ING004', 0.02, 'bag', b'0'),
('RE014', 'PR007', 'ING005', 0.1, 'l', b'0'),
('RE015', 'PR007', 'ING008', 0.01, 'l', b'0'),

('RE011', 'PR008', 'ING002', 0.04, 'kg', b'0'),
('RE012', 'PR008', 'ING003', 0.03, 'kg', b'0'),
('RE013', 'PR008', 'ING004', 0.03, 'bag', b'0'),
('RE014', 'PR008', 'ING005', 0.2, 'l', b'0'),
('RE015', 'PR008', 'ING008', 0.02, 'l', b'0'),

('RE011', 'PR009', 'ING002', 0.045, 'kg', b'0'),
('RE012', 'PR009', 'ING003', 0.035, 'kg', b'0'),
('RE013', 'PR009', 'ING004', 0.035, 'bag', b'0'),
('RE014', 'PR009', 'ING005', 0.25, 'l', b'0'),
('RE015', 'PR009', 'ING008', 0.025, 'l', b'0'),

('RE016', 'PR010', 'ING004', 0.02, 'bag', b'0'),
('RE017', 'PR010', 'ING008', 0.01, 'l', b'0'),
('RE018', 'PR010', 'ING009', 0.01, 'l', b'0'),
('RE019', 'PR010', 'ING010', 0.075, 'l', b'0'),
('RE020', 'PR010', 'ING011', 0.025, 'l', b'0'),
('RE021', 'PR010', 'ING012', 0.025, 'kg', b'0'),

('RE016', 'PR011', 'ING004', 0.025, 'bag', b'0'),
('RE017', 'PR011', 'ING008', 0.015, 'l', b'0'),
('RE018', 'PR011', 'ING009', 0.015, 'l', b'0'),
('RE019', 'PR011', 'ING010', 0.080, 'l', b'0'),
('RE020', 'PR011', 'ING011', 0.030, 'l', b'0'),
('RE021', 'PR011', 'ING012', 0.030, 'kg', b'0'),

('RE016', 'PR012', 'ING004', 0.03, 'bag', b'0'),
('RE017', 'PR012', 'ING008', 0.02, 'l', b'0'),
('RE018', 'PR012', 'ING009', 0.02, 'l', b'0'),
('RE019', 'PR012', 'ING010', 0.085, 'l', b'0'),
('RE020', 'PR012', 'ING011', 0.035, 'l', b'0'),
('RE021', 'PR012', 'ING012', 0.035, 'kg', b'0'),

('RE022', 'PR013', 'ING004', 0.02, 'bag', b'0'),
('RE023', 'PR013', 'ING010', 0.075, 'l', b'0'),
('RE024', 'PR013', 'ING011', 0.025, 'l', b'0'),
('RE025', 'PR013', 'ING012', 0.025, 'kg', b'0'),
('RE026', 'PR013', 'ING013', 0.01, 'l', b'0'),
('RE027', 'PR013', 'ING014', 0.035, 'l', b'0'),

('RE022', 'PR014', 'ING004', 0.025, 'bag', b'0'),
('RE023', 'PR014', 'ING010', 0.080, 'l', b'0'),
('RE024', 'PR014', 'ING011', 0.030, 'l', b'0'),
('RE025', 'PR014', 'ING012', 0.030, 'kg', b'0'),
('RE026', 'PR014', 'ING013', 0.015, 'l', b'0'),
('RE027', 'PR014', 'ING014', 0.040, 'l', b'0'),

('RE022', 'PR015', 'ING004', 0.030, 'bag', b'0'),
('RE023', 'PR015', 'ING010', 0.085, 'l', b'0'),
('RE024', 'PR015', 'ING011', 0.035, 'l', b'0'),
('RE025', 'PR015', 'ING012', 0.035, 'kg', b'0'),
('RE026', 'PR015', 'ING013', 0.020, 'l', b'0'),
('RE027', 'PR015', 'ING014', 0.045, 'l', b'0'),

('RE028', 'PR016', 'ING004', 0.02, 'bag', b'0'),
('RE029', 'PR016', 'ING010', 0.075, 'l', b'0'),
('RE030', 'PR016', 'ING011', 0.025, 'l', b'0'),
('RE031', 'PR016', 'ING012', 0.25, 'kg', b'0'),
('RE032', 'PR016', 'ING013', 0.015, 'l', b'0'),
('RE033', 'PR016', 'ING015', 0.01, 'l', b'0'),

('RE028', 'PR017', 'ING004', 0.025, 'bag', b'0'),
('RE029', 'PR017', 'ING010', 0.080, 'l', b'0'),
('RE030', 'PR017', 'ING011', 0.030, 'l', b'0'),
('RE031', 'PR017', 'ING012', 0.30, 'kg', b'0'),
('RE032', 'PR017', 'ING013', 0.02, 'l', b'0'),
('RE033', 'PR017', 'ING015', 0.015, 'l', b'0'),

('RE028', 'PR018', 'ING004', 0.03, 'bag', b'0'),
('RE029', 'PR018', 'ING010', 0.085, 'l', b'0'),
('RE030', 'PR018', 'ING011', 0.035, 'l', b'0'),
('RE031', 'PR018', 'ING012', 0.35, 'kg', b'0'),
('RE032', 'PR018', 'ING013', 0.025, 'l', b'0'),
('RE033', 'PR018', 'ING015', 0.02, 'l', b'0'),

('RE034', 'PR019', 'ING016', 0.015, 'kg', b'0'),
('RE035', 'PR019', 'ING017', 0.01, 'l', b'0'),
('RE036', 'PR019', 'ING018', 0.03, 'kg', b'0'),
('RE037', 'PR019', 'ING019', 0.03, 'kg', b'0'),
('RE038', 'PR019', 'ING020', 0.1, 'bag', b'0'),

('RE039', 'PR020', 'ING017', 0.01, 'l', b'0'),
('RE040', 'PR020', 'ING018', 0.03, 'kg', b'0'),
('RE041', 'PR020', 'ING020', 0.1, 'bag', b'0'),
('RE042', 'PR020', 'ING021', 0.01, 'kg', b'0'),

('RE043', 'PR021', 'ING004', 0.02, 'bag', b'0'),
('RE044', 'PR021', 'ING005', 0.13, 'l', b'0'),
('RE045', 'PR021', 'ING022', 0.002, 'kg', b'0'),
('RE046', 'PR021', 'ING023', 0.02, 'kg', b'0'),
('RE047', 'PR021', 'ING024', 0.05, 'l', b'0'),
('RE048', 'PR021', 'ING025', 0.015, 'kg', b'0'),

('RE043', 'PR022', 'ING004', 0.028, 'bag', b'0'),
('RE044', 'PR022', 'ING005', 0.21, 'l', b'0'),
('RE045', 'PR022', 'ING022', 0.0028, 'kg', b'0'),
('RE046', 'PR022', 'ING023', 0.028, 'kg', b'0'),
('RE047', 'PR022', 'ING024', 0.064, 'l', b'0'),
('RE048', 'PR022', 'ING025', 0.023, 'kg', b'0'),

('RE043', 'PR023', 'ING004', 0.034, 'bag', b'0'),
('RE044', 'PR023', 'ING005', 0.27, 'l', b'0'),
('RE045', 'PR023', 'ING022', 0.0035, 'kg', b'0'),
('RE046', 'PR023', 'ING023', 0.031, 'kg', b'0'),
('RE047', 'PR023', 'ING024', 0.069, 'l', b'0'),
('RE048', 'PR023', 'ING025', 0.028, 'kg', b'0'),

('RE049', 'PR024', 'ING004', 0.02, 'bag', b'0'),
('RE050', 'PR024', 'ING005', 0.13, 'l', b'0'),
('RE051', 'PR024', 'ING007', 0.02, 'l', b'0'),
('RE052', 'PR024', 'ING026', 0.04, 'kg', b'0'),
('RE053', 'PR024', 'ING027', 0.03, 'kg', b'0'),
('RE054', 'PR024', 'ING028', 0.02, 'kg', b'0'),
('RE055', 'PR024', 'ING029', 0.05, 'kg', b'0'),

('RE049', 'PR025', 'ING004', 0.028, 'bag', b'0'),
('RE050', 'PR025', 'ING005', 0.20, 'l', b'0'),
('RE051', 'PR025', 'ING007', 0.027, 'l', b'0'),
('RE052', 'PR025', 'ING026', 0.045, 'kg', b'0'),
('RE053', 'PR025', 'ING027', 0.035, 'kg', b'0'),
('RE054', 'PR025', 'ING028', 0.03, 'kg', b'0'),
('RE055', 'PR025', 'ING029', 0.054, 'kg', b'0'),

('RE049', 'PR026', 'ING004', 0.035, 'bag', b'0'),
('RE050', 'PR026', 'ING005', 0.25, 'l', b'0'),
('RE051', 'PR026', 'ING007', 0.032, 'l', b'0'),
('RE052', 'PR026', 'ING026', 0.050, 'kg', b'0'),
('RE053', 'PR026', 'ING027', 0.042, 'kg', b'0'),
('RE054', 'PR026', 'ING028', 0.037, 'kg', b'0'),
('RE055', 'PR026', 'ING029', 0.053, 'kg', b'0'),

('RE056', 'PR027', 'ING004', 0.02, 'bag', b'0'),
('RE057', 'PR027', 'ING005', 0.13, 'l', b'0'),
('RE058', 'PR027', 'ING007', 0.01, 'kg', b'0'),
('RE059', 'PR027', 'ING029', 0.05, 'kg', b'0'),
('RE060', 'PR027', 'ING030', 0.04, 'kg', b'0'),
('RE061', 'PR027', 'ING031', 0.01, 'kg', b'0'),
('RE062', 'PR027', 'ING032', 0.015, 'l', b'0'),

('RE056', 'PR028', 'ING004', 0.025, 'bag', b'0'),
('RE057', 'PR028', 'ING005', 0.19, 'l', b'0'),
('RE058', 'PR028', 'ING007', 0.014, 'kg', b'0'),
('RE059', 'PR028', 'ING029', 0.053, 'kg', b'0'),
('RE060', 'PR028', 'ING030', 0.045, 'kg', b'0'),
('RE061', 'PR028', 'ING031', 0.014, 'kg', b'0'),
('RE062', 'PR028', 'ING032', 0.02, 'l', b'0'),

('RE056', 'PR029', 'ING004', 0.03, 'bag', b'0'),
('RE057', 'PR029', 'ING005', 0.25, 'l', b'0'),
('RE058', 'PR029', 'ING007', 0.02, 'kg', b'0'),
('RE059', 'PR029', 'ING029', 0.06, 'kg', b'0'),
('RE060', 'PR029', 'ING030', 0.052, 'kg', b'0'),
('RE061', 'PR029', 'ING031', 0.021, 'kg', b'0'),
('RE062', 'PR029', 'ING032', 0.028, 'l', b'0'),

('RE063', 'PR030', 'ING004', 0.02, 'bag', b'0'),
('RE064', 'PR030', 'ING005', 0.12, 'l', b'0'),
('RE065', 'PR030', 'ING007', 0.02, 'l', b'0'),
('RE066', 'PR030', 'ING032', 0.002, 'kg', b'0'),
('RE067', 'PR030', 'ING033', 0.015, 'kg', b'0'),
('RE068', 'PR030', 'ING034', 0.05, 'kg', b'0'),
('RE069', 'PR030', 'ING035', 0.05, 'kg', b'0'),

('RE063', 'PR031', 'ING004', 0.025, 'bag', b'0'),
('RE064', 'PR031', 'ING005', 0.128, 'l', b'0'),
('RE065', 'PR031', 'ING007', 0.024, 'l', b'0'),
('RE066', 'PR031', 'ING032', 0.0028, 'kg', b'0'),
('RE067', 'PR031', 'ING033', 0.0154, 'kg', b'0'),
('RE068', 'PR031', 'ING034', 0.053, 'kg', b'0'),
('RE069', 'PR031', 'ING035', 0.054, 'kg', b'0'),

('RE063', 'PR032', 'ING004', 0.030, 'bag', b'0'),
('RE064', 'PR032', 'ING005', 0.135, 'l', b'0'),
('RE065', 'PR032', 'ING007', 0.03, 'l', b'0'),
('RE066', 'PR032', 'ING032', 0.0035, 'kg', b'0'),
('RE067', 'PR032', 'ING033', 0.016, 'kg', b'0'),
('RE068', 'PR032', 'ING034', 0.058, 'kg', b'0'),
('RE069', 'PR032', 'ING035', 0.057, 'kg', b'0'),

('RE070', 'PR033', 'ING002', 0.025, 'l', b'0'),
('RE071', 'PR033', 'ING003', 0.02, 'l', b'0'),
('RE072', 'PR033', 'ING004', 0.02, 'bag', b'0'),
('RE073', 'PR033', 'ING005', 0.12, 'l', b'0'),
('RE074', 'PR033', 'ING036', 0.002, 'kg', b'0'),
('RE075', 'PR033', 'ING037', 0.005, 'l', b'0'),
('RE076', 'PR033', 'ING038', 0.015, 'kg', b'0'),

('RE070', 'PR034', 'ING002', 0.03, 'l', b'0'),
('RE071', 'PR034', 'ING003', 0.025, 'l', b'0'),
('RE072', 'PR034', 'ING004', 0.025, 'bag', b'0'),
('RE073', 'PR034', 'ING005', 0.125, 'l', b'0'),
('RE074', 'PR034', 'ING036', 0.0025, 'kg', b'0'),
('RE075', 'PR034', 'ING037', 0.0055, 'l', b'0'),
('RE076', 'PR034', 'ING038', 0.0155, 'kg', b'0'),

('RE070', 'PR035', 'ING002', 0.033, 'l', b'0'),
('RE071', 'PR035', 'ING003', 0.0258, 'l', b'0'),
('RE072', 'PR035', 'ING004', 0.0258, 'bag', b'0'),
('RE073', 'PR035', 'ING005', 0.1254, 'l', b'0'),
('RE074', 'PR035', 'ING036', 0.00253, 'kg', b'0'),
('RE075', 'PR035', 'ING037', 0.00552, 'l', b'0'),
('RE076', 'PR035', 'ING038', 0.01555, 'kg', b'0'),

('RE077', 'PR036', 'ING043', 3, 'quả', b'0'),
('RE078', 'PR036', 'ING002', 0.090, 'l', b'0'),
('RE079', 'PR036', 'ING039', 0.230, 'kg', b'0'),
('RE080', 'PR036', 'ING007', 0.007, 'kg', b'0'),
('RE081', 'PR036', 'ING040', 0.020, 'kg', b'0'),
('RE082', 'PR036', 'ING041', 0.003, 'kg', b'0'),
('RE083', 'PR036', 'ING042', 0.010, 'kg', b'0'),
('RE084', 'PR036', 'ING006', 0.001, 'kg', b'0'),

('RE077', 'PR037', 'ING044', 4, 'quả', b'0'),
('RE078', 'PR037', 'ING045', 0.1, 'kg', b'0'),
('RE079', 'PR037', 'ING046', 0.030, 'kg', b'0'),
('RE080', 'PR037', 'ING042', 0.025, 'kg', b'0'),
('RE081', 'PR037', 'ING047', 0.100, 'kg', b'0'),
('RE082', 'PR037', 'ING048', 0.200, 'l', b'0'),
('RE084', 'PR037', 'ING007', 0.050, 'kg', b'0'),

('RE082', 'PR038', 'ING049', 0.150, 'kg', b'0'),
('RE082', 'PR038', 'ING050', 5, 'quả', b'0'),
('RE082', 'PR038', 'ING051', 0.024, 'l', b'0'),
('RE082', 'PR038', 'ING052', 0.010, 'kg', b'0'),
('RE082', 'PR038', 'ING053', 0.020, 'kg', b'0'),
('RE082', 'PR038', 'ING054', 0.400, 'kg', b'0'),
('RE082', 'PR038', 'ING055', 0.025, 'l', b'0'),
('RE082', 'PR038', 'ING041', 0.010, 'kg', b'0'),
('RE082', 'PR038', 'ING007', 0.260, 'kg', b'0'),
('RE082', 'PR038', 'ING002', 0.040, 'l', b'0'),
('RE082', 'PR038', 'ING001', 0.200, 'kg', b'0'),
('RE082', 'PR038', 'ING048', 0.500, 'l', b'0'),
('RE082', 'PR038', 'ING046', 0.010, 'l', b'0'),

('RE082', 'PR039', 'ING029', 0.350, 'kg', b'0'),
('RE082', 'PR039', 'ING031', 0.200, 'l', b'0'),
('RE082', 'PR039', 'ING057', 0.010, 'l', b'0'),
('RE082', 'PR039', 'ING046', 0.020, 'kg', b'0'),
('RE082', 'PR039', 'ING002', 0.200, 'l', b'0'),
('RE082', 'PR039', 'ING048', 0.200, 'kg', b'0'),
('RE082', 'PR039', 'ING007', 0.100, 'kg', b'0'),
('RE082', 'PR039', 'ING050', 2, 'quả', b'0'),

('RE082', 'PR040', 'ING046', 0.010, 'kg', b'0'),
('RE082', 'PR040', 'ING005', 0.025, 'l', b'0'),
('RE082', 'PR040', 'ING047', 0.200, 'kg', b'0'),
('RE082', 'PR040', 'ING058', 0.100, 'kg', b'0'),
('RE082', 'PR040', 'ING007', 0.080, 'kg', b'0'),
('RE082', 'PR040', 'ING048', 0.200, 'kg', b'0'),
('RE082', 'PR040', 'ING036', 0.010, 'kg', b'0'),
('RE082', 'PR040', 'ING002', 0.030, 'l', b'0'),

('RE082', 'PR041', 'ING042', 0.100, 'kg', b'0'),
('RE082', 'PR041', 'ING059', 0.050, 'kg', b'0'),
('RE082', 'PR041', 'ING047', 0.453, 'kg', b'0'),
('RE082', 'PR041', 'ING050', 2, 'quả', b'0'),
('RE082', 'PR041', 'ING003', 0.414, 'l', b'0'),
('RE082', 'PR041', 'ING007', 0.050, 'kg', b'0'),
('RE082', 'PR041', 'ING054', 0.030, 'kg', b'0'),

('RE082', 'PR042', 'ING045', 0.125, 'kg', b'0'),
('RE082', 'PR042', 'ING042', 0.030, 'kg', b'0'),
('RE082', 'PR042', 'ING048', 0.030, 'l', b'0'),
('RE082', 'PR042', 'ING001', 0.020, 'kg', b'0'),
('RE082', 'PR042', 'ING047', 0.675, 'kg', b'0'),
('RE082', 'PR042', 'ING058', 0.113, 'kg', b'0'),
('RE082', 'PR042', 'ING007', 0.220, 'kg', b'0'),
('RE082', 'PR042', 'ING050', 2, 'quả', b'0'),

('RE082', 'PR043', 'ING014', 0.600, 'l', b'0'),
('RE082', 'PR043', 'ING003', 0.080, 'l', b'0'),
('RE082', 'PR043', 'ING057', 0.040, 'l', b'0'),
('RE082', 'PR043', 'ING005', 0.200, 'l', b'0'),
('RE082', 'PR043', 'ING007', 0.050, 'kg', b'0'),
('RE082', 'PR043', 'ING036', 0.010, 'kg', b'0'),
('RE082', 'PR043', 'ING058', 0.200, 'l', b'0'),
('RE082', 'PR043', 'ING002', 0.100, 'l', b'0'),
('RE082', 'PR043', 'ING004', 0.030, 'kg', b'0'),

('RE082', 'PR044', 'ING014', 0.650, 'l', b'0'),
('RE082', 'PR044', 'ING003', 0.090, 'l', b'0'),
('RE082', 'PR044', 'ING057', 0.050, 'l', b'0'),
('RE082', 'PR044', 'ING005', 0.250, 'l', b'0'),
('RE082', 'PR044', 'ING007', 0.055, 'kg', b'0'),
('RE082', 'PR044', 'ING036', 0.015, 'kg', b'0'),
('RE082', 'PR044', 'ING058', 0.250, 'l', b'0'),
('RE082', 'PR044', 'ING002', 0.150, 'l', b'0'),
('RE082', 'PR044', 'ING004', 0.050, 'kg', b'0'),

('RE082', 'PR045', 'ING014', 0.680, 'l', b'0'),
('RE082', 'PR045', 'ING003', 0.093, 'l', b'0'),
('RE082', 'PR045', 'ING057', 0.055, 'l', b'0'),
('RE082', 'PR045', 'ING005', 0.254, 'l', b'0'),
('RE082', 'PR045', 'ING007', 0.059, 'kg', b'0'),
('RE082', 'PR045', 'ING036', 0.017, 'kg', b'0'),
('RE082', 'PR045', 'ING058', 0.256, 'l', b'0'),
('RE082', 'PR045', 'ING002', 0.170, 'l', b'0'),
('RE082', 'PR045', 'ING004', 0.053, 'kg', b'0'),

'RE082', 'PR046', 'ING048', 0.240, 'l', b'0'),
'RE082', 'PR046', 'ING007', 0.200, 'kg', b'0'),
'RE082', 'PR046', 'ING057', 0.010, 'l', b'0'),
'RE082', 'PR046', 'ING004', 0.150, 'kg', b'0'),
'RE082', 'PR046', 'ING001', 0.125, 'kg', b'0'),
'RE082', 'PR046', 'ING003', 0.200, 'l', b'0'),
'RE082', 'PR046', 'ING011', 0.100, 'kg', b'0'),

'RE082', 'PR047', 'ING048', 0.280, 'l', b'0'),
'RE082', 'PR047', 'ING007', 0.250, 'kg', b'0'),
'RE082', 'PR047', 'ING057', 0.020, 'l', b'0'),
'RE082', 'PR047', 'ING004', 0.175, 'kg', b'0'),
'RE082', 'PR047', 'ING001', 0.175, 'kg', b'0'),
'RE082', 'PR047', 'ING003', 0.250, 'l', b'0'),
'RE082', 'PR047', 'ING011', 0.150, 'kg', b'0'),

'RE082', 'PR048', 'ING048', 0.300, 'l', b'0'),
'RE082', 'PR048', 'ING007', 0.275, 'kg', b'0'),
'RE082', 'PR048', 'ING057', 0.029, 'l', b'0'),
'RE082', 'PR048', 'ING004', 0.185, 'kg', b'0'),
'RE082', 'PR048', 'ING001', 0.190, 'kg', b'0'),
'RE082', 'PR048', 'ING003', 0.275, 'l', b'0'),
'RE082', 'PR048', 'ING011', 0.195, 'kg', b'0'),

'RE082', 'PR049', 'ING045', 0.200, 'kg', b'0'),
'RE082', 'PR049', 'ING015', 0.020, 'l', b'0'),
'RE082', 'PR049', 'ING007', 0.020, 'l', b'0'),
'RE082', 'PR049', 'ING003', 0.030, 'l', b'0'),
'RE082', 'PR049', 'ING002', 0.045, 'l', b'0'),
'RE082', 'PR049', 'ING057', 0.020, 'l', b'0'),

'RE082', 'PR050', 'ING045', 0.250, 'kg', b'0'),
'RE082', 'PR050', 'ING015', 0.024, 'l', b'0'),
'RE082', 'PR050', 'ING007', 0.030, 'l', b'0'),
'RE082', 'PR050', 'ING003', 0.040, 'l', b'0'),
'RE082', 'PR050', 'ING002', 0.060, 'l', b'0'),
'RE082', 'PR050', 'ING057', 0.025, 'l', b'0'),

'RE082', 'PR051', 'ING045', 0.270, 'kg', b'0'),
'RE082', 'PR051', 'ING015', 0.030, 'l', b'0'),
'RE082', 'PR051', 'ING007', 0.039, 'l', b'0'),
'RE082', 'PR051', 'ING003', 0.050, 'l', b'0'),
'RE082', 'PR051', 'ING002', 0.070, 'l', b'0'),
'RE082', 'PR051', 'ING057', 0.035, 'l', b'0'),

'RE082', 'PR052', 'ING001', 0.100, 'kg', b'0'),
'RE082', 'PR052', 'ING011', 0.200, 'kg', b'0'),
'RE082', 'PR052', 'ING003', 0.020, 'kg', b'0'),
'RE082', 'PR052', 'ING014', 0.040, 'kg', b'0'),
'RE082', 'PR052', 'ING004', 0.250, 'kg', b'0'),
'RE082', 'PR052', 'ING015', 0.090, 'kg', b'0'),

'RE082', 'PR053', 'ING001', 0.130, 'kg', b'0'),
'RE082', 'PR053', 'ING011', 0.250, 'kg', b'0'),
'RE082', 'PR053', 'ING003', 0.035, 'kg', b'0'),
'RE082', 'PR053', 'ING014', 0.050, 'kg', b'0'),
'RE082', 'PR053', 'ING004', 0.275, 'kg', b'0'),
'RE082', 'PR053', 'ING015', 0.120, 'kg', b'0'),

'RE082', 'PR054', 'ING001', 0.140, 'kg', b'0'),
'RE082', 'PR054', 'ING011', 0.275, 'kg', b'0'),
'RE082', 'PR054', 'ING003', 0.050, 'kg', b'0'),
'RE082', 'PR054', 'ING014', 0.045, 'kg', b'0'),
'RE082', 'PR054', 'ING004', 0.290, 'kg', b'0'),
'RE082', 'PR054', 'ING015', 0.140, 'kg', b'0'),

'RE082', 'PR055', 'ING048', 0.240, 'l', b'0'),
'RE082', 'PR055', 'ING007', 0.200, 'kg', b'0'),
'RE082', 'PR055', 'ING057', 0.010, 'l', b'0'),
'RE082', 'PR055', 'ING004', 0.150, 'kg', b'0'),
'RE082', 'PR055', 'ING001', 0.125, 'kg', b'0'),
'RE082', 'PR055', 'ING003', 0.200, 'l', b'0'),
'RE082', 'PR055', 'ING024', 0.050, 'l', b'0'),
'RE082', 'PR055', 'ING037', 0.200, 'l', b'0'),
'RE082', 'PR055', 'ING011', 0.100, 'kg', b'0'),

'RE082', 'PR056', 'ING048', 0.280, 'l', b'0'),
'RE082', 'PR056', 'ING007', 0.250, 'kg', b'0'),
'RE082', 'PR056', 'ING057', 0.020, 'l', b'0'),
'RE082', 'PR056', 'ING004', 0.175, 'kg', b'0'),
'RE082', 'PR056', 'ING001', 0.175, 'kg', b'0'),
'RE082', 'PR056', 'ING003', 0.250, 'l', b'0'),
'RE082', 'PR056', 'ING024', 0.080, 'l', b'0'),
'RE082', 'PR056', 'ING037', 0.230, 'l', b'0'),
'RE082', 'PR056', 'ING011', 0.150, 'kg', b'0'),

'RE082', 'PR057', 'ING048', 0.300, 'l', b'0'),
'RE082', 'PR057', 'ING007', 0.275, 'kg', b'0'),
'RE082', 'PR057', 'ING057', 0.029, 'l', b'0'),
'RE082', 'PR057', 'ING004', 0.185, 'kg', b'0'),
'RE082', 'PR057', 'ING001', 0.190, 'kg', b'0'),
'RE082', 'PR057', 'ING003', 0.275, 'l', b'0'),
'RE082', 'PR057', 'ING024', 0.950, 'l', b'0'),
'RE082', 'PR057', 'ING037', 0.250, 'l', b'0'),
'RE082', 'PR057', 'ING011', 0.195, 'kg', b'0'),

'RE082', 'PR058', 'ING036', 0.010, 'kg', b'0'),
'RE082', 'PR058', 'ING005', 0.100, 'l', b'0'),
'RE082', 'PR058', 'ING003', 0.020, 'l', b'0'),
'RE082', 'PR058', 'ING007', 0.030, 'l', b'0'),
'RE082', 'PR058', 'ING060', 0.020, 'l', b'0'),
'RE082', 'PR058', 'ING004', 0.200, 'l', b'0'),

'RE082', 'PR059', 'ING036', 0.020, 'kg', b'0'),
'RE082', 'PR059', 'ING005', 0.150, 'l', b'0'),
'RE082', 'PR059', 'ING003', 0.030, 'l', b'0'),
'RE082', 'PR059', 'ING007', 0.037, 'l', b'0'),
'RE082', 'PR059', 'ING060', 0.026, 'l', b'0'),
'RE082', 'PR059', 'ING004', 0.250, 'l', b'0'),

'RE082', 'PR060', 'ING041', 0.010, 'kg', b'0'),
'RE082', 'PR060', 'ING003', 0.200, 'kg', b'0'),
'RE082', 'PR060', 'ING007', 0.200, 'kg', b'0'),
'RE082', 'PR060', 'ING014', 0.060, 'kg', b'0'),
'RE082', 'PR060', 'ING036', 0.060, 'kg', b'0'),
'RE082', 'PR060', 'ING005', 0.300, 'l', b'0'),

'RE082', 'PR061', 'ING041', 0.020, 'kg', b'0'),
'RE082', 'PR061', 'ING003', 0.250, 'kg', b'0'),
'RE082', 'PR061', 'ING007', 0.230, 'kg', b'0'),
'RE082', 'PR061', 'ING014', 0.080, 'kg', b'0'),
'RE082', 'PR061', 'ING036', 0.070, 'kg', b'0'),
'RE082', 'PR061', 'ING005', 0.300, 'l', b'0'),


'RE082', 'PR062', 'ING041', 0.010, 'kg', b'0'),
'RE082', 'PR062', 'ING003', 0.200, 'kg', b'0'),
'RE082', 'PR062', 'ING007', 0.200, 'kg', b'0'),
'RE082', 'PR062', 'ING014', 0.060, 'kg', b'0'),
'RE082', 'PR062', 'ING061', 0.060, 'kg', b'0'),
'RE082', 'PR062', 'ING062', 0.080, 'l', b'0'),
'RE082', 'PR062', 'ING005', 0.300, 'l', b'0'),

'RE082', 'PR063', 'ING041', 0.020, 'kg', b'0'),
'RE082', 'PR063', 'ING003', 0.250, 'kg', b'0'),
'RE082', 'PR063', 'ING007', 0.230, 'kg', b'0'),
'RE082', 'PR063', 'ING014', 0.065, 'kg', b'0'),
'RE082', 'PR063', 'ING061', 0.075, 'kg', b'0'),
'RE082', 'PR063', 'ING062', 0.100, 'l', b'0'),
'RE082', 'PR063', 'ING005', 0.330, 'l', b'0'),

'RE082', 'PR064', 'ING041', 0.010, 'kg', b'0'),
'RE082', 'PR064', 'ING003', 0.200, 'kg', b'0'),
'RE082', 'PR064', 'ING007', 0.200, 'kg', b'0'),
'RE082', 'PR064', 'ING014', 0.060, 'kg', b'0'),
'RE082', 'PR064', 'ING001', 0.070, 'kg', b'0'),
'RE082', 'PR064', 'ING015', 0.085, 'l', b'0'),
'RE082', 'PR064', 'ING005', 0.300, 'l', b'0'),

'RE082', 'PR065', 'ING041', 0.020, 'kg', b'0'),
'RE082', 'PR065', 'ING003', 0.250, 'kg', b'0'),
'RE082', 'PR065', 'ING007', 0.230, 'kg', b'0'),
'RE082', 'PR065', 'ING014', 0.065, 'kg', b'0'),
'RE082', 'PR065', 'ING001', 0.075, 'kg', b'0'),
'RE082', 'PR065', 'ING015', 0.100, 'l', b'0'),
'RE082', 'PR065', 'ING005', 0.330, 'l', b'0');

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
('ST01', 'NGUYỄN TIẾN DŨNG', b'1', '2003-12-19', '2019-1-1', '0812535278', 'dungboi@gmail.com', 0, '0000-00-00', b'0'),
('ST02', 'ĐINH QUANG DUY', b'1', '2023-01-20', '2019-1-1', '0834527892', 'quangduy@gmail.com', 0, '0000-00-00', b'0'),
('ST03', 'NGUYỄN HOÀNG LONG', b'1', '2003-08-30', '2019-1-1', '0359872569', 'longbot@gmail.com', 0, '0000-00-00', b'0'),
('ST04', 'NGUYỄN ZI ĐAN', b'1', '2003-03-06', '2019-1-1', '0970352875', 'zidan@gmail.com', 0, '0000-00-00', b'0'),
('ST05', 'NGUYỄN THỊ XUÂN MAI', b'0', '2002-06-19', '2019-2-2', '0367834257', 'thungan@gmail.com', 3100000, '2023-09-15', b'0'),
('ST06', 'ĐINH TIẾN MẠNH', b'1', '2002-09-20', '2019-10-3', '0825367498', 'nhakho@gmail.com', 3100000, '2023-05-16', b'0'),
('ST07', 'ĐẶNG VĂN LÂM', b'1', '2001-02-18', '2020-5-6', '0935627488', 'phache@gmail.com', 3100000, '2023-06-27', b'0'),
('ST08', 'NGUYỄN THỊ LỆ GIANG', b'0', '2000-05-27', '2022-3-9', '0340734629', 'phucvu@gmail.com', 3100000, '2023-09-28', b'0'),
('ST09', 'HOÀNG XUÂN PHÚC', b'1', '2001-04-11', '2022-5-10', '0963527895', 'sale@gmail.com', 2000000, '2023-08-17', b'0');

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
('SUP001', 'GLOFOOD', '02838035555', ' L2-10 Tầng 2 Pearl Plaza, 561A Điện Biên Phủ, Phường 25, Quận Bình Thạnh, TP. HCM', 'Gigroup@gigroup.net', 150000, b'0'),
('SUP002', 'BIG C', '0839958368', '163 Phan Đăng Lưu, P. 1, Quận Phú Nhuận, TP.HCM', 'crv.dvkh@vn.centralretail.com', 150000, b'0'),
('SUP003', 'TousLesJours', '02838272772', '180 Hai Bà Trưng, Quận 1, TP. HCM', 'touslesjours@gmail.com', 150000, b'0');

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
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`RECEIPT_ID`),
  ADD KEY `FK_STAF` (`STAFF_ID`);

--
-- Indexes for table `recipe`
--
ALTER TABLE `recipe`
  ADD PRIMARY KEY (`RECIPE_ID`),
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
  ADD CONSTRAINT `FK_RECEIPT` FOREIGN KEY (`RECEIPT_ID`) REFERENCES `receipt` (`RECEIPT_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FK_SUP` FOREIGN KEY (`SUPPLIER_ID`) REFERENCES `supplier` (`SUPPLIER_ID`) ON UPDATE CASCADE;

--
-- Constraints for table `receipt`
--
ALTER TABLE `receipt`
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
