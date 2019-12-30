import { Component } from "react";
import { connect } from "dva";
import { Table } from "antd";
import { Link } from 'react-router-dom'
import style from "./ProcessTable.less";

class ProcessTable extends Component {
  componentDidMount() {
    const { dispatch, list } = this.props;
    let time=new Date().getTimezoneOffset()/60
    if (list != null && list.length <= 0) {
      dispatch({ type: "process/fetch", payload: { requestTimeZone: time } });
    }
  }

  render() {
    const { list, loading } = this.props;
    const columns = [
      {
        title: "Process Name",
        dataIndex: "processName",
        key: "processName",
        width: "25%"
      },
      { title: "Error Count", dataIndex: "errorCount", key: "errorCount" },
      {
        title: "Undefined Error",
        dataIndex: "undefinedCount",
        key: "undefinedCount"
      },
      { title: "Last Time", dataIndex: "lastTimeStr", key: "lastTimeStr" },
      {
        title: "Description",
        dataIndex: "message",
        key: "message",
        width: "25%",
        render(text) {
          return <h3>{text}</h3>;
        }
      },
      {
        title: "Action",
        width: "10%",
        render(record,text) {   
          return (<Link to={`Process/Detial?Id=${text.processId}`}>Detail</Link>);
        }
      }
    ];
    return (
      <div
        style={{
          height: "75%",
          background: "white",
          marginTop: "3%",
          padding: "0 1%"
        }}
      >
        <Table
          rowKey={record=>record.processId}
          className={style.table}
          columns={columns}
          dataSource={list}
          loading={loading}
        />
      </div>
    );
  }
}

function mapStateToProps(state) {
  // 得到modal中的state)
  const { list, page } = state.process;
  return {
    loading: state.loading.models.process,
    list,
    page
  };
}

export default connect(mapStateToProps)(ProcessTable);
