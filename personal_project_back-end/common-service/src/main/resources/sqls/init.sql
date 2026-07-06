
CREATE TABLE CHECKOUT_REQUEST (
                                  ID INT AUTO_INCREMENT PRIMARY KEY,
                                  ROOM_ID INT NOT NULL,
                                  USER_ID INT NOT NULL,
                                  REQUEST_DATE DATE NOT NULL,
                                  STATUS VARCHAR(50) NOT NULL,
                                  REASON TEXT,
                                  CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE CONTRACT (
                          ID INT AUTO_INCREMENT PRIMARY KEY,
                          ROOM_ID INT NOT NULL,
                          USER_ID INT NOT NULL,
                          START_DATE DATE NOT NULL,
                          END_DATE DATE,
                          DEPOSIT DECIMAL(10, 2) NOT NULL,
                          MONTHLY_RENT DECIMAL(10, 2) NOT NULL,
                          STATUS VARCHAR(50) NOT NULL,
                          CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE MAINTENANCE_FEE (
                                 ID INT AUTO_INCREMENT PRIMARY KEY,
                                 MAINTENANCE_REQUEST_ID INT NOT NULL,
                                 PRICE DECIMAL(10, 2) NOT NULL,
                                 TOTAL_FEE DECIMAL(10, 2),
                                 CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE MAINTENANCE_REQUEST (
                                     ID INT AUTO_INCREMENT PRIMARY KEY,
                                     USER_ID INT NOT NULL,
                                     ROOM_ID INT NOT NULL,
                                     SERVICE_ROOM_ID INT NOT NULL,
                                     STATUS VARCHAR(50) NOT NULL,
                                     REQUEST_DATE DATE NOT NULL,
                                     DECISION TEXT,
                                     CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                     UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE SERVICE (
                         ID INT AUTO_INCREMENT PRIMARY KEY,
                         NAME VARCHAR(100) NOT NULL,
                         PRICE DECIMAL(10, 2) NOT NULL,
                         IS_ACTIVE TINYINT(1) DEFAULT 1,
                         CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE SERVICE_ROOM (
                              ID INT AUTO_INCREMENT PRIMARY KEY,
                              ROOM_ID INT NOT NULL,
                              SERVICE_ID INT NOT NULL,
                              QUANTITY INT NOT NULL,
                              PRICE DECIMAL(10, 2) NOT NULL,
                              IS_ACTIVE TINYINT(1) DEFAULT 1,
                              CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ROOM (
                      ID INT AUTO_INCREMENT PRIMARY KEY,
                      NUMBER VARCHAR(50) NOT NULL,
                      PRICE DECIMAL(10, 2) NOT NULL,
                      LENGTH DECIMAL(5, 2),
                      WIDTH DECIMAL(5, 2),
                      STATUS VARCHAR(50) NOT NULL,
                      NOTE TEXT,
                      CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE USER (
                      ID INT AUTO_INCREMENT PRIMARY KEY,
                      FULLNAME VARCHAR(100) NOT NULL,
                      EMAIL VARCHAR(100) NOT NULL,
                      CCCD VARCHAR(20) NOT NULL,
                      PHONE_NUMBER VARCHAR(20) NOT NULL,
                      PERMANENT_ADDRESS TEXT,
                      DATE_OF_BIRTH DATE,
                      RECORDED_AT DATE,
                      LICENSE_PLATE_NUMBER VARCHAR(20),
                      NOTE TEXT,
                      USERNAME VARCHAR(50) NOT NULL UNIQUE,
                      PASSWORD VARCHAR(255) NOT NULL,
                      CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ROLE (
                      ID INT AUTO_INCREMENT PRIMARY KEY,
                      NAME VARCHAR(50) NOT NULL,
                      CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE USER_ROLE (
                           ID INT AUTO_INCREMENT PRIMARY KEY,
                           USER_ID INT NOT NULL,
                           ROLE_ID INT NOT NULL,
                           CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE UTILITY_INDEX (
                               ID INT AUTO_INCREMENT PRIMARY KEY,
                               ROOM_ID INT NOT NULL,
                               ELECTRIC_OLD_INDEX INT NOT NULL,
                               ELECTRIC_NEW_INDEX INT NOT NULL,
                               ELECTRIC_USAGE INT NOT NULL,
                               ELECTRIC_RECORDED_AT DATE NOT NULL,
                               WATER_OLD_INDEX INT NOT NULL,
                               WATER_NEW_INDEX INT NOT NULL,
                               WATER_USAGE INT NOT NULL,
                               WATER_RECORDED_AT DATE NOT NULL,
                               CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE INVOICE_DETAIL (
                                ID INT AUTO_INCREMENT PRIMARY KEY,
                                INVOICE_ID INT NOT NULL,
                                DESCRIPTION TEXT,
                                QUANTITY INT NOT NULL,
                                PRICE DECIMAL(10, 2) NOT NULL,
                                CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE INVOICE (
                         ID INT AUTO_INCREMENT PRIMARY KEY,
                         PHONE_NUMBER VARCHAR(20) NOT NULL,
                         CCCD VARCHAR(20) NOT NULL,
                         EMAIL VARCHAR(100) NOT NULL,
                         USER_ID INT NOT NULL,
                         ROOM_ID INT NOT NULL,
                         NOTE TEXT,
                         TOTAL_PRICE DECIMAL(10, 2) NOT NULL,
                         DUE_DATE DATE NOT NULL,
                         PAYMENT_DATE DATE,
                         STATUS VARCHAR(50) NOT NULL,
                         PAYMENT_ID INT,
                         INVOICE_DETAIL_ID INT,
                         CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE ROOM_MEMBER (
                             ID INT AUTO_INCREMENT PRIMARY KEY,
                             ROOM_ID INT NOT NULL,
                             USER_ID INT NOT NULL,
                             CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                             UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE PAYMENT (
                         ID INT AUTO_INCREMENT PRIMARY KEY,
                         METHOD VARCHAR(50) NOT NULL,
                         STATUS VARCHAR(50) NOT NULL,
                         CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
