'use strict';

module.exports = app => {
  const { STRING, INTEGER } = app.Sequelize;

  const role = app.model.define('Role', {
    id: { type: INTEGER, primaryKey: true, autoIncrement: true },
    role_name: { type: STRING },
  }, {
    freezeTableName: true,
    tableName: 'Role',
    timestamps: false,
  });

  return role;
};
