import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter} from "react-router-dom";
import {Route} from "react-router";

import SideMenu from "./sideMenu/SideMenu";
import StartingPage from "./startingPage/StartingPage";
import Stocks from "./stocks/Stocks";
import Feedback from "./feedback/Feedback";
import Login from "./login/Login";
import Registration from "./registration/Registration";
import Profile from "./profile/Profile";
import Navigation from "./navigation/Navigation";
import Limits from "./limits/Limits";

ReactDOM.render(
    <BrowserRouter>
        <div className="header">
            <h1><i>Навигатор Будущего</i></h1>
        </div>
        <div className="row">
            <div className="column side">
                <Route path="/" name="menu" component={SideMenu} />
            </div>
            <div className="column middle">
                <Route path="/main" name="main" component={StartingPage} />
                <Route path="/stocks" name="stocks" component={Stocks} />
                <Route path="/feedback" name="feedback" component={Feedback} />
                <Route path="/login" name="login" component={Login} />
                <Route path="/registration" name="registration" component={Registration} />
                <Route path="/profile" name="profile" component={Profile} />
                <Route path="/navigation" name="navigation" component={Navigation}  />
                <Route path="/limits" name="limits" component={Limits} />
            </div>
        </div>
    </BrowserRouter>
    , document.getElementById('app'));