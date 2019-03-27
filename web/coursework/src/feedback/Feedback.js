import React from 'react';
import {InputText} from 'primereact/inputtext';
import {InputTextarea} from 'primereact/inputtextarea';
import {Button} from 'primereact/button';

export default class Feedback extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            email: '',
            message: ''
        };
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    };

    handleSubmit = (event) => {
        event.preventDefault();
        const {name, email, message} = this.state;
        let errorMsg = this.validatePassword();
        if (errorMsg.length < 1) {
            fetch('api/placeFeedback', {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    name: name,
                    email: email,
                    message: message,
                })
            }).then(response => {
                return response.text();
            }).then(data => {
                if (data === "Report feedback sent")
                    alert('Отзыв отправлен');
            })
        } else {
            alert(errorMsg);
        }
    };

    validate = () => {
        const {email, name} = this.state;
        let message= '';
        // eslint-disable-next-line
        if (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email.toLowerCase())
            && /[а-яА-ЯA-Za-z]/.test(name)) {
            return message;
        } else {
            message += 'Исправьте следующие данные: ';
            // eslint-disable-next-line
            if (!/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email.toLowerCase()))
                message += 'эл. адрес, ';
            if (!/[а-яА-ЯA-Za-z]/.test(name)) message += 'имя, ';

            return message.substring(0, message.length - 2);
        }
    };

    render() {
        const {name, email, message} = this.state;
        return (
            <div align="center">
                <form onSubmit={this.handleSubmit}>
                    <div>
                        Имя:<br/>
                        <InputText name="name" onChange={this.handleChange} value={name}/>
                    </div>
                    <div>
                        Адрес электронной почты:<br/>
                        <InputText name="email" onChange={this.handleChange} value={email}/>
                    </div>
                    <div>
                        Текст отзыва:<br/>
                        <InputTextarea name="message" rows={10} cols={50} autoResize={true} onChange={this.handleChange} value={message}/>
                    </div>
                    <Button label="Отправить отзыв" icon="pi pi-check" />
                </form>
            </div>
        )
    }
}