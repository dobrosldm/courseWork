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
        };
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    };

    handleGoogle = () => {
        /*fetch("http://localhost:24315/api/auth/googleCode", {method: "POST", redirect: "follow"})
            .then(response => {
                return response;
            }).then(data => {
                console.log(data.url);
                window.open(data.url, "_self")
            })*/
    };

    handleSubmit = (event) => {
        event.preventDefault();
        if (this.state.j_username === null || this.state.j_username.length < 1) {
            alert("Почту введи, друг"); return;
        }
        if (this.state.j_password === null || this.state.j_password.length < 1) {
            alert("Тi забыл пароль"); return;
        }
        fetch('j_security_check?j_username='+this.state.j_username+'&j_password='+this.state.j_password, {
            method: 'post'
        }).then(response => {
            if (response.ok) this.props.history.push('/navigation');
            else alert("Тi не понравился серверу")
            }).catch(err => alert(err.message))
    };

    render() {
        return (
            <center>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        EMail:<br/>
                        <InputText name="j_username" value={this.state.j_username} onChange={this.handleChange}/>
                    </div>
                    <div>
                        Password:<br/>
                        <Password name="j_password" value={this.state.j_password} onChange={this.handleChange} feedback={false} />
                    </div>
                    <div>
                        <Button label="Login" icon="pi pi-check" />
                    </div>
                </form>
                <br/>
                <form method="post" action="api/auth/googleCode">
                    <Button label="Зайти через Google" icon="pi pi-check" onClick={this.handleGoogle}/>
                </form>
            </center>
        )
    }
}