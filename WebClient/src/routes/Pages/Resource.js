import React, { Component } from "react";
import { connect } from "dva";

import ResourcesTable from "../../components/Resource/ResourceTable";

class Resource extends Component {
  render() {
    return (
      <div>
        <ResourcesTable resources={this.props.list}></ResourcesTable>
      </div>
    );
  }
}

Resource.propTypes = {};

export default connect()(Resource);
