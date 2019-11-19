'use strict';
const equelize = {
  dialect: 'mysql', // support: mysql, mariadb, postgres, mssql
  database: 'monitor',
  host: 'localhost',
  port: '3306',
  username: 'root',
  underscored: true,
  password: 'root',
};
module.exports = equelize;
