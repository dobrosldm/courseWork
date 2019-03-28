import React from 'react';
import {Button} from "primereact/button";
import {Password} from "primereact/password";
import {InputText} from "primereact/inputtext";
import {RadioButton} from "primereact/radiobutton";
import {ListBox} from "primereact/listbox";
import {InputMask} from "primereact/inputmask";

export default class Registration extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            firstPassword: '',
            secondPassword: '',
            name: '',
            surname: '',
            middlename: '',
            gender: 'Не афишировать',
            birthDate : undefined,
            mobileTelephone : null,
            preference: 0,
            showAl: false,
            alText: ""
        };
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    };

    handleSubmit = (event) => {
        event.preventDefault();
        this.setState({showAl: false});
        const {email, firstPassword, secondPassword, name, surname, middlename, gender, birthDate, mobileTelephone, preference} = this.state;
        let errorMsg = this.validate();
        if (errorMsg.length < 1) {
            fetch('api/auth/register', {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    email: email,
                    firstPassword: firstPassword,
                    secondPassword: secondPassword,
                    name: name,
                    surname: surname,
                    middlename: middlename,
                    sex: gender,
                    birthDate: birthDate,
                    mobileTelephone: mobileTelephone,
                    preference: preference
                })
            }).then(response => {
                return response.text();
            }).then(data => {
                if (data === "Successfully registered") {
                    fetch('j_security_check?j_username='+this.state.email+'&j_password='+this.state.firstPassword, {
                        method: 'post'
                    }).then(response => {
                        if (response.ok) this.props.history.push('/login');
                        else console.log("Интернал еррор")
                        }).catch(err => console.log(err.message))
                }

            })
        } else {
            this.setState({showAl: true, alText: errorMsg});
        }
    };

    validate = () => {
        const {email, name, surname, middlename, firstPassword, secondPassword} = this.state;
        let message= '';
        // eslint-disable-next-line
        if (/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email.toLowerCase())
            && /[а-яА-ЯA-Za-z]/.test(name) && /[а-яА-ЯA-Za-z]/.test(surname) && (middlename.length < 1 || middlename === null || /[а-яА-ЯA-Za-z]/.test(middlename)) &&
            (firstPassword === secondPassword) && (firstPassword.length > 0 && firstPassword !== null)) {
            return message;
        } else {
            message += 'Исправьте следующие данные: ';
            // eslint-disable-next-line
            if (!/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email.toLowerCase()))
                message += 'эл. адрес, ';
            if (!/[а-яА-ЯA-Za-z]/.test(name)) message += 'имя, ';
            if (!/[а-яА-ЯA-Za-z]/.test(surname)) message += 'фамилия, ';
            if (middlename.length < 1 && middlename === null && !/[а-яА-ЯA-Za-z]/.test(middlename)) message += 'отчество, ';
            if (firstPassword !== secondPassword) message += 'несовпадающие пароли, ';
            if (firstPassword.length < 1 || firstPassword === null || secondPassword.length < 1 || secondPassword === null) message += 'пустые пароли, ';

            return message.substring(0, message.length - 2);
        }
    };

    handleGoogle = () => {
        localStorage.setItem("token", 'OAuth');
    };

    render() {
        const genders = [
            {gender: 'Не афишировать'},
            {gender: 'М'},
            {gender: 'Ж'}
        ];
        return (
            <div align="center">
                <h2>Регистрация</h2>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        Адрес электронной почты:<br/>
                        <InputText name="email" keyfilter="email" onChange={this.handleChange} value={this.state.email}/>
                    </div>
                    <div>
                        Придумайте пароль:<br/>
                        <Password name="firstPassword" onChange={this.handleChange} value={this.state.firstPassword}/>
                    </div>
                    <div>
                        Повторите пароль:<br/>
                        <Password name="secondPassword" onChange={this.handleChange} value={this.state.secondPassword} feedback={false} />
                    </div>
                    <div>
                        <br/>Имя:<br/>
                        <InputText name="name" keyfilter={/[а-яА-ЯA-Za-z]/} onChange={this.handleChange} value={this.state.name}/>
                    </div>
                    <div>
                        Фамилия:<br/>
                        <InputText name="surname" keyfilter={/[а-яА-ЯA-Za-z]/} onChange={this.handleChange} value={this.state.surname}/>
                    </div>
                    <div>
                        Отчество:<br/>
                        <InputText name="middlename" keyfilter={/[а-яА-ЯA-Za-z]/} onChange={this.handleChange} value={this.state.middlename}/>
                    </div>
                    <div>
                        <br/>Пол:<br/>
                        <ListBox name="gender" value={this.state.gender} options={genders} onChange={(event) => {this.setState({gender: event.value.gender})}} optionLabel="gender"/>
                    </div>
                    <div>
                        Дата рождения:<br/>
                        <input type="date" name="birthDate" value={this.state.birthDate} onChange={this.handleChange}/>
                        {/*<Calendar name="birthDate" dateFormat="dd/mm/yy" value={birthDate} onChange={this.handleChange} monthNavigator={true} yearNavigator={true} yearRange="1900:2019"/>*/}
                    </div>
                    <div>
                        Номер телефона:<br/>
                        <InputMask name="mobileTelephone" mask="+79999999999" placeholder="+7**********" value={this.state.mobileTelephone} onChange={this.handleChange} />
                    </div>
                    <br/>
                    Выберете льготу:<br/>
                    <div className="lgota">
                        <RadioButton name="preference" value={0} onChange={this.handleChange} checked={this.state.preference === 0} /> Нет льготы<br/>
                        <RadioButton name="preference" value={1} onChange={this.handleChange} checked={this.state.preference === 1} /> Безработный<br/>
                        <RadioButton name="preference" value={2} onChange={this.handleChange} checked={this.state.preference === 2} /> Пенсионер<br/>
                        <RadioButton name="preference" value={3} onChange={this.handleChange} checked={this.state.preference === 3} /> Политрепрессированный<br/>
                        <RadioButton name="preference" value={4} onChange={this.handleChange} checked={this.state.preference === 4} /> Многодетный<br/>
                        <RadioButton name="preference" value={5} onChange={this.handleChange} checked={this.state.preference === 5} /> Ветеран<br/>
                        <RadioButton name="preference" value={6} onChange={this.handleChange} checked={this.state.preference === 6} /> Студент<br/>
                        <RadioButton name="preference" value={7} onChange={this.handleChange} checked={this.state.preference === 7} /> Ученик<br/>
                        <RadioButton name="preference" value={8} onChange={this.handleChange} checked={this.state.preference === 8} /> Инвалид<br/>
                    </div>
                    <br/>
                    <div>
                        <Button type='submit' label="Зарегистрироваться" icon="pi pi-check" />
                    </div>
                </form>
                <br/>
                {this.state.showAl && <div><div className="badAlert">{this.state.alText}</div><br/></div>}
                <form action="api/auth/googleCode" method="POST">
                    <Button label="Зарегистрироваться через Google" icon="pi pi-check" onClick={this.handleGoogle}/>
                </form>
            </div>
        );
    }
}