import React, { Component } from "react";
import { Route, Redirect } from "dva/router";

class PrivateRoute extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isAuthenticated: sessionStorage.getItem("hasLogin")
    };
  }

  render() {
    let { component: Component, ...rest } = this.props;
    return this.state.isAuthenticated ? (
      <Route {...rest} render={props => <Component {...props} />} />
    ) : (
      <Redirect to="login" />
    );
  }
}

export default PrivateRoute;
