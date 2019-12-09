import React, { Component } from "react";


class SubLayoutForProcess extends Component {
  render() {
    const { children } = this.props;
    return (
      <div >
        <div >{children}</div>
      </div>
    );
  }
}

export default SubLayoutForProcess;
