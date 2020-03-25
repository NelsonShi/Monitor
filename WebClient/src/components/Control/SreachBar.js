import React from "react";
import { Select, DatePicker, Button } from "antd";
import moment from "moment";
const { Option } = Select;

class SreachBar extends React.Component {
  state = {
    selectedResource: '',
    selectedStatus: '',
    startDate: '',
    endDate: ''
  };

  componentDidMount() {
    let today = new Date();
    let month = today.getMonth() + 1;
    month = month < 10 ? "0" + month : month;
    let day = today.getDate() < 10 ? "0" + today.getDate() : today.getDate();
    let startDate = today.getFullYear() + "/" + month + "/" + day;
    this.setState({startDate:startDate})
    this.onClick(startDate);
  }

  onClick = (value) => {
    const { dispatch } = this.props;
    const { selectedResource, selectedStatus, startDate, endDate } = this.state;
    let searchDate;
    if(startDate.length>0){
      searchDate=startDate;
    }else{
      searchDate=value;
    }
    let timeZone = new Date().getTimezoneOffset() / 60;
    dispatch({
      type: "control/loadSessions",
      payload: {
        resourceId: selectedResource,
        status: selectedStatus,
        startTime: searchDate,
        endTime: endDate,
        timeZone: timeZone
      }
    });
  };

  resourceOnChanged = value => {
    this.setState({ selectedResource: value });
  };

  statusOnChanged = value => {
    this.setState({ selectedStatus: value });  
  };

  startDateOnChanged = (date, dateString) => {
    this.setState({ startDate: dateString });  
  };

  endDateOnChanged = (date, dateString) => {
    this.setState({ endDate: dateString });
  };

  changeSearchValue(){
    const { dispatch } = this.props;
    const { selectedResource, selectedStatus, startDate, endDate } = this.state;
    let timeZone = new Date().getTimezoneOffset() / 60;
    dispatch({
      type: "control/changeSearchValue",
      payload:{
        resourceId: selectedResource,
        status: selectedStatus,
        startTime: startDate,
        endTime: endDate,
        timeZone: timeZone
      }
    })
  }

  render() {
    const dateFormat = "YYYY/MM/DD";
    const { resources, bpaStatus } = this.props;
    return (
      <div
        style={{
          height: "100%"
        }}
      >
        <span>
          <Select
            allowClear={true}
            onChange={this.resourceOnChanged}
            style={{ width: "20%" }}
            showSearch
            placeholder="Select Resource"
            optionFilterProp="children"
            filterOption={(input, option) =>
              option.props.children
                .toLowerCase()
                .indexOf(input.toLowerCase()) >= 0
            }
          >
            {resources.map(item => {
              return (
                <Option value={item.resourceid} key={item.resourceid}>
                  {item.name}
                </Option>
              );
            })}
          </Select>
          <Select
            style={{ width: "20%", marginLeft: "1%" }}
            allowClear={true}
            showSearch
            onChange={this.statusOnChanged}
            placeholder="Select Status"
            optionFilterProp="children"
            filterOption={(input, option) =>
              option.props.children
                .toLowerCase()
                .indexOf(input.toLowerCase()) >= 0
            }
          >
            {bpaStatus.map(item => {
              return (
                <Option value={item.statusid} key={item.statusid}>
                  {item.description}
                </Option>
              );
            })}
          </Select>
          <DatePicker
            defaultValue={moment()}
            format={dateFormat}
            onChange={this.startDateOnChanged}
            placeholder="Select Start Time"
            style={{ width: "20%", marginLeft: "1%" }}
          />
          <DatePicker
            // defaultValue={moment(endDate, dateFormat)}
            format={dateFormat}
            onChange={this.endDateOnChanged}
            placeholder="Select End Time"
            style={{ width: "20%", marginLeft: "1%" }}
          />
          <Button
            type="primary"
            icon="search"
            style={{ width: "16%", marginLeft: "1%" }}
            onClick={this.onClick}
          >
            Search
          </Button>
        </span>
      </div>
    );
  }
}

export default SreachBar;
