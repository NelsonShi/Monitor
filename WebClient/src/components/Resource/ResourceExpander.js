
import {Component}  from 'react'
import { connect } from 'dva'
import { Table} from 'antd'


class ResourceExpander extends Component{

    componentDidMount() {
        const {dispatch,bots} = this.props
        if (bots <= 0) {
            dispatch({ type: 'timer/getBots', payload: { page: 1 } })
        }
        this.timer = setInterval(() => dispatch({ type: 'timer/getBots', payload: { page: 1 } }), 1000);
    }

    componentWillUnmount(){
        clearInterval(this.timer)
    }

     render(){
         const {BotName,bots} = this.props
         if(bots==null||bots<=0)return(<div>there is no data</div>)
         let bot=bots.filter(item=>item.BotName===BotName) ;
         // eslint-disable-next-line react/style-prop-object
         if(bot==null||bot<=0){return(<div style={{color:'blue'}}>This bot did not connected to netty</div>);}else{
             const colums=[{title:'Name',dataIndex:'BotName',key:'BotName'},
                           {title:'CPU',dataIndex:'CPUCount',key:'CPUCount'},
                           {title:'CPUUseRate',dataIndex:'CPUUseRate',key:'CPUUseRate'},
                           {title:'RAMUseRate',dataIndex:'RAMUseRate',key:'RAMUseRate'},
                           {title:'Resolution',dataIndex:'Resolution',key:'Resolution'},
                           {title:'Application Status',dataIndex:'processList',key:'processList',
                            render(content){
                                if(content==null||content<=0){
                                    return(<div style={{color:'red'}}>no application running</div>)
                                }else{
                                    return(content.map((r=><div style={{color:'blue'}}>{r.ProcessName} running Status: {r.ProcessRunningStatus}</div>)))
                                }
                            }
                           },
                          ]
             return(
                 <Table
                  loading={false}
                  columns={colums}
                  dataSource={bot}
                  pagination={false}
                 ></Table>
             )
         }
         
     }
}

function mapStateToProps(state) {
    // 得到modal中的state)
    return {    
        bots:state.timer.bots
    }
}


export default connect(mapStateToProps)(ResourceExpander)