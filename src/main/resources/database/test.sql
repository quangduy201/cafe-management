CREATE TABLE `decentralization` (
  `DECENTRALIZATION_ID` varchar(10) NOT NULL,
  `DECENTRALIZATION_NAME` varchar(20) NOT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `decentralization_detail` (
  `DECENTRALIZATION_ID` varchar(10) NOT NULL,
  `MODULE_ID` varchar(10) NOT NULL,
  `CAN_ADD` bit(1) DEFAULT b'0',
  `CAN_EDIT` bit(1) DEFAULT b'0',
  `CAN_REMOVE` bit(1) DEFAULT b'0',
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `module` (
  `MODULE_ID` varchar(10) NOT NULL,
  `MODULE_NAME` varchar(20) NOT NULL,
  `DELETED` bit(1) DEFAULT b'0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

ALTER TABLE `decentralization_detail`
  ADD PRIMARY KEY (`DECENTRALIZATION_ID`, `MODULE_ID`),
  ADD KEY `FK_DECENT` (`DECENTRALIZATION_ID`),
  ADD KEY `FK_MODULE` (`MODULE_ID`);

ALTER TABLE `module`
  ADD PRIMARY KEY (`MODULE_ID`);

ALTER TABLE `decentralization_detail`
  ADD CONSTRAINT `FK_DECENT` FOREIGN KEY (`DECENTRALIZATION_ID`) REFERENCES `decentralization` (`DECENTRALIZATION_ID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `PK_MODULE` FOREIGN KEY (`MODULE_ID`) REFERENCES `module` (`MODULE_ID`) ON UPDATE CASCADE;


INSERT INTO `decentralization` (`DECENTRALIZATION_ID`, `DECENTRALIZATION_NAME`, `DELETED`) VALUES
('DE00', 'admin', b'0'),
('DE01', 'QUẢN LÝ', b'0'),
('DE02', 'NV BÁN HÀNG', b'0'),
('DE03', 'NV QUẢN LÝ NHÀ KHO', b'0'),
('DE04', 'NV PHỤC VỤ', b'0'),
('DE05', 'NV PHA CHẾ', b'0'),
('DE06', 'NV QUẢN LÝ GIẢM GIÁ', b'0');

INSERT INTO `decentralization_detail` (`DECENTRALIZATION_ID`, `MODULE_ID`, `CAN_ADD`, `CAN_EDIT`, `CAN_REMOVE`, `DELETED`) VALUES
('DE00', 'MOD01', 1, 1, 1, b'0'),
('DE00', 'MOD02', 1, 1, 1, b'0'),
('DE00', 'MOD03', 1, 1, 1, b'0'),
('DE00', 'MOD04', 1, 1, 1, b'0'),
('DE00', 'MOD05', 1, 1, 1, b'0'),
('DE00', 'MOD06', 1, 1, 1, b'0'),
('DE00', 'MOD07', 1, 1, 1, b'0'),
('DE00', 'MOD08', 1, 1, 1, b'0'),
('DE00', 'MOD09', 1, 1, 1, b'0'),
('DE00', 'MOD10', 1, 1, 1, b'0'),
('DE00', 'MOD11', 1, 1, 1, b'0'),
('DE00', 'MOD12', 1, 1, 1, b'0'),
('DE00', 'MOD13', 1, 1, 1, b'0'),
('DE01', 'MOD01', 1, 1, 1, b'0'),
('DE01', 'MOD02', 1, 1, 1, b'0'),
('DE01', 'MOD03', 1, 1, 1, b'0'),
('DE01', 'MOD04', 1, 1, 1, b'0'),
('DE01', 'MOD05', 1, 1, 1, b'0'),
('DE01', 'MOD06', 1, 1, 1, b'0'),
('DE01', 'MOD07', 1, 1, 1, b'0'),
('DE01', 'MOD08', 1, 1, 1, b'0'),
('DE01', 'MOD09', 1, 1, 1, b'0'),
('DE01', 'MOD10', 1, 1, 1, b'0'),
('DE01', 'MOD11', 1, 1, 1, b'0'),
('DE01', 'MOD12', 1, 1, 1, b'0'),
('DE01', 'MOD13', 1, 1, 1, b'0'),
('DE02', 'MOD01', 1, 0, 0, b'0'),
('DE02', 'MOD02', 0, 0, 0, b'0'),
('DE02', 'MOD03', 1, 1, 0, b'0'),
('DE02', 'MOD04', 0, 0, 0, b'0'),
('DE02', 'MOD05', 1, 1, 0, b'0'),
('DE02', 'MOD06', 1, 1, 0, b'0'),
('DE02', 'MOD07', 0, 0, 0, b'0'),
('DE02', 'MOD08', 0, 0, 0, b'0'),
('DE02', 'MOD09', 1, 1, 0, b'0'),
('DE02', 'MOD10', 0, 0, 0, b'0'),
('DE02', 'MOD11', 0, 0, 0, b'0'),
('DE02', 'MOD12', 0, 0, 0, b'0'),
('DE02', 'MOD13', 1, 0, 0, b'0'),
('DE03', 'MOD01', 1, 0, 0, b'0'),
('DE03', 'MOD02', 1, 1, 0, b'0'),
('DE03', 'MOD03', 0, 0, 0, b'0'),
('DE03', 'MOD04', 1, 1, 0, b'0'),
('DE03', 'MOD05', 0, 0, 0, b'0'),
('DE03', 'MOD06', 0, 0, 0, b'0'),
('DE03', 'MOD07', 0, 0, 0, b'0'),
('DE03', 'MOD08', 0, 0, 0, b'0'),
('DE03', 'MOD09', 0, 0, 0, b'0'),
('DE03', 'MOD10', 0, 0, 0, b'0'),
('DE03', 'MOD11', 0, 0, 0, b'0'),
('DE03', 'MOD12', 0, 0, 0, b'0'),
('DE03', 'MOD13', 1, 1, 0, b'0'),
('DE04', 'MOD01', 1, 0, 0, b'0'),
('DE04', 'MOD02', 0, 0, 0, b'0'),
('DE04', 'MOD03', 0, 0, 0, b'0'),
('DE04', 'MOD04', 0, 0, 0, b'0'),
('DE04', 'MOD05', 0, 0, 0, b'0'),
('DE04', 'MOD06', 0, 0, 0, b'0'),
('DE04', 'MOD07', 0, 0, 0, b'0'),
('DE04', 'MOD08', 0, 0, 0, b'0'),
('DE04', 'MOD09', 0, 0, 0, b'0'),
('DE04', 'MOD10', 0, 0, 0, b'0'),
('DE04', 'MOD11', 0, 0, 0, b'0'),
('DE04', 'MOD12', 0, 0, 0, b'0'),
('DE04', 'MOD13', 0, 0, 0, b'0'),
('DE05', 'MOD01', 1, 0, 0, b'0'),
('DE05', 'MOD02', 0, 0, 0, b'0'),
('DE05', 'MOD03', 0, 0, 0, b'0'),
('DE05', 'MOD04', 0, 0, 0, b'0'),
('DE05', 'MOD05', 0, 0, 0, b'0'),
('DE05', 'MOD06', 0, 0, 0, b'0'),
('DE05', 'MOD07', 1, 1, 0, b'0'),
('DE05', 'MOD08', 0, 0, 0, b'0'),
('DE05', 'MOD09', 0, 0, 0, b'0'),
('DE05', 'MOD10', 0, 0, 0, b'0'),
('DE05', 'MOD11', 0, 0, 0, b'0'),
('DE05', 'MOD12', 0, 0, 0, b'0'),
('DE05', 'MOD13', 0, 0, 0, b'0'),
('DE06', 'MOD01', 1, 0, 0, b'0'),
('DE06', 'MOD02', 0, 0, 0, b'0'),
('DE06', 'MOD03', 0, 0, 0, b'0'),
('DE06', 'MOD04', 0, 0, 0, b'0'),
('DE06', 'MOD05', 0, 0, 0, b'0'),
('DE06', 'MOD06', 0, 0, 0, b'0'),
('DE06', 'MOD07', 0, 0, 0, b'0'),
('DE06', 'MOD08', 1, 1, 0, b'0'),
('DE06', 'MOD09', 0, 0, 0, b'0'),
('DE06', 'MOD10', 0, 0, 0, b'0'),
('DE06', 'MOD11', 0, 0, 0, b'0'),
('DE06', 'MOD12', 0, 0, 0, b'0'),
('DE06', 'MOD13', 0, 0, 0, b'0');

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




