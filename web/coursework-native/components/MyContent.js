import {StyleSheet, Text, View} from "react-native";
import Stocks from "../components/Stocks";
import Feedback from "../components/Feedback";
import React from "react";

const styles = StyleSheet.create({
    container: {
        padding: 75,
        alignItems: 'center',
        justifyContent: 'center',
        flex: 1,
        backgroundColor: '#fff'
    },
    head: {
        color: '#6826ff',
        fontSize: 21,
        fontWeight: 'bold',
        textAlign: 'center'
    }
});

const MyContent = ({mode = 'STOCKS'}) => (
    <View style={styles.container}>
        <Text style={styles.head}>Добро пожаловать в Навигатор Будущего!{"\n"}</Text>
        {mode === 'STOCKS' && <Stocks/>}
        {mode === 'FEEDBACK' && <Feedback/>}
    </View>
);

export default MyContent;