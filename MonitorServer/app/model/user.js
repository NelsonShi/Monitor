'use strict';

module.exports = app => {
  const { STRING, INTEGER } = app.Sequelize;

  const User = app.model.define('User', {
    id: { type: INTEGER, primaryKey: true, autoIncrement: true },
    name: { type: STRING },
    gender: { type: INTEGER},
    login_name: STRING,
    password: STRING,
    department_id:INTEGER,
  }, {
    freezeTableName: true,
    tableName: 'User',
    timestamps: false,
  });

  return User;
};
