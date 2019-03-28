import React from 'react';
import { Container, Content } from 'native-base';
import AppFooterContainer from './containers/AppFooterContainer';
import {createStore} from 'redux';
import {Provider} from 'react-redux';
import reducers from './reducers';
import MyContentContainer from './containers/MyContentContainer';

const initialState = {
    mode: 'STOCKS'
};
const store = createStore(reducers, initialState);

const App = () => (
    <Provider store={store}>
        <Container>
            <Content>
                <MyContentContainer/>
            </Content>
            <AppFooterContainer/>
        </Container>
    </Provider>
);

export default App;
