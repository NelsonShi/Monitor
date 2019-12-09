// eslint-disable-next-line strict
const Service = require('egg').Service;

class UserService extends Service {

  async findAll() {
    const { ctx, app } = this;
    let userList = {};
    try {
        userList = await app.model.User.findAll();
    } catch (err) {
      ctx.logger.error(err);
    }
    return userList;
  }

  async findUser(user){
    const { ctx, app } = this;
    let userList = {};
    try {
        userList = await app.model.User.findOne({where:{login_name:user.username,password:user.password}});
    } catch (err) {
      ctx.logger.error(err);
    }
    return userList;
  }

}
module.exports = UserService;