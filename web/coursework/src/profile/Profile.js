import React from 'react';
import {Password} from 'primereact/password';
import {InputText} from 'primereact/inputtext';
import {Button} from 'primereact/button';
import {RadioButton} from "primereact/radiobutton";
import {ListBox} from "primereact/listbox";
import {InputMask} from "primereact/inputmask";

export default class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            surname: '',
            middlename: '',
            gender: 'Не афишировать',
            birthDate : undefined,
            mobileTelephone : '',
            preference: 0,
            oldPassword: '',
            firstPassword: '',
            secondPassword: '',
        };
    }

    componentWillMount() {
        if(localStorage.getItem("token") === null) {
            this.props.history.replace('/main');
            return;
        }
        fetch('api/profile')
            .then(results => {
                return results.json();
            }).then(data => {
                this.setState({name: data.name});
                this.setState({surname: data.surname});
                this.setState({middlename: data.middlename});
                this.setState({gender: data.gender});
                this.setState({birthDate: data.birthDate});
                this.setState({mobileTelephone: data.mobileTelephone});
                this.setState({preference: data.preference});
            })
    }

    handleChange = (event) => {
        this.setState({[event.target.name]: event.target.value})
    };

    handleSubmitOfData = (event) => {
        event.preventDefault();
        const {name, surname, middlename, gender, birthDate, mobileTelephone, preference} = this.state;
        let errorMsg = this.validateData();
        if (errorMsg.length < 1) {
            fetch('api/profile', {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
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
                if (data === "Profile info updated") {
                    alert('Данные обновлены')
                }

            })
        } else {
            alert(errorMsg);
        }
    };

    validateData = () => {
        const {name, surname, middlename} = this.state;
        let message= '';
        if (/[а-яА-ЯA-Za-z]/.test(name) && /[а-яА-ЯA-Za-z]/.test(surname) && (middlename.length < 1 || middlename === null || /[а-яА-ЯA-Za-z]/.test(middlename))) {
            return message;
        } else {
            message += 'Исправьте следующие данные: ';
            if (!/[а-яА-ЯA-Za-z]/.test(name)) message += 'имя, ';
            if (!/[а-яА-ЯA-Za-z]/.test(surname)) message += 'фамилия, ';
            if (middlename.length < 1 && middlename === null && !/[а-яА-ЯA-Za-z]/.test(middlename)) message += ',отчество ';

            return message.substring(0, message.length - 2);
        }
    };

    handleSubmitOfPassword = (event) => {
        event.preventDefault();
        const {oldPassword, firstPassword, secondPassword} = this.state;
        let errorMsg = this.validatePassword();
        if (errorMsg.length < 1) {
            fetch('api/profile/changePassword', {
                method: 'post',
                headers: {'Content-Type': 'application/json'},
                body: JSON.stringify({
                    oldPassword: oldPassword,
                    firstPassword: firstPassword,
                    secondPassword: secondPassword,
                })
            }).then(response => {
                return response.text();
            }).then(data => {
                if (data === "Password updated")
                    alert('Пароль обновлён');
                if (data === "Passwords do not match")
                    alert("Новый пароль введён второй раз неверно");
                if (data === "Old password is wrong")
                    alert("Старый пароль введён неверно");
            })
        } else {
            alert(errorMsg);
        }
    };

    validatePassword = () => {
        const {oldPassword, firstPassword, secondPassword} = this.state;
        let message= '';
        if ((oldPassword.length > 0 && oldPassword !== null) && (firstPassword === secondPassword) && (firstPassword.length > 0 && firstPassword !== null)) {
            return message;
        } else {
            message += 'Следующие данные указаны неверно:';
            if (oldPassword.length < 1 || oldPassword === null) message += 'пустой старый пароль, ';
            if (firstPassword !== secondPassword) message += 'несовпадающие пароли, ';
            if (firstPassword.length < 1 || firstPassword === null || secondPassword.length < 1 || secondPassword === null) message += 'пустые новые пароли, ';

            return message.substring(0, message.length - 2);
        }
    };

    render() {
        const genders = [
            {gender: 'Не афишировать'},
            {gender: 'М'},
            {gender: 'Ж'}
        ];
        return (
            <div align="center">
                <form onSubmit={this.handleSubmitOfData}>
                    <div>
                        Имя:<br/>
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
                        Пол:<br/>
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
                    <div>
                        Выберете льготу:<br/>
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
                    <div>
                        <Button type='submit' label="Изменить данные" icon="pi pi-check" />
                    </div>
                </form>


                <form onSubmit={this.handleSubmitOfPassword}>
                    <div>
                        Старый пароль:<br/>
                        <Password name="oldPassword" feedback={false} onChange={this.handleChange} value={this.state.oldPassword}/>
                    </div>
                    <div>
                        Новый пароль:<br/>
                        <Password name="firstPassword" onChange={this.handleChange} value={this.state.firstPassword}/>
                    </div>
                    <div>
                        Повторите новый пароль:<br/>
                        <Password name="secondPassword" feedback={false} onChange={this.handleChange} value={this.state.secondPassword}/>
                    </div>
                    <div>
                        <Button label="Изменить пароль" icon="pi pi-check" />
                    </div>
                </form>
            </div>
        )
    }
}