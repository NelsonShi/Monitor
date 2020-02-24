/* eslint-disable react/jsx-no-comment-textnodes */
import React from "react";
import { Switch, routerRedux, Route } from "dva/router";
import PrivateRoute from "./routes/PrivateRouter";
import App from "./routes/App";

import dynamic from "dva/dynamic"; // 按需加载路由

const { ConnectedRouter } = routerRedux;

function RouterConfig({ history, app }) {
  const Process = dynamic({
    app,
    component: () => import("./routes/Pages/Process")
  });

  const errorPage = dynamic({
    app,
    component: () => import("./routes/Pages/404")
  });

  const ProcessDetial = dynamic({
    app,
    component: () => import("./routes/Pages/ProcessDetial")
  });

  const Login = dynamic({
    app,
    component: () => import("./routes/Login/Login")
  });
  const Resource = dynamic({
    app,
    component: () => import("./routes/Pages/Resource")
  });
  const Control = dynamic({
    app,
    component: () => import("./routes/Pages/Control")
  });
  return (
    <ConnectedRouter history={history}>
      <Switch>
        <Route path="/login" exact component={Login} />
        <App>       
          <Switch>
           /**
             route 请注意上下顺序
             */
            <PrivateRoute path={"/Resource"} exact component={Resource} />         
            <PrivateRoute path={"/Process/Detial"}  component={ProcessDetial} />
            <PrivateRoute path={"/Process"} exact component={Process} />
            <PrivateRoute path={"/Control"} exact component={Control} />
            {
            //子路由实现方式
            // <Route
            //   path="/process"
            //   render={({ history, location }) => (
            //     <SubLayout history={history} location={location}>
            //       <Switch>
            //         <Route path="/detial" component={ProcessDetial}></Route>
            //         <Route path="/table" component={Process}></Route>
            //         {/* <Route  component={errorPage}></Route> */}
            //       </Switch>
            //     </SubLayout>
            //   )}
            // />
            }
            <PrivateRoute component={errorPage} />
          </Switch>
        </App>
      </Switch>
    </ConnectedRouter>
  );
}

export default RouterConfig;
