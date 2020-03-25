import React from "react";
import { Tree, Input, Checkbox, Modal, Button, Row, Col, Select } from "antd";
import style from "./ProcessGroupList.less";
const { TreeNode, DirectoryTree } = Tree;
const { Search } = Input;
const { Option } = Select;

const isExistInChild = (processList, searchValue) => {
  let isExist = false;
  if (!processList) {
    return isExist;
  }
  processList.map(p => {
    if (p.name.indexOf(searchValue) > -1) {
      isExist = true;
    }
  });
  return isExist;
};

class ProcessGroupList extends React.Component {
  state = {
    expandedKeys: [],
    searchValue: "",
    autoExpandParent: true,
    loading: false,
    visible: false,
    currentProcess: {},
    RsAvailableAllow: "Disable"
  };

  checkBoxChanged = e => {
    const { resources } = this.props;
    resources.map(item=>{
     if(item.resourceid===e.target.value){
        item.checked=e.target.checked;
     }
    })
  };

  onClick = p => {
    this.setState({ currentProcess: p });
    let dateNow = new Date();
    let timeZone = dateNow.getTimezoneOffset() / 60;
    const dispatch = this.props.dispatch;
    dispatch({
      type: "control/changeModalVisiable",
      payload: {
        modalVisiable:true
      }
    });
    dispatch({
      type: "control/loadAvailableResources",
      payload: {
        timeZone: timeZone,
        timeStart: dateNow,
        timeSpan: p.runTimeMins
      }
    });
    dispatch({ type: "resource/fetch", payload: { requestTimeZone: timeZone } });
   
  };

  handleOk = () => {
    // this.setState({ loading: true });
    // setTimeout(() => {
    //   this.setState({ loading: false, visible: false });
    // }, 3000);
    const { dispatch, resources} = this.props;
    const { currentProcess } = this.state;
    const username = sessionStorage.getItem("username");
    const userId=sessionStorage.getItem("userId");
    let resourcesIds = [];
    resources.map(item => {
      if (item.checked) {
        resourcesIds.push(item.resourceid);
      }
    });
    dispatch({
      type: "control/pending",
      payload: {
        userId:userId,
        userName: username,
        processId: currentProcess.processid,
        resourceIds: resourcesIds
      }
    });
  };

  handleCancel = () => {
    const dispatch = this.props.dispatch;
    dispatch({
      type: "control/changeModalVisiable",
      payload: {
        modalVisiable:false
      }
    });
  };

  modalClose = () => {
    const { dispatch,resources} = this.props;
    dispatch({ type: "control/clearAvailableRs" });
    resources.map(item => {
      item.checked = false;
    });
  };

  onExpand = expandedKeys => {
    this.setState({
      expandedKeys,
      autoExpandParent: false
    });
  };

  RsAvailableChanged = value => {
    this.setState({ RsAvailableAllow: value });
  };

  onChange = e => {
    const { value } = e.target;
    const { processlist } = this.props;
    const expandedKeys = processlist
      .map(item => {
        const isExist = isExistInChild(item.processList, value);
        if (item.name.indexOf(value) > -1 || isExist) {
          return item.id;
        }
        return null;
      })
      .filter((item, i, self) => item && self.indexOf(item) === i);
    this.setState({
      expandedKeys: expandedKeys,
      searchValue: value,
      autoExpandParent: true
    });
  };

  render() {
    console.log("render processGroupList",resources)
    const { processlist, resources, availableResources,modalVisiable} = this.props;
    const {
      loading,
      currentProcess,
      autoExpandParent,
      searchValue,
      expandedKeys,
      RsAvailableAllow
    } = this.state;
    const getTitle = (content, searchValue) => {
      const index = content.indexOf(searchValue);
      const beforeStr = content.substr(0, index);
      const afterStr = content.substr(index + searchValue.length);
      const title =
        index > -1 ? (
          <span>
            {beforeStr}
            <span style={{ color: "#f50" }}>{searchValue}</span>
            {afterStr}
          </span>
        ) : (
          <span>{content}</span>
        );
      return title;
    };
    const loop = data =>
      data.map(item => {
        const title = getTitle(item.name, searchValue);
        if (item.processList) {
          return (
            <TreeNode key={item.id} title={title}>
              {item.processList.map(p => {
                const subTitle = getTitle(p.name, searchValue);
                return (
                  <TreeNode
                    title={
                      <span>
                        {subTitle}
                        <Button style={{marginLeft:'3px'}}
                          shape="circle"
                          icon="play-circle"
                          size="small"
                          onClick={this.onClick.bind(this, p)}
                        />
                      </span>
                    }
                    isLeaf
                    key={p.processid}
                  ></TreeNode>
                );
              })}
            </TreeNode>
          );
        }
        return <TreeNode key={item.id} title={title} />;
      });
    let runTimeCompont;
    if (currentProcess.runTimeMins > 0) {
      runTimeCompont = (
        <font style={{ margin: "5px" }}>
          {currentProcess.runTimeStr} / {currentProcess.runTimeMins} minites
        </font>
      );
    } else {
      runTimeCompont = <div></div>;
    }
    return (
      <div style={{ margin: "6px" }}>
        <Search
          style={{ marginBottom: 8 }}
          placeholder="Search"
          onChange={this.onChange}
        />
        <DirectoryTree className={style.normal}
          multiple={true}
          defaultExpandAll={true}
          onExpand={this.onExpand}
          expandedKeys={expandedKeys}
          autoExpandParent={autoExpandParent}
        >
          {loop(processlist)}
        </DirectoryTree>
        <Modal
          width={820}
          visible={modalVisiable}
          title="Pending a process"
          onOk={this.handleOk}
          onCancel={this.handleCancel}
          maskClosable={false}
          afterClose={this.modalClose}
          footer={[
            <Button key="Cancel" onClick={this.handleCancel}>
              Cancel
            </Button>,
            <Button
              key="Pending"
              type="primary"
              loading={loading}
              onClick={this.handleOk}
            >
              Pending
            </Button>
          ]}
        >
          <div>
            <p>
              <font size="2">
                {" "}
                <b>Name :</b>{" "}
              </font>
              <font style={{ margin: "5px" }}>{currentProcess.name}</font>
            </p>
            <p>
              <font size="2">
                <b>Run Time :</b>{" "}
              </font>
              {runTimeCompont}
            </p>
            <span>
              <b>Select Resource </b>
              <Select
                defaultValue={RsAvailableAllow}
                style={{ width: 120 }}
                onChange={this.RsAvailableChanged}
              >
                <Option value="Disable">Disable</Option>
                <Option value="Allow">Allow</Option>
              </Select>
            </span>
            <Checkbox.Group style={{ width: "100%" }} defaultValue={[]}>
              <Row>
                {resources.map(item => {
                  let nameColr = "black";
                  let statusStr = "";
                  switch (item.processStatus) {
                    case -1:
                      nameColr = "red";
                      statusStr = "  ( " + item.displayStatus + " )";
                      break;
                    case 0:
                      nameColr = "#ffc069";
                      statusStr = "  ( pending )";
                      break;
                    case 1:
                      nameColr = "#52c41a";
                      statusStr = "  ( running )";
                      break;
                    case 7:
                      nameColr = "red";
                      statusStr = "  ( stopping )";
                      break
                    default:
                      break;
                  }
                  if (availableResources && availableResources.length > 0) {
                    let availableResource = availableResources.filter(
                      r => r.resourceid === item.resourceid
                    );
                    if (
                      availableResource[0] &&
                      availableResource[0].available >= 0
                    ) {
                      if (RsAvailableAllow === "Disable") {
                        item.available = availableResource[0].available === 1;
                      } else {
                        item.available = true;
                      }
                      if (availableResource[0].available < 1) {
                        nameColr = "#c41d7f";
                        statusStr =
                          " ( " + availableResource[0].nextRunTime + " mins)";
                      }
                    }
                  }
                  return (
                    <Col span={8} key={item.resourceid}>
                      <Checkbox
                        style={{ margin: "5px" }}
                        value={item.resourceid}
                        disabled={!item.available}
                        onChange={this.checkBoxChanged}
                      >
                        <font size="3" color={nameColr}>
                          {item.name}
                        </font>
                        <font size="2" color={nameColr}>
                          {statusStr}
                        </font>
                      </Checkbox>
                    </Col>
                  );
                })}
              </Row>
            </Checkbox.Group>
          </div>
        </Modal>
      </div>
    );
  }
}

export default ProcessGroupList;
