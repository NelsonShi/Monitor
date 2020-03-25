import React from "react";
import { Table, Button, Dropdown, Icon, Menu } from "antd";
import style from "./SessionTable.less";

class SessionTable extends React.Component {
  menuOnClick = (record, { key }) => {
    const {dispatch}=this.props;
    const userId=sessionStorage.getItem("userId");
    dispatch({
      type: "control/"+key,
      payload: {
        resourceId:record.starterresourceid,
        sessionId:record.sessionid,
        userId:userId
      }
    });
  };

  render() {
    const { title, fontColor, dataSource,type} = this.props;
    const pendingMenu = ({record}) => (
      <Menu onClick={this.menuOnClick.bind(this, record)}>
        <Menu.Item key="start">Start</Menu.Item>
        <Menu.Item key="delete">Delete</Menu.Item>
      </Menu>
    );
    const seesionMenu=({record})=>(
      <Menu onClick={this.menuOnClick.bind(this, record)}>
         <Menu.Item key="stop" disabled={record.statusid!==1}>Stop</Menu.Item>
      </Menu>
    );
    let meun=type===0?pendingMenu:seesionMenu;
    const columns = [
      {
        title: "ID",
        dataIndex: "sessionnumber",
        key: "sessionnumber",
        width: 50,
        ellipsis: true,
        className: style.smallColunm
      },
      {
        title: "Process",
        dataIndex: "processName",
        key: "processName",
        className: style.smallColunm
      },
      {
        title: "Resource",
        dataIndex: "resourceName",
        key: "resourceName",
        width: 100,
        ellipsis: true,
        className: style.smallColunm
      },
      {
        title: "User",
        dataIndex: "userName",
        key: "userName",
        width: 100,
        ellipsis: true,
        className: style.smallColunm
      },
      {
        title: "Status",
        dataIndex: "statusStr",
        key: "statusStr",
        width: 100,
        ellipsis: true,
        className: style.smallColunm
      },
      {
        title: "Start Time",
        dataIndex: "startTimeStr",
        key: "startTimeStr",
        width: 150,
        className: style.smallColunm
      },
      {
        title: "End Time",
        dataIndex: "endTimeStr",
        key: "endTimeStr",
        width: 150,
        className: style.smallColunm
      },
      {
        title: "Action",
        key: "action",
        width: 150,
        className: style.smallColunm,
        render(record) {    
          return (
            <Dropdown
              overlay={meun({record:record})}
              placement="bottomCenter"
              trigger={["click"]}
            >
              <a className="ant-dropdown-link">
                Actions <Icon type="down" />
              </a>
            </Dropdown>
          );
        }
      }
    ];
    return (
      <div style={{ background: "white", height: "100%" }}>
        <Table
          columns={columns}
          pagination={{ pageSize: 10 }}
          scroll={{ y: 440 }}
          dataSource={dataSource}
          size="small"
          title={() => (
            <font size="3" color={fontColor}>
              <b>{title}</b>
            </font>
          )}
        ></Table>
      </div>
    );
  }
}

export default SessionTable;
