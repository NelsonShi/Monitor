// eslint-disable-next-line strict
const Service = require('egg').Service;

class RoleService extends Service {

  async findAll() {
    const { ctx, app } = this;
    let roleList = {};
    try {
        roleList = await app.model.Role.findAll();
    } catch (err) {
      ctx.logger.error(err);
    }
    return roleList;
  }

}
module.exports = RoleService;