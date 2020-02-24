import dva from 'dva'
// import createLogger from 'redux-logger'
import createLoading from 'dva-loading'
import { message } from 'antd'
import './index.css'
// const createHistory = require("history").createHashHistory
import { createBrowserHistory } from 'history';

const ERROR_MSG_DURATION = 3

// 1. Initialize
const app = dva({
    history: createBrowserHistory(),
    ...createLoading({ effects: true }),
    initialState: {
        '@@dva': {
            c: 123,
            b: 321
        }
    },
    onError(e, dispatch) {
        message.error(e.message, ERROR_MSG_DURATION)
    },
    onStateChange(state) {
        // console.log(state)
    }
})

// 2. Plugins
// app.use({});

// 3. Model
app.model(require('./models/resource').default);
app.model(require('./models/timer').default);
app.model(require('./models/login').default);
app.model(require('./models/process').default);
app.model(require('./models/control').default);

// 4. Router
app.router(require('./router').default);

// 5. Start
app.start('#root');
