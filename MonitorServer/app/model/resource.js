'use strict';

module.exports = app => {
  const { STRING, INTEGER } = app.Sequelize;

  const Resource = app.model.define('resource', {
    id: { type: INTEGER, primaryKey: true, autoIncrement: true },
    bp_id: { type: INTEGER },
    name: { type: STRING, allowNull: true, field: 'name' },
    connect_status: INTEGER,
    running_status: INTEGER,
  }, {
    freezeTableName: true,
    tableName: 'resource',
    timestamps: false,
  });

  return Resource;
};
