import React, { Component } from 'react'
import { Menu, Icon } from 'antd'
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

    render() {
        const { location } = this.props
        return (
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
        )
    }
}

export default connect()(Header)