/* eslint-disable no-useless-constructor */
// react
import React, { Component } from "react";
// dva
import { connect } from "dva";
// antd
import { Table, Icon } from "antd";
import styles from "./Resource.css";
import ResourceExpander from "./ResourceExpander";

class ResourceTable extends Component {
  constructor(props) {
    super(props);
  }

  componentDidMount() {
    const { dispatch, list, schedules } = this.props;
    let dateNow=new Date();
    let time=dateNow.getTimezoneOffset()/60
    if (list!=null&&list.length <= 0) {
      dispatch({ type: "resource/fetch", payload: { requestTimeZone: time } });
    }
    if (schedules.length <= 0) {
      dispatch({ type: "resource/findSchedules", payload: {dateNow:dateNow, requestTimeZone: time } });
    }
  }



  render() {
    const { list: dataSource, loading} = this.props;
    const columns = [
      {
        dataIndex: "isNettyConnected",
        key: "isNettyConnected",
        render(text) {
          let type = "check-circle";
          let tcolor = "#52c41a";
          if (text === 0) {
            type = "close-circle";
            tcolor = "#eb2f96";
          }
          return (
            <Icon type={type} theme="twoTone" twoToneColor={tcolor}></Icon>
          );
        }
      },
      { title: "Name", dataIndex: "name", key: "name" },
      {
        title: "Process Name",
        dataIndex: "processName",
        key: "processName",
        render: (text, row, index) => {
          let ss = {};
          switch (row.processStatus) {
            case 0:
              ss.content = row.processName;
              ss.fncolor = "#FFA500";
              break;
            case 1:
              ss.content = row.processName;
              ss.fncolor = "#008000";
              break;
            case 7:
              ss.content = row.processName + "( Stopping )";
              ss.fncolor = "#FF4500";
              break;
            default:
              ss.content = "No Process";
              ss.fncolor = "blue";
              break;
          }
          return (
            <font size="3" color={ss.fncolor}>
              {ss.content}
            </font>
          );
        }
      },
      { title: "TimeSlot", dataIndex: "timeSlot", key: "timeSlot" },
      { title: "Last Update", dataIndex: "lastupdated", key: "lastupdated" },
      { title: "UserName", dataIndex: "userName", key: "userName" },
      {
        title: "DisplayStatus",
        dataIndex: "displayStatus",
        key: "displayStatus",
        render(text) {
          return <h3>{text}</h3>;
        }
      }
    ];
    return (
      <div className={styles.normal}>
        <div>
          <Table
            rowKey={record=>record.resourceid}
            className={styles.table}
            columns={columns}
            dataSource={dataSource}
            loading={loading}
            expandedRowRender={record => (
              <ResourceExpander
                BotName={record.name}
                schedules={this.props.schedules}
              />
            )}
          />
        </div>
      </div>
    );
  }
}

// <Pagination
// className="ant-table-pagination"
// total={total}
// current={current}
// pageSize={PAGE_SIZE}
//  />

function mapStateToProps(state) {
  // 得到modal中的state)
  const { list, total, page } = state.resource;
  return {
    loading: state.loading.models.resource,
    list,
    total: parseInt(total, 10),
    page,
    schedules: state.resource.schedules
  };
}

export default connect(mapStateToProps)(ResourceTable);
