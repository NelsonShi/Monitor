import { Form, Icon, Input, Button, Checkbox } from 'antd';
import React, { Component } from 'react'
import { connect } from 'dva';
import styles from './Login.css';

class Login extends Component {
  handleSubmit = e => {
    e.preventDefault();
    const {dispatch}=this.props;
    this.props.form.validateFields((err, values) => {
      if (!err) {
        console.log('Received values of form: ', values);
        dispatch({ type: "login/login", payload: { user: values } });
      }
    });
  };

  render() {
    const { getFieldDecorator } = this.props.form;
    let faildStr= (this.props.loginStatus.logined&&!this.props.loginStatus.loginSuccess)?'login in failed':'';
    return (
      <div className={styles.normal}>
       <h2>Login In</h2>
       <Form onSubmit={this.handleSubmit} className="login-form">
        <Form.Item>
          {getFieldDecorator('username', {
            rules: [{ required: true, message: 'Please input your username!' }],
          })(
            <Input className={styles.input}
              prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="Username"
            />,
          )}
        </Form.Item>
        <Form.Item>
          {getFieldDecorator('password', {
            rules: [{ required: true, message: 'Please input your Password!' }],
          })(
            <Input className={styles.input}
              prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
              type="password"
              placeholder="Password"
            />,
          )}
        </Form.Item>
        <Form.Item>         
          <Button type="primary" htmlType="submit" className="login-form-button" style={{width:'80%'}}>
            Log in
          </Button>
           <p style={{color:'	#00BFFF'}}>
             <span style={{margin:'10px'}}>Username: Nelson</span>
             <span style={{margin:'10px'}}>Password: 1</span>
            </p>          
           <p style={{color:'red'}}>{faildStr}</p>                  
        </Form.Item>
      </Form>
      </div>
    );
  }
}

function mapStateToProps(state) {
  // 得到modal中的state)
  return {
      loginStatus: state.login.loginStatus,
  }
}

const WrappedNormalLoginForm = Form.create({ name: 'normal_login' })(Login);

export default connect(mapStateToProps)(WrappedNormalLoginForm);