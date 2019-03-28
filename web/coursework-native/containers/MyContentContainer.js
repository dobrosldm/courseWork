import React from 'react';
import MyContent from '../components/MyContent.js';
import {connect} from 'react-redux';

const mapStateToProps = (state) => ({
    mode: state.mode
});

const MyContentContainer = ({mode}) => (
    <MyContent mode={mode}/>
);

export default connect(mapStateToProps)(MyContentContainer);