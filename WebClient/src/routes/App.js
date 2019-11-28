import React, { Component, Children } from "react";
import { connect } from "dva";
import { withRouter } from "dva/router";
import { Layout, Menu, Breadcrumb, Icon, BackTop } from "antd";
import style from "./App.less";
import { routerRedux } from "dva/router";
import qs from "qs";

const { Header, Footer, Sider, Content } = Layout;
const { SubMenu } = Menu;

class App extends Component {
  state = {
    collapsed: false,
    itemInfo: {},
    menufold: "menu-fold"
  };

  menuClick = ({ item, key, keyPath }) => {
    let itemInfo = {
      type: item.props.children[0].props.type,
      name: item.props.children[1].props.children
    };
    this.setState({ itemInfo });
    this.props.dispatch(
      routerRedux.push({
        pathname: key,
        search: qs.stringify()
      })
    );
  };

  onCollapse = () => {
    let collapsed = !this.state.collapsed;
    this.setState(
      collapsed ? { menufold: "menu-unfold" } : { menufold: "menu-fold" }
    );
    this.setState({ collapsed });
  };

  outClick=()=>{
    this.props.dispatch({ type:"login/logout"})
}

  render() {
    let { children, location } = this.props;
    let  itemInfo  = this.state.itemInfo.type==null?{type:'desktop',name:'Resource'}:this.state.itemInfo
    const username=sessionStorage.getItem("username")==null?'':sessionStorage.getItem("username")
    return (
      <div>
        <Layout style={{ minHeight: "100vh" }}>
          <Sider collapsed={this.state.collapsed}>
            <div className={style.logo}>
              <img alt="logo" src="/public/logo.svg" />
            </div>
            <Menu
              theme="dark"
              defaultSelectedKeys={["Resource"]}
              mode="inline"
              selectedKeys={[location.pathname]}
              onClick={this.menuClick}
            >
              <Menu.Item key="/Resource">
                <Icon type="desktop" />
                <span>Resource</span>
              </Menu.Item>
              <Menu.Item key="/Process">
                <Icon type="project" />
                <span>Process</span>
              </Menu.Item>
              <Menu.Item key="/Users">
                <Icon type="team" />
                <span>Users</span>
              </Menu.Item>
            </Menu>
          </Sider>
          <Layout
            style={{ height: "100vh", overflow: "scroll" }}
            id="mainContainer"
          >
            <BackTop target={() => document.getElementById("mainContainer")} />
            <Header className={style.header}>
              <div className={style.button} onClick={this.onCollapse}>
                <Icon type={this.state.menufold} />
              </div>
              <div className={style.rightWarpper}>
                <Menu  mode="horizontal">
                  <SubMenu
                    style={{
                      float: "right"
                    }}
                    title={
                      <span>
                        <Icon type="user" />
                        {username}
                      </span>
                    }
                   >
                    <Menu.Item key="logout" onClick={this.outClick}>Sign out</Menu.Item>
                  </SubMenu>
                </Menu>
              </div>
            </Header>
            <Content style={{ margin: "0 16px",background:'white' }} location={location}>
              <Breadcrumb >
                <Breadcrumb.Item>
                  <Menu mode="horizontal" style={{ background: "#F0F2F5",borderBottom:'none'}}>
                    <Menu.Item key={"/" + itemInfo.name}>
                      <Icon type={itemInfo.type} />
                      <span>{itemInfo.name}</span>
                    </Menu.Item>
                  </Menu>
                </Breadcrumb.Item>
              </Breadcrumb>
              {children}
            </Content>
            <Footer style={{ textAlign: "center",height:'5%'}}>
                <h5>BP Dashboard ©2019 Created by RPA Platform</h5>
            </Footer>
          </Layout>
        </Layout>
      </div>
    );
  }
}

App.propTypes = {};

export default withRouter(
  connect(({ app, loading }) => ({
    app,
    loading,
  }))(App)
);
