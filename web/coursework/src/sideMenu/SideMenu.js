import React from 'react';
import {Menu} from 'primereact/menu';

const SideMenu = (props) => {
    let items1 = [
            {label: 'Главная', icon: 'pi pi-fw pi-home', command: () => { props.history.push('/main') }},
            {label: 'Навигация', icon: 'pi pi-map-marker', command: () => { props.history.push('/navigation') }},
            {label: 'Акции', icon: 'pi pi-calendar', command: () => { props.history.push('/stocks') }},
            {label: 'Лимиты', icon: 'pi pi-info-circle', command: () => { props.history.push('/limits') }},
            {label: 'Обратная связь', icon: 'pi pi-envelope', command: () => { props.history.push('/feedback') } }
        ],
        items2 = [
            {label: 'Регистрация', icon: 'pi pi-user-plus', command: () => { props.history.push('/registration') }},
            {label: 'Вход', icon: 'pi pi-sign-in', command: () => { props.history.push('/login') }},
            {label: 'Настройки', icon: 'pi pi-cog', command: () => { props.history.push('/profile') }},
            {label: 'Выйти', icon: 'pi pi-sign-out', command:() => {
                fetch("api/auth/logout", {method: 'post'})
                    .then(response => {
                        return response.text();
                    }).then(data => {
                    if (data === "Logged out")
                        // alert('Что ты мне сделаешь я вдругой сессии');
                        props.history.push('/main');
                })
            }}

        ];
    return (
        <div>
            <Menu model={items1} />
            <br/>
            <Menu model={items2} />
        </div>
    );
};

export default SideMenu;