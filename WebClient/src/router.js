/* eslint-disable react/jsx-no-comment-textnodes */
import React from 'react'
import { Router, Route, Switch, Redirect, routerRedux } from 'dva/router'
import IndexPage from './routes/IndexPage'
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
              <Route path="/" exact component={Login} />
              <Route path="/process" exact component={Process} />
              <Route path="/resource" exact component={Resource} />
            //   <Route path="*" render={() => <Redirect to="users" />} />
          </Switch>
      </App>
  </ConnectedRouter>
    )
}

export default RouterConfig