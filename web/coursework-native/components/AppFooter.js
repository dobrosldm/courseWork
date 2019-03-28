import React from 'react';
import { Footer, FooterTab, Button, Text} from 'native-base';
import { Font, AppLoading } from "expo";

export default class AppFooter extends React.Component {
    constructor(props) {
        super(props);
        this.state = { loading: true };
    }

    async componentWillMount() {
        await Font.loadAsync({
            Roboto: require("native-base/Fonts/Roboto.ttf"),
            Roboto_medium: require("native-base/Fonts/Roboto_medium.ttf")
        });
        this.setState({ loading: false });
    }

    render() {
        const {mode = 'STOCKS', setMode = () => {}} = this.props;
        if (this.state.loading) {
            return (
                <AppLoading/>
            )
        } else {
            return (
                <Footer>
                    <FooterTab>
                        <Button active={mode === 'STOCKS'} onPress={() => setMode('STOCKS')}>
                            <Text>Акции</Text>
                        </Button>
                        <Button active={mode === 'FEEDBACK'} onPress={() => setMode('FEEDBACK')}>
                            <Text>Отзыв</Text>
                        </Button>
                    </FooterTab>
                </Footer>
            )
        }
    }
}