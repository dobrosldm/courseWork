import React from 'react';
import {Text, View, TextInput, StyleSheet} from 'react-native';
import {Button} from 'react-native-elements';

export default class Feedback extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: '',
            email: '',
            message: '',
            validated: false,
            error: false,
            infoMsg: ''
        };
    }

    handleSubmit = (event) => {
        event.preventDefault();
        const {name, email, message} = this.state;
        let errorMsg = this.validate();
        this.setState({validated: true});
        if (errorMsg.length < 1) {
            fetch('http://localhost:24315/api/placeFeedback', {
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
                if (data === "Report feedback sent") {
                    this.setState({error: false});
                    this.setState({infoMsg: 'Отзыв отправлен'});
                    this.render();
                }
            })
        } else {
            this.setState({error: true});
            this.setState({infoMsg: errorMsg});
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

    styles = StyleSheet.create({
        goodAlert: {
            color: '#20a900',
            backgroundColor: '#cbf2cf',
            padding: 15,
            marginBottom: 20,
            borderWidth: 1,
            borderStyle: 'solid',
            borderColor: '#67eb24',
            borderRadius: 4
        },
        badAlert: {
            color: '#a94442',
            backgroundColor: '#f2dede',
            padding: 15,
            marginBottom: 20,
            borderWidth: 1,
            borderStyle: 'solid',
            borderColor: '#ebaba7',
            borderRadius: 4
        },
        input: {
            height: 50
        },
        form: {
            flex: 1,
            justifyContent: 'center'
        }
    });

    render() {
        const {name, email, message} = this.state;
        return (
            <View style={this.styles.form}>
                <TextInput style={this.styles.input} underlineColorAndroid='#D3D3D3' name="name" label='Имя' onChangeText={(name) =>  {
                    this.setState({name});
                    this.setState({validated: false});
                }} value={name} placeholder='Введите ваше имя'/>

                <TextInput style={this.styles.input} underlineColorAndroid='#D3D3D3' name="email" onChangeText={(email) => {
                    this.setState({validated: false});
                    this.setState({email})
                }} value={email} placeholder='Введите вашу почту'/>

                <TextInput underlineColorAndroid='#D3D3D3' name="message" multiline={true} numberOfLines={5} maxLength={500}
                           onChangeText={(message) => {
                               this.setState({validated: false});
                               this.setState({message})
                           }} value={message} placeholder='Напишите сообщение замечательным админам'/>

                <Button type='outline' title="Отправить отзыв" onPress={this.handleSubmit} />

                <Text>{'\n'}</Text>

                {!this.state.error && this.state.validated && <View key={'goodAlert'} style={this.styles.goodAlert}>
                    <Text>{this.state.infoMsg}</Text>
                </View>}
                {this.state.validated && this.state.error && <View key={'badAlert'} style={this.styles.badAlert}>
                    <Text>{this.state.infoMsg}</Text>
                </View>}
            </View>
        )
    }
}