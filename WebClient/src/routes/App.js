import React, { Component } from "react";
import { connect } from "dva";
import { withRouter } from "dva/router";
import { Layout, Menu, Breadcrumb, Icon, BackTop } from "antd";
import style from "./App.less";
import { routerRedux } from "dva/router";
import qs from "qs";

const { Header, Sider, Content } = Layout;
const { SubMenu } = Menu;
const icons = new Map();

class App extends Component {
  state = {
    collapsed: false,
    menufold: "menu-fold"
  };

  menuClick = ({ key }) => {
    this.props.dispatch(
      routerRedux.push({
        pathname: key,
        search: qs.stringify()
      })
    );
  };

  componentDidMount() {
    icons.set("Resource", "desktop");
    icons.set("Process", "project");
    icons.set("Users", "team");
    icons.set("Detial", "bar-chart");
    icons.set("Control", "control");
  }

  onCollapse = () => {
    let collapsed = !this.state.collapsed;
    this.setState(
      collapsed ? { menufold: "menu-unfold" } : { menufold: "menu-fold" }
    );
    this.setState({ collapsed });
  };

  outClick = () => {
    this.props.dispatch({ type: "login/logout" });
  };

  render() {
    const { children, location } = this.props;
    const pathNames = location.pathname
      .split("/")
      .filter(item => item.length > 0);
    const selectedkey = "/" + pathNames[0];
    const username =
      sessionStorage.getItem("username") == null
        ? ""
        : sessionStorage.getItem("username");
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
              selectedKeys={[selectedkey]}
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
              <Menu.Item key="/Control">
                <Icon type="control" />
                <span>Control</span>
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
                <Menu mode="horizontal">
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
                    <Menu.Item key="logout" onClick={this.outClick}>
                      Sign out
                    </Menu.Item>
                  </SubMenu>
                </Menu>
              </div>
            </Header>
            <Content
              style={{ margin: "0 16px", background: "white", height: "100%" }}
              location={location}
            >
              <Breadcrumb
                separator=">"
                style={{ height: "56px", background: "#F0F2F5" }}
              >
                {pathNames.map(item => {
                  let type = icons.get(item);
                  return (
                    <Breadcrumb.Item key={item}>
                      <Icon type={type}></Icon>
                      <span style={{ lineHeight: "56px", fontSize: "14px" }}>
                        {item}
                      </span>
                    </Breadcrumb.Item>
                  );
                })}
              </Breadcrumb>
              {children}
            </Content>
            {/* <Footer style={{ textAlign: "center",height:'5%'}}>
                <h5>BP Dashboard ©2019 Created by RPA Platform</h5>
            </Footer> */}
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
    loading
  }))(App)
);
