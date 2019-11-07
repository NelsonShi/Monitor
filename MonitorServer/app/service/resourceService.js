// eslint-disable-next-line strict
const Service = require('egg').Service;

class ResourceService extends Service {

  async findAll() {
    const { ctx, app } = this;
    let resourceList = {};
    try {
      resourceList = await app.model.Resource.findAll();
    } catch (err) {
      ctx.logger.error(err);
    }
    return resourceList;
  }

}
module.exports = ResourceService;
