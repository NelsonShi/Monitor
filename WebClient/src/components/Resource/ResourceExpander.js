import { Component } from "react";
import { connect } from "dva";
import { Table, Row, Col } from "antd";
import TimeRuler from './TimeRuler' 
import styles from './Resource.css'

class ResourceExpander extends Component {
  componentDidMount() {
    const { dispatch, bots } = this.props;
    if (bots <= 0) {
      dispatch({ type: "timer/getBots", payload: { page: 1 } });
    }
    this.timer = setInterval(
      () => dispatch({ type: "timer/getBots", payload: { page: 1 } }),
      1000
    );
  }

  componentWillUnmount() {
    clearInterval(this.timer);
  }

  getScheduleComponet() {
    const { schedules,BotName } = this.props;
    let schedule = schedules.filter(item => item.resourcename === BotName);
    if(schedule[0]==null||schedule[0].timeSlots==null||schedule[0].timeSlots.length<=0){
      return(<Row>     
        <Col span={24} type="flex" justify="space-arround">
          <div style={{background:'#BBBBBB',height:'30PX',width:'100%'}}>          
          </div>
        </Col>    
     </Row>)
    }else{
      return(
        <Row>     
         <Col span={24} type="flex" justify="space-arround">
           <div className={styles.panel}>   
           {
             schedule[0].timeSlots.map((r)=>
               <div style={{background:'#27B42E',height:'29PX',display:'inline-block',width:r.width+'%',marginLeft:r.marginLeft+'%'}}
               title={r.processName+' ('+r.timeSpan+')'}/>
             )
           }
           </div>
         </Col>    
       </Row>    
      )            
    }  
  }

  render() {
    const { BotName, bots } = this.props;
    let botComponent;
    let scheduleCompontent = this.getScheduleComponet();
    if (bots == null || bots <= 0) {
      botComponent = <div style={{ color: "blue" }}>All bot did not connected to netty</div>;
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
        const colums = [
          { title: "Name", dataIndex: "BotName", key: "BotName",width:'20%' },
          //    {title:'CPU',dataIndex:'CPUCount',key:'CPUCount'},
          { title: "CPU", dataIndex: "CPUUseRate", key: "CPUUseRate",width:'15%' },
          { title: "RAM", dataIndex: "RAMUseRate", key: "RAMUseRate",width:'15%' },
          { title: "Resolution", dataIndex: "Resolution", key: "Resolution",width:'20%' },
          {
            title: "Application Status",
            dataIndex: "processList",
            key: "processList",
            width:'30%',
            render(content) {
              if (content == null || content <= 0) {
                return (
                  <div style={{ color: "red" }}>no application running</div>
                );
              } else {
                return content.map(r => (
                  <div style={{ color: "blue" }}>
                    {r.ProcessName} running Status: {r.ProcessRunningStatus}
                  </div>
                ));
              }
            }
          }
        ];
        botComponent = (
          <Table
            loading={false}
            columns={colums}
            dataSource={bot}
            pagination={false}
          ></Table>
        );
      }
    }
    return <div>{botComponent} {scheduleCompontent}<TimeRuler/></div>;
  }
}

function mapStateToProps(state) {
  // 得到modal中的state
  return {
    bots: state.timer.bots
  };
}

export default connect(mapStateToProps)(ResourceExpander);
