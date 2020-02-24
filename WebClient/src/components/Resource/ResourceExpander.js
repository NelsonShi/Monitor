import { Component } from "react";
import { connect } from "dva";
import { Table, Row, Col, Menu, Dropdown, Icon, message } from "antd";
import TimeRuler from "./TimeRuler";
import styles from "./Resource.css";

class ResourceExpander extends Component {
  componentDidMount() {
    const { dispatch, bots } = this.props;
    if (bots <= 0) {
      dispatch({ type: "timer/getBots", payload: { page: 1 } });
    }
  }

  onClick = (processId, botIp, { key }) => {
    const { dispatch } = this.props;
    message.info(`trigger :  ${botIp}, ${key},${processId}`);
    dispatch({
      type: "resource/control",
      payload: { ip: botIp, type: key, processId: processId }
    });
  };

  getScheduleComponet() {
    const { schedules, BotName } = this.props;
    let schedule = schedules.filter(item => item.resourcename === BotName);
    if (
      schedule[0] == null ||
      schedule[0].timeSlots == null ||
      schedule[0].timeSlots.length <= 0
    ) {
      return (
        <Row>
          <Col span={24} type="flex" justify="space-arround">
            <div className={styles.panel}></div>
          </Col>
        </Row>
      );
    } else {
      return (
        <Row>
          <Col span={24} type="flex" justify="space-arround">
            <div className={styles.panel}>
              {schedule[0].timeSlots.map(r => (
                <div
                  key={r.index}
                  style={{
                    background: "#73d13d",
                    height: "29PX",
                    display: "inline-block",
                    width: r.width + "%",
                    marginLeft: r.marginLeft + "%"
                  }}
                  title={r.processName + " (" + r.timeSpan + ")"}
                />
              ))}
            </div>
          </Col>
        </Row>
      );
    }
  }

  render() {
    const { BotName, bots } = this.props;
    let botComponent;
    let scheduleCompontent = this.getScheduleComponet();
    if (bots == null || bots <= 0) {
      botComponent = (
        <div style={{ color: "blue" }}>All bot did not connected to netty</div>
      );
    } else {
      let bot = bots.filter(item => item.BotName === BotName);
      // eslint-disable-next-line react/style-prop-object
      if (bot == null || bot <= 0) {
        botComponent = (
          <div style={{ color: "blue" }}>
            This bot did not connected to netty
          </div>
        );
      } else {
        const menu = ({ processId, botIp }) => (
          <Menu onClick={this.onClick.bind(this, processId, botIp)}>
            <Menu.Item key="ReStart">ReStart</Menu.Item>
            <Menu.Item key="Close">Close</Menu.Item>
          </Menu>
        );

        const falseMenu = ({ processId, botIp }) => (
          <Menu onClick={this.onClick.bind(this, processId, botIp)}>
            <Menu.Item key="Start">Start</Menu.Item>
          </Menu>
        );
        const colums = [
          { title: "Name", dataIndex: "BotName", key: "BotName", width: "20%" },
          //    {title:'CPU',dataIndex:'CPUCount',key:'CPUCount'},
          {
            title: "CPU",
            dataIndex: "CPUUseRate",
            key: "CPUUseRate",
            width: "15%",
            render(text) {
              return <span>{text + "%"}</span>;
            }
          },
          {
            title: "RAM",
            dataIndex: "RAMUseRate",
            key: "RAMUseRate",
            width: "15%",
            render(text) {
              return <span>{text + "%"}</span>;
            }
          },
          {
            title: "Resolution",
            dataIndex: "Resolution",
            key: "Resolution",
            width: "20%"
          },
          {
            title: "Application Status",
            dataIndex: "processList",
            key: "processList",
            width: "30%",
            render(content, record) {
              if (content == null || content <= 0) {
                return (
                  <div style={{ color: "red" }}>no application running</div>
                );
              } else {
                return content.map(r => (
                  <Dropdown
                    key={r.processId}
                    overlay={
                      r.processRunningStatus === 1
                        ? menu({ processId: r.processId, botIp: record.BotIP })
                        : falseMenu({
                            processId: r.processId,
                            botIp: record.BotIP
                          })
                    }
                    placement="bottomCenter"
                    trigger={["click"]}
                  >
                    <div style={{ width: "50%" }}>
                      <a
                        className="ant-dropdown-link"
                        style={
                          r.processRunningStatus === 1
                            ? { color: "#5b8c00" }
                            : { color: "#ad2102" }
                        }
                      >
                        {r.WindowTitle}
                        <Icon type="down" />
                      </a>
                    </div>
                  </Dropdown>
                ));
              }
            }
          }
        ];
        botComponent = (
          <Table
            rowKey={record => record.BotIP}
            loading={false}
            size="middle"
            columns={colums}
            dataSource={bot}
            pagination={false}
          ></Table>
        );
      }
    }
    return (
      <div>
        {botComponent} {scheduleCompontent}
        <TimeRuler />
      </div>
    );
  }
}

function mapStateToProps(state) {
  // 得到modal中的state
  return {
    bots: state.timer.bots
  };
}

export default connect(mapStateToProps)(ResourceExpander);
