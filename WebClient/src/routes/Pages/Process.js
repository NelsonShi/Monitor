import React, { Component } from 'react'
import { connect } from 'dva'


class Process extends Component {
    render() {
        return (
            <div>
                <h4> this is process page! Waiting for coding!</h4>
            </div>
        )
    }
}

Process.propTypes = {}



export default connect()(Process)