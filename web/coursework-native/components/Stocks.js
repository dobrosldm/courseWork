import React from 'react';
import {StyleSheet, Text, View} from 'react-native';

export default class Stocks extends React.Component {
    constructor(props) {
        super(props);
        this.state = {stocks: []};
    }

    componentDidMount() {
        fetch('http://localhost:24315/api/stocks')
            .then(results => {
                return results.json();
            }).then(data => {
            this.setState({stocks: data});
        })
    }

    styles = StyleSheet.create({
        naming: {
            padding: 15,
            color: '#0b060f',
            fontSize: 14,
            fontWeight: 'bold',
            textAlign: 'center'
        },
        addition: {
            color: '#0b060f',
            fontSize: 14,
            textAlign: 'center'
        }
    });

    render() {
        return (
            <View>
                {
                    this.state.stocks.map(stock => {
                        return (
                            <View key={stock.naming}>
                                <Text style={this.styles.naming}>{stock.naming} {stock.date}{"\n"}</Text>
                                <Text style={this.styles.addition}>Надбавка: {stock.addition} лимитов{"\n\n"}</Text>
                            </View>
                        )
                    })
                }
            </View>
        )
    }
}