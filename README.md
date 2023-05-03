# Cafe Management

Cafe Management is a desktop Java application that helps manage a cafe. It consists of three layers:<br>

- Graphics User Interface (GUI): a presentation layer  that uses Java Swing for the user interface.
- Business Logic Layer (BLL): a business layer that handles the application logic.
- Data Access Layer (DAL): a data access layer that connects to a MySQL database.

___

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Contributors](#contributors)
- [License](#license)
- [External Dependencies](#external-dependencies)

___

## Features

The Cafe Management application includes the following features:

- Manage items which a real cafe has (e.g. products, staffs, customers, ingredients...).
- Create, manage and export bills (selling products) and receipt (importing ingredients) to Excel.
- Generate reports on sales, inventory and other metrics.

___

## Installation

To install the Cafe Management application, follow these steps:

- Clone the repository to your local machine.
- Import the project into your Java IDE as Maven project (e.g. IntelliJ, Eclipse).
- Create a MySQL database named `cafe-management` and run this [`SQL`](database/cafe_db.sql) file.
- Configure this [`MySQL`](src/com/cafe/DAL/MySQL.java) class to connect to your MySQL database.
- Build the project and run this [`CafeManagement`](src/com/cafe/main/CafeManagement.java) class.
- Login with the default account: `username = 'admin'`, `password = 'admin'`

___

## Contributors

The following contributors have contributed to the Cafe Management:

| **ID**       | **Name**                                              |
|--------------|-------------------------------------------------------|
| `3121410116` | [`Đinh Quang Duy   `](https://github.com/quangduy201) |
| `3121410296` | [`Nguyễn Hoàng Long`](https://github.com/LongBOTT)    |
| `3121410111` | [`Nguyễn Tiến Dũng `](https://github.com/Dungweb)     |
| `3121410138` | [`Nguyễn Zi Đan    `](https://github.com/zidan63)     |

___

## External Dependencies

- MySQL Connector/J 8.0.32
- Apache POI 5.2.3
- Apache Log4j 2.20.0
- JCalendar 1.4
- JFreeChart 1.5.4
- OpenCV 4.7.0-0

___

## License

This project is licensed under the [`MIT License`](https://opensource.org/licenses/MIT).
See the [`LICENSE`](LICENSE) file for more information.

___

_This file was created on April 15, 2023, v1.0_
