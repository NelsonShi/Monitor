import { Component } from "react";
import { Menu, Card, Row, Col } from "antd";
import { Table, Button, Icon  } from 'antd'

class ProcessTable extends Component {
  render() {
    const columns = [
      { title: "Name", dataIndex: "name", key: "name" },
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
      <div style={{ height: "75%", background: "white", marginTop: "3%" }}>
        <Table
          columns={columns}
        
         
        />
      </div>
    );
  }
}

export default ProcessTable;
