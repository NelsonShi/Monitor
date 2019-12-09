import React, { Component } from "react";
import { connect } from "dva";
import ProcessHeaderCards from "../../components/Process/ProcessHeaderCards";
import ProcessTable from "../../components/Process/ProcessTable";

class Process extends Component {
  render() {
    return (
      <div style={{ height: "100%", background: "#F0F2F5" }}>
        <ProcessHeaderCards></ProcessHeaderCards>
        <ProcessTable></ProcessTable>
      </div>
    );
  }
}

Process.propTypes = {};

export default connect()(Process);
