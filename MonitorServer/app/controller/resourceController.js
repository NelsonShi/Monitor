'use strict';

const Controller = require('egg').Controller;

class ResourceController extends Controller {
  async findAll() {
    const { ctx } = this;
    ctx.body = await ctx.service.resourceService.findAll();
  }

}

module.exports = ResourceController;
