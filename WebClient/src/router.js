/* eslint-disable react/jsx-no-comment-textnodes */
import React from 'react'
import {Switch, routerRedux, Route } from 'dva/router'
import PrivateRoute from './routes/PrivateRouter'
import App from './routes/App'

import dynamic from 'dva/dynamic' // 按需加载路由

const { ConnectedRouter } = routerRedux

function RouterConfig({ history, app }) {
    const Process = dynamic({
        app,
        component: () => import('./routes/Pages/Process')
    })

    const errorPage = dynamic({
        app,
        component: () => import('./routes/Pages/404')
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
          <Switch>
              <Route path="/login" exact component={Login} />
              <App>
                 <Switch>
                   <PrivateRoute path="/process" exact component={Process} />
                   <PrivateRoute path="/resource" exact component={Resource} /> 
                   <PrivateRoute  exact component={errorPage} />   
                 </Switch>          
              </App>          
          </Switch>
      </ConnectedRouter>
    )
}

export default RouterConfig