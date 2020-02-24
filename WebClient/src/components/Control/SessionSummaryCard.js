import React from "react";
import { Card } from "antd";

class SessionSummaryCard extends React.Component {
  render() {
    const { title, content, fontColor } = this.props;
    return (
      <Card
        title={
        <font size='3' color={fontColor}>{title}</font>
        }
        style={{ width: "100%", height: "100%" }}
        size="small"
      >
        <h2> {content}</h2>
      </Card>
    );
  }
}

export default SessionSummaryCard;
