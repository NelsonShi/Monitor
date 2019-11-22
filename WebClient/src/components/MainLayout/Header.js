import React, { Component } from 'react'
import { Menu, Icon,Row, Col } from 'antd'
import { connect } from 'dva'
import { Link, routerRedux } from 'dva/router'
import qs from 'qs'

const { SubMenu } = Menu;

class Header extends Component{

    menuClick = ({ item, key, keyPath }) => {
        this.props.dispatch(
            routerRedux.push({
                pathname: key,
                search: qs.stringify()
            })
        )
    }

    outClick=()=>{
        this.props.dispatch({ type:"login/logout"})
    }

    render() {
        const { location } = this.props
        const username=sessionStorage.getItem("username")==null?'':sessionStorage.getItem("username")
        return (
            <Row style={{width:'100%'}}>
            <Col span={21} type="flex" justify="space-arround">
            <Menu selectedKeys={[location.pathname]}
                mode="horizontal"
                theme="dark"
                onClick={this.menuClick}
                >
                <Menu.Item key="/Resource">
                  <Icon type='Resources'/>
                   Resources
                </Menu.Item>              
                <Menu.Item key="/process">
                    Process
                </Menu.Item>
            </Menu>
            </Col>
            <Col span={2} style={{background:'#001529',height:'100%'}} type="flex" justify="space-arround">
                <div style={{width:'50%',height:'50%',position:'absolute',marginTop:'10%'}}>
                    <spn style={{color:'#0088FF',height:'100%',lineHeight:'100%'}}>{username}</spn>
                </div>
            </Col>
            <Col span={1} style={{background:'#001529',height:'100%'}} type="flex" justify="space-arround">
                <div style={{width:'50%',height:'50%',position:'absolute',marginTop:'20%'}}>
                    <a onClick={this.outClick} style={{color:'#0088FF',height:'100%',lineHeight:'100%'}}>Out</a>
                </div>
            </Col>
            </Row>
        )
    }
}

function mapStateToProps(state) {
    // 得到modal中的state)
    return {
        login: state.login.loginStatus,
    }
  }

export default connect(mapStateToProps)(Header)