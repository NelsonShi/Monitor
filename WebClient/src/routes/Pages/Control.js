import React, { Component } from "react";
import { Row, Col } from "antd";
import { connect } from "dva";
import ProcessGroup from "../../components/Control/ProcessGroupList";
import SessionSummaryCard from "../../components/Control/SessionSummaryCard";
import SessionTable from "../../components/Control/SessionTable";
import SreachBar from "../../components/Control/SreachBar";

class Control extends Component {
  componentDidMount() {
    const {
      dispatch,
      groups,
      resources,
      pengdingSessions,
      bpaStatus
    } = this.props;
    let dateNow = new Date();
    let time = dateNow.getTimezoneOffset() / 60;
    if (groups != null && groups.length <= 0) {
      dispatch({ type: "control/fetch" });
    }
    if (resources != null && resources.length <= 0) {
      dispatch({ type: "resource/fetch", payload: { requestTimeZone: time } });
    }
    if (pengdingSessions != null && pengdingSessions.length <= 0) {
      dispatch({ type: "control/findPendingSessions" });
    }
    if (bpaStatus != null && bpaStatus.length <= 0) {
      dispatch({ type: "control/loadStatus" });
    }
  }

  render() {
    const {
      groups,
      resources,
      availableResources,
      dispatch,
      pengdingSessions,
      bpaStatus,
      sessions,
      modalVisiable
    } = this.props;
    const runningCount=sessions.length===0?0:sessions.filter(item=>item.statusid===1).length;
    const stopCount=sessions.length===0?0:sessions.filter(item=>item.statusid===3).length;
    return (
      <div style={{ height: "1200px", background: "#F0F2F5" }}>
        <Row style={{ height: "100%" }}>
          <Col span={6} style={{ height: "100%", background: "white" }}>
            <ProcessGroup
              processlist={groups}
              resources={resources}
              availableResources={availableResources}
              dispatch={dispatch}
              modalVisiable={modalVisiable}
            ></ProcessGroup>
          </Col>
          <Col span={18} style={{ height: "100%" }}>
            <div
              style={{
                height: "100%",
                background: "#F0F2F5",
                marginLeft: "16px",
                boxSizing: "border-box"
              }}
            >
              <Row style={{ height: "100px" }} gutter={[16, 0]}>
                <Col span={8} style={{ height: "100%" }}>
                  <SessionSummaryCard
                    title="Pending Count"
                    content={pengdingSessions.length}
                    fontColor="#ffc069"
                  />
                </Col>
                <Col span={8} style={{ height: "100%" }}>
                  <SessionSummaryCard
                    title="Running Count"
                    content={runningCount}
                    fontColor="#a0d911"
                  />
                </Col>
                <Col span={8} style={{ height: "100%" }}>
                  <SessionSummaryCard
                    title="Stopped Count"
                    content={stopCount}
                    fontColor="#f5222d"
                  />
                </Col>
              </Row>
              <Row
                style={{
                  height: "450px",
                  background: "#F0F2F5",
                  paddingTop: "10px"
                }}
              >
                <SessionTable
                 type={0}
                  title="Pendings"
                  fontColor="#ffc069"
                  dataSource={pengdingSessions}
                  dispatch={dispatch}
                />
              </Row>
              <Row
                style={{
                  height: "45px",
                  background: "#F0F2F5",
                  paddingTop: "10px"
                }} 
              >
                <SreachBar
                  dispatch={dispatch}
                  resources={resources}
                  bpaStatus={bpaStatus}
                />
              </Row>
              <Row
                style={{
                  height: "605px",
                  background: "#F0F2F5",
                  paddingTop: "10px"
                }}
              >
                <SessionTable
                  type={1}
                  title="Sessions"
                  fontColor="blue"
                  dispatch={dispatch}
                  dataSource={sessions}
                />
              </Row>
            </div>
          </Col>
        </Row>
      </div>
    );
  }
}

function mapStateToProps(state) {
  // 得到modal中的state)
  const {
    groups,
    availableResources,
    pengdingSessions,
    bpaStatus,
    sessions,
    modalVisiable
  } = state.control;
  const { list: resources } = state.resource;
  return {
    loading: state.loading.models.control,
    groups,
    resources,
    availableResources,
    pengdingSessions,
    bpaStatus,
    sessions,
    modalVisiable
  };
}

export default connect(mapStateToProps)(Control);
