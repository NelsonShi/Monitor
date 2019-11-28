import { Component } from "react";
import { Menu, Card, Row, Col } from "antd";
import style from "./NumberCard.less";

class ProcessHeaderCards extends Component {
  render() {
    return (
      <div style={{ height: "15%" }}>
        <Row>
          <Col span={6} justify="space-arround" style={{paddingRight:'3%'}}>
            <Card className={style.numberCard}></Card>
          </Col>
          <Col span={6} justify="space-arround" style={{padding:'0 2% 0 1%'}}>
            <Card className={style.numberCard}></Card>
          </Col>
          <Col span={6} justify="space-arround" style={{padding:'0 1% 0 2%'}}>
            <Card className={style.numberCard}></Card>
          </Col>
          <Col span={6} justify="space-arround" style={{paddingLeft:'3%'}}>
            <Card className={style.numberCard}></Card>
          </Col>
        </Row>
      </div>
    );
  }
}

export default ProcessHeaderCards;
