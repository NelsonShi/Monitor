/* eslint-disable no-useless-constructor */
// react
import React, { Component } from 'react'
// dva
import { connect } from 'dva'
// antd
import { Table, Button, Icon  } from 'antd'
import styles from './Resource.css'
import ResourceExpander from './ResourceExpander'

// import { PAGE_SIZE } from '../../constants'
// import { Record } from '_immutable@3.8.2@immutable'

class ResourceTable extends Component{
    
    constructor(props) {
        super(props)
      }

    componentDidMount() {
        const { dispatch, list } = this.props
        if (list.length <= 0) {
            dispatch({ type: 'resource/fetch', payload: { page: 1 } })
        }
        this.timer = setInterval(() =>{this.fleshResources()} , 2000);
    }

    componentWillUnmount(){
        clearInterval(this.timer)
    }

    fleshResources(){
        const { dispatch} = this.props     
        dispatch({ type: 'timer/fleshResource', payload: { page: 1 } })
        const vos=this.props.vos;   
        if(vos!=null&&vos.length>0){
            dispatch({ type: 'resource/fleshList', payload: { value: vos } })
        }
    }
   
    render() {
        const { list: dataSource, loading, total, page: current } = this.props
        const columns = [ { dataIndex: 'isNettyConnected',key:'isNettyConnected',
                          render(text){
                          let type='check-circle';
                          let tcolor='#52c41a';
                          if(text===0){
                                type='close-circle';
                                tcolor='#eb2f96';
                             }
                              return(<Icon type={type} theme='twoTone' twoToneColor={tcolor}></Icon>)
                             }
                          }, 
                          {title: 'Id', dataIndex: 'resourceid', key:'resourceid',width:200},
                          { title: 'Name',dataIndex: 'name',key:'name',},
                          
                          {title: 'Process Running',dataIndex: 'processesrunning',key:'processesrunning',
                          render:text=>                            
                               {
                                // eslint-disable-next-line eqeqeq
                                // eslint-disable-next-line default-case
                                let ss={}
                                switch(text){
                                case 1:ss.content='processing';
                                         ss.fncolor='green';
                                         break;
                                default:ss.content='no process';
                                        ss.fncolor='#FFC125'
                                        break  
                                }
                                return(<font size="3" color={ss.fncolor}>{ss.content}</font> );                                
                               }                                                                                                    
                          },
                          {title: 'Action Running', dataIndex: 'actionsrunning', key: 'actionsrunning',
                          render:text=>                            
                          {
                           // eslint-disable-next-line eqeqeq
                           let hasaction=(text=='1');
                           let fncolor=hasaction?'green':'#FFC125';
                           let content=hasaction?'running':'no action'
                           return(<font size="3" color={fncolor}>{content}</font> );                                
                          }                                                       
                          },
                          {title: 'Last Update',dataIndex: 'lastupdated',key: 'lastupdated'},
                          {title: 'UserName',dataIndex: 'userName',key: 'userName'},
                          {title: 'Statusid',dataIndex: 'statusid',key: 'statusid'},
                          {title: 'DisplayStatus',dataIndex: 'displayStatus',key: 'displayStatus',render(text){return(<h3>{text}</h3>)}},
                          {title: 'AttributeID',dataIndex: 'attributeID',key: 'attributeID'},
                          {title: 'FQDN',dataIndex: 'fqdn',key: 'fqdn'},
                          {title: 'Bot IP',dataIndex: 'botIp',key: 'botIp'},
                        //   {title: 'Action',key: 'operation',render:()=><Button>detial</Button>},
                        ]
        return (
            <div className={styles.normal}>
            <div>             
                <Table className={styles.table}             
                    columns={columns}
                    dataSource={dataSource}
                    loading={loading}
                    expandedRowRender={record=><ResourceExpander BotName={record.name}/>}             
                />            
            </div>
        </div>
        );
    }
}

// <Pagination
// className="ant-table-pagination"
// total={total}
// current={current}
// pageSize={PAGE_SIZE}
//  />

function mapStateToProps(state) {
    // 得到modal中的state)
    const { list, total, page } = state.resource
    return {
        loading: state.loading.models.resource,
        list,
        total: parseInt(total, 10),
        page,
        vos:state.timer.vos,
    }
}

export default connect(mapStateToProps)(ResourceTable)