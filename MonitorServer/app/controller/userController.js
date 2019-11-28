'use strict';

const Controller = require('egg').Controller;

class UserController extends Controller {
  async findAll() {
    const { ctx } = this;
    ctx.body = await ctx.service.userService.findAll();
  }

  async findAllRoles() {
    const { ctx } = this;
    ctx.body = await ctx.service.roleService.findAll();
  }

  async UserLogin(){
    const{ctx}=this;
    let user=await ctx.service.userService.findUser(ctx.request.body.user);
    ctx.body=user;     
  }

}

module.exports = UserController;
