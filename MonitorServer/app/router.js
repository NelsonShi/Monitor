'use strict';

/**
 * @param {Egg.Application} app - egg application
 */
module.exports = app => {
  const { router, controller } = app;
  router.get('/', controller.home.index);
  router.get('/resources', controller.resourceController.findAll);
  router.get('/users', controller.userController.findAll);
  router.get('/roles', controller.userController.findAllRoles);
  router.post('/login', controller.userController.UserLogin);
};
