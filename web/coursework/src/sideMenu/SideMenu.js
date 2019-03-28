import React from 'react';
import {Menu} from 'primereact/menu';

export default class SideMenu extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            items1 : [
                {label: 'Главная', icon: 'pi pi-fw pi-home', command: () => { props.history.push('/main') }},
                {label: 'Акции', icon: 'pi pi-calendar', command: () => { props.history.push('/stocks') }},
                {label: 'Обратная связь', icon: 'pi pi-envelope', command: () => { props.history.push('/feedback') } }
            ],
            items2 : [
                {label: 'Главная', icon: 'pi pi-fw pi-home', command: () => { props.history.push('/main') }},
                {label: 'Навигация', icon: 'pi pi-map-marker', command: () => { props.history.push('/navigation') }},
                {label: 'Акции', icon: 'pi pi-calendar', command: () => { props.history.push('/stocks') }},
                {label: 'Лимиты', icon: 'pi pi-info-circle', command: () => { props.history.push('/limits') }},
                {label: 'Обратная связь', icon: 'pi pi-envelope', command: () => { props.history.push('/feedback') } }
            ],
            items3 : [
                {label: 'Вход', icon: 'pi pi-sign-in', command: () => { props.history.push('/login') }},
                {label: 'Регистрация', icon: 'pi pi-user-plus', command: () => { props.history.push('/registration') }}
            ],
            items4 : [
                {label: 'Настройки', icon: 'pi pi-cog', command: () => { props.history.push('/profile') }},
                {label: 'Выйти', icon: 'pi pi-sign-out', command:() => {
                        fetch("api/auth/logout", {method: 'post'})
                            .then(response => {
                                return response.text();
                            }).then(data => {
                            if (data === "Logged out") {
                                // alert('Что ты мне сделаешь я вдругой сессии');
                                localStorage.removeItem("token");
                                props.history.push('/main');
                            }
                        })
                    }}

            ]
        };
    }

    componentWillMount(){
        this.props.history.replace('/main');
    }

    render() {
        return (
            <div className="ggg">
                <Menu model={this.state.items1} className={!(localStorage.getItem("token") === null) && "hidden"} />
                <Menu model={this.state.items2} className={(localStorage.getItem("token") === null) && "hidden"} />
                <br/>
                <Menu model={this.state.items3} className={!(localStorage.getItem("token") === null) && "hidden"} />
                <Menu model={this.state.items4} className={(localStorage.getItem("token") === null) && "hidden"} />
            </div>
        )
    }
}