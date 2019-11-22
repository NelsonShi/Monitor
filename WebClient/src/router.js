/* eslint-disable react/jsx-no-comment-textnodes */
import React from 'react'
import {Switch, Redirect, routerRedux, Route } from 'dva/router'
import PrivateRoute from './routes/PrivateRouter'
import App from './routes/App'

import dynamic from 'dva/dynamic' // 按需加载路由

const { ConnectedRouter } = routerRedux

function RouterConfig({ history, app }) {
    const Process = dynamic({
        app,
        component: () => import('./routes/Pages/Process')
    })

    const Login = dynamic({
        app,
        component: () => import('./routes/Login/Login')
    })
    const Resource = dynamic({
        app,
        component: () => import('./routes/Pages/Resource')
    })
    return (
      <ConnectedRouter history={history}>
      <App>
          <Switch>
              <Route path="/login" exact component={Login} />
              <PrivateRoute path="/process" exact component={Process} />
              <PrivateRoute path="/resource" exact component={Resource} />
              <Route path="*" render={() => <Redirect to="Resource" />} />
          </Switch>
      </App>
  </ConnectedRouter>
    )
}

export default RouterConfig