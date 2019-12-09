import React, { Component } from "react";
import { connect } from "dva";
import { Table, Row, Col } from "antd";
import ErrorChart from "../../components/Process/ProcessErrorChart";
import style from "./ProcessDetial.less";

class ProcessDetial extends Component {
  componentDidMount() {
    const { dispatch, location } = this.props;
    const id = new URLSearchParams(location.search.substring(1)).get("Id");
    if (id != null) {
      dispatch({ type: "process/loadError", payload: { id: id } });
    }
  }

  render() {
    const { currentP } = this.props;
    const columns1 = [
      {
        title: "Error Code",
        dataIndex: "errorType",
        key: "errorType",
        width: "25%",
        className: style.column
      },
      {
        title: "Error Count",
        dataIndex: "errorCount",
        key: "errorCount",
        width: "25%",
        className: style.column
      },
      {
        title: "Description",
        dataIndex: "errorDescription",
        key: "errorDescription",
        className: style.column
      }
    ];
    const columns2 = [
      {
        title: "Time",
        dataIndex: "time",
        key: "time",
        width: "25%",
        className: style.column
      },
      {
        title: "Stage Name",
        dataIndex: "stageName",
        key: "stageName",
        width: "25%",
        className: style.column
      },
      {
        title: "Log",
        dataIndex: "logResult",
        key: "logResult",
        className: style.column
      }
    ];
    return (
      <div style={{ height: "100%", background: "#F0F2F5" }}>
        <ErrorChart data={currentP} />
        <div style={{ height: "550px" }}>
          <Row style={{ height: "100%" }}>
            <Col
              span={12}
              justify="space-arround"
              style={{ padding: "2% 1% 0 0", height: "100%" }}
            >
              <div
                style={{ height: "550px", width: "100%", background: "white" }}
              >
                <Table
                  style={{ width: "100%", background: "white" }}
                  rowKey={record => record.errorType}
                  size="small"
                  dataSource={currentP.errorCodes}
                  columns={columns1}
                  pagination={{ pageSize: 50 }}
                  scroll={{ y: 400 }}
                  title={() => (
                    <font size="3" color="#ffa940">
                      Error
                    </font>
                  )}
                />
              </div>
            </Col>
            <Col
              span={12}
              justify="space-arround"
              style={{ padding: "2% 0 0 1%", height: "100%" }}
            >
              <div
                style={{ height: "550px", width: "100%", background: "white" }}
              >
                <Table
                  style={{ width: "100%", background: "white" }}
                  size="small"
                  rowKey={record => record.id}
                  dataSource={currentP.logs}
                  columns={columns2}
                  pagination={{ pageSize: 50 }}
                  scroll={{ y: 400 }}
                  title={() => (
                    <font size="3" color="#1890ff">
                      Logs
                    </font>
                  )}
                />
              </div>
            </Col>
          </Row>
        </div>
      </div>
    );
  }
}

function mapStateToProps(state) {
  // 得到modal中的state)
  const { currentP } = state.process;
  return {
    currentP
  };
}

export default connect(mapStateToProps)(ProcessDetial);
