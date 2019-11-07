import React, { Component } from 'react'
import { connect } from 'dva'

import styles from './Users.less'


class Users extends Component {
    render() {
        return (
            <div className={styles.normal}>
                Users
            </div>
        )
    }
}

Users.propTypes = {}

export default connect()(Users)
