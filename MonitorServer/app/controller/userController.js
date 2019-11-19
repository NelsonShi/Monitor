'use strict';

const Controller = require('egg').Controller;

class UserController extends Controller {
  async findAll() {
    const { ctx } = this;
    ctx.body = await ctx.service.userService.findAll();
  }

}

module.exports = UserController;
