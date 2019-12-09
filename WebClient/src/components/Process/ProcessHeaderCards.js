import { Component } from "react";
import { Icon, Card, Row, Col } from "antd";
import style from "./NumberCard.less";
import CountUp from "react-countup";
import { connect } from "dva";

class ProcessHeaderCards extends Component {
  render() {
    const {processCount,errorCount,undefinedCount}=this.props;
    return (
      <div style={{ height: "15%" }}>
        <Row>
          <Col span={6} justify="space-arround" style={{ paddingRight: "3%" }}>
            <Card className={style.numberCard}>
              <Icon
                className={style.iconWarp}
                style={{ color: "#f5222d" }}
                type="warning"
              />
              <div className={style.content}>
                <p className={style.title}>Error Process</p>
                <p className={style.number}>
                  <CountUp
                    start={0}
                    end={processCount}
                    duration={2.75}
                    useEasing
                    useGrouping
                    separator=","
                  />
                </p>
              </div>
            </Card>
          </Col>
          <Col
            span={6}
            justify="space-arround"
            style={{ padding: "0 2% 0 1%" }}
          >
            <Card className={style.numberCard}>
              <Icon
                className={style.iconWarp}
                style={{ color: "#ff7a45" }}
                type="alert"
              />
              <div className={style.content}>
                <p className={style.title}>Total Error</p>
                <p className={style.number}>
                  <CountUp
                    start={0}
                    end={errorCount}
                    duration={2.75}
                    useEasing
                    useGrouping
                    separator=","
                  />
                </p>
              </div>
            </Card>
          </Col>
          <Col
            span={6}
            justify="space-arround"
            style={{ padding: "0 1% 0 2%" }}
          >
            <Card className={style.numberCard}>
              <Icon
                className={style.iconWarp}
                style={{ color: "#ffa940" }}
                type="question"
              />
              <div className={style.content}>
                <p className={style.title}>Undefined Error</p>
                <p className={style.number}>
                  <CountUp
                    start={0}
                    end={undefinedCount}
                    duration={2.75}
                    useEasing
                    useGrouping
                    separator=","
                  />
                </p>
              </div>
            </Card>
          </Col>
          <Col span={6} justify="space-arround" style={{ paddingLeft: "3%" }}>
            <Card className={style.numberCard}>
              <div className={style.content}>
                <p className={style.title}></p>
                <p className={style.number}></p>
              </div>
            </Card>
          </Col>
        </Row>
      </div>
    );
  }
}
function mapStateToProps(state) {
  // 得到modal中的state)
  const {processCount,errorCount,undefinedCount}=state.process;
  return {
    processCount,
    errorCount,
    undefinedCount
  };
}

export default connect(mapStateToProps) (ProcessHeaderCards)
