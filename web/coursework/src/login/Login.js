import React from 'react';
import {Password} from 'primereact/password';
import {InputText} from 'primereact/inputtext';
import {Button} from 'primereact/button';

export default class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            j_username: '',
            j_password: '',
            succLog: false,
            errData: false
        };
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    };

    handleGoogle = () => {
        localStorage.setItem("token", 'OAuth');
    };

    handleSubmit = (event) => {
        event.preventDefault();
        this.setState({errData: false});
        if (this.state.j_username === null || this.state.j_username.length < 1) {
            this.setState({errData: true}); return;
        }
        if (this.state.j_password === null || this.state.j_password.length < 1) {
            this.setState({errData: true}); return;
        }
        fetch('j_security_check?j_username='+this.state.j_username+'&j_password='+this.state.j_password, {
            method: 'post'
        }).then(response => {
            if (response.ok) {
                localStorage.setItem("token", this.state.j_username);
                this.setState({succLog: false});
                this.props.history.push('/navigation');
            }
            else
                this.setState({errData: true});
            }).catch(err => alert(err.message))
    };

    render() {
        return (
            <div>
                <form method="post" action="api/auth/googleCode">
                    <Button label="Войти через Google" icon="pi pi-check" onClick={this.handleGoogle}/>
                </form>
                <br/>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        Введите eMail:<br/>
                        <InputText name="j_username" value={this.state.j_username} onChange={this.handleChange}/>
                    </div>
                    <div>
                        Введите пароль:<br/>
                        <Password name="j_password" value={this.state.j_password} onChange={this.handleChange} feedback={false} />
                    </div>
                    <div>
                        <Button label="Войти" icon="pi pi-check" />
                    </div>
                </form>
                <br/>
                {this.state.succLog && <div className="goodAlert">Вы успешно вошли</div>}
                {this.state.errData && <div className="badAlert">Введены неверные данные</div>}
            </div>
        )
    }
}